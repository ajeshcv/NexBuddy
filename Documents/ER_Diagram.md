# Entity Relationship Diagram (ERD)

# NexBuddy

**Version:** 1.0

**Project:** NexBuddy

**Database:** PostgreSQL

---

# Table of Contents

1. Database Overview
2. Database Design Principles
3. Entity Categories
4. ER Diagram - User Management
5. Entity Descriptions
6. Relationships
7. Primary Keys
8. Foreign Keys

---

# 1. Database Overview

NexBuddy is designed using a relational database model.

The database stores all application data required for:

- User Authentication
- User Profiles
- Buddy Matching
- Communities
- Events
- Chat
- Social Feed
- Notifications
- Reports
- Administration

The database is designed to satisfy the Third Normal Form (3NF), reducing redundancy while ensuring efficient querying.

---

# Database Statistics

Estimated Number of Tables

```
35+
```

Estimated Relationships

```
70+
```

Database Engine

```
PostgreSQL
```

Primary Key Type

```
UUID
```

Timestamp Format

```
TIMESTAMP
```

---

# 2. Database Design Principles

The NexBuddy database follows these principles.

## Normalization

- First Normal Form
- Second Normal Form
- Third Normal Form

---

## Data Integrity

- Primary Keys
- Foreign Keys
- Unique Constraints

---

## Scalability

The schema allows future expansion without redesign.

---

## Security

Sensitive information such as passwords is encrypted before storage.

---

# 3. Entity Categories

The database is divided into logical modules.

```
Authentication

User Management

Buddy Matching

Communities

Events

Posts

Chat

Notifications

Reports

Administration
```

---

# USER MANAGEMENT MODULE

---

# 4. Entities

## Users

Stores login credentials.

### Attributes

```
user_id (PK)

email

password

email_verified

account_status

role

created_at

updated_at
```

---

## User_Profile

Stores personal information.

### Attributes

```
profile_id (PK)

user_id (FK)

first_name

last_name

username

bio

gender

date_of_birth

phone

city

state

country

profile_picture

cover_photo

occupation

education

created_at

updated_at
```

---

## Interests

Master list of interests.

### Attributes

```
interest_id (PK)

interest_name

category

icon

created_at
```

Examples

```
Travel

Gaming

Football

Photography

Movies

Reading

Programming

Fitness
```

---

## User_Interests

Many-to-Many relationship.

### Attributes

```
user_interest_id (PK)

user_id (FK)

interest_id (FK)

created_at
```

---

## User_Settings

Stores privacy settings.

### Attributes

```
setting_id (PK)

user_id (FK)

profile_visibility

friend_request_permission

message_permission

location_visibility

notification_enabled

theme

language

updated_at
```

---

## User_Sessions

Stores active login sessions.

### Attributes

```
session_id (PK)

user_id (FK)

jwt_token

device

ip_address

login_time

logout_time

status
```

---

# 5. ER Diagram

```text
                    USERS
+------------------------------------------------+
| PK user_id                                     |
| email                                          |
| password                                       |
| email_verified                                 |
| account_status                                 |
| role                                           |
| created_at                                     |
+------------------------------------------------+
                    |
                    | 1
                    |
                    | 1
                    ▼
              USER_PROFILE
+------------------------------------------------+
| PK profile_id                                  |
| FK user_id                                     |
| first_name                                     |
| last_name                                      |
| username                                       |
| bio                                            |
| gender                                         |
| date_of_birth                                  |
| phone                                          |
| city                                           |
| state                                          |
| country                                        |
| profile_picture                                |
| cover_photo                                    |
| occupation                                     |
| education                                      |
+------------------------------------------------+

                    |
                    |
                    | 1
                    |
                    | N
                    ▼

             USER_SETTINGS
+------------------------------------------------+
| PK setting_id                                  |
| FK user_id                                     |
| profile_visibility                             |
| friend_request_permission                      |
| message_permission                             |
| notification_enabled                           |
| theme                                          |
| language                                       |
+------------------------------------------------+

                    |
                    |
                    | 1
                    |
                    | N
                    ▼

             USER_SESSIONS
+------------------------------------------------+
| PK session_id                                  |
| FK user_id                                     |
| jwt_token                                      |
| device                                         |
| ip_address                                     |
| login_time                                     |
| logout_time                                    |
| status                                         |
+------------------------------------------------+

                    |
                    |
                    | N
                    |
                    | M
                    ▼

             USER_INTERESTS
+------------------------------------------------+
| PK user_interest_id                            |
| FK user_id                                     |
| FK interest_id                                 |
+------------------------------------------------+
                    |
                    |
                    ▼

               INTERESTS
+------------------------------------------------+
| PK interest_id                                 |
| interest_name                                  |
| category                                       |
| icon                                           |
+------------------------------------------------+
```

