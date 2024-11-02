package org.sc2002.hospital.record.data;
import org.sc2002.hospital.record.Record;
public class Appointment extends Record {
    private String date;
    private int patientRecordId;
    private int doctorRecordId;
    private String status="pending";

    public Appointment(String date, int patientRecordId, int doctorRecordId) {
        this.date = date;
        this.patientRecordId = patientRecordId;
        this.doctorRecordId = doctorRecordId;
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {   // Setter method for status
        this.status = status;
    }


}
