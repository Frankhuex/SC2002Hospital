package org.sc2002.hospital.record;

public abstract class Record {
    private static int idCounter=0;
    private final int recordId;
    public Record() {
        recordId=idCounter++;
    }
    public int getRecordId() {
        return recordId;
    }

    @Override
    public String toString() {
        return ""+recordId;
    }
}
