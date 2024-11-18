 package org.sc2002.hospital.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.sc2002.hospital.container.ContainerLoader;
import org.sc2002.hospital.container.data.AppointmentContainer;
import org.sc2002.hospital.container.data.InventoryContainer;
import org.sc2002.hospital.container.data.ReplenishContainer;
import org.sc2002.hospital.container.user.PatientContainer;
import org.sc2002.hospital.container.user.StaffContainer;
import org.sc2002.hospital.record.Record;
import org.sc2002.hospital.record.data.Appointment;
import org.sc2002.hospital.record.data.Inventory;
import org.sc2002.hospital.record.data.Replenish;
import org.sc2002.hospital.record.user.Patient;
import org.sc2002.hospital.record.user.Staff;
import org.sc2002.hospital.record.user.User;
import org.sc2002.hospital.utility.Utility;

public class AdministratorMenu implements Menu {
    private final StaffContainer staffContainer;
    private final InventoryContainer inventoryContainer;
    private final AppointmentContainer appointmentContainer;
    private final PatientContainer PatientContainer;
    private final ReplenishContainer replenishContainer;
    private final int adminRecordId;

    private final Scanner sc;

    public AdministratorMenu(int adminRecordId,ContainerLoader containerLoader) {
        super();
        this.adminRecordId = adminRecordId;
        this.staffContainer = containerLoader.getStaffContainer();
        this.inventoryContainer = containerLoader.getInventoryContainer();
        this.appointmentContainer = containerLoader.getAppointmentContainer();
        this.PatientContainer = containerLoader.getPatientContainer();
        this.replenishContainer = containerLoader.getReplenishContainer();
        sc=new Scanner(System.in);
    }

// ====================================  Staff  ================================================================

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
        // do { 
        //     hospitalId=Utility.inputNonEmptyString(sc);
        //     if (!staffContainer.containsUser(hospitalId)) {
        //         exists = false;
        //     } else {
        //         System.out.println("Hospital ID already exists. Please enter a new one: ");
        //     }
        // } while (exists);

        /// Very Strange: 
        /// the do-while loop above will cause inexpected error in following inputs. 
        /// The following while-loop does not have this problem.
        /// I don't know why.

        while (true) { 
            hospitalId=Utility.inputNonEmptyString(sc);
            if (!staffContainer.containsUser(hospitalId)) {
                break;
            } else if (hospitalId.equals("")) {
                System.out.println("Invalid input");
            }
            else {
                System.out.println("Hospital ID already exists. Please enter a new one: ");
            }
        }

