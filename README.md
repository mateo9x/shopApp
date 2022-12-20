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
- Notifying seller about "one" of his item been bought through email message
- Buying product straight from cart page when user is logged
- Verify what products seller already sold
- Verify what products seller is selling now

### Coming soon:
- Paypal integration

## Technologies
* Java 11
* Spring Boot
* Angular
* Postgres
* JWT Authorization
* Spring Security
* Hibernate
* Liquibase
* Primeng
* FontAwesome

## Application Run
From main directory of project:

Angular:  
> npm start --prefix angular/  


Spring:  

> Unzip Images.rar and paste them where do you prefer.  
>   
> Then set all the properties:  
>   
> ${DATABASE_URL},  
> ${DATABASE_USER},  
> ${DATABASE_PASSWORD},   
> ${GMAIL_USER},  
> ${GMAIL_PASSWORD},  
> ${FRONT_APPLICATION_URL},  
> ${PHOTO_PATH_URL},  
>   
> Then simply run:  
> ./mvnw spring-boot:run
