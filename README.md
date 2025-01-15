# Conference Management System

The **Conference Management System** is a Java-based application designed to streamline the management of academic and professional conferences. It provides features for managing attendees, speakers, sessions, feedback, certificates, and more, using a graphical interface and backend data persistence.

---

## Features

### Attendee Features
- **Registration and Login**: Attendees can register or log in with a unique ID.
- **Session Management**: Browse, register, and view session schedules.
- **Feedback**: Provide feedback for attended sessions.
- **Certificates**: Generate and download participation certificates.

### Manager Features
- **Session Management**: Add and manage sessions, assign speakers, and track attendance.
- **Attendee Management**: Register attendees and view attendee data.
- **Reporting**: Generate detailed attendance and feedback reports.
- **Notifications**: Enable notifications to inform attendees of updates.

### Speaker Features
- **Session Assignment**: Manage assigned sessions.
- **Biography**: Display speaker details and expertise.

### Additional Features
- **Email Service**: Send email notifications and certificates with attachments.
- **Persistence**: Save and load session and attendee data using serialization.
- **GUI**: Separate graphical interfaces for attendees and managers.

---

## Code Structure

### Main Classes

#### **Main**
- Entry point of the application.
- Launches the `ManagerGUI` and `AttendeeGUI`.
- Simulates adding sessions, attendees, and generating reports.

#### **Attendee**
- Represents a conference participant.
- Manages personal information, schedule, attendance, and feedback.

#### **AttendeeGUI**
- GUI for attendees to interact with the system.
- Supports logging in, registering for sessions, providing feedback, and downloading certificates.

#### **ManagerGUI**
- GUI for conference managers.
- Allows adding sessions, registering attendees, and generating reports.

#### **Session**
- Represents a conference session.
- Tracks attendees, feedback, and attendance.

#### **Speaker**
- Represents a speaker at the conference.
- Includes biography and assigned session management.

#### **Feedback**
- Captures feedback provided by attendees for sessions.
- Includes rating, comments, and attendee information.

#### **CertificateManager**
- Generates certificates of participation.
- Sends certificates via email to attendees.

#### **EmailService**
- Handles sending plain emails and emails with attachments.
- Configurable for SMTP servers like Gmail.

#### **Notifications**
- Manages notification messages for attendees.
- Includes saving and loading notification logs.

#### **Report**
- Generates attendance and feedback reports for the conference.

#### **PersistenceManager**
- Handles data persistence through file-based serialization and text file management.

#### **Person (Abstract Class)**
- Base class for attendees and speakers.
- Encapsulates shared attributes like name and email.

---

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher.
- IDE such as Eclipse, IntelliJ, or BlueJ.

### Steps
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd conference-management-system
