BOOK APP - USER PROFILE BACKEND

This folder contains the User Profile CRUD classes:
- User.java: entity with id, firstName, lastName, email
- UserRepository.java: database access
- UserService.java: business logic
- UserController.java: REST endpoints

HOW TO OPEN IN VISUAL STUDIO CODE
1. Extract the downloaded ZIP file.
2. Open Visual Studio Code.
3. Choose File > Open Folder.
4. Select the extracted 'bookapp-user-profile' folder.
5. Copy the four Java packages into the matching packages in your Spring Boot project.

IMPORTANT
The package name used here is com.bookapp. If your existing project uses a different
base package, change the package declarations and imports to match your project.
This code expects Spring Boot with Spring Web, Spring Data JPA, and a configured
PostgreSQL datasource. It is a code module, not a complete runnable Spring Boot project.

API ENDPOINTS
POST   /api/users       Create a user
GET    /api/users       List users
GET    /api/users/{id}  Get a user by ID
PUT    /api/users/{id}  Update a user
DELETE /api/users/{id}  Delete a user

Example POST body:
{
  "firstName": "Julio",
  "lastName": "Becerra",
  "email": "julio@email.com"
}
