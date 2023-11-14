# tiny-http-server

## Generate the 'http-server' artifact
Naviagate the http-server project and execute the command 'mvn clean install' to generate the jar file.

## Run the test application.
Go to http-server-demo project, run com.sample.app.App class.

## Test the applicaiton.
### Get APIs
http://127.0.0.1:8080/v1/employees
http://127.0.0.1:8080/v1/employees/by-city/Chennai
http://127.0.0.1:8080/health

# POST API
curl --location --request POST 'http://127.0.0.1:8080/v1/employees' \
--header 'Content-Type: application/json' \
--data-raw '
{"id": 10,
"name": "Thulasi",
"age": 21,
"city": "Bangalore"
}'