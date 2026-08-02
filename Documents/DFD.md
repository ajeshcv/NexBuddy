# Data Flow Diagram (DFD)

# NexBuddy

Version : 1.0

Prepared By : Ajesh CV

Project : NexBuddy

---

# Table of Contents

1. Introduction

2. Purpose

3. DFD Symbols

4. External Entities

5. Data Stores

6. Context Diagram

7. Level 0 DFD

---

# 1. Introduction

A Data Flow Diagram (DFD) graphically represents how data moves through the NexBuddy system.

It shows

- External entities
- Processes
- Data Stores
- Data Flow

The DFD focuses on the movement of information rather than the implementation details.

---

# 2. Purpose

The purpose of this document is to

- Understand system workflow
- Identify major processes
- Define system boundaries
- Understand database interactions
- Assist implementation

---

# 3. DFD Symbols

## External Entity

Represents

```
User

Admin

Google Maps

Cloudinary

Firebase
```

Notation

```
+---------+

 Entity

+---------+
```

---

## Process

Represents processing.

Notation

```
( Process )
```

---

## Data Store

Represents database.

Notation

```
|| Database ||
```

---

## Data Flow

Represents movement of information.

Notation

```
---------->
```

---

# 4. External Entities

The NexBuddy system interacts with the following external entities.

---

## Guest

Can

- Register
- Login
- Browse Public Content

---

## User

Can

- Find Buddy
- Create Event
- Join Community
- Chat
- Create Posts

---

## Community Moderator

Can

- Moderate Posts
- Remove Members

---

## Administrator

Can

- Manage Users
- Review Reports
- View Analytics

---

## Cloudinary

Stores

- Images
- Community Banners
- Event Photos

---

## Google Maps

Provides

- Maps
- Nearby Users
- Nearby Events

---

## Firebase

Provides

- Push Notifications

---

# 5. Data Stores

The following logical data stores are used.

```
D1 Users

D2 Profiles

D3 Interests

D4 Buddy Requests

D5 Friendships

D6 Communities

D7 Events

D8 Posts

D9 Messages

D10 Notifications

D11 Reports
```

---

# 6. Context Diagram

The Context Diagram represents NexBuddy as one single process.

```text

                 +----------------------+
                 |      Guest           |
                 +----------+-----------+
                            |
            Register/Login  |
                            |
                            ▼

                 +----------------------+
                 |                      |
                 |      NexBuddy        |
                 |                      |
                 +----------------------+
                            ▲
                            |
                    Dashboard/Data
                            |
     +----------+-----------+------------+-----------+
     |          |                        |           |
     |          |                        |           |
     ▼          ▼                        ▼           ▼

+---------+  +------------+       +-----------+  +---------+
|  User   |  | Moderator  |       |  Admin    |  | Google  |
+---------+  +------------+       +-----------+  | Maps    |
     |              |                    |       +---------+
     |              |                    |
     |              |                    |
     ▼              ▼                    ▼

+-----------------------------------------------+
|              Cloudinary                       |
+-----------------------------------------------+

                     ▲

                     |

             +---------------+
             |   Firebase    |
             +---------------+
```

---

# Context Diagram Explanation

Guest interacts with the system by

- Registering
- Logging in

---

Users interact with the system by

- Creating Events

- Joining Communities

- Finding Buddies

- Posting

- Messaging

---

Administrators interact by

- Managing Users

- Reviewing Reports

- Viewing Analytics

---

Cloudinary stores media.

---

Google Maps provides location services.

---

Firebase sends notifications.

---

# 7. Level 0 DFD

The Level 0 DFD decomposes NexBuddy into major processes.

```text

                         USERS

                           |

                           ▼

                  +-----------------+

                  | Authentication  |

                  +-----------------+

                           |

                           ▼

                     D1 USERS

                           |

                           ▼

+--------------------------------------------------------------+

|                        NexBuddy                              |

+--------------------------------------------------------------+

 |           |            |            |           |

 |           |            |            |           |

 ▼           ▼            ▼            ▼           ▼

Buddy     Community     Events       Chat      Social Feed

 |           |            |            |           |

 ▼           ▼            ▼            ▼           ▼

D4         D6           D7           D9          D8

 |           |            |            |           |

 +-----------+------------+------------+-----------+

                           |

                           ▼

                   Notification Module

                           |

                           ▼

                         D10

                           |

                           ▼

                        Firebase
```

