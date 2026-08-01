# System Architecture

# NexBuddy

**Version:** 1.0

**Project:** NexBuddy

**Project Type:** Social Networking & Activity Matching Platform

**Prepared By:** Ajesh CV


---

# Revision History

| Version | Date | Description | Author |
|----------|------------|---------------------------|------------|
| 1.0 | July 2026 | Initial Architecture Document | Ajesh CV |

---

# Table of Contents

1. Introduction
2. Architecture Objectives
3. System Overview
4. Architectural Style
5. High-Level Architecture
6. Technology Stack
7. Architectural Principles

---

# 1. Introduction

## 1.1 Purpose

This document describes the overall architecture of the NexBuddy platform. It explains how different components of the system interact to provide a scalable, secure, and maintainable social networking platform.

The architecture document serves as a technical blueprint for developers during implementation and future maintenance.

---

## 1.2 Scope

This architecture covers:

- Web Application
- Mobile Application
- Backend Services
- Database Design
- Authentication
- Real-Time Communication
- Notifications
- Cloud Storage
- External APIs

---

## 1.3 Objectives

The architecture is designed to achieve the following objectives:

- High Scalability
- High Availability
- Easy Maintenance
- Strong Security
- Modular Design
- Code Reusability
- Fast Performance
- Easy Deployment
- Future Expansion

---

# 2. Architecture Objectives

The NexBuddy architecture follows modern software engineering principles.

The primary goals include:

## Scalability

The architecture should support thousands of concurrent users without significant performance degradation.

---

## Maintainability

The project is divided into independent modules, allowing developers to modify one component without affecting others.

---

## Security

The architecture includes multiple security layers including:

- JWT Authentication
- HTTPS Communication
- Password Encryption
- Role-Based Authorization

---

## Performance

The system minimizes response times by using optimized database queries, efficient API design, and asynchronous communication where appropriate.

---

## Flexibility

Future services such as AI recommendations, voice calls, premium subscriptions, and additional activity categories can be integrated without major architectural changes.

---

# 3. System Overview

NexBuddy is designed as a multi-platform system.

Users can access the platform using:

- Mobile Application
- Web Application

Both clients communicate with a centralized backend using RESTful APIs.

The backend manages:

- Authentication
- User Profiles
- Buddy Matching
- Communities
- Events
- Chat
- Notifications
- Database Operations

External services are integrated for maps, cloud storage, and push notifications.

---

# 4. High-Level System Architecture

```text
                    +----------------------+
                    |      End Users       |
                    +----------+-----------+
                               |
               +---------------+---------------+
               |                               |
               ▼                               ▼
     +------------------+             +------------------+
     | Flutter App      |             | React Web App    |
     +--------+---------+             +--------+---------+
              |                                |
              +---------------+----------------+
                              |
                              ▼
                  REST API (HTTPS + JSON)
                              |
                              ▼
               +------------------------------+
               | Spring Boot Backend Server   |
               +------------------------------+
                |      |       |       |      |
                |      |       |       |      |
                ▼      ▼       ▼       ▼      ▼
        Authentication  Buddy   Events  Chat  Notifications
                |
                ▼
         PostgreSQL Database
                |
        +-------+--------+
        |                |
        ▼                ▼
 Cloudinary       Firebase Cloud Messaging
(Image Storage)     (Push Notifications)

                Future Expansion
                     |
                     ▼
          AI Recommendation Service
```

---

# Architecture Explanation

### Client Layer

The client layer consists of:

- Flutter Mobile Application
- React Web Application

Both provide similar functionality while adapting the interface to their respective platforms.

---

### Communication Layer

Communication between client and server uses:

- HTTPS
- REST APIs
- JSON

For real-time messaging:

- WebSocket

---

### Application Layer

The Spring Boot backend acts as the central processing unit of the platform.

Responsibilities include:

- Authentication
- Authorization
- Business Logic
- API Processing
- Data Validation
- Database Communication

---

### Data Layer

PostgreSQL stores all persistent information including:

- Users
- Communities
- Events
- Messages
- Notifications
- Reports

---

### External Services

The system integrates with:

Cloudinary

- Profile Images
- Community Images
- Event Images
- Post Images

Google Maps API

- Nearby Users
- Nearby Events
- Location Selection

Firebase Cloud Messaging

- Push Notifications
- Event Reminders
- Chat Alerts

---

# 5. Architectural Style

The NexBuddy platform combines multiple architectural patterns to improve maintainability, scalability, and performance.

---

## 5.1 Client-Server Architecture

The platform follows the Client-Server model.

Clients:

- Flutter
- React

Server:

- Spring Boot

The clients send requests to the server.

The server processes requests and returns JSON responses.

Benefits:

- Centralized business logic
- Better security
- Easy maintenance

---

## 5.2 Layered Architecture

The backend follows a layered architecture.

