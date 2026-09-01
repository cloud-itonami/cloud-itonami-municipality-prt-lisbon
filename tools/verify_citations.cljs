#!/usr/bin/env nbb
;; tools/verify_citations.cljs — re-fetch every :ordinance/url in
;; data/datascript-tx.edn and confirm that :ordinance/url-verified-phrase
;; is actually present in the document.
;;
;; ## Why not a status-code check
;;
;; Measured 2026-09-01: https://diariodarepublica.pt/dr/detalhe/aviso/
;; 99999-2099-000000 — an aviso id that does not exist — returns HTTP 200
;; with the same 2,346-byte JavaScript shell as a real one. So does
;; https://files.diariodarepublica.pt/2s/2099/99/999999999/0000000000.pdf.
;; A `curl -o /dev/null -w %{http_code}` gate against those hosts is a
;; check that cannot fail, which makes a green run indistinguishable from
;; no run at all. This verifier reads the document instead.
;;
;; Exit codes are three-valued on purpose:
;;   0  every citation fetched AND its phrase found
;;   1  at least one citation is wrong (phrase absent, or fetch failed)
;;   2  REFUSED — could not answer (no entries, no pdftotext, nothing
;;      scanned). Never reported as a pass.
;;
;; Usage:  nbb tools/verify_citations.cljs [--only <ordinance-id>]

(ns verify-citations
  (:require ["fs" :as fs]
            ["os" :as os]
            ["path" :as path]
            ["child_process" :as cp]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(def argv (vec (drop 2 (.-argv js/process))))
(def only (second (drop-while #(not= "--only" %) argv)))

(defn die! [code & msg]
  (binding [*print-fn* *print-err-fn*] (apply println msg))
  (.exit js/process code))

(defn norm
  "Collapse every run of whitespace to one space. PDF text arrives wrapped
   at the column the gazette was typeset to, so a phrase quoted from the
   page spans line breaks; comparing raw text would reject correct
   citations. Non-breaking and narrow spaces are whitespace too."
  [s]
  (-> s
      (str/replace #"[    ]" " ")
      (str/replace #"\s+" " ")
      str/trim))

(defn strip-tags [s]
  (-> s
      (str/replace #"(?is)<(script|style)\b[^>]*>.*?</\1>" " ")
      (str/replace #"(?s)<[^>]+>" " ")
      (str/replace #"&nbsp;" " ")))

(defn charset-of [content-type]
  (let [m (re-find #"(?i)charset=\s*\"?([\w-]+)" (or content-type ""))]
    (str/lower-case (or (second m) "utf-8"))))

(defn have-pdftotext? []
  (try (cp/execSync "command -v pdftotext" #js {:stdio "ignore"}) true
       (catch :default _ false)))

(defn pdf->text [buf]
  (let [f (path/join (os/tmpdir) (str "cite-" (rand-int 1e9) ".pdf"))]
    (try
      (fs/writeFileSync f buf)
      (.toString (cp/execSync (str "pdftotext " (pr-str f) " -")
                              #js {:maxBuffer (* 256 1024 1024)}))
      (finally (try (fs/unlinkSync f) (catch :default _ nil))))))

(defn fetch-text
  "-> {:text s} | {:error msg}. The response body is kept on failure: a
   check that throws away what the server said cannot explain itself."
  [url]
  (-> (js/fetch url #js {:redirect "follow"
                         :headers #js {"user-agent" "cloud-itonami-municipality-prt-lisbon citation verifier"}})
      (.then (fn [r]
               (if-not (.-ok r)
                 (.then (.text r) (fn [b] {:error (str "HTTP " (.-status r) " — " (subs (norm b) 0 200))}))
                 (.then (.arrayBuffer r)
                        (fn [ab]
                          (let [buf (js/Buffer.from ab)
                                ct (or (.get (.-headers r) "content-type") "")]
                            (cond
                              (str/includes? ct "application/pdf")
                              {:text (pdf->text buf)}

                              ;; A 2,346-byte HTML body from files.diariodarepublica.pt
                              ;; is the SPA shell served for paths that do not exist.
                              ;; Report it as the miss it is, not as a document.
                              :else
                              (let [s (.decode (js/TextDecoder. (charset-of ct)) buf)]
                                (if (< (count s) 4000)
                                  {:error (str "body is " (count s) " bytes of " ct
                                               " — too small to be the cited document"
                                               " (this host answers 200 with a JS shell for paths that do not exist)")}
                                  {:text (strip-tags s)})))))))))
      (.catch (fn [e] {:error (str "fetch failed: " (.-message e))}))))

(defn -main []
  (when-not (have-pdftotext?)
    (die! 2 "REFUSED: pdftotext is not on PATH; the gazette citations are PDFs and cannot be read."
          "\n  Install it (poppler) and re-run. Reporting a pass without reading them would be a lie."))
  (let [f "data/datascript-tx.edn"]
    (when-not (fs/existsSync f)
      (die! 2 (str "REFUSED: " f " not found (run from the repo root).")))
    (let [rows (edn/read-string (str (fs/readFileSync f "utf8")))
          rows (if only (filterv #(= only (:ordinance/id %)) rows) rows)]
      (when (empty? rows)
        (die! 2 (str "REFUSED: 0 citations to check"
                     (when only (str " (no ordinance with id " (pr-str only) ")"))
                     " — an empty input is not a clean result.")))
      (-> (js/Promise.all
           (clj->js
            (for [{:ordinance/keys [id url url-verified-phrase]} rows]
              (if (str/blank? url-verified-phrase)
                (js/Promise.resolve
                 {:id id :ok false
                  :why "no :ordinance/url-verified-phrase — this citation is UNVERIFIED, which is not the same as verified"})
                (.then (fetch-text url)
                       (fn [{:keys [text error]}]
                         (cond
                           error {:id id :ok false :why error}
                           (str/includes? (norm text) (norm url-verified-phrase))
                           {:id id :ok true :bytes (count text)}
                           :else
                           {:id id :ok false
                            :why (str "fetched " (count text) " chars but the phrase "
                                      (pr-str url-verified-phrase) " is not in them")})))))))
          (.then (fn [rs]
                   (let [rs (js->clj rs :keywordize-keys true)
                         bad (remove :ok rs)]
                     (doseq [{:keys [id ok why bytes]} rs]
                       (println (if ok "OK  " "FAIL") id (if ok (str "(" bytes " chars)") (str "— " why))))
                     (println (str "SCANNED\t" (count rs)))
                     (when (zero? (count rs))
                       (die! 2 "REFUSED: scanned 0 citations."))
                     (println (str "VERIFIED\t" (- (count rs) (count bad))))
                     (println (str "FAILED\t" (count bad)))
                     (.exit js/process (if (seq bad) 1 0)))))))))

(-main)
