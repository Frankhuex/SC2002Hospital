package org.sc2002.hospital.menu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import org.sc2002.hospital.container.ContainerLoader;
import org.sc2002.hospital.container.data.AppointmentContainer;
import org.sc2002.hospital.container.data.AppointmentOutcomeContainer;
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

public class PatientMenu implements Menu {
    private int patientRecordId;
    private Patient patient;
    private MedicalContainer ownMedicalRecord;
    private AppointmentContainer appointmentContainer;
    private AppointmentOutcomeContainer appointmentOutcomeContainer;
    private StaffContainer staffContainer;
    private PrescriptionContainer prescriptionContainer;
    private final Scanner sc;

    public PatientMenu(
        int patientRecordId,
        ContainerLoader containerLoader
    ) {
        this.patientRecordId = patientRecordId;
        appointmentContainer=containerLoader.getAppointmentContainer();
        appointmentOutcomeContainer=containerLoader.getAppointmentOutcomeContainer();
        staffContainer=containerLoader.getStaffContainer();
        prescriptionContainer=containerLoader.getPrescriptionContainer();
        MedicalContainer medicalContainer=containerLoader.getMedicalContainer();
        PatientContainer patientContainer=containerLoader.getPatientContainer();
        patient = (Patient)patientContainer.getRecord(patientRecordId);
        ownMedicalRecord = new MedicalContainer();
        ArrayList<Integer> recordIds = medicalContainer.searchPatientMedicalRecord(patientRecordId);
        for (int recordId : recordIds) {
            ownMedicalRecord.putRecord(medicalContainer.getRecord(recordId));
        }
        sc=new Scanner(System.in);
    }


