package org.sc2002.hospital.menu;
import java.util.ArrayList;
import java.util.Scanner;

import org.sc2002.hospital.container.data.MedicalContainer;
import org.sc2002.hospital.container.user.PatientContainer;
import org.sc2002.hospital.record.data.Medical;
import org.sc2002.hospital.record.user.Patient;
public class PatientMenu extends Menu {
    private int patientRecordId;
    private Patient patient;
    private MedicalContainer medicalRecordCache;

    public PatientMenu(
        int patientRecordId,
        PatientContainer patientContainer,
        MedicalContainer medicalContainer) 
    {
        this.patientRecordId = patientRecordId;
        patient=(Patient)patientContainer.getRecord(patientRecordId);
        medicalRecordCache=new MedicalContainer();
        refreshMedicalRecordCache(medicalContainer);
    }

    public void refreshMedicalRecordCache(MedicalContainer medicalContainer) {
        medicalRecordCache.clear();
        ArrayList<Integer> recordIds=medicalContainer.searchPatientMedicalRecord(patientRecordId);
        for (int recordId : recordIds) {
            medicalRecordCache.putRecord(medicalContainer.getRecord(recordId));
        }
    }

    @Override
    public void run() {
        Scanner sc=new Scanner(System.in);
        int choice;
        do {
            System.out.println("Patient Menu");
            System.out.println("0. Log out");
            System.out.println("1. View Medical Record");
            System.out.println("2. View contact information");
            System.out.println("3. View past diagnoses and treatments");
            System.out.println("4. Change Email");
            System.out.println("5. Change Phone Number");
            System.out.print("Enter your choice: ");
            choice=sc.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Logging out..."); break;
                case 1:
                    viewMedicalRecord(); break;
                case 2:
                    viewContactInformation(); break;
                case 3:
                    viewPastDiagnosesAndTreatments(); break;
                case 4:
                    changeEmail(); break;
                case 5:
                    changePhoneNumber(); break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }

    private void viewMedicalRecord() {
        String patientId=patient.getHospitalId();
        String name=patient.getName();
        String dateOfBirth=patient.getDateOfBirth();
        String gender=patient.getGender();
        System.out.println("Patient Id: "+patientId);
        System.out.println("Name: "+name);
        System.out.println("Date of Birth: "+dateOfBirth);
        System.out.println("Gender: "+gender);
    }

    private void viewContactInformation() {
        String email=patient.getEmail();
        String phone=patient.getPhoneNumber();
        System.out.println("Email: "+email);
        System.out.println("Phone: "+phone);
    }

    private void viewPastDiagnosesAndTreatments() {
        System.out.println("Diagnoses and Treatments");
        for (int recordId : medicalRecordCache.getRecords().keySet()) {
            Medical record=(Medical)medicalRecordCache.getRecord(recordId);
            ArrayList<String> treatmentPlans=record.getTreatmentPlans();
            ArrayList<String> diagnoses=record.getDiagnoses();
            for (String treatmentPlan : treatmentPlans) {
                System.out.println("Treatment Plan: "+treatmentPlan);
            }
            for (String diagnosis : diagnoses) {
                System.out.println("Diagnosis: "+diagnosis);
            }
        }
    }

    private void changeEmail() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter new email: ");
        String newEmail=sc.nextLine();
        patient.setEmail(newEmail);
        System.out.println("Email changed to "+newEmail);
    }

    private void changePhoneNumber() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter new phone number: ");
        String newPhoneNumber=sc.nextLine();
        patient.setPhoneNumber(newPhoneNumber);
        System.out.println("Phone number changed to "+newPhoneNumber);
    }
}

