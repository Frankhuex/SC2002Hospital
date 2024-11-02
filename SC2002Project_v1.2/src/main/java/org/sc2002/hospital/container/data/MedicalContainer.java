package org.sc2002.hospital.container.data;
import java.util.ArrayList;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.data.Medical;

public class MedicalContainer extends RecordContainer {
    public ArrayList<Integer> searchPatientMedicalRecord(int patientRecordId) {
        ArrayList<Integer> medicalRecordIds=new ArrayList<>();
        for (int recordId : getRecords().keySet()) {
            if (((Medical)getRecord(recordId)).getPatientRecordId()==patientRecordId) {
                medicalRecordIds.add(recordId);
            }
        }
        return medicalRecordIds;
    }
}
