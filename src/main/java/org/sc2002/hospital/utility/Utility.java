package org.sc2002.hospital.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.sc2002.hospital.record.user.User;

public class Utility {
    public static final java.time.LocalDate CURRENTDATE_ORIGINAL = java.time.LocalDate.now();
    public static final String CURRENTDATE=String.format("%02d-%02d-%d", 
        CURRENTDATE_ORIGINAL.getDayOfMonth(), 
        CURRENTDATE_ORIGINAL.getMonthValue(), 
        CURRENTDATE_ORIGINAL.getYear());

    public static int inputSafeInt(Scanner sc) {
        while (true) {
            try {
                int input = sc.nextInt();        
                if (input<0) {
                    throw new Exception();
                }
                sc.nextLine();
                return input;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a positive integer.");
                sc.nextLine();
            }
        }
    }

    public static String inputNonEmptyString(Scanner sc) {
        while (true) {
            String input = sc.nextLine();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Invalid input. Please enter a non-empty string.");
            }
        }
    }

    public static String inputSafeDate(Scanner sc) {
        while (true) {
            String date=Utility.inputNonEmptyString(sc);
            if (validateDateFormat(date)) {
                return date;
            }
        }
    }

    public static boolean isValidDateOfBirth(String date) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(date).matches();
    }

    public static int compareDate(String date1, String date2) {
        String[] dateParts1 = date1.split("-");
        String[] dateParts2 = date2.split("-");
        int year1 = Integer.parseInt(dateParts1[2]);
        int year2 = Integer.parseInt(dateParts2[2]);
        int month1 = Integer.parseInt(dateParts1[1]);
        int month2 = Integer.parseInt(dateParts2[1]);
        int day1 = Integer.parseInt(dateParts1[0]);
        int day2 = Integer.parseInt(dateParts2[0]);
        int hour1 = Integer.parseInt(dateParts1[3]);
        int hour2 = Integer.parseInt(dateParts2[3]);
        if (year1 != year2) {
            return year1 - year2;
        } else if (month1 != month2) {
            return month1 - month2;
        } else if (day1 != day2) {
            return day1 - day2;
        } else if (hour1 != hour2) {
            return hour1 - hour2;
        } else {
            return 0;
        }
    }

    public static boolean isFutureDate(String date) {
        return compareDate(date, CURRENTDATE+"-0") > 0;
    }

    public static boolean isPastDate(String date) {
        return compareDate(date, CURRENTDATE+"-24") < 0;
    }


    public static boolean validateDateFormat(String date) {
        // Validate date format (DD-MM-YYYY-Hour)
        String[] dateParts = date.split("-");
        if (dateParts.length != 4) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY-Hour format.");
            return false;
        }
        
        try {
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            int hour = Integer.parseInt(dateParts[3]);
            
            // Basic date validation
            if (day < 1 || day > 31) {
                System.out.println("Invalid day. Must be between 1 and 31.");
                return false;
            }
            if (month < 1 || month > 12) {
                System.out.println("Invalid month. Must be between 1 and 12.");
                return false;
            }
            if (year < CURRENTDATE_ORIGINAL.getYear()) {
                System.out.println("Invalid year. Cannot schedule appointments in the past.");
                return false;
            }
            if (hour < 10 || hour > 18) {
                System.out.println("Invalid hour. Hospital hours are 10 AM to 6 PM.");
                return false;
            }
            
            // Validate against current date
            java.time.LocalDate appointmentDate = java.time.LocalDate.of(year, month, day);
            if (appointmentDate.isBefore(CURRENTDATE_ORIGINAL)) {
                System.out.println("Cannot schedule appointments in the past.");
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid date format. Please use numbers for DD-MM-YYYY-Hour.");
            return false;
        } catch (Exception e) {
            System.out.println("Invalid date. Please check your input.");
            return false;
        }
    }

    public static ArrayList<String> futureDateList() {
        ArrayList<String> list=new ArrayList<String>();
        for (int day = 0; day < 7; day++) {
            java.time.LocalDate date = CURRENTDATE_ORIGINAL.plusDays(day);
            for (int hour = 10; hour <= 18; hour++) {
                String slot = String.format("%02d-%02d-%d-%d", 
                    date.getDayOfMonth(), date.getMonthValue(), date.getYear(), hour);
                list.add(slot);
            }     
        }
        sortDateList(list);
        return list;
    }

    public static ArrayList<String> pastDateList() {
        ArrayList<String> list=new ArrayList<String>();
        for (int day = -1; day > -7; day--) {
            java.time.LocalDate date = CURRENTDATE_ORIGINAL.plusDays(day);
            for (int hour = 10; hour <= 18; hour++) {
                String slot = String.format("%02d-%02d-%d-%d", 
                    date.getDayOfMonth(), date.getMonthValue(), date.getYear(), hour);
                list.add(slot);
            }     
        }
        sortDateList(list);
        return list;
    }

    public static ArrayList<String> allDateList() {
        ArrayList<String> list=new ArrayList<String>();
        for (int day = -1; day < 7; day++) {
            java.time.LocalDate date = CURRENTDATE_ORIGINAL.plusDays(day);
            for (int hour = 10; hour <= 18; hour++) {
                String slot = String.format("%02d-%02d-%d-%d", 
                    date.getDayOfMonth(), date.getMonthValue(), date.getYear(), hour);
                list.add(slot);
            }     
        }
        sortDateList(list);
        return list;
    }

    public static void sortDateList(ArrayList<String> list) {
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String slot1, String slot2) {
                return compareDate(slot1, slot2);
            }
        });
    }


    public static void removeEmptyLines(String csvPath) {
        String line;
        List<String> nonEmptyLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    nonEmptyLines.add(line); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            for (String nonEmptyLine : nonEmptyLines) {
                writer.write(nonEmptyLine);
                writer.newLine(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void removeEmptyLines(String[] csvPaths) {
        for (String csvPath : csvPaths) {
            removeEmptyLines(csvPath);
        }
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.isEmpty() || password == "\n") {
            System.out.println("Error: Password cannot be empty.");
            return false;
        }
        if (password.length() < 8) {
            System.out.println("Error: Password must be at least 8 characters long.");
            return false;
        }
        // Additional validation criteria can be added here if needed
        return true;
    }

    public static boolean changePasswordProcedure(User user) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter current password: ");
        String currentPassword=Utility.inputNonEmptyString(sc);
        if(currentPassword.equals(user.getPassword())) {

            System.out.print("Enter new password: ");
            String newPassword=Utility.inputNonEmptyString(sc);
            if (Utility.validatePassword(newPassword)) 
            {
                user.setPassword(newPassword);
                System.out.print("Updating password and logging out \n");
                return true;
            }
            else
            {
                return false;
            } 
        }
        else {
            System.out.println("Incorrect password.");
            return false;
        }
    }
}
