# Filmsamling

Et CRUD-program til en filmsamling - 1. semester, datamatiker, EK. 

Filmsamlingen er en digital oversigt over din personlige filmsamling.
Programmet gør det muligt at oprette, vise, søge efter redigere og slette film samt registrere, hvornår de sidst er set. 

Projektet udvikles som et terminalprogram i Java med fokus på objektorienteret programmering, unit testing, fejlhåndtering, fillagring og sortering.

## Gruppen
| Fornavn | GitHub                                        |
|---------|-----------------------------------------------|
| Nikita  | [NikitaM2527](https://github.com/NikitaM2527) |
| Mathias | [mwnDK1402](https://github.com/mwnDK1402)     |
| Nikolas | [NikolasCCT](https://github.com/NikolasCCT)   |

## Funktioner
Programmet indeholder følgende funktioner:
1. **Opret film**: Tilføj en film til samlingen med oplysninger om titel, instruktør, udgivelsesår, farve, længde, genre og dato for sidst set. 
2. **Vis film**: Se en oversigt over filmene i samlingen. 
3. **Søg efter film**: Find film ud fra titel. 
4. **Redigér film**: Opdatér oplysningerne på en eksisterende film.
5. **Slet film**: Fjern en film fra samlingen.
6. **Markér som set**: Registrér, hvornår en film sidst er blevet set.
7. **Vis sete-/ikke-sete oplysninger**: Se, hvor længe det er siden, en film sidst blev set, eller om den aldrig er blevet set. 
8. **Sortér film**: Sortér filmsamlingen efter titel, instruktør, udgivelsesår, længde eller genre.

## Fillagring
Filmsamlingen gemmes i en CSV-fil (`movies.csv`), så filmene kan indlæses igen, når programmet startes på ny. 

Programmet benytter `FileHandler` til at håndtere indlæsning og lagring af film. 
Ugyldige linjer i CSV-filen springes over, så de ikke forhindrer indlæsning af de øvrige film.

## User stories
### Implementerede user stories

| ID   | Titel                         | Type         | Del                                     | Status  |
|------|-------------------------------|--------------|-----------------------------------------|---------|
| US1  | Opret en film                 | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US2  | Menu                          | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US3  | Vis alle film                 | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US4  | Søg efter film                | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US5  | Redigér en film               | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US6  | Slet en film                  | Funktionel   | [Del 1-4](#del-1-4-crud)                | Ja      |
| US7  | Automatiske tests             | Technical    | [Del 5](#del-5-test)                    | Ja      |
| US8  | Markér en film som set        | Funktionel   | [Del 5.5](#del-55-datoer)               | Ja      |
| US9  | Film, jeg ikke har set længe  | Funktionel   | [Del 5.5](#del-55-datoer)               | Ja      |
| US10 | Robust input                  | Funktionel   | [Del 6](#del-6-robusthed)               | Delvist |
| US11 | Kun gyldige film              | Funktionel   | [Del 6](#del-6-robusthed)               | Delvist |
| US12 | Angiv datoen, jeg så filmen   | Funktionel   | [Del 6](#del-6-robusthed)               | Ja      |
| US13 | Gem samlingen                 | Funktionel   | [Del 7](#del-7-gem-og-indlæs)           | Ja      |
| US14 | Gem kun, når der er ændringer | Technical    | [Del 7](#del-7-gem-og-indlæs)           | Ja      |
| US15 | Lag i koden                   | Technical    | [Del 8](#del-8-code-review-og-packages) | Ja      |
| US16 | Alfabetisk visning            | Funktionel   | [Del 9](#del-9-sortering)               | Delvist |
| US17 | Vælg, hvad der sorteres efter | Funktionel   | [Del 9](#del-9-sortering)               | Ja      |

### User stories, vi ikke nåede

- **US10:** Tal og ja/nej håndteres, men `j`/`n` og `0` = fortryd ved valg fra liste mangler.
- **US11:** Tomme felter og nedre grænser tjekkes, men øvre årstal, dato i fremtiden og
  "alt eller intet" ved redigering mangler.
- **US16:** Filmene sorteres efter titel, men sammenligningen er case-sensitiv.

## Sådan køres programmet
Projektet er udviklet i Java og kræver **JDK 21 (LTS)**.
Programmet startes fra klassens `Main.java`:
`dk.ek.stud.group7.filmsamling.Main`

I IntelliJ:
1. Åbn projektet i IntelliJ. 
2. Kontrollér, at projektets JDK er sat til JDK 21. 
3. Åbn `Main.java`.
4. Kør programmets `main()`-metode ved at trykke på den grønne Run-knap. 

Programmet afvikles i terminalen. 

## Faglige områder
Projektet er udviklet i Java og anvender blandt andet: 
- Objektorienteret programmering
- CRUD-operationer
- Builder Pattern
- Exceptions og fejlhåndtering
- Unit testing
- `ArrayList` og Collections
- `Comparable` og `Comparator`
- CSV til lagring af data
- Filhåndtering med `Scanner` og `PrintStream`


### Klasse diagram

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/class-diagram-dark.svg">
  <img alt="Class diagram" src="docs/class-diagram-light.svg">
</picture>
