# shopApp
Mini version of online shop like Allegro.  
Application made for my personal skills development.  
Application uses JWT Token with sessionStorage.  
User authenthicated has access to secured endpoints.

### Features on:
- Register new user (if user register is success on his email application sends welcome email)
- User can log into application
- Log out
- Change password
- Reset password for registered User
- Displaying items by categories
- Adding items to Cart by user logged and also anonymous user to Session storage
- Displaying item details
- Buying items (logged user only) and selecting order address and payment method
- Searching items by Seller name or by using searcher on main bar
- Returning items for 7 days since product buy
- Finalizing item buy in profile component
- Adding new items on auction

### Coming soon:
- Better design of application
- And... more !

## Technologies
* Java 11
* Spring Boot
* Angular
* MySQL
* MongoDB
* JWT Authorization
* Spring Security
* Hibernate
* Primeng
* FontAwesome

## Application Run
From main directory of project:

Angular:  
> npm start --prefix angular/  


Spring:  
> ./mvnw spring-boot:run
