# finance-tracker-backend
Backend application for financeTracker Application

# ABOUT THE APPLICATION
FinanceTracker is an application that will help regular business owners and workers to keep track of their expenditure.
The app allows you to keep track of your income, expenses, Budget and billings. It doesn't end there, it also provides real-time 
update on your finances. Some of the operations you can perform are:
1. update your income fields, expenses and budget
2. Provides graphical report of your expenses based on a certain duration say months, weeks or years
3. You can create your own personal account on the web app
4. Provides a daily article on how to manage your finances

# How To start the application from Development environment
Kindly install the current or more recent version of java sdk on your local machine. Maven was used as the build tool for this project so do well to 
install. Run mvn sprint-boot:run in your terminal from the root of your directory(ensure you are in the directory containing the maven project).
The command will start the server on http://localhost:8080.

# Current State of the Project
Web app has been deployed

# To view the source code check out the master branch

# API DOCUMENTATION
### Endpoint
- **URL**: `/api/user/register`
- **Method** : `POST`
### Description
This endpoint allows you to create an account

### Parameters
- `firstname`
- `lastname`
- `email`
- `password`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: Account created successfully

### Endpoint
- **URL**: `/api/user/login`
- **Method** : `POST`
### Description
This endpoint allows you to login into your account

### Parameters
- `email`
- `password`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: JSON representation of the user object without password

### Example Response
```json
{
    "id":23,
    "firstname":"daniel",
    "lastname":"Philips",
    "email":"daniel@gmail.com"
}
```

## Expenses Endpoint

### Endpoint
- **URL**: `/api/user/expenses/{userId}`
- **Method** : `GET`
### Description
The Endpoint retrieves all the expenses by the user with the given userId

### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: JSON representation of an expense object

### Example Response

```json
[
    {
        "id":30,
        "amount":405.40,
        "source":"Payment of academic fees",
        "date":20/03/2022
    }
]
```

### Endpoint
- **URL**: `/api/user/expenses`
- **Method** : `POST`
### Description
This endpoint allows you to add a new expense

### Request Parameters
- `userId`
- `amount`
- `date`
- `source`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: Expense added

### Endpoint
- **URL**: `/api/user/expenses`
- **Method** : `DELETE`
### Description
This endpoint allows you delete an expense

### Request Parameters
- `expenseId`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: text

### Endpoint
- **URL**: `/api/user/expenses/update-amount`
- **Method** : `PUT`
### Description
This endpoint allows you to update the amount of a particular expenditure

###  Request Parameters
- `id`
- `amount`

### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: Amount updated successfully

### Endpoint
- **URL**: `/api/user/expenses/update-source`
- **Method** : `POST`
### Description
This endpoint allows you to create an account

### Parameters
- `id`
- `source`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: 

## Income Endpoints

### Endpoint
- **URL**: `/api/user/income`
- **Method** : `POST`
### Description
This endpoint allows you to add a new source of income

### Request Parameters
- `userId`
- `amount`
- `source`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: New Income added successfully

### Endpoint
- **URL**: `/api/user/income`
- **Method** : `GET`
### Description
This endpoint allows you to retrieve all the sources of income by a user

###  Request Parameters
- `userId`

### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: JSON representation of an array of income objects

### Example Response
```json
[
    {
        "id":23,
        "source":"Salary",
        "amount":900.00
    }
]
```

### Endpoint
- **URL**: `/api/user/income`
- **Method** : `DELETE`
### Description
This endpoint allows you delete an income

### Request Parameters
- `incomeId`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: 

### Endpoint
- **URL**: `/api/user/income/update-amount`
- **Method** : `PUT`
### Description
This endpoint allows you to update the amount you take as income from a particular source say salary

### Parameters
- `incomeId`
- `amount`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: Amount update successful

### Endpoint
- **URL**: `/api/user/income/update-source`
- **Method** : `PUT`
### Description
This endpoint allows you to change the source of income provided the name of the company changes

### Parameters
- `source`
- `incomeId`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: Income source update successful

## Transactions

### Endpoint
- **URL**: `/api/user/transaction`
- **Method** : `POST`
### Description
This endpoint allows you to add a new transaction

### Request Parameters
- `userId`
- `amount`
- `personReceived`
- `description`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: Transaction added

### Endpoint
- **URL**: `/api/user/transaction`
- **Method** : `GET`
### Description
This endpoint allows you to retreive transactions by a user with the given userId

### Request Parameters
- `userId`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: JSON representation of an array of transaction object

### Example Response
```json
[
    {
        "id":90,
        "amount":392,
        "personReceived":"Kwame Sarpong",
        "description":"The transaction was made to purchase a pair of trousers"
    }
]
```
### Endpoint
- **URL**: `/api/user/transaction`
- **Method** : `DELETE`
### Description
This endpoint allows you to delete a transaction 

### Request Parameters
- `transactionId`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: 


### Endpoint
- **URL**: `/api/user/transaction/update-receiver`
- **Method** : `PUT`
### Description
This endpoint allows you to change say the name of the receiver

### Parameters
- `transactionId`
- `personReceived`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: 

### Endpoint
- **URL**: `/api/user/transaction/update-amount`
- **Method** : `PUT`
### Description
This endpoint allows you to change or update the amount transacted

### Request Parameters
- `transactionId`
- `amount`
### Response
- **HTTP Status Code**: 200 (OK)
- **Body**: transaction amount updated successfully


# Link To the Project
https://fintracker-znxn.onrender.com

# Contributions
Contributions on this project are warmly welcomed. You can reach out to the developer at:
1. Email : acquahfrank25@gmail.com  