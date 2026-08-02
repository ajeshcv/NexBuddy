# API Documentation

# NexBuddy

Version : 1.0

Prepared By : Ajesh CV

Backend Framework : Spring Boot

Database : PostgreSQL

Authentication : JWT

---

# Table of Contents

1. Introduction

2. API Standards

3. Base URL

4. Authentication

5. Request & Response Format

6. HTTP Status Codes

7. Authentication APIs

---

# 1. Introduction

The NexBuddy REST API provides secure communication between the frontend applications (React Web and Flutter Mobile) and the backend server.

All APIs follow REST architectural principles and exchange data in JSON format.

---

# API Features

- RESTful Design
- JWT Authentication
- JSON Request & Response
- Stateless Communication
- Role-Based Authorization
- Standard HTTP Status Codes

---

# 2. API Standards

## Protocol

```
HTTPS
```

---

## Data Format

```
JSON
```

---

## Character Encoding

```
UTF-8
```

---

## Authentication

```
Bearer JWT Token
```

---

## API Version

```
v1
```

---

# 3. Base URL

Development

```text
http://localhost:8080/api/v1
```

Production

```text
https://api.nexbuddy.com/api/v1
```

---

# 4. Authentication

Protected APIs require JWT.

Header

```http
Authorization: Bearer <JWT_TOKEN>
```

Example

```http
Authorization: Bearer eyJhbGciOiJIUzI1Ni...
```

---

# 5. Request Format

Example

```json
{
  "email": "ajesh@example.com",
  "password": "Password@123"
}
```

---

# Success Response

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {}
}
```

---

# Error Response

```json
{
  "success": false,
  "message": "Invalid request",
  "errors": []
}
```

---

# Pagination Response

```json
{
  "page": 1,
  "size": 10,
  "totalPages": 15,
  "totalElements": 150,
  "content": []
}
```

---

# 6. HTTP Status Codes

| Code | Meaning |
|------|---------|
|200|OK|
|201|Created|
|204|No Content|
|400|Bad Request|
|401|Unauthorized|
|403|Forbidden|
|404|Not Found|
|409|Conflict|
|422|Validation Failed|
|500|Internal Server Error|

---

# 7. Authentication APIs

---

## Register User

### Endpoint

```http
POST /auth/register
```

### Description

Creates a new user account.

---

### Request

```json
{
  "firstName": "Ajesh",
  "lastName": "CV",
  "username": "ajeshcv",
  "email": "ajesh@example.com",
  "password": "Password@123",
  "confirmPassword": "Password@123"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Registration successful. Please verify your email."
}
```

---

### Possible Errors

```text
400 Bad Request

409 Email Already Exists

422 Validation Failed
```

---

## Verify Email

### Endpoint

```http
POST /auth/verify-email
```

---

### Request

```json
{
  "email": "ajesh@example.com",
  "otp": "548271"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Email verified successfully."
}
```

---

## Resend Verification OTP

### Endpoint

```http
POST /auth/resend-otp
```

---

### Request

```json
{
  "email": "ajesh@example.com"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "OTP sent successfully."
}
```

---

## Login

### Endpoint

```http
POST /auth/login
```

---

### Request

```json
{
  "email": "ajesh@example.com",
  "password": "Password@123"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Login successful.",
  "data": {
    "token": "JWT_TOKEN",
    "refreshToken": "REFRESH_TOKEN",
    "expiresIn": 3600,
    "user": {
      "id": "uuid",
      "name": "Ajesh CV",
      "email": "ajesh@example.com",
      "role": "USER"
    }
  }
}
```

---

### Error Responses

```text
401 Invalid Credentials

403 Email Not Verified

