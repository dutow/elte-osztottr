Feladatok
==

A kliens legyen kepes visszacsatlakozni a jatekhoz
---

* Ha a kliens valamiert szetkapcsol (pl. a jatekos bezarja) a szerver ne alljon le, hanem varjon egy uj kliens csatlakozasara.
* Ha a kliens ismet csatlakozott, a jatek folytatodjon mintha mi sem tortent volna.

A szerver valaszoljon
---

* A szerver is kuldjon valaszokat: hany sertetlen hajo van meg a palyan.
* A kliens fogadja ezeket a valaszokat, es jelenitse meg a jatekosnak.

Ket jatekos jatszon
---

* A szerver varjon ket kliens csatlakozasara.
* Az elso csatlakozo kliens az elso jatekos, a masodik a masodik.
* Az egyszeruseg kedveert az ujracsatlakozassal ne foglalkozzunk - tegyuk fel, hogy ha a kliens bontotta a kapcsolatot, a jatek veget ert, mert feladta.
* A szerver hasznaljon ket terkepet (lehet ugyanaz a tartalma, csak kulon peldany), es a kliensek felvaltva lojenek egy-egy terkepre.
* A szerver a valaszt, hogy hany ep hajo van, kuldje el mindket kliensnek, es a kliensek megfeleloen fogadjak.

Bonusz (egyszeru): a jatek kezdodjon ujra, ha nincs tobb hajo
---

* A palyara a hajokat veletlengeneralva tegye le a program, ne fajlbol olvasva.
* Ha valamelyik jatekos hajoi elfogytak, a jatek jelezze nekik, hogy a jatek veget ert, hogy ki a nyertes, majd inditson uj jatekot.
* Az uj jatekot az elozo nyertes kezdi.

Bonusz (nehez): Ket jatekos ujracsatlakozhat
---

* Engedjuk meg, hogy a kliensek bontsak a kapcsolatot, majd visszacsatlakozzanak.
* Az egyertelmu azonositas erdekeben modositsuk a protokollt: a kliens eloszor azt kuldi el, hogy "jatekos <n>".
* A szerver ezt fogadja, majd:
  * ha meg nem csatlakozott N. jatekos, felveszi a klienst, es minden ugy halad mint eddig
  * ha mar van csatlakozott N. kliens, adjon hibauzenetet, bontsa a kapcsolato, es varjon ismet csatlakozasra
* Figyeljunk ra, hogy a le-fel csatlakozas ne okozzon bugokat (pl. ne lephessen egy jatekos egymas utan ketszer).
* Ez a feladat *nem* valosithato meg try-with-resources-el, a kliens kapcsolatokat manualisan kell kezelni!