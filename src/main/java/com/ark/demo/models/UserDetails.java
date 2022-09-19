package com.ark.demo.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import java.util.List;

import static java.util.Objects.isNull;

@Entity
public class UserDetails  extends AbstractEntity{
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String addressLine1;
    private String addressLine2;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zipcode;
    @NotNull
    private String emailAddress;
    @NotNull
    private String phoneNumber;

    @ElementCollection(targetClass = String.class)
    private List<String> gratitudeCards;

    private Boolean emailVerified;

    private String uid;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToOne(mappedBy = "userDetails")
    private User user;

    public UserDetails(String firstName, String lastName, String addressLine1, String addressLine2, String city, String state, String zipcode, String emailAddress, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.emailVerified = false;
        this.uid = encoder.encode("ARK"+this.getEmailAddress());
    }

    public UserDetails() {
    }

    public boolean isMatchingUid(String uid){
        return encoder.matches(uid,this.uid);
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String emailAddress) {
        if(isNull(emailAddress)){
            this.uid = null;
        } else {
            this.uid = encoder.encode("ARK"+emailAddress);
        }
    }

    public List<String> getGratitudeCards() {
        return gratitudeCards;
    }
    public void addGratitudeCard(String pathToCard){
        this.gratitudeCards.add(pathToCard);
    }
}
