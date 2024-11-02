package org.sc2002.hospital.menu;

import java.util.HashMap;
import java.util.Scanner;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.container.data.AppointmentContainer;
import org.sc2002.hospital.container.data.AppointmentOutcomeContainer;
import org.sc2002.hospital.container.data.InventoryContainer;
import org.sc2002.hospital.container.data.MedicalContainer;
import org.sc2002.hospital.container.user.PatientContainer;
import org.sc2002.hospital.container.user.StaffContainer;
import org.sc2002.hospital.record.user.Patient;
import org.sc2002.hospital.record.user.Staff;

public class StartMenu extends Menu {
    HashMap<String,RecordContainer> containers;
    public StartMenu(HashMap<String,RecordContainer> containers) {
        this.containers=containers;
    }
    @Override
    public void run() {
        Scanner sc=new Scanner(System.in);
        int choice;
        do {
            System.out.println("Hospital Start Menu");
            System.out.println("0. Quit");
            System.out.println("1. Log in");
            System.out.print("Enter your choice: ");
            choice=sc.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Quitting..."); break;
                case 1:
                    System.out.print("Enter hospital ID: ");
                    String hospitalId=sc.next();
                    System.out.print("Enter password: ");
                    String password=sc.next();
                    Menu userMenu=createUserMenu(hospitalId,password);
                    if (userMenu!=null) userMenu.run();
                    else System.out.println("Wrong hospital ID or wrong password");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }

    public Menu createUserMenu(String hospitalId,String password) {
        PatientContainer patientContainer = (PatientContainer)(containers.get("Patient"));
        StaffContainer staffContainer = (StaffContainer)(containers.get("Staff"));
        AppointmentContainer appointmentContainer = (AppointmentContainer)(containers.get("Appointment"));
        AppointmentOutcomeContainer appointmentOutcomeContainer = (AppointmentOutcomeContainer)(containers.get("AppointmentOutcome"));
        InventoryContainer inventoryContainer = (InventoryContainer)(containers.get("Inventory"));
        MedicalContainer medicalContainer = (MedicalContainer)(containers.get("Medical"));

        if (patientContainer.containsUser(hospitalId)) {
            int recordId=patientContainer.getRecordIdByHospitalId(hospitalId);
            String correctPassword=((Patient)(patientContainer.getRecord(recordId))).getPassword();
            if (password.equals(correctPassword)) {
                return new PatientMenu(
                    recordId,
                    patientContainer,
                    medicalContainer
                );
            } else return null;
        } else if (staffContainer.containsUser(hospitalId)) {
            int recordId=staffContainer.getRecordIdByHospitalId(hospitalId);
            String correctPassword=((Staff)(staffContainer.getRecord(recordId))).getPassword();
            if (password.equals(correctPassword)) {
                String userType=((Staff)(staffContainer.getRecord(recordId))).getUserType();
                if (userType.equals("Doctor")) {
                    //Change after defining DoctorMenu
                    return new DoctorMenu();
                } else if (userType.equals("Administrator")) {
                    //Change after defining AdministratorMenu
                    return new AdministratorMenu();
                } else if (userType.equals("Pharmacist")) {
                    //Change after defining PharmacistMenu
                    return new PharmacistMenu();
                } else return null;
            } else return null;
        }


        return null;
    }

    
}
