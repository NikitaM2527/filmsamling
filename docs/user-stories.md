# User stories – Filmsamling

Samlet oversigt over projektets user stories. Hver story står med sin oprindelige tekst og
acceptkriterier, og hver del linker til kilden.

## Oversigt

| ID   | Titel                         | Type         | Del                                     | Status  |
|------|-------------------------------|--------------|-----------------------------------------|---------|
| US1  | Opret en film                 | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US2  | Menu                          | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US3  | Vis alle film                 | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US4  | Søg efter film                | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US5  | Redigér en film               | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US6  | Slet en film                  | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US7  | Automatiske tests             | Technical    | [Del 5](#del-5-test)                    | Delvist |
| US8  | Markér en film som set        | Funktionel   | [Del 5.5](#del-55-datoer)               | Ja      |
| US9  | Film, jeg ikke har set længe  | Funktionel   | [Del 5.5](#del-55-datoer)               | Ja      |
| US10 | Robust input                  | Funktionel   | [Del 6](#del-6-robusthed)               | Delvist |
| US11 | Kun gyldige film              | Funktionel   | [Del 6](#del-6-robusthed)               | Delvist |
| US12 | Angiv datoen, jeg så filmen   | Funktionel   | [Del 6](#del-6-robusthed)               | Ja      |
| US13 | Gem samlingen                 | Funktionel   | [Del 7](#del-7-gem-og-indlæs)           | Ja      |
| US14 | Gem kun, når der er ændringer | Technical    | [Del 7](#del-7-gem-og-indlæs)           | Ja      |
| US15 | Lag i koden                   | Technical    | [Del 8](#del-8-code-review-og-packages) | Ja |
| US16 | Alfabetisk visning            | Funktionel   | [Del 9](#del-9-sortering)               | Delvist |
| US17 | Vælg, hvad der sorteres efter | Funktionel   | [Del 9](#del-9-sortering)               | Ja      |
| US18 | Sortér efter to ting          | Ekstraopgave | [Del 9](#del-9-sortering)               | Nej     |

**Status** er vurderet ud fra koden i repoet. Opdatér kolonnen, når en story bliver færdig.

- **US7:** Der er tests, men ikke af alle offentlige metoder, og én test fejler (semikolon i titel).
- **US10:** Tal og ja/nej håndteres, men `j`/`n` og `0` = fortryd ved valg fra liste mangler.
- **US11:** Tomme felter og nedre grænser tjekkes, men øvre årstal, dato i fremtiden og
  "alt eller intet" ved redigering mangler.
- **US16:** Filmene sorteres efter titel, men sammenligningen er case-sensitiv.

---

## Del 1-4: CRUD

Kilde: [del-1-4-crud.md – User stories](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/blob/main/projekter/filmsamling/del-1-4-crud.md#user-stories)

### US1 – Opret en film

> Som filmentusiast vil jeg kunne tilføje en film til min filmsamling, så jeg har en liste over alle mine film.

En film har en **titel**, en **instruktør**, et **årstal**, en **længde i minutter** og en **genre**,
og den er enten **i farver** eller **sort-hvid**.

- **Givet** at jeg har valgt "Opret en film", **når** jeg har indtastet alle oplysninger, **så** er
  filmen lagt i samlingen, og programmet bekræfter det med filmens titel.

### US2 – Menu

> Som filmentusiast vil jeg have en menu, så jeg kan vælge, hvad jeg vil gøre – og blive ved, indtil jeg selv vælger at
> stoppe.

- **Givet** at programmet er startet, **så** vises en menu med mindst "Opret en film" og "Afslut".
- **Givet** at jeg har udført et valg, **når** det er færdigt, **så** vises menuen igen.
- **Givet** at jeg skriver noget, der ikke er et menuvalg, **så** får jeg det at vide, og menuen
  vises igen – programmet går ikke ned.
- **Givet** at jeg vælger "Afslut", **så** stopper programmet med en afskedshilsen.

### US3 – Vis alle film

> Som filmentusiast vil jeg kunne se alle film i samlingen, så jeg får et overblik.

- **Givet** at samlingen indeholder film, **når** jeg vælger "Vis alle film", **så** vises alle
  oplysninger om hver film.
- Der må **ikke** stå computer-ord som `null`, `true` eller `false` i udskriften. En film er
  "farver" eller "sort-hvid" – ikke `true`.
- **Givet** at samlingen er tom, **så** får jeg en besked om det i stedet for ingenting.

### US4 – Søg efter film

> Som filmentusiast vil jeg kunne søge efter film på titel, så jeg hurtigt kan finde en bestemt film.

- Jeg kan søge på **en del af titlen**: søgningen `god` finder både *The Godfather* og *Godzilla*.
- Søgningen er **ligeglad med store og små bogstaver**: `GOD`, `god` og `God` giver det samme.
- **Givet** at flere film matcher, **så** vises **alle** de film, der matcher.
- **Givet** at ingen film matcher, **så** får jeg en besked om det.

### US5 – Redigér en film

> Som filmentusiast vil jeg kunne rette oplysningerne om en film, så samlingen altid passer med det, jeg ved.

- Jeg finder filmen ved at søge (som i US4). Matcher flere film, vælger jeg den rigtige ud fra et
  nummer.
- Jeg kan rette **alle** oplysninger, og jeg kan beholde en oplysning uden at skrive den igen.
- **Efter** redigeringen vises **alle** filmens oplysninger – også dem, jeg ikke har ændret.

### US6 – Slet en film

> Som filmentusiast vil jeg kunne slette en film, så samlingen kun indeholder film, jeg stadig vil holde styr på.

- Jeg finder filmen på samme måde som i US5.
- Programmet spørger, om jeg er sikker, før filmen slettes.
- Bagefter er filmen væk fra samlingen.

---

## Del 5: Test

Kilde: [del-5-test.md – User story](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/blob/main/projekter/filmsamling/del-5-test.md#user-story)

### US7 – Automatiske tests

*(technical story – brugeren ser den ikke, men udviklerne har brug for den)*

> Som udvikler vil jeg have automatiske tests af programmets logik, så vi hurtigt kan se, om en ændring har ødelagt
> noget.

- Der er unit tests af `Movie` og af **alle** de offentlige metoder i `MovieCollection`.
- Hver metode er testet i det normale tilfælde **og** i de tilfælde, hvor der ikke er noget at
  finde, rette eller slette.
- Alle tests er **grønne**.

`UserInterface` testes ikke med unit tests – den læser fra tastaturet og skriver på skærmen.

---

## Del 5.5: Datoer

Kilde: [del-5-datoer.md – User stories](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/blob/main/projekter/filmsamling/del-5-datoer.md#user-stories)

### US8 – Markér en film som set

> Som filmentusiast vil jeg kunne markere, at jeg har set en film, så jeg kan huske, hvornår jeg sidst så den.

- **Givet** at jeg vælger "Markér en film som set" og vælger en film (som ved redigering), **så**
  gemmes dagens dato som den dato, jeg sidst så filmen.
- Når en film vises, står der, hvornår den sidst blev set, og hvor mange dage siden det er – fx
  `Sidst set: 26-10-2026 (for 0 dage siden)`.
- En film, jeg aldrig har set, viser `Sidst set: aldrig` – ikke `null`.

### US9 – Film, jeg ikke har set længe

> Som filmentusiast vil jeg kunne finde de film, jeg ikke har set længe, så jeg har noget at vælge imellem, når jeg vil
> se en film i aften.

- Jeg angiver et antal dage, fx 365.
- **Så** vises alle film, jeg **aldrig** har set, og alle film, jeg sidst så for **mere end** det
  antal dage siden.
- En film, jeg så for præcis 365 dage siden, er **ikke** med, når jeg skriver 365.

---

## Del 6: Robusthed

Kilder:
[del-6-exceptions.md – User story](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/blob/main/projekter/filmsamling/del-6-exceptions.md#user-story)
og
[del-6-exceptions.md – User stories](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/blob/main/projekter/filmsamling/del-6-exceptions.md#user-stories)

### US10 – Robust input

> Som filmentusiast vil jeg have, at programmet ikke går ned, når jeg kommer til at skrive forkert, så jeg ikke mister
> mit arbejde.

- **Givet** at programmet forventer et helt tal (årstal, længde, nummer), **når** jeg skriver noget
  andet, **så** får jeg at vide, at det ikke er et tal, og bliver spurgt igen.
- **Givet** at programmet spørger ja/nej, **når** jeg skriver noget andet end `ja`, `j`, `nej` eller
  `n`, **så** bliver jeg spurgt igen.
- **Givet** at jeg skal vælge en film fra en nummereret liste, **når** jeg skriver et nummer, der
  ikke er på listen, **så** bliver jeg spurgt igen. Skriver jeg `0`, fortryder jeg, og intet sker.
- **Givet** at jeg vil rette, slette eller markere en film, som ikke findes, **så** får jeg det at
  vide, og programmet går ikke ned.

### US11 – Kun gyldige film

> Som filmentusiast vil jeg have, at programmet afviser oplysninger, der ikke kan passe, så min samling ikke fyldes med
> fejl.

- Titel, instruktør og genre må ikke være tomme.
- Årstallet skal være mellem **1888** (den ældste bevarede film,
  [Roundhay Garden Scene](https://en.wikipedia.org/wiki/Roundhay_Garden_Scene)) og **næste år**.
- Længden skal være mindst 1 minut.
- **Givet** at en oplysning er ugyldig, **når** jeg opretter eller redigerer en film, **så** får jeg
  at vide **hvad** der er galt, og filmen bliver **ikke** oprettet eller ændret – heller ikke
  delvist.

### US12 – Angiv datoen, jeg så filmen

> Som filmentusiast vil jeg kunne skrive, hvilken dag jeg så en film, så jeg også kan registrere film, jeg så i sidste
> uge.

- Menuvalg 6 hedder nu **Markér en film som set**. Programmet spørger om datoen på formen
  `dd-mm-åååå`. Trykker jeg bare Enter, bruges dagens dato.
- **Givet** at jeg skriver noget, der ikke er en dato, **så** bliver jeg spurgt igen.
- **Givet** at datoen ligger i fremtiden, **så** får jeg det at vide, og filmen markeres ikke.

---

## Del 7: Gem og indlæs

Kilde: [del-7-filer.md – User stories](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/blob/main/projekter/filmsamling/del-7-filer.md#user-stories)

### US13 – Gem samlingen

> Som filmentusiast vil jeg have, at min filmsamling bliver gemt, så jeg ikke skal taste den ind igen, hver gang jeg
> starter programmet.

- **Givet** at jeg afslutter programmet med menuvalg 0, **så** gemmes samlingen i filen `movies.csv`.
- **Givet** at programmet startes, og `movies.csv` findes, **så** indlæses filmene, og jeg får at
  vide, hvor mange der blev indlæst.
- **Alle** oplysninger overlever – også æ, ø og å i titlerne, og datoen for, hvornår jeg sidst så
  filmen.
- **Givet** at filen ikke findes (første gang programmet kører), **så** starter jeg med en tom
  samling – programmet går ikke ned.
- **Givet** at en linje i filen er ødelagt, **så** springes den over, jeg får en advarsel, og
  resten af filmene indlæses.

### US14 – Gem kun, når der er ændringer

*(technical story)*

> Som udvikler vil jeg kun skrive filen, når data er ændret, så programmet ikke laver unødigt arbejde.

- **Givet** at jeg hverken har oprettet, rettet, slettet eller markeret en film som set, **når** jeg
  afslutter, **så** bliver filen **ikke** skrevet, og jeg får at vide, at der ikke var noget at
  gemme.

---

## Del 8: Code review og packages

Kilde: [del-8-refaktorering.md – User story](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/blob/main/projekter/filmsamling/del-8-refaktorering.md#user-story)

### US15 – Lag i koden

*(technical story)*

> Som udvikler vil jeg have koden delt i packages efter lag, så det er tydeligt, hvilke klasser der hører til
> brugerfladen, domænet og data – og så en ny udvikler hurtigt kan finde rundt.

- Klasserne ligger i tre packages: `ui`, `domainmodel` og `datasource`.
- Programmet opfører sig **nøjagtig** som før.
- Alle tests ligger i den package, der svarer til den klasse, de tester, og de er grønne.

---

## Del 9: Sortering

Kilde: [del-9-sortering.md – User stories](https://github.com/EK-DAT-GBG-1SEM-E26AB/DAT-GBG-DA-E26AB/blob/main/projekter/filmsamling/del-9-sortering.md#user-stories)

### US16 – Alfabetisk visning

> Som filmentusiast vil jeg se mine film i alfabetisk orden, så jeg hurtigt kan finde en bestemt film i listen.

- **Når** jeg vælger "Vis alle film", **så** vises filmene sorteret efter titel – uanset store og
  små bogstaver.

### US17 – Vælg, hvad der sorteres efter

> Som filmentusiast vil jeg selv kunne vælge, hvad filmene sorteres efter, så jeg kan se samlingen på den måde, der
> passer mig lige nu.

- Et nyt menuvalg, **Vis film sorteret efter ...**, lader mig vælge mellem titel, instruktør,
  årstal, længde og genre.
- Tekst sorteres alfabetisk, tal fra mindste til største.

### US18 – Sortér efter to ting

*(Ekstraopgave – ikke et krav)*

> Som filmentusiast vil jeg kunne sortere efter én ting og derefter en anden, fx instruktør og derefter årstal, så jeg
> kan se hver instruktørs film i rækkefølge.

- Efter det første valg kan jeg vælge et andet – eller "ingen".
- Film, der er **ens** på det første, sorteres efter det andet. Film, der er forskellige på det
  første, sorteres **kun** efter det første.
