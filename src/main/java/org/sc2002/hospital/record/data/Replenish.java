package org.sc2002.hospital.record.data;
import org.sc2002.hospital.record.Record;
public class Replenish extends Record {
    private int inventoryRecordId;
    private int quantity;
    private int pharmacistRecordId;
    public enum Status {PENDING,APPROVED};
    private Status status;
    public Replenish(int inventoryRecordId, int quantity, int pharmacistRecordId, Status status) {
        this.inventoryRecordId = inventoryRecordId;
        this.quantity = quantity;
        this.pharmacistRecordId = pharmacistRecordId;
        this.status = status;
    }
    public Replenish(int inventoryRecordId, int quantity, int pharmacistRecordId) {
        this.inventoryRecordId = inventoryRecordId;
        this.quantity = quantity;
        this.pharmacistRecordId = pharmacistRecordId;
        status=Status.PENDING;
    }

    public int getInventoryRecordId() {
        return inventoryRecordId;
    }

    public void setInventoryRecordId(int inventoryRecordId) {
        this.inventoryRecordId = inventoryRecordId;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPharmacistRecordId() {
        return pharmacistRecordId;
    }

    public void setPharmacistRecordId(int pharmacistRecordId) {
        this.pharmacistRecordId = pharmacistRecordId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
