package org.sc2002.hospital.container;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.sc2002.hospital.record.Record;
public abstract class RecordContainer {
    private final HashMap<Integer,Record> records;
    private final Queue<Integer> recordIdQueue;
    public RecordContainer() {
        records = new HashMap<>();
        recordIdQueue = new LinkedList<>();
    }
    public abstract void readCSV(String csvPath);
    public abstract void writeCSV(String csvPath);
    public void putRecord(Record record) {
        records.put(record.getRecordId(), record);
        recordIdQueue.add(record.getRecordId());
    }
    public void removeRecord(int recordId) {
        if (records.containsKey(recordId))
            records.remove(recordId);
    }
    public Record getRecord(int recordId) {
        if (!records.containsKey(recordId))
            return null;
        return records.get(recordId);
    }

    public HashMap<Integer,Record> getRecords() {
        return records;
    }

    public int dequeueRecordId() {
        if (recordIdQueue.isEmpty())
            return -1;
        return recordIdQueue.remove();
    }

    public boolean recordIdQueueIsEmpty() {
        return recordIdQueue.isEmpty();
    }

    

    public Boolean containsRecord(int recordId) {
        return records.containsKey(recordId);
    }

    public void clear() {
        records.clear();
    }

    @Override
    public String toString() {
        String result = "";
        for (int i:records.keySet()) {
            result+=i+": "+records.get(i).toString()+"\n";
        }
        return result;
    }
}
