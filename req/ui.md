# Library Management App - UI Documentation

This document outlines the UI structure and component details for both user and admin menus within the JavaFX library management app.

---

## User Menu

### 1. Home
- **Description**: Main landing page after login.
- **Components**:
    - **Header**: Title, User Profile Icon
        - Width: `100%`
        - Background Color: `#F0F0F0`
    - **Content Area**: Featured Books, Recommended Reads
        - Padding: `20px`
- **Preferred Width**: `800px`
- **Background Color**: `#FFFFFF`

### 2. Search
- **Description**: Page for searching books by title, author, or genre.
- **Components**:
    - **Search Bar**: Input field with search button
        - Width: `90%`
        - Padding: `10px`
    - **Results List**: Vertical list of search results
        - Component Type: `VBox`
        - Background Color: `#FAFAFA`
- **Preferred Width**: `800px`

### 3. Bookshelf
- **Description**: Page displaying user's saved books in categories.
- **Components**:
    - **Category Tabs**: Sections like Want to Read, Finished
        - Width: `100%`
        - Background Color: `#EFEFEF`
- **Preferred Width**: `800px`

### 4. GetAll
- **Description**: Page to view all books in the library's collection.
- **Components**:
    - **Book Grid**: Grid layout of all books
        - Component Type: `FlowPane`
        - Background Color: `#F9F9F9`
- **Preferred Width**: `800px`

### 5. Want to Read
- **Description**: Page for managing books marked "Want to Read."
- **Components**:
    - **List of Books**: Display books the user intends to read
        - Component Type: `VBox`
        - Padding: `10px`
- **Preferred Width**: `800px`

### 6. Finished
- **Description**: Page showing books marked as "Finished."
- **Components**:
    - **List of Finished Books**: Vertical list of completed books
        - Padding: `10px`
- **Preferred Width**: `800px`

---

## Admin Menu

### 1. Dashboard
- **Description**: Admin overview of user activity and library stats.
- **Components**:
    - **Metrics Panel**: Charts for loans, users, and inventory
        - Background Color: `#FFFFFF`
- **Preferred Width**: `800px`
- **Background Color**: `#FFFFFF`

### 2. User Page
- **Description**: Admin page to manage library users.
- **Components**:
    - **User List**: List of registered users
        - Component Type: `VBox`
        - Background Color: `#EFEFEF`
    - **Edit Panel**: Side panel to edit user details
- **Preferred Width**: `800px`

### 3. Loan Page
- **Description**: Page for managing book loans.
- **Components**:
    - **Loan List**: Vertical list of active loans
        - Component Type: `VBox`
        - Background Color: `#F5F5F5`
    - **Action Buttons**: Buttons to edit, renew, or remove loans
- **Preferred Width**: `800px`

### 4. Book Page
- **Description**: Page to manage books in the library.
- **Components**:
    - **Book List**: Display of all books with edit options
        - Component Type: `FlowPane`
        - Padding: `15px`
- **Preferred Width**: `800px`

---

## Authentication Pages

### Login
- **Description**: Page for users to log into the app.
- **Components**:
    - **Username Field**: Input field for the username
        - Width: `80%`
        - Padding: `10px`
    - **Password Field**: Input field for password
        - Width: `80%`
        - Padding: `10px`
    - **Login Button**: Button to submit login details
        - Width: `60%`
        - Background Color: `#4CAF50`
    - **Forgot Password Link**: Link to reset password
- **Preferred Width**: `500px`
- **Background Color**: `#FFFFFF`

### Register
- **Description**: Page for new users to create an account.
- **Components**:
    - **Username Field**: Input field for the username
        - Width: `80%`
        - Padding: `10px`
    - **Password Field**: Input field for password
        - Width: `80%`
        - Padding: `10px`
    - **Confirm Password Field**: Input field to confirm password
        - Width: `80%`
        - Padding: `10px`
    - **Register Button**: Button to submit registration details
        - Width: `60%`
        - Background Color: `#2196F3`
- **Preferred Width**: `500px`
- **Background Color**: `#FFFFFF`

---

> Each component is structured with `FlowPane` and `VBox` for layouts, ensuring consistency across the app's pages.