# Project Two - RESTful APIs

## Description

In this repo you will find a Spring server that exposes two RESTful APIs. The first APIs is used to manage users
accounts (auth-server). The second API exposes 3 entites (User - WatchedMovie - Movie) and is used to keep a history
of the viewed movies (movie-server).

A Cucumber project (server-specs) is implemented to validate the two APIs implementation.


## Using RESTful APIs

To use these APIs, you can build them with `docker-compose`.

```bash
cd docker/topology-amt
docker-compose up
```

* User API : [http://localhost/auth](http://localhost/auth)
* Movie API : [http://localhost/api](http://localhoast/api)

To validate the API implementation just execute the followings commands. Do this when the two servers are running.

```bash
cd microservices/server-specs
mvn clean test
```

You will see the result in the console.

## Documentation

All documentation files can be found in the [doc folder](doc/). You have access to the doc
files directly from the following links:

* [1 - Guidelines](doc/1_guidelines.md)
* [2 - implementation](doc/2_implementation.md)

## Authors

| Name                                 | Email                                | Github      |
|--------------------------------------|--------------------------------------|-------------|
| Loris Gilliand                       | loris.gilliand@heig-vd.ch            | texx94      |
| Mateo Tutic                          | mateo.tutic@heig-vd.ch               | mtutic      |
