# Area Api

## I. Users

### 1) Get a specific user

```GET {url}/api/Users/{id}```

**Url Params:**
* id:long

**Return type: User**

Returns a User object.

### 2) Get all users

```GET {url}/api/Users/```

**Return type: List<User>**

Returns a list containing users

### 3) Register a user

```POST {url}/api/Users/```

**Params:**
* Email:string
* Name:string
* Password:string 

**Return type: User**

Returns the created user object if it succeed.

### 4) Login a user

```POST {url}/api/Users/login```

**Url Params:**
* Name:string
* Password:string 

**Return type: User**

Returns the user object of user who logged in, if it succeed.

### 5) Logout a user

```GET {url}/api/Users/logout```

**Return type: None**

### 6) Update a user

```PUT {url}/api/Users/{id}```
**Url Params:**
* Id:string

**Params:**
* Name:string
* Password:string 

**Return type: User**

Returns the user object of user who logged in, if it succeed.

### 7) Delete a user

```DELETE {url}/api/Users/{id}```

**Url Params:**
* id:long

**Return type: User**

Returns the deleted user object if it succeed.


## II. Services

### 1) Get a specific service

```GET {url}/api/Services/{id}```

**Params:**
* id:long

**Return type: Service**

Returns a Service object.

### 2) Get all services

```GET {url}/api/Services/```

**Return type: List<Service>**

Returns a list containing all services


## III. RegisteredServices

### 1) Get the registered services of the logged user

```GET {url}/api/RegisteredServices```

**Return type: List<RegisteredService>**

Returns the list of registered services of a user.

### 2) Register the logged user to a new service

```POST {url}/api/RegisteredServices/```

**Params**
* Name:string = Name of the service which the user wants to register.
* AccessToken:string = Access Token of the service's API.
* (optionnal) RefreshToken:string = Refresh Token of the service's API.

**Return type: List<RegisteredService>**

Returns the subscribed registered service.

### 3) Unregister the logged user to a new service

```DELETE {url}/api/RegisteredServices/{serviceName}/```

**Url Params:**
* serviceName:string = Name of the service which the user wants to unregister

**Return type: RegisteredService**

Returns the unsuscribed registered service.


## IV. Actions (AREAs)

### 1) Get the registered AREA of the logged user

```GET {url}/api/Actions```

**Return type: List<Actions>**

Returns the list of registered actions of a user.

### 2) Register the logged user to a new AREA

```POST {url}/api/Actions/```

**Params**
* Name:string = Name of the action the user wants to register.
* Description:string = Description of the action the user wants to register.
* Reaction:Reaction = Reaction which will be triggered by the action which the user wants to register.

**Return type: List<Action>**

Returns the list of AREAs the user registered.

### 3) Unregister the logged user to a new service

```DELETE {url}/api/RegisteredServices/{serviceName}/```

**Url Params:**
* serviceName:string = Name of the service which the user wants to unregister

**Return type: RegisteredService**

Returns the unsuscribed registered service.


## V. Objects

### 1) User object

* Id:long
* Name:string
* Email:string
* Password:string
* RegisteredServices:List<RegisteredService>
* RegisteredActions:List<Action>

### 2) Service object

* Name:string
* Actions:List<Action>
* Reactions:List<Reaction>

### 3) Registered Service object (extends from Service)

* AccessToken:string
* RefreshToken:string

### 4) Action object

* Name:string
* Description:string
* Reaction:Reaction

### 5) Reaction object

* Name:string
* Description:string
* Actions:List<string>
* Params:List<string>