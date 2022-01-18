package com.skilfulsidiq.service;

import com.skilfulsidiq.model.Customer;
import com.skilfulsidiq.model.IRoom;
import com.skilfulsidiq.model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    private static final int ROOMS_DEFAULT_DAYS = 7;
    private static final ReservationService SINGLETON = new ReservationService();
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    public static ReservationService getSingleton() {
        return SINGLETON;
    }

    public void addRoom(IRoom room){
        rooms.put(room.getRoomNumber(),room);
    }
    public IRoom getARoom(String roomId){
        return rooms.get(roomId);
    }
    public Collection<IRoom> findARooms(Date checkInDate, Date checkOutDate){
        return findAvailableRooms(checkInDate, checkOutDate);
    }
    private Collection<IRoom> findAvailableRooms(final Date checkInDate, final Date checkOutDate) {
        Collection<Reservation> allReservation = showAllReservation();
        Collection<IRoom> bookedRooms = new ArrayList<>();
        for(Reservation reservation: allReservation){
            if(checkOutDate.after(reservation.getCheckInDate()) && checkInDate.before(reservation.getCheckOutDate())){
                bookedRooms.add(reservation.getRoom());
            }
            
           
        }
        return rooms.values().stream().filter(room -> bookedRooms.stream()
                                           .noneMatch( booked ->bookedRooms.equals(room)))
                                           .collect(Collectors.toList());
    }

    public Reservation reserveARoom(Customer customer,IRoom room,Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer,room,checkInDate,checkOutDate);
        Collection<Reservation> customerReservation = getCustomerReservation(customer);
        if(customerReservation==null){
            customerReservation = new ArrayList<>();
        }
        customerReservation.add(reservation);
        reservations.put(customer.getEmail(),customerReservation);
        return  reservation;
    }
    private boolean isRoomOpenForReserved(IRoom room, Date checkInDate, Date checkOutDate){
        if (reservations.isEmpty()) return false; //if no reservation has been made, then all rooms are free
        for (Collection<Reservation> reservations : reservations.values()) {
            // System.out.println("Number of reservations " + reservations.size() + " ");
            for (Reservation reservation : reservations){
                IRoom reservedRoom = reservation.getRoom();
                if (reservedRoom.getRoomNumber().equals(room.getRoomNumber())){
                    // System.out.println("Room is reserved..checking if room will be free by date...");
                    // if the room has been reserved but the new date is not within the reserved room's date, then it is free.
                    if (isDateWithinRange(checkInDate, checkOutDate,  reservation)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean isDateWithinRange(Date checkInDate,Date checkOutDate,Reservation reservation){
        return !(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate()));
    }
    public Collection<Reservation> getCustomerReservation(Customer customer){
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation(){
        Collection<Reservation> allReservations = showAllReservation();
        if(allReservations.isEmpty()){
            System.out.println("No Reservation found");
        }else {
     
            for (Reservation r : allReservations) {
                System.out.println(r +"\n");
            }
        }
    }

    public Collection<IRoom> allRooms(){
        return rooms.values();
    }

    public Collection<Reservation> showAllReservation(){
        Collection<Reservation> allReservation = new ArrayList<>();
        for(Collection<Reservation> reservation: reservations.values()){
            allReservation.addAll(reservation);
        }
        return  allReservation;
    }
}
