Adatbaziskezeles
====

HSQLDB jar, ha nem a gradle projektet hasznalod:
https://mvnrepository.com/artifact/org.hsqldb/hsqldb/2.4.1

Adj jelszavakat a peldahoz
---

* A pelda alkalmazas taroljon jelszavakat es loginnevet is az emberekhez, ugyanabban a tablaban.
* Az egyszeruseg kedveert a jelszavakat tarold siman szovegkent. (Rendes alkalmazasban ezt ne tedd)
* Listazaskor a jelszavakat ne jelentisd meg, de a felhasznaloneveket igen.

Adj hozza kereso funkciot
---

* Ha a programot parancssori egy parameterrel hivjak meg, csak azokat a felhasznalokat listazd ki, akiknek a neveben benne van a kifejezes.
* A szurest valositsd meg az SQL lekerdezes reszekent, ehhez hasznald a `LIKE` sql kulcssszot
* Mivel a lekerdezes felhasznalo altal megadott bemenetet fog tartalmazni, hasznalj PreparedStatement-et

Hozd letre az uzenetek tablat is
---

* Az emberek mellett legyen egy uzeneteket tartalmazo tabla is: kitol (id), kinek (id), uzenet tartalma (TEXT)
* Keszitsd el a "MessengeSender" programot is: parancssori parameterkent varja a kuldo nevet, jelszavat, valamint a cimzettet.
* Ha a parameterek kozul barmelyik hibas, jelenitsen meg hibauzenetet, amiben errol tajekoztat.
* Az ellenorzeseket SQL utasitasokkal vegezd
* Mivel a lekerdezes felhasznalo altal megadott bemenetet fog tartalmazni, hasznalj PreparedStatement-et
* A program olvassa be a sorokat addig, amig a sor siman csak a "." karaktert tartalmazza - ez jelzi az uzenet veget.
* Ha az uzenet veget ert, tarolja el azt, majd lepjen ki.

A listazo program jelenitse meg, kinek hany uzenete van
---

* Ehhez a mar meglevo lekerdezest modositsad, egy `JOIN` hozzaadasaval