423 Account Suspended
```

---

## Refresh Token

### Endpoint

```http
POST /auth/refresh-token
```

---

### Request

```json
{
  "refreshToken": "REFRESH_TOKEN"
}
```

---

### Success Response

```json
{
  "success": true,
  "data": {
    "token": "NEW_JWT_TOKEN",
    "expiresIn": 3600
  }
}
```

---

## Forgot Password

### Endpoint

```http
POST /auth/forgot-password
```

---

### Request

```json
{
  "email": "ajesh@example.com"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Password reset OTP sent."
}
```

---

## Verify Password Reset OTP

### Endpoint

```http
POST /auth/verify-reset-otp
```

---

### Request

```json
{
  "email": "ajesh@example.com",
  "otp": "453821"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "OTP verified."
}
```

---

## Reset Password

### Endpoint

```http
POST /auth/reset-password
```

---

### Request

```json
{
  "email": "ajesh@example.com",
  "newPassword": "NewPassword@123",
  "confirmPassword": "NewPassword@123"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Password updated successfully."
}
```

---

## Logout

### Endpoint

```http
POST /auth/logout
```

---

### Header

```http
Authorization: Bearer JWT_TOKEN
```

---

### Success Response

```json
{
  "success": true,
  "message": "Logged out successfully."
}
```

---

# Authentication Flow

```text
Register
     │
     ▼
Email Verification
     │
     ▼
Login
     │
     ▼
JWT Generated
     │
     ▼
Protected APIs
     │
     ▼
Refresh Token
     │
     ▼
Logout
```

---

# Authentication Endpoints Summary

| Method | Endpoint | Authentication |
|---------|----------|----------------|
| POST | /auth/register | No |
| POST | /auth/verify-email | No |
| POST | /auth/resend-otp | No |
| POST | /auth/login | No |
| POST | /auth/refresh-token | No |
| POST | /auth/forgot-password | No |
| POST | /auth/verify-reset-otp | No |
| POST | /auth/reset-password | No |
| POST | /auth/logout | Yes |

---

# Security Notes

- Passwords are stored using BCrypt.
- JWT access tokens are short-lived.
- Refresh tokens are used to obtain new access tokens.
- All authenticated endpoints require the `Authorization: Bearer <token>` header.
- Sensitive endpoints validate ownership and user roles before processing requests.

---

# 8. User APIs

The User APIs allow authenticated users to manage their profiles, preferences, settings, and account information.

---

## Get My Profile

### Endpoint

```http
GET /users/me
```

### Authentication

```
Required
```

---

### Success Response

```json
{
  "success": true,
  "data": {
    "id": "uuid",
    "firstName": "Ajesh",
    "lastName": "CV",
    "username": "ajeshcv",
    "email": "ajesh@example.com",
    "bio": "Software Developer",
    "city": "Kochi",
    "country": "India",
    "profilePicture": "https://..."
  }
}
```

---

## Get User Profile

### Endpoint

```http
GET /users/{userId}
```

---

## Update Profile

### Endpoint

```http
PUT /users/profile
```

---

### Request

```json
{
  "firstName": "Ajesh",
  "lastName": "CV",
  "bio": "MCA Student",
  "city": "Kochi",
  "country": "India",
  "occupation": "Student"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Profile updated successfully."
}
```

---

## Upload Profile Picture

### Endpoint

```http
POST /users/profile-picture
```

### Content Type

```
multipart/form-data
```

Parameter

```
file
```

---

## Upload Cover Photo

### Endpoint

```http
POST /users/cover-photo
```

---

## Update User Interests

### Endpoint

```http
PUT /users/interests
```

---

### Request

```json
{
  "interestIds": [
    "uuid1",
    "uuid2",
    "uuid3"
  ]
}
```

---

## Get Available Interests

### Endpoint

```http
GET /interests
```

---

### Response

```json
{
  "success": true,
  "data": [
    {
      "id": "uuid",
      "name": "Travel"
    },
    {
      "id": "uuid",
      "name": "Gaming"
    }
  ]
}
```

---

## Update Settings

### Endpoint

```http
PUT /users/settings
```

---

### Request

```json
{
  "notificationEnabled": true,
  "profileVisible": true,
  "locationVisible": false,
  "language": "English",
  "theme": "Dark"
}
```

---

## Change Password

### Endpoint

```http
PUT /users/change-password
```

---

### Request

```json
{
  "currentPassword": "OldPassword123",
  "newPassword": "NewPassword123",
  "confirmPassword": "NewPassword123"
}
```

---

## Delete Account

### Endpoint

```http
DELETE /users/me
```

---

### Response

```json
{
  "success": true,
  "message": "Account deleted successfully."
}
```

---

# User API Summary

| Method | Endpoint | Authentication |
|---------|----------|----------------|
| GET | /users/me | Yes |
| GET | /users/{userId} | Yes |
| PUT | /users/profile | Yes |
| POST | /users/profile-picture | Yes |
| POST | /users/cover-photo | Yes |
| PUT | /users/interests | Yes |
| GET | /interests | Yes |
| PUT | /users/settings | Yes |
| PUT | /users/change-password | Yes |
| DELETE | /users/me | Yes |

---

# 9. Buddy Matching APIs

These APIs manage buddy discovery, friendship requests, reviews, and blocking.

---

## Find Buddies

### Endpoint

```http
GET /buddies/search
```

---

### Query Parameters

```text
interest=Travel

