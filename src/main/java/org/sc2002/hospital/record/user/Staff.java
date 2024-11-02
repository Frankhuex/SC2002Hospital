package org.sc2002.hospital.record.user;

public class Staff extends User {
    private int age;

    public Staff(
        String hospitalId, 
        String password, 
        String name, 
        String gender, 
        String userType, 
        String email,
        int age) 
    {
        super(hospitalId, password, name, gender, userType, email);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
