package com.simanta.restaurant_backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.razorpay.RazorpayException;
import com.simanta.restaurant_backend.dto.Razorpay_CreatePayment_response_Controller_DTO;
import com.simanta.restaurant_backend.dto.Razorpay_CreatePayment_response_Service_DTO;
import com.simanta.restaurant_backend.dto.Razorpay_VerifyPaymen_response_Controller_DTO;
import com.simanta.restaurant_backend.dto.Razorpay_VerifyPaymen_response_Service_DTO;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.service.PaymentService_USER;

import jakarta.mail.MessagingException;


@RestController
@RequestMapping("/restaurant/user/api/payment")
public class PaymentController_USER {

    @Value("${razorpay.key_id}")
    private String keyId;

    private final PaymentService_USER paymentService_USER;
    private final AuthRepository authRepository;

    public PaymentController_USER(PaymentService_USER paymentService_USER,AuthRepository authRepository) {
        this.paymentService_USER = paymentService_USER;
        this.authRepository = authRepository;
    }

    // Create Payment
    @PostMapping("/create-payment/{userid}/{orderid}")
    public ResponseEntity<Razorpay_CreatePayment_response_Controller_DTO> create_payment(@PathVariable final Long userid,@PathVariable final Long orderid,
        Authentication authentication) throws RazorpayException{

            final String email = authentication.getName();

            final User loggedInUser = authRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

            if(!loggedInUser.getId().equals(userid)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
            }

            Razorpay_CreatePayment_response_Service_DTO service_response = paymentService_USER.create_payment(userid,orderid);

            Razorpay_CreatePayment_response_Controller_DTO controller_response = new Razorpay_CreatePayment_response_Controller_DTO
            (Math.round(service_response.getTotalamount() * 100) , service_response.getRazorpayOrderId(),keyId);

        return ResponseEntity.status(HttpStatus.OK).body(controller_response);
    }


    // Verify payment 
    @PostMapping("/verify-payment/{userid}")
    public ResponseEntity<Razorpay_VerifyPaymen_response_Service_DTO> verify_paymeny(@PathVariable final Long userid,Authentication authentication,
        @RequestBody Razorpay_VerifyPaymen_response_Controller_DTO razorpay_VerifyPaymen_response_Controller_DTO) throws RazorpayException, MessagingException{

        final String email = authentication.getName();

        final User loggedInUer = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUer.getId().equals(userid)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        Razorpay_VerifyPaymen_response_Service_DTO verify_payment_response = paymentService_USER.verify_payment(
            razorpay_VerifyPaymen_response_Controller_DTO.getRazorpayOrderId(), 
        razorpay_VerifyPaymen_response_Controller_DTO.getRazorpayPaymentId(),razorpay_VerifyPaymen_response_Controller_DTO.getRazorpaySignature());

        return ResponseEntity.status(HttpStatus.OK).body(verify_payment_response);
    }

}


