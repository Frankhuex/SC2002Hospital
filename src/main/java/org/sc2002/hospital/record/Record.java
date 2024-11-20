package org.sc2002.hospital.record;

public abstract class Record {
    private static int idCounter=0;
    private int recordId;
    private static int maxInitRecordId=0;

    public Record() {
        recordId=idCounter++;
    }

    public int getRecordId() {
        return recordId;
    }

    public void initRecordId(int recordId) {
        maxInitRecordId=Math.max(maxInitRecordId,recordId);
        this.recordId = recordId;
        idCounter=maxInitRecordId+1;
        //System.out.println(idCounter);
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
