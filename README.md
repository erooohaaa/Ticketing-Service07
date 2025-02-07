# 🎟️ Ticketing-Service07
- Event Management System
Ticketing-Service07 is an event management system that allows creating, editing, deleting, and viewing events, managing categories, and handling user roles (Admin, User). It uses PostgreSQL, JDBC, DAO, SOLID principles, and implements JOIN for detailed event information. 📌 GitHub 🚀
# 🚀 Launch Instructions
# 1️⃣ Install PostgreSQL and Create a Database
Run the following SQL command in PostgreSQL:
CREATE DATABASE TicketSystemDB;
# 2️⃣ Configure Database Connection (DatabaseConfig.java)
Modify the credentials in DatabaseConfig.java:
java
private static final String URL = "jdbc:postgresql://localhost:5432/TicketSystemDB";
private static final String USER = "postgres";
private static final String PASSWORD = "0000";
# 3️⃣ Build and Run the Project