Tavoli eljarashivas
===

Probaljatok ki a peldat!
---

* Inditsatok el kezzel a registry-t egy porton, es ugy
* Bizzatok a registry kezeleset a szerverre, es ugy
* Nezzetek meg a lehetseges hibauzeneteket, pl.:
  * rossz port
  * rossz classpath a registrynek
  * hianyzo interface/exception specifikacio
  * kulonbozo kliens/szerver verzio

Keszits egy szamologepet!
---

* Keszitsd el a Calculator interface-t.
  * Rendelkezzen add, sub, mul, div muveletekkel, mind egy parametert varjon.
  * Rendelkezzen egy getValue muvelettel, ami vissszaadja az aktualis szamot.
  * Rendelkezzen egy reset muvelttel, ami alaphelyzetbe allitja.
* Keszitsd el a CalculatorImpl-t, ami implemnetalja ezeket a muveleteket, egy privat adattagban tarolva a szamot, aminek kezdeti erteke nulla.
* Keszitsd el a CalculatorClient-et, ami muveleteket ker be a felhasznalotol soronkent (pl. add 42 \n div 8).
  A muveleteket ertelmezi, vegrehajtja, majd lekeri az eredmenyt a szervertol es megjelenit azt.
  Ezutan varja a kovetkezo muveletet.

Keszits tobb szamitasra kepes szamologepet!
---

* Keszitsd el a CalculatorFactory interface-t.
  * Rendelkezzen egy getCalculator metodussal, ami egy nevet var, es egy Calculator-t ad vissza.
* Keszitsd el a CalculatorFactoryImpl osztalyt, ami implemnetalja a metodust, es egy CalculatorImpl-t ad vissza.
* A kliens program eloszor egy CalculatorFactory-hoz kapcsolodjon, majd kerje le a "default" kalkulatort.
* Implementald a "use <name>" parancsot, ami valt az adott nevu kalkulatorra a kliensen.

*A kalkulator vegye eszre, ha mar senki sem hasznalja egy ideje
---

* A Calculator objektum vegye eszre, ha mar nem hivatkozik ra kliens, es semmisitse meg onmagat.
* Segitseg:
  * `java.rmi.server.Unreferenced`
  * `UnicastRemoteObject.unexportObject`

*A kliens kapjon ertesitest, ha az ertek megvaltozott
---

* Modositsd a programot, hogy ha egyszerre tobb kliens hasznalja ugyanazt a szamologepet, ertesuljenek, ha az ertek valtozik
* Implementald periodikus lekerdezesekkel (pl. 1 mp)
* Implementalhato tenyleges, azonnali ertesitessel?