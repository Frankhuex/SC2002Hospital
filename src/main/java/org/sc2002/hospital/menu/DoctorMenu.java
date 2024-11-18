package org.sc2002.hospital.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.sc2002.hospital.container.ContainerLoader;
import org.sc2002.hospital.container.data.AppointmentContainer;
import org.sc2002.hospital.container.data.AppointmentOutcomeContainer;
import org.sc2002.hospital.container.data.InventoryContainer;
import org.sc2002.hospital.container.data.MedicalContainer;
import org.sc2002.hospital.container.data.PrescriptionContainer;
import org.sc2002.hospital.container.user.PatientContainer;
import org.sc2002.hospital.container.user.StaffContainer;
import org.sc2002.hospital.record.data.Appointment;
import org.sc2002.hospital.record.data.AppointmentOutcome;
import org.sc2002.hospital.record.data.Medical;
import org.sc2002.hospital.record.data.Prescription;
import org.sc2002.hospital.record.user.Patient;
import org.sc2002.hospital.record.user.Staff;
import org.sc2002.hospital.utility.Utility;

public class DoctorMenu implements Menu {

    //changes start here

    private PatientContainer patientContainer;
    private StaffContainer staffContainer;
    private AppointmentContainer appointmentContainer;
    private AppointmentOutcomeContainer appointmentOutcomeContainer;
    private InventoryContainer inventoryContainer;  
    private MedicalContainer medicalContainer;   
    private PrescriptionContainer prescriptionContainer;   

    private Staff doctor;
    private int doctorRecordId;
    private String doctorHospitalId;
    private HashMap<String,Appointment> futureAppointmentTable;
    private HashMap<String,Appointment> pastAppointmentTable;
    private ArrayList<String> pastDates;
    private ArrayList<String> futureDates;

    private ArrayList<String> medicineList;

    private Scanner sc;
    


    public DoctorMenu(int recordId,ContainerLoader containerLoader) 
    {
        this.doctorRecordId = recordId;  
        this.patientContainer = containerLoader.getPatientContainer();
        this.staffContainer = containerLoader.getStaffContainer();
        this.appointmentContainer = containerLoader.getAppointmentContainer();
        this.appointmentOutcomeContainer = containerLoader.getAppointmentOutcomeContainer();
        this.medicalContainer = containerLoader.getMedicalContainer();
        this.inventoryContainer = containerLoader.getInventoryContainer();
        this.prescriptionContainer = containerLoader.getPrescriptionContainer();

        doctor=(Staff)this.staffContainer.getRecord(doctorRecordId); //remember its lower case staff not Staff
        doctorHospitalId=doctor.getHospitalId();
        futureAppointmentTable=appointmentContainer.getFutureAppointmentTable(doctorRecordId);
        pastAppointmentTable=appointmentContainer.getPastAppointmentTable(doctorRecordId);
        pastDates=new ArrayList<>(pastAppointmentTable.keySet());
        futureDates=new ArrayList<>(futureAppointmentTable.keySet());
        Utility.sortDateList(pastDates);
        Utility.sortDateList(futureDates);

        medicineList=inventoryContainer.getMedicineNames();


        System.out.println("Welcome "+doctor.getName());
        sc=new Scanner(System.in);
    }
    //changes end here
    
