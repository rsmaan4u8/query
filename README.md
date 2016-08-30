 README
========

## Requisites
- Java (version 1.8.0_40)
- Gradle
- Preferred IDE/Text editor
- Internet connection

## Basic commands
- `$ ./gradlew test`: run tests
- `$ ./gradlew build`: builds the project
- `$ java -Dapi="itunes omdb" -Dmovie="Indiana Jones" -jar build/libs/query-1.0.0.jar`: runs tool to search API

## Example
From the query directory run
- `$ java -Dapi="itunes omdb" -Dmovie="Indiana Jones" -jar build/libs/query-1.0.0.jar`
- `$ java -Dapi="itunes omdb" -Dmusic="Bob marley" -jar build/libs/query-1.0.0.jar`

#### To search in itunes only
- `$ java -Dapi="itunes" -Dmusic="Bob marley" -jar build/libs/query-1.0.0.jar`


## Further improvements
- Logging should go to a file
- If there is some problem in search e.g. no internet connection better message should be printed
- May be refactor APISearchController Unit tests
- Refactor SearchService implementations to extract url building in a separate component
- SpecificationFactory can be refactored to separate from validation
- getSearchProvider in APISearchService seems bit wrong as similer method getProvider exist in SearchItem
- Number of results returned relies on the default result size of API provider