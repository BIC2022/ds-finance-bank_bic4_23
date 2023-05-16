package net.froihofer.ejb.bank.common.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {

    @JsonIgnore
    private Integer id;

    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonIgnore
    private List<AddressDTO> addresses;
    @JsonIgnore
    private List<ShareDTO> shares;

    @JsonIgnore
    public void addAddress(AddressDTO address) {
        if (this.addresses == null)
            this.addresses = new ArrayList<>();
        if (!this.addresses.contains(address))
            this.addresses.add(address);
    }

    @JsonIgnore
    public boolean removeAddress(AddressDTO address) {
        return this.addresses.remove(address);
    }

    @JsonIgnore
    public void addShare(ShareDTO share) {
        if(this.shares == null)
            this.shares = new ArrayList<>();
        this.shares.add(share);
    }

    public UserDTO() {
    }

    public UserDTO(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = new ArrayList<>();
        this.shares = new ArrayList<>();
    }

    public UserDTO(String firstName, String lastName) {
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

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public List<ShareDTO> getShares() {
        return shares;
    }

    public void setShares(List<ShareDTO> shares) {
        this.shares = shares;
    }

    @JsonIgnore
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + id);
        builder.append(" Firstname: " + firstName);
        builder.append(" Lastname: " + lastName);
        builder.append("\n");
        for (int i = 0; i < addresses.size(); i++)
            builder.append(" Address: " + addresses.get(i));
        return builder.toString();
    }
}
