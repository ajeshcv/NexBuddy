# Use Case Document

# NexBuddy

**Version:** 1.0

**Project:** NexBuddy

**Prepared By:** Ajesh CV


---

# Table of Contents

1. Introduction
2. Purpose
3. Scope
4. Actors
5. Use Case Diagram
6. Guest Use Cases
7. User Use Cases
8. Community Moderator Use Cases
9. Administrator Use Cases
10. Use Case Descriptions
11. Include & Extend Relationships
12. Summary

---

# 1. Introduction

The Use Case document describes how different actors interact with the NexBuddy system. It identifies the functionalities available to each actor and defines the system behavior for every interaction.

This document serves as the foundation for application development, testing, and UI design.

---

# 2. Purpose

The purpose of this document is to:

* Identify all system actors
* Define user interactions
* Describe system functionality
* Help developers understand system behavior
* Assist testers in preparing test cases

---

# 3. Scope

The document covers all major NexBuddy modules:

* Authentication
* User Profile
* Buddy Matching
* Communities
* Events
* Social Feed
* Chat
* Notifications
* Reporting
* Administration

---

# 4. Actors

## Guest

An unregistered visitor.

Permissions:

* View Landing Page
* Register
* Login
* Browse Public Communities
* Browse Public Events

---

## Registered User

A verified user of the platform.

Permissions:

* Manage Profile
* Find Buddies
* Join Communities
* Create Events
* Chat
* Create Posts
* Report Users
* Receive Notifications

---

## Community Moderator

A user with additional permissions inside a community.

Permissions:

* Approve Members
* Remove Members
* Delete Community Posts
* Moderate Discussions

---

## Administrator

System administrator.

Permissions:

* Manage Users
* Manage Communities
* Manage Events
* Review Reports
* View Analytics
* Configure Platform

---

# 5. Overall Use Case Diagram

```text
                           +----------------+
                           |     Guest      |
                           +----------------+
                              |        |
                              |        |
                     Register |        | Login
                              |        |
                              ▼        ▼

                    +----------------------+
                    | Registered User      |
                    +----------------------+
                     |   |   |   |   |   |
                     |   |   |   |   |   |
                     ▼   ▼   ▼   ▼   ▼   ▼
                 Profile Buddy Community Event Chat Feed
                     |
                     ▼
                Notifications
                     |
                     ▼
                  Reporting

                     ▲
                     |
             +------------------+
             | Community Mod.   |
             +------------------+
                     |
                     ▼
           Moderate Communities

                     ▲
                     |
              +---------------+
              | Administrator |
              +---------------+
                     |
                     ▼
      User Management / Reports / Analytics
```

---

# 6. Guest Use Cases

## UC-01 Register

Actor

Guest

Description

Creates a new NexBuddy account.

Preconditions

* User is not registered.

Postconditions

* Account is created.
* Verification email is sent.

---

## UC-02 Login

Actor

Guest

Description

Logs into the system.

Preconditions

* Registered account exists.

Postconditions

* User Dashboard opens.

---

## UC-03 Browse Public Content

Actor

Guest

Description

View public events and communities.

---

# 7. Registered User Use Cases

## UC-04 Manage Profile

Description

Users can:

* Edit Profile
* Upload Profile Picture
* Add Interests
* Update Privacy Settings

---

## UC-05 Find Buddy

Description

Users search for buddies based on:

* Interests
* Location
* Activity
* Availability

---

## UC-06 Send Buddy Request

Description

Send buddy requests to other users.

Possible Outcomes

* Accepted
* Rejected
* Cancelled

---

## UC-07 Manage Friends

Users can:

* View Friends
* Remove Friends
* Block Users

---

## UC-08 Create Community

Users create communities.

Input

* Name
* Description
* Category
* Privacy

Output

Community Created

---

## UC-09 Join Community

Users join communities.

---

## UC-10 Leave Community

Users leave joined communities.

---

## UC-11 Create Event

Users create events.

Information

* Title
* Date
* Time
* Venue
* Description
* Participants

---

## UC-12 Join Event

Users join available events.

---

## UC-13 Cancel Event Participation

Users leave events.

---

## UC-14 Create Post

Users publish:

