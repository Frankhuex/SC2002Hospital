package org.sc2002.hospital.menu;

import java.util.ArrayList;
import java.util.Scanner;

import org.sc2002.hospital.container.data.AppointmentOutcomeContainer;
import org.sc2002.hospital.container.data.InventoryContainer;
import org.sc2002.hospital.container.data.PrescriptionContainer;
import org.sc2002.hospital.container.data.ReplenishContainer;
import org.sc2002.hospital.container.user.StaffContainer;
import org.sc2002.hospital.container.*;
import org.sc2002.hospital.record.data.AppointmentOutcome;
import org.sc2002.hospital.record.data.Inventory;
import org.sc2002.hospital.record.data.Prescription;
import org.sc2002.hospital.record.data.Replenish;
import org.sc2002.hospital.record.user.Staff;
import org.sc2002.hospital.utility.Utility;

public class PharmacistMenu implements Menu {
    private AppointmentOutcomeContainer appointmentOutcomeContainer;
    private InventoryContainer inventoryContainer;
    private PrescriptionContainer prescriptionContainer;
    private ReplenishContainer replenishContainer;
    private Staff pharmacist;

    private final Scanner sc;

    public PharmacistMenu(int pharmacistRecordId,ContainerLoader containerLoader) {
        appointmentOutcomeContainer = containerLoader.getAppointmentOutcomeContainer();
        inventoryContainer = containerLoader.getInventoryContainer();
        prescriptionContainer = containerLoader.getPrescriptionContainer();
        replenishContainer = containerLoader.getReplenishContainer();
        StaffContainer staffContainer = containerLoader.getStaffContainer();
        pharmacist=(Staff)staffContainer.getRecord(pharmacistRecordId);
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        int choice;
        do {
            System.out.println("Pharmacist Menu");
            
            System.out.println("1. View Appointment Outcome and Manage Prescriptions");
            System.out.println("2. View Medication Inventory and Request Replenishment");
            System.out.println("3. Change Password");
            System.out.println("0. Log out");

            System.out.print("Enter your choice: ");
            choice = Utility.inputSafeInt(sc);
            switch (choice) {
                case 0:
                    System.out.println("Logging out...");
                    break;
                case 1:
                    manageAppointmentOutcomeAndPrescriptions();
                    break;
                case 2:
                    viewInventoryAndRequestReplenishment();
                    break;
                case 3:
                    if( Utility.changePasswordProcedure(pharmacist)) return;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }

    private void manageAppointmentOutcomeAndPrescriptions() {
        ArrayList<AppointmentOutcome> outcomes = appointmentOutcomeContainer.getAllOutcomes();
        if (outcomes.isEmpty()) {
            System.out.println("No appointment outcome records available.");
        } else {
            for (int i=0;i<outcomes.size();i++) {
                AppointmentOutcome outcome = outcomes.get(i);
                System.out.println((i+1)+".");
                System.out.println("Appointment Outcome Record ID: " + outcome.getRecordId());
                System.out.println("Appointment Record ID:"+outcome.getAppointmentRecordId());
                System.out.println("Service Type: " + outcome.getServiceType());
                System.out.println("Prescriptions:");
                for (int prescriptionRecordId : outcome.getPrescriptionRecordIds()) {
                    Prescription prescription = (Prescription)prescriptionContainer.getRecord(prescriptionRecordId);
                    System.out.println(prescription);
                }
                System.out.println("Consultation Notes:");
                System.out.println(outcome.getConsultationNote());
                System.out.println("---------------------------------------------------\n");
            }   
            int choice;
            do {
                System.out.println("Choose an appointment outcome to manage prescriptions (0. Back): ");
                choice=Utility.inputSafeInt(sc);
                if (choice>=1 && choice<=outcomes.size()) {
                    updatePrescriptionStatus(outcomes.get(choice-1));
                    break;
                } else if (choice == 0) {
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            } while (choice != 0);
        }
    }

    private void updatePrescriptionStatus(AppointmentOutcome outcome) {
        if (outcome != null) {
            System.out.println("Prescriptions for Appointment Outcome ID " + outcome.getRecordId() + ":");
            for (int i=0;i<outcome.getPrescriptionRecordIds().size();i++) {
                Prescription prescription = (Prescription)prescriptionContainer.getRecord(outcome.getPrescriptionRecordId(i));
                String medicineName=prescription.getMedication();
                int inventoryRecordId=inventoryContainer.getRecordIdByMedicineName(medicineName);
                
                System.out.println((i+1)+". "+prescription);
                if (inventoryContainer.getRecord(inventoryRecordId)==null) {
                    System.out.println("\tNew medicine, not found in inventory.");
                } else {
                    int currentStock=((Inventory)inventoryContainer.getRecord(inventoryRecordId)).getCurrentStock();
                    System.out.println("\tcurrent stock: "+currentStock);
                }
            }
            int choice;
            do {
                System.out.println("Choose a prescription to dispense (0. Back): ");
                choice = Utility.inputSafeInt(sc);
                if (choice == 0) {
                    break;
                }
                else if (choice>=1 && choice<=outcome.getPrescriptionRecordIds().size()) {
                    Prescription prescription = (Prescription)prescriptionContainer.getRecord(outcome.getPrescriptionRecordId(choice-1));
                    if (prescription.getStatus() == Prescription.Status.DISPENSED) {
                        System.out.println("Prescription already dispensed.");
                        continue;
                    }
                    int medicineRecordId=inventoryContainer.getRecordIdByMedicineName(prescription.getMedication());
                    String medication=prescription.getMedication();
                    if (medicineRecordId==-1) {
                        System.out.println("New medicine, not found in inventory.");
                        System.out.println("Create a new inventory record for this medicine? (y/n): ");
                        String choiceStr = Utility.inputNonEmptyString(sc);
                        if (choiceStr.equalsIgnoreCase("y")) {
                            System.out.println("Enter the stock alert threshold: ");
                            int threshold = Utility.inputSafeInt(sc);
                            Inventory inventory = new Inventory(medication, 0, threshold);
                            inventoryContainer.putRecord(inventory);                            
                        } else {
                            System.out.println("Not operated");
                        }
                        continue;
                    }
                    int dosage=prescription.getDosage();
                    int currentStock=((Inventory)inventoryContainer.getRecord(medicineRecordId)).getCurrentStock();
                    if (currentStock<dosage) {
                        System.out.println("Not enough stock available for this prescription.");
                    } else {
                        prescription.setStatus(Prescription.Status.DISPENSED);
                        ((Inventory)inventoryContainer.getRecord(medicineRecordId)).setCurrentStock(currentStock-dosage);
                        System.out.println("Prescription dispensed successfully.");
                    }
                    break;
                }
            } while (choice != 0);
        } else {
            System.out.println("Appointment Outcome not found.");
        }
    }

    private void viewInventoryAndRequestReplenishment() {

        // TODO

        ArrayList<String> medications = inventoryContainer.getMedicineNames();
        medications.sort(null);
        if (medications.isEmpty()) {
            System.out.println("No inventory records.");
            return;
        }

        for (int i=0;i<medications.size();i++) {
            Inventory inventory = (Inventory)inventoryContainer.getRecord(inventoryContainer.getRecordIdByMedicineName(medications.get(i)));
            System.out.println((i+1)+". "+inventory);
        }

        int choice;
        do {
            System.out.println("Choose a medication to request replenishment (0. Back): ");
            choice = Utility.inputSafeInt(sc);
            if (choice == 0) {
                break;
            }
            else if (choice>=1 && choice<=medications.size()) {
                String medication = medications.get(choice-1);
                System.out.println("Requesting replenishment for medication: "+medication);
                int inventoryRecordId = inventoryContainer.getRecordIdByMedicineName(medication);
                System.out.print("Enter quantity to replenish: ");
                int quantity = Utility.inputSafeInt(sc);
                Replenish replenish = new Replenish(inventoryRecordId, quantity,pharmacist.getRecordId());
                replenishContainer.putRecord(replenish);
                System.out.println("Replenishment request submitted successfully.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
}
        // System.out.print("Enter the medication name for replenishment: ");
        // Utility.inputNonEmptyString(sc); // Consume newline left from previous input
        // String medication = Utility.inputNonEmptyString(sc);
        // int medicationRecordId = inventoryContainer.getRecordIdByMedicineName(medication);
        // if (medicationRecordId == -1) {
        //     System.out.println("Medication not found in inventory.");
        //     return;
        // }

        // System.out.print("Enter the medication name for replenishment: ");
        // Utility.inputNonEmptyString(sc); // Consume newline left from previous input
        // String medication = Utility.inputNonEmptyString(sc);
        // System.out.print("Enter quantity to replenish: ");
        // int quantity = Utility.inputSafeInt(sc);
        // Utility.inputNonEmptyString(sc); // Clear buffer
        // inventoryContainer.requestReplenishment(medication, quantity);
        // System.out.println("Replenishment request submitted successfully.");

