package org.sc2002.hospital.record.data;
import java.util.ArrayList;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.Record;
public class AppointmentOutcome extends Record {
    
    private int appointmentRecordId;
    private String serviceType;
    private final ArrayList<Prescription> prescriptions;
    private final ArrayList<String> consultationNotes;
    
    public AppointmentOutcome(int appointmentRecordId,String serviceType) {
        this.appointmentRecordId = appointmentRecordId;
        this.serviceType = serviceType;
        prescriptions = new ArrayList<>();
        consultationNotes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return serviceType;
    }

    //Date
    public String getDate(RecordContainer appointmentContainer) {
        return ((Appointment)(appointmentContainer.getRecord(appointmentRecordId))).getDate();
    }

    public void setDate(String date, RecordContainer appointmentContainer) {
        ((Appointment)(appointmentContainer.getRecord(appointmentRecordId))).setDate(date);
    }

    //Service Type
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    //Prescriptions
    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }
    public String getMedication(int prescriptionIndex) {
        return prescriptions.get(prescriptionIndex).getMedication();
    }
    public void setMedication(int prescriptionIndex,String medication) {
        prescriptions.get(prescriptionIndex).setMedication(medication);
    }
    public String getStatus(int prescriptionIndex) {
        return prescriptions.get(prescriptionIndex).getStatus();
    }
    public void setStatus(int prescriptionIndex,String status) {
        prescriptions.get(prescriptionIndex).setStatus(status);
    }

    //Consultation Notes
    public ArrayList<String> getConsultationNotes() {
        return consultationNotes;
    }
    public void addConsultationNote(String consultationNote) {
        consultationNotes.add(consultationNote);
    }
    public String getConsultationNote(int consultationNoteIndex) {
        return consultationNotes.get(consultationNoteIndex);
    }
    public void setConsultationNote(int consultationNoteIndex,String consultationNote) {
        consultationNotes.set(consultationNoteIndex,consultationNote);
    }
}
