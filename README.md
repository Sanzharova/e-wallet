# ecommerce-shop
E-wallet
 
# ecommerce-shop

```bash
Add configuration in src/main/resources/application.properties
spring.application.name=ecommerse-shop
server.port=8080
spring.datasource.url=${H2_DATASOURCE_URL} // database url, example jdbc:h2:mem:your_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.name=${H2_DATASOURCE_NAME} // database name
spring.datasource.username=${H2_DATASOURCE_USERNAME} // database username
spring.datasource.password=${H2_DATASOURCE_PASSWORD} // database password 
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

springdoc.api-docs.path=/api-docs // configuration for swagger 

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.trace=false
```

# Default Endpoints
```bash
    Endpoints:
      your_host/swagger-ui/index.html - swagger url
      your_host/h2-console - for open h2 database http url
```


# Favour Endpoint

```bash
    CRUD favour
    'your_host/favours/{id}':
      Http-Get: Get favour by Id
      
    'your_host/favours'
      Http-Get: Get favours by pagination filter
    
    'your_host/favours'
      Http-Post: Save favour
    
    'your_host/favours/{id}'
      Http-Put: Update favour 
      
    'your_host/favours/{id}'
      Http-Delete: Delete favour
```    

# Wallet Endpoint

```bash
    CRUD wallet
    'your_host/wallets/{id}':
      Http-Get: Get wallet by Id
      
    'your_host/wallets'
      Http-Get: Get wallets by pagination filter
    
    'your_host/wallets'
      Http-Post: Save wallet 
    
    'your_host/wallets/{id}'
      Http-Put: Update wallet 
      
    'your_host/wallets/{id}'
      Http-Delete: Delete wallet 
```    

# User Endpoint

```bash
    CRUD user
    'your_host/users/{id}':
      Http-Get: Get user by Id
      
    'your_host/users'
      Http-Get: Get users by pagination filter
    
    'your_host/users'
      Http-Post: Save user 
    
    'your_host/users/{id}'
      Http-Put: Update user 
      
    'your_host/users/{id}'
      Http-Delete: Delete user 
```   

# Payment Endpoint

```bash
    CRUD user
    'your_host/payments/check':
      Http-Post: This endpoint validate request, then create payment in db
      
    'your_host/payments/create/{id}'
      Http-Post: Change status to 'CREATED' payment
    
    'your_host/payments/confirm'
      Http-Post: Change status to 'CONFIRM' payment
    
     'your_host/payments/rollback'
      Http-Post: Rollback payment
      
    'your_host/payments/status/{status}'
      Http-Post: Get payment by status
```   