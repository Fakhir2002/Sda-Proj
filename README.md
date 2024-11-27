
# CureTrack  

## Description  
**CureTrack** is a centralized hospital management system developed as part of the Software Design and Analysis (SDA) course. This system is designed to streamline hospital operations by centralizing resources, managing staff, and optimizing scheduling. Developed by **Syntegrity**, the project implements **Object-Oriented Programming (OOP)** principles, adheres to **GRASP Patterns**, and incorporates **Gang of Four (GoF) Design Patterns**.  

---

## Product Scope  

### Overview  
CureTrack focuses on simplifying basic administrative functions within hospitals, enabling efficient resource management. While it does not handle advanced patient data, it provides essential features to improve operational efficiency.  

### Key Functionalities  
- **Online Appointment Booking**  
- **Booking Management**  
- **Feedback and FAQs**  
- **Video Consultation**  
- **Hospital Addition**  
- **Integrated Billing**  
- **Room Allocation**  
- **Equipment Management**  
- **Staff Scheduling**  

---

## Project Objectives  
### Title  
CureTrack: A Centralized Hospital Management System for Streamlined Operations and Resource Optimization  

### Goals  
1. To centralize and streamline hospital resources and operations.  
2. To enhance staff management and scheduling.  
3. To provide an easy-to-use interface for online bookings, consultations, and feedback.  
4. To optimize resource allocation, including rooms and equipment.  

---

## Prerequisites  

### Software Requirements  
- **Java Development Kit (JDK)** 17 or later  
- **IntelliJ IDEA** (recommended) or a similar Java IDE with JavaFX support  
- **MySQL Workbench**  
- **JavaFX SDK**  
- **MySQL Connector JAR File**  

### Libraries and Tools  
Ensure the following are configured:  
- JavaFX SDK: Add as a library and configure VM options as follows:  
  ```
  --module-path "path_to_javafx_sdk/lib" --add-modules javafx.controls,javafx.fxml
  ```  
  Replace `"path_to_javafx_sdk"` with the actual path to your JavaFX SDK.  

- MySQL Connector JAR File: Include this in your project dependencies for database connectivity.  

---

## Directory Structure  

### Source Roots  
Ensure the following directories are marked as **Source Root** in your IDE:  
- `App`  
- `DB`  
- `java`  
- `OOP`  

### Database Files  
The SQL schema and scripts are located in:  
`src/main/DB/MYSQL_FILES/DB.sql`  
You must import this file into MySQL Workbench to set up the database.  

---

## Setup  

1. Clone the repository or download the project.  
2. Open the project in IntelliJ IDEA.  
3. Mark the directories (`App`, `DB`, `java`, `OOP`) as **Source Roots**:  
   - Right-click each folder in the project explorer.  
   - Select **Mark Directory as > Source Root**.  
4. Import the `DB.sql` file into MySQL Workbench:  
   - Open MySQL Workbench.  
   - Create a new schema or use an existing one.  
   - Import the SQL file located at `src/main/DB/MYSQL_FILES/DB.sql`.  
5. Configure the MySQL Connector JAR in the project:  
   - Add the JAR file to your project dependencies.  

---



## Database Configuration  

The database configuration is handled in the following interface:  
`src/main/DB/Database/DatabaseConfig.java`  

You can modify the database connection details by editing the interface:  

```java
public interface DatabaseConfig {
  String URL = "jdbc:mysql://localhost:3306/user"; 
  String USER = "root";
  String PASSWORD = "12345678";
}

  ```  
Make sure to replace "user", "root", and "12345678" with your actual MySQL database name, username, and password.
---

## Running the Application  

1. Navigate to the `src/main/App/Application` directory.  
2. Locate the file `HomePageApplication.java`.  
3. Run the application directly from the IDE.  


---

## Design Patterns  

The project demonstrates adherence to the following design principles:  

- **Object-Oriented Programming (OOP)**:  
  - Encapsulation, inheritance, polymorphism, and abstraction are implemented across the application.  

- **GRASP Patterns**:  
  - Patterns like **Controller**, **Creator**, and **Information Expert** are used for better responsibility allocation.  

- **Gang of Four (GoF) Patterns**:  
  - Patterns such as **Singleton**, **Factory**, and **Observer** are integrated for reusable and scalable design.  

---

## Features  

1. **User-Friendly UI**:  
   - Built with JavaFX and FXML for an interactive experience.  

2. **Hospital Management**:  
   - Includes appointment booking, staff scheduling, room allocation, and equipment management.  

3. **Video Consultations**:  
   - Facilitates online consultations for patients.  

4. **Feedback System**:  
   - Provides a platform for users to submit feedback and access FAQs.  

5. **Database Integration**:  
   - Connects seamlessly with MySQL Workbench for data storage and retrieval.  

---

## Known Limitations  

- Advanced patient data management is not included in this version.  
- Requires manual setup of the database schema in MySQL Workbench.  

---

## License  

This project is licensed under the [MIT License](LICENSE).  
