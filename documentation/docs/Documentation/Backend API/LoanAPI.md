# Loan API 

The Loan API manages the lending and returning of devices within the system.  

It tracks loan details such as start date, due date, and condition status while ensuring accurate recordkeeping of all borrowed items.  

Only authorized users can create, update, or delete loan records.  

This API ensures accountability and maintains a clear history of each device loan.

---

## Create Loan
This function allows an authorized user to create a new loan record in the system.  
```
POST /api/loans
```
**Request:**
```javascript
{
  "citizen_id": number,
  "employee_id": number,
  "device_id": number,
  "status_id": number,
  "start_at": "YYYY-MM-DD",
  "due_at": "YYYY-MM-DD",
  "loan_condition": number,
  "loan_condition_notes": string,
  "all_accessories_returned": boolean,
  "missing_accessories": string,
  "notes": string
}
```
**Request Example:**
```javascript
{
  "citizen_id": 101,
  "employee_id": 3,
  "device_id": 22,
  "status_id": 1,
  "start_at": "2025-09-14",
  "due_at": "2025-09-21",
  "loan_condition": 2,
  "loan_condition_notes": "Minor scratch on front right side of device",
  "all_accessories_returned": true,
  "missing_accessories": null,
  "notes": "DL was verified before checkout"
}
```
**Response:**
```javascript
success: 201 created
{
  "status": 201,
  "message": "Loan created successfully"
}
```
## Get All Loans
This function allows an authorized user to retrieve every loan record in the system.    
```
GET /api/loans
```

**Response:** 
```javascript
"success" 200 OK
{
  "status": 200,
  "message": "Loans retrieved successfully"
}
```

## Get Loan by ID
This function allows an authorized user to retrieve a specific loan record by its ID number.  
**GET** /api/loans{loan_id}

**Response:**
```javascript
success: 200 OK
{
  "status": 200,
  "message": "Loan retrieved successfully"
}
```
## Replace Loan
This function allows an authorized user to replace all details of an existing loan record.  
```
PUT /api/loans/{loan_id}
```

**Request:**
```javascript
{
  "citizen_id": number,
  "employee_id": number,
  "device_id": number,
  "status_id": number,
  "start_at": "YYYY-MM-DD",
  "due_at": "YYYY-MM-DD",
  "loan_condition": number,
  "loan_condition_notes": string,
  "return_condition": number,
  "return_condition_notes": string,
  "damage_fee": number,
  "all_accessories_returned": boolean,
  "missing_accessories": string,
  "notes": string
}
```

**Request Example:**
```javascript
{
  "citizen_id": 101,
  "employee_id": 3,
  "device_id": 22,
  "status_id": 2,
  "start_at": "2025-09-14",
  "due_at": "2025-09-21",
  "loan_condition": 3,
  "loan_condition_notes": "Updated condition details",
  "return_condition": null,
  "return_condition_notes": null,
  "damage_fee": null,
  "all_accessories_returned": false,
  "missing_accessories": "Charger missing",
  "notes": "Updated loan information"
}
```
**Response:**
```javascript
success: 200 OK
{
  "status": 200,
  "message": "Loan replaced successfully"
}
```
## Update Loan
This function allows an authorized user to update or more fields of a loan record.  
```
PATCH /api/loans/{loan_id}
```
**Request:**
```javascript
{
  "returned_at": "YYYY-MM-DD",
  "return_condition": number,
  "return_condition_notes": string,
  "damage_fee": number,
  "all_accessories_returned": boolean,
  "missing_accessories": string,
  "notes": string
}
```
**Request Example:**
```javascript
{
  "returned_at": "2025-09-20",
  "return_condition": 1,
  "return_condition_notes": "Returned in excellent condition",
  "all_accessories_returned": true,
  "notes": "Returned early with no signs of damage"
}
```
**Response:**
```javascript
success: 200 OK
{
  "status": 200,
  "message": "Loan updated successfully"
}
```

## Delete Loan
This function allows an authorized user to delete a loan record from the system.
```
DELETE /api/loans/{loan_id}
```
**Response**
```javascript
success: 204 No Content
{
  "status": 204,
  "message": "Loan deleted successfully"
}
```

