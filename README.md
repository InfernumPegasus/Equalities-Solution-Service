# Equalities-Solution-Service
Лабораторные работы по Java с использованием фреймворка Spring Boot

## Программное обеспечение
* JetBrains Intellij IDEA
* PostgreSQL
* Postman

### Вариант 11. Решение уравнений вида
* X + firstValue = secondValue, где leftBorder <= X <= rightBorder.

## Условия работ
### Лабораторная работа 1
* Create and run locally simple web/REST service using any open-source example on Java stack: Spring/maven/gradle/Jersey/Spring MVC.
* Create GET endpoint with input parameters as URL query params, performing calculation based on your Option, and returning results in JSON format.

### Лабораторная работа 2
* Add validation for the input parameters returning 400 HTTP error in case of empty or incorrect params provided
* Add internal errors handling returning 500 HTTP error code in case of internal service exception/error
* Add logging of all process steps and errors (use different log levels for errors and debug messages)
* Add unit test for your service checking happy path and exceptional cases

### Лабораторная работа 3
* Add simple cache for your service as in-memory Map where key is an input parameter and value is calculation results. This Map (cache) should be stored as a separate class (bean). Use dependencies injection mechanism of Spring Framework (autowiring) for adding cache to your service. Web service should check cache to get calculation results from it first, if there are no calculation results for input parameter yet, do the calculation and put it into the cache before returning results in response.

### Лабораторная работа 4
* Add a new service (Counter) which calculates number of your main service calls and returns the number when requested. Counter should be implemented as a separate thread safe bean with synchronized access.
* Create high load test for your main service using JMeter, Postman or any other tool. The load test should make thousands of calls in short period of time. Then make sure that Counter correctly calculates number of service calls done by the load test.

### Лабораторная работа 5
In scope of this practical task we need to try functional programming approach using Java 8 new features:
* Update initial service created in first practical task to have ability to process sequence (stream) of input parameters. Stream API and lambdas should be used for this.
* Add new POST method which should get sequence of input parameters (they can be passed as JSON data in request body, or as simple CSV file), validate them and call new service for processing bulk data. Result can be returned as JSON or CSV file.

### Лабораторная работа 6
In previous task we did bulk operation for a stream of input data. In this task we need to apply some statistics calculation using aggregation/reduction. Calculated statistic should be added into POST response data (either JSON or CSV file). Statistics should include:
* Total amount of input parameters (or pairs/triples of parameters) provided
* Total amount of input parameters (or pairs/triples of parameters) which produced incorrect result (were not valid)
* Maximum and minimum values from results calculated
* Result which was returned most times (most popular)

### Лабораторная работа 7
We are going to wire persistence layer for storage of inbound request data and outbound response. Feel free to pick whatever database that will satisfy your needs but be ready to explain your choice.
* Adjust your web service with ability to store all calculation results of the service in database or file using one of standard persistence framework (Spring Data/Hibernate/MyBatis).

### Лабораторная работа 8
* Improve your web-service by adding asynchronous call for main calculation service and providing REST call response containing auto-generated processID (random number, which should be used as a key to store calculation results in database) without waiting for the calculation results. All calculation results should be written into database (storage) within asynchronously called service. Add a rest endpoint to get calculation results from database by processID provided.
