# FURPS – vores filmsamling

|   | Krav                                                                                          | Opfyldt?    | Hvor                                |
|---|-----------------------------------------------------------------------------------------------|-------------|-------------------------------------|
| F | Man kan oprette film                                                                          | Ja          | US1-2, `MovieCollection.addMovie`   |
| F | Man kan vise alle film                                                                        | Ja          | US3, `MovieCollection.getMovies`    |
| F | Man kan søge film                                                                             | Ja          | US4, `MovieCollection.searchMovies` |
| F | Man kan søge på en del af en titel                                                            | Ja          | US4, `MovieCollection.searchMovies` |
| F | Man kan rette film                                                                            | Ja          | US5, `MovieCollection.editMovie`    |
| F | Man kan slette film                                                                           | Ja          | US6, `MovieCollection.deleteMovie`  |
| F | Man kan markere en film som set                                                               | Nej         | US8                                 |
| F | Man kan vise alle film, der ikke er set længe                                                 | Nej         | US9                                 |
| U | Man kan trykke Enter for at beholde en værdi, når man redigerer                               | Ja          |                                     |
| U | Når man skriver en genre, foreslår den en genre baseret på eksisterende genre i filmsamlingen | Nej         |                                     |
| U | Når du skriver invalid data, giver den en passende besked                                     | Ja          |                                     |
| U | Vi kan søge en film frem baseret på dele af attributter (instruktør, genre, årti)             | Nej         |                                     |
| U | Case-insensitiv søgning                                                                       | Ja          |                                     |
| U | Alle matchende film vises ved søgning                                                         | Ja          |                                     |
| U | Diakritiske tegn ignoreres ved søgning                                                        | Nej         |                                     |
| R | Programmet går ikke ned ved bogstaver i årstal                                                | Ja          |                                     |
| R | Man skal kunne tilføje film, uden at erstatte en anden film                                   | Ja          |                                     |
| R | Samlingen mistes aldrig, når programmet lukkes                                                | Nej         |                                     |
| P | Filen skrives kun, hvis der er ændringer                                                      | Nej – del 6 |                                     |
| P | Programmet starter på under 1 sekund                                                          | Ja          |                                     |
| P | Man skal kunne benytte filmsamlingen inden for 2 sekunder                                     | Ja          |                                     |
| S | `MovieCollection` og `Movie` er dækket af unit tests                                          | Ja          |                                     |
| S | Al logik uden for `UserInterface` er dækket af unit tests                                     | Nej – del 6 |                                     |
| S | Hele test-suiten kan køre inden for 2 sekunder                                                | Ja          |                                     |

| Del | User stories                          | CRUD                   |
|-----|---------------------------------------|------------------------|
| 1   | US1 opret en film, US2 menu           | **C**reate             |
| 2   | US3 vis alle film                     | **R**ead               |
| 3   | US4 søg efter film                    | **R**ead               |
| 4   | US5 redigér en film, US6 slet en film | **U**pdate, **D**elete |
| 5   | US7 automatiske tests                 | N/A                    |
| 5½  | US8 markér en film som set            | **C**reate, **U**pdate |
| 5½  | US9 film, der ikke er set længe       | **R**ead               |
| ??  | US                                    |                        |
