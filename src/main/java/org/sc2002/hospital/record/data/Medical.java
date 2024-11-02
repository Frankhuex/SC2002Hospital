package org.sc2002.hospital.record.data;
import java.util.ArrayList;

import org.sc2002.hospital.record.Record;


public class Medical extends Record {
    private int patientRecordId;
    private ArrayList<String> treatmentPlans;
    private ArrayList<String> prescriptions;
    private ArrayList<String> diagnoses;

    public Medical(int patientRecordId) {
        super();
        this.patientRecordId = patientRecordId;
        this.treatmentPlans = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Patient"+patientRecordId;
    }

    //Patient Record ID
    public int getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(int patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    // public String getPatientId(RecordContainer rc) {
    //     return rc.getRecord
    // }

    //Treatment Plans
    public ArrayList<String> getTreatmentPlans() {
        return treatmentPlans;
    }

    public void setTreatmentPlans(ArrayList<String> treatmentPlans) {
        this.treatmentPlans = treatmentPlans;
    }

    public void addTreatmentPlan(String treatmentPlan) {
        treatmentPlans.add(treatmentPlan);
    }

    public void removeTreatmentPlan(int index) {
        if (index >= 0 && index < treatmentPlans.size()) {
            treatmentPlans.remove(index);
        }
    }

    //Prescriptions
    public ArrayList<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(ArrayList<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addPrescriptions(String prescription) {
        prescriptions.add(prescription);
    }

    public void removePrescription(int index) {
        if (index >= 0 && index < prescriptions.size()) {
            prescriptions.remove(index);
        }
    }

    //Diagnoses
    public ArrayList<String> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(ArrayList<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public void addDiagnoses(String diagnose) {
        diagnoses.add(diagnose);
    }

    public void removeDiagnose(int index) {
        if (index >= 0 && index < diagnoses.size()) {
            diagnoses.remove(index);
        }
    }
}
