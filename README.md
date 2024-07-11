<h1>Web-Gateway</h1>
Main gateway service for Tarifficator application.

<h2>Getting started</h2>

1. Install the Docker from https://www.docker.com/get-started/
2. Clone project from git.hub
3. Open the terminal in project folder
4. Create docker network by command ```docker network create tarificator-network ```
5. Enter the command ```docker-compose up```
6. Set up other microservices (tariff-service, product-service, account-service). You can find it in this git.hub account

<h3>Endpoints</h3>

Default url domain: http://localhost:18004/

<h4>Tariff API</h4>
<h5>Only for authorized users. Need to send JWT-token into `Authorization` header</h5>

1. ```GET /api/tariffs``` - return all saved tariffs
2. ```GET /api/tariffs/{id}``` - return tariff by url parameter [id]
3. ```DELETE /api/tariffs/{id}``` - delete tariff by url parameter [id]
4. ```POST /api/tariffs``` - create new tariff
    1. Need to send dto to request body: <br>```{
       "name":"Зарплатный тариф",
       "startDate":"2024-01-01",
       "endDate":"2024-12-31",
       "description":"Для рабочего класса",
       "rate":"3.75"
       }```
    2. Field ```description``` is optional. Other fields are required
5. ```PATCH /api/tariffs/{id}``` - update tariff by url parameter [id]
    1. Need to send DTO to request body: <br>```{
       "name":"Зарплатный тариф",
       "startDate":"2024-01-01",
       "endDate":"2024-12-31",
       "description":"Для рабочего класса",
       "rate":"3.75"
       }```
    2. Field ```description``` is optional. Other fields are required
   
<h4>Product API</h4>
<h5>Only for authorized users. Need to send JWT-token into `Authorization` header</h5>

1. ```GET /api/products``` - return all saved products
2. ```GET /api/products/{id}``` - return product by url parameter [id]
3. ```POST api/products``` - create product
   1. Need to send DTO to request body: <br> ```{
      "name":"Тестовый продукт demo",
      "type":"CARD",
      "startDate": "2024-01-01",
      "endDate": "2024-12-01",
      "tariff": {
            "id": "84735af0-e569-4d6e-8695-e9acf3518f52"
      }```
   2. Field ```type``` may be in two values: ```CARD``` and ```LOAN```
4. ```PUT api/products/{id}``` - update product by url parameter [id]
    1. Need to send DTO to request body: <br> ```{
       "name":"Тестовый продукт demo",
       "type":"CARD",
       "startDate": "2024-01-01",
       "endDate": "2024-12-01",
       "tariff": {
       "id": "84735af0-e569-4d6e-8695-e9acf3518f52"
       }```
    2. Field ```type``` may be in two values: ```CARD``` and ```LOAN```
5. ```GET /api/products/history/{id}``` - return version history of product by url parameter [id]
6. ```GET /history/{id}/by-period``` - return a version of product by date period and url parameter [id]
   1. Need to send two request parameters ```startDate``` and ```endDate``` in format ```2024-12-01```
7. ```PUT /history/{id}/rollback``` - rollback a product to some version by url parameter [id]
   1. May send the request parameter ```version```. If this parameter is null, a product rollback to the previous version

<h4>Account API</h4>

1. ```POST api/accounts/all``` - return saved accounts by filter if presents.
   If don't send FilterDto in request body, response returns all accounts.
    1. Optional send FilterDto. Example:</br>
       ```{"surname":"Ivanov", "name":"Ivan", "patronymic":null, "phone": "79000000000", "mail": null, "login":"qwerty", "password":"admin"}```
2. ```GET api/accounts/{id}``` - return account by request parameter [id].
3. ```POST api/accounts``` - create account with validation.
    1. Need to send header ```x-Source```
    2. Need to send AccountDto to request body. Example: <br>
       ```{"bankId":null, "surname":null, "name":"Maria", "patronymic":null, "birth":null, "passport":null, "birthPlace":null, "phone":"79000000000", "mail":null, "registrationAddress":"Moscow", "factAddress":null, "login":"qwerty", "password":"admin"}```

<h4>Auth API</h4>
1. ```POST /login``` - authenticate user in system. Return the JWT token
   1. Need to send form-data two fields: ```username``` and ```password```. They are ```login``` and ```password``` from ```Account API```
