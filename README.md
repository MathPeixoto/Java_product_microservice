# product-ms
Microservice responsible for the product actions, such as,
add or remove a product from database and retrieve its products based on a requirement.

In order to run this service, you should go into target folder and run the following code:
`java -jar product-ms-0.0.1-SNAPSHOT.jar`

As long as the service is up, you'll be able to send any request to its endpoints

## Alternatives
If you want to test this project without setting your environment, you can use docker. Just run these steps:

1 - This will create a docker image of the application
`mvn spring-boot:build-image`

2- And this one to up a container and set up the mysql database:
`docker compose up`
