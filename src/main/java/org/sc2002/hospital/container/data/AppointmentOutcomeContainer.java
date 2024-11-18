package org.sc2002.hospital.container.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.Record;
import org.sc2002.hospital.record.data.AppointmentOutcome;

public class AppointmentOutcomeContainer extends RecordContainer {
    private final HashMap<String, Integer> userMap = new HashMap<>(); // Maps hospitalId to recordId
    public AppointmentOutcomeContainer() {}
    public AppointmentOutcomeContainer(String csvPath) {
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
                int appointmentRecordId=Integer.parseInt(values.get(1).toString());
                String serviceType=values.get(2).toString();
                String prescriptionRecordIds=values.get(3).toString();
                String[] prescriptionRecordIdArray=prescriptionRecordIds.split(",");
                ArrayList<Integer> prescriptionRecordIdList=new ArrayList<>();
                for (String prescriptionRecordId : prescriptionRecordIdArray) {
                    prescriptionRecordIdList.add(Integer.parseInt(prescriptionRecordId));
                }
                String consultaionNote=values.get(4).toString();
                AppointmentOutcome appointmentOutcome=new AppointmentOutcome(
                    appointmentRecordId,
                    serviceType,
                    prescriptionRecordIdList,
                    consultaionNote
                );
                appointmentOutcome.initRecordId(recordId);
                //System.out.println(appointmentOutcome);
                putRecord(appointmentOutcome);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCSV(String csvPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("\"recordId\",\"appointmentRecordId\",\"serviceType\",\"prescriptionRecordIds\",\"consultaionNote\"");
            writer.newLine();
            while (!recordIdQueueIsEmpty()) {
                AppointmentOutcome record = (AppointmentOutcome)getRecord(dequeueRecordId());
                String pRecordIds = String.join(",", record.getPrescriptionRecordIds().toString())
                    .replace("[", "")
                    .replace("]", "")
                    .replace(" ", "");
                String line = String.format("%d,%d,\"%s\",\"%s\",\"%s\"",
                    record.getRecordId(),
                    record.getAppointmentRecordId(),
                    record.getServiceType(),
                    pRecordIds,
                    record.getConsultationNote()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing appointment outcome record to CSV file: " + e.getMessage());
        }
    }


    // Add an AppointmentOutcome to the container

    // Check if a user exists in the container by hospitalId
    public Boolean containsUser(String hospitalId) {
        return userMap.containsKey(hospitalId);
    }

    // Get record ID by hospital ID
    public Integer getRecordIdByHospitalId(String hospitalId) {
        return userMap.get(hospitalId);
    }

    // Get AppointmentOutcome by appointmentRecordId
    public ArrayList<AppointmentOutcome> getAllByAppointmentRecordId(int appointmentRecordId) {
        ArrayList<AppointmentOutcome> matchingOutcomes = new ArrayList<>();

        for (org.sc2002.hospital.record.Record record : getRecords().values()) { // Explicitly refer to your custom
                                                                                 // Record
            if (record instanceof AppointmentOutcome) { // Check if the record is an AppointmentOutcome
                AppointmentOutcome outcome = (AppointmentOutcome) record;
                if (outcome.getAppointmentRecordId() == appointmentRecordId) {
                    matchingOutcomes.add(outcome); // Add matching outcome to the list
                }
            }
        }

        return matchingOutcomes; // Return the list of matching outcomes
    }

    public AppointmentOutcome getByAppointmentRecordId(int appointmentRecordId) {
        for (Record record : getRecords().values()) { 
            if (record instanceof AppointmentOutcome) { // Check if the record is an AppointmentOutcome
                AppointmentOutcome outcome = (AppointmentOutcome)record;
                if (outcome.getAppointmentRecordId() == appointmentRecordId) {
                    return outcome;
                }
            }
        }
        return null; // Return null if no matching record is found
    }

    // Add this method to return all AppointmentOutcome objects //FR
    public ArrayList<AppointmentOutcome> getAllOutcomes() {
        ArrayList<AppointmentOutcome> allOutcomes = new ArrayList<>();
        for (org.sc2002.hospital.record.Record record : getRecords().values()) {
            if (record instanceof AppointmentOutcome) {
                allOutcomes.add((AppointmentOutcome) record);
            }
        }
        return allOutcomes;
    }

}
