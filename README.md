# SC2002 Hospital Management System
## Introduction
This is a hospital management system developed for SC2002, a software engineering course at Nanyang Technological University. The system is designed to manage the hospital's patients, doctors, and appointments. It allows the hospital to manage their resources and schedule appointments with patients. The system is designed to be user-friendly and easy to use, with a command line based user interface.

## Key Features
- User authentication and authorization
- Appointment scheduling
- Prescription management
- Medication inventory management
- Staff management

## User Guide
### Login
Enter hospital ID and password to login to the system. The system will show a role-specific menu according to the user's role.
Sample Accounts for Testing:
| Hospital ID | Password | Role |
|-------------|----------|------|
| P1001       | password | Patient |
| P1002       | password | Patient |
| P1003       | password | Patient |
| D001        | password | Doctor |
| D002        | password | Doctor |
| P001        | password | Pharmacist |
| A001        | password | Administrator |

### Role-specific Menu
#### Menu
After a you log in as a specific role, you can access the menu. You can choose from the shown options and perform the desired action.
1. View Personal Medical Record
2. Change Personal Information
3. Manage Appointments
4. View Past Appointment Outcomes
5. Change Password
0. Log Out

#### Doctor Menu
1. Manage Patient Records
2. Manage Past Appointments
3. Manage Future Appointments
4. View Appointment Outcome Records
5. Change Password
0. Log Out

#### Pharmacist Menu
1. View Appointment Outcome and Manage Prescriptions
2. View Medication Inventory and Request Replenishment
3. Change Password
0. Log Out

#### Administrator Menu
1. View and Manage Hospital Staff
2. View Appointments Details
3. View and Manage Medication Inventory
4. Change Password
0. Log Out
