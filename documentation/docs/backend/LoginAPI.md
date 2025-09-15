Login API:
Enables users to login and access the system. Only registered users, employee and admin, can log in.
Users must provide a username and password to log in. Failed logins return error messages.

Endpoint:
POST https://website.com/api/user/login

Request:
	{
 	 "username": "ExampleUser",
 	 "password": "SecurePassword#123"
    }
Required fields:
Username (string)
Password (string)

Response:
    Success: 200 Ok
    {
        message: "Login successful",
        "user": 
            {
            "user_id": 21,
            "role_id": 2,
            "role_name": "employee"
            }
    }
    
    Error: 401 Unauthorized
    {
    "error": "Invalid username or password"
    }

    Error 403 Forbidden
    {
    "error": "Account disabled. Please contact admin."
    }

    Error 404
    {
    "error": "Account not found."
    }

    Error 500
    {
    "error": "An unexpected error has occurred."
    }