---

# 6. Relationship Summary

### Users → User_Profile

Relationship

```
One-to-One
```

Explanation

Every registered user has exactly one profile.

---

### Users → User_Settings

Relationship

```
One-to-One
```

Every user has one settings record.

---

### Users → User_Sessions

Relationship

```
One-to-Many
```

A user may log in from multiple devices.

---

### Users → Interests

Relationship

```
Many-to-Many
```

Implemented using

```
User_Interests
```

---

# 7. Primary Keys

```
user_id

profile_id

interest_id

user_interest_id

setting_id

session_id
```

---

# 8. Foreign Keys

```
User_Profile.user_id

User_Settings.user_id

User_Sessions.user_id

User_Interests.user_id

User_Interests.interest_id
```

---

# Database Notes

Passwords are stored as BCrypt hashes.

JWT tokens should never be stored permanently after expiration.

Profile pictures store only the Cloudinary URL.

Interest categories are predefined by administrators but can be extended in future versions.

---

# BUDDY MATCHING MODULE

The Buddy Matching module manages how users discover, connect, and interact with other users based on shared interests and activities.

---

# 9. Entities

## Buddy_Requests

Stores pending friend/buddy requests.

### Attributes

```
request_id (PK)

sender_id (FK)

receiver_id (FK)

status

message

created_at

updated_at
```

Status

```
Pending

Accepted

Rejected

Cancelled
```

---

## Friendships

Stores accepted buddy relationships.

### Attributes

```
friendship_id (PK)

user_one_id (FK)

user_two_id (FK)

created_at

status
```

Status

```
Active

Removed

Blocked
```

---

## Blocked_Users

Stores blocked users.

### Attributes

```
block_id (PK)

blocker_id (FK)

blocked_user_id (FK)

reason

created_at
```

---

## User_Locations

Stores latest user location.

### Attributes

```
location_id (PK)

user_id (FK)

latitude

longitude

city

state

country

last_updated
```

---

## Activity_Categories

Master table of buddy categories.

### Attributes

```
category_id (PK)

category_name

icon

description
```

Examples

```
Travel Buddy

Study Buddy

Gaming Buddy

Movie Buddy

Food Buddy

Tea Buddy

Gym Buddy

Sports Buddy

Photography Buddy

Music Buddy

Networking Buddy
```

---

## User_Activity_Preferences

Stores activities selected by users.

### Attributes

```
preference_id (PK)

user_id (FK)

category_id (FK)

availability

experience_level

created_at
```

Availability

```
Weekdays

Weekends

Morning

Afternoon

Evening

Flexible
```

Experience

```
Beginner

Intermediate

Advanced
```

---

## Buddy_Reviews

Users can review buddies after activities.

### Attributes

```
review_id (PK)

reviewer_id (FK)

reviewed_user_id (FK)

rating

review

created_at
```

Rating

```
1 - 5 Stars
```

---

## Buddy_Match_History

Stores recommendation history.

### Attributes

```
match_id (PK)

user_id (FK)

matched_user_id (FK)

matching_score

algorithm_version

matched_at
```

