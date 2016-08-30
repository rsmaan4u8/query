Feature: Movie or Music search in itunes, omdb API

  Scenario Outline: For wrong input for movie search, user should be informed with correct message
    When I run application with command 'java -jar -Dapi="<API>" -Dmovie="<MOVIE_TITLE>" query.jar'
    Then console contains
      | OUTPUT   |
      | <OUTPUT> |
    Examples:
      | MOVIE_TITLE   | API    | OUTPUT                                          |
      |               |        | Problem : Please specify API to use ITUNES OMDB |
      |               |        | Problem : Movie search value is empty           |
      | Indiana Jones |        | Problem : Please specify API to use ITUNES OMDB |
      |               | itunes | Problem : Movie search value is empty           |

  Scenario Outline: For wrong input for music search, user should be informed with correct message
    When I run application with command 'java -jar -Dapi="<API>" -Dmusic="<MUSIC_TITLE>" query.jar'
    Then console contains
      | OUTPUT   |
      | <OUTPUT> |
    Examples:
      | MUSIC_TITLE | API    | OUTPUT                                          |
      |             |        | Problem : Please specify API to use ITUNES OMDB |
      |             |        | Problem : Music search value is empty           |
      | Bob Marley  |        | Problem : Please specify API to use ITUNES OMDB |
      |             | itunes | Problem : Music search value is empty           |

  Scenario: On search for music AND movie, user should be informed that only one option is allowed
    When I run application with command 'java -jar -Dapi="itunes" -Dmovie="Indiana Jones" -Dmusic="Bob Marley" query.jar'
    Then console contains
      | OUTPUT                                                                                                    |
      | Problem : You cannot search both movie and music at same time.Please provide either music or movie option |

  Scenario: On unsupported API, user should be informed about supported APIs
    When I run application with command 'java -jar -Dapi="imdb" -Dmovie="Indiana Jones" query.jar'
    Then console contains
      | OUTPUT                                                             |
      | Problem : imdb not supported You can only search in itunes or omdb |

  Scenario: search with movie title should find matching movies
    When I run application with command 'java -jar -Dapi="itunes" -Dmovie="Indiana Jones" query.jar'
    Then console contains
      | OUTPUT                                                                                                               |
      | Provider : ITUNES Title : Indiana Jones and the Raiders of the Lost Ark Year : 1981 Director : Steven Spielberg      |
      | Provider : ITUNES Title : Indiana Jones and the Last Crusade Year : 1989 Director : Steven Spielberg                 |
      | Provider : ITUNES Title : Indiana Jones and the Kingdom of the Crystal Skull Year : 2008 Director : Steven Spielberg |
      | Provider : ITUNES Title : Indiana Jones and the Temple of Doom Year : 1984 Director : Steven Spielberg               |

  Scenario: search with music title should find matching music
    When I run application with command 'java -jar -Dapi="itunes" -Dmusic="Bob Marley" query.jar'
    Then console contains
      | OUTPUT                                                                                                |
      | Provider : ITUNES Title : Is This Love Year : 1984 Director : Bob Marley & The Wailers                |
      | Provider : ITUNES Title : One Love / People Get Ready Year : 1984 Director : Bob Marley & The Wailers |
      | Provider : ITUNES Title : Jamming Year : 1984 Director : Bob Marley & The Wailers                     |
      | Provider : ITUNES Title : No Woman, No Cry Year : 1984 Director : Bob Marley & The Wailers            |

  Scenario: search itunes and omdb together should find matching result in both APIs
    When I run application with command 'java -jar -Dapi="itunes omdb" -Dmusic="Bob Marley" query.jar'
    Then console contains
      | OUTPUT                                                                                                              |
      | Provider : ITUNES Title : Is This Love Year : 1984 Director : Bob Marley & The Wailers                              |
      | Provider : ITUNES Title : One Love / People Get Ready Year : 1984 Director : Bob Marley & The Wailers               |
      | Provider : ITUNES Title : Jamming Year : 1984 Director : Bob Marley & The Wailers                                   |
      | Provider : ITUNES Title : No Woman, No Cry Year : 1984 Director : Bob Marley & The Wailers                          |
      | Provider : OMDB Title : Classic Albums: Bob Marley & the Wailers - Catch a Fire Year : 1999 Director : Jeremy Marre |
      | Provider : OMDB Title : Bob Marley: The Legend Live Year : 2003 Director : Don Gazzanaga                            |
      | Provider : OMDB Title : Bob Marley Year : 1981 Director : Stefan Paul                                               |
      | Provider : OMDB Title : Bob Marley and the Wailers: Live! At the Rainbow Year : 1991 Director : Keef                |

  Scenario: If no result should print message
    When I run application with command 'java -jar -Dapi="omdb" -Dmusic="%20 BNDG" query.jar'
    Then console contains
      | OUTPUT                                           |
      | No result found. Try searching some other title. |
