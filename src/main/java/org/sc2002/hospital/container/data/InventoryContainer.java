package org.sc2002.hospital.container.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileWriter;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.data.AppointmentOutcome;
import org.sc2002.hospital.record.data.Inventory;
import org.sc2002.hospital.record.data.Medical;
import org.sc2002.hospital.record.user.Patient;


public class InventoryContainer extends RecordContainer {
    public InventoryContainer() {}
    public InventoryContainer(String csvPath) {
        readCSV(csvPath);
    }

    @Override
    public final void readCSV(String csvPath) {
        String line;
        String delimiter=",";
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|\\b(\\d+)\\b");
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath)))
        {  
            br.readLine(); // skip header row
            while ((line=br.readLine())!=null) {
                ArrayList<Object> values=new ArrayList<>();
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (matcher.group(1)!=null) {
                        values.add(matcher.group(1));
                    } else if (matcher.group(2)!=null) {
                        values.add(Integer.parseInt(matcher.group(2)));
                    }
                }
                int recordId=Integer.parseInt(values.get(0).toString());
                String medicineName=(String) values.get(1);
                int currentStock=Integer.parseInt(values.get(2).toString());
                int alertThreshold=Integer.parseInt(values.get(3).toString());
                Inventory inventory=new Inventory(
                    medicineName,
                    currentStock,
                    alertThreshold
                );
                inventory.initRecordId(recordId); 
                putRecord(inventory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCSV(String csvPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("\"recordId\",\"medicineName\",\"currentStock\",\"alertThreshold\",\"pharmacistRefillRequest\"");
            writer.newLine();
            while (!recordIdQueueIsEmpty()) {
                Inventory record = (Inventory)getRecord(dequeueRecordId());
                String line = String.format("%d,\"%s\",%d,%d",
                    record.getRecordId(),
                    record.getMedicineName(),
                    record.getCurrentStock(),
                    record.getAlertThreshold()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing inventory record to CSV file: " + e.getMessage());
        }
    }
    
    /*
    public InventoryContainer(String csvPath) {
        super();
        String line;
        String delimiter=",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath)))
        {  
            br.readLine(); // skip header row
            while ((line=br.readLine())!=null) {
                String[] values=line.split(delimiter);
                String medicineName=values[0];
                int currentStock=Integer.parseInt(values[1]);
                int alertThreshold=Integer.parseInt(values[2]);
                Inventory inventory=new Inventory(
                    medicineName,
                    currentStock,
                    alertThreshold
                );
                putRecord(inventory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    
    public boolean containsMedicine(String medicineName) {
        for (int recordId : getRecords().keySet()) {
            Inventory inventory = (Inventory) getRecord(recordId);
            if (inventory.getMedicineName().equals(medicineName)) {
                return true;
            }
        }
        return false;
    }

    public String getMedicineNameByRecordId(int recordId) {
        return ((Inventory)(getRecord(recordId))).getMedicineName();
    }

    public int getRecordIdByMedicineName(String medicineName) {
        for (int recordId : getRecords().keySet()) {
            Inventory inventory = (Inventory) getRecord(recordId);
            if (inventory.getMedicineName().equals(medicineName)) {
                return recordId;
            }
        }
        return -1;
    }

    public ArrayList<String> getMedicineNames() {
        ArrayList<String> medicineNames = new ArrayList<String>();
        for (int recordId : getRecords().keySet()) {
            Inventory inventory = (Inventory) getRecord(recordId);
            medicineNames.add(inventory.getMedicineName());
        }
        return medicineNames;
    }

    // written by pharmacist
    // Method to view inventory details //FR
    // public void viewInventory() {
    //     for (String medication : inventory.keySet()) {
    //         int stock = inventory.get(medication);
    //         System.out.println("Medication: " + medication + "; Stock Level: " + stock);
    //     }
    // }

    public void viewInventory() {
        System.out.println(this);
    }

}
