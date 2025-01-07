# Ticket Event Manager

## 📚 Project Description

Ticket Event Manager is a microservices-based system designed to manage event tickets efficiently. The project consists of two independent microservices:
- **Event Manager**: Manages event data, including creating and retrieving event details.
- **Ticket Manager**: Handles ticket operations, ensuring tickets are associated with existing events.

This project was built as part of a learning exercise to integrate microservices, external APIs, and databases.

---

## 🔧 Architecture Overview

### **Event Manager Service** (`Port 8082`):
- Manages events (create, update, retrieve, delete).
- Consumes external APIs (e.g., ViaCep) to validate address details during event creation.
- Stores event data in MongoDB.

### **Ticket Manager Service** (`Port 8081`):
- Manages tickets and ensures they are tied to valid events.
- Communicates with the Event Manager service using **OpenFeign**.
- Stores ticket data in MongoDB.

---

## ⚖️ Features

### Event Manager:
- **Create Events**: Add new events with validated address information.
- **Retrieve Events**: Fetch event details by ID or list all events.
- **Retrieve Sorted Events**: Fetch all events sorted by date.
- **Update Events**: Modify existing event details.
- **Delete Events**: Soft-delete events (mark as `deleted: true`).

### Ticket Manager:
- **Create Tickets**: Add tickets linked to existing events.
- **Retrieve Tickets**: Fetch ticket details by ID or list all tickets.
- **Retrieve Tickets by CPF**: List all tickets associated with a specific CPF.
- **Update Tickets**: Modify existing ticket details.
- **Delete Tickets**: Cancel tickets by ID.

---

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.4.0**
- **Spring Cloud OpenFeign**
- **MongoDB**
- **Docker**
- **RabbitMQ (CloudAMQP)**
- **ViaCep API**
- **Maven**
- **REST API**

---

## 🔧 Setup Instructions

### Prerequisites:
1. **Docker** and **Docker Compose** installed.
2. Access to a MongoDB Atlas cluster or local MongoDB instance.
3. CloudAMQP account credentials.
4. Update email credentials for notification functionality in the application configuration.

### Steps:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/<your-repository>/ticket-event-manager.git
   cd ticket-event-manager
   ```

2. **Build the Microservices**:
   ```bash
   cd ms-event-manager
   mvn clean install
   cd ../ms-ticket-manager
   cd ms -ticket0manager
   mvn clean install
   cd ..
   ```

3. **Configure Environment Variables**:
   - Update `application.properties` or `.env` files for:
     - **MongoDB Atlas**: Provide database URI, create a event_ticket_db and 2 collections(tickets and events).
     - **CloudAMQP**: Add your RabbitMQ username and password.
     - **Email Credentials**: Provide Gmail username and password for ticket notifications.
       

4. **Build and Start Services**:
   ```bash
   docker-compose up --build -d
   ```

5. **Verify Running Containers**:
   ```bash
   docker ps
   ```
   Expected output should show containers for:
   - Event Manager Service (Port 8082)
   - Ticket Manager Service (Port 8081)
   - MongoDB (Port 27017)

6. **Configure EC2 (if applicable)**:
   - Use the public IP address of the EC2 instance instead of `localhost` for service URLs.

---

## 🌐 API Endpoints

### Event Manager:
| Method | Endpoint                      | Description              |
|--------|-------------------------------|--------------------------|
| POST   | `/eventmanagement/v1/create-event` | Create a new event       |
| GET    | `/eventmanagement/v1/get-event`    | Retrieve all events      |
| GET    | `/eventmanagement/v1/get-event/{id}` | Retrieve event by ID     |
| GET    | `/eventmanagement/v1/get-all-events/sorted` | Retrieve all events sorted |
| PUT    | `/eventmanagement/v1/update-event/{id}` | Update an event          |
| DELETE | `/eventmanagement/v1/delete-event/{id}` | Soft-delete an event     |

### Ticket Manager:
| Method | Endpoint                      | Description              |
|--------|-------------------------------|--------------------------|
| POST   | `/ticketmanagement/v1/create-ticket` | Create a new ticket      |
| GET    | `/ticketmanagement/v1/get-ticket/{ticketId}` | Retrieve ticket by ID    |
| GET    | `/ticketmanagement/v1/list-tickets-by-cpf/{cpf}` | Retrieve tickets by CPF |
| PUT    | `/ticketmanagement/v1/update-ticket/{id}` | Update a ticket          |
| DELETE | `/ticketmanagement/v1/cancel-ticket/{id}` | Cancel a ticket          |

---

## 🛠️ Testing the Application

1. **Access the Services**:
   - **Event Manager**: `http://<EC2_PUBLIC_IP or LocalHost >:8082`
   - **Ticket Manager**: `http://<EC2_PUBLIC_IP or LocalHost>:8081`

2. **Test Endpoints with cURL**:

   - **Create an Event**:
     ```bash
     curl -X POST http://<EC2_PUBLIC_IP>:8082/eventmanagement/v1/create-event \
     -H "Content-Type: application/json" \
     -d '{
       "eventName": "Concert",
       "dateTime": "2025-12-31T20:00:00",
       "cep": "01001-000"
     }'
     ```

   - **Create a Ticket**:
     ```bash
     curl -X POST http://<EC2_PUBLIC_IP>:8081/ticketmanagement/v1/create-ticket \
     -H "Content-Type: application/json" \
     -d '{
      "customerName": "Johnnei Joee",
      "cpf": "253454389433",
      "customerMail": "johndoe@example.com.br",
      "id": "676ef2d97927b8034dbda9ef",
      "eventName": "Event Example",
      "brlAmount": 50.00,
      "usdAmount": 10.00
       }
     }'
     ```

3. **Test with Postman**:
   Import the API endpoints and interact with the application visually.

---

## 🎮 Future Improvements

1. Implement user authentication (e.g., JWT).
2. Add support for event categories and filtering.
3. Improve error handling and validation messages.
4. Enhance testing with integration and performance tests.
5. Add real-time notifications for ticket availability.
6. Swagger Documentation.
7. Get All Tickets by an Event.
8. Sequencial and numerical Id for Ticket and Event.

---

## 📖 Documentation and References

- **MongoDB Atlas Documentation**: [https://www.mongodb.com/docs/atlas/](https://www.mongodb.com/docs/atlas/)
- **Spring Boot Documentation**: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
- **OpenFeign Documentation**: [https://spring.io/projects/spring-cloud-openfeign](https://spring.io/projects/spring-cloud-openfeign)
- **ViaCep API**: [https://viacep.com.br/](https://viacep.com.br/)