---

# 10. ER Diagram

```text
                         USERS
                            │
        ┌───────────────────┼────────────────────┐
        │                   │                    │
        ▼                   ▼                    ▼

 BUDDY_REQUESTS      FRIENDSHIPS        BLOCKED_USERS

+----------------+  +----------------+  +----------------+
| PK request_id  |  | PK friendship  |  | PK block_id    |
| FK sender_id   |  | FK user_one    |  | FK blocker_id  |
| FK receiver_id |  | FK user_two    |  | FK blocked_id  |
| status         |  | status         |  | reason         |
| message        |  | created_at     |  | created_at     |
+----------------+  +----------------+  +----------------+

                            │
                            │
                            ▼

                   USER_LOCATIONS

+---------------------------------------+
| PK location_id                        |
| FK user_id                            |
| latitude                              |
| longitude                             |
| city                                  |
| state                                 |
| country                               |
+---------------------------------------+

                            │
                            │
                            ▼

             USER_ACTIVITY_PREFERENCES

+----------------------------------------+
| PK preference_id                       |
| FK user_id                             |
| FK category_id                         |
| availability                           |
| experience_level                       |
+----------------------------------------+
                  │
                  ▼

          ACTIVITY_CATEGORIES

+----------------------------------------+
| PK category_id                         |
| category_name                          |
| icon                                   |
| description                            |
+----------------------------------------+

                            │
                            │
                            ▼

                 BUDDY_REVIEWS

+----------------------------------------+
| PK review_id                           |
| FK reviewer_id                         |
| FK reviewed_user_id                    |
| rating                                 |
| review                                 |
+----------------------------------------+

                            │
                            ▼

             BUDDY_MATCH_HISTORY

+----------------------------------------+
| PK match_id                            |
| FK user_id                             |
| FK matched_user_id                     |
| matching_score                         |
| algorithm_version                      |
+----------------------------------------+
```

---

# 11. Relationship Summary

## Users → Buddy Requests

Relationship

```
One User

↓

Many Requests Sent

Many Requests Received
```

---

## Users → Friendships

Relationship

```
Many-to-Many
```

Implemented using

```
Friendships
```

---

## Users → Blocked Users

Relationship

```
Many-to-Many
```

Implemented using

```
Blocked_Users
```

---

## Users → Locations

Relationship

```
One-to-One
```

Latest location only.

---

## Users → Activity Preferences

Relationship

```
Many-to-Many
```

Using

```
User_Activity_Preferences
```

---

## Users → Reviews

Relationship

```
One User

↓

Many Reviews Given

Many Reviews Received
```

---

## Users → Match History

Relationship

```
One User

↓

Many Match Records
```

---

# 12. Primary Keys

```
request_id

friendship_id

block_id

location_id

category_id

preference_id

review_id

match_id
```

---

# 13. Foreign Keys

```
sender_id

receiver_id

user_one_id

user_two_id

blocker_id

blocked_user_id

user_id

category_id

reviewer_id

reviewed_user_id

matched_user_id
```

---

# Database Notes

- A friendship is created only when a buddy request is accepted.
- A blocked user cannot send buddy requests or messages to the blocker.
- User location stores the latest coordinates for nearby matching.
- Activity preferences allow users to select multiple buddy categories and availability.
- Buddy reviews help build trust and can be used in future recommendation algorithms.
- Match history records recommendation results for analytics and future AI improvements.

---


# COMMUNITY & EVENTS MODULE

This module manages user-created communities and events, allowing people to interact, organize activities, and participate in real-world meetups.

---

# 14. Community Module

## Communities

Stores all communities created by users.

### Attributes

```text
community_id (PK)

owner_id (FK)

community_name

description

category_id (FK)

profile_image

cover_image

privacy

member_count

created_at

updated_at
```

Privacy

```text
Public

Private
```

---

## Community_Members

Stores members of each community.

### Attributes

