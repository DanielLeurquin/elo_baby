# Installation Guide
## 1. Download the project
Make a new directory and use the command `git clone` to download the project"

## 2. Install Postgres
- First Solution :

Got to this [link](https://www.postgresql.org/download/) and download the version that suits your OS.

- Second Solution :
  If you have docker installed on your machine you can use the following command to download the postgres image and run it on your machine
  `docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD={myNotSoSecretPassword} -d postgres`

## 3. Env Variable

Set your postgres password as an environment variable named `POSTGRES_PASSWORD` on your machine or on your project

## 4. Create the Database
- Easy method :
  Use a database managing sofware (you can download one as a Vscode plugin) create a new database named `boilerplate`


- Hard method :
    - Open a terminal
    - If you used docker to run your postgres execute the following command. Ignore it otherwise  
      `docker exec -it postgres bash`
    - Connect to your postgres database using `psql -U postgres`
    - Enter your password
    - Create a new database using the command `CREATE DATABASE boilerplate;`
    - Exit the psql terminal using `\q`

## 5. Download maven
Download maven from this [link](https://maven.apache.org/download.cgi) and ensure that maven is correctly installed on
your machine by running the command `mvn -v` in a terminal

## 6. Install dependencies
Open a terminal in the projects directory and run the command `mvn clean install -DskipTests`
and then `mvn clean package -DskipTests`

## 7. Run the project
You can now run the project from your IDE. To check if your project is running properly,
you can use postman or another tool and send the following **POST** request `localhost:8080/auth`

The result of the request should give you a status 200 by default. You now have to
code the logic of the application.

# Project Structure

In this project you will find different packages

## 1. Config

This packages handles spring configurations and the part that is responsible for the
token generation and validation as well as the security configuration.

## 2. Controller

This package handles the different endpoints of the application. You can find the
different controllers that are responsible for the different endpoints of the application.
By default, you can find a UserController and an AuthController. There is also an Exception handler that intercepts 
java **controller** exception and decides what to send back to the client.

## 3. DAL (Data Access Layer)

It contains the different repositories that are responsible for the communication with the database as well as
dto objects or enums that are used in the application. In the postgres package you can find the database entities.

## 4. Exception

You can find different classes for custom exception handling in this package

## 5. Mapper

This project uses Mapstruct to map the different entities to dto objects. You can find the different mappers in this package.

## 6. Service

You can find all the different services in this package. Usually you can find one 
service per controller, repository or database entity, but it is not mandatory.

## 7. Resources

- Flyway :

You can find the different sql scripts that are used to create the database tables in this package.
The scripts are executed in the order of their names. If you modify the scripts, you have to change the name of the script for them
to be run properly.

- Application profiles :

You can find the different application profiles with their parameters inside.
By default, the project uses the `local` profile. You can change the run profile when you run your project.