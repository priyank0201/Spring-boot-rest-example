** Lochside House booking project **

This project uses Java 1.7, Maven 4, Spring Boot 1.3.3, Tomcat 7 (embedded) nad follows the REST architecture.

** Setup/ Running **

This application is packaged as a war which has Tomcat 7 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.


 Build the project and run the tests by running "mvn clean package" from maven or "clean package" from Eclipse or similar IDE.
 Start the service from command prompt by the command:
 
 	java -jar -Dspring.profiles.active=test lochsideHouseBookingSystem-0.1.0.war


Once the service starts up, something like this should appear on the console:

2017-03-07 13:46:05.474  INFO 24608 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8091 (http)
2017-03-07 13:46:05.506  INFO 24608 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090 (http)
2017-03-07 13:46:05.506  INFO 24608 --- [           main] com.priyank.example.Application          : Started Application in 10.5 seconds (JVM running for 11.058)
 
 
 
** The service should start on port 8090

** Controller, Application **

The Rest controller is defined in: com.priyank.example.api.rest.hotelController, while the Spring boot Application file is: com.priyank.example.Application

** Rest service operations **

1. Create
POST /example/hotel/lochsideHouse/create
Accept: application/json
Content-Type: application/json

{
	"booking":
	{
		"customer":{
			"id":1,"bookings":[]
		},
	
		"room":{
			"id":1,"type":"SINGLE_ROOM","price":100
		 },

		"checkInDate":"2017-04-04T23:28:56.782Z",
		"checkOutDate":"2017-05-04T23:28:56.782Z",
		"price":100,
		"state":"CREATED"
	 }

}

2. Bet bookings for a room
GET /example/hotel/lochsideHouse/bookings/room/{id}
Accept: application/json
Content-Type: application/json

{id}=1


3. Bet bookings for a customer
GET /example/hotel/lochsideHouse/bookings/customer/{id}
Accept: application/json
Content-Type: application/json

{id}=1

4. Check availability for a particular room
GET /example/hotel/lochsideHouse/available/room/{id}
Accept: application/json
Content-Type: application/json

{id}=1

