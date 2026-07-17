(ns ordinance.facts
  "Municipal-ordinance compliance catalog for Lisbon -- the FORTY-SIXTH
  municipality-level entry (see cloud-itonami-municipality-jpn-tokyo,
  -usa-washington-dc, -gbr-london, -can-toronto, -deu-berlin, -fra-paris,
  -nld-amsterdam, -esp-madrid, -kor-seoul, -ita-roma, -aus-sydney,
  -arg-buenos-aires, -fin-helsinki, -dnk-copenhagen, -nor-oslo,
  -bel-brussels, -chl-santiago, -col-bogota, -cri-san-jose,
  -bra-sao-paulo, -ury-montevideo, -zaf-cape-town, -ecu-quito,
  -swe-gothenburg, -pry-asuncion, -mex-guadalajara, -fra-lyon,
  -ind-new-delhi, -pol-warsaw, -ken-nairobi, -tha-bangkok, -are-abu-dhabi,
  -vnm-hanoi, -idn-jakarta, -phl-manila, -egy-cairo, -tur-ankara,
  -nga-abuja, -sau-riyadh, -mys-kuala-lumpur, -aut-vienna, -che-bern,
  -irl-dublin, -nzl-wellington, -cze-prague for the first forty-five)
  per ADR-2607141700 (cloud-itonami-compliance-fact-federation).
  Portugal's first entry across the municipality axis, closing one of
  the 4 remaining structural country-without-municipality gaps
  identified at tick 141 (GTM/HND/PAN/PRT).

  Lisbon is Portugal's stable capital (Wikidata Q597), with no
  ongoing ambiguity.

  Lei n.º 169/99, de 18 de Setembro (establishing the framework of
  competencies and the legal regime governing the functioning of
  municipal and parish bodies in Portugal) -- title, law number, and
  date directly confirmed via pgdlisboa.pt (the Portuguese Public
  Prosecutor's Office / Ministério Público's own official legal
  database), which states verbatim 'Lei n.º 169/99, de 18 de
  Setembro' with the subject 'Autarquias Locais - Competências e
  Regime Jurídico'.

  The 1147 Portuguese/Christian conquest of Lisbon -- directly
  confirmed via en.wikipedia.org's own 'History of Lisbon' article,
  which states verbatim: 'In 1147, after a four-month siege,
  Christian crusaders under the command of Afonso I captured the city
  and Christian rule returned' and 'the Moors capitulated on 22
  October.' King Afonso I's name, referenced only as historical
  context for a documented 12th-century military event (not a modern
  office-holder), is mentioned here but not persisted as a standalone
  fact.

  An ordinance not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url/date.")

(def catalog
  "municipality-slug -> vector of ordinance entries."
  {"lisbon"
   [{:ordinance/id "lisbon.lei-169-1999-autarquias-locais"
     :ordinance/title "Lei n.º 169/99, de 18 de Setembro (Autarquias Locais - Competências e Regime Jurídico)"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :local-act
     :ordinance/number "Lei n.º 169/99"
     :ordinance/url "https://www.pgdlisboa.pt/leis/lei_mostra_articulado.php?nid=592&tabela=lei_velhas&nversao=4"
     :ordinance/url-provenance :official-pgdlisboa-pt
     :ordinance/enacted-date "1999-09-18"
     :ordinance/retrieved-at "2026-07-18"
     :ordinance/topic #{:governance}}
    {:ordinance/id "lisbon.1147-portuguese-conquest"
     :ordinance/title "1147 Siege of Lisbon: after a four-month siege, Christian crusaders captured the city and Christian rule returned; the Moors capitulated on 22 October"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :local-act
     :ordinance/number "1147"
     :ordinance/url "https://en.wikipedia.org/wiki/History_of_Lisbon"
     :ordinance/url-provenance :wikipedia-corroborated
     :ordinance/enacted-date "1147-10-22"
     :ordinance/retrieved-at "2026-07-18"
     :ordinance/topic #{:governance}}]})

(defn spec-basis [muni] (get catalog muni))

(defn coverage
  ([] (coverage (keys catalog)))
  ([munis]
   (let [have (filter catalog munis)
         missing (remove catalog munis)]
     {:requested (count munis)
      :covered (count have)
      :covered-municipalities (vec (sort have))
      :missing-municipalities (vec (sort missing))
      :note (str "cloud-itonami-municipality-prt-lisbon Wave 0 (ADR-2607141700): "
                 (count (get catalog "lisbon")) " Lisbon entries seeded "
                 "with pgdlisboa.pt/Wikipedia citations. "
                 "Extend `ordinance.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [muni topic]
  (filterv #(contains? (:ordinance/topic %) topic) (spec-basis muni)))
