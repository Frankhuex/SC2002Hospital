package org.sc2002.hospital.record.data;

import java.util.ArrayList;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.Record;

public class AppointmentOutcome extends Record {

    private int appointmentRecordId;
    private String serviceType;
    private final ArrayList<Integer> prescriptionRecordIds;
    private String consultationNote;

    public AppointmentOutcome(int appointmentRecordId, String serviceType) {
        this.appointmentRecordId = appointmentRecordId;
        this.serviceType = serviceType;
        prescriptionRecordIds = new ArrayList<>();
        consultationNote = "";
    }

    public AppointmentOutcome(int appointmentRecordId, String serviceType, ArrayList<Integer> prescriptionRecordIds, String consultationNote) {
        this.appointmentRecordId = appointmentRecordId;
        this.serviceType = serviceType;
        this.prescriptionRecordIds = prescriptionRecordIds;
        this.consultationNote = consultationNote;
    }

    public int getAppointmentRecordId() {
        return appointmentRecordId;
    }

    @Override
    public String toString() {
        return appointmentRecordId + " " + serviceType + " " + prescriptionRecordIds + " " + consultationNote;
    }

    // Date
    public String getDate(RecordContainer appointmentContainer) {
        return ((Appointment) (appointmentContainer.getRecord(appointmentRecordId))).getDate();
    }

    public void setDate(String date, RecordContainer appointmentContainer) {
        ((Appointment) (appointmentContainer.getRecord(appointmentRecordId))).setDate(date);
    }

    // Service Type
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    // Prescriptions
    public ArrayList<Integer> getPrescriptionRecordIds() {
        return prescriptionRecordIds;
    }

    public int getPrescriptionRecordId(int prescriptionIndex) {
        return prescriptionRecordIds.get(prescriptionIndex);
    }

    public void addPrescriptionRecordId(int prescriptionRecordId) {
        prescriptionRecordIds.add(prescriptionRecordId);
    }

    // public String getMedication(int prescriptionIndex) {
    //     return prescriptionRecordIds.get(prescriptionIndex).getMedication();
    // }

    // public void setMedication(int prescriptionIndex, String medication) {
    //     prescriptions.get(prescriptionIndex).setMedication(medication);
    // }

    // public Prescription.Status getStatus(int prescriptionIndex) {
    //     return prescriptions.get(prescriptionIndex).getStatus();
    // }

    // public void setStatus(int prescriptionIndex, Prescription.Status status) {
    //     prescriptions.get(prescriptionIndex).setStatus(status);
    // }

    // Method to update prescription status by medication name //FR
    // public void updatePrescriptionStatus(String medication, Prescription.Status newStatus) {
    //     for (Prescription prescription : getPrescriptions()) {
    //         if (prescription.getMedication().equals(medication)) {
    //             prescription.setStatus(newStatus);
    //             break;
    //         }
    //     }
    // }

    // Consultation Notes
    public String getConsultationNote() {
        return consultationNote;
    }

    public void setConsultationNote(String consultationNote) {
        this.consultationNote = consultationNote; 
    }


}
