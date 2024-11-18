package org.sc2002.hospital.record.data;
import org.sc2002.hospital.record.Record;
public class Prescription extends Record {
    private String medication;
    private int dosage;
    public enum Status {PENDING,DISPENSED};
    private Status status=Status.PENDING;
    public Prescription(String medication,int dosage) {
        this.medication = medication;
        this.dosage = dosage;
        status=Status.PENDING;
    }

    public Prescription(String medication,int dosage,Status status) {
        this.medication = medication;
        this.dosage = dosage;
        this.status=status;
    }

    @Override
    public String toString() {
        return "Medication: "+medication+", dosage: "+dosage+", status: "+status;
    }

    public String getMedication() {
        return medication;
    }
    public void setMedication(String medication) {
        this.medication = medication;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }
}