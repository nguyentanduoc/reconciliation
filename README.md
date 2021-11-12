# High level design
![HighLevelDesign")](asset/HighLevelDesign.png?raw=true "HighLevelDesign")
# Principles
<ul>
    <li>Single Responsibility Principle</li>
    <li>Interface Segregation Principle</li>
    <li>Dependency Inversion Principle</li>
</ul>

# Patterns
<ul>
    <li>Strategy Pattern</li>
    <li>Data transfer object Pattern</li>
    <li>Builder pattern</li>
</ul>

# Practices
1. Three Layers
    - Controller Layer
    - Service Layer
    - Repository Layer
2. Mapper <br/>
Mappers are used to converting the form of data when transferred between layers. There are two types of Mappers:
    - Model Mapper: This will map any data to the Model.
    - DTO Mapper: This will map any data to DTOs.
# Project Structure
![HighLevelDesign")](asset/microservice.png?raw=true "HighLevelDesign")

I have 5 services
- authentication-service:  Server that authenticates the Resource Owner and issues access tokens after getting proper authorization
- discover-service: Service Registration and Discovery
- gateway-service: Gateway service
- transaction-service: Handle upload file and store transactions into the database
- history-service: Store the history upload
# The code folder structure (transaction-service)
![transaction-service-structure")](asset/transaction-service-structure.PNG?raw=true "transaction-service-structure")
- package config: contain config security
- package controller:
    - It will contain Rest APIs definition and request body. 
    - Only API calls should invoke the Controller Layer.
- package service:
    - It will only take the data from the controller layer and transfer it to the repository layer.
    - It will also contain business logic and model the data for the repository layer.
    - It will also take the data from the repository layer and send it back to Controller Layer.
    - Only the Controller layer should invoke the Service Layer.
- package repository:
    - It will interact with the underlying database.
- package DTO:
    - DTOs (Data Transfer objects) are the objects or classes used to transfer data between layers through the service layer. 
    - The service layer only accepts data in the form of DTOs.
    - Any data returned to the controller layer will be in the form of DTOs.
- package Model
    - Models are the object used by the repository layer to call the Database stored procedure or perform CRUD operations without using Stored Procedure.
- package utils: contain constants
# The key libraries
- opencsv (read csv file)
- apache poi (read excel file)
- openfeign (communication between services)
- liquibase (tracking, managing and applying database schema changes)
- lombok: Lombok is a Java library that generates getter & setter codes automatically. Besides, it also supports generating constructors with parameters, or without parameters
# Frameworks
- Spring boot
- Oauth2
# The steps in order to get the application on local
## Environment
    - Java: openjdk 11.0.8-internal 2020-07-14
    - PostgreSQL 14.0
## Steps to run on local

1. Pull code from github to local computer.
2. Create databases (`authentication-db`, `history-db`, `transaction-db`)
3. Edit config connection to database
    1. authentication-service:
        - open `authentication-service\src\main\resources\application.properties`
        - edit: user and password
        ![edit-authentication-service")](asset/edit-authentication-service.PNG?raw=true "edit-authentication-service")
    2. transaction-service:
        - open `transaction-service\src\main\resources\application.properties`
        - edit: user and password
        ![edit-transactionservice")](asset/edit-transactionservice.PNG?raw=true "edit-transactionservice")
    3. history-service:
        - open `history-service\src\main\resources\application.properties`
        - edit: user and password
        ![edit-history-service")](asset/edit-history-service.PNG?raw=true "edit-history-service")

3. D-click `start-discover-service` (waiting for the service started successfully)
4. D-click `start-authentication-service` (waiting for the service started successfully)
5. D-click `start-gateway-service`
6. D-click `start-history-service`
7. D-click `start-transaction-service`
# cURL commands
1. Get token
   ```
   curl --location --request POST 'http://localhost:8888/authentication-service/oauth/token' \
   --header 'Authorization: Basic Y2xpZW50OnBhc3N3b3Jk' \
   --header 'Content-Type: application/x-www-form-urlencoded' \
   --data-urlencode 'grant_type=password' \
   --data-urlencode 'username=admin' \
   --data-urlencode 'password=password'
   ```
2. Upload transactions
    ```
    curl --location --request POST 'http://localhost:8888/transaction-service/transaction/bulk-upload' \
    --header 'Authorization: Bearer ${token get from api get token}' \
    --form 'file=@"/C:/Users/NTD_S/OneDrive/Máy tính/reconciliation-xlsx - Copy.xlsx"'
    ```
3. Get all transactions
   ```
   curl --location --request GET 'http://localhost:8888/transaction-service/transaction/?page=1' \
    --header 'Authorization: Bearer ${token get from api get token}'
   ```
4. Get All historis
    ```
    curl --location --request GET 'http://localhost:8888/history-service/history?page=0' \
    --header 'Authorization: Bearer ${token get from api get token}'
    ```