* Text
* Images
* Videos

---

## UC-15 Like Post

Users like posts.

---

## UC-16 Comment on Post

Users add comments.

---

## UC-17 Save Post

Users bookmark posts.

---

## UC-18 Chat

Users exchange:

* Text
* Images
* Files
* Voice Notes

---

## UC-19 Receive Notifications

Notifications include:

* Messages
* Friend Requests
* Community Updates
* Event Invitations

---

## UC-20 Report User

Users report:

* Fake Accounts
* Spam
* Abuse
* Harassment

---

# 8. Community Moderator Use Cases

## UC-21 Approve Members

Approve pending membership requests.

---

## UC-22 Remove Members

Remove users violating community rules.

---

## UC-23 Delete Posts

Delete inappropriate posts.

---

## UC-24 Moderate Discussions

Monitor community activities.

---

# 9. Administrator Use Cases

## UC-25 Manage Users

Admin can:

* View Users
* Suspend Accounts
* Delete Accounts
* Reset Passwords

---

## UC-26 Manage Communities

Admin can:

* Delete Communities
* Suspend Communities
* Review Reports

---

## UC-27 Manage Events

Admin can:

* Remove Events
* Feature Events
* Monitor Activities

---

## UC-28 Manage Reports

Admin reviews reports.

Possible Actions

* Warning
* Suspension
* Permanent Ban

---

## UC-29 Manage Categories

Admin creates:

* Activity Categories
* Community Categories
* Event Categories

---

## UC-30 View Analytics

Dashboard displays:

* Total Users
* Active Users
* Communities
* Events
* Reports
* Daily Registrations

---

# 10. Detailed Use Case Description

## UC-05 Find Buddy

### Actor

Registered User

### Goal

Find users with similar interests.

### Preconditions

* User logged in.
* Profile completed.

### Main Flow

1. Open Buddy Search.
2. Select interests.
3. Choose location.
4. Apply filters.
5. View matching users.
6. Open profile.
7. Send buddy request.

### Alternate Flow

* No matching users found.
* Filters return empty results.

### Postconditions

Buddy request sent.

---

## UC-11 Create Event

### Actor

Registered User

### Goal

Create a new event.

### Preconditions

* Logged in.

### Main Flow

1. Open Events.
2. Click Create Event.
3. Enter details.
4. Upload image.
5. Publish.

### Postconditions

Event visible to eligible users.

---

## UC-18 Chat

### Actor

Registered User

### Goal

Communicate with another user.

### Preconditions

* Both users have permission to chat.

### Main Flow

1. Open Chat.
2. Select conversation.
3. Type message.
4. Send.
5. Receiver receives instantly.

### Alternate Flow

Receiver Offline

↓

Store Message

↓

Deliver Later

---

## UC-20 Report User

### Preconditions

User logged in.

### Main Flow

1. Open profile.
2. Click Report.
3. Select reason.
4. Add description.
5. Submit.

### Postconditions

Admin receives report.

---

# 11. Include Relationships

The following use cases always include other use cases.

```text
Register
    └── Email Verification

Login
    └── JWT Authentication

Create Event
    └── Upload Image

Create Community
    └── Select Category

Chat
    └── Notification

Buddy Request
    └── Notification

Join Community
    └── Membership Validation

Join Event
    └── Participant Registration
```

---

# 12. Extend Relationships

Optional behavior.

```text
Create Event
      └── Invite Friends

Profile
      └── Upload Cover Photo

Chat
      └── Send File

Community
      └── Assign Moderator

Post
      └── Add Image

Event
      └── Add Location

Buddy
      └── Leave Review
```

---

# 13. Use Case Summary

| Actor               | Number of Use Cases |
| ------------------- | ------------------: |
| Guest               |                   3 |
| Registered User     |                  17 |
| Community Moderator |                   4 |
| Administrator       |                   6 |

**Total Use Cases:** 30

---

# 14. Conclusion

The NexBuddy Use Case Model defines the interaction between the system and its actors. It ensures that all functional requirements identified in the Software Requirements Specification are represented as user interactions.

This document serves as a reference for system design, user interface development, backend implementation, API design, and software testing throughout the NexBuddy project lifecycle.

---

