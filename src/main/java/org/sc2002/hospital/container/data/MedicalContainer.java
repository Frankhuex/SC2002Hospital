package org.sc2002.hospital.container.data;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.data.Medical;

public class MedicalContainer extends RecordContainer {
    public MedicalContainer() {}

    public MedicalContainer(String csvPath) {
        readCSV(csvPath);
    }

    @Override
    public final void readCSV(String csvPath) {
        String line;
        String delimiter=",";
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|\\b(\\d+)\\b");
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath)))
        {  
            br.readLine(); // skip header row
            while ((line=br.readLine())!=null) {
                ArrayList<Object> values=new ArrayList<>();
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (matcher.group(1)!=null) {
                        values.add(matcher.group(1));
                    } else if (matcher.group(2)!=null) {
                        values.add(Integer.parseInt(matcher.group(2)));
                    }
                }
                int recordId=Integer.parseInt(values.get(0).toString());
                int patientRecordId=Integer.parseInt(values.get(1).toString());
                String[] treatmentPlans=values.get(2).toString().split(delimiter);
                String[] prescriptions=values.get(3).toString().split(delimiter);
                String[] diagnoses=values.get(4).toString().split(delimiter);
                Medical medicalRecord=new Medical(patientRecordId, treatmentPlans, prescriptions, diagnoses);
                medicalRecord.initRecordId(recordId);
                putRecord(medicalRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCSV(String csvPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("\"recordId\",\"patientRecordId\",\"treatmentPlans\",\"prescriptions\",\"diagnoses\"");
            writer.newLine();
            while (!recordIdQueueIsEmpty()) {
                Medical record = (Medical) getRecord(dequeueRecordId());
                if (record==null) continue;
                String line = String.format("%d,%d,\"%s\",\"%s\",\"%s\"",
                    record.getRecordId(),
                    record.getPatientRecordId(),
                    String.join(",", record.getTreatmentPlans()),
                    String.join(",", record.getPrescriptions()),
                    String.join(",", record.getDiagnoses())
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing medical record to CSV file: " + e.getMessage());
        }
    }

    public ArrayList<Integer> searchPatientMedicalRecord(int patientRecordId) {
        ArrayList<Integer> medicalRecordIds=new ArrayList<>();
        for (int recordId : getRecords().keySet()) {
            if (((Medical)getRecord(recordId)).getPatientRecordId()==patientRecordId) {
                medicalRecordIds.add(recordId);
            }
        }
        return medicalRecordIds;
    }

    public boolean createMedicalRecord(int patientRecordId) {
        Medical newRecord = new Medical(patientRecordId);
        putRecord(newRecord); // Store the new medical record created
        System.out.println("Medical record created for patient ID: " + patientRecordId);
        return true;
    }


    public void displayMedicalRecordsForPatient(int patientRecordId) {
        ArrayList<Integer> medicalRecordIds = searchPatientMedicalRecord(patientRecordId);

        if (medicalRecordIds.isEmpty()) {
            System.out.println("No medical records found for patient ID: " + patientRecordId);
        } else {
            System.out.println("Displaying medical records for patient ID: " + patientRecordId);
            for (int recordId : medicalRecordIds) {
                Medical medicalRecord = (Medical) getRecord(recordId);
                System.out.println("Medical Record ID: " + recordId);

                // Display treatment plans
                System.out.print("Treatment Plans: ");
                for (String treatment : medicalRecord.getTreatmentPlans()) {
                    System.out.print(treatment + " ");
                }
                System.out.println(); // New line after listing all treatments

                // Display prescriptions
                System.out.print("Prescriptions: ");
                for (String prescription : medicalRecord.getPrescriptions()) {
                    System.out.print(prescription + " ");
                }
                System.out.println(); // New line after listing all prescriptions

                // Display diagnoses
                System.out.print("Diagnoses: ");
                for (String diagnose : medicalRecord.getDiagnoses()) {
                    System.out.print(diagnose + " ");
                }
                System.out.println(); // New line after listing all diagnoses
            }
        }
    }

    public void updateMedicalRecord(Medical medicalRecord) {
        ArrayList<Integer> existingRecords = searchPatientMedicalRecord(medicalRecord.getPatientRecordId());
        if (existingRecords.isEmpty()) {
            putRecord(medicalRecord); // Store the new medical record created
            System.out.println("Medical record created.");
        } else {
            Medical existingRecord = (Medical) getRecord(existingRecords.get(0));
            existingRecord.addTreatmentPlans(medicalRecord.getTreatmentPlans());
            existingRecord.addPrescriptions(medicalRecord.getPrescriptions());
            existingRecord.addDiagnoses(medicalRecord.getDiagnoses());
            System.out.println("Medical record updated.");
        }
    }


    public boolean overwriteMedicalRecord(Medical patientRecord) {
        // Check if a medical record already exists for this patient
        ArrayList<Integer> existingRecords = searchPatientMedicalRecord(patientRecord.getPatientRecordId());

        if (!existingRecords.isEmpty()) { //that record exists
            System.out.println("A medical record already exists");
            System.out.print("Do you want to overwrite it? (yes/no): ");
            
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput.equals("yes")) {
                // Delete all existing records for this patient ID
                for (int recordId : existingRecords) {
                    removeRecord(recordId);
                }
                putRecord(patientRecord); // Add the new record
                System.out.println("Medical record has been overwritten" );
                return true;
            } else {
                System.out.println("Medical record creation canceled.");
                return false;
            }
        } else {
            // No existing record, so create a new one
            putRecord(patientRecord); // Store the new medical record
            System.out.println("Medical record created");
            return true;
        }
    }

    public boolean deleteAppointmentByrecordId(int recordID3) {
        if (containsRecord(recordID3)) {
            removeRecord(recordID3);
            System.out.println("Appointment with recordID " + recordID3 + " has been deleted.");
            return true;
        } else {
            System.out.println("Appointment with recordID " + recordID3 + " not found.");
            return false;
        }
    }

}
