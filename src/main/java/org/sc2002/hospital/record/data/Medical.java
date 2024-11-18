package org.sc2002.hospital.record.data;
import java.util.ArrayList;
import java.util.Arrays;

import org.sc2002.hospital.record.Record;


public class Medical extends Record {
    private int patientRecordId;
    private ArrayList<String> treatmentPlans;
    private ArrayList<String> prescriptions;
    private ArrayList<String> diagnoses;

    public Medical(int patientRecordId) {
        this.patientRecordId = patientRecordId;
        this.treatmentPlans = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
    }

    public Medical(int patientRecordId, String[] treatmentPlans, String[] prescriptions, String[] diagnoses) {
        this.patientRecordId = patientRecordId;
        this.treatmentPlans = new ArrayList<>(treatmentPlans.length);
        this.treatmentPlans.addAll(Arrays.asList(treatmentPlans));
        this.prescriptions = new ArrayList<>(prescriptions.length);
        this.prescriptions.addAll(Arrays.asList(prescriptions));
        this.diagnoses = new ArrayList<>(diagnoses.length);
        this.diagnoses.addAll(Arrays.asList(diagnoses));
    }

    @Override
    public String toString() {
        return patientRecordId + " " + treatmentPlans + " " + prescriptions + " " + diagnoses;
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

    public void addTreatmentPlans(ArrayList<String> treatmentPlans) {
        this.treatmentPlans.addAll(treatmentPlans);
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

    public void addPrescription(String prescription) {
        prescriptions.add(prescription);
    }

    public void addPrescriptions(ArrayList<String> prescriptions) {
        this.prescriptions.addAll(prescriptions);
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

    public void addDiagnose(String diagnose) {
        diagnoses.add(diagnose);
    }

    public void addDiagnoses(ArrayList<String> diagnoses) {
        this.diagnoses.addAll(diagnoses);
    }

    public void removeDiagnose(int index) {
        if (index >= 0 && index < diagnoses.size()) {
            diagnoses.remove(index);
        }
    }
}