```text
membership_id (PK)

community_id (FK)

user_id (FK)

role

joined_at

status
```

Role

```text
Owner

Moderator

Member
```

Status

```text
Active

Removed

Left
```

---

## Community_Posts

Stores posts inside communities.

### Attributes

```text
community_post_id (PK)

community_id (FK)

user_id (FK)

content

image_url

created_at

updated_at
```

---

## Community_Comments

Stores comments on community posts.

### Attributes

```text
comment_id (PK)

community_post_id (FK)

user_id (FK)

comment

created_at
```

---

## Community_Categories

Master table of community categories.

### Attributes

```text
category_id (PK)

category_name

description

icon
```

Examples

```text
Travel

Gaming

Study

Sports

Movies

Photography

Music

Technology

Fitness
```

---

# Community ER Diagram

```text
                     USERS
                        │
                        │
                        ▼

                  COMMUNITIES
+--------------------------------------+
| PK community_id                      |
| FK owner_id                          |
| community_name                       |
| description                          |
| FK category_id                       |
| privacy                              |
+--------------------------------------+
           │
           │
           │
           ▼

      COMMUNITY_MEMBERS

+--------------------------------------+
| PK membership_id                     |
| FK community_id                      |
| FK user_id                           |
| role                                 |
| status                               |
+--------------------------------------+

           │
           │
           ▼

      COMMUNITY_POSTS

+--------------------------------------+
| PK community_post_id                 |
| FK community_id                      |
| FK user_id                           |
| content                              |
| image_url                            |
+--------------------------------------+

           │
           ▼

    COMMUNITY_COMMENTS

+--------------------------------------+
| PK comment_id                        |
| FK community_post_id                 |
| FK user_id                           |
| comment                              |
+--------------------------------------+

           │
           ▼

 COMMUNITY_CATEGORIES

+--------------------------------------+
| PK category_id                       |
| category_name                        |
| description                          |
+--------------------------------------+
```

---

# Community Relationships

Users → Communities

```text
One User

↓

Many Communities
```

---

Communities → Members

```text
One Community

↓

Many Members
```

---

Communities → Posts

```text
One Community

↓

Many Posts
```

---

Posts → Comments

```text
One Post

↓

Many Comments
```

---

# 15. Event Module

Events are organized by users or communities.

---

## Events

### Attributes

```text
event_id (PK)

creator_id (FK)

category_id (FK)

title

description

location

latitude

longitude

event_date

start_time

end_time

maximum_participants

entry_fee

image_url

status

created_at
```

Status

```text
Upcoming

Ongoing

Completed

Cancelled
```

---

## Event_Participants

Stores users joining events.

### Attributes

```text
participant_id (PK)

event_id (FK)

user_id (FK)

joined_at

attendance_status
```

Attendance

```text
Registered

Attended

Cancelled

No Show
```

---

## Event_Invitations

Stores invitations.

### Attributes

```text
invitation_id (PK)

event_id (FK)

sender_id (FK)

receiver_id (FK)

status

sent_at
```

Status

```text
Pending

Accepted

Declined
```

---

## Event_Reviews

Users review events.

### Attributes

```text
review_id (PK)

event_id (FK)

user_id (FK)

rating

review

created_at
```

---

## Event_Categories

### Attributes

```text
category_id (PK)

category_name

description

icon
```

Examples

```text
Travel

Football

Cricket

Gaming

Movie

Study

Meetup

Workshop

Conference
```

---

# Event ER Diagram

