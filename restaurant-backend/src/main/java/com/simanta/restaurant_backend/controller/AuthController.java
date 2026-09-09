package com.simanta.restaurant_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.ForgotPassword_Request_DTO;
import com.simanta.restaurant_backend.dto.ForgotPassword_response_DTO;
import com.simanta.restaurant_backend.dto.Login_request_DTO;
import com.simanta.restaurant_backend.dto.Login_response_DTO;
import com.simanta.restaurant_backend.dto.Register_Response_DTO;
import com.simanta.restaurant_backend.dto.Register_request_DTO;
import com.simanta.restaurant_backend.dto.ResendVerificationLinkToEmail_Response_DTO;
import com.simanta.restaurant_backend.dto.ResetPassword_Request_DTO;
import com.simanta.restaurant_backend.dto.ResetPassword_Response_DTO;
import com.simanta.restaurant_backend.dto.VerifyEmail_Response_DTO;
import com.simanta.restaurant_backend.dto.VerifyOTP_Request_DTO;
import com.simanta.restaurant_backend.dto.VerifyOtp_Here_is_resetToken;
import com.simanta.restaurant_backend.service.AuthService;
 
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant/api/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
 
    // Register
    @PostMapping("/register")
    public ResponseEntity<Register_Response_DTO> register(@Valid @RequestBody Register_request_DTO register_request_DTO){
        Register_Response_DTO register_Response = authService.register(register_request_DTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(register_Response);
    }

    // Resend Verification Link
    @PostMapping("/resend-verification-Link")
    public ResponseEntity<ResendVerificationLinkToEmail_Response_DTO> resendVerificationLink(@RequestParam String email){
        ResendVerificationLinkToEmail_Response_DTO verification_Response = authService.resendVerificationLinkToEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(verification_Response);
    }

    // Verify Email
    @GetMapping("/verify-email")
    public ResponseEntity<VerifyEmail_Response_DTO> verifyEmail(@RequestParam String token){
        VerifyEmail_Response_DTO verify_Email_Response = authService.verifyEmail(token);
        return ResponseEntity.status(HttpStatus.OK).body(verify_Email_Response);
    }
 
    // login
    @PostMapping("/login")
    public ResponseEntity<Login_response_DTO> login(@Valid @RequestBody Login_request_DTO login_request_DTO){
        Login_response_DTO login_response = authService.login(login_request_DTO);
        return ResponseEntity.status(HttpStatus.OK).body(login_response);
    } 

    // Forgot Password
    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPassword_response_DTO> forgotPassword(@Valid @RequestBody ForgotPassword_Request_DTO forgotPassword_Request_DTO){
        ForgotPassword_response_DTO forgot_Password_response = authService.forgotPassword(forgotPassword_Request_DTO);
        return ResponseEntity.status(HttpStatus.OK).body(forgot_Password_response);
    } 

    // Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOtp_Here_is_resetToken> verifyOTP(@Valid @RequestBody VerifyOTP_Request_DTO verifyOTP_Request_DTO){
        VerifyOtp_Here_is_resetToken verifyOTP_response = authService.verifyOtp(verifyOTP_Request_DTO);
        return ResponseEntity.status(HttpStatus.OK).body(verifyOTP_response);
    }

    // Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<ResetPassword_Response_DTO> resetPassword(@Valid @RequestBody ResetPassword_Request_DTO resetPassword_Request_DTO){
        ResetPassword_Response_DTO reset_Password_response = authService.resetPassword(resetPassword_Request_DTO);
        return ResponseEntity.status(HttpStatus.OK).body(reset_Password_response);
    }

}
