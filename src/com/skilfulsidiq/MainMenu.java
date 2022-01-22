package com.skilfulsidiq;


import java.text.SimpleDateFormat;
import java.util.*;

import com.skilfulsidiq.api.HotelResource;
import com.skilfulsidiq.model.IRoom;
import com.skilfulsidiq.model.Reservation;

public class MainMenu extends Menu {
   
    private static HotelResource hotelResource = HotelResource.getSingleton();
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    public static void runMainMenu() {
        new MainMenu().showMenus();
        Scanner scanner = new Scanner(System.in);
        int x = 1;
        int input;
        do{
            try{
                input = scanner.nextInt();
                switch(input){
                    case 1:
                        //find and resrve room
                        findAndReserveRoom();
                        break;
                    case 2:
                        // see my reservation
                        seeMyReservation();
                        break;
                    case 3:
                        // create account
                        createAccount();
                        break;
                    case 4:
                        //open admin menu
                        AdminMenu.runAdminMenu();
                        break;
                    case 5:
                        //exit
                        x=2;
                        System.out.println("Program exit...");
                       
                        break;
                }

            }catch(Exception e){
               
                // System.out.println(e);
                System.out.println("Empty input received. Exiting program...");
                runMainMenu();

                // x=2;
            }

        }while(x==1);

        
    }
    
    private static Date getDateFormat(String date){
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Error: Invalid date.");
            findAndReserveRoom();
        }
        return null;
    }
    public static void findAndReserveRoom(){
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Check-In Date dd/mm/yyyy example: 14/01/2020");
        Date checkIn = getDateFormat(scanner.nextLine());

        System.out.println("Enter Check-out Date dd/mm/yyyy example: 14/01/2020");
        Date checkOut = getDateFormat(scanner.nextLine());
        if(checkIn != null && checkOut != null){
            Collection<IRoom> openRooms = hotelResource.findARoom(checkIn, checkOut);
            if(openRooms.isEmpty()){
                Collection<IRoom> alternativeRooms = hotelResource.findRecommendedRooms(checkIn, checkOut);

                if (alternativeRooms.isEmpty()) {
                    System.out.println("No rooms found.");
                } else {
                    final Date alternativeCheckIn = hotelResource.addMoreDays(checkIn);
                    final Date alternativeCheckOut = hotelResource.addMoreDays(checkOut);

                    System.out.println("The following are rooms available on recommended date");
                    System.out.println("Check-In Date:" + alternativeCheckIn);
                    System.out.println("Check-Out Date:" + alternativeCheckOut);

                    printAvailableRoom(alternativeRooms);
                    processRoomReservation(scanner, alternativeCheckIn, alternativeCheckOut, alternativeRooms);
                }


                // System.out.println("No available room ");
                // runMainMenu();
            }else{
                printAvailableRoom(openRooms);
                processRoomReservation(scanner, checkIn, checkOut, openRooms);
            }
        }

    }
    private static void processRoomReservation(Scanner scanner, Date checkIn, Date checkOut,Collection<IRoom> rooms){
        System.out.println("Would like to reserve a room? y/n");
        String readyToBook = scanner.nextLine();
        if(yesIsSelected(readyToBook)){
            System.out.println("Do you have an account? y/n");
            String havaAccount = scanner.nextLine();

            if(yesIsSelected(havaAccount)){

                System.out.println("Enter Email format: mail@xyz.com");
                final String customerEmail = scanner.nextLine();
                if(hotelResource.getCustomer(customerEmail) != null){
                    System.out.println("What room number would you like to reserve?");
                    final String roomNumber = scanner.nextLine();
                    if(rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))){
                        IRoom selectedRoom = hotelResource.getRoom(roomNumber);
                        //create reservation
                        Reservation reserveRoom = hotelResource.bookARoom(customerEmail, selectedRoom, checkIn, checkOut);

                        System.out.println("Reservation created successfully!");
                        System.out.println(reserveRoom);
                    }else{
                        System.out.println("Error: room number not available.\nStart reservation again.");
                        processRoomReservation(scanner, checkIn, checkOut, rooms);
                    }
                }else{
                    System.out.println("Cannot find customer with this email .\nPlease  create a new account.");
                    runMainMenu();
                }
            }else{
                System.out.println("Please, create an account.");
                createAccount();
            }

        }else{
            runMainMenu();
        }
    }

  
    private static void printAvailableRoom(Collection<IRoom> rooms){
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }
    

    private static void seeMyReservation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Email format: mail@xyz.com");
        String customerEmail = scanner.nextLine();
        if(customerEmail != null){
            Collection<Reservation> customerReservation = hotelResource.getCustomersReservations(customerEmail);
            if(customerReservation==null || customerReservation.isEmpty()){
                System.out.println("No reservation found");
            }else{
                customerReservation.stream().forEach(reservation -> System.out.println("\n"+ reservation));
            }
        }
        // runMainMenu();
    }
    private static void createAccount(){
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Email format: mail@xyz");
        final String email = scanner.nextLine();

        System.out.println("First Name:");
        final String firstName = scanner.nextLine();

        System.out.println("Last Name:");
        final String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");

            runMainMenu();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAccount();
        }
    }

    @Override
    public  void showMenus() {
        // TODO Auto-generated method stub
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println("--------------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an Account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("--------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }
}
