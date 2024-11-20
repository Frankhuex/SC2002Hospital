package org.sc2002.hospital.container.data;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.data.Prescription;
public class PrescriptionContainer extends RecordContainer {
    public PrescriptionContainer() {}
    public PrescriptionContainer(String csvPath) {
        readCSV(csvPath);
    }

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
                String medication=values.get(1).toString();
                int dosage=Integer.parseInt(values.get(2).toString());
                Prescription.Status status=Prescription.Status.valueOf(values.get(3).toString());
                Prescription prescription=new Prescription(medication,dosage,status);
                prescription.initRecordId(recordId);
                putRecord(prescription);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCSV(String csvPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("\"recordId\",\"medication\",\"dosage\",\"status\"");
            writer.newLine();
            while (!recordIdQueueIsEmpty()) {
                Prescription prescription = (Prescription) getRecord(dequeueRecordId());
                if (prescription==null) continue;
                String line = String.format("%d,\"%s\",%d,\"%s\"",
                    prescription.getRecordId(),
                    prescription.getMedication(),
                    prescription.getDosage(),
                    prescription.getStatus().toString()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing prescription record to CSV file: " + e.getMessage());
        }
    }
}
