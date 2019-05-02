Gyakorlas 1
===

Keszits egy szotarat
---

* Hasznald a Word osztalyt, ami egy szot es annak forditasat tartalmazza.
* Keszits egy WordDao osztalyt, ami szavak adatbazisba tarolasaert felelos.
  * Implemnetalja az `Autoclosable` interfeszt: a konstruktor csatlakozik az adatbazishoz, a close metodus zarja a kapcsolatot
  * Legyen egy `save` metodusa, ami elment egy szot
  * Legyen egy `lookup` metodusa, ami visszaad egy listat minden bejegyzesrol, ami tartalmazza a parameterkent kapott kulcsszot.
    (akar az angol, akar a magyar valtozatban)
  * Legyen egy `list` metodusa, ami visszaad minden szot.
* Ne lehessen pontosan ugyanazt a kapcsolatot part (szot) tobbszor tarolni az adatbazisban
* Keszits egy egyszeru tesztprogramot, ami teszteli ezt


Keszits egy szotarkezelo programot
---

* Keszits egy IWordService interfeszt az alapertelmezett csomagban, ami egy Remote interfesz
  * A metodusai pontosan a kovetkezok legyenek:
  * `void save(String hun, String eng)`
  * `List<Word> lookup(String keyword)`
  * `List<Word> list()`
* Keszits egy WordServiceImpl tipust, ami implementalja az IWordService interfeszt, a WordDao segitsegevel
* Keszits egy WordServiceRunner futtathato java osztalyt, ami elindit egy registry-t a megadott porton, es exportal egy WordService-t "words" neven.
* Keszits egy WordClient futtathato java osztalyt, ami csatlakozik a megadott registry-hez, es segit a szotar hasznalataban.
  * A program varjon a felhasznalo parancsaira a standard bemeneten
  * Tamogatott parancsok: `add <hun> <eng>`, `lookup <keyword>`, `list`

Csatlakozas tobb registry-hez
---

* Modositsd a WordClient-et, hogy tobb szerverhez is tudjon kapcsolodni
* Fogadjon el tetszolegesen sok parametert, mindet a kovetkezo formatumok egyikeben:
  * `port`
  * `host:port`
* Egeszitsd ki a tamogatott parancsokat a `connect <host> <port>` paranccsal, ami csatlakozik a megadott elerhetosegu nevjegyzekhez is
* A `list` parancs az ismert szavak uniojat jelenitse meg
* A `lookup` parancs a talalatok uniojat jelenitse meg
* Az `add` parancs adja hozza a bejegyzest az osszes szolgaltatashoz
* Probaljatok ki tobb gepen is!

Lehessen tudast megosztani
---

* Implementald a `share` parancsot.
* A megosztas soran a kliens minden szolgaltatashoz hozaad minden ismeretlen szokapcsolato
* Ne generalj folosleges adatforgalmat:
  csak olyan szavakat adjon hozza a kliens, amikrol tudomasa szerint a szerver nem tud
