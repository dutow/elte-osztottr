Szerializacios feladatok
===

1: Osszegzes
---

* Irj ki egy fajlba 100 objektumot - veletlenszeruen, egyenlo esellyel Integer vagy Double tipusuakat, veletlenszeru ertekkel
* Olvasd be az objektumokat, es ird ki az osszeguket

2: Binaris fa
---

* Keszits egy tipust, ami egy egesz erteku binaris fat reprezental, a leheto legegyszerubb modon:
  * Harom adattag (ertek, bal, jobb)
  * Getterek/setterek
  * toString
  * Harom konstruktor (csak ertek ; ertek es bal gyerek ; ertek es mindket gyerek)
    (ezekkel a konstuktorokkal a fat jol strukturaltan letre tudod majd hozni a forraskodban)
* Keszits egy foprogramot, ami letrehoz egy kicsi binaris fat, es kiirja egy fajlba, majd beolvassa a fajlbol

3: Binaris fa halozaton
---

* Az elozo programot alakitsd at egy kliensse:
   * Keszit egy fat
   * Megjeleniti a fat
   * Csatlakozik a szerverhez
   * Elkuldi a szervernek a binaris fat objektumkent
   * Var egy objektum valaszra
   * Megjeleniti a valaszul kapott objektumot
* Keszits egy szervert, ami fogad egy fat, vegez vele valami muveletet (pl. tukrozi), majd visszakuldi azt