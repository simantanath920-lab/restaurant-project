package com.simanta.restaurant_backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.dto.Address_Add_request_USER_DTO;
import com.simanta.restaurant_backend.dto.Address_Add_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Address_Delete_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Address_Set_IsDefault_response_DTO;
import com.simanta.restaurant_backend.dto.Address_Update_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Address_View_response_USER_DTO;
import com.simanta.restaurant_backend.exception.AddressException;
import com.simanta.restaurant_backend.model.Address;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AddressRepository;
import com.simanta.restaurant_backend.repository.AuthRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AuthRepository authRepository;

    public AddressService(AddressRepository addressRepository,AuthRepository authRepository) {
        this.addressRepository = addressRepository;
        this.authRepository = authRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

     
    // Add Address
    @Transactional
    public Address_Add_response_USER_DTO add_Address(final Long id,final Address_Add_request_USER_DTO address_Add_request_USER_DTO){

        try {

            final User user = authRepository.findById(id)
                .orElseThrow(()-> new AddressException("User not found"));

            final Address address = new Address();

            address.setFullName(address_Add_request_USER_DTO.getFullName());
            address.setPhoneNumber(address_Add_request_USER_DTO.getPhoneNumber());
            address.setStreet(address_Add_request_USER_DTO.getStreet());
            address.setArea(address_Add_request_USER_DTO.getArea());
            address.setCity(address_Add_request_USER_DTO.getCity());
            address.setState(address_Add_request_USER_DTO.getState());
            address.setPincode(address_Add_request_USER_DTO.getPincode());
            address.setCountry(address_Add_request_USER_DTO.getCountry());
            address.setAddressType(address_Add_request_USER_DTO.getAddressType());

            address.setUser(user);

            if(!addressRepository.existsByUserId(id)){
                address.setDefault(true);
            }

            if(address.isDefault()){
                resetDefault(id);
            }

            addressRepository.save(address);

            return new Address_Add_response_USER_DTO("Address added");
            
        } catch (Exception e) {
            throw new AddressException("Unable to add address. Please try again later.");
        }
    }


//___________________________________________________________________
    // HELPER METHOD
    private void resetDefault(Long id){
        List<Address> addresses = addressRepository.findByUserId(id);
        
        for(Address addr : addresses){
            addr.setDefault(false);
        }
        addressRepository.saveAll(addresses);
    }
//___________________________________________________________________


    // Update Address
    @Transactional
    public Address_Update_response_USER_DTO update_Address(final Long Userid,final Long AddressID,final Address_Add_request_USER_DTO address_Add_request_USER_DTO){

        LOGGER.info("");

        try {

            final User user = authRepository.findById(Userid)
                .orElseThrow(()-> new AddressException("User not found"));

            final Address address = addressRepository.findByIdAndUser(AddressID,user)
                .orElseThrow(()-> new AddressException("Address not found"));

                address.setFullName(address_Add_request_USER_DTO.getFullName());
                address.setPhoneNumber(address_Add_request_USER_DTO.getPhoneNumber());
                address.setStreet(address_Add_request_USER_DTO.getStreet());
                address.setArea(address_Add_request_USER_DTO.getArea());
                address.setCity(address_Add_request_USER_DTO.getCity());
                address.setState(address_Add_request_USER_DTO.getState());
                address.setPincode(address_Add_request_USER_DTO.getPincode());
                address.setCountry(address_Add_request_USER_DTO.getCountry());
                address.setAddressType(address_Add_request_USER_DTO.getAddressType());
                address.setUpdatedAt(LocalDateTime.now());

                addressRepository.save(address);
            
            return new Address_Update_response_USER_DTO("Address updated");

        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }


    // Set IsDefault
    @Transactional
    public Address_Set_IsDefault_response_DTO set_Address_IsDefault(final Long userId,final Long addressId){

        final User user = authRepository.findById(userId)
            .orElseThrow(()-> new AddressException("User not found"));

        final Address address = addressRepository.findByIdAndUser(addressId, user)
            .orElseThrow(()-> new AddressException("Address not found"));

        resetDefault(user.getId());

        address.setDefault(true);

        addressRepository.save(address);

        return new Address_Set_IsDefault_response_DTO("Updated to default address");
    }


    // Delete Address
    @Transactional
    public Address_Delete_response_USER_DTO delete_Address(final Long userid,final Long addressid){

        final User user = authRepository.findById(userid)
            .orElseThrow(()-> new AddressException("User not found"));

        final Address address = addressRepository.findByIdAndUser(addressid, user)
            .orElseThrow(()-> new AddressException("Address not found"));

        addressRepository.delete(address);

        if(address.isDefault()){
            List<Address> remainingAddress = addressRepository.findByUserId(userid);

            if(!remainingAddress.isEmpty()){

                Address firstAddress = remainingAddress.get(0);
                firstAddress.setDefault(true);
                addressRepository.save(firstAddress);
            }
        }
        return new Address_Delete_response_USER_DTO("Address deleted");
    }

    
    // View Address
    public List<Address_View_response_USER_DTO> view_Address(final Long userid){

        final User user = authRepository.findById(userid)
            .orElseThrow(()-> new AddressException("User not found"));

        List<Address> findAllAddress = addressRepository.findByUserId(user.getId());

        List<Address_View_response_USER_DTO> StoreAllviewAllAddress = new ArrayList<>();

        for(Address address:findAllAddress){

            StoreAllviewAllAddress.add(new Address_View_response_USER_DTO(address.getId(),address.getFullName(),address.getPhoneNumber(),address.getStreet(),address.getArea(),
                address.getCity(),address.getState(),address.getPincode(),address.getCountry(),address.isDefault(),address.getAddressType()));
        }

        return StoreAllviewAllAddress;
    }
}
