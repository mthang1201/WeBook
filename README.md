# library_management


## Build Instructions

1. **Clone the Project**  
   First, clone this project and open it in IntelliJ IDEA.

2. **Start MySQL Server**  
   Start your MySQL server and import the `.sql` file located at `{project_dir}/src/main/resources/org/uet/library_management/data/library.sql`.

3. **Database Configuration**  
   Your database should be running on port 3306 with the following credentials:
   - **User:** root
   - **Password:** root  
   (If your server uses different credentials, please update the configuration in the code.)

4. **Reload Maven Dependencies**  
   After setting up the database, reload `pom.xml` to retrieve the necessary packages from Maven.

5. **Run the Application**  
   Finally, click the green "Run" button in the IDE, and you're good to go!


## Contributors

This project is maintained by:

- Bui Minh Thang (23020646@vnu.edu.vn)
- Hoang Quoc Duong (jane.smith@example.com)
- Vu Ngoc Duc (alice.johnson@example.com)

Licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
