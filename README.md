# onlinestore
Customer buying a product from Online Store

To run the jar file from this repository 
	install Java 8.
	open the command line tool from the location of the jar
	type "java -jar onlinestore-0.0.1-SNAPSHOT" 

To run the project through IDE 
	import this project into IDE of your choice 
	right click and build (mvn package)
	run as Spring Boot Application.

To run from command line 
	install both maven and java
	open the project in command line tool and type "mvn package"
	the jar will be generated in onlinestore/target/onlinestore-0.0.1-SNAPSHOT	

The Postman API is attached in the classpath of this project to import and test.

The products and 2 user details are loaded as part of application startup.
Below are the login details for loaded users :
User1 -> email    : emilydavis@gmail.com 
	 password : emily123
User2 -> email    : lukemartin@gmail.com
	 password : luke123 	

The application uses inmemory database h2.
Once the Application is loaded the database can be viewd at : http://localhost:8080/api/h2-console