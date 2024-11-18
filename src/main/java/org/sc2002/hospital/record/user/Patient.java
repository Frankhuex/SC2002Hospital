package org.sc2002.hospital.record.user;

public class Patient extends User {
    private String dateOfBirth;
    private String phoneNumber;
    private String bloodType;

    public Patient(
        String hospitalId, 
        String password, 
        String name, 
        String gender, 
        String userType, 
        String email,
        
        String dateOfBirth,
        String phoneNumber,
        String bloodType
        ) 
    {
        super(hospitalId, password, name, gender, userType,email);

        this.dateOfBirth = dateOfBirth; 
        this.phoneNumber = phoneNumber;
        this.bloodType = bloodType;
    }

    public Patient(String hospitalId) {
        super(hospitalId,"password",hospitalId,"","Patient","");
        this.dateOfBirth = "";
        this.phoneNumber = "";
        this.bloodType = "";
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
