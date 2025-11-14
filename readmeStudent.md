
# **Student Management System – Java Swing Project**

A lightweight and user-friendly **Student Management System** built using **Java Swing**, **JDBC**, and the **DAO design pattern**. This project demonstrates how to combine GUI programming, OOP principles, and database operations to create a functional desktop application for managing student records.

---

## 🚀 **Features**

* **Add New Students**
  Enter student information such as name, roll number, branch, and age and save it to the database.

* **View All Students**
  Displays all student records in a structured table format.

* **Update Student Details**
  Modify existing records with ease.

* **Delete Students**
  Remove any student entry directly from the system.

* **Clean & Simple GUI**
  Built using Java Swing for easy navigation and a smooth user experience.

* **DAO Architecture**
  Separates database logic from UI for improved maintainability and scalability.

---

## 🧩 **Project Structure**

```
/src
 ├── MainGui.java         # Handles the GUI and user interactions
 ├── Student.java         # OOP model class for student entity
 └── StudentDAO.java      # Database operations (insert, update, delete, fetch)
```

---

## ⚙️ **Technologies Used**

* **Java (JDK 8+)**
* **Java Swing**
* **JDBC**
* **MySQL / Local Database**
  *(You can switch to any other DB as DAO is modular)*

---

## 📦 **How to Run**

1. Install **Java JDK 8 or above**.
2. Set up a **MySQL database** (or your preferred DB).
3. Update database credentials in `StudentDAO.java`.
4. Compile and run:

```
javac *.java
java MainGui
```

---

## 📚 **Learning Outcomes**

This project helps you understand:

* GUI development with Java Swing
* OOP concepts (classes, objects, encapsulation)
* Event handling
* DAO pattern and database abstraction
* JDBC database connectivity
* MVC-style architecture
* Error handling and data validation

---

## 📌 **Future Enhancements**

* Search and filter functionality
* Sorting records
* Export database to CSV/PDF
* Login authentication system
* Better UI using JavaFX

---

 📜 **License**

This project is free to use for learning and academic purposes.