---

# Level 0 Processes

## P1 Authentication

Handles

- Register
- Login
- JWT

Uses

```
Users Table
```

---

## P2 Buddy Matching

Handles

- Friend Requests

- Matching

- Reviews

Uses

```
Buddy Requests

Friendships
```

---

## P3 Communities

Handles

- Community Creation

- Members

- Community Posts

---

## P4 Events

Handles

- Event Creation

- Event Participation

---

## P5 Chat

Handles

- Messages

- Attachments

---

## P6 Social Feed

Handles

- Posts

- Likes

- Comments

---

## P7 Notifications

Handles

- Push Notifications

- In-App Notifications

---

# Level 1 Data Flow Diagram

The Level 1 DFD decomposes each major module of the NexBuddy system into smaller processes.

---

# P1 Authentication Module

## Description

Handles user registration, login, authentication, and session validation.

---

## Level 1 DFD

```text
                  USER
                    │
                    │ Register/Login
                    ▼

             (1.1 Validate Input)
                    │
                    ▼
          (1.2 Authenticate User)
                    │
                    ▼
              || D1 USERS ||

                    │
                    ▼
           (1.3 Generate JWT)

                    │
                    ▼

           Login Success / Failure
```

---

## Processes

### 1.1 Validate Input

Checks:

- Email
- Password
- Required Fields

---

### 1.2 Authenticate User

Validates:

- User Exists
- Password
- Account Status

---

### 1.3 Generate JWT

Creates secure authentication token.

---

# Data Store

```
D1 USERS
```

---

# P2 User Management Module

## Level 1 DFD

```text
                   USER

                     │

                     ▼

            (2.1 View Profile)

                     │

                     ▼

           (2.2 Update Profile)

                     │

                     ▼

             || D2 PROFILE ||

                     │

                     ▼

             Updated Profile
```

---

## Processes

### 2.1 View Profile

Displays user profile.

---

### 2.2 Update Profile

Updates:

- Bio
- Interests
- Profile Picture
- Settings

---

### Data Store

```
D2 PROFILE
```

---

# P3 Buddy Matching Module

## Level 1 DFD

```text
                 USER

                  │

                  ▼

        (3.1 Search Buddies)

                  │

                  ▼

      || D3 INTERESTS ||

                  │

                  ▼

     (3.2 Find Matching Users)

                  │

                  ▼

      || D4 BUDDY REQUESTS ||

                  │

                  ▼

      (3.3 Send Buddy Request)

                  │

                  ▼

        Matching Results
```

---

## Processes

### 3.1 Search

Search using:

- Interest
- Location
- Activity

---

### 3.2 Matching

Compare:

- Interests
- Distance
- Availability

---

### 3.3 Buddy Request

Stores request.

---

# Data Stores

```
D3 Interests

D4 Buddy Requests

D5 Friendships
```

---

# P4 Community Module

## Level 1 DFD

```text
                   USER

                     │

                     ▼

        (4.1 Create Community)

                     │

                     ▼

        || D6 COMMUNITIES ||

                     │

                     ▼

        (4.2 Join Community)

                     │

                     ▼

       (4.3 Community Posts)

                     │

                     ▼

        Updated Community
```

---

## Processes

### Create Community

Stores community information.

---

### Join Community

Creates membership.

---

### Community Posts

Stores discussions.

---

# Data Store

```
D6 Communities
```

---

# P5 Event Module

## Level 1 DFD

```text
                 USER

                   │

                   ▼

           (5.1 Create Event)

                   │

                   ▼

            || D7 EVENTS ||

                   │

                   ▼

           (5.2 Join Event)

                   │

                   ▼

       (5.3 Event Participants)

                   │

                   ▼

           Event Confirmation
```

---

## Processes

### Create Event

Stores event.

---

### Join Event

Adds participant.

---

### Event Management

Updates event information.

---

# Data Store

```
D7 EVENTS
```