    @Override
    public void run() {
        int choice;
        do {
            System.out.println("\nPatient Menu");
            System.out.println("1. View Personal Medical Record");
            System.out.println("2. Change Personal Information");
            System.out.println("3. Manage Appointments");
            System.out.println("4. View Past Appointment Outcomes");
            System.out.println("5. Change Password");
            System.out.println("0. Log out");
            System.out.print("Enter your choice: ");
            choice = Utility.inputSafeInt(sc);
            switch (choice) {
                case 1:
                    viewMedicalRecord(); 
                    break;
                case 2:
                    changePersonalInformation(); 
                    break;
                case 3:
                    manageAppointments(); 
                    break;
                case 4:
                    viewPastAppointmentOutcomes(); 
                    break;
                case 5:
                    if(Utility.changePasswordProcedure(patient)) return;
                    break;
                case 0:
                    System.out.println("Logging out..."); 
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }





    private void viewMedicalRecord() {
        int choice;
        do {
            System.out.println("\n=== Medical Record ===");
            System.out.println("1. View Personal Information");
            System.out.println("2. View Contact Information");
            System.out.println("3. View Past Diagnoses and Treatments");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            choice = Utility.inputSafeInt(sc);  
            switch (choice) {
                case 1:
                    viewPersonalInformation();
                    break;
                case 2:
                    viewContactInformation();
                    break;
                case 3:
                    viewPastDiagnosesAndTreatments();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice !=0);
    }

    private void viewPersonalInformation() {
        System.out.println("\n=== Medical Record ===");
        System.out.println("Patient Id: " + patient.getHospitalId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Blood Type: " + patient.getBloodType());
    }

    private void viewContactInformation() {
        System.out.println("\n=== Contact Information ===");
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Phone: " + patient.getPhoneNumber());
    }

    private void viewPastDiagnosesAndTreatments() {
        System.out.println("\n=== Past Diagnoses and Treatments ===");
        for (int recordId : ownMedicalRecord.getRecords().keySet()) {
            Medical medicalRecord = (Medical)ownMedicalRecord.getRecord(recordId);
            System.out.println("\nRecord ID: " + recordId);
            System.out.println("Medical Record ID: " + recordId);
                
                System.out.print("Treatment Plans: ");
                for (String treatment : medicalRecord.getTreatmentPlans()) {
                    System.out.print(treatment+", "); 
                }
                System.out.println(); 

                System.out.print("Prescriptions: ");
                for (String prescription : medicalRecord.getPrescriptions()) {
                    System.out.print(prescription+", "); 
                }
                System.out.println(); 

                System.out.print("Diagnoses: ");
                for (String diagnose : medicalRecord.getDiagnoses()) {
                    System.out.print(diagnose+", "); 
                }
                System.out.println("\n"); 
        }
    }







    private void changePersonalInformation() {   
        int choice;
        do {
            System.out.println("\n=== Change Personal Information ===");
            System.out.println("1. Change Email");
            System.out.println("2. Change Phone Number");
            System.out.println("0. Back");
            choice=Utility.inputSafeInt(sc);
            switch(choice) {
                case 1:
                    changeEmail();
                    break;
                case 2:
                    changePhoneNumber();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }

    private void changeEmail() {
        System.out.print("Enter new email: ");
        String newEmail = Utility.inputNonEmptyString(sc);
        patient.setEmail(newEmail);
        System.out.println("Email updated successfully to: " + newEmail);
    }

    private void changePhoneNumber() {
        System.out.print("Enter new phone number: ");
        String newPhoneNumber = Utility.inputNonEmptyString(sc);
        patient.setPhoneNumber(newPhoneNumber);
        System.out.println("Phone number updated successfully to: " + newPhoneNumber);
    }



    private void manageAppointments() {
        int choice;
        do {
            System.out.println("\nAppointment Management");
            System.out.println("1. View Available Appointment Slots and Schedule New Appointment");
            System.out.println("2. View Current Appointments");
            System.out.println("3. Reschedule Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            choice = Utility.inputSafeInt(sc);
            switch(choice) {
                case 1:
                    viewAndScheduleAvailableSlots();
                    break;
                case 2: 
                    viewCurrentAppointments(); 
                    break;
                case 3: 
                    rescheduleAppointment(); 
                    break;
                case 4: 
                    cancelAppointment(); 
                    break;
                case 0: 
                    System.out.println("Returning to main menu..."); 
                    break;
                default: 
                    System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }

    private void viewAndScheduleAvailableSlots() {
        System.out.println("\n=== Available Appointment Slots ===");
        
        // Display all doctors first
        System.out.println("Available Doctors:");
        HashSet<String> doctorIds=new HashSet<>();

        for (int recordId : staffContainer.getRecords().keySet()) {
            Staff staff = (Staff)staffContainer.getRecord(recordId);
            if (staff.getUserType().equals("Doctor")) {
                doctorIds.add(staff.getHospitalId());
                System.out.println("Doctor ID: " + staff.getHospitalId() + " - Name: " + staff.getName());
            }
        }

        System.out.print("\nEnter doctor's hospital ID to view available slots: ");
        
        String doctorId = Utility.inputNonEmptyString(sc);
        if (!doctorIds.contains(doctorId)) {
            System.out.println("Invalid hospital ID.");
            return;
        }
        int doctorRecordId = staffContainer.getRecordIdByHospitalId(doctorId);
        ArrayList<String> availableSlots=viewAvailableSlots(doctorRecordId);
        if (availableSlots.isEmpty()) {return;}
        scheduleAppointment(doctorRecordId,doctorId,availableSlots);
    }

    private ArrayList<String> viewAvailableSlots(int doctorRecordId) {
        // Show available slots (10 AM to 6 PM)
        System.out.println("\nAvailable slots for next 7 days:");

        HashMap<String,Appointment> doctorAvailableSlots=appointmentContainer.getFutureAppointmentTable(doctorRecordId);
        ArrayList<String> availableSlots=new ArrayList<>();
        for (String date:doctorAvailableSlots.keySet()) {
            if (doctorAvailableSlots.get(date)==null) {
                availableSlots.add(date);
            }
        }
        Utility.sortDateList(availableSlots);
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots found for this doctor.");
        } else {
            for (int i=0; i<availableSlots.size(); i++) {
                System.out.println((i+1)+": "+"Date: "+availableSlots.get(i)+" - Available");
            }
        }
        return availableSlots;
    }

    private boolean scheduleAppointment(int doctorRecordId,String doctorId,ArrayList<String> availableSlots) {
        System.out.println("Choose a slot to submit an appointment request (0. Back): ");
        int choice;
        do {
            choice=Utility.inputSafeInt(sc);
            if (choice==0) {
                break;
            } else if (choice>0 && choice<=availableSlots.size()) {
                String selectedSlot=availableSlots.get(choice-1);
                System.out.println("Selected slot: " + selectedSlot);
                System.out.println("Doctor: "+doctorId+" - "+((Staff)staffContainer.getRecord(doctorRecordId)).getName());
                System.out.println("Confirm to submit appointment request? (y/n): ");
                String confirm=Utility.inputNonEmptyString(sc);
                if (confirm.equalsIgnoreCase("y")) {
                    boolean success=appointmentContainer.createDoctorAppointment(selectedSlot, patientRecordId, doctorRecordId);
                    if (success) {
                        System.out.println("Appointment request submitted successfully.");
                        return true;
                    }
                    else System.out.println("Error occurs.");
                } else {
                    System.out.println("Appointment request not submitted.");
                }
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        } while (choice!=0);
        return false;
    }



    private void viewCurrentAppointments() {
        ArrayList<Appointment> appointments = 
            appointmentContainer.searchAppointmentsByPatientrecordId(patientRecordId);
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.println("\n=== Your Appointments ===");
        for (Appointment apt : appointments) {
            Staff doctor = (Staff)staffContainer.getRecord(apt.getDoctorRecordId());
            System.out.println("ID: " + apt.getRecordId() + 
                             "\nDate: " + apt.getDate() +
                             "\nDoctor: " + doctor.getName() +
                             "\nStatus: " + apt.getStatus() + "\n");
        }
    }
    
    /* 
    private void scheduleNewAppointment() {
        System.out.println("\n=== Schedule New Appointment ===");
        
        // First show available doctors
        System.out.println("Available Doctors:");
        for (int recordId : staffContainer.getRecords().keySet()) {
            Staff staff = (Staff)staffContainer.getRecord(recordId);
            if (staff.getUserType().equals("Doctor")) {
                System.out.println("Doctor ID: " + staff.getHospitalId() + " - Name: " + staff.getName());
            }
        }

        System.out.print("Enter doctor's hospital ID: ");
        String doctorId = Utility.inputNonEmptyString(sc);
        int doctorRecordId = staffContainer.getRecordIdByHospitalId(doctorId);
        
        System.out.print("Enter appointment date (DD-MM-YYYY-Hour[10-18]): ");
        String date = Utility.inputSafeDate(sc);

        boolean success = appointmentContainer.createDoctorAppointment(date, patientRecordId, doctorRecordId);
        if (success) {
            System.out.println("Appointment scheduled successfully.");
        }
    }
    */
    
    private void rescheduleAppointment() {
        ArrayList<Appointment> appointments = 
            appointmentContainer.searchAppointmentsByPatientrecordId(patientRecordId);
        
        if (appointments.isEmpty()) {
            System.out.println("No appointments found to reschedule.");
            return;
        }
        
        // Show current appointments
        System.out.println("\n=== Current Appointments ===");
        for (Appointment apt : appointments) {
            if (apt.getStatus()!=Appointment.Status.COMPLETED) {
                Staff doctor = (Staff)staffContainer.getRecord(apt.getDoctorRecordId());
                System.out.println("ID: " + apt.getRecordId() + 
                                 ", Date: " + apt.getDate() +
                                 ", Doctor: " + doctor.getName() +
                                 ", Status: " + apt.getStatus());
            }
        }
        
        System.out.print("\nEnter appointment ID to reschedule: ");
        int appointmentId = Utility.inputSafeInt(sc);
        Appointment appointment = (Appointment)appointmentContainer.getRecord(appointmentId);
        if (appointment == null || !appointments.contains(appointment)) {
            System.out.println("Invalid appointment ID.");
            return;
        }

        int doctorRecordId = appointment.getDoctorRecordId();
        String doctorId = ((Staff)staffContainer.getRecord(doctorRecordId)).getHospitalId();
        ArrayList<String> availableSlots=viewAvailableSlots(doctorRecordId);
        if (availableSlots.isEmpty()) {return;}
        if (scheduleAppointment(doctorRecordId,doctorId,availableSlots)) {
            appointmentContainer.removeRecord(appointmentId);
        }
        
        
        // System.out.print("Enter new date (DD-MM-YYYY-Hour[10-18]): ");
        // String newDate = Utility.inputSafeDate(sc);

        // if (appointmentContainer.searchAppointmentByDateAndDoctorId(appointment.getDoctorRecordId(), newDate)!=null) {
        //     System.out.println("Selected time slot is not available.");
        //     return;
        // }

        // appointment.setDate(newDate);
        // System.out.println("Appointment rescheduled successfully.");
    }

    private void cancelAppointment() {
        ArrayList<Appointment> appointments = 
            appointmentContainer.searchAppointmentsByPatientrecordId(patientRecordId);
        
        if (appointments.isEmpty()) {
            System.out.println("No appointments found to cancel.");
            return;
        }
        
        System.out.println("\n=== Current Appointments ===");
        for (Appointment apt : appointments) {
            if (apt.getStatus()!=Appointment.Status.COMPLETED) {
                Staff doctor = (Staff)staffContainer.getRecord(apt.getDoctorRecordId());
                System.out.println("ID: " + apt.getRecordId() + 
                                 ", Date: " + apt.getDate() +
                                 ", Doctor: " + doctor.getName() +
                                 ", Status: " + apt.getStatus());
            }
        }
        
        System.out.print("\nEnter appointment ID to cancel: ");
        int appointmentId = Utility.inputSafeInt(sc);
        
        Appointment appointment = (Appointment)appointmentContainer.getRecord(appointmentId);
        if (appointment == null || !appointments.contains(appointment)) {
            System.out.println("Invalid appointment ID.");
            return;
        }
        
        if (appointment.getStatus()==Appointment.Status.COMPLETED) {
            System.out.println("Cannot cancel completed appointments.");
            return;
        }
        
        appointmentContainer.removeRecord(appointmentId);
        System.out.println("Appointment cancelled successfully.");
    }



    private void viewPastAppointmentOutcomes() {
        ArrayList<Appointment> appointments = 
            appointmentContainer.searchAppointmentsByPatientrecordId(patientRecordId);
        boolean found = false;
        
        System.out.println("\n=== Past Appointment Outcomes ===");
        for (Appointment apt : appointments) {
            if (apt.getStatus()==Appointment.Status.COMPLETED) {
                found = true;
                Staff doctor = (Staff)staffContainer.getRecord(apt.getDoctorRecordId());
                AppointmentOutcome outcome = 
                    appointmentOutcomeContainer.getByAppointmentRecordId(apt.getRecordId());
                
                if (outcome != null) {
                    System.out.println("\nAppointment Date: " + apt.getDate());
                    System.out.println("Doctor: " + doctor.getName());
                    System.out.println("Service Type: " + outcome.getServiceType());
                    
                    System.out.println("Prescriptions:");
                    for (int prescriptionRecordId : outcome.getPrescriptionRecordIds()) {
                        Prescription prescription = (Prescription)prescriptionContainer.getRecord(prescriptionRecordId);
                        System.out.println(prescription);
                    }
                    
                    System.out.println("Consultation Notes:");
                    System.out.println(outcome.getConsultationNote());
                    System.out.println("----------------------------------------");
                }
            }
        }
        
        if (!found) {
            System.out.println("No completed appointments found.");
        }
    }

    
}