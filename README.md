Micro Services on cities
=======================
Copy of below repository
https://github.com/mstine/citytest

Upgraded to Spring Boot 1.1.6.RELEASE
Used Mysql as cloud and local datastore

===========
Cities Test
===========

To Deploy PWS:

create mysql service with below command( using cities-db in manifest file)

$cf create-service p-mysql 100mb-dev cities-db

mvn war:war

cf push

=================================
Checking api end points with curl
=================================
$ curl -i "https://pws-cities.cfapps.io/cities"

$ curl -i "https://pws-cities.cfapps.io/cities?size=5"

$ curl -i "https://pws-cities.cfapps.io/cities?size=5&page=3"

$ curl -i "https://pws-cities.cfapps.io/cities?sort=postalCode,desc"
