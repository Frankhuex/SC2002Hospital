/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.sc2002.hospital;
import java.util.HashMap;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.container.data.AppointmentContainer;
import org.sc2002.hospital.container.data.AppointmentOutcomeContainer;
import org.sc2002.hospital.container.data.InventoryContainer;
import org.sc2002.hospital.container.data.MedicalContainer;
import org.sc2002.hospital.container.user.PatientContainer;
import org.sc2002.hospital.container.user.StaffContainer;
import org.sc2002.hospital.menu.StartMenu;
/**
 *
 * @author apple
 */
public class SC2002Project {
    public static void main(String[] args) {
        String patientPath="data/Patient_List.xlsx";
        String staffPath="data/Staff_List.xlsx";
        String inventoryPath="data/Medicine_List.xlsx";
        
        PatientContainer patientContainer=new PatientContainer(patientPath);
        StaffContainer staffContainer=new StaffContainer(staffPath);
        
        InventoryContainer inventoryContainer=new InventoryContainer(inventoryPath);
        MedicalContainer medicalContainer=new MedicalContainer();
        AppointmentContainer appointmentContainer=new AppointmentContainer();
        AppointmentOutcomeContainer appointmentOutcomeContainer=new AppointmentOutcomeContainer();

        HashMap<String,RecordContainer> containers=new HashMap<>();
        containers.put("Patient",patientContainer);
        containers.put("Staff",staffContainer);
        containers.put("Inventory",inventoryContainer);
        containers.put("Medical",medicalContainer);
        containers.put("Appointment",appointmentContainer);
        containers.put("AppointmentOutcome",appointmentOutcomeContainer);

        StartMenu startMenu=new StartMenu(containers);
        startMenu.run();

    }
}
