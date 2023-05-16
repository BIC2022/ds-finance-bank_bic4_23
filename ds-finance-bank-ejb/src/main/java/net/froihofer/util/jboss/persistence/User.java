package net.froihofer.util.jboss.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST)
    private List<Address> addresses;

    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST)
    private List<Share> shares;

    public void addAddress(Address address) {
        if(this.addresses == null)
            this.addresses = new ArrayList<>();
        if (!this.addresses.contains(address))
            this.addresses.add(address);
    }

    public boolean removeAddress(Address address) {
        return this.addresses.remove(address);
    }

    public void addShare(Share share) {
        if(this.shares == null)
            this.shares = new ArrayList<>();
        this.shares.add(share);
    }
    public User() {
    }

    public User(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = new ArrayList<>();
        this.shares = new ArrayList<>();
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = new ArrayList<>();
        this.shares = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + id);
        builder.append("\nFirstname: " + firstName);
        builder.append("\nLastname: " + lastName);
        for (int i = 0; i < addresses.size(); i++)
            builder.append("\nAddress: " + addresses.get(i));
        for (int i = 0; i < shares.size(); i++)
            builder.append("\nShare: " + shares.get(i));
        return builder.toString();
    }
}