---

# P6 Social Feed Module

## Level 1 DFD

```text
                 USER

                   │

                   ▼

           (6.1 Create Post)

                   │

                   ▼

            || D8 POSTS ||

                   │

                   ▼

          (6.2 Like Post)

                   │

                   ▼

       (6.3 Comment Post)

                   │

                   ▼

             Updated Feed
```

---

## Processes

### Create Post

Stores new post.

---

### Like

Stores like.

---

### Comment

Stores comments.

---

# Data Store

```
D8 POSTS
```

---

# P7 Chat Module

## Level 1 DFD

```text
                 USER

                   │

                   ▼

         (7.1 Open Chat)

                   │

                   ▼

       (7.2 Send Message)

                   │

                   ▼

         || D9 MESSAGES ||

                   │

                   ▼

       Receiver Receives Message
```

---

## Processes

### Open Chat

Loads conversations.

---

### Send Message

Stores message.

---

### Receive Message

Delivers message.

---

# Data Store

```
D9 Messages
```

---

# P8 Notification Module

## Level 1 DFD

```text
             System Event

                  │

                  ▼

      (8.1 Generate Notification)

                  │

                  ▼

      || D10 NOTIFICATIONS ||

                  │

                  ▼

        Firebase Service

                  │

                  ▼

              USER DEVICE
```

---

## Processes

Generate notification for:

- Messages
- Buddy Requests
- Community Updates
- Event Invitations
- Likes
- Comments

---

# Data Store

```
D10 Notifications
```

---

# P9 Report Module

## Level 1 DFD

```text
                 USER

                   │

                   ▼

         (9.1 Submit Report)

                   │

                   ▼

           || D11 REPORTS ||

                   │

                   ▼

         (9.2 Admin Review)

                   │

                   ▼

            Report Result
```

---

## Processes

### Submit Report

Stores report.

---

### Review Report

Admin investigates report.

---

### Action

Warning

Suspend

Delete

Reject

---

# Data Store

```
D11 Reports
```

---

# Summary of Level 1 Processes

| Process | Module |
|----------|---------|
| P1 | Authentication |
| P2 | User Management |
| P3 | Buddy Matching |
| P4 | Community |
| P5 | Event |
| P6 | Social Feed |
| P7 | Chat |
| P8 | Notification |
| P9 | Reports |

---

# Level 2 Data Flow Diagram

The Level 2 DFD expands the major modules from Level 1 into detailed processing steps.

---

# Level 2 DFD – Authentication Module

## Description

The Authentication module manages registration, login, password recovery, and secure access.

---

## Authentication Flow

```text
                    USER
                      │
                      ▼

          (1.1 Enter Credentials)
                      │
                      ▼

        (1.2 Validate Input Fields)
                      │
             ┌────────┴─────────┐
             │                  │
        Invalid             Valid
             │                  │
             ▼                  ▼
     Return Error      (1.3 Check User)
                               │
                               ▼
                         || D1 USERS ||
                               │
                      ┌────────┴────────┐
                      │                 │
                 Not Found         User Found
                      │                 │
                      ▼                 ▼
             Return Error     (1.4 Verify Password)
                                        │
                               ┌────────┴────────┐
                               │                 │
                          Incorrect         Correct
                               │                 │
                               ▼                 ▼
                      Return Error    (1.5 Generate JWT)
                                                │
                                                ▼
                                         Login Success
```

---

# Processes

### 1.1 Enter Credentials

User enters email and password.

---

### 1.2 Validate Input

Checks:

- Empty fields
- Email format
- Password length

---

### 1.3 Check User

Reads from:

```
D1 USERS
```

---

### 1.4 Verify Password

Compares BCrypt hash.

---

### 1.5 Generate JWT

Creates authentication token.

---

# Level 2 DFD – Buddy Matching

---

## Buddy Matching Flow