```text
                      USERS
                         │
                         │
                         ▼

                       EVENTS

+--------------------------------------+
| PK event_id                          |
| FK creator_id                        |
| FK category_id                       |
| title                                |
| description                          |
| location                             |
| latitude                             |
| longitude                            |
| event_date                           |
| maximum_participants                 |
| status                               |
+--------------------------------------+

             │
             │
             ▼

      EVENT_PARTICIPANTS

+--------------------------------------+
| PK participant_id                    |
| FK event_id                          |
| FK user_id                           |
| attendance_status                    |
+--------------------------------------+

             │
             ▼

      EVENT_INVITATIONS

+--------------------------------------+
| PK invitation_id                     |
| FK event_id                          |
| FK sender_id                         |
| FK receiver_id                       |
| status                               |
+--------------------------------------+

             │
             ▼

        EVENT_REVIEWS

+--------------------------------------+
| PK review_id                         |
| FK event_id                          |
| FK user_id                           |
| rating                               |
| review                               |
+--------------------------------------+

             │
             ▼

      EVENT_CATEGORIES

+--------------------------------------+
| PK category_id                       |
| category_name                        |
| description                          |
+--------------------------------------+
```

---

# Event Relationships

Users → Events

```text
One User

↓

Many Events
```

---

Events → Participants

```text
One Event

↓

Many Participants
```

---

Events → Invitations

```text
One Event

↓

Many Invitations
```

---

Events → Reviews

```text
One Event

↓

Many Reviews
```

---

# Primary Keys

```text
community_id

membership_id

community_post_id

comment_id

event_id

participant_id

invitation_id

review_id
```

---

# Foreign Keys

```text
owner_id

community_id

user_id

creator_id

event_id

category_id

sender_id

receiver_id
```

---

# Database Notes

- Every community has exactly one owner.
- Communities can have multiple moderators.
- A user may join many communities.
- Events may be public or linked to a community in future versions.
- Participants can leave events before they begin.
- Reviews are allowed only after an event is completed.
- Community categories and event categories are maintained separately to keep classification flexible.

---

# SOCIAL FEED, CHAT, NOTIFICATIONS & ADMIN MODULE

---

# 16. Social Feed Module

The Social Feed allows users to share updates, interact with posts, and engage with the community.

---

## Posts

### Attributes

```text
post_id (PK)

user_id (FK)

content

image_url

visibility

created_at

updated_at
```

Visibility

```text
Public

Friends

Private
```

---

## Comments

### Attributes

```text
comment_id (PK)

post_id (FK)

user_id (FK)

comment

created_at
```

---

## Likes

### Attributes

```text
like_id (PK)

post_id (FK)

user_id (FK)

created_at
```

---

## Saved_Posts

### Attributes

```text
saved_post_id (PK)

user_id (FK)

post_id (FK)

saved_at
```

---

# Social Feed ER Diagram

```text
USERS
   │
   ▼

POSTS
+------------------------------+
| PK post_id                   |
| FK user_id                   |
| content                      |
| image_url                    |
| visibility                   |
+------------------------------+
      │
      ├───────────────┐
      ▼               ▼

COMMENTS          LIKES

+-------------+   +-------------+
| comment_id  |   | like_id     |
| post_id FK  |   | post_id FK  |
| user_id FK  |   | user_id FK  |
+-------------+   +-------------+

      │
      ▼

SAVED_POSTS

+------------------------------+
| saved_post_id                |
| FK user_id                   |
| FK post_id                   |
+------------------------------+
```

---

# 17. Chat Module

---

## Chat_Rooms

### Attributes

```text
chat_room_id (PK)

room_type

created_at
```

Room Type

```text
Private

Group

Community

Event
```

---

## Chat_Members

### Attributes

```text
member_id (PK)

chat_room_id (FK)

user_id (FK)

joined_at
```

---

## Messages

### Attributes

```text
message_id (PK)

chat_room_id (FK)

sender_id (FK)

message

message_type

created_at

is_read
```

Message Type

```text
Text

Image

Video

File

Voice
```

---

## Message_Attachments

### Attributes

```text
attachment_id (PK)

message_id (FK)

file_url

file_type

file_size
```

---

# Chat ER Diagram

