package org.sc2002.hospital.record.data;
import org.sc2002.hospital.record.Record;
public class Inventory extends Record {
    private String medicineName;
    private int currentStock;
    private int alertThreshold;
    public Inventory(String medicineName, int currentStock, int alertThreshold) {
        this.medicineName = medicineName;
        this.currentStock = currentStock;
        this.alertThreshold = alertThreshold;
    }

    @Override
    public String toString() {
        return medicineName;
    }

    //Medicine Name
    public String getMedicineName() {
        return medicineName;
    }
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    //Current Stock
    public int getCurrentStock() {
        return currentStock;
    }
    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }
    //Alert Threshold
    public int getAlertThreshold() {
        return alertThreshold;
    }
    public void setAlertThreshold(int alertThreshold) {
        this.alertThreshold = alertThreshold;
    }
}
