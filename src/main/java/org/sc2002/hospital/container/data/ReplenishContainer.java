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
import org.sc2002.hospital.record.data.Replenish;
public class ReplenishContainer extends RecordContainer {
    public ReplenishContainer() {}
    public ReplenishContainer(String csvPath) {
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
                int inventoryRecordId=Integer.parseInt(values.get(1).toString());
                int quantity=Integer.parseInt(values.get(2).toString());
                int pharmacistRecordId=Integer.parseInt(values.get(3).toString());
                Replenish.Status status=Replenish.Status.valueOf(values.get(4).toString());
                Replenish replenish=new Replenish(inventoryRecordId, quantity, pharmacistRecordId, status);
                replenish.initRecordId(recordId);
                putRecord(replenish);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void writeCSV(String csvPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("\"recordId\",\"inventoryRecordId\",\"quantity\",\"pharmacistRecordId\",\"status\"");
            writer.newLine();
            while (!recordIdQueueIsEmpty()) {
                Replenish record = (Replenish) getRecord(dequeueRecordId());
                if (record==null) continue;
                String line = String.format("%d,%d,%d,%d,\"%s\"",
                    record.getRecordId(),
                    record.getInventoryRecordId(),
                    record.getQuantity(),
                    record.getPharmacistRecordId(),
                    record.getStatus().toString()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing replenish record to CSV file: " + e.getMessage());
        }
    }


    public ArrayList<Replenish> getAllReplenish() {
        ArrayList<Replenish> replenishList = new ArrayList<>();
        for (int recordId: getRecords().keySet()) {
            Replenish replenish=(Replenish) getRecord(recordId);
            if (replenish.getStatus()==Replenish.Status.PENDING)
                replenishList.add(replenish);
        }
        replenishList.sort((r1, r2) -> r1.getInventoryRecordId() - r2.getInventoryRecordId());
        return replenishList;
    }
}
