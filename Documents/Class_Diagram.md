# Class Diagram

# NexBuddy

Version : 1.0

Project : NexBuddy

Prepared By : Ajesh CV

---

# Table of Contents

1. Introduction

2. Purpose

3. UML Notations

4. Package Structure

5. User Management Classes

6. Relationships

---

# 1. Introduction

The Class Diagram describes the static structure of the NexBuddy system.

It illustrates

- Classes
- Attributes
- Methods
- Relationships
- Multiplicity
- Associations
- Dependencies

Unlike the ER Diagram, which focuses on database tables, the Class Diagram represents the object-oriented design that will be implemented in Java using Spring Boot.

---

# 2. Purpose

The objectives of this Class Diagram are

- Represent system objects
- Define object relationships
- Assist Spring Boot implementation
- Improve maintainability
- Support future scalability

---

# 3. UML Notations

## Class

```
+----------------------+
|      Class Name      |
+----------------------+
| attributes           |
+----------------------+
| methods              |
+----------------------+
```

---

## Association

```
User -------- Profile
```

---

## One-to-One

```
1 -------- 1
```

---

## One-to-Many

```
1 -------- *
```

---

## Many-to-Many

```
* -------- *
```

---

## Aggregation

```
◇---------
```

Represents a weak ownership relationship.

---

## Composition

```
◆---------
```

Represents a strong ownership relationship.

---

## Inheritance

```
△---------
```

Represents an "is-a" relationship.

---

# 4. Package Structure

The backend is organized into packages following the Spring Boot layered architecture.

```text
com.nexbuddy

│

├── config

├── controller

├── dto

├── entity

├── repository

├── security

├── service

├── websocket

├── exception

├── util

└── NexBuddyApplication.java
```

The class diagrams in this document focus on the **entity package**, which contains the domain model.

---

# 5. User Management Classes

---

## User

```text
+------------------------------------------------+
|                    User                        |
+------------------------------------------------+
| - UUID userId                                 |
| - String email                                |
| - String password                             |
| - Boolean emailVerified                       |
| - AccountStatus accountStatus                 |
| - Role role                                  |
| - LocalDateTime createdAt                     |
| - LocalDateTime updatedAt                     |
+------------------------------------------------+
| + register()                                  |
| + login()                                     |
| + logout()                                    |
| + verifyEmail()                               |
| + changePassword()                            |
+------------------------------------------------+
```

---

## UserProfile

```text
+------------------------------------------------+
|                UserProfile                     |
+------------------------------------------------+
| - UUID profileId                              |
| - String firstName                            |
| - String lastName                             |
| - String username                             |
| - String bio                                  |
| - String gender                               |
| - LocalDate dateOfBirth                       |
| - String phone                                |
| - String city                                 |
| - String state                                |
| - String country                              |
| - String profilePicture                       |
| - String coverPhoto                           |
| - String occupation                           |
| - String education                            |
+------------------------------------------------+
| + updateProfile()                             |
| + uploadProfilePicture()                      |
| + uploadCoverPhoto()                          |
+------------------------------------------------+
```

---

## Interest

```text
+----------------------------------------------+
|                 Interest                     |
+----------------------------------------------+
| - UUID interestId                            |
| - String interestName                        |
| - String category                            |
| - String icon                                |
+----------------------------------------------+
| + createInterest()                           |
| + updateInterest()                           |
+----------------------------------------------+
```

---

## UserInterest

```text
+----------------------------------------------+
|              UserInterest                    |
+----------------------------------------------+
| - UUID userInterestId                        |
| - User user                                  |
| - Interest interest                          |
| - LocalDateTime createdAt                    |
+----------------------------------------------+
| + assignInterest()                           |
| + removeInterest()                           |
+----------------------------------------------+
```

---

## UserSettings

```text
+------------------------------------------------+
|               UserSettings                     |
+------------------------------------------------+
| - UUID settingId                              |
| - Boolean notificationEnabled                 |
| - Boolean profileVisible                      |
| - Boolean locationVisible                     |
| - String language                             |
| - String theme                                |
+------------------------------------------------+
| + updateSettings()                            |
| + enableNotifications()                       |
| + disableNotifications()                      |
+------------------------------------------------+
```

---

## UserSession

```text
+------------------------------------------------+
|                UserSession                     |
+------------------------------------------------+
| - UUID sessionId                              |
| - String jwtToken                             |
| - String device                               |
| - String ipAddress                            |
| - LocalDateTime loginTime                     |
| - LocalDateTime logoutTime                    |
| - Boolean active                              |
+------------------------------------------------+
| + createSession()                             |
| + invalidateSession()                         |
+------------------------------------------------+
```

