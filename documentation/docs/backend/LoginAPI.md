# Login API:
Enables users to log in and out of the system. 

---

### Login Function:
Users must provide a username and password to log in. Failed logins return error messages. Only registered users, employee and admin, can log in.

```javascript
    POST https://website.com/api/user/login
```

```javascript
    Request:
	{
 	    "username": "ExampleUser",
 	    "password": "SecurePassword#123"
    }

```
Required fields:
- Username (string)
- Password (string)

```javascript
    Response:
        Success: 200 Ok
        {
            message: "Logout successful",
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
```

---

### Logout Function:
Allows a logged-in user to log out of the system. Once logged out, the user will no longer have access until they log in again.

```javascript
    POST https://website.com/api/user/logout
```

```javascript
    Request:
	{
 	    "username": "ExampleUser",
    }
```
Required fields:
- Username (string)

```javascript
    Response:
        Success: 200 Ok
        {
            message: "Logout successful",
        }
        
        Error: 401 Unauthorized
        {
            "error": "User not logged in"
        }

        Error 500
        {
            "error": "An unexpected error has occurred."
        }
```