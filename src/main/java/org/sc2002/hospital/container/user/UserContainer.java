package org.sc2002.hospital.container.user;
import java.util.HashMap;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.Record;
import org.sc2002.hospital.record.user.User;

public class UserContainer extends RecordContainer {
    private final HashMap<String,Integer> userMap;
    public UserContainer() {
        super();
        userMap = new HashMap<>();
    }

    @Override
    public void putRecord(Record record) {
        super.putRecord(record);
        userMap.put(((User)record).getHospitalId(),record.getRecordId());
    }

    public Boolean containsUser(String hospitalId) {
        return userMap.containsKey(hospitalId);
    }

    public int getRecordIdByHospitalId(String hospitalId) {
        return userMap.get(hospitalId);
    }
}
