package com.skilfulsidiq.api;

import com.skilfulsidiq.model.Customer;
import com.skilfulsidiq.model.IRoom;
import com.skilfulsidiq.model.Reservation;
import com.skilfulsidiq.service.CustomerService;
import com.skilfulsidiq.service.ReservationService;

import java.util.*;

public class HotelResource {
    private static final HotelResource SINGLETON = new HotelResource();
     private final CustomerService customerService = CustomerService.getSingleton();
     private final  ReservationService reservationService = ReservationService.getSingleton();

     public static HotelResource getSingleton(){
         return SINGLETON;
     }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(firstName,lastName,email);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checlInDate, Date checkOutDate){
        Customer newCustomer = customerService.getCustomer(customerEmail);
     return reservationService.reserveARoom(newCustomer,room,checlInDate,checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        Customer customer = customerService.getCustomer(customerEmail);
        if(customer != null){
            return reservationService.getCustomerReservation(customer);
        }
        return null;
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
       return reservationService.findARooms(checkIn, checkOut);
    }

    public Collection<IRoom> findRecommendedRooms(final Date checkIn, final Date checkOut) {
        return reservationService.displayRecommendedRoom(checkIn, checkOut);
    }
    public Date addMoreDays(Date date){
        return reservationService.addDaysToDate(date);
    }
}