```text
Presentation Layer
        │
        ▼
Controller Layer
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
Database Layer
```

Advantages:

- Easy debugging
- Modular code
- Better maintainability
- Independent testing

---

## 5.3 RESTful Architecture

Communication uses REST APIs.

Examples:

```
GET    /api/users

POST   /api/login

POST   /api/events

GET    /api/events

PUT    /api/profile

DELETE /api/community/{id}
```

Benefits:

- Platform independent
- Easy frontend integration
- Lightweight communication

---

## 5.4 MVC Pattern

Spring Boot follows the MVC architecture.

```text
User

↓

Controller

↓

Service

↓

Repository

↓

Database
```

Components:

Model

Represents database entities.

View

Provided by Flutter and React.

Controller

Handles incoming HTTP requests.

---

## 5.5 Component-Based Architecture

React and Flutter applications are built using reusable UI components.

Examples:

- Navigation Bar
- Cards
- Chat Widget
- Event Card
- User Card
- Notification Tile

Benefits:

- Reusability
- Faster development
- Cleaner code

---

# 6. Technology Stack

## Frontend

- React.js
- HTML5
- CSS3
- JavaScript
- Tailwind CSS

---

## Mobile

- Flutter
- Dart

---

## Backend

- Spring Boot
- Spring Security
- Spring Data JPA
- REST API
- WebSocket

---

## Database

- PostgreSQL

---

## Authentication

- JWT

- BCrypt

---

## Storage

- Cloudinary

---

## Notifications

- Firebase Cloud Messaging

---

## Maps

- Google Maps API

---

## Development Tools

- IntelliJ IDEA
- VS Code
- Android Studio
- Postman
- Git
- GitHub

---

# 7. Architectural Principles

The design of NexBuddy follows these principles:

## Separation of Concerns

Each module has a specific responsibility.

Examples:

Authentication

↓

User Management

↓

Events

↓

Communities

↓

Messaging

---

## Loose Coupling

Modules communicate through interfaces and APIs, minimizing dependencies between components.

---

## High Cohesion

Related functionalities are grouped together to simplify development and maintenance.

---

## Reusability

Common services, utilities, and UI components are designed to be reused across different modules.

---

## Security by Design

Security considerations are incorporated into every layer of the system, including authentication, authorization, data validation, and encrypted communication.

---

## Scalability

The architecture allows future expansion by adding new services or modules without redesigning the existing system.

---

# 8. Layered Architecture

The NexBuddy backend follows a **five-layer architecture**, where each layer has a specific responsibility. This separation improves maintainability, scalability, and testability.

```text
                    Client Layer
          (Flutter / React / Admin)

                     │
                     ▼

             Controller Layer
        (REST API Endpoints)

                     │
                     ▼

              Service Layer
      (Business Logic & Validation)

                     │
                     ▼

            Repository Layer
        (Spring Data JPA Repositories)

                     │
                     ▼

             PostgreSQL Database
```

---

## 8.1 Presentation Layer

The Presentation Layer is responsible for user interaction.

### Components

### Flutter Mobile App

Responsibilities

- Login
- Registration
- User Dashboard
- Chat
- Communities
- Events
- Notifications

---

### React Web Application

Responsibilities

- Landing Page
- User Dashboard
- Community Management
- Event Management
- Settings

---

### Admin Dashboard

Responsibilities

- User Management
- Reports
- Analytics
- Categories
- Moderation

---

## 8.2 Controller Layer

The Controller Layer receives HTTP requests from clients.

Responsibilities

- Receive Requests
- Validate Request Format
- Forward Requests
- Return HTTP Responses

Example

```text
POST /api/auth/login

↓

AuthenticationController

↓

AuthenticationService
```

---

## 8.3 Service Layer

This layer contains the complete business logic.

Examples

Authentication Service

- Login
- Register
- JWT Generation

User Service

- Update Profile
- Search Users

Buddy Service

- Matching Algorithm
- Friend Requests

Community Service

- Create Community
- Join Community

Event Service

- Create Event
- Join Event

Notification Service

- Send Notifications

---

## 8.4 Repository Layer

Repositories communicate directly with PostgreSQL.

Examples

UserRepository

CommunityRepository

EventRepository

ChatRepository

NotificationRepository

Each repository performs CRUD operations.

---

## 8.5 Database Layer

Stores all application data.

Examples

- Users
- Profiles
- Interests
- Events
- Communities
- Messages
- Notifications
- Reports

---

# 9. Component Architecture

The application consists of multiple independent components.

```text
                    NexBuddy

                         │

 ┌───────────────┬───────────────┬───────────────┐
 │               │               │               │
 ▼               ▼               ▼               ▼
Authentication   User       Community       Event

 │               │               │               │

 ├───────────────┼───────────────┼───────────────┤

 ▼               ▼               ▼               ▼

Chat        Notification    Buddy Match    Admin

                 │
                 ▼

             PostgreSQL
```

