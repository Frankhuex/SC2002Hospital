package org.sc2002.hospital.container.data;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sc2002.hospital.container.RecordContainer;
import org.sc2002.hospital.record.data.Appointment;
import org.sc2002.hospital.utility.Utility;

public class AppointmentContainer extends RecordContainer {
    public AppointmentContainer() {}
    public AppointmentContainer(String csvPath) {
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
                int recordId = Integer.parseInt(values.get(0).toString());
                String date=values.get(1).toString();
                int patientRecordId=Integer.parseInt(values.get(2).toString());
                int doctorRecordId=Integer.parseInt(values.get(3).toString());
                Appointment.Status status=Appointment.Status.valueOf(values.get(4).toString());
                Appointment appointment=new Appointment(date, patientRecordId, doctorRecordId, status);
                appointment.initRecordId(recordId);
                putRecord(appointment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCSV(String csvPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            writer.write("\"recordId\",\"date\",\"patientRecordId\",\"doctorRecordId\",\"status\"");
            writer.newLine();
            while (!recordIdQueueIsEmpty()) {
                Appointment record = (Appointment) getRecord(dequeueRecordId());
                if (record==null) continue;
                String line = String.format("%d,\"%s\",%d,%d,\"%s\"",
                    record.getRecordId(),
                    record.getDate(),
                    record.getPatientRecordId(),                    
                    record.getDoctorRecordId(),
                    record.getStatus().toString()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing appointment record to CSV file: " + e.getMessage());
        }
    }

    public ArrayList<Appointment> searchAppointmentsByPatientrecordId(int patientRecordId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        for (int recordId : getRecords().keySet()) {
            Appointment appointment = (Appointment) getRecord(recordId);
            if (appointment.getPatientRecordId() == patientRecordId) {
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public ArrayList<Appointment> searchAppointmentsByDoctorrecordId(int doctorRecordId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        for (int recordId : getRecords().keySet()) {
            Appointment appointment = (Appointment) getRecord(recordId);
            if (appointment.getDoctorRecordId() == doctorRecordId) {
                appointments.add(appointment);
            }
        }
        return appointments; // Returns a list of Appointment objects
    }

    public Appointment searchAppointmentByDateAndDoctorId(int doctorRecordId, String date) {
        for (int recordId : getRecords().keySet()) {
            Appointment appointment = (Appointment) getRecord(recordId);
            if (appointment.getDoctorRecordId() == doctorRecordId && appointment.getDate().equals(date)) {
                return appointment; // Appointment exists for this doctor on the specified date
            }
        }
        return null; // No matching appointment found
    }

    public ArrayList<Appointment> searchAppointmentsByStatus(Appointment.Status status) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        for (int recordId : getRecords().keySet()) {
            Appointment appointment = (Appointment) getRecord(recordId);
            if (appointment.getStatus() == status) {
                appointments.add(appointment);
            }
        }
        return appointments; // Returns a list of Appointment objects
    }

    public HashMap<String,Appointment> getFutureAppointmentTable(int doctorRecordId) {
        HashMap<String,Appointment> appointments = new HashMap<>();
        for (String slot: Utility.futureDateList()) {
            Appointment appointment = searchAppointmentByDateAndDoctorId(doctorRecordId, slot);
            appointments.put(slot, appointment); //appointment can be null
        }
        return appointments; 
    }

    public HashMap<String,Appointment> getPastAppointmentTable(int doctorRecordId) {
        HashMap<String,Appointment> appointments = new HashMap<>();
        for (Appointment appointment: searchAppointmentsByDoctorrecordId(doctorRecordId)) {
            if (appointment.getStatus()==Appointment.Status.CONFIRMED
                || appointment.getStatus()==Appointment.Status.COMPLETED
                ) {
                appointments.put(appointment.getDate(), appointment); //appointment can be null
            }
        }
        return appointments; 
    }

    public boolean createDoctorAppointment(String date, int patientRecordId, int doctorRecordId) {
        if (searchAppointmentByDateAndDoctorId(doctorRecordId, date)!=null) {
            System.out.println("Appointment already exists for this doctor on the specified date.");
            return false; // Appointment creation failed due to existing appointment
        }
        Appointment newAppointment = new Appointment(date, patientRecordId, doctorRecordId);
        putRecord(newAppointment);
        System.out.println("Appointment created successfully.");
        return true; // Appointment created successfully
    }

    public boolean modifyDoctorAppointment(String date, int doctorRecordId, String newDate, int newPatientRecordId) {
        for (int recordId : getRecords().keySet()) {
            Appointment appointment = (Appointment) getRecord(recordId);
            if (appointment.getDoctorRecordId() == doctorRecordId && appointment.getDate().equals(date)) {
                // Update appointment details
                appointment.setDate(newDate);
                appointment.setPatientRecordId(newPatientRecordId);
                System.out.println("Appointment modified successfully.");
                return true; // Appointment modified successfully
            }
        }
        System.out.println("No appointment found for the specified doctor on the specified date.");
        return false; // No matching appointment found
    }

    public Appointment searchAppointmentByDateAndPatientId(String date, int patientRecordId) {
        for (int recordId : getRecords().keySet()) {
            Appointment appointment = (Appointment) getRecord(recordId);
            if (appointment.getDate().equals(date) && appointment.getPatientRecordId() == patientRecordId) {
                return appointment; // Return the matching appointment
            }
        }
        return null; // Return null if no matching appointment is found
    }

}
