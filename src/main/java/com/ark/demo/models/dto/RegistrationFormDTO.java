package com.ark.demo.models.dto;

import com.ark.demo.models.customConstraints.ValidPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationFormDTO {
    @NotNull
    @NotBlank
    @Size(min=3,max=20,message="Invalid Username.  Username must be between 3 and 20 characters")
    private String username;

    @NotNull
    @NotBlank
    @ValidPassword(message= """
            Invalid Password.  Password must:
            \tBe at least 8 characters in length.
            \tContain at least 1 UPPERCASE character.
            \tContain at least 1 LOWERCASE character.
            \tContain at least 1 NUMBER character.
            \tContain at least 1 SPECIAL character.
            \tNot Contain a space.""")
    private String password;

    private String verifyPassword;

    private String icon = "basic_guy";
    @NotNull
    @NotBlank
    @Size(min=1,max=20,message="Invalid First Name.  First Name must be between 1 and 20 characters.")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min=1,max=20,message="Invalid Last Name.  Last Name must be between 1 and 20 characters.")
    private String lastName;

    @NotNull
    @NotBlank(message = "Address is a required field.")
    private String addressLine1;

    private String addressLine2;

    @NotNull
    @NotBlank(message = "City is a required field.")
    private String city;

    @NotNull
    @NotBlank(message = "State is a required field")
    private String state;

    @NotNull
    @NotBlank(message = "Zipcode is a required field.")
    @Pattern(regexp = "[0-9]{5}",message = "Zipcode must contain 5 number characters.")
    private String zipcode;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[a-z0-9\\.]+@[a-z]+\\.[a-z]{2,3}",message = "Invalid Email.")
    private String emailAddress;

    @Pattern(regexp = "[0-9]{3}+\\-[0-9]{3}+\\-[0-9]{4}",message = "Invalid phone number. Use format 800-123-4567")
    private String phoneNumber;

    @NotNull(message = "You have not submitted your address.")
    @NotBlank(message = "You have not submitted your address.")
    private String location;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
