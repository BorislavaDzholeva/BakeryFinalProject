# Bakery Web Store - Spring Boot Project
*Demo URL:* [Link](https://bakery.agreeablebeach-fd5711a9.westeurope.azurecontainerapps.io/)

This Spring Boot project, named "Bakery," is a comprehensive web application for a bakery store. It provides a catalog and an online shop, along with an admin panel accessible at the `/admin/` route. The admin panel allows for efficient management of various aspects of the system, including users, categories, orders, products, and user reviews.

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
## Project Structure

- `src/main/java/BakeryProject/demo`: Contains the main application class and other Java classes.
- `src/main/resources`: Contains application properties, Thymeleaf templates, and static resources.
- `src/test`: Contains test classes.

## Admin Panel Features

The admin panel, accessible at `/admin/`, provides the following management options:

- **Managing Users:** Admin can add, edit and remove user accounts.
- **Managing Categories:** Admin can add, edit and remove product categories.
- **Managing Orders:** Admin can view and manage customer orders.
- **Managing Products:** Admin can add, edit, or remove bakery products.
- **Managing User Reviews:** Admin can view and approve user reviews.

## Frontend Features

The frontend offers the following features:

- **User Registration:** Users can register an account on the platform. ![readMeRegistration.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeRegistration.png)
- **User Login and Logout:** Users can log in and log out of their accounts. ![readMeLogin.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeLogin.png)
- **Online Shopping:** Users can browse the catalog, add products to their cart, and place orders.
  ![readMeCategories.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeCategories.png)
- **User Reviews:** Users can write reviews for products. ![readMeReview.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeReview.png)
- **User Profile:** Users can view their profile information and order history. ![readMeUserProfile.png](src%2Fmain%2Fresources%2Fstatic%2Fimg%2FreadMeUserProfile.png)