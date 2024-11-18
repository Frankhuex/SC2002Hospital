package org.sc2002.hospital.container.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sc2002.hospital.record.user.Staff;

public class StaffContainer extends UserContainer {
    public StaffContainer() {}

    public StaffContainer(String csvPath) {
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
                int age=Integer.parseInt(values.get(7).toString());
                //System.out.println(hospitalId+","+password+","+userType);
                Staff staff=new Staff(
                    hospitalId,
                    password,
                    name,
                    gender,
                    userType,
                    email,
                    age
                );
                staff.initRecordId(recordId);
                putRecord(staff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCSV(String csvPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("\"recordId\",\"hospitalId\",\"password\",\"name\",\"gender\",\"userType\",\"email\",\"age\"");
            writer.newLine();
            while (!recordIdQueueIsEmpty()) {
                Staff record = (Staff) getRecord(dequeueRecordId());
                String line=String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d",
                    record.getRecordId(),
                    record.getHospitalId(),
                    record.getPassword(),
                    record.getName(),
                    record.getGender(),
                    record.getUserType(),
                    record.getEmail(),
                    record.getAge()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing patient record to CSV file: " + e.getMessage());
        }
    }

    /*public StaffContainer(String csvPath) {
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
                String userType=values[2];
                String gender=values[3];
                int age=Integer.parseInt(values[4]);
                Staff staff=new Staff(
                    hospitalId,
                    "password",
                    name,
                    gender,
                    userType,
                    "",
                    age
                );
                putRecord(staff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */

}