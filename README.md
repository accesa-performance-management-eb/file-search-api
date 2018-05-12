# poc-file-search-api
Proof of Concept - File Search API using Elasticsearch

## How To Use

To clone and run this application, you'll need [Git](https://git-scm.com), 
[JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html),
 [Maven](https://maven.apache.org/download.cgi) and 
 [Elasticsearch 2.4.4](https://www.elastic.co/downloads/past-releases/elasticsearch-2-4-4) installed on your computer. 

- start Elasticsearch from [elasticsearch_home]/bin/elasticsearch

From your command line:

```bash
# Clone this repository
$ git clone https://github.com/accesa-performance-management-eb/poc-file-search-api.git

# Go into the repository
$ cd poc-file-search-api
```

You can start the application in any of the following ways:


```
# Start it using spring-boot:run plugin
$ mvn spring-boot:run
```

or

```
# Build the package
$ mvn package

# Go into build folder
$ cd target

# run the compiled JAR
$ java -jar poc-file-search-api-1.0-SNAPSHOT.jar
```
after the application is started you can call the endpoints using [Postman](https://www.getpostman.com) or even
directly your browser for [GET Open endpoints ](#open-endpoints).  


## API documentation
You can access the API documentation at the following location: [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

## Open endpoints

Open endpoints require no Authentication:

* Search files: `GET http://localhost:8080/api/rest/v1/file/search?pageSize=10&pageNumber=1&fileName=name&parentDir=name`
* Index files from a directory: `POST http://localhost:8080/api/rest/v1/file/index?recursive=true&path=/path/`
* Delete all files from index : `DELETE http://localhost:8080/api/rest/v1/file/index`

