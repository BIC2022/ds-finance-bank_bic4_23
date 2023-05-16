package net.froihofer.util.jboss.persistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ADRESSES")
public class Address implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String street;
    @Column(name="HOUSE_NR")
    private String houseNo;
    private String place;
    @Column(name="ZIP_CODE")
    private Integer zipCode;
    @ManyToOne
    @JoinColumn(name="USER_FK")
    private User user;

    public Address() {}
    public Address(Integer id, String street, String houseNo, String place, Integer zipCode) {
        this.id = id;
        this.street = street;
        this.houseNo = houseNo;
        this.place = place;
        this.zipCode = zipCode;
    }

    public Address(String street, String houseNo, String place, Integer zipCode) {
        this.street = street;
        this.houseNo = houseNo;
        this.place = place;
        this.zipCode = zipCode;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + id);
        builder.append(" Adress: " + street + " " + houseNo + " " + place + " " + zipCode);
        return builder.toString();
    }
}
