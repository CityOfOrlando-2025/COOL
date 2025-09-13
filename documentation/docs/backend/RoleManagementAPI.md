#  Role Management API Function: 
When a new user registers for an account, their role_name is automatically set to "employee." 

Only users with the admin role have permission to change another user's role_name using the user's role_id number. 
- role_id
- role_name 

---

### List All Roles 
This function allows the admin to retrieve every role available in the system. 
<br>

        GET https://Website.com/api/user_role
```
    Response:
    success: 200 OK
        {
            status: 200,
            message: "User roles retrieved successfully"
            user_role: [
                {
                    role_id: int,
                    role_name: string
                },
                {
                    role_id: int,
                    role_name: string
                }
                {
                    role_id: int,
                    role_name: string
                }

            ]
        }

    error: 403 Forbidden
    {
        error: "Access denied. You do not have permission to view roles."
    }    

    error: 500 Internal Server Error
    {
        error: "An unexpected error has occurred."
    }
```
---
### Update User Role
This function allows an admin to update the role properties in the system.
<br>

     PATCH https://website.com/api/user_role

```
    Request:
    {
        role_id: int,
        role_name: string
    }
```
```
    Response:
        success: 200 OK
        {
            role_id: int,
            role_name: string,
        }    

        error: 403 Forbidden
        {
            error: "Unauthorized to update role."
        }

        error: 404 Not Found
        {
            error: "User not found"
        }

```
---
### Create Role Funtionality
This function lets the admin add a new role to the system. 

    POST https://website. com/api/user_role


```
    Request:
    {
        role_name: string
        dl_required: boolean
    }
```
```
    Response: 
        success: 201 Created
        {
            role_id: int
            role_name: string
            dl_required: boolean
        }
    
        error: 400 Bad Request
        {
            error:"Invaild data provided."
        }
    
        error: 403 Forbidden
        {     
            error: "Unauthorized to create role."
        }

        error: 409 Conflit
        {
            error: "Role already exists."
        }


```

---
### Delete Role Funtionality 
This function allows the admin to delete roles in the system. 

<u>**NOTE**: Core Roles ("Admin", and "Employee") cannot be deleted.</u>

        DELETE https://website. com/api/user_role

```
    Request:
    {
        role_name: string
    }
```
```
    Response: 
    success: 200 OK
    {
        status:200
        message: "Role deleted successfuly"
    }

    error: 403 Forbidden 
    {
        error: "Unauthorized to delete role."
    }

    error: 404 Not Found
    {
        error: Role not found." 
    }

    error: 409 Conflict
    {
        error: "Core roles cannot be deleted."
    }
```
