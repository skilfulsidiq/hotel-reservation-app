package com.skilfulsidiq.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;
    private static final String emailRegrex =  "^(.+)@(.+)$";

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Customer(String firstName, String lastName, String email) {
        this.validateEmail(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void validateEmail(String email){
        Pattern pattern = Pattern.compile(emailRegrex);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException();
        }
    }
    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Room)) {
            return false;
        }

        final Customer customer = (Customer) obj;
        return Objects.equals(this.email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
