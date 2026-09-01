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

  Lisbon is Portugal's stable capital (Wikidata Q597), with no
  ongoing ambiguity.

  ## Every `:ordinance/url` here is content-verified, not status-verified

  `diariodarepublica.pt/dr/detalhe/...` is a client-rendered page: the
  server returns HTTP 200 with a 2,346-byte JavaScript shell for a
  FABRICATED aviso id exactly as it does for a real one (measured
  2026-09-01: `/dr/detalhe/aviso/99999-2099-000000` -> 200). A
  status-code check against that host therefore cannot fail, so it
  cannot be evidence. This catalog cites the **published gazette PDFs**
  (`files.diariodarepublica.pt`) and the municipality's own PDFs
  (`lisboa.pt`) instead, and every entry carries
  `:ordinance/url-verified-phrase` -- a string quoted verbatim from
  that document. `tools/verify_citations.cljs` re-fetches each URL and
  fails unless the phrase is present in the extracted text, so the
  check discriminates in both directions (a wrong URL, a moved
  document, or a mis-transcribed number all turn it red).

  ## Sourcing

  The 2026-07-18 Wave 0 seeding cited pgdlisboa.pt (the Portuguese
  Public Prosecutor's Office's own official legal database) and
  en.wikipedia.org. The 2026-09-01 extension adds nine entries drawn
  from Portugal's official gazette and from Lisbon's own published
  regulation PDFs.

  Note on `:ordinance/kind`: the two Wave 0 entries were both seeded as
  `:local-act`, including Lei n.º 169/99, which is national law. Later
  entries distinguish `:national-act`, `:municipal-regulation` and
  `:municipal-plan`; the two original entries keep their seeded value
  so that this file records what was asserted rather than silently
  restating it.

  Note on Lei n.º 169/99: the framework it establishes was largely
  replaced by the Regime Jurídico das Autarquias Locais (Lei
  n.º 75/2013), which is recorded here as a separate entry. Lei
  n.º 169/99 is retained because parts of it remain in force.

  An ordinance not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url/date.")

(def catalog
  "municipality-slug -> vector of ordinance entries.

  `:ordinance/enacted-date` is the date the instrument itself bears
  (for a gazette notice, its publication date). `:ordinance/retrieved-at`
  is the day the URL was last fetched and the verbatim phrase confirmed."
  {"lisbon"
   [{:ordinance/id "lisbon.lei-169-1999-autarquias-locais"
     :ordinance/title "Lei n.º 169/99, de 18 de Setembro (Autarquias Locais - Competências e Regime Jurídico)"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :local-act
     :ordinance/number "Lei n.º 169/99"
     :ordinance/url "https://www.pgdlisboa.pt/leis/lei_mostra_articulado.php?nid=592&tabela=lei_velhas&nversao=4"
     :ordinance/url-provenance :official-pgdlisboa-pt
     :ordinance/url-verified-phrase "Lei n.º 169/99"
     :ordinance/enacted-date "1999-09-18"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:governance}}
    {:ordinance/id "lisbon.1147-portuguese-conquest"
     :ordinance/title "1147 Siege of Lisbon: after a four-month siege, Christian crusaders captured the city and Christian rule returned; the Moors capitulated on 22 October"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :local-act
     :ordinance/number "1147"
     :ordinance/url "https://en.wikipedia.org/wiki/History_of_Lisbon"
     :ordinance/url-provenance :wikipedia-corroborated
     :ordinance/url-verified-phrase "the Moors capitulated on 22 October"
     :ordinance/enacted-date "1147-10-22"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:governance}}

    ;; ── 2026-09-01 extension. Each entry below was confirmed by
    ;; fetching the document named in :ordinance/url and reading
    ;; :ordinance/url-verified-phrase out of it.

    {:ordinance/id "lisbon.lei-75-2013-rjal"
     :ordinance/title "Lei n.º 75/2013, de 12 de setembro — Regime Jurídico das Autarquias Locais (RJAL): establishes the legal regime of local authorities, the statute of inter-municipal entities, the regime for transferring competences from the State to local authorities, and the regime of municipal associationism"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :national-act
     :ordinance/number "Lei n.º 75/2013"
     :ordinance/url "https://files.diariodarepublica.pt/1s/2013/09/17600/0568805724.pdf"
     :ordinance/url-provenance :official-diario-da-republica
     :ordinance/url-verified-phrase "Estabelece o regime jurídico das autarquias locais"
     :ordinance/gazette "Diário da República, 1.ª série — N.º 176 — 12 de setembro de 2013, p. 5688"
     :ordinance/enacted-date "2013-09-12"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:governance}}

    {:ordinance/id "lisbon.aviso-11622-2012-pdm"
     :ordinance/title "Aviso n.º 11622/2012 — Aprovação da revisão do Plano Diretor Municipal de Lisboa (PDM), including the Regulamento, the Planta de Ordenamento and the Planta de Condicionantes, approved by the Assembleia Municipal de Lisboa on 24 July 2012 via Deliberação n.º 46/AML/2012 and Deliberação n.º 47/AML/2012"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :municipal-plan
     :ordinance/number "Aviso n.º 11622/2012"
     :ordinance/url "https://files.diariodarepublica.pt/gratuitos/2s/2012/08/2S168A0000S00.pdf"
     :ordinance/url-provenance :official-diario-da-republica
     :ordinance/url-verified-phrase "aprovar a Revisão do Plano Diretor Municipal de Lisboa"
     :ordinance/gazette "Diário da República, 2.ª série — N.º 168 — 30 de agosto de 2012, p. 30275"
     :ordinance/enacted-date "2012-08-30"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:land-use :urbanism}}

    {:ordinance/id "lisbon.aviso-11983-2009-rmtrauoc"
     :ordinance/title "Aviso n.º 11983/2009 — Regulamento Municipal de Taxas Relacionadas com a Actividade Urbanística e Operações Conexas (RMTRAUOC), approved by Deliberação n.º 15/AM/2009; in force from 6 August 2009"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :municipal-regulation
     :ordinance/number "Aviso n.º 11983/2009"
     :ordinance/url "https://files.diariodarepublica.pt/gratuitos/2s/2009/07/2S129A0000S00.pdf"
     :ordinance/url-provenance :official-diario-da-republica
     :ordinance/url-verified-phrase "Regulamento Municipal de Taxas Relacionadas com a Actividade Urbanística e Operações Conexas"
     :ordinance/gazette "Diário da República, 2.ª série — N.º 129 — 7 de julho de 2009, p. 26549"
     :ordinance/enacted-date "2009-07-07"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:taxation :urbanism}}

    {:ordinance/id "lisbon.aviso-13293-2012-rmtrauoc-alteracao"
     :ordinance/title "Aviso n.º 13293/2012 — alteração ao Regulamento Municipal de Taxas Relacionadas com a Atividade Urbanística e Operações Conexas, approved by the Assembleia Municipal de Lisboa on 24 July 2012 via Deliberação n.º 48/AML/2012 (Deliberação n.º 734/CM/2011)"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :municipal-regulation
     :ordinance/number "Aviso n.º 13293/2012"
     :ordinance/url "https://www.lisboa.pt/fileadmin/info_administrativa/normativas/regulamentos/urbanismo/RMTRAUOC.pdf"
     :ordinance/url-provenance :official-cml-lisboa-pt
     :ordinance/url-verified-phrase "a alteração ao Regulamento Municipal de Taxas Relacionadas com a Atividade Urbanística e Operações Conexas"
     :ordinance/gazette "Diário da República, 2.ª série — N.º 193 — 4 de outubro de 2012"
     :ordinance/enacted-date "2012-10-04"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:taxation :urbanism}}

    {:ordinance/id "lisbon.aviso-14828-2015-riep"
     :ordinance/title "Aviso n.º 14828/2015 — Regulamento de Infraestruturas em Espaço Público (RIEP), approved by the Câmara Municipal on 29 April 2015 and the Assembleia Municipal on 7 July 2015"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :municipal-regulation
     :ordinance/number "Aviso n.º 14828/2015"
     :ordinance/url "https://files.diariodarepublica.pt/gratuitos/2s/2015/12/2S247A0000S00.pdf"
     :ordinance/url-provenance :official-diario-da-republica
     :ordinance/url-verified-phrase "foi aprovado o Regulamento de Infraestruturas em Espaço Público"
     :ordinance/gazette "Diário da República, 2.ª série — N.º 247 — 18 de dezembro de 2015, p. 36752"
     :ordinance/enacted-date "2015-12-18"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:public-space :urbanism}}

    {:ordinance/id "lisbon.aviso-9897-a-2020-patrimonio-imobiliario"
     :ordinance/title "Aviso n.º 9897-A/2020 — Regulamento do Património Imobiliário do Município de Lisboa, approved by the Assembleia Municipal de Lisboa in ordinary session on 27 February 2020, on a Câmara Municipal proposal approved at its extraordinary meeting of 19 December 2019"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :municipal-regulation
     :ordinance/number "Aviso n.º 9897-A/2020"
     :ordinance/url "https://files.diariodarepublica.pt/gratuitos/2s/2020/07/2S126A0000S01.pdf"
     :ordinance/url-provenance :official-diario-da-republica
     :ordinance/url-verified-phrase "Sumário: Regulamento do Património Imobiliário do Município de Lisboa"
     :ordinance/gazette "Diário da República, 2.ª série — N.º 126, 1.º Suplemento — 1 de julho de 2020, p. 304-(2)"
     :ordinance/enacted-date "2020-07-01"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:property}}

    {:ordinance/id "lisbon.aviso-20811-b-2019-residuos"
     :ordinance/title "Aviso n.º 20811-B/2019 — Regulamento de gestão de resíduos, limpeza e higiene urbana de Lisboa, approved by the Assembleia Municipal de Lisboa on 3 December 2019 under Proposta de Câmara n.º 676/CM/2019"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :municipal-regulation
     :ordinance/number "Aviso n.º 20811-B/2019"
     :ordinance/url "https://www.lisboa.pt/fileadmin/info_administrativa/normativas/regulamentos/ambiente/Regulamento_de_Gest%C3%A3o_de_Res%C3%ADduos_Limpeza_e_Higiene_Urbana_de_Lisboa.pdf"
     :ordinance/url-provenance :official-cml-lisboa-pt
     :ordinance/url-verified-phrase "Regulamento de gestão de resíduos, limpeza e higiene urbana de Lisboa"
     :ordinance/gazette "Diário da República, 2.ª série — N.º 251 — 31 de dezembro de 2019, p. 331-(143)"
     :ordinance/enacted-date "2019-12-31"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:environment}}

    {:ordinance/id "lisbon.regulamento-geral-das-feiras-2005"
     :ordinance/title "Regulamento Geral das Feiras da Cidade de Lisboa — Deliberação n.º 28/AM/2005 (Deliberação n.º 31/CM/2005, Proposta n.º 31/2005), replacing the 1982 Regulamento da Feira da Ladra and the Regulamento das Feiras do Relógio e Galinheiras"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :municipal-regulation
     :ordinance/number "Deliberação n.º 28/AM/2005"
     :ordinance/url "https://www.lisboa.pt/fileadmin/info_administrativa/normativas/regulamentos/comercio/Regulamento_Geral_das_Feiras.pdf"
     :ordinance/url-provenance :official-cml-lisboa-pt
     :ordinance/url-verified-phrase "Deliberação n.º 28/AM/2005"
     :ordinance/gazette "Boletim Municipal da Câmara Municipal de Lisboa"
     :ordinance/enacted-date "2005-01-01"
     :ordinance/retrieved-at "2026-09-01"
     :ordinance/topic #{:commerce}}

    {:ordinance/id "lisbon.regimento-cml-2025"
     :ordinance/title "Regimento da Câmara Municipal de Lisboa — approved by Proposta n.º 596/2025 at the extraordinary meeting of the Câmara Municipal held on 19 November 2025"
     :ordinance/municipality "lisbon"
     :ordinance/country "PRT"
     :ordinance/kind :municipal-regulation
     :ordinance/number "Proposta n.º 596/2025"
     :ordinance/url "https://www.lisboa.pt/fileadmin/info_administrativa/normativas/municipio/regimento_CML_1_Suplemento_BM_1657_20_11_2025.pdf"
     :ordinance/url-provenance :official-cml-lisboa-pt
     :ordinance/url-verified-phrase "Aprovou o Regimento da Câmara Municipal de Lisboa"
     :ordinance/gazette "1.º Suplemento ao Boletim Municipal n.º 1657, 20 de novembro de 2025"
     :ordinance/enacted-date "2025-11-19"
     :ordinance/retrieved-at "2026-09-01"
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
      :note (str "cloud-itonami-municipality-prt-lisbon (ADR-2607141700): "
                 (count (get catalog "lisbon")) " Lisbon entries, each with a "
                 "verbatim phrase confirmed against the cited document "
                 "(see tools/verify_citations.cljs). "
                 "Extend `ordinance.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [muni topic]
  (filterv #(contains? (:ordinance/topic %) topic) (spec-basis muni)))

(defn citations
  "Every entry's (id, url, phrase) triple -- the input to the citation
  verifier. An entry with no phrase is reported with `nil` rather than
  omitted, so that an unverifiable entry cannot hide by being absent."
  ([] (citations "lisbon"))
  ([muni]
   (mapv (fn [o] {:id (:ordinance/id o)
                  :url (:ordinance/url o)
                  :phrase (:ordinance/url-verified-phrase o)})
         (spec-basis muni))))