Each component performs a dedicated task while communicating through service interfaces.

---

# 10. Module Architecture

The system is divided into functional modules.

---

## Authentication Module

Responsibilities

- Register
- Login
- Logout
- Forgot Password
- Email Verification

Uses

- JWT
- BCrypt
- Spring Security

---

## User Module

Responsibilities

- Profile
- Interests
- Friends
- Settings
- Privacy

---

## Buddy Module

Responsibilities

- Buddy Requests
- Friend Suggestions
- Nearby Users
- Activity Matching

---

## Community Module

Responsibilities

- Community Creation
- Member Management
- Posts
- Discussions

---

## Event Module

Responsibilities

- Event Creation
- Event Registration
- Event Chat
- Invitations

---

## Chat Module

Responsibilities

- Private Chat
- Group Chat
- Media Sharing
- Read Receipts

Uses

WebSocket

---

## Notification Module

Responsibilities

- Push Notifications
- In-App Notifications
- Event Alerts
- Chat Alerts

---

## Admin Module

Responsibilities

- Reports
- User Management
- Community Moderation
- Analytics

---

# 11. Backend Architecture

The backend follows the MVC pattern with Spring Boot.

```text
Controller

↓

Service

↓

Repository

↓

Database
```

---

## Backend Package Structure

```text
Backend

│

├── config/

├── controller/

├── dto/

├── entity/

├── repository/

├── security/

├── service/

├── websocket/

├── exception/

├── util/

└── NexBuddyApplication.java
```

---

## Controller Layer

Receives HTTP requests.

Examples

AuthenticationController

UserController

CommunityController

EventController

ChatController

---

## Service Layer

Contains business logic.

Examples

AuthenticationService

EventService

ChatService

NotificationService

---

## Repository Layer

Responsible for database interaction.

Examples

UserRepository

EventRepository

MessageRepository

CommunityRepository

---

## Security Layer

Includes

- JWT Filter
- Authentication Manager
- Password Encoder
- Role Authorization

---

# 12. Frontend Architecture

The frontend is built using React.js.

Structure

```text
src/

├── assets/

├── components/

├── pages/

├── layouts/

├── hooks/

├── context/

├── routes/

├── services/

├── utils/

└── App.jsx
```

---

## Pages

Examples

Home

Login

Register

Dashboard

Communities

Events

Messages

Profile

Settings

---

## Components

Reusable UI

Examples

Navbar

Sidebar

Buddy Card

Event Card

Community Card

Notification Card

Footer

---

## Context

Used for

- Authentication
- Theme
- Notifications
- User Session

---

## Services

Responsible for API communication.

Examples

AuthenticationService

EventService

CommunityService

MessageService

---

# 13. Mobile Application Architecture

Flutter follows MVVM-inspired project organization.

```text
lib/

├── models/

├── providers/

├── services/

├── screens/

├── widgets/

├── routes/

├── utils/

└── main.dart
```

---

## Screens

Splash Screen

Login

Register

Home

Events

Communities

Messages

Profile

Settings

---

## Widgets

Reusable Components

Examples

Buddy Card

Community Card

Navigation Bar

Buttons

Dialogs

---

## Providers

Responsible for application state.

Examples

AuthenticationProvider

UserProvider

ChatProvider

NotificationProvider

---

# 14. Admin Dashboard Architecture

The Admin Dashboard is a separate React application.

Modules

Dashboard

Users

Reports

Communities

Events

Categories

Analytics

Settings

---

## Dashboard Statistics

Displays

- Total Users
- Active Users
- Communities
- Events
- Reports
- Daily Registrations

---

# 15. AI Service Architecture (Future)

AI will be implemented as an independent microservice.

```text
Spring Boot

↓

REST API

↓

AI Service (Python/FastAPI)

↓

Recommendation Engine
```

Possible AI Features

- Buddy Recommendation
- Event Recommendation
- Spam Detection
- Fake Account Detection
- Personalized Feed

---

# 16. Request Processing Flow

Example: User Login

```text
Flutter App

↓

POST /api/login

↓

Authentication Controller

↓

Authentication Service

↓

JWT Authentication

↓

Database Validation

↓

JWT Generated

↓

Response Sent

↓

Dashboard
```

---

Example: Create Event

```text
User

↓

React / Flutter

↓

Event Controller

↓

Event Service

↓

Event Repository

↓

PostgreSQL

↓

Success Response
```

---

# 17. Folder-Level Architecture

```text
NexBuddy/

│

├── Backend/

├── Web/

├── MobileApp/

├── AdminDashboard/

├── Database/

├── AI/

├── Documents/

├── UI-UX/

├── API/

├── Testing/

└── Deployment/
```

This structure keeps each part of the system independent while allowing them to work together through well-defined interfaces.

---
