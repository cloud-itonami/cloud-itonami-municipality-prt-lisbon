# cloud-itonami-municipality-prt-lisbon

Municipal-ordinance compliance catalog for **Lisbon** — the
FORTY-SIXTH municipality-level entry, alongside 45 prior entries
including
[`cloud-itonami-municipality-cze-prague`](https://github.com/cloud-itonami/cloud-itonami-municipality-cze-prague),
[`cloud-itonami-municipality-nzl-wellington`](https://github.com/cloud-itonami/cloud-itonami-municipality-nzl-wellington),
[`cloud-itonami-municipality-irl-dublin`](https://github.com/cloud-itonami/cloud-itonami-municipality-irl-dublin),
and
[`cloud-itonami-municipality-che-bern`](https://github.com/cloud-itonami/cloud-itonami-municipality-che-bern).
Part of the [`cloud-itonami`](https://github.com/cloud-itonami)
compliance-fact family (ADR-2607141700,
`cloud-itonami-compliance-fact-federation`, in `com-junkawasaki/root`).

Portugal's first entry across any of the 3 axes on the municipality
side — closing one of the 4 structural country-without-municipality
gaps identified at tick 141 (GTM/HND/PAN/PRT).

## Citations are content-verified, not status-verified

Every entry carries `:ordinance/url-verified-phrase`, a string quoted
verbatim from the document at `:ordinance/url`.
[`tools/verify_citations.cljk`](tools/verify_citations.cljk) re-fetches
each URL and fails unless that phrase is present in the extracted text:

```
kbb --backend sci tools/verify_citations.cljk          # 0 = all verified, 1 = a citation is wrong,
                                         # 2 = REFUSED (could not answer)
kbb --backend sci tools/verify_citations.cljk --only lisbon.aviso-14828-2015-riep
```

It needs `pdftotext` (poppler) on `PATH`, because the gazette citations
are PDFs; without it the tool exits **2** rather than reporting a pass
it did not earn.

**Why not just check for HTTP 200.** Measured 2026-09-01:
`https://diariodarepublica.pt/dr/detalhe/aviso/99999-2099-000000` — an
aviso id that does not exist — returns HTTP 200 with the same
2,346-byte JavaScript shell as a real one, and so does a fabricated
path under `files.diariodarepublica.pt`. A status-code gate against
those hosts is a check that cannot fail, which makes a green run
indistinguishable from no run at all. So this repo cites the
**published gazette PDFs** and the municipality's own PDFs, and reads
them.

The verifier has been shown to go red for each reason it names: a URL
swapped to a different real gazette issue, a mistyped phrase, a missing
phrase, a document that has moved (404), a path the host does not have
(the 200-with-shell case), an empty catalog, and `--only` naming an
ordinance that does not exist.

## Sources

| # | Instrument | Source |
|---|---|---|
| 1 | Lei n.º 169/99, de 18 de Setembro (Autarquias Locais) | pgdlisboa.pt |
| 2 | 1147 Siege of Lisbon | en.wikipedia.org |
| 3 | Lei n.º 75/2013 — Regime Jurídico das Autarquias Locais | *DR*, 1.ª série, n.º 176, 12 set. 2013 |
| 4 | Aviso n.º 11622/2012 — revisão do Plano Diretor Municipal | *DR*, 2.ª série, n.º 168, 30 ago. 2012 |
| 5 | Aviso n.º 11983/2009 — RMTRAUOC | *DR*, 2.ª série, n.º 129, 7 jul. 2009 |
| 6 | Aviso n.º 13293/2012 — alteração ao RMTRAUOC | lisboa.pt (*DR*, 2.ª série, n.º 193) |
| 7 | Aviso n.º 14828/2015 — Regulamento de Infraestruturas em Espaço Público | *DR*, 2.ª série, n.º 247, 18 dez. 2015 |
| 8 | Aviso n.º 9897-A/2020 — Regulamento do Património Imobiliário | *DR*, 2.ª série, n.º 126, 1.º Supl., 1 jul. 2020 |
| 9 | Aviso n.º 20811-B/2019 — gestão de resíduos, limpeza e higiene urbana | lisboa.pt (*DR*, 2.ª série, n.º 251) |
| 10 | Regulamento Geral das Feiras — Deliberação n.º 28/AM/2005 | lisboa.pt (Boletim Municipal) |
| 11 | Regimento da Câmara Municipal de Lisboa — Proposta n.º 596/2025 | lisboa.pt (1.º Supl. ao BM n.º 1657) |

Lei n.º 169/99 (entry 1) is retained because parts of it remain in
force, but the framework it established was largely replaced by the
RJAL (entry 3). Entries 1 and 2 were seeded in Wave 0 with
`:ordinance/kind :local-act`, including entry 1 which is national law;
later entries distinguish `:national-act`, `:municipal-regulation` and
`:municipal-plan`. The seeded values are left as they were recorded
rather than silently restated.

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on the City of
Lisbon's behalf.

Coverage is reported honestly (see `ordinance.facts/coverage`): a
municipality not in `catalog` has **no spec-basis**, full stop — never
fabricate one.

## Data

- `src/ordinance/facts.cljk` — the catalog, source of truth.
- `schema/ordinance.edn` — DataScript schema.
- `data/datascript-tx.edn` — **generated** DataScript tx-data; regenerate
  with `kbb -M -i tools/gen_tx.cljk`. `tx-data-is-derived-from-the-catalog`
  in the test suite fails if the two drift apart.
- `tools/verify_citations.cljk` — the citation verifier described above.

Query the tx-data alongside other `cloud-itonami`/`etzhayyim`
compliance-fact sources via `com-junkawasaki/root`'s
`scripts/compliance-fact-query.cljs`.

## Tests

```
kbb -M:test     # unit tests, offline
kbb -M:lint     # clj-kondo
kbb --backend sci tools/verify_citations.cljk   # network; re-reads every cited document
```

The unit tests are offline on purpose: they check the catalog's
internal invariants (unique ids, distinct URLs, every entry carrying a
phrase long enough to identify a document, tx-data derived from the
catalog, schema declaring every attribute used). Whether each cited
document still says what it said is a separate question that only the
verifier can answer, because it needs the network.

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention). Law text itself
remains Portugal's/Lisbon's; this repo stores only citation metadata
(id/title/url/dates/verbatim phrase), not full text.
