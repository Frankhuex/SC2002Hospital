package org.sc2002.hospital.record.data;
import org.sc2002.hospital.record.Record;
public class Appointment extends Record {
    private String date;
    private int patientRecordId;
    private int doctorRecordId;

    public enum Status {PENDING,CONFIRMED,CANCELED,COMPLETED,UNAVAILABLE};
    private Status status=Status.PENDING;

    public Appointment(String date, int patientRecordId, int doctorRecordId) {
        this.date = date;
        this.patientRecordId = patientRecordId;
        this.doctorRecordId = doctorRecordId;
        status=Status.PENDING;
    }
    public Appointment(String date, int patientRecordId, int doctorRecordId, Status status) {
        this.date = date;
        this.patientRecordId = patientRecordId;
        this.doctorRecordId = doctorRecordId;
        this.status = status;
    }

    @Override
    public String toString() {
        return date;
    }

    //Date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {   // Setter method for date
        this.date = date;
    }


    //Patient Record ID
    public int getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(int patientRecordId) {   // Setter method for patientRecordId
        this.patientRecordId = patientRecordId;
    }

    //Doctor Record ID

    public int getDoctorRecordId() {
        return doctorRecordId;
    }
    public void setDoctorRecordId(int doctorRecordId) {   // Setter method for doctorRecordId
        this.doctorRecordId = doctorRecordId;
    }

    //Status
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {   // Setter method for status
        this.status = status;
    }


}
