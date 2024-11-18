package org.sc2002.hospital.container.user;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sc2002.hospital.record.Record;
import org.sc2002.hospital.record.user.Patient;

public class PatientContainer extends UserContainer {
    public PatientContainer() {}
    public PatientContainer(String csvPath) {
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
                String hospitalId=values.get(1).toString();
                String password=values.get(2).toString();
                String name=values.get(3).toString();
                String gender=values.get(4).toString();
                String userType=values.get(5).toString();
                String email=values.get(6).toString();
                String dateOfBirth=values.get(7).toString();
                String phoneNumber=values.get(8).toString();
                String bloodType=values.get(9).toString();
                Patient patient=new Patient(
                    hospitalId,
                    password,
                    name,
                    gender,
                    userType,
                    email,
                    dateOfBirth,
                    phoneNumber,
                    bloodType
                );
                patient.initRecordId(recordId);
                putRecord(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCSV(String csvPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("\"recordId\",\"hospitalId\",\"password\",\"name\",\"gender\",\"userType\",\"email\",\"dateOfBirth\",\"phoneNumber\",\"bloodType\"");
            writer.newLine();
            while (!recordIdQueueIsEmpty()) {
                Patient patient = (Patient) getRecord(dequeueRecordId());
                String line=String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                    patient.getRecordId(),
                    patient.getHospitalId(),
                    patient.getPassword(),
                    patient.getName(),
                    patient.getGender(),
                    patient.getUserType(),
                    patient.getEmail(),
                    patient.getDateOfBirth(),
                    patient.getPhoneNumber(),
                    patient.getBloodType()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing patient record to CSV file: " + e.getMessage());
        }
    }

    /*
    public PatientContainer(String csvPath) {
        super();
        String line;
        String delimiter=",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath)))
        {  
            br.readLine(); // skip header row
            while ((line=br.readLine())!=null) {
                String[] values=line.split(delimiter);
                String hospitalId=values[0];
                String name=values[1];
                String dateOfBirth=values[2];
                String gender=values[3];
                String bloodType=values[4];
                String email=values[5];
                Patient patient=new Patient(
                    hospitalId,
                    "password",
                    name,
                    gender,
                    "Patient",
                    email,
                    dateOfBirth,
                    "",
                    bloodType
                );
                putRecord(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public HashSet<String> getAllPatientHospitalIds() {
        HashSet<String> hospitalIds=new HashSet<>();
        for (Record patient:getRecords().values()) {
            String hospitalId=((Patient)patient).getHospitalId();
            hospitalIds.add(hospitalId);
        }
        return hospitalIds;
    }
}
