package org.sc2002.hospital.menu;

import java.util.Scanner;

public class PharmacistMenu extends Menu {
    
    @Override
    public void run() {
        Scanner sc=new Scanner(System.in);
        int choice;
        do {
            System.out.println("Pharmacist Menu");
            System.out.println("0. Log out");
            
            System.out.print("Enter your choice: ");
            choice=sc.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Logging out..."); break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice!=0);
    }
}
