Adott
===

Egy torpedo jatek, a torpedo-2 kicsit tovabb fejlesztett valtozata.

Protokoll
---

* A szerverhez kapcsolodik egy kliens
* A szerverhez kapcsolodik meg egy kliens
* Az elso kliens elkuldi a nevet (egy sor)
* A masodik kliens elkuldi a nevet (egy sor)
* Az elso kliens elkuldi a hajoit.
  * A hajok veget egy sor jelzi, ami csak harom kotojelet tartalmaz.
  * A hajok formatuma ugyanaz, mint korabban volt.
* A masodik kliens is elkuldi a hajoit.
* A szerver kisorsolja, ki lesz az elso jatekos.
  * Az elso jatekosnak elkuldi az `play <ID> <N> <M>` uzenetet, ahol ID a jatek sorszama, N az ellenfel hatralevo hajoinak szama, M pedig az aktualis jatekos hajoinaks szama.
  * A masodik jatekosnak elkuldi a `wait <ID> <N> <M>` uzenetet.
* A szerver var az elso jatekos lovesere: `shoot <X> <Y>`
  * Ha a jatekos nem megfelelo formatumu uzenetet kuld, egy korbol kimarad
  * Mindket jatekosnak elkuldi a `hit <ID> <0|1> <X> <Y> <N> <M>` uzenetet (0 - az elso jatekosra lottunk ; 1 - a masodik jatekosra lottunk)
* A szerver var a masik jatekos lovesere
  * ...
* Amikor az egyik jatekos hajoi elfogytak, a szerver mindket kliensnek elkuldi a `results game <ID> winner "<name>" loser "<name>"` uzenetet, majd bontja a kapcsolatot.
* A szerver mindket kliensnek elkuldi az `exit` uzenetet.

Szerver
---
A szerver lejatszik egy jatekot, majd mindenkit kileptet.

Kliens
---

A kliens harom parancssori parametert var:

* A szerver hosztnevet
* A jatekos nevet
* A hajokat tartalmazo fajl nevet

A kliens ezutan:
* amikor loves infot kap a szervertol, eltarolja egy terkepen, es megjeleniti
* amikor a jatekos jon, var egy sor bemenetre es elkuldi

Feladatok
==

* A cel: csoportosan oldjunk meg minnel tobb reszfeladatot.
* A feladatok tetszoleges sorrendben megoldhatoak, az elofelteteleket figyelembe veve
* Toltsetek el 5 percet a feladatlista atnezesevel - legalabb a feladatok cimeit
* Minden feladat megoldasat egyvalaki vezeti, utana valtunk.

A: A kliens mukodjon tobb szalon
---
A kliensnel kulonoljon el a fogado es a kuldo szal.


B: A szerver legyen kepes egyszerre/egymas utan tobb jatek bonyolitasara
---

* A szerver var jatekosok fogadasara
* Ha nincs szabad jatekos, a most csatlakozott lesz szabad jatekos lesz (Lista)
* Ha van szabad jatekos, elindul egy jatek az elso szabad jatekos es a most csatlakozott kozott


C: A jatekosok kapjanak uj jatekot.
---

Szukseges: B

* Amikor egy jatek veget ert, a szerver tarolja el a jatekosokat mint szabad jatekos
* A hajok es a nev fix: a protokoll a "wait/play" uzenettel kezdi a kovetkezo jatekot


D: A szabad jatekos listat periodikusan nezzuk
---

Szukseges: B, C

Modsitsuk a klienseket fogado ciklust:

* Ha egy jatekos csatlakozik, keruljon a szabad jatekosok listajaba

Legyen egy masik szal, ami jatekokat indit:

* Ha a szabad jatekos listahoz hozzaadtak valakit, nezze at a listat
* Ha 5 masodperc eltelt anelkul, hogy tortent volna valami, nezze at a listat

Lista atnezese:

* Veletlenszeruen atrendezi a listat
* Ha a listaban ket egymas utani jatekos meg nem jatszott egymassal, vagy a legutobbi jatekuk ota eltelt 30 masodperc, jatek indul koztuk.

E: Idolimit lepesekre
---

Szukseges: A

* Ha egy kliens nem kuld lepest 15 masodpercen belul, kimarad a korbol, es elsullyed egy hajoja.
* Hogy ehhez ne kelljen protokollt modositani, a szerver ilyenkor a `hit <ID> <jatekos> <X> <Y> <N> <M> sink` uzenetet kuldi mindket jatekosnak, a megfelelo koordinatakkal.
* A hibas lepest (nem `shoot <X> <Y>` formatumu, kivulesik a palyan) tekintsuk az idolimit lejartanak.

F: Mindenki ertesuljon az eredmenyekrol.
---

Szukseges: A, B

* A `results` uzenetet mindenkinek kuldjuk el, ne csak a resztvevoknek.


G: Ne engedjuk a jatekosokat elore lepni
---

Szukseges: A

* Ha egy jatekos azelott elkuldi a kovetkezo lepeset, hogy az o kore jonne, ezt hagyjuk figyelmen kivul.
* Kuldjunk neki egy `ignored: <eredeti uzenet>` uzenetet is.


H: A jatekos kapjon lehetoseget a hibajanak a javitasara
---

Szukseges: A, E

* Ha egy jatekos rossz uzenetet kuldott (nem "<shoot> X Y" formatumu, vagy a koordinatak nem esnek a palyara) legyen lehetosege tovabb probalkozni, amig az idolimiten belul van.
* Hibas uzenetekre a szerver kuldjon egy `ignored: <eredeti uzenet>` valaszt is.

I: Legyen nezo is
---

* Egy masik porton kapcsolodhasson egy nezo is a szerverhez
* Amikor tortenik egy lepes (barmelyik jatekban), a szerver kuldje el a nezonek is. (a `hit` uzenetet)
* Illetve azt is, ha a jatek veget er (a `results` uzenetet)
* Nezonek hasznaljunk telnetet/putty-t

J: Lehessen tobb nezo is
---

Szukseges: I

* A szerver tudjon egyszerre/egymas utan tetszoleges szamu nezot fogadni

K: Csak eredmenyeket-nezo
---

Szukseges: J

* Ha egy nezo elkuldi a szervernek, hogy "only results", onnantol kezdve csak az eredmenyekrol kapjon ertesitest.

L: Toplista
---

Szukseges: J, K

* Keszits egy klienst, ami megjeleniti a jatekosok toplistajat (nev+pontszam).
* A toplista hasznaljon ket szam parametert, A-t es B-t, ami a szorzo a gyozelemhez es a veszteseghez.
  * Pl. `1 0` a gyozelmek szama szerint rendezi oket
  * Pl. `1 -2` egy pontot ad a gyozelemert, es kettot levon ha valaki veszit