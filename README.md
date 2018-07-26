# poc-file-search-api
[![Build Status](https://api.travis-ci.org/accesa-performance-management-eb/poc-file-search-api.svg?branch=master)](https://travis-ci.org/eusebiu-biroas/randomfiles.io-api) [![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=bugs)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=code_smells
)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) [![Duplicated lines](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=duplicated_lines_density
)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=ncloc
)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.biroas.poc%3Apoc-file-search-api&metric=coverage
)](https://sonarcloud.io/dashboard?id=com.biroas.poc%3Apoc-file-search-api) 

#Proof of Concept - File Search API using Elasticsearch

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
directly your browser.


## API documentation
You can access the API documentation at the following location: [`https://poc-file-search-api.herokuapp.com/swagger-ui.html`](https://poc-file-search-api.herokuapp.com/swagger-ui.html)

# C4 Model Diagrams

## Level 1: System Context diagram

![alt text](c4model/C1.png)

## Level 2: Container diagram

![alt text](c4model/C2.png)