---

# User Management UML Diagram

```text
                    +----------------------+
                    |        User          |
                    +----------------------+
                    | userId              |
                    | email               |
                    | password            |
                    | role                |
                    +----------------------+
                     |1
                     |
                     |1
                     ▼
            +----------------------+
            |    UserProfile       |
            +----------------------+

                     |

                     |1

                     |

                     ▼

            +----------------------+
            |    UserSettings      |
            +----------------------+

                     |

                     |1

                     |

                     ▼

            +----------------------+
            |    UserSession       |
            +----------------------+

                     |

                     |*

                     |

                     ▼

            +----------------------+
            |   UserInterest       |
            +----------------------+

                     |

                     |*

                     |

                     ▼

            +----------------------+
            |      Interest        |
            +----------------------+
```

---

# 6. Relationships

## User ↔ UserProfile

Relationship

```
One-to-One
```

Reason

Each registered user owns exactly one profile.

---

## User ↔ UserSettings

Relationship

```
One-to-One
```

Each user has one settings object.

---

## User ↔ UserSession

Relationship

```
One-to-Many
```

A user may log in from multiple devices over time.

---

## User ↔ Interest

Relationship

```
Many-to-Many
```

Implemented using

```
UserInterest
```

A user can have multiple interests, and an interest can belong to many users.

---

# Design Notes

- `User` acts as the central entity for authentication and identity.
- `UserProfile` stores personal information separately from authentication data.
- `UserSettings` isolates configurable preferences.
- `UserSession` supports tracking active and historical login sessions.
- `UserInterest` resolves the many-to-many relationship between users and interests.

---

# 7. Buddy Matching Classes

The Buddy Matching module enables users to discover, connect with, and review other users.

---

## BuddyRequest

```text
+------------------------------------------------+
|                BuddyRequest                    |
+------------------------------------------------+
| - UUID requestId                              |
| - User sender                                 |
| - User receiver                               |
| - RequestStatus status                        |
| - String message                              |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + sendRequest()                               |
| + acceptRequest()                             |
| + rejectRequest()                             |
| + cancelRequest()                             |
+------------------------------------------------+
```

---

## Friendship

```text
+------------------------------------------------+
|                 Friendship                     |
+------------------------------------------------+
| - UUID friendshipId                           |
| - User userOne                                |
| - User userTwo                                |
| - FriendshipStatus status                     |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + createFriendship()                          |
| + removeFriendship()                          |
+------------------------------------------------+
```

---

## BlockedUser

```text
+------------------------------------------------+
|                BlockedUser                     |
+------------------------------------------------+
| - UUID blockId                                |
| - User blocker                                |
| - User blockedUser                            |
| - String reason                               |
| - LocalDateTime blockedAt                     |
+------------------------------------------------+
| + blockUser()                                 |
| + unblockUser()                               |
+------------------------------------------------+
```

---

## UserLocation

```text
+------------------------------------------------+
|               UserLocation                     |
+------------------------------------------------+
| - UUID locationId                             |
| - Double latitude                             |
| - Double longitude                            |
| - String city                                 |
| - String state                                |
| - String country                              |
| - LocalDateTime updatedAt                     |
+------------------------------------------------+
| + updateLocation()                            |
+------------------------------------------------+
```

---

## ActivityCategory

```text
+------------------------------------------------+
|             ActivityCategory                  |
+------------------------------------------------+
| - UUID categoryId                             |
| - String categoryName                         |
| - String description                          |
| - String icon                                 |
+------------------------------------------------+
| + createCategory()                            |
| + updateCategory()                            |
+------------------------------------------------+
```

---

## UserActivityPreference

```text
+------------------------------------------------+
|         UserActivityPreference                 |
+------------------------------------------------+
| - UUID preferenceId                           |
| - User user                                   |
| - ActivityCategory category                   |
| - String availability                         |
| - String experienceLevel                      |
+------------------------------------------------+
| + addPreference()                             |
| + removePreference()                          |
+------------------------------------------------+
```

---

## BuddyReview

```text
+------------------------------------------------+
|               BuddyReview                      |
+------------------------------------------------+
| - UUID reviewId                               |
| - User reviewer                               |
| - User reviewedUser                           |
| - Integer rating                              |
| - String review                               |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + submitReview()                              |
+------------------------------------------------+
```

---

