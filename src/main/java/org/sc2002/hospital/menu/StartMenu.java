package org.sc2002.hospital.menu;

import java.util.Scanner;

import org.sc2002.hospital.container.ContainerLoader;
import org.sc2002.hospital.container.user.PatientContainer;
import org.sc2002.hospital.container.user.StaffContainer;
import org.sc2002.hospital.record.user.Patient;
import org.sc2002.hospital.record.user.Staff;
import org.sc2002.hospital.utility.Utility;

public class StartMenu implements Menu {
    // HashMap<String, RecordContainer> containers;
    private ContainerLoader containerLoader;
    private PatientContainer patientContainer;
    private StaffContainer staffContainer;
    private final Scanner sc;
    public StartMenu(ContainerLoader containerLoader) {
        this.containerLoader = containerLoader;
        patientContainer = containerLoader.getPatientContainer();
        staffContainer = containerLoader.getStaffContainer();
        sc=new Scanner(System.in);
    }

    @Override
    public void run() {
        int choice;
        do {
            System.out.println("Hospital Start Menu");
            System.out.println("1. Log in");
            System.out.println("2. Register as a new patient");
            System.out.println("0. Quit");
            System.out.print("Enter your choice: ");
            choice = Utility.inputSafeInt(sc);
            switch (choice) {
                case 0:
                    System.out.println("Quitting...");
                    sc.close(); // Close scanner to fix the resource leak
                    break;
                case 1:
                    System.out.print("Enter hospital ID: ");
                    String hospitalId = Utility.inputNonEmptyString(sc);
                    System.out.print("Enter password: ");
                    String password = Utility.inputNonEmptyString(sc);
                    
                    // if (!validateUsername(hospitalId)) {
                    //     break; // Break out if password is invalid
                    // }
                    // if (!validatePassword(password)) {
                    //     break; // Break out if password is invalid
                    // }
                    

                    Menu userMenu = createUserMenu(hospitalId, password);
                    if (userMenu != null)
                        userMenu.run();
                    else
                        System.out.println("Wrong hospital ID or wrong password");
                    break;
                case 2:
                    registerPatient(); break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }

    public void registerPatient() {
        System.out.println("Register patient");
        
        // Hospital ID input loop
        String hospitalID = "";
        while (true) {
            System.out.print("Enter hospital ID (or 0 to quit): ");
            hospitalID = Utility.inputNonEmptyString(sc);
            if (patientContainer.containsUser(hospitalID) || staffContainer.containsUser(hospitalID)) {
                System.out.println("Error: Hospital ID already exists.");
                return;
            }
            if (hospitalID.equals("0")) {
                System.out.println("Returning to main menu...");
                return;  // Quit and return to main menu
            }
            if (!hospitalID.isEmpty()) {
                break;
            } else {
                System.out.println("Error: Hospital ID cannot be empty.");
            }
        }
        
        Patient patient = new Patient(hospitalID);
    
        // Name input loop
        String name = "";
        while (true) {
            System.out.print("Enter Name (or 0 to quit): ");
            name = Utility.inputNonEmptyString(sc);
            if (name.equals("0")) {
                System.out.println("Returning to main menu...");
                return;  // Quit and return to main menu
            }
            if (!name.isEmpty()) {
                patient.setName(name);
                break;
            } else {
                System.out.println("Error: Name cannot be empty.");
            }
        }
    
        // Gender input loop
        String gender = "";
        while (true) {
            System.out.print("Enter Gender (Male/Female) (or 0 to quit): ");
            gender = Utility.inputNonEmptyString(sc);
            if (gender.equals("0")) {
                System.out.println("Returning to main menu...");
                return;  // Quit and return to main menu
            }
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                patient.setGender(gender);
                break;
            } else {
                System.out.println("Error: Gender must be 'Male' or 'Female'.");
            }
        }
    
        // Date of Birth input loop
        String dob = "";
        while (true) {
            System.out.print("Enter Date of Birth (dd-mm-yyyy) (or 0 to quit): ");
            dob = Utility.inputNonEmptyString(sc);
            if (dob.equals("0")) {
                System.out.println("Returning to main menu...");
                return;  // Quit and return to main menu
            }
            if (dob.matches("\\d{2}-\\d{2}-\\d{4}")) {
                patient.setDateOfBirth(dob);
                break;
            } else {
                System.out.println("Error: Invalid date format. Please use dd-mm-yyyy.");
            }
        }
    
        // Blood Type input loop
        String bloodType = "";
        while (true) {
            System.out.print("Enter Blood type (A/B/AB/O/A+/B+/AB+/O+/A-/B-/AB-/O-) (or 0 to quit): ");
            bloodType = Utility.inputNonEmptyString(sc);
            if (bloodType.equals("0")) {
                System.out.println("Returning to main menu...");
                return;  // Quit and return to main menu
            }
            if (bloodType.matches("A|A+|A-|B|B+|B-|AB|AB+|AB-|O|O+|O-")) {
                patient.setBloodType(bloodType);
                break;
            } else {
                System.out.println("Error: Invalid blood type. Enter one of A/B/AB/O/A+/B+/AB+/O+/A-/B-/AB-/O-.");
            }
        }
    
        // If all inputs are valid, register the patient
        patientContainer.putRecord(patient);
        System.out.println("Default password is 'password'. You can update your information after login.");
        System.out.println("Registration successful.");
    }
    

    public Menu createUserMenu(String hospitalId, String password) {
        if (patientContainer.containsUser(hospitalId)) {
            int recordId = patientContainer.getRecordIdByHospitalId(hospitalId);
            String correctPassword = ((Patient) (patientContainer.getRecord(recordId))).getPassword();
            if (password.equals(correctPassword)) {
                return new PatientMenu(recordId,containerLoader);
            } else
                return null;
        } else if (staffContainer.containsUser(hospitalId)) {
            int recordId = staffContainer.getRecordIdByHospitalId(hospitalId);
            String correctPassword = ((Staff) (staffContainer.getRecord(recordId))).getPassword();
            if (password.equals(correctPassword)) {
                String userType = ((Staff) (staffContainer.getRecord(recordId))).getUserType();
                if (userType.equals("Doctor")) {
                    return new DoctorMenu(recordId,containerLoader);
                } else if (userType.equals("Administrator")) {
                    return new AdministratorMenu(recordId,containerLoader);
                } else if (userType.equals("Pharmacist")) {
                    return new PharmacistMenu(recordId,containerLoader); // FR
                } else
                    return null;
            } else
                return null;
        }

        return null;
    }

    // private boolean validatePassword(String password) {
    //     if (password == null || password.isEmpty() || password == "\n") {
    //         System.out.println("Error: Password cannot be empty.");
    //         return false;
    //     }

    //     /*
    //     if (password.length() < 8) {
    //         System.out.println("Error: Password must be at least 8 characters long.");
    //         return false;
    //     }
    //     */

    //     // Additional validation criteria can be added here if needed
    //     return true;
    // }
    private boolean validateUsername(String Username) {
        if (Username == null || Username.isEmpty() || Username == "\n") {
            System.out.println("Error: Username cannot be empty.");
            return false;
        }
        
        // Additional validation criteria can be added here if needed
        return true;
    }

}
