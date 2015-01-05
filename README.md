Spring boot, Hazelcast and MongoDB
==================================

Simple project running on a Jetty 9 embebed. CRUD restful services operations implemented.

Requeriment:

Mongodb running on default port.

Hazelcast configuration:
- hazelcast.xml file.

Mongodb configuration:
- application.properties

****************
Integration Test:

An embebed mongodb de.flapdoodle.embed has been integrated. CRUD test operations have been implemented using spring restTemplate.

Build and run:

* mvn clean install
* java -jar target/(jar name).jar
