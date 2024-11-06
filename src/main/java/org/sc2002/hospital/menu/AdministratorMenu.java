package org.sc2002.hospital.menu;

import java.util.HashMap;
import java.util.Scanner;

import org.sc2002.hospital.container.user.StaffContainer;
import org.sc2002.hospital.record.Record;
import org.sc2002.hospital.record.user.Staff;

public class AdministratorMenu extends Menu {
    private StaffContainer staffContainer;
    private Scanner sc=new Scanner(System.in);

    public AdministratorMenu(StaffContainer staffContainer) {
        super();
        this.staffContainer = staffContainer;
    }

    public void displayStaffList(){
        Record record;
        String hospitalID, name, gender, userType;
        int age;
        HashMap<Integer, Record> staffRecords = staffContainer.getRecords();
        System.out.println("Staff List: \nHospital ID, Name, Gender, User Type, Age");
        for (int i:staffRecords.keySet()) {
            record = staffRecords.get(i);
            hospitalID = ((Staff)record).getHospitalId();
            name = ((Staff)record).getName();
            gender = ((Staff)record).getGender();
            userType = ((Staff)record).getUserType();
            age = ((Staff)record).getAge();

            System.out.println(hospitalID+", "+name+", "+gender+", "+userType+", "+age);
        }
        System.out.println();
    }

    public void addStaff(){
        System.out.print("Your are adding Staff\nPlease enter hospital ID: ");
        boolean exists = true;
        String hospitalId;
        do { 
            hospitalId=sc.next();
            if (!staffContainer.containsUser(hospitalId)) {
                exists = false;
            } else {
                System.out.println("Hospital ID already exists. Please enter a new one: ");
            }
        } while (exists);

        System.out.println("Enter name: ");
        String name=sc.next();
        System.out.println("Enter gender: ");
        String gender = sc.next();
        System.out.println("Enter user type: ");
        String userType = sc.next();
        System.out.println("Enter age: ");
        int age = sc.nextInt();

        Staff staff=new Staff(
                hospitalId,
                "password",
                name,
                gender,
                userType,
                "",
                age
            );
        staffContainer.putRecord(staff);
        System.out.println("Staff added successfully\n");
    }

    public void removeStaff(){
        System.out.print("You are removing staff!\nEnter hospital ID of staff to remove: ");
        String hospitalId=sc.next();
        if (staffContainer.containsUser(hospitalId)) {
            int recordID = staffContainer.getRecordIdByHospitalId(hospitalId);
            staffContainer.removeRecord(recordID);
            System.out.println("Staff removed successfully");
        } else {
            System.out.println("Staff does not exist");
        }
    }

    public void updateStaff(){
        System.out.print("You are updating staff!\nEnter hospital ID of staff to update: ");
        String hospitalId=sc.next();
        if (staffContainer.containsUser(hospitalId)) {
            int recordID = staffContainer.getRecordIdByHospitalId(hospitalId);
            
            int choice = 5;
            do{
                System.out.println("What do you want to update?\n1. Name\n2. Gender\n3. User Type\n4. Age\n0. Back");
                choice = sc.nextInt();
                switch (choice){
                    case 0:
                        System.out.println("Going back..."); break;
                    case 1:
                        System.out.println("Enter new name: ");
                        String name = sc.next();
                        ((Staff)staffContainer.getRecord(recordID)).setName(name);
                        break;
                    case 2:
                        System.out.println("Enter new gender: ");
                        String gender = sc.next();
                        ((Staff)staffContainer.getRecord(recordID)).setGender(gender);
                        break;
                    case 3:
                        System.out.println("Enter new user type: ");
                        String userType = sc.next();
                        ((Staff)staffContainer.getRecord(recordID)).setUserType(userType);
                        break;
                    case 4:
                        System.out.println("Enter new age: ");
                        int age = sc.nextInt();
                        ((Staff)staffContainer.getRecord(recordID)).setAge(age);
                        break;
                    case 5:
                        System.out.println("Enter new hospital ID: ");
                        
                        String newHospitalId;
                        Boolean exists = true;
                        do { 
                            newHospitalId = sc.next();
                            if (!staffContainer.containsUser(newHospitalId)) {
                                exists = false;
                            } else {
                                System.out.println("Hospital ID already exists. Please enter a new one: ");
                            }
                        } while (exists);
                        ((Staff)staffContainer.getRecord(recordID)).setHospitalId(newHospitalId);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } while (choice!=0);
        } else {
            System.out.println("Staff does not exist");
        }
    }
        
    void manageStaff(){
        Scanner sc=new Scanner(System.in);
        int choice, age;
        String hospitalId, name, gender, userType;

        do {
            System.out.println("Manage Hospital Staff");
            System.out.println("0. Back");
            System.out.println("1. View Staff");
            System.out.println("2. Add Staff");
            System.out.println("3. Remove Staff");
            System.out.println("4. Update Staff\n");

           

            System.out.print("Enter your choice: ");
            choice=sc.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Going back..."); break;
                case 1:
                    displayStaffList();
                    break;
                case 2:
                    addStaff();
                    break;
                case 3:
                    removeStaff();
                   //remove staff
                    break;
                case 4:
                    updateStaff();
                    //update staff
                    break;
                default: 
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);

    }


    public void manageAppointments(){
        System.out.println("Appointments details");
        

    }










    @Override
    public void run(){
        Scanner sc=new Scanner(System.in);
        int choice;
        do {
            System.out.println("Administrator Menu");
            System.out.println("0. Log out");
            System.out.println("1. View and manage hospital staff");
            System.out.println("2. View appointments details");
            System.out.println("3. View and Manage Medication Inventory ");
            System.out.println("4. Approve Replenishment Requests ");
            System.out.print("Enter your choice: ");
            choice=sc.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Logging out..."); break;
                case 1:
                    manageStaff();
                    break;
                case 2:
                    manageAppointments();
                    break;
                case 3:
                    // displayMedicationInventory();
                    // manageMedicationInventory();
                    break;
                case 4:
                    // displayReplenishmentRequests();
                    // approveReplenishmentRequests();
                    break;      
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }
}









// extra code
            // System.out.println("View appointments details:");
            // System.out.println("5. View Appointments\n");

            // System.out.println("Manage Medication Inventory: ");
            // System.out.println("6. View Medication Inventory");
            // System.out.println("7. Add Medication");
            // System.out.println("8. Remove Medication");
            // System.out.println("9. Update Medication\n");

            // System.out.println("Approve Replenishment Requests: ");
            // System.out.println("10. View and Approve Replenishment Requests");