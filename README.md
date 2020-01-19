# Project Two - RESTful APIs

## Description

In this repo you will find two Spring servers that exposes RESTful APIs. The first APIs is used to manage users
accounts (auth-server). The second API exposes 3 entites (User - WatchedMovie - Movie) and is used to keep a history
of the viewed movies (movie-server).

A Cucumber project (server-specs) is implemented to validate the two APIs implementation.


## Using RESTful APIs

To use these APIs, you can build them with `docker-compose`.

```bash
cd docker/topology-amt
docker-compose up
```

* User API : [http://localhost/auth](http://localhost/auth){:target="_blank"}
* Movie API : [http://localhost/api](http://localhost/api){:target="_blank"}

To validate the API implementation just execute the following commands. Do this when the two servers are running.

```bash
cd microservices/server-specs
mvn clean test
```

You will see the result in the console.

## Documentation

All documentation files can be found in the [doc folder](doc/). You have access to the doc
files directly from the following links:

* [1 - Guidelines](doc/1_guidelines.md)
* [2 - Implementation](doc/2_implementation.md)

## Authors

| Name                                 | Email                                | Github      |
|--------------------------------------|--------------------------------------|-------------|
| Loris Gilliand                       | loris.gilliand@heig-vd.ch            | texx94      |
| Mateo Tutic                          | mateo.tutic@heig-vd.ch               | mtutic      |
