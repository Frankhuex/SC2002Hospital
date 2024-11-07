package org.sc2002.hospital.container.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.data.Inventory;

public class InventoryContainer extends RecordContainer {
    public InventoryContainer() {
        super();
    }
    public InventoryContainer(String filePath) {
        super();
        try (
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis)
        ) {  
            Sheet sheet = workbook.getSheetAt(0);  // 获取第一个工作表
            for (int i=1;i<=sheet.getLastRowNum();i++) {
                Row row=sheet.getRow(i);
                String medicineName=row.getCell(0).getStringCellValue();
                int currentStock=(int)(row.getCell(1).getNumericCellValue());
                int alertThreshold=(int)(row.getCell(2).getNumericCellValue());
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
        return 0;
    }

    // written by pharmacist
    // Method to view inventory details //FR
    // public void viewInventory() {
    //     for (String medication : inventory.keySet()) {
    //         int stock = inventory.get(medication);
    //         System.out.println("Medication: " + medication + "; Stock Level: " + stock);
    //     }
    // }

    // Method to request replenishment of a medication
    // Admin version, remember to edit PharmacistMenu
    public void requestReplenishment(String medication, int quantity) {
        if(containsMedicine(medication)) {
            int recordId = getRecordIdByMedicineName(medication);
            Inventory inventory = (Inventory) getRecord(recordId);
            inventory.setRequest(quantity);
        }
    }


}
