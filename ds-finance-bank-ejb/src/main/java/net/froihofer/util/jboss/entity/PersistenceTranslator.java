package net.froihofer.util.jboss.entity;

import net.froihofer.ejb.bank.common.persistence.ShareDTO;
import net.froihofer.util.jboss.persistence.Address;
import net.froihofer.ejb.bank.common.persistence.AddressDTO;
import net.froihofer.util.jboss.persistence.Share;
import net.froihofer.util.jboss.persistence.User;
import net.froihofer.ejb.bank.common.persistence.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class PersistenceTranslator {
    public User toUser(UserDTO userDTO) {
        if(userDTO == null)
            return null;

        User user;

        if(userDTO.getId() != null)
            user = new User(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName());
        else
            user = new User(userDTO.getFirstName(), userDTO.getLastName());

        List<Address> addressList = new ArrayList<>();

        for(int i = 0; i < userDTO.getAddresses().size(); i++) {
            Address address = toAddress(userDTO.getAddresses().get(i));
            address.setUser(user);
            addressList.add(address);
        }
        user.setAddresses(addressList);

        List<Share> shareList = new ArrayList<>();
        for(int i = 0; i < userDTO.getShares().size(); i++) {
            Share share = toShare(userDTO.getShares().get(i));
            share.setUser(user);
            shareList.add(share);
        }
        user.setShares(shareList);
        return user;
    }

    public UserDTO toUserDTO(User user) {
        if(user == null)
            return null;
        UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName());
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for(int i = 0; i < user.getAddresses().size(); i++) {
            AddressDTO addressDTO = toAddressDTO(user.getAddresses().get(i));
            addressDTO.setUser(userDTO.getId());
            addressDTOList.add(addressDTO);
        }
        userDTO.setAddresses(addressDTOList);

        List<ShareDTO> shareDTOList = new ArrayList<>();
        for(int i = 0; i < user.getShares().size(); i++) {
            ShareDTO shareDTO = toShareDTO(user.getShares().get(i));
            shareDTO.setUser(userDTO.getId());
            shareDTOList.add(shareDTO);
        }
        userDTO.setShares(shareDTOList);
        return userDTO;
    }

    public Address toAddress(AddressDTO addressDTO) {
        if(addressDTO == null)
            return null;

        if(addressDTO.getId() != null)
            return new Address(addressDTO.getId(),addressDTO.getStreet(), addressDTO.getHouseNo(),
                addressDTO.getPlace(), addressDTO.getZipCode());
        else
            return new Address(addressDTO.getStreet(), addressDTO.getHouseNo(),
                    addressDTO.getPlace(), addressDTO.getZipCode());
    }

    public AddressDTO toAddressDTO(Address address) {
        if(address == null)
            return null;

        if(address.getId() != null)
            return new AddressDTO(address.getId(), address.getStreet(),
                    address.getHouseNo(), address.getPlace(), address.getZipCode(), address.getUser().getId());
        else
            return new AddressDTO(address.getId(), address.getStreet(),
                    address.getHouseNo(), address.getPlace(), address.getZipCode(), address.getUser().getId());
    }

    public ShareDTO toShareDTO(Share share) {
        if(share == null)
            return null;

        if(share.getId() != null)
            return new ShareDTO(share.getId(), share.getCompanyName(), share.getBoughtShares(), share.getBuyPrice(),
                    share.getTimeOfPurchase(), share.getSymbol(), share.getUser().getId());
        else
            return new ShareDTO(share.getCompanyName(), share.getBoughtShares(), share.getBuyPrice(),
                    share.getTimeOfPurchase(), share.getSymbol(), share.getUser().getId());
    }

    public Share toShare(ShareDTO shareDTO) {
        if(shareDTO == null)
            return null;

        if(shareDTO.getId() != null)
            return new Share(shareDTO.getId(), shareDTO.getCompanyName(), shareDTO.getBoughtShares(), shareDTO.getBuyPrice(),
                    shareDTO.getTimeOfPurchase(), shareDTO.getSymbol());
        else
            return new Share(shareDTO.getCompanyName(), shareDTO.getBoughtShares(), shareDTO.getBuyPrice(),
                    shareDTO.getTimeOfPurchase(), shareDTO.getSymbol());

    }

    public List<UserDTO> toUserDTOList(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for(int i = 0; i < userList.size(); i++) {
            userDTOList.add(toUserDTO(userList.get(i)));
        }
        return userDTOList;
    }

    public List<AddressDTO> toAddressDTOList(List<Address> addressList) {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for(int i = 0; i < addressList.size(); i++) {
            addressDTOList.add(toAddressDTO(addressList.get(i)));
        }
        return addressDTOList;
    }

    public List<ShareDTO> toShareDTOList(List<Share> shareList) {
        List<ShareDTO> shareDTOList = new ArrayList<>();
        for(int i = 0; i < shareList.size(); i++) {
            shareDTOList.add(toShareDTO(shareList.get(i)));
        }
        return shareDTOList;
    }
}
