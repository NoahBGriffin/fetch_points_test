# fetch_points_test_Noah-Griffin
Coding Exercise - backend software engineering
REST API web service for tracking a specific user's rewards points balance. Accepts HTTP request formatted in JSON. Implemented with Spring Boot in Java

## ASSUMPTIONS
- Original transaction data should not be modified once it is added to memory
- Clients can add negative point values using /add-transaction, but point balances for each payer can not be made negative
using this method
- Clients cannot spend negative points when using /spend-points
- Clients can add transactions at any point regardless of timestamp. Ie transactions are not necessarily sent in correct timestamp order

## REST API

#### 1) /add-transaction:
<b>POST</b>  
Accepts POSTS request to add transaction, echoes back transaction  
    format: { "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00" }  
    return: { "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00" }   
    ResponseCode: 201 on success  
    
#### 2) /spend-points:
<b>PUT</b>  
Accepts PUT request and responds with the name of each payer who originally paid points to the user account
and the amount of points spent from each. Points are spent based on chronological order of when they were earned, with points 
earned earliest being spent first (based on submitted timestamp).  
   format: { "points": 100 }  
   return: { "payer": "DANNON", "points": -100 }  
   ResponseCode: 202 on success  
   
#### 3) /point-balance:
<b>GET</b>  
Accepts GET request and returns the user's current point balance, separated by payer.  
    return: { "NOAH": 2000, "DANNON": 600 }  
    ResponseCode: 200 on success  
   
## How to Run
run BackendCodingExerciseApplication.java

## Sample Commands

ADD POINTS: { "payer": "DANNON", "points": 100, "timestamp": "2022-02-01T14:00:00" } 
http://localhost:8080/add-transaction

POINT BALANCE: http://localhost:8080/point-balance

SPEND POINTS: { "points": 200 } http://localhost:8080/spend-points