        System.out.println("Enter name: ");
        String name=Utility.inputNonEmptyString(sc);
        System.out.println("Enter gender: ");
        String gender = Utility.inputNonEmptyString(sc);
        System.out.println("Enter user type: ");
        String userType = Utility.inputNonEmptyString(sc);
        System.out.println("Enter age: ");
        int age=Utility.inputSafeInt(sc);

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
        String hospitalId=Utility.inputNonEmptyString(sc);
        if (hospitalId.equals("")) {
            System.out.println("Invalid input");
        } else if (staffContainer.containsUser(hospitalId)) {
            int recordID = staffContainer.getRecordIdByHospitalId(hospitalId);
            staffContainer.removeRecord(recordID);
            System.out.println("Staff removed successfully");
        } else {
            System.out.println("Staff does not exist");
        }
    }

    public void updateStaff(){
        System.out.print("You are updating staff!\nEnter hospital ID of staff to update: ");
        String hospitalId=Utility.inputNonEmptyString(sc);
        if (staffContainer.containsUser(hospitalId)) {
            int recordID = staffContainer.getRecordIdByHospitalId(hospitalId);
            
            int choice = 5;
            do{
                System.out.println("What do you want to update?\n1. Name\n2. Gender\n3. User Type\n4. Age\n0. Back");
                choice = Utility.inputSafeInt(sc);
                switch (choice){
                    case 0:
                        System.out.println("Going back..."); break;
                    case 1:
                        System.out.println("Enter new name: ");
                        String name = Utility.inputNonEmptyString(sc);
                        ((Staff)staffContainer.getRecord(recordID)).setName(name);
                        break;
                    case 2:
                        System.out.println("Enter new gender: ");
                        String gender = Utility.inputNonEmptyString(sc);
                        ((Staff)staffContainer.getRecord(recordID)).setGender(gender);
                        break;
                    case 3:
                        System.out.println("Enter new user type: ");
                        String userType = Utility.inputNonEmptyString(sc);
                        ((Staff)staffContainer.getRecord(recordID)).setUserType(userType);
                        break;
                    case 4:
                        System.out.println("Enter new age: ");
                        int age = Utility.inputSafeInt(sc);
                        ((Staff)staffContainer.getRecord(recordID)).setAge(age);
                        break;
                    case 5:
                        System.out.println("Enter new hospital ID: ");
                        
                        String newHospitalId;
                        Boolean exists = true;
                        do { 
                            newHospitalId = Utility.inputNonEmptyString(sc);
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
            System.out.println("1. View Staff");
            System.out.println("2. Add Staff");
            System.out.println("3. Remove Staff");
            System.out.println("4. Update Staff\n");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            choice=Utility.inputSafeInt(sc);
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

// ====================================  Appointments  ================================================================

    public void viewAppointments(){
        int choice = 5;
        do{
            System.out.println("\nAppointments details");
            System.out.println("Docter ID, Patient ID, Date & Time, Status, Appointment Outcome Record");
            System.out.println("filter according to:");
            System.out.println("1. Doctor ID");
            System.out.println("2. Patient ID");
            System.out.println("3. Date & Time");
            System.out.println("4. Status");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            choice=Utility.inputSafeInt(sc);
            switch (choice) {
                case 0:
                    System.out.println("Going back..."); break;
                case 1:
                    adminViewAppointementsByDoctorId();
                    break;
                case 2:
                    adminViewAppointmentsByPatientId();
                    break;
                case 3:
                    adminViewAppointmentsByDate();
                    break;
                case 4:
                    adminViewAppointmentsByStatus();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }while(choice!=0);
    }

    public void adminViewAppointementsByDoctorId(){
        HashSet<String> doctorIds=new HashSet<>();

        for (int recordId : staffContainer.getRecords().keySet()) {
            Staff staff = (Staff)staffContainer.getRecord(recordId);
            if (staff.getUserType().equals("Doctor")) {
                doctorIds.add(staff.getHospitalId());
                System.out.println("Doctor ID: " + staff.getHospitalId() + " - Name: " + staff.getName());
            }
        }

        System.out.print("Enter Doctor ID: ");
        String doctorId=Utility.inputNonEmptyString(sc);
        if (doctorIds.contains(doctorId)) {
            int doctorRecordId = staffContainer.getRecordIdByHospitalId(doctorId);
            ArrayList<Appointment> appointments = appointmentContainer.searchAppointmentsByDoctorrecordId(doctorRecordId);
            if (appointments.isEmpty()) {
                System.out.println("No appointments found.");
                return;
            }
            System.out.println("\n=== "+doctorId+"'s  Appointments ===");
            for (Appointment apt : appointments) {
                if (apt.getStatus()==Appointment.Status.UNAVAILABLE) continue;
                Patient patient = (Patient)PatientContainer.getRecord(apt.getPatientRecordId());
                //String patientId = patient.getHospitalId();
                System.out.println("ID: " + apt.getRecordId() + 
                                 "\nDate: " + apt.getDate() +
                                 "\nDoctor: " + patient.getName() +
                                 "\nStatus: " + apt.getStatus() + "\n");
            }

        } else {
            System.out.println("Doctor ID does not exist");
        }
    }

    public void adminViewAppointmentsByPatientId(){
        HashSet<String> patientIds=new HashSet<>();

        for(int recordId : PatientContainer.getRecords().keySet()){
            Patient patient = (Patient)PatientContainer.getRecord(recordId);
            patientIds.add(patient.getHospitalId());
            System.out.println("Patient ID: " + patient.getHospitalId() + " - Name: " + patient.getName());
        }

        System.out.print("Enter Patient ID: ");
        String patientId=Utility.inputNonEmptyString(sc);
        if (patientIds.contains(patientId)) {
            int patientRecordId = PatientContainer.getRecordIdByHospitalId(patientId);
            ArrayList<Appointment> appointments = appointmentContainer.searchAppointmentsByPatientrecordId(patientRecordId);
            if (appointments.isEmpty()) {
                System.out.println("No appointments found.");
                return;
            }
            System.out.println("\n=== "+patientId+"'s  Appointments ===");
            for (Appointment apt : appointments) {
                Staff doctor = (Staff)staffContainer.getRecord(apt.getDoctorRecordId());
                //String doctorId = doctor.getHospitalId();
                System.out.println("ID: " + apt.getRecordId() + 
                                 "\nDate: " + apt.getDate() +
                                 "\nDoctor: " + doctor.getName() +
                                 "\nStatus: " + apt.getStatus() + "\n");
            }

        } else {
            System.out.println("Patient ID does not exist");
        }
    }

    public void adminViewAppointmentsByDate(){          //need choice select
        int choice;
        String dateX;
        int number = 1;
        ArrayList<String> dateList = Utility.allDateList();
        System.out.println("Available dates: ");
        for (String date: dateList) {
            System.out.println(number + ". "+date);
            number++;
        }
        
        do{
            System.out.print("Enter your choice: ");
            choice = Utility.inputSafeInt(sc);
            if(choice>=1 && choice<=dateList.size()){
                dateX = dateList.get(choice-1);
                break;
            }else{
                System.out.println("Invalid choice\nPlease enter a valid choice");
            }
        }while(true);
        



        for (int recordId : staffContainer.getRecords().keySet()) {
            Staff staff = (Staff)staffContainer.getRecord(recordId);

            if (staff.getUserType().equals("Doctor")) {

                System.out.println("\nDoctor ID: " + staff.getHospitalId() + " - Name: " + staff.getName() + "on " + dateX);
                Appointment appointment = appointmentContainer.searchAppointmentByDateAndDoctorId(recordId,dateX);

                if (appointment == null) {
                    System.out.println("No appointments found.");
                    continue;
                }

                Patient patient = (Patient)PatientContainer.getRecord(appointment.getPatientRecordId());
                Staff doctor = (Staff)staffContainer.getRecord(appointment.getDoctorRecordId());
                //String patientId = patient.getHospitalId();
                //String doctorId = doctor.getHospitalId();
                System.out.println("ID: " + appointment.getRecordId() + 
                                "\nPatient: " + patient.getName() +
                                "\nDoctor: " + doctor.getName() +
                                "\nStatus: " + appointment.getStatus() + "\n");
            }
        }        
    }

    // public void adminViewDateList(){
    //     System.out.println("Available dates: ");
    //     for (String date: Utility.futureDateList()) {
    //         System.out.println(date);
    //     }
    // }

    public void adminViewAppointmentsByStatus(){ 
        Appointment.Status status;
        int choiceStatus;
        List<String> statusList = Arrays.asList("CONFIRMED", "PENDING", "COMPLETED", "CANCELLED");    
        System.out.println("Enter status: (1. CONFIRMED\n2. PENDING \n3. COMPLETED\n4. CANCELLED)");
        choiceStatus = Utility.inputSafeInt(sc);
        status = Appointment.Status.valueOf(statusList.get(choiceStatus-1));

        ArrayList<Appointment> appointments = appointmentContainer.searchAppointmentsByStatus(status);
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.println("\n=== Appointments with status: "+status+" ===");
        for (Appointment apt : appointments) {
            Patient patient = (Patient)PatientContainer.getRecord(apt.getPatientRecordId());
            Staff doctor = (Staff)staffContainer.getRecord(apt.getDoctorRecordId());
            //String patientId = patient.getHospitalId();
            //String doctorId = doctor.getHospitalId();
            System.out.println("ID: " + apt.getRecordId() + 
                             "\nDate: " + apt.getDate() +
                             "\nPatient: " + patient.getName() +
                             "\nDoctor: " + doctor.getName() + "\n");
        }
    }


// ====================================  Medication Inventory  ================================================================
    /*
    public void viewMedicationInventory(){
        Record record;
        String medicineName;
        int initialStock, alertLevel;
        HashMap<Integer, Record> inventoryRecords = inventoryContainer.getRecords();
        System.out.println("Medication Inventory: \nMedicine Name, Current Stock, Alert Level");
        for (int i:inventoryRecords.keySet()) {
            record = inventoryRecords.get(i);
            medicineName = ((Inventory)record).getMedicineName();
            initialStock = ((Inventory)record).getCurrentStock();
            alertLevel = ((Inventory)record).getAlertThreshold();

            System.out.println(medicineName+", "+initialStock+", "+alertLevel);
        }
        System.out.println();
    }
    */

    private ArrayList<Inventory> viewMedicationInventory() {
        ArrayList<Inventory> inventoryList=new ArrayList<>();
        for (int recordId : inventoryContainer.getRecords().keySet()) {
            inventoryList.add((Inventory)inventoryContainer.getRecord(recordId));
        }
        for (int i=0;i<inventoryList.size();i++) {
            System.out.println((i+1)+": "+inventoryList.get(i).getMedicineName()+", "+inventoryList.get(i).getCurrentStock()+", "+inventoryList.get(i).getAlertThreshold());
        }
        return inventoryList;
    }

    public void addMedication(){
        System.out.print("You are adding a new medication\nEnter new medicine name: ");
        String medicineName = Utility.inputNonEmptyString(sc);

        if (inventoryContainer.containsMedicine(medicineName)) {
            System.out.println("Medicine already exists. Going back to the parent menu: ");
            return;
        }
        
        System.out.print("Enter initial stock: ");      //can add check for invalid (negative) stock
        int initialStock=Utility.inputSafeInt(sc);
        System.out.print("Enter alert level: ");
        int alertLevel=Utility.inputSafeInt(sc);

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
        String medicineName=Utility.inputNonEmptyString(sc);
        if (inventoryContainer.containsMedicine(medicineName)) {
            int recordID = inventoryContainer.getRecordIdByMedicineName(medicineName);
            inventoryContainer.removeRecord(recordID);
            System.out.println("Medication removed successfully");
        } else {
            System.out.println("Medication does not exist");
        }
    }

    public void updateMedicationStockLevel(){
        System.out.println("You are updating medication stock level!");
        ArrayList<Inventory> inventoryList=viewMedicationInventory();
        System.out.print("Choice a medicine to update (0. Back): ");
        int choice=Utility.inputSafeInt(sc);
        do {
            if (choice==0) break;
            if (choice>=1 && choice<=inventoryList.size()) {
                System.out.println("Enter new stock level: ");
                int newStockLevel=Utility.inputSafeInt(sc);
                inventoryList.get(choice-1).setCurrentStock(newStockLevel);
                System.out.println("Stock level updated successfully");
                break;
            } else {
                System.out.println("invalid choice");
            }
        } while (choice!=0);
    }

    public void updateLowStockLevelAlertLine(){
        System.out.println("You are updating medication low stock-level alert line!");
        ArrayList<Inventory> inventoryList=viewMedicationInventory();
        System.out.print("Choice a medicine to update (0. Back): ");
        int choice=Utility.inputSafeInt(sc);
        do {
            if (choice==0) break;
            if (choice>=1 && choice<=inventoryList.size()) {
                System.out.println("Enter new low stock-level alert line: ");
                int newAlertLine=Utility.inputSafeInt(sc);
                inventoryList.get(choice-1).setAlertThreshold(newAlertLine);
                System.out.println("Stock level updated successfully");
                break;
            } else {
                System.out.println("invalid choice");
            }
        } while (choice!=0);
    }











////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void printReplenish(Replenish replenish) {
        String inventoryName=((Inventory)inventoryContainer.getRecord(replenish.getInventoryRecordId())).getMedicineName();
        int quantity=replenish.getQuantity();
        String pharmacistName=((Staff)staffContainer.getRecord(replenish.getPharmacistRecordId())).getName();
        String status=replenish.getStatus().toString();
        System.out.println("Medication: "+inventoryName+", Quantity: "+quantity+", Pharmacist: "+pharmacistName+", Status: "+status);
    }

    /*
    public void approvePharmacistsReplenishmentRequests(){
        System.out.print("\nYou are approving pharmacists' replenishment requests!\nEnter medicine name of medication to approve: ");
        String medicineName=Utility.inputNonEmptyString(sc);
        if (inventoryContainer.containsMedicine(medicineName)) {
            int recordID = inventoryContainer.getRecordIdByMedicineName(medicineName);
            int medRequest = ((Inventory)inventoryContainer.getRecord(recordID)).getPharmacistRefillRequest() + ((Inventory)inventoryContainer.getRecord(recordID)).getCurrentStock();
            ((Inventory)inventoryContainer.getRecord(recordID)).setCurrentStock(medRequest);
            ((Inventory)inventoryContainer.getRecord(recordID)).setRequest(0);
            System.out.println("Request approved successfully");
        } else {
            System.out.println("Medication does not exist");
        }
    }
    */

    /* 
    public void simulateRequest(){
        System.out.print("\nYou are simulating a request!\nEnter medicine name of medication to request: ");
        String medicineName=Utility.inputNonEmptyString(sc);
        if (inventoryContainer.containsMedicine(medicineName)) {
            System.out.print("Enter quantity to request: ");
            int quantity = Utility.inputSafeInt(sc);
            inventoryContainer.requestReplenishment(medicineName, quantity);
            System.out.println("Request sent successfully");
        } else {
            System.out.println("Medication does not exist");
        }
    }
    */

    public void managePharmacistsReplenishmentRequests(){
        int choice;
        System.out.println("\nPharmacists' Replenishment Requests");
        ArrayList<Replenish> requests = replenishContainer.getAllReplenish();
        for (int i=0; i<requests.size(); i++) {
            System.out.print((i+1)+": ");
            printReplenish(requests.get(i));
        }
        do { 
            System.out.println("Choose a replenishment to operate (0. Back)");
            choice=Utility.inputSafeInt(sc);
            if (choice>=1 && choice<=requests.size()) {
                askApproveReplenish(requests.get(choice-1));
                break;
            } else if (choice==0) {
                break;
            } else {
                System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }

    private void askApproveReplenish(Replenish replenish) {
        System.out.println(replenish);
        System.out.println("Do you want to approve this request and update the inventory stock?(y/n)");
        String choice=Utility.inputNonEmptyString(sc);
        if (choice.equalsIgnoreCase("y")) {
            replenish.setStatus(Replenish.Status.APPROVED);
            Inventory inventory = (Inventory)inventoryContainer.getRecord(replenish.getInventoryRecordId());
            inventory.setCurrentStock(inventory.getCurrentStock() + replenish.getQuantity());
            System.out.println("Request approved and inventory stock updated successfully");
        } else {
            System.out.println("Request not approved");
        }
    }

    public void manageMedicationInventory(){
        int choice;

        do { 
            System.out.println("\nManage Medication Inventory");
            System.out.println("1. View Medication Inventory");
            System.out.println("2. Add Medication");
            System.out.println("3. Remove Medication");
            System.out.println("4. Update Medication Stock Level");
            System.out.println("5. Update Low Stock Level Alert Line");
            System.out.println("6. Approve Pharmacists' Replenishment Requests ");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            choice=Utility.inputSafeInt(sc);
            switch (choice) {
                case 0:
                    System.out.println("Going back..."); 
                    break;

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
                    managePharmacistsReplenishmentRequests();
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
            System.out.println("\nAdministrator Menu");            
            System.out.println("1. View and manage hospital staff");
            System.out.println("2. View appointments details");
            System.out.println("3. View and Manage Medication Inventory ");
            System.out.println("4. Change Password");
            System.out.println("0. Log out");
            System.out.print("Enter your choice: ");
            choice=Utility.inputSafeInt(sc);
            switch (choice) {
                case 0:
                    System.out.println("Logging out..."); break;
                case 1:
                    manageStaff();
                    break;
                case 2:
                    viewAppointments();
                    break;
                case 3:
                    manageMedicationInventory();
                    break;
                case 4:
                    if(Utility.changePasswordProcedure((User)staffContainer.getRecord(adminRecordId))) return;
                    break;
                // case 4:
                //     managePharmacistsReplenishmentRequests();
                //     break;      
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