    @Override
    public void run() {
        int choice;
        
        do {
            System.out.println("Doctor Menu");
            System.out.println("1. Manage patient records");
            System.out.println("2. Manage past appointments");
            System.out.println("3. Manage future appointments");
            System.out.println("4. View appointment outcome records");
            System.out.println("5. Change password");
            System.out.println("0. Log out");
            System.out.print("Enter your choice: ");
            choice=Utility.inputSafeInt(sc);


            switch (choice) {              
                case 1:
                    managePatientRecords();break;
                case 2:
                    managePastAppointments();break;
                case 3:
                    manageFutureAppointments();break;
                case 4:
                    viewAppointmentOutcomeRecord();break;
                case 5:
                    if(Utility.changePasswordProcedure(doctor)) return;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }

////////////////////////////////////////////////////
    // manage patient records

    private void managePatientRecords() {
        showPatientIds();
        int choice;
        do {
            System.out.println("Patient Records Management Menu");
            System.out.println("1. View patient records");
            System.out.println("2. Update patient records");
            System.out.println("0. Back");

            System.out.print("Enter your choice: ");
            choice=Utility.inputSafeInt(sc);

            switch (choice) {
                case 1:
                    viewPatientRecords();break;
                case 2:
                    updatePatientRecords();break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }

    private void showPatientIds() {
        ArrayList<String> patientIds = new ArrayList<>(patientContainer.getAllPatientHospitalIds());
        patientIds.sort(null); // Sort the list alphabetically
        System.out.println("Patients ID:");
        for (String pid: patientIds) {
            System.out.print(pid+", ");
        }
    }

    private void viewPatientRecords() {
        String patientID2;
        System.out.println("\nInput a patient ID to view their Records: ");
        patientID2 = Utility.inputNonEmptyString(sc);
        int recordId2=patientContainer.getRecordIdByHospitalId(patientID2);  //have patient Id want patient recordID

        ArrayList<Integer> medicalRecordIds = medicalContainer.searchPatientMedicalRecord(recordId2);
        if (medicalRecordIds.isEmpty()) {
            System.out.println("No medical records found for patient ID: " + patientID2);
        } else {
            System.out.println("Displaying medical records for patient ID: " + patientID2);
            for (int recordId : medicalRecordIds) {
                Medical medicalRecord = (Medical) medicalContainer.getRecord(recordId);
                System.out.println("Medical Record ID: " + recordId);
                
                System.out.print("Treatment Plans: ");
                for (String treatment : medicalRecord.getTreatmentPlans()) {
                    System.out.print(treatment+", "); 
                }
                System.out.println();

                // Iterating through prescriptions
                System.out.print("Prescriptions: ");
                for (String prescription : medicalRecord.getPrescriptions()) {
                    System.out.print(prescription+", "); 
                }
                System.out.println(); 

                // Iterating through diagnoses
                System.out.print("Diagnoses: ");
                for (String diagnose : medicalRecord.getDiagnoses()) {
                    System.out.print(diagnose+", "); 
                }
                System.out.println("\n"); 
            }
        }
    }

    private void updatePatientRecords() {
        System.out.println("Input a patient ID to create or update their record: ");
        String patientID2 = Utility.inputNonEmptyString(sc);
        int recordId2 = patientContainer.getRecordIdByHospitalId(patientID2);

        if (recordId2==-1) {
            System.out.println("Patient ID does not exist: "+ patientID2 +"\nPlease enter a valid patient ID.");
            return;
        }  
            
        if (medicalContainer.searchPatientMedicalRecord(recordId2).isEmpty()) {
            System.out.println("No existing medical record found for patient ID: " + patientID2 + ". Creating a new record.");
        } else {
            System.out.println("Updating existing medical record for patient ID: " + patientID2);
        }

        // Collect data for the new or updated record
        Medical newRecord = collectMedicalRecordData(recordId2);
        medicalContainer.updateMedicalRecord(newRecord);
    }



    


////////////////////////////////
    // appointment view
    private void printAppointment(Appointment appointment) {
        if (appointment.getStatus()==Appointment.Status.UNAVAILABLE) {
            System.out.println("Date: "+appointment.getDate() + " - Unavailable");
            return;
        } 
        String patientId=((Patient)patientContainer.getRecord(appointment.getPatientRecordId())).getHospitalId();
        String patientName=((Patient)patientContainer.getRecord(appointment.getPatientRecordId())).getName();
        System.out.println("Date: "+appointment.getDate()
            +"\t - status: "+appointment.getStatus()
            +"\t Patient ID: " + patientId
            +"\t Patient Name: " + patientName);
    }

    private boolean viewAppointmentsByDates(ArrayList<String> dates, HashMap<String,Appointment> appointmentTable) {
        if (dates.isEmpty()) {
            System.out.println("No appointments found.");
            return false;
        }
        Utility.sortDateList(dates);
        for (int i=0; i<dates.size(); i++) {
            String date=dates.get(i);
            Appointment appointment = appointmentTable.get(date);
            System.out.print((i+1)+". ");
            if (appointment!=null) {
                printAppointment(appointment);
            } else {
                System.out.println("Date: "+date+" - Available");
            }
        }
        return true;
    }

    private boolean viewPastAppointments() {
        System.out.println("Past Appointments:");
        return viewAppointmentsByDates(pastDates, pastAppointmentTable);
    }

    private boolean viewFutureAppointments() {
        System.out.println("Future Appointments:");
        return viewAppointmentsByDates(futureDates, futureAppointmentTable);
    }




////////////////////////////////////////////////


    // manage past appointments

    private void managePastAppointments() {
        int choice;
        System.out.println("Appointment Management Menu");
        pastAppointmentTable=appointmentContainer.getPastAppointmentTable(doctorRecordId);
        pastDates=new ArrayList<>(pastAppointmentTable.keySet());
        Utility.sortDateList(pastDates);
        if (!viewPastAppointments()) return;
        System.out.println("Choose an appointment to complete (0. Back):");
        do {
            choice=Utility.inputSafeInt(sc);
            if (choice==0) {break;}
            else if (choice>=1 && choice<=pastDates.size()) {
                String date=pastDates.get(choice-1);
                Appointment appointment = pastAppointmentTable.get(date);
                printAppointment(appointment);
                completeAppointment(appointment);
                
                break;
            } else {
                System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }

    
    
    private void completeAppointment(Appointment appointment) {
        if (appointment.getStatus()==Appointment.Status.COMPLETED) {
            System.out.println("Appointment already completed.");
            return;
        }
        if (appointment.getStatus()!=Appointment.Status.CONFIRMED) {
            System.out.println("Appointment not confirmed yet.");
            return;
        }
        System.out.println("Complete this appointment? (y/n)");
        String choice = Utility.inputNonEmptyString(sc);
        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("Appointment still not completed.");
            return;
        }
        // Collect outcome data for the completed appointment
        System.out.println("Creating the outcome of the appointment... ");
        System.out.print("Enter service type: ");
        String serviceType = Utility.inputNonEmptyString(sc);
        AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointment.getRecordId(), serviceType);
        
        System.out.println("Prescriptions:");
        addPrescriptions(appointmentOutcome);

        System.out.println("Consultation Notes:");
        String Consultation_Notes = Utility.inputNonEmptyString(sc);
        appointmentOutcome.setConsultationNote(Consultation_Notes);
        System.out.println();
        appointmentOutcomeContainer.putRecord(appointmentOutcome);
        appointment.setStatus(Appointment.Status.COMPLETED);
        System.out.println("Appointment completed successfully for patient ID: ");
    }



    private void addPrescriptions(AppointmentOutcome appointmentoutcome) {
        System.out.println("Medicine list:");
        for (int i=0; i<medicineList.size(); i++) {
            System.out.println((i+1)+". "+medicineList.get(i));
        }
        System.out.println((medicineList.size()+1)+". New medicine");
        int choice;
        while (true) {
            System.out.println("Choose a medicine to add to prescription (0. Complete):");
            choice=Utility.inputSafeInt(sc);
            if (choice>=1 && choice<=medicineList.size()) {
                String medicineName=medicineList.get(choice-1);
                System.out.println("Enter the dosage for "+medicineName+":");
                int dosage=Utility.inputSafeInt(sc);
                Prescription prescription=new Prescription(medicineName,dosage);
                prescriptionContainer.putRecord(prescription);
                appointmentoutcome.addPrescriptionRecordId(prescription.getRecordId());
            } else if (choice==medicineList.size()+1) {
                System.out.println("Enter the name of the new medicine:");
                String medicineName=Utility.inputNonEmptyString(sc);
                System.out.println("Enter the dosage for "+medicineName+":");
                int dosage=Utility.inputSafeInt(sc);
                Prescription prescription=new Prescription(medicineName,dosage);
                prescriptionContainer.putRecord(prescription);
                appointmentoutcome.addPrescriptionRecordId(prescription.getRecordId());
            } else if (choice==0) {
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
    

/////////////////////////////////////////////////////
    // manage future appointments

    private void manageFutureAppointments() {
        int choice;
        System.out.println("Appointment Management Menu");
        viewFutureAppointments();
        System.out.println("Choose an appointment to operate (0. Back):");
        do {
            choice=Utility.inputSafeInt(sc);
            if (choice==0) {break;}
            else if (choice>=1 && choice<=futureDates.size()) {
                String date=futureDates.get(choice-1);
                Appointment appointment = futureAppointmentTable.get(date);
                if (appointment==null) { //null means available
                    System.out.println("Date: "+date+" Available");
                    askSetUnavailable(date);
                } else if (appointment.getStatus()==Appointment.Status.UNAVAILABLE) {
                    System.out.println("Date: "+date+" Unavailable");
                    askSetAvailable(appointment);
                } else { //PENDING, ACCEPTED, or REJECTED
                    System.out.println("Date: "+date+" Appointment status: "+appointment.getStatus());
                    askSetARPC(appointment);
                }
                break;
            } else {
                System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }

    private void askSetUnavailable(String date) {
        System.out.println("Set this appointment as unavailable? (y/n)");
        String choice = Utility.inputNonEmptyString(sc);
        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("Appointment still available.");
            return;
        }
        Appointment appointment = new Appointment(date,-1,doctorRecordId);
        appointment.setStatus(Appointment.Status.UNAVAILABLE);
        futureAppointmentTable.put(date,appointment);
        appointmentContainer.putRecord(appointment);
        System.out.println("Appointment set as unavailable.");
    }

    private void askSetAvailable(Appointment appointment) {
        System.out.println("Set this appointment as available? (y/n)");
        String choice = Utility.inputNonEmptyString(sc);
        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("Appointment still unavailable.");
            return;
        }
        futureAppointmentTable.put(appointment.getDate(),null);
        appointmentContainer.removeRecord(appointment.getRecordId());
        System.out.println("Appointment set as available.");
    }

    private void askSetARPC(Appointment appointment) { //ARC:Accepted, Rejected, Pending, Completed
        System.out.println("Choose operation:"); 
        System.out.println("1. Confirm, 2. Cancel, 3. Pend, 4. Complete, 0. Back");
        int choice = Utility.inputSafeInt(sc);
        switch (choice) {
            case 1:
                appointment.setStatus(Appointment.Status.CONFIRMED);
                System.out.println("Appointment confirmed.");
                break;
            case 2:
                appointment.setStatus(Appointment.Status.CANCELED);
                System.out.println("Appointment canceled.");
                break;
            case 3:
                appointment.setStatus(Appointment.Status.PENDING);
                System.out.println("Appointment set to pending.");
                break;
            case 4:
                completeAppointment(appointment);
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice. Please enter 1, 2, 3 or 0.");
                break;
        }

    }

    /*
    private void operateScheduledAppointments() {
        // System.out.println("Input the [patient ID] to accept or reject an appointment: ");
        // String patientID2 = Utility.inputNonEmptyString(sc);
        System.out.println("Input the [date] to accept or reject an appointment: (format: DD-MM-YYYY-Hour[10-18])");
        String date1 = Utility.inputSafeDate(sc);
        //int recordId2 = patientContainer.getRecordIdByHospitalId(patientID2);
        Appointment appointment1 = appointmentTable.get(date1);

        if (appointment1==null || appointment1.getStatus()==Appointment.Status.UNAVAILABLE) {
            System.out.println("No appointment found for date: " + date1);
        } else {
            String patientID2 = ((Patient)patientContainer.getRecord(appointment1.getPatientRecordId())).getHospitalId();
            Appointment.Status appointmentStatus = appointment1.getStatus();
            System.out.println("Input 1 to accept, 2 to reject, or 3 to complete the appointment: ");
            int choice1 = Utility.inputSafeInt(sc);
            switch (choice1) {
                case 1:
                    appointment1.setStatus(Appointment.Status.ACCEPTED);
                    System.out.println("Appointment accepted successfully for patient ID: " + patientID2 + " on date: " + date1);
                    break;
                case 2:
                    appointment1.setStatus(Appointment.Status.REJECTED);
                    System.out.println("Appointment rejected successfully for patient ID: " + patientID2 + " on date: " + date1);
                    appointmentContainer.removeRecord(appointment1.getRecordId()); // Remove the appointment
                    break;
                case 3:
                    appointment1.setStatus(Appointment.Status.COMPLETED);
                    System.out.println("Appointment completed successfully for patient ID: " + patientID2 + " on date: " + date1);
                    // Collect outcome data for the completed appointment
                    System.out.print("Enter the outcome of the appointment: ");
                    String outcome = Utility.inputNonEmptyString(sc);
                    AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointment1.getRecordId(), outcome);
                    System.out.println("Prescriptions:");
                    Prescription Prescriptions = new Prescription(Utility.inputNonEmptyString(sc));
                    appointmentOutcome.addPrescription(Prescriptions);
                    System.out.println("Consultation Notes:");
                    String Consultation_Notes = Utility.inputNonEmptyString(sc);
                    appointmentOutcome.addConsultationNote(Consultation_Notes);
                    System.out.println(); // Blank line for better readability
                    // Assuming you have a method in appointmentOutcomeContainer to create an outcome record
                    
                    appointmentOutcomeContainer.putRecord(appointmentOutcome);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;
            }
        }
        
    }
    */

    

    private void viewAppointmentOutcomeRecord() {
        String DoctorID2 = doctor.getHospitalId(); // Get the doctor's hospital ID

        ArrayList<Appointment> doctorAppointments = this.appointmentContainer.searchAppointmentsByDoctorrecordId(this.doctorRecordId);
        if (doctorAppointments.isEmpty()) {
            System.out.println("No appointmentsOutcomes found for Doctor ID: " + DoctorID2);
        } 
        else {
            for (Appointment appointment : doctorAppointments) {
                if ( appointment.getStatus()==Appointment.Status.COMPLETED) {
                    // Add a separator for readability
                    System.out.println("---------------------------------------------------");
                    AppointmentOutcome outcome = appointmentOutcomeContainer.getByAppointmentRecordId(appointment.getRecordId());
                    
                    // Print the required details
                    System.out.println("Appointment Record ID: " + outcome.getRecordId());
                    System.out.println("Service Type: " + outcome.getServiceType());
                    
                    // Print Prescriptions
                    System.out.println("Prescriptions:");
                    for (int prescriptionRecordId : outcome.getPrescriptionRecordIds()) {
                        Prescription prescription = (Prescription) prescriptionContainer.getRecord(prescriptionRecordId);
                        System.out.println(prescription);
                    }
    
                    // Print Consultation Notes
                    System.out.println("Consultation Notes:");
                    System.out.println(outcome.getConsultationNote());
                    
                
                }
            }
            // Add a separator for readability
            System.out.println("---------------------------------------------------");
        }
    }


    private Medical collectMedicalRecordData(int patientRecordId) {
        System.out.print("Enter new treatment plans (comma-separated): ");
        String[] treatments = Utility.inputNonEmptyString(sc).split(",");
        ArrayList<String> treatmentPlans = new ArrayList<>();
        for (String treatment : treatments) {
            treatmentPlans.add(treatment.trim());
        }

        System.out.print("Enter new prescriptions (comma-separated): ");
        String[] prescriptions = Utility.inputNonEmptyString(sc).split(",");
        ArrayList<String> prescriptionList = new ArrayList<>();
        for (String prescription : prescriptions) {
            prescriptionList.add(prescription.trim());
        }

        System.out.print("Enter new diagnoses (comma-separated): ");
        String[] diagnoses = Utility.inputNonEmptyString(sc).split(",");
        ArrayList<String> diagnosesList = new ArrayList<>();
        for (String diagnose : diagnoses) {
            diagnosesList.add(diagnose.trim());
        }

        // Create a new Medical record with collected data
        Medical medicalRecord = new Medical(patientRecordId);
        medicalRecord.setTreatmentPlans(treatmentPlans);
        medicalRecord.setPrescriptions(prescriptionList);
        medicalRecord.setDiagnoses(diagnosesList);

        return medicalRecord;
    }



}



