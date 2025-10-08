# Device API

The device CRUD is made for the purpose of managing devices in the overall system. It allows for the system to perform all basic functions towards device objects, each containing a type and status alongside an ID so the system can differentiate each one. Most of it is not supposed to be accessed by average users, only employees will have direct access to manage the device database directly.

### Background Information

The device objects have three parts to them, an ID, a status, and a type. The ID is there so that the system can differentiate between each device, it is the only unique part of each device. Conversely status is used to determine whether the device can be loaded out, isn’t able to be loaned out, or already loaned out. And type is used to describe what the device is, but unlike ID can be the same as any other device and is not used outside of information. Users will not directly be able to affect any of these, with ID and type being locked behind the system not for them to touch, the only one they will be able to affect indirectly is status, being able to change the status of them by withdrawing or returning.

The main interactions will be done through a GUI, it will feature all devices in the device database, detailing their ID, status, and type. The actions users can take change depending on the status of the device.

If the status is available there will be a button that allows them to take out a device, setting the status to lent out. If the status is unavailable or lent out, the button will not allow for it to be taken out.

Additionally full CRUD functionality has been programmed, allowing for more direct modifications of the status and type if needed.

### Read Devices

This function returns all devices in the database.

```
GET /api/my-devices/device
```

**Response**

Status: 200 OK

```javascript
[

  {
  
    "deviceId": 0,
    "status": "active",
    "type": "basic"
  
  },
  
  {
  
    "deviceId": 2,
    "status": null,
    "type": null
  
  },
  
  {
  
    "deviceId": 52,
    "status": "Update",
    "type": "Testing"
  
  },
  
  {
  
    "deviceId": 102,
    "status": null,
    "type": null
  
  },
  
  {
  
    "deviceId": 202,
    "status": "test2",
    "type": "type2"
  
  }

]
```

Status: 500 Internal Server Error

```javascript
{
  "error": "Internal Server Error"
}
```

### Create Device

Adds a device to the database

```
POST /api/my-devices/device
```

**Request**

```javascript
{

  "deviceId": number,
  "status": string,
  "type": string

}
```

```javascript
{

  "deviceId": 252,
  "status": "Lent",
  "type": "Laptop"

}
```

**Response**

Status: 200 OK

```javascript
{

  "deviceId": 252,
  "status": "Lent",
  "type": "Laptop"

}
```

Status: 400 Bad Request

```javascript
{
  "error": "Bad Request"
}
```

### Update Device

Allows the user to change a device’s type and status.

```
PUT /api/my-devices/device/{deviceId}
```

**Request**
```javascript
{

  "deviceId": number,
  "status": string,
  "type": string

}
```

```javascript
{

  "deviceId": 2,
  "status": "Ready",
  "type": "Phone"

}
```

**Response**

Status: 200 OK

```javascript
{

  "deviceId": 2,
  "status": "Ready",
  "type": "Phone"

}
```

Status: 400 Bad Request

```javascript
{
  "error": "Bad Request"
}
```

### Delete Device

Allows for the deletion of a device from the database.

```
DELETE /api/my-devices/device/{deviceId}
```

**Response**

Status: 200 OK

Deleted Successfully

Status: 404 Not Found

```javascript
{
  "error": "Not Found"
}
```