```text
                 USER

                   │

                   ▼

      (2.1 Select Activity Category)

                   │

                   ▼

         (2.2 Select Interests)

                   │

                   ▼

      (2.3 Detect User Location)

                   │

                   ▼

         || D3 INTERESTS ||

                   │

                   ▼

      || USER_LOCATIONS ||

                   │

                   ▼

       (2.4 Find Matching Users)

                   │

                   ▼

        (2.5 Rank Match Results)

                   │

                   ▼

        Matching User List

                   │

                   ▼

      (2.6 Send Buddy Request)

                   │

                   ▼

      || D4 BUDDY_REQUESTS ||
```

---

## Processes

### 2.1 Select Activity

Examples

- Travel
- Gaming
- Movies
- Food

---

### 2.2 Select Interests

Uses:

```
Interests Table
```

---

### 2.3 Detect Location

Uses GPS.

---

### 2.4 Matching

Compares:

- Interests
- Location
- Availability

---

### 2.5 Ranking

Sorts matching users.

---

### 2.6 Buddy Request

Stores request.

---

# Data Stores

```
D3 Interests

User_Locations

D4 Buddy Requests
```

---

# Level 2 DFD – Event Module

---

## Event Flow

```text
                  USER

                    │

                    ▼

          (3.1 Create Event)

                    │

                    ▼

        (3.2 Validate Details)

                    │

                    ▼

       (3.3 Upload Event Image)

                    │

                    ▼

            Cloudinary

                    │

                    ▼

          (3.4 Store Event)

                    │

                    ▼

          || D7 EVENTS ||

                    │

                    ▼

      (3.5 Notify Participants)

                    │

                    ▼

             Firebase

                    │

                    ▼

             Event Created
```

---

## Processes

### Create Event

Receives event information.

---

### Validate

Checks:

- Date
- Time
- Venue

---

### Upload Image

Stores image.

---

### Store Event

Writes to database.

---

### Notification

Invites users.

---

# Data Stores

```
D7 EVENTS
```

---

# Level 2 DFD – Chat Module

---

## Chat Flow

```text
                  USER

                    │

                    ▼

           (4.1 Open Chat)

                    │

                    ▼

         (4.2 Load Messages)

                    │

                    ▼

        || D9 MESSAGES ||

                    │

                    ▼

          (4.3 Type Message)

                    │

                    ▼

          (4.4 Send Message)

                    │

                    ▼

          Spring WebSocket

                    │

                    ▼

          Receiver Online?

           │              │

         YES              NO

           │              │

           ▼              ▼

 Deliver Instantly   Store Message

           │              │

           ▼              ▼

     Receiver       || D9 MESSAGES ||
```

---

## Processes

### Open Chat

Loads previous messages.

---

### Load Messages

Reads database.

---

### Type Message

User composes message.

---

### Send Message

Uses WebSocket.

---

### Delivery

If receiver is offline:

Store message.

If online:

Deliver immediately.

---

# Data Stores

```
D9 Messages
```

---

# Summary

| Module | Level 2 Processes |
|----------|-------------------|
| Authentication | 5 |
| Buddy Matching | 6 |
| Event | 5 |
| Chat | 5 |

---


# 8. Data Stores

Data Stores represent the logical repositories where information is stored within the NexBuddy system.

---

## D1 – Users

Stores authentication information.

Contents

- User ID
- Email
- Password
- Account Status
- Role
- Email Verification Status

Used By

- Authentication Module
- Admin Module

---

## D2 – User Profiles

Stores personal profile information.

Contents

- Name
- Bio
- Profile Picture
- Cover Photo
- Date of Birth
- Gender
- Location
- Education
- Occupation

Used By

- User Management
- Buddy Matching

---

## D3 – Interests

Stores available interests.

Examples

- Travel
- Gaming
- Football
- Programming
- Photography
- Movies

Used By

- Buddy Matching

---

## D4 – Buddy Requests

Stores friendship requests.

Contents

- Sender
- Receiver
- Status
- Created Date

Used By

- Buddy Matching

---

## D5 – Friendships

Stores accepted buddy relationships.

Used By

- Buddy Matching
- Chat

---

## D6 – Communities

Stores community information.

Contains

- Name
- Category
- Members
- Privacy
- Description

Used By

- Community Module

---

## D7 – Events

Stores event information.

Contains

- Title
- Date
- Location
- Participants
- Category

Used By

- Event Module

---

## D8 – Posts

Stores user posts.