# Buddy Matching UML Diagram

```text
User
 │
 ├─────────────── BuddyRequest
 │                     │
 │                     ▼
 │                Friendship
 │
 ├─────────────── BlockedUser
 │
 ├─────────────── UserLocation
 │
 ├─────────────── UserActivityPreference
 │                     │
 │                     ▼
 │              ActivityCategory
 │
 └─────────────── BuddyReview
```

---

# 8. Community Classes

---

## Community

```text
+------------------------------------------------+
|                Community                       |
+------------------------------------------------+
| - UUID communityId                            |
| - String name                                 |
| - String description                          |
| - CommunityPrivacy privacy                    |
| - String profileImage                         |
| - String coverImage                           |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + createCommunity()                           |
| + updateCommunity()                           |
| + deleteCommunity()                           |
+------------------------------------------------+
```

---

## CommunityMember

```text
+------------------------------------------------+
|             CommunityMember                    |
+------------------------------------------------+
| - UUID membershipId                           |
| - User member                                 |
| - Community community                         |
| - MemberRole role                             |
| - LocalDateTime joinedAt                      |
+------------------------------------------------+
| + joinCommunity()                             |
| + leaveCommunity()                            |
+------------------------------------------------+
```

---

## CommunityPost

```text
+------------------------------------------------+
|              CommunityPost                     |
+------------------------------------------------+
| - UUID postId                                 |
| - User author                                 |
| - Community community                         |
| - String content                              |
| - String imageUrl                             |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + createPost()                                |
| + editPost()                                  |
| + deletePost()                                |
+------------------------------------------------+
```

---

## CommunityComment

```text
+------------------------------------------------+
|            CommunityComment                    |
+------------------------------------------------+
| - UUID commentId                              |
| - CommunityPost post                          |
| - User author                                 |
| - String comment                              |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + addComment()                                |
| + deleteComment()                             |
+------------------------------------------------+
```

---

# Community UML Diagram

```text
Community
    │
    ├──────────── CommunityMember
    │                 │
    │                 ▼
    │               User
    │
    └──────────── CommunityPost
                      │
                      ▼
             CommunityComment
```

---

# 9. Event Classes

---

## Event

```text
+------------------------------------------------+
|                    Event                       |
+------------------------------------------------+
| - UUID eventId                                |
| - String title                                |
| - String description                          |
| - String location                             |
| - Double latitude                             |
| - Double longitude                            |
| - LocalDate eventDate                         |
| - LocalTime startTime                         |
| - LocalTime endTime                           |
| - Integer maxParticipants                     |
| - Double entryFee                             |
| - EventStatus status                          |
+------------------------------------------------+
| + createEvent()                               |
| + updateEvent()                               |
| + cancelEvent()                               |
+------------------------------------------------+
```

---

## EventParticipant

```text
+------------------------------------------------+
|             EventParticipant                   |
+------------------------------------------------+
| - UUID participantId                          |
| - Event event                                 |
| - User participant                            |
| - AttendanceStatus attendanceStatus           |
| - LocalDateTime joinedAt                      |
+------------------------------------------------+
| + joinEvent()                                 |
| + leaveEvent()                                |
+------------------------------------------------+
```

---

## EventInvitation

```text
+------------------------------------------------+
|             EventInvitation                    |
+------------------------------------------------+
| - UUID invitationId                           |
| - Event event                                 |
| - User sender                                 |
| - User receiver                               |
| - InvitationStatus status                     |
+------------------------------------------------+
| + sendInvitation()                            |
| + acceptInvitation()                          |
| + declineInvitation()                         |
+------------------------------------------------+
```

---

## EventReview

```text
+------------------------------------------------+
|               EventReview                      |
+------------------------------------------------+
| - UUID reviewId                               |
| - Event event                                 |
| - User reviewer                               |
| - Integer rating                              |
| - String review                               |
+------------------------------------------------+
| + submitReview()                              |
+------------------------------------------------+
```

---

# Event UML Diagram

```text
Event
 │
 ├──────────── EventParticipant
 │                   │
 │                   ▼
 │                 User
 │
 ├──────────── EventInvitation
 │
 └──────────── EventReview
```

---

# Relationships

## User ↔ BuddyRequest

One user can send many buddy requests and receive many buddy requests.

---

## User ↔ Friendship

Many-to-Many

Implemented through the Friendship class.

---

## User ↔ Community

Many-to-Many

Implemented through CommunityMember.

---

## Community ↔ CommunityPost

One-to-Many

---