```text
CHAT_ROOMS
      │
      ▼

CHAT_MEMBERS

+----------------------------+
| member_id                  |
| room_id FK                 |
| user_id FK                 |
+----------------------------+

      │
      ▼

MESSAGES

+----------------------------+
| message_id                 |
| room_id FK                 |
| sender_id FK               |
| message                    |
| message_type               |
+----------------------------+

      │
      ▼

MESSAGE_ATTACHMENTS

+----------------------------+
| attachment_id              |
| message_id FK              |
| file_url                   |
+----------------------------+
```

---

# 18. Notification Module

---

## Notifications

### Attributes

```text
notification_id (PK)

user_id (FK)

title

message

type

is_read

created_at
```

Type

```text
Buddy Request

Message

Community

Event

System
```

---

# Notification ER Diagram

```text
USERS

│

▼

NOTIFICATIONS

+-------------------------------+
| notification_id               |
| user_id FK                    |
| title                         |
| message                       |
| type                          |
| is_read                       |
+-------------------------------+
```

---

# 19. Report Module

---

## Reports

### Attributes

```text
report_id (PK)

reporter_id (FK)

reported_user_id (FK)

reason

description

status

created_at
```

Status

```text
Pending

Under Review

Resolved

Rejected
```

---

# Report ER Diagram

```text
USERS

│

▼

REPORTS

+------------------------------+
| report_id                    |
| reporter_id FK               |
| reported_user_id FK          |
| reason                       |
| status                       |
+------------------------------+
```

---

# 20. Administration Module

---

## Roles

```text
role_id (PK)

role_name
```

Examples

```text
Admin

Moderator

User
```

---

## Permissions

```text
permission_id (PK)

permission_name
```

---

## Role_Permissions

```text
role_permission_id (PK)

role_id (FK)

permission_id (FK)
```

---

## Activity_Logs

```text
log_id (PK)

user_id (FK)

action

ip_address

created_at
```

---

# Admin ER Diagram

```text
ROLES

│

▼

ROLE_PERMISSIONS

│

▼

PERMISSIONS

USERS

│

▼

ACTIVITY_LOGS
```

---

# 21. Master Relationship Summary

```text
Users
│
├── User_Profile
├── User_Settings
├── User_Sessions
├── User_Interests
├── Buddy_Requests
├── Friendships
├── Blocked_Users
├── User_Locations
├── Communities
├── Community_Members
├── Community_Posts
├── Events
├── Event_Participants
├── Posts
├── Comments
├── Likes
├── Saved_Posts
├── Messages
├── Notifications
├── Reports
└── Activity_Logs
```

---

# 22. Estimated Database Size

| Module | Tables |
|---------|-------:|
| Authentication | 2 |
| User Management | 4 |
| Buddy Matching | 8 |
| Communities | 5 |
| Events | 5 |
| Social Feed | 4 |
| Chat | 4 |
| Notifications | 1 |
| Reports | 1 |
| Administration | 4 |

**Estimated Total:** **38 Tables**

---

# 23. Normalization

The database follows:

✅ First Normal Form (1NF)

✅ Second Normal Form (2NF)

✅ Third Normal Form (3NF)

This minimizes redundancy while maintaining efficient relationships.

---

# 24. Indexing Strategy

Indexes should be created on:

```text
email

username

community_name

event_date

city

interest_name

created_at

sender_id

receiver_id

chat_room_id
```

Composite indexes can also be added for:

- (user_id, created_at)
- (community_id, created_at)
- (event_id, user_id)

---

# 25. Design Considerations

The schema is designed to support:

- Scalability
- High performance
- Modular development
- Easy maintenance
- Future AI integration
- Cloud deployment
- Efficient querying

---

# 26. Conclusion

The NexBuddy database architecture provides a normalized, scalable, and extensible relational model that supports authentication, user profiles, buddy matching, communities, events, social interactions, messaging, notifications, reporting, and administration.

By separating concerns into dedicated modules and using clear relationships between entities, the database is well suited for implementation using PostgreSQL and Spring Data JPA.

This ER design forms the foundation for the backend implementation and will directly guide the creation of Spring Boot entity classes, repositories, and database migrations.

---
