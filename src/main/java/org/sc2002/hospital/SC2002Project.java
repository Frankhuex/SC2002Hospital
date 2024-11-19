/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.sc2002.hospital;
import org.sc2002.hospital.container.ContainerLoader;
import org.sc2002.hospital.menu.StartMenu;
import org.sc2002.hospital.utility.Utility;
/**
 *
 * @author apple
 */
public class SC2002Project {
    public static void main(String[] args) {
        System.out.println(Utility.CURRENTDATE);
        String[] paths={
            "data/appointment.csv",
            "data/appointmentOutcome.csv",
            "data/inventory.csv",
            "data/medical.csv",
            "data/prescription.csv",
            "data/replenish.csv",
            "data/patient.csv",
            "data/staff.csv"
        };
        ContainerLoader containerLoader=new ContainerLoader(paths);
        //System.out.println(Record.getIdCounter());
        StartMenu startMenu=new StartMenu(containerLoader);
        startMenu.run();
        containerLoader.writeCSVs();
    }
    /*
    public static void main1(String[] args) {
        System.out.println(Utility.CURRENTDATE);

        String patientPath="data/patient.csv";
        String staffPath="data/staff.csv";
        String inventoryPath="data/inventory.csv";
        String medicalPath="data/medical.csv";
        String appointmentPath="data/appointment.csv";
        String appointmentOutcomePath="data/appointmentOutcome.csv";
        String replenishPath="data/replenish.csv";
        String prescriptionPath="data/prescription.csv";
        String[] paths={patientPath,staffPath,inventoryPath,medicalPath,appointmentPath,appointmentOutcomePath,prescriptionPath};

        Utility.removeEmptyLines(paths);
        PatientContainer patientContainer=new PatientContainer(patientPath);
        StaffContainer staffContainer=new StaffContainer(staffPath);
        InventoryContainer inventoryContainer=new InventoryContainer(inventoryPath);
        MedicalContainer medicalContainer=new MedicalContainer(medicalPath);
        AppointmentContainer appointmentContainer=new AppointmentContainer(appointmentPath);
        AppointmentOutcomeContainer appointmentOutcomeContainer=new AppointmentOutcomeContainer(appointmentOutcomePath);
        ReplenishContainer replenishContainer=new ReplenishContainer(replenishPath);
        PrescriptionContainer prescriptionContainer=new PrescriptionContainer(prescriptionPath);

        HashMap<String,RecordContainer> containers=new HashMap<>();
        containers.put("Patient",patientContainer);
        containers.put("Staff",staffContainer);
        containers.put("Inventory",inventoryContainer);
        containers.put("Medical",medicalContainer);
        containers.put("Appointment",appointmentContainer);
        containers.put("AppointmentOutcome",appointmentOutcomeContainer);
        containers.put("Replenish",replenishContainer);
        containers.put("Prescription",prescriptionContainer);
        for (RecordContainer container: containers.values()) {
            System.out.println(container);
        }
        StartMenu startMenu=new StartMenu(containers);
        startMenu.run();
        patientContainer.saveFile(patientPath);
        staffContainer.saveFile(staffPath);
        inventoryContainer.saveFile(inventoryPath);
        medicalContainer.saveFile(medicalPath);
        appointmentContainer.saveFile(appointmentPath);
        appointmentOutcomeContainer.saveFile(appointmentOutcomePath);
        replenishContainer.saveFile(replenishPath);
        prescriptionContainer.saveFile(prescriptionPath);
    }
    */
}