city=Kochi

availability=Weekend

page=0

size=10
```

---

### Response

```json
{
  "success": true,
  "data": [
    {
      "id": "uuid",
      "name": "John",
      "city": "Kochi",
      "matchScore": 92
    }
  ]
}
```

---

## Get Nearby Users

### Endpoint

```http
GET /buddies/nearby
```

---

### Query Parameters

```text
latitude

longitude

radius
```

Example

```
latitude=9.9312

longitude=76.2673

radius=10
```

---

## Send Buddy Request

### Endpoint

```http
POST /buddy-requests
```

---

### Request

```json
{
  "receiverId": "uuid",
  "message": "Let's connect!"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Buddy request sent."
}
```

---

## Get Incoming Requests

### Endpoint

```http
GET /buddy-requests/incoming
```

---

## Get Sent Requests

### Endpoint

```http
GET /buddy-requests/sent
```

---

## Accept Buddy Request

### Endpoint

```http
PUT /buddy-requests/{requestId}/accept
```

---

### Response

```json
{
  "success": true,
  "message": "Buddy request accepted."
}
```

---

## Reject Buddy Request

### Endpoint

```http
PUT /buddy-requests/{requestId}/reject
```

---

## Cancel Buddy Request

### Endpoint

```http
DELETE /buddy-requests/{requestId}
```

---

## Get Friends

### Endpoint

```http
GET /friends
```

---

## Remove Friend

### Endpoint

```http
DELETE /friends/{friendId}
```

---

## Block User

### Endpoint

```http
POST /blocked-users
```

---

### Request

```json
{
  "userId": "uuid",
  "reason": "Spam"
}
```

---

## Unblock User

### Endpoint

```http
DELETE /blocked-users/{userId}
```

---

## Submit Buddy Review

### Endpoint

```http
POST /buddy-reviews
```

---

### Request

```json
{
  "reviewedUserId": "uuid",
  "rating": 5,
  "review": "Very friendly and punctual."
}
```

---

## Get Buddy Reviews

### Endpoint

```http
GET /users/{userId}/buddy-reviews
```

---

# Buddy API Summary

| Method | Endpoint | Authentication |
|---------|----------|----------------|
| GET | /buddies/search | Yes |
| GET | /buddies/nearby | Yes |
| POST | /buddy-requests | Yes |
| GET | /buddy-requests/incoming | Yes |
| GET | /buddy-requests/sent | Yes |
| PUT | /buddy-requests/{requestId}/accept | Yes |
| PUT | /buddy-requests/{requestId}/reject | Yes |
| DELETE | /buddy-requests/{requestId} | Yes |
| GET | /friends | Yes |
| DELETE | /friends/{friendId} | Yes |
| POST | /blocked-users | Yes |
| DELETE | /blocked-users/{userId} | Yes |
| POST | /buddy-reviews | Yes |
| GET | /users/{userId}/buddy-reviews | Yes |

---

# API Design Notes

- All endpoints require a valid JWT except authentication endpoints.
- Pagination (`page` and `size`) should be supported for list APIs.
- Search APIs should support optional filters (city, interests, availability).
- File upload APIs should use `multipart/form-data`.
- UUIDs are used as resource identifiers throughout the API.

---


# 10. Community APIs

The Community APIs allow users to create, join, manage, and interact within communities.

---

## Create Community

### Endpoint

```http
POST /communities
```

### Authentication

```
Required
```

---

### Request

```json
{
  "name": "Travel Enthusiasts",
  "description": "A community for people who love traveling.",
  "categoryId": "uuid",
  "privacy": "PUBLIC"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Community created successfully.",
  "data": {
    "communityId": "uuid"
  }
}
```

---

## Get All Communities

### Endpoint

```http
GET /communities
```

### Query Parameters

```text
page=0
size=10
category=Travel
privacy=PUBLIC
keyword=travel
```

---

## Get Community Details

### Endpoint

```http
GET /communities/{communityId}
```

---

## Update Community

### Endpoint

```http
PUT /communities/{communityId}
```

---

## Delete Community

### Endpoint

```http
DELETE /communities/{communityId}
```

Only the community owner or an administrator may delete a community.

---

## Join Community

### Endpoint

```http
POST /communities/{communityId}/join
```

---

## Leave Community

### Endpoint

```http
DELETE /communities/{communityId}/leave
```

---

## Get Community Members

### Endpoint

```http
GET /communities/{communityId}/members
```

---

## Remove Community Member

### Endpoint

```http
DELETE /communities/{communityId}/members/{userId}
```

Permissions:

- Community Owner
- Community Moderator

---

## Create Community Post

### Endpoint

```http
POST /communities/{communityId}/posts
```

---

### Request

```json
{
  "content": "Weekend trip discussion!",
  "imageUrl": "https://..."
}
```

---

## Get Community Posts

### Endpoint

```http
GET /communities/{communityId}/posts
```

---

## Add Community Comment

### Endpoint

```http
POST /community-posts/{postId}/comments
```

---

### Request

```json
{
  "comment": "Sounds great!"
}
```

---

## Delete Community Post

### Endpoint

```http
DELETE /community-posts/{postId}
```

Permissions:

- Author
- Community Moderator
- Community Owner
- Administrator

---

# Community API Summary

| Method | Endpoint | Authentication |
|---------|----------|----------------|
| POST | /communities | Yes |
| GET | /communities | Yes |
| GET | /communities/{communityId} | Yes |
| PUT | /communities/{communityId} | Yes |
| DELETE | /communities/{communityId} | Yes |
| POST | /communities/{communityId}/join | Yes |
| DELETE | /communities/{communityId}/leave | Yes |
| GET | /communities/{communityId}/members | Yes |
| DELETE | /communities/{communityId}/members/{userId} | Yes |
| POST | /communities/{communityId}/posts | Yes |
| GET | /communities/{communityId}/posts | Yes |
| POST | /community-posts/{postId}/comments | Yes |
| DELETE | /community-posts/{postId} | Yes |

---

# 11. Event APIs

The Event APIs manage creation, participation, invitations, and reviews for events.

---

## Create Event

### Endpoint

```http
POST /events
```

---

### Request

```json
{
  "title": "Weekend Trek",
  "description": "One-day trekking trip.",
  "categoryId": "uuid",
  "location": "Munnar",
  "latitude": 10.0889,
  "longitude": 77.0595,
  "eventDate": "2026-09-15",
  "startTime": "09:00",
  "endTime": "17:00",
  "maximumParticipants": 25,
  "entryFee": 500
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Event created successfully.",
  "data": {
    "eventId": "uuid"
  }
}
```

---

## Get All Events

### Endpoint

```http
GET /events
```

### Query Parameters

```text
page=0
size=10
category=Travel
city=Kochi
date=2026-09-15
```

---

## Get Event Details

### Endpoint

```http
GET /events/{eventId}
```

---

## Update Event

### Endpoint

```http
PUT /events/{eventId}
```

Only the event creator or an administrator may update the event.

---

## Cancel Event

### Endpoint

```http
DELETE /events/{eventId}
```

---

## Join Event

### Endpoint

```http
POST /events/{eventId}/join
```

---

## Leave Event

### Endpoint

```http
DELETE /events/{eventId}/leave
```

---

## Get Event Participants

### Endpoint

```http
GET /events/{eventId}/participants
```

---

## Invite User to Event

### Endpoint

```http
POST /events/{eventId}/invite
```

---

### Request

```json
{
  "userId": "uuid"
}
```

---

## Accept Event Invitation

### Endpoint

```http
PUT /event-invitations/{invitationId}/accept
```

---

## Decline Event Invitation

### Endpoint

```http
PUT /event-invitations/{invitationId}/decline
```

---

## Submit Event Review

### Endpoint

```http
POST /event-reviews
```

---

### Request

```json
{
  "eventId": "uuid",
  "rating": 5,
  "review": "Amazing experience!"
}
```

---

## Get Event Reviews

### Endpoint

```http
GET /events/{eventId}/reviews
```

---

## Upload Event Image

### Endpoint

```http
POST /events/{eventId}/image
```

### Content Type

```text
multipart/form-data
```

Parameter

```text
file
```

---

# Event API Summary

| Method | Endpoint | Authentication |
|---------|----------|----------------|
| POST | /events | Yes |
| GET | /events | Yes |
| GET | /events/{eventId} | Yes |
| PUT | /events/{eventId} | Yes |
| DELETE | /events/{eventId} | Yes |
| POST | /events/{eventId}/join | Yes |
| DELETE | /events/{eventId}/leave | Yes |
| GET | /events/{eventId}/participants | Yes |
| POST | /events/{eventId}/invite | Yes |
| PUT | /event-invitations/{invitationId}/accept | Yes |
| PUT | /event-invitations/{invitationId}/decline | Yes |
| POST | /event-reviews | Yes |
| GET | /events/{eventId}/reviews | Yes |
| POST | /events/{eventId}/image | Yes |

---

# Community & Event API Notes

- Community and event listing APIs should support pagination, filtering, and keyword search.
- File uploads (community images and event images) should use `multipart/form-data`.
- Authorization checks should ensure only owners, moderators, or administrators can perform restricted actions.
- Event participation should validate capacity before adding a participant.
- Event reviews should only be allowed after the event has been completed.

---

# 12. Social Feed APIs

The Social Feed APIs allow users to create posts, interact with content, and manage saved posts.

---

## Create Post

### Endpoint

```http
POST /posts
```

### Authentication

```
Required
```

---

### Request

```json
{
  "content": "Looking for a trekking buddy this weekend!",
  "visibility": "PUBLIC"
}
```

---

### Success Response

```json
{
  "success": true,
  "message": "Post created successfully.",
  "data": {
    "postId": "uuid"
  }
}
```

---

## Upload Post Image

### Endpoint

```http
POST /posts/{postId}/image
```

### Content Type

```text
multipart/form-data
```

Parameter

```text
file
```

---

## Get Feed

### Endpoint

```http
GET /posts/feed
```

### Query Parameters

```text
page=0
size=10
```

---

## Get Post

### Endpoint

```http
GET /posts/{postId}
```

---

## Update Post

### Endpoint

```http
PUT /posts/{postId}
```

Only the author may update the post.

---

## Delete Post

### Endpoint

```http
DELETE /posts/{postId}
```

---

## Like Post

### Endpoint

```http
POST /posts/{postId}/like
```

---

## Unlike Post

### Endpoint

```http
DELETE /posts/{postId}/like
```

---

## Comment on Post

### Endpoint

```http
POST /posts/{postId}/comments
```

---

### Request

```json
{
  "content": "I'm interested!"
}
```

---

## Delete Comment

### Endpoint

```http
DELETE /comments/{commentId}
```

---

## Save Post

### Endpoint

```http
POST /posts/{postId}/save
```

---

## Remove Saved Post

### Endpoint

```http
DELETE /posts/{postId}/save
```

---

## Get Saved Posts

### Endpoint

```http
GET /posts/saved
```

---

# Social Feed API Summary

| Method | Endpoint | Authentication |
|---------|----------|----------------|
| POST | /posts | Yes |
| POST | /posts/{postId}/image | Yes |
| GET | /posts/feed | Yes |
| GET | /posts/{postId} | Yes |
| PUT | /posts/{postId} | Yes |
| DELETE | /posts/{postId} | Yes |
| POST | /posts/{postId}/like | Yes |
| DELETE | /posts/{postId}/like | Yes |
| POST | /posts/{postId}/comments | Yes |
| DELETE | /comments/{commentId} | Yes |
| POST | /posts/{postId}/save | Yes |
| DELETE | /posts/{postId}/save | Yes |
| GET | /posts/saved | Yes |

---

# 13. Chat APIs

The Chat APIs manage conversations, messages, and file sharing.

---

## Get Chat Rooms

### Endpoint

```http
GET /chat-rooms
```

---

## Create Chat Room

### Endpoint

```http
POST /chat-rooms
```

---

### Request

```json
{
  "roomType": "PRIVATE",
  "participantIds": [
    "uuid1",
    "uuid2"
  ]
}
```

---

## Get Chat Room Details

### Endpoint

```http
GET /chat-rooms/{roomId}
```

---

## Get Messages

### Endpoint

```http
GET /chat-rooms/{roomId}/messages
```

### Query Parameters

```text
page=0
size=50
```

---

## Send Message (REST)

### Endpoint

```http
POST /chat-rooms/{roomId}/messages
```

---

### Request

```json
{
  "message": "Hello!",
  "messageType": "TEXT"
}
```

---

## Send Message (WebSocket)

### Destination

```text
/app/chat.sendMessage
```

---

## Subscribe to Chat

### Topic

```text
/topic/chat/{roomId}
```

---

## Upload Attachment

### Endpoint

```http
POST /messages/{messageId}/attachments
```

### Content Type

```text
multipart/form-data
```

---

## Delete Message

### Endpoint

```http
DELETE /messages/{messageId}
```

---

## Mark Message as Read

### Endpoint

```http
PUT /messages/{messageId}/read
```

---

# Chat API Summary

| Method | Endpoint | Authentication |
|---------|----------|----------------|
| GET | /chat-rooms | Yes |
| POST | /chat-rooms | Yes |
| GET | /chat-rooms/{roomId} | Yes |
| GET | /chat-rooms/{roomId}/messages | Yes |
| POST | /chat-rooms/{roomId}/messages | Yes |
| POST | /messages/{messageId}/attachments | Yes |
| DELETE | /messages/{messageId} | Yes |
| PUT | /messages/{messageId}/read | Yes |

---

# WebSocket Endpoints

| Purpose | Endpoint |
|----------|----------|
| Connect | `/ws` |
| Send Message | `/app/chat.sendMessage` |
| Subscribe | `/topic/chat/{roomId}` |

---

# 14. Notification APIs

The Notification APIs allow users to retrieve and manage system notifications.

---

## Get Notifications

### Endpoint

```http
GET /notifications
```

### Query Parameters

```text
page=0
size=20
```

---

### Success Response

```json
{
  "success": true,
  "data": [
    {
      "notificationId": "uuid",
      "title": "New Buddy Request",
      "message": "John sent you a buddy request.",
      "type": "BUDDY_REQUEST",
      "isRead": false
    }
  ]
}
```

---

## Get Notification

### Endpoint

```http
GET /notifications/{notificationId}
```

---

## Mark Notification as Read

### Endpoint

```http
PUT /notifications/{notificationId}/read
```

---

## Mark All Notifications as Read

### Endpoint

```http
PUT /notifications/read-all
```

---

## Delete Notification

### Endpoint

```http
DELETE /notifications/{notificationId}
```

---

## Delete All Notifications

### Endpoint

```http
DELETE /notifications
```

---

# Notification API Summary

| Method | Endpoint | Authentication |
|---------|----------|----------------|
| GET | /notifications | Yes |
| GET | /notifications/{notificationId} | Yes |
| PUT | /notifications/{notificationId}/read | Yes |
| PUT | /notifications/read-all | Yes |
| DELETE | /notifications/{notificationId} | Yes |
| DELETE | /notifications | Yes |

---

# API Notes

### Pagination

The following endpoints should support pagination:

- Feed
- Communities
- Events
- Messages
- Notifications

---

### File Upload

The following endpoints accept `multipart/form-data`:

- Profile Picture
- Cover Photo
- Event Image
- Community Image
- Post Image
- Message Attachment

---

### WebSocket

Real-time communication is used for:

- Chat
- Typing Indicator (future)
- Online Presence (future)

---

### Security

Every endpoint in this section requires:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 15. Admin APIs

The Admin APIs are accessible only to users with the **ADMIN** role.

All endpoints require:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## Get Dashboard Statistics

### Endpoint

```http
GET /admin/dashboard
```

### Response

```json
{
    "totalUsers": 12543,
    "activeUsers": 4120,
    "totalCommunities": 450,
    "totalEvents": 612,
    "pendingReports": 27,
    "newRegistrations": 56
}
```

---

## Get All Users

### Endpoint

```http
GET /admin/users
```

### Query Parameters

```text
page=0

