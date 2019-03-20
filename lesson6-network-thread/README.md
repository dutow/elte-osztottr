Halozatos szalkezelesi feladatok
===

Adott:
---

* Egy szerver, ami hosszan futo feladatokat szimulal: a kliens elkuld neki egy bemenetet, a szerver ezutan szamol valamennyi ideig, majd valaszol az eredmennyel
  * A bemenet jelenleg ket szambol all: az elso egy egyszeru azonosito, a masodik pedig a varakozasi ido szamitasahoz parameter
* Egy kliens, ami var a felhasznalotol egy bemenetet, elkuldi a szervernek, megvarja a valaszt, kiirja a kimenetre, majd kezdi elorol
  * Az id-t nem a felhasznalotol varja, hanem 1-tol indulva folyamatosan noveli

Feladatok
----

### A kliens fusson ket szalon

* Egy szal felelos a felhasznaloi bemenetert: olvas a standard bemenetrol, majd elkuldi a szervernek az uzenetet
* Egy szal felelos a szerver valaszainak feldolgozasaert: fogadja a valaszokat a szervertol, majd megjeleniti azokat

### A szerver tudjon egyszerre tobb klienst fogadni

* A szerver tarolja egy listaban az aktiv kliens kapcsolatokat, es mindegyikhez tartozzon egy szal
* Figyeljunk oda a kapcsolatok megfelelo zarasara!
* Figyeljunk oda a klienslista megfeleloen szinkronizalt eleresere!
* A szerver a standard kimeneten megfeleloen reszletesen jelezze a mukodeset (melyik klienssel mi tortenik)

### A szerver kuldjon el minden valaszt mindenkinek

* A szerver valaszat modositsuk a "<kliens id> <feladat id> <megoldas>" formatumra. A kliens id induljon egyrol, es noveljuk egyesevel kliensek csatlakozasakor.
* Minden szervernek legyen ket szala:
  * Egy fogadja az adatokat, szamolja ki a feladatot, majd tarolja el egy listaban.
    Ha elkeszult, ertesitsen errol minden kimeneti szalat.
  * Egy kimeneti szal pedig varjon az eredmenyekre, es kuldje el a kliensnek.

### Egy feladatot ne szamoljunk ki ketszer

* Mivel ugyanarra a bemenetre mindig ugyanaz a megoldas, optimalizaljuk a szervert:
  ha egy keres mar ki van szamolva, vagy egy masik szal eppen szamitja, ne szamoljuk ki megegyszer, hanem foglalkozzunk egybol a kovetkezo feladattal (ha van).
* Ha mar korabban kiszamolta valaki, kuldjok el meg egyszer a valaszt (mindenkinek)
* Ha eppen szamolja valaki, nem kell ketszer elkuldeni a valaszt, eleg egyszer ha a masik szal vegez vele

### Bonusz: multi-kliens

* Keszits egy klienst, ami sok kliens csatlakozasat szimulalja
* A multi-kliens inditson N kliens kapcsolatot (paranccsori parameter)
* Minden kliens kapcsolat szimulalja egy normal human kliens mukodeset:
  varjon egy veletlenszeru idointervallumot, majd kuldjon egy veletlenszeruen generalt bemenetet.
* A standard bemeneten ugyanugy jelenitsen meg mindent, mint a normal kliens

### Bonusz: a szerver szamoljon hatterszalakkal

* Indulaskor a szerver inditson N+1 szalat, ahol N a processzorok (magok) szama.
  A processzorok lekerdezezhetoek:

  ```java
  int cores = Runtime.getRuntime().availableProcessors();
  ```
* Ezek a szalak varjanak egy feladatra, es ha kiszamoltak egyet, dolgozzak fel, majd ertesitsek a kliens szalakat
* A kliensek fogado szala amikor kapott egy feladatot, adja oda egy feldolgozo szalnak
* Ennek az adatmozgatasnak a megvalositasahoz hasznalhatsz standard tipusokat, mint pl. a BlockingQueue.