package org.sc2002.hospital.menu;

import java.util.HashMap;
import java.util.Scanner;

import org.sc2002.hospital.container.data.InventoryContainer;
import org.sc2002.hospital.container.user.StaffContainer;
import org.sc2002.hospital.record.Record;
import org.sc2002.hospital.record.data.Inventory;
import org.sc2002.hospital.record.user.Staff;

public class AdministratorMenu extends Menu {
    private StaffContainer staffContainer;
    private InventoryContainer inventoryContainer;
    private Scanner sc=new Scanner(System.in);

    public AdministratorMenu(StaffContainer staffContainer, InventoryContainer inventoryContainer) {
        super();
        this.staffContainer = staffContainer;
        this.inventoryContainer = inventoryContainer;
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
        int choice;

        do {
            System.out.println("\nManage Hospital Staff");
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
        System.out.println("NOT COMPLETED YET");
    }

    public void viewMedicationInventory(){
        Record record;
        String medicineName;
        int initialStock, alertLevel;
        HashMap<Integer, Record> inventoryRecords = inventoryContainer.getRecords();
        System.out.println("Medication Inventory: \nMedicine Name, Initial Stock, Alert Level");
        for (int i:inventoryRecords.keySet()) {
            record = inventoryRecords.get(i);
            medicineName = ((Inventory)record).getMedicineName();
            initialStock = ((Inventory)record).getCurrentStock();
            alertLevel = ((Inventory)record).getAlertThreshold();

            System.out.println(medicineName+", "+initialStock+", "+alertLevel);
        }
        System.out.println();
    }

    public void addMedication(){

        System.out.print("You are adding medication\nEnter medicine name: ");
        String medicineName = sc.next();

        if (inventoryContainer.containsMedicine(medicineName)) {
            System.out.println("Medicine already exists. Going back to the parent menu: ");
            return;
        }
        
        System.out.print("Enter initial stock: ");      //can add check for invalid (negative) stock
        int initialStock=sc.nextInt();
        System.out.print("Enter alert level: ");
        int alertLevel=sc.nextInt();

        Inventory inventory=new Inventory(
            medicineName,
            initialStock,
            alertLevel
        );
        inventoryContainer.putRecord(inventory);
        System.out.println("Medication added successfully\n");
    }

    public void removeMedication(){
        System.out.print("You are removing medication!\nEnter medicine name of medication to remove: ");
        String medicineName=sc.next();
        if (inventoryContainer.containsMedicine(medicineName)) {
            int recordID = inventoryContainer.getRecordIdByMedicineName(medicineName);
            inventoryContainer.removeRecord(recordID);
            System.out.println("Medication removed successfully");
        } else {
            System.out.println("Medication does not exist");
        }
    }

    public void updateMedicationStockLevel(){
        System.out.print("You are updating medication stock level!\nEnter medicine name of medication to update: ");
        String medicineName=sc.next();
        if (inventoryContainer.containsMedicine(medicineName)) {
            int recordID = inventoryContainer.getRecordIdByMedicineName(medicineName);
            System.out.print("Enter new stock level: ");
            int newStockLevel = sc.nextInt();
            ((Inventory)inventoryContainer.getRecord(recordID)).setCurrentStock(newStockLevel);
            System.out.println("Stock level updated successfully");
        } else {
            System.out.println("Medication does not exist");
        }
    }

    public void updateLowStockLevelAlertLine(){
        System.out.print("You are updating low stock level alert line!\nEnter medicine name of medication to update: ");
        String medicineName=sc.next();
        if (inventoryContainer.containsMedicine(medicineName)) {
            int recordID = inventoryContainer.getRecordIdByMedicineName(medicineName);
            System.out.print("Enter new low stock level alert line: ");
            int newAlertLevel = sc.nextInt();
            ((Inventory)inventoryContainer.getRecord(recordID)).setAlertThreshold(newAlertLevel);
            System.out.println("Low stock level alert line updated successfully");
        } else {
            System.out.println("Medication does not exist");
        }
    }

    public void approvePharmacistsReplenishmentRequests(){
        System.out.println("Approve Pharmacists' Replenishment Requests");
        System.out.println("NOT COMPLETED YET");
    }


    public void manageMedicationInventory(){
        int choice;

        do { 
            System.out.println("\nManage Medication Inventory");
            System.out.println("0. Back");
            System.out.println("1. View Medication Inventory");
            System.out.println("2. Add Medication");
            System.out.println("3. Remove Medication");
            System.out.println("4. Update Medication Stock Level");
            System.out.println("5. Update Low Stock Level Alert Line");
            System.out.println("6. Approve Pharmacists' Replenishment Requests ");
            System.out.print("Enter your choice: ");

            choice=sc.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Going back..."); break;

                case 1: 
                    viewMedicationInventory();
                    break;

                case 2:
                    addMedication();
                    break;
                
                case 3:
                    removeMedication();
                    break;

                case 4:
                    updateMedicationStockLevel();
                    break;

                case 5:
                    updateLowStockLevelAlertLine();
                    break;
                
                case 6:
                    approvePharmacistsReplenishmentRequests();
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);  
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
                    manageMedicationInventory();
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