## CommunityPost ↔ CommunityComment

One-to-Many

---

## User ↔ Event

Many-to-Many

Implemented through EventParticipant.

---

## Event ↔ EventInvitation

One-to-Many

---

## Event ↔ EventReview

One-to-Many

---

# 10. Social Feed Classes

The Social Feed module allows users to create posts, interact through likes and comments, and save posts for later viewing.

---

## Post

```text
+------------------------------------------------+
|                    Post                        |
+------------------------------------------------+
| - UUID postId                                 |
| - User author                                 |
| - String content                              |
| - String imageUrl                             |
| - PostVisibility visibility                   |
| - LocalDateTime createdAt                     |
| - LocalDateTime updatedAt                     |
+------------------------------------------------+
| + createPost()                                |
| + editPost()                                  |
| + deletePost()                                |
+------------------------------------------------+
```

---

## Comment

```text
+------------------------------------------------+
|                  Comment                       |
+------------------------------------------------+
| - UUID commentId                              |
| - Post post                                   |
| - User author                                 |
| - String content                              |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + addComment()                                |
| + editComment()                               |
| + deleteComment()                             |
+------------------------------------------------+
```

---

## Like

```text
+------------------------------------------------+
|                    Like                        |
+------------------------------------------------+
| - UUID likeId                                 |
| - User user                                   |
| - Post post                                   |
| - LocalDateTime likedAt                       |
+------------------------------------------------+
| + likePost()                                  |
| + unlikePost()                                |
+------------------------------------------------+
```

---

## SavedPost

```text
+------------------------------------------------+
|                 SavedPost                      |
+------------------------------------------------+
| - UUID savedPostId                            |
| - User user                                   |
| - Post post                                   |
| - LocalDateTime savedAt                       |
+------------------------------------------------+
| + savePost()                                  |
| + removeSavedPost()                           |
+------------------------------------------------+
```

---

# Social Feed UML Diagram

```text
                    User
                      │
                      ▼
                    Post
          ┌──────────┼──────────┐
          ▼          ▼          ▼
      Comment      Like     SavedPost
```

---

# 11. Chat Classes

The Chat module supports one-to-one, group, community, and event-based messaging.

---

## ChatRoom

```text
+------------------------------------------------+
|                 ChatRoom                       |
+------------------------------------------------+
| - UUID chatRoomId                             |
| - ChatRoomType roomType                       |
| - String title                                |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + createRoom()                                |
| + closeRoom()                                 |
+------------------------------------------------+
```

---

## ChatMember

```text
+------------------------------------------------+
|                ChatMember                      |
+------------------------------------------------+
| - UUID memberId                               |
| - ChatRoom room                               |
| - User user                                   |
| - LocalDateTime joinedAt                      |
+------------------------------------------------+
| + joinRoom()                                  |
| + leaveRoom()                                 |
+------------------------------------------------+
```

---

## Message

```text
+------------------------------------------------+
|                  Message                       |
+------------------------------------------------+
| - UUID messageId                              |
| - ChatRoom room                               |
| - User sender                                 |
| - String content                              |
| - MessageType messageType                     |
| - Boolean isRead                              |
| - LocalDateTime sentAt                        |
+------------------------------------------------+
| + sendMessage()                               |
| + editMessage()                               |
| + deleteMessage()                             |
| + markAsRead()                                |
+------------------------------------------------+
```

---

## MessageAttachment

```text
+------------------------------------------------+
|             MessageAttachment                  |
+------------------------------------------------+
| - UUID attachmentId                           |
| - Message message                             |
| - String fileUrl                              |
| - String fileType                             |
| - Long fileSize                               |
+------------------------------------------------+
| + uploadAttachment()                          |
| + removeAttachment()                          |
+------------------------------------------------+
```

---

# Chat UML Diagram

```text
                ChatRoom
                    │
      ┌─────────────┴─────────────┐
      ▼                           ▼
ChatMember                    Message
      │                           │
      ▼                           ▼
    User                 MessageAttachment
```

---

# 12. Notification Classes

Notifications inform users about important system events.

---

## Notification

```text
+------------------------------------------------+
|               Notification                     |
+------------------------------------------------+
| - UUID notificationId                         |
| - User user                                   |
| - String title                                |
| - String message                              |
| - NotificationType type                       |
| - Boolean isRead                              |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + sendNotification()                          |
| + markAsRead()                                |
| + deleteNotification()                        |
+------------------------------------------------+
```

---

# Notification UML Diagram

```text
User
 │
 ▼
Notification
```

---

# 13. Report Classes

The reporting module allows users to report inappropriate content or users.

---

## Report

```text
+------------------------------------------------+
|                  Report                        |
+------------------------------------------------+
| - UUID reportId                               |
| - User reporter                               |
| - User reportedUser                           |
| - String reason                               |
| - String description                          |
| - ReportStatus status                         |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + submitReport()                              |
| + updateStatus()                              |
+------------------------------------------------+
```

---

# Report UML Diagram

```text
           User
            │
     ┌──────┴──────┐
     ▼             ▼
Reporter      Reported User
      \         /
       \       /
        ▼     ▼
         Report
```

---

# Relationships

## User ↔ Post

**One-to-Many**

A user can create many posts.

---

## Post ↔ Comment

**One-to-Many**

Each post can have multiple comments.

---

## Post ↔ Like

**One-to-Many**

A post can receive multiple likes.

---

## User ↔ SavedPost

**One-to-Many**

A user can save multiple posts.

---

## ChatRoom ↔ ChatMember

**One-to-Many**

A chat room contains multiple members.

---

## ChatRoom ↔ Message

**One-to-Many**

A chat room stores multiple messages.

---

## Message ↔ MessageAttachment

**One-to-Many**

A message may contain multiple attachments.

---

## User ↔ Notification

**One-to-Many**

A user can receive multiple notifications.

---

## User ↔ Report

**One-to-Many**

A user can submit multiple reports and may also be the subject of multiple reports.

---

# Design Notes

- The `Post` class is independent of `CommunityPost`, allowing users to create personal posts outside communities.
- `ChatRoom` supports private, group, community, and event conversations through the `roomType` field.
- `MessageAttachment` separates file metadata from message content, making future support for images, videos, and documents easier.
- `Notification` is generic so it can be reused for messages, buddy requests, community updates, event reminders, and system alerts.
- The `Report` class supports moderation workflows and future administrative actions.

---

# 14. Administration Classes

The Administration module manages system-wide roles, permissions, monitoring, and auditing.

---

## Role

```text
+------------------------------------------------+
|                    Role                        |
+------------------------------------------------+
| - UUID roleId                                 |
| - String roleName                             |
| - String description                          |
+------------------------------------------------+
| + createRole()                                |
| + updateRole()                                |
| + deleteRole()                                |
+------------------------------------------------+
```

Examples

```
ADMIN

USER

MODERATOR
```

---

## Permission

```text
+------------------------------------------------+
|                 Permission                     |
+------------------------------------------------+
| - UUID permissionId                           |
| - String permissionName                       |
| - String description                          |
+------------------------------------------------+
| + createPermission()                          |
+------------------------------------------------+
```

Examples

```
CREATE_EVENT

DELETE_POST

BAN_USER

MANAGE_COMMUNITY

VIEW_REPORTS
```

---

## RolePermission

```text
+------------------------------------------------+
|               RolePermission                   |
+------------------------------------------------+
| - UUID rolePermissionId                       |
| - Role role                                   |
| - Permission permission                       |
+------------------------------------------------+
| + assignPermission()                          |
| + removePermission()                          |
+------------------------------------------------+
```

---

## ActivityLog

```text
+------------------------------------------------+
|                ActivityLog                     |
+------------------------------------------------+
| - UUID logId                                  |
| - User user                                   |
| - String action                               |
| - String ipAddress                            |
| - LocalDateTime createdAt                     |
+------------------------------------------------+
| + recordActivity()                            |
+------------------------------------------------+
```

---

# Administration UML Diagram

```text
              Role
                │
                ▼
        RolePermission
                │
                ▼
          Permission


User
 │
 ▼
ActivityLog
```

---

# 15. Master Class Diagram

The following simplified UML diagram shows how the major classes relate to each other.

```text
                                User
                                  │
      ┌──────────────┬────────────┼───────────────┬──────────────┐
      ▼              ▼            ▼               ▼              ▼
UserProfile    UserSettings   UserSession   UserInterest    UserLocation
                                                   │
                                                   ▼
                                              Interest

      │
      ├──────────── BuddyRequest
      ├──────────── Friendship
      ├──────────── BlockedUser
      ├──────────── BuddyReview
      ├──────────── ActivityPreference
      │
      ├──────────── CommunityMember ─────► Community
      │                                     │
      │                                     ▼
      │                               CommunityPost
      │                                     │
      │                                     ▼
      │                              CommunityComment
      │
      ├──────────── EventParticipant ───────► Event
      │                                         │
      │                                         ├──── EventInvitation
      │                                         └──── EventReview
      │
      ├──────────── Post
      │                 │
      │        ┌────────┼────────┐
      │        ▼        ▼        ▼
      │    Comment    Like   SavedPost
      │
      ├──────────── ChatMember ─────► ChatRoom
      │                                   │
      │                                   ▼
      │                               Message
      │                                   │
      │                                   ▼
      │                          MessageAttachment
      │
      ├──────────── Notification
      ├──────────── Report
      └──────────── ActivityLog
```

