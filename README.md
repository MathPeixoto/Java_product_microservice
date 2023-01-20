# Product Microservice
This microservice is a Java 17 and Spring Boot 3 application that provides REST endpoints for managing products in a MySQL database. It also utilizes Keycloak for identity and access management.

## Getting Started
Install Docker and Docker Compose on your machine.

Clone this repository and navigate to the root directory.

Build the Docker image by running:

`docker-compose build`
Start the service by running:

`docker-compose up`
The service will be running at http://localhost:8080


## Endpoints
GET /v1/products/: Retrieve a list of products
GET /v1/products/{product-id}: Retrieve a product
POST /v1/products: Add a new product
PUT /v1/products/{product-id}: Update a product


## Keycloak Setup
1. Start the Keycloak server by running:
`docker-compose up`
The Keycloak server will be running at http://localhost:9080

2. Create a new realm and client for the microservice.

3. Configure the client with the appropriate permissions for the endpoints.

4. Update the application.properties file with the Keycloak configuration.

## Database
This microservice uses a MySQL database. The database is automatically set up and seeded with data when the service is started.

## Contributing
Fork the repository
Create your feature branch (git checkout -b my-new-feature)
Commit your changes (git commit -am 'Add some feature')
Push to the branch (git push origin my-new-feature)
Create a new Pull Request
