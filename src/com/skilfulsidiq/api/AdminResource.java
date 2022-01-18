package com.skilfulsidiq.api;

import java.util.*;

import com.skilfulsidiq.model.Customer;
import com.skilfulsidiq.model.IRoom;
import com.skilfulsidiq.service.CustomerService;
import com.skilfulsidiq.service.ReservationService;

public class AdminResource {
    private static final AdminResource SINGLETON = new AdminResource();
    private final CustomerService customerService = CustomerService.getSingleton();
     private final  ReservationService reservationService = ReservationService.getSingleton();

    public static  AdminResource getSingleton(){
        return SINGLETON;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }
    public void addRoom(List<IRoom> rooms){
        for(IRoom room: rooms){
            reservationService.addRoom(room);
        }
    }
    public Collection<IRoom> getAllRooms(){
        return reservationService.allRooms();
    }
    public Collection<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }
    public void displayAllReservations(){
        reservationService.printAllReservation();
    }
}
