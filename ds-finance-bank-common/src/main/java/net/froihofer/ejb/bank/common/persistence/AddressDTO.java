package net.froihofer.ejb.bank.common.persistence;

import java.io.Serializable;

public class AddressDTO implements Serializable {
    private Integer id;
    private String street;
    private String houseNo;
    private String place;
    private Integer zipCode;
    private Integer user;

    public AddressDTO() {

    }

    public AddressDTO(Integer id, String street, String houseNo, String place, Integer zipCode) {
        this.id = id;
        this.street = street;
        this.houseNo = houseNo;
        this.place = place;
        this.zipCode = zipCode;
    }

    public AddressDTO(Integer id, String street, String houseNo, String place, Integer zipCode, Integer user) {
        this.id = id;
        this.street = street;
        this.houseNo = houseNo;
        this.place = place;
        this.zipCode = zipCode;
        this.user = user;
    }

    public AddressDTO(String street, String houseNo, String place, Integer zipCode, Integer user) {
        this.street = street;
        this.houseNo = houseNo;
        this.place = place;
        this.zipCode = zipCode;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + id);
        builder.append(" Adress: " + street + " " + houseNo + " " + place + " " + zipCode);
        builder.append("\nUser ID: " + user);
        return builder.toString();
    }
}