size=20

status=ACTIVE

keyword=ajesh
```

---

## Get User Details

### Endpoint

```http
GET /admin/users/{userId}
```

---

## Suspend User

### Endpoint

```http
PUT /admin/users/{userId}/suspend
```

---

### Request

```json
{
    "reason":"Spam activities"
}
```

---

## Activate User

### Endpoint

```http
PUT /admin/users/{userId}/activate
```

---

## Delete User

### Endpoint

```http
DELETE /admin/users/{userId}
```

---

## Get Reports

### Endpoint

```http
GET /admin/reports
```

---

## Review Report

### Endpoint

```http
PUT /admin/reports/{reportId}
```

---

### Request

```json
{
    "status":"RESOLVED",
    "action":"WARNING"
}
```

---

## Delete Community

### Endpoint

```http
DELETE /admin/communities/{communityId}
```

---

## Delete Event

### Endpoint

```http
DELETE /admin/events/{eventId}
```

---

## Delete Post

### Endpoint

```http
DELETE /admin/posts/{postId}
```

---

## Get System Logs

### Endpoint

```http
GET /admin/logs
```

---

## Get Analytics

### Endpoint

```http
GET /admin/analytics
```

---

### Response

```json
{
    "dailyActiveUsers":2500,
    "monthlyRegistrations":800,
    "totalMessages":50000,
    "totalPosts":12000,
    "topCommunities":[]
}
```

---

# Admin API Summary

| Method | Endpoint |
|----------|----------|
| GET | /admin/dashboard |
| GET | /admin/users |
| GET | /admin/users/{id} |
| PUT | /admin/users/{id}/suspend |
| PUT | /admin/users/{id}/activate |
| DELETE | /admin/users/{id} |
| GET | /admin/reports |
| PUT | /admin/reports/{id} |
| DELETE | /admin/communities/{id} |
| DELETE | /admin/events/{id} |
| DELETE | /admin/posts/{id} |
| GET | /admin/logs |
| GET | /admin/analytics |

---

# 16. Standard Error Responses

## 400 Bad Request

```json
{
    "success":false,
    "message":"Invalid Request"
}
```

---

## 401 Unauthorized

```json
{
    "success":false,
    "message":"Authentication Required"
}
```

---

## 403 Forbidden

```json
{
    "success":false,
    "message":"Access Denied"
}
```

---

## 404 Not Found

```json
{
    "success":false,
    "message":"Resource Not Found"
}
```

---

## 409 Conflict

```json
{
    "success":false,
    "message":"Resource Already Exists"
}
```

---

## 422 Validation Failed

```json
{
    "success":false,
    "errors":[
        "Email is required",
        "Password must contain at least 8 characters"
    ]
}
```

---

## 500 Internal Server Error

```json
{
    "success":false,
    "message":"Something went wrong"
}
```

---

# 17. Validation Rules

## User Registration

- Email must be unique.
- Password must contain at least 8 characters.
- Password should include uppercase, lowercase, number, and special character.
- Username must be unique.
- Email must be verified before login.

---

## Community

- Name cannot be empty.
- Description cannot exceed 500 characters.
- Community name must be unique.

---

## Event

- Event date cannot be in the past.
- Maximum participants must be greater than zero.
- Start time must be before end time.

---

## Post

- Content cannot be empty if no image is uploaded.
- Uploaded files must follow supported formats.

---

## Message

- A message must contain text or an attachment.
- Maximum attachment size should follow server limits.

---

# 18. API Security

The NexBuddy API follows modern REST security practices.

## Authentication

- JWT Access Token
- Refresh Token

---

## Password Encryption

```text
BCrypt
```

---

## Authorization

Role-Based Access Control (RBAC)

Roles

```text
USER

