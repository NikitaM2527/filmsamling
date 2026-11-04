# FURPS – vores filmsamling

|   | Krav                                                                                          | Opfyldt? | Hvor                                              |
|---|-----------------------------------------------------------------------------------------------|----------|---------------------------------------------------|
| F | Man kan oprette film                                                                          | Ja       | US1-2, `MovieCollection.addMovie`                 |
| F | Man kan vise alle film                                                                        | Ja       | US3, `MovieCollection.getMovies`                  |
| F | Man kan søge film                                                                             | Ja       | US4, `MovieCollection.searchMovies`               |
| F | Man kan søge på en del af en titel                                                            | Ja       | US4, `MovieCollection.searchMovies`               |
| F | Man kan rette film                                                                            | Ja       | US5, `MovieCollection.editMovie`                  |
| F | Man kan slette film                                                                           | Ja       | US6, `MovieCollection.deleteMovie`                |
| F | Man kan markere en film som set                                                               | Ja       | US8, `Movie.markAsWatched`                        |
| F | Man kan vise alle film, der ikke er set længe                                                 | Ja       | US9, `MovieCollection.getMoviesNotWatchedSince`   |
| U | Man kan trykke Enter for at beholde en værdi, når man redigerer                               | Ja       | US5, `UserInterface.prompt*`                      |
| U | Når man skriver en genre, foreslår den en genre baseret på eksisterende genre i filmsamlingen | Nej      |                                                   |
| U | Når du skriver ugyldig data, giver den en passende besked                                     | Ja       | US10, `UserInterface.prompt*`                     |
| U | Vi kan søge en film frem baseret på dele af attributter (instruktør, genre, årti)             | Nej      |                                                   |
| U | Case-insensitiv søgning                                                                       | Ja       | US4, `MovieCollection.searchMovies`               |
| U | Alle matchende film vises ved søgning                                                         | Ja       | US4, `MovieCollection.searchMovies`               |
| U | Diakritiske tegn ignoreres ved søgning                                                        | Nej      |                                                   |
| R | Programmet går ikke ned ved bogstaver i årstal                                                | Ja       | US10, `UserInterface.prompt*`                     |
| R | Man skal kunne tilføje film, uden at erstatte en anden film                                   | Ja       | US1, `MovieCollection.addMovie`                   |
| R | Samlingen mistes aldrig, når programmet lukkes                                                | Ja       | US13, `Controller.saveMoviesIfChanged`            |
| P | Filen skrives kun, hvis der er ændringer                                                      | Ja       | US14, `Controller.saveMoviesIfChanged`            |
| P | Programmet starter på under 1 sekund                                                          | Ja       |                                                   |
| P | Man skal kunne benytte filmsamlingen inden for 2 sekunder                                     | Ja       |                                                   |
| S | `MovieCollection` og `Movie` er dækket af unit tests                                          | Ja       | US7, `MovieTest.java`, `MovieCollectionTest.java` |
| S | Al logik i io/model er dækket af unit tests                                                   | Ja       | US7, `FileHandlerTest.java`                       |
| S | Hele test-suiten kan køre inden for 2 sekunder                                                | Ja       |                                                   |

| Del | User stories                              | CRUD                   |
|-----|-------------------------------------------|------------------------|
| 1   | US1 opret en film, US2 menu               | **C**reate             |
| 2   | US3 vis alle film                         | **R**ead               |
| 3   | US4 søg efter film                        | **R**ead               |
| 4   | US5 redigér en film, US6 slet en film     | **U**pdate, **D**elete |
| 5   | US7 automatiske tests                     | N/A                    |
| 5½  | US8 markér en film som set                | **C**reate, **U**pdate |
| 5½  | US9 film, der ikke er set længe           | **R**ead               |
| 6   | US10 håndter fejl uden at gå ned          | **CRUD**               |
| 6   | US11 håndter ugyldigt input               | **CRUD**               |
| 6   | US12 markér en film som set (med dato)    | **CRUD**               |
| 7   | US13 gem filmsamlingen                    | **C**reate, **R**ead   |
| 7   | US14 gem kun ved ændringer                | **C**reate             |
| 8   | US15 opdel programmet i packages          | N/A                    |
| 9   | US16 sortér samlingen efter titel         | **R**ead               |
| 9   | US17 sortér efter andre attributter       | **R**ead               |
| 9   | US18 sortér efter to attributter samtidig | **R**ead               |
