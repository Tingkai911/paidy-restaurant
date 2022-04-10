Refer to the "documents" folder, it contains:
1. API design document
2. SQL script to create the tables needed
3. Entity relationship diagram
4. Postman script to test the API

Features:
1. Use cases following the instructions from **https://github.com/tsunghsiang/simple-restaurant-api**
2. Basic Authentication feature using the username and password from the database for some endpoints
3. Pagination for all endpoints that may return many data

Requirements:
1) Java installed (this project is using Java 8, but installing later version should not have an effect)
2) Maven installed
3) MySQL installed

To run the unit test:
1) On the directory with "restaurant" folder, run the command "cd restaurant"
2) Now you should be in the directory that contains "pom.xml", run the command "mvn clean test"
3) Go to the "target" folder using the command "cd target/jacoco-ut", you should see a file "index.html", open this file in the browser to see the test coverage

To run the program:
1) On the directory with "restaurant" folder, run the command "cd restaurant"
2) Now you should be in the directory that contains "pom.xml", run the command "mvn clean package"
3) Go to the "target" folder using the command "cd target", you should see a file called "restaurant-0.0.1-SNAPSHOT.jar "
4) run the command "java -jar restaurant-0.0.1-SNAPSHOT.jar" to start the application, you can now test the APIs using the postman script provided