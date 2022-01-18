package com.skilfulsidiq;

import java.util.*;

import com.skilfulsidiq.api.AdminResource;
import com.skilfulsidiq.api.HotelResource;
import com.skilfulsidiq.model.Customer;
import com.skilfulsidiq.model.IRoom;
import com.skilfulsidiq.model.Reservation;
import com.skilfulsidiq.model.Room;
import com.skilfulsidiq.model.RoomType;

public class AdminMenu extends Menu {
    private static HotelResource hotelResource = HotelResource.getSingleton();
    private static AdminResource adminResource = AdminResource.getSingleton();

    public static void runAdminMenu() {
        new AdminMenu().showMenus();
        Scanner scanner = new Scanner(System.in);
        int x = 1;
        int input;

        do{
            try{
                input = scanner.nextInt();
                switch(input){
                    case 1:
                        //see all cutomer
                        seeAllCustomer();
                        break;
                    case 2:
                        // see all room
                        seeAllRooms();
                        break;
                    case 3:
                        // see all reservation
                        seeAllReservations();
                        break;
                    case 4:
                        //add a room
                        addRoom();
                        break;
                    case 5:
                        //back to main menu
                        MainMenu.runMainMenu();
                        break;
                }

            }catch(Exception e){
                // x=2;
                System.out.println("Empty input received. Exiting program...");
                runAdminMenu();
            }

        }while(x==1);
    }

    @Override
    public void showMenus(){
        System.out.println("Admin Menu");
        System.out.println("--------------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservation");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("--------------------------------------------");
        System.out.println("Please select a number for the menu option");

    }

    private static void seeAllCustomer(){
        Collection<Customer> allCustomers = adminResource.getAllCustomer();
        if(!allCustomers.isEmpty()){
            allCustomers.stream().forEach(customer->System.out.println("\n"+ customer));
        }else{
            System.out.println("No Customer found");
        }
    }

    private static void seeAllRooms(){
        Collection<IRoom> aRooms= adminResource.getAllRooms();
        if(!aRooms.isEmpty()){
            aRooms.stream().forEach(room->System.out.println("\n"+ room));
        }else{
            System.out.println("No Room found");
        }
    }
    private static void seeAllReservations(){
        adminResource.displayAllReservations();
        
    }
    private static void addRoom(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter room number:-");
        final String roomNumber = scanner.nextLine();

        System.out.println("Enter price per night:-");
        final double price = roomPriceInput(scanner);

        System.out.println("Enter room type: 1 for single bed, 2 for double bed:-");
        final RoomType roomType = roomTypeInput(scanner);

        Room newRoom = new Room(roomNumber, price, roomType);
        adminResource.addRoom(Collections.singletonList(newRoom));
        System.out.println("Room added successfully!");

        System.out.println("Would like to add another room? Y/N");
        addAnotherRoom();
    }

    private static double roomPriceInput(final Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException exp) {
            System.out.println("Invalid room price! Please, enter a valid double number. " +
                    "Decimals should be separated by point (.)");
            return roomPriceInput(scanner);
        }
    }

    private static RoomType roomTypeInput(final Scanner scanner) {
        try {
            return RoomType.valueOfLabel(scanner.nextLine());
        } catch (IllegalArgumentException exp) {
            System.out.println("Invalid room type! Please, choose 1 for single bed or 2 for double bed:");
            return roomTypeInput(scanner);
        }
    }
    private static void addAnotherRoom(){
        final Scanner scanner = new Scanner(System.in);

        try {
            String newRoom = scanner.nextLine();

            if (yesIsSelected(newRoom)){
                // newRoom = scanner.nextLine();
                addRoom();
            } else{
                runAdminMenu();
            }
            
        } catch (StringIndexOutOfBoundsException ex) {
            addAnotherRoom();
        }
    }
}