---

# 16. Multiplicity Summary

| Relationship | Multiplicity |
|--------------|--------------|
| User → UserProfile | 1 : 1 |
| User → UserSettings | 1 : 1 |
| User → UserSession | 1 : N |
| User ↔ Interest | M : N |
| User → BuddyRequest | 1 : N |
| User ↔ Friendship | M : N |
| User → Community | 1 : N (Owner) |
| Community → CommunityMember | 1 : N |
| Community → CommunityPost | 1 : N |
| CommunityPost → CommunityComment | 1 : N |
| User → Event | 1 : N (Creator) |
| Event → EventParticipant | 1 : N |
| Event → EventInvitation | 1 : N |
| Event → EventReview | 1 : N |
| User → Post | 1 : N |
| Post → Comment | 1 : N |
| Post → Like | 1 : N |
| ChatRoom → ChatMember | 1 : N |
| ChatRoom → Message | 1 : N |
| Message → MessageAttachment | 1 : N |
| User → Notification | 1 : N |
| User → Report | 1 : N |
| Role ↔ Permission | M : N |

---

# 17. Design Principles

The NexBuddy class model follows established object-oriented design principles.

---

## Single Responsibility Principle (SRP)

Each class has one clear responsibility.

Examples:

- `User` → Authentication & identity
- `Post` → Social content
- `Message` → Chat messages
- `Notification` → User notifications

---

## Open/Closed Principle (OCP)

Classes can be extended without modifying existing code.

Example:

New notification types can be added without changing the `Notification` class structure.

---

## Liskov Substitution Principle (LSP)

Future subclasses (for example, specialized notification types) should be usable wherever the base type is expected.

---

## Interface Segregation Principle (ISP)

Service interfaces should expose only methods required by each module.

Examples:

- `UserService`
- `CommunityService`
- `EventService`
- `ChatService`

---

## Dependency Inversion Principle (DIP)

Controllers depend on service interfaces, not concrete implementations.

```text
Controller
      │
      ▼
Service Interface
      │
      ▼
Service Implementation
      │
      ▼
Repository
```

---

# 18. Object-Oriented Features

The model uses the following OOP concepts:

### Encapsulation

Private fields with controlled access through methods.

---

### Association

Examples:

- User ↔ Post
- User ↔ Community

---

### Aggregation

Examples:

- Community aggregates members.
- Event aggregates participants.

---

### Composition

Examples:

- ChatRoom strongly owns Messages.
- Message strongly owns MessageAttachments.

---

### Abstraction

Business logic is encapsulated in service classes rather than entity classes.

---

# 19. Design Patterns Used

The implementation aligns with common Spring Boot patterns.

### MVC (Model–View–Controller)

- Model → Entity classes
- View → React / Flutter UI
- Controller → REST Controllers

---

### Repository Pattern

Each entity has a corresponding repository.

Examples:

- `UserRepository`
- `PostRepository`
- `CommunityRepository`

---

### Service Layer Pattern

Business logic resides in services.

Examples:

- `UserService`
- `EventService`
- `ChatService`

---

### DTO Pattern

Data Transfer Objects isolate API payloads from entity classes.

Examples:

- `LoginRequestDTO`
- `UserProfileDTO`
- `EventResponseDTO`

---

# 20. Conclusion

The Class Diagram defines the object-oriented structure of the NexBuddy platform. It identifies the key domain entities, their attributes, behaviors, and relationships while following modern software engineering principles.

This model provides a direct blueprint for implementing:

- Spring Boot entity classes
- JPA relationships
- Repository interfaces
- Service layer
- REST controllers
- Data Transfer Objects (DTOs)

Together with the SRS, System Architecture, ER Diagram, Use Case, and DFD documents, this Class Diagram completes the core analysis and design phase of the NexBuddy project.

---

# References

- UML 2.5 Specification
- Object-Oriented Analysis and Design
- Spring Boot Documentation
- Spring Data JPA Documentation

---