Contains

- Content
- Images
- Likes
- Comments

Used By

- Social Feed

---

## D9 – Messages

Stores chat messages.

Contains

- Sender
- Receiver
- Chat Room
- Message
- Attachments

Used By

- Chat Module

---

## D10 – Notifications

Stores notifications.

Contains

- Type
- Title
- User
- Read Status

Used By

- Notification Module

---

## D11 – Reports

Stores reports submitted by users.

Contains

- Reporter
- Reported User
- Reason
- Status

Used By

- Admin Module

---

# 9. External Entities

The NexBuddy system exchanges information with external actors and third-party services.

---

## Guest

Interacts with:

- Registration
- Login
- Public Communities
- Public Events

---

## Registered User

Interacts with:

- Buddy Matching
- Events
- Communities
- Chat
- Social Feed
- Notifications

---

## Community Moderator

Interacts with:

- Community Members
- Community Posts
- Reports

---

## Administrator

Interacts with:

- User Management
- Reports
- Communities
- Analytics
- Categories

---

## Google Maps API

Provides

- Location
- Coordinates
- Nearby Search

---

## Cloudinary

Provides

- Image Storage
- Image Delivery

---

## Firebase Cloud Messaging

Provides

- Push Notifications

---

# 10. Overall Data Flow Summary

The following diagram summarizes how data moves through the NexBuddy platform.

```text
                  Guest / User / Admin
                           │
                           ▼
                  Presentation Layer
            (Flutter / React / Web Dashboard)
                           │
                           ▼
                  REST API (Spring Boot)
                           │
       ┌─────────────┬─────────────┬─────────────┐
       ▼             ▼             ▼
Authentication   Business Logic   WebSocket
       │             │             │
       └─────────────┼─────────────┘
                     ▼
               PostgreSQL Database
                     │
     ┌───────────────┼────────────────┐
     ▼               ▼                ▼
 Cloudinary     Google Maps      Firebase
```

---

# 11. Data Flow Validation Rules

The DFD follows standard software engineering guidelines.

### Rule 1

Every process has:

- Input
- Output

---

### Rule 2

Every data store is connected through a process.

External entities never access data stores directly.

---

### Rule 3

No data flows directly between two data stores.

---

### Rule 4

No data flows directly between two external entities.

---

### Rule 5

Every Level 1 DFD is derived from the Level 0 DFD.

---

### Rule 6

Every Level 2 DFD expands a Level 1 process.

---

# 12. Advantages of the DFD

The Data Flow Diagram provides several benefits.

---

## Better Understanding

Visualizes system functionality.

---

## Easy Communication

Helps developers, guides, and reviewers understand the system.

---

## Database Planning

Shows which modules interact with each data store.

---

## API Design

Helps identify the required REST API endpoints.

---

## Easier Development

Provides a roadmap before implementation.

---

## Testing Support

Makes it easier to design test cases and validate workflows.

---

# 13. Assumptions

The following assumptions are considered.

- Users have internet connectivity.
- Users complete registration before accessing protected features.
- Google Maps API is available.
- Cloudinary stores uploaded images successfully.
- Firebase delivers notifications successfully.
- PostgreSQL database is operational.

---

# 14. Limitations

Current limitations include:

- Internet dependency.
- Third-party API usage limits.
- Cloud storage quota.
- Notification service availability.

These limitations may be addressed in future versions through caching, offline support, and additional infrastructure.

---

# 15. Conclusion

The Data Flow Diagram (DFD) provides a comprehensive representation of how information flows through the NexBuddy platform.

By modelling the Context Diagram, Level 0, Level 1, and Level 2 processes, the document illustrates the interaction between users, system modules, databases, and external services. It also establishes a clear understanding of data movement, making it easier to design APIs, implement business logic, develop the database, and perform system testing.

This DFD serves as a key design artifact and complements the Software Requirements Specification (SRS), System Architecture, and ER Diagram prepared for the NexBuddy project.

---

# References

- IEEE Software Engineering Standards
- Data Flow Diagram (DFD) Notation Guidelines
- Spring Boot Documentation
- PostgreSQL Documentation
- REST API Design Principles

---
