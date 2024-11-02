package org.sc2002.hospital.record.data;

public class Prescription {
    private String medication;
    private String status="pending";
    public Prescription(String medication) {
        this.medication = medication;
    }

    @Override
    public String toString() {
        return medication;
    }

    public String getMedication() {
        return medication;
    }
    public void setMedication(String medication) {
        this.medication = medication;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}