MODERATOR

ADMIN
```

---

## HTTPS

All production APIs must use HTTPS.

---

## Input Validation

All incoming requests should be validated before processing.

---

## CORS

Allow only trusted frontend domains.

Example

```text
https://app.nexbuddy.com

https://admin.nexbuddy.com
```

---

# 19. Rate Limiting

To protect the API from abuse, rate limits should be applied.

| Endpoint | Limit |
|----------|-------|
| Login | 5 requests/minute |
| Register | 3 requests/minute |
| Forgot Password | 3 requests/hour |
| Search | 100 requests/hour |
| Chat Messages | 30 requests/minute |

---

# 20. API Versioning

Current Version

```
v1
```

Example

```text
/api/v1/users

/api/v1/events

/api/v1/chat
```

Future versions

```
v2

v3
```

Older versions should remain supported for a transition period when practical.

---

# 21. API Best Practices

- Use nouns for resource names.
- Use appropriate HTTP methods.
- Return consistent JSON structures.
- Support pagination for collection endpoints.
- Validate all request payloads.
- Return meaningful HTTP status codes.
- Log important operations for auditing.
- Keep APIs backward compatible within the same major version.

---

# 22. API Testing Tools

Recommended tools

- Postman
- Swagger UI / OpenAPI
- Insomnia
- Bruno

---

# 23. Conclusion

The NexBuddy REST API is designed to be secure, scalable, and maintainable.

It follows RESTful principles, JWT-based authentication, role-based authorization, and consistent JSON responses. The API specification defines the interaction between the frontend applications (React Web and Flutter Mobile) and the Spring Boot backend, providing a clear contract for implementation and testing.

This document should be updated whenever new endpoints are introduced or existing endpoints change to keep development and integration synchronized.

---

# References

- REST Architectural Style
- HTTP/1.1 Semantics (RFC 9110)
- JWT (RFC 7519)
- Spring Boot Documentation
- Spring Security Documentation
- OpenAPI Specification

---
