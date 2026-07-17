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

## Sourcing note

Both entries directly cite `pgdlisboa.pt` (the Portuguese Public
Prosecutor's Office's own official legal database, for the current
national municipal-competencies law, Lei n.º 169/99) and
`en.wikipedia.org` (for the 1147 Siege of Lisbon, directly quoted:
"In 1147, after a four-month siege, Christian crusaders under the
command of Afonso I captured the city and Christian rule returned"
and "the Moors capitulated on 22 October").

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on the City of
Lisbon's behalf.

Coverage is reported honestly (see `ordinance.facts/coverage`): a
municipality not in `catalog` has **no spec-basis**, full stop — never
fabricate one.

## Data

- `src/ordinance/facts.cljc` — the catalog, source of truth.
- `schema/ordinance.edn` — DataScript schema.
- `data/datascript-tx.edn` — derived DataScript tx-data (query this
  alongside other `cloud-itonami`/`etzhayyim` compliance-fact sources via
  `com-junkawasaki/root`'s `scripts/compliance-fact-query.cljs`).

Both entries directly confirmed: **Lei n.º 169/99, de 18 de
Setembro** (Autarquias Locais - Competências e Regime Jurídico,
18 September 1999) and the **1147 Siege of Lisbon** (Portuguese/
Christian conquest, Moors capitulated 22 October).

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention). Law text itself
remains Portugal's/Lisbon's; this repo stores only citation metadata
(id/title/url/dates), not full text.
