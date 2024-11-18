package org.sc2002.hospital.record;

public abstract class Record {
    private static int idCounter=0;
    private int recordId;

    public Record() {
        recordId=idCounter++;
    }
    public int getRecordId() {
        return recordId;
    }

    public void initRecordId(int recordId) {
        this.recordId = recordId;
        idCounter=Math.max(recordId,idCounter);
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        Record.idCounter = idCounter;
    }

    @Override
    public String toString() {
        return ""+recordId;
    }
}
