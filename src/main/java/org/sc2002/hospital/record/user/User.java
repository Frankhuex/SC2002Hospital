package org.sc2002.hospital.record.user;

import java.util.Scanner;
import org.sc2002.hospital.utility.Utility;

import org.sc2002.hospital.record.Record;

public abstract class User extends Record {
    private String hospitalId;
    private String password="password";
    private String name;
    private String gender;
    private String userType;
    private String email;

    public User(
        String hospitalId, 
        String password, 
        String name, 
        String gender, 
        String userType, 
        String email) 
    {
        this.hospitalId = hospitalId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.userType = userType;
        this.email = email;
    }

    @Override
    public String toString() {
        return name;
    }

    //Hospital Id
    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    //Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Gender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //User Type
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    //Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email; 
    }

    
    

}
