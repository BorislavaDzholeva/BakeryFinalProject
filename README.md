# Bakery Web Store - My first Spring Boot Project
### Welcome to Bakery Website repository! This is my personal project, marking my first venture and steps into application development.

## Table of Contents
* Description 
* Technologies Used
* Getting Started
* Demo
* Project Structure
* Backend Features
* Admin Panel Features
* User Features


## Description

This Spring Boot project, named "Bakery," is a comprehensive web application for a bakery store.
It supports user authentication and authorization, including user roles such as Administrator and User.
It provides a catalog and an online shop, along with an admin panel accessible at the `/admin/` route. The admin panel allows for efficient management of various aspects of the system, including users, categories, orders, products, and user reviews.


![A4MTFD3FpR7a61R1ovl9.jpg](src%2Fmain%2Fresources%2Fstatic%2Fimages%2FA4MTFD3FpR7a61R1ovl9.jpg) ![img.png](img.png)
## Technologies Used

Before you start, ensure you have the following installed on your machine:

- Java Development Kit (JDK) 17
- Spring Security
- Spring Data JPA
- Spring Web
- Spring Boot Mail for sending emails
- Thymeleaf
- HTML, CSS, JavaScript
- Bootstrap 5
- Maven
- MySQL
- ModelMapper
- And more...


## Getting Started

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/BorislavaDzholeva/BakeryFinalProject.git
   ```

2. **Configure Database:**

    - Create a MySQL database with the name `bakery_database`.
    - Update the database configuration in `src/main/resources/application.properties`:

      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/bakery_database
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      ```

3. **Run the Application:**

   ```bash
   cd BakeryFinalProject
   mvn spring-boot:run
   ```

   The application will start, and you can access it at [http://localhost:8080](http://localhost:8080).
![readMephoto.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMephoto.png)

## Demo
Deployed to : [Link](https://bakery.agreeablebeach-fd5711a9.westeurope.azurecontainerapps.io/)

## Project Structure

- `src/main/java/BakeryProject/demo`: Contains the main application class and other Java classes.
- `src/main/resources`: Contains application properties, Thymeleaf templates, and static resources.
- `src/test`: Contains test classes.

## Backend Features
- **Security:** The application uses Spring Security for authentication and authorization. It has two types of users: Administrator and User.
- **Hibernate Validator:** The application uses Hibernate Validator for validating user input.
- **Error Handling:** The application defines custom exception classes for various HTTP status codes.
- **Interceptor:** The application defines a custom interceptor to check for blacklisted (banned) users everytime when request to the server is made.
- **Event and Listener:** The application defines two event and listener classes:
  - **First** - for sending emails to users when their orders are shipped and delivered.
  - **Second** - for sending emails to users for products available only in the weekend.
- **Schedule Tasks:** The application defines two scheduled tasks:
  - **First** - for checking every 30 minutes if 24 hours of user's ban time has passed and if so, to remove the user from the blacklist.
  - **Second** - for sending emails to users every 11:00 AM on Friday for products available only in the weekend.
- **JavaMailSender:** The application uses JavaMailSender for sending emails to users.
- **Azure blob storage:** The application uses Azure blob storage for storing images.
- **AOP:** The application uses AOP for notifying the administrator when new user is registered.
- **JUnit, Mockito:** The application uses JUnit and Mockito for unit and integration tests.
- **ModelMapper:** The application uses ModelMapper for mapping entities to DTOs.



## Admin Panel Features

The admin panel, accessible at `/admin/`, provides the following management options:

- **Managing Users:** Admin can add, edit and remove user accounts. ![readMeAdminAddUser.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeAdminAddUser.png)
- **Managing Categories:** Admin can add, edit and remove product categories. ![readMeAdminCategories.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeAdminCategories.png)
- **Managing Orders:** Admin can view and manage customer orders. ![readMeAdminOrders.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeAdminOrders.png)
- **Managing Products:** Admin can add, edit, or remove bakery products. ![readMeAdminProducts.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeAdminProducts.png) ![readMeAdminAddProduct.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeAdminAddProduct.png) ![readMeAdminEditProduct.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeAdminEditProduct.png)
- **Managing User Reviews:** Admin can view and approve user reviews. ![readMeAdminReviews.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeAdminReviews.png)
- **Managing Blacklist:** Admin can add or remove user's IP address from the blacklist.


## User Features

The frontend offers to User the following features:

- **User Registration:** Users can register an account on the platform. ![readMeRegistration.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeRegistration.png)
- **User Login and Logout:** Users can log in and log out of their accounts. ![readMeLogin.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeLogin.png)
- **User Reviews:** Users can write reviews for products. ![readMeReview.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeReview.png)
- **Online Shopping:** Users can browse the catalog, add products to their cart, and place orders.
  ![readMeCategories.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeCategories.png)
- **User Cart:** Users can view their cart, update quantities, and remove products. 
![ReadMeCart.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FReadMeCart.png)
- **User Create an order:** Users can place orders.
![readMeCreateAnOrder.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeCreateAnOrder.png)
![orderShipped.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2ForderShipped.png)
- **User Profile:** Users can view their profile information and order history. ![readMeUserProfile.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeUserProfile.png)