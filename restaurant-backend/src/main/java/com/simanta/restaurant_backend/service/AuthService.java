package com.simanta.restaurant_backend.service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.simanta.restaurant_backend.exception.AuthServiceException;
import com.simanta.restaurant_backend.exception.EmailAlreadyExistsException;
import com.simanta.restaurant_backend.exception.EmailAlreadyVerified;
import com.simanta.restaurant_backend.exception.EmailDoesNotExistException;
import com.simanta.restaurant_backend.exception.EmailNotVerifiedException;
import com.simanta.restaurant_backend.exception.EmailSendingFailedException;
import com.simanta.restaurant_backend.exception.InvalidEmailOrPassword;
import com.simanta.restaurant_backend.exception.InvalidOtpException;
import com.simanta.restaurant_backend.exception.InvalidResetTokenException;
import com.simanta.restaurant_backend.exception.OtpExpiredException;
import com.simanta.restaurant_backend.exception.RegistrationFailedException;
import com.simanta.restaurant_backend.exception.TokenAlreadyExpired;
import com.simanta.restaurant_backend.exception.TokenNotFoundException;
import com.simanta.restaurant_backend.exception.UserAlreadyVerifiedException;
import com.simanta.restaurant_backend.exception.UserNotFoundException;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.securtity.JwtToken;

import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailVerification_Send_Service verificationSend_Service;
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final ForgotPassword_Otp_Service forgotPassword_Otp_Service;
 
    public AuthService(AuthRepository authRepository,BCryptPasswordEncoder passwordEncoder,EmailVerification_Send_Service verificationSend_Service,
        AuthenticationManager authenticationManager,JwtToken jwtToken,ForgotPassword_Otp_Service forgotPassword_Otp_Service) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationSend_Service = verificationSend_Service;
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
        this.forgotPassword_Otp_Service = forgotPassword_Otp_Service;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    // Register
    @Transactional
    public Register_Response_DTO register(final Register_request_DTO register_request_DTO) {

        final String normalizeEmail = register_request_DTO.getEmail().trim().toLowerCase();

        if (authRepository.existsByEmailIgnoreCase(normalizeEmail)) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        final User user = new User();

        user.setName(register_request_DTO.getName().trim());
        user.setEmail(normalizeEmail);
        user.setPhonenumber(register_request_DTO.getPhonenumber().trim());
        user.setAddress(register_request_DTO.getAddress().trim());

        final String hashPassword = passwordEncoder.encode(register_request_DTO.getPassword());

        user.setPassword(hashPassword);
        user.setRole("USER");
        user.setIsActive(true);
        user.setEmailverified(false);

        final String verificationToken = UUID.randomUUID().toString();

        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpire(LocalDateTime.now().plusMinutes(30));

        try {

            final User savedUser = authRepository.save(user);

            verificationSend_Service.emailVerificationLink(savedUser.getEmail(),verificationToken);

            return new Register_Response_DTO("Registration successful. Verify your email.",LocalDateTime.now(),201);

        } catch (EmailSendingFailedException e) {
            LOGGER.error("Email sending failed for email: {}",normalizeEmail,e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error during registration for email: {}",normalizeEmail,e);
            throw new RegistrationFailedException("Registration failed. Please try again later.");
        }
    }


    //Recend Verification Link
    @Transactional
    public ResendVerificationLinkToEmail_Response_DTO resendVerificationLinkToEmail(final String email){

        final User user = authRepository.findByEmail(email)
                .orElseThrow(() -> {
                    return new EmailDoesNotExistException("User not found.");
                });

        if (Boolean.TRUE.equals(user.getEmailverified())) {
            throw new UserAlreadyVerifiedException("User already verified.");
        }

        final String resendVerificationToken = UUID.randomUUID().toString();

        user.setVerificationToken(resendVerificationToken);
        user.setVerificationTokenExpire(LocalDateTime.now().plusMinutes(30));

        authRepository.save(user);

        try {

            verificationSend_Service.emailVerificationLink(user.getEmail(),resendVerificationToken);
            return new ResendVerificationLinkToEmail_Response_DTO("A new verification link has been send to your email.");

        } catch (EmailSendingFailedException e) {
            throw e;

        } catch (Exception e) {
            throw new EmailSendingFailedException("Unable to send verification email.");
        }
    }


      
    // Verify Email 
    @Transactional
    public VerifyEmail_Response_DTO verifyEmail(final String token) {

        final User user = authRepository.findByVerificationToken(token)
                .orElseThrow(() -> {
                    return new TokenNotFoundException("Invalid verification token.");
                });

        if (Boolean.TRUE.equals(user.getEmailverified())) {
            throw new EmailAlreadyVerified("Email already verified.");
        }

        if (user.getVerificationTokenExpire() == null || user.getVerificationTokenExpire().isBefore(LocalDateTime.now())) {
            throw new TokenAlreadyExpired("Verification token expired. Please request a new verification email.");
        }
 
        user.setEmailverified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpire(null);

        authRepository.save(user);

        return new VerifyEmail_Response_DTO("Email verified successfully.");
    }

    

    // Login
    public Login_response_DTO login(final Login_request_DTO login_request_DTO) {
 
        try { 
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login_request_DTO.getEmail(),login_request_DTO.getPassword()));

        } catch (BadCredentialsException e) {

            throw new InvalidEmailOrPassword("Invalid email or password.");
        }

        final User user = authRepository.findByEmail(login_request_DTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password."));

        if (!Boolean.TRUE.equals(user.getEmailverified())) {
            throw new EmailNotVerifiedException("Please verify your email.");
        } 

        final String token = jwtToken.createToken(user.getEmail(),user.getRole());

        return new Login_response_DTO("Login successfully",LocalDateTime.now(),200,token,user.getRole(),user.getId());
      }



     // Forgot Password
        @Transactional
        public ForgotPassword_response_DTO forgotPassword(final ForgotPassword_Request_DTO forgotPassword_Request_DTO) {

            final String normalizedEmail = forgotPassword_Request_DTO.getEmail().trim().toLowerCase();

            final User user = authRepository.findByEmail(normalizedEmail)
                    .orElseThrow(() -> {
                        return new EmailDoesNotExistException("User not found");
                    });

            final SecureRandom secureRandom = new SecureRandom();
            final String otp = String.valueOf(100000 + secureRandom.nextInt(900000));

            user.setOtp(otp);
            user.setOtpExpire(LocalDateTime.now().plusMinutes(5));

            try {

                final User savedUser = authRepository.save(user);

                forgotPassword_Otp_Service.OTPverifyLink(savedUser.getEmail(),savedUser.getOtp());

                return new ForgotPassword_response_DTO("OTP has been sent to your email.");

            } catch (EmailSendingFailedException e) {
                throw e;

            } catch (Exception e) {
                throw new AuthServiceException("Unable to process forgot password request.");
            }
        }


    // Verify OTP
    @Transactional
    public VerifyOtp_Here_is_resetToken verifyOtp(final VerifyOTP_Request_DTO verifyOTP_Request_DTO) {

        final User user = authRepository.findByEmail(verifyOTP_Request_DTO.getEmail())
                .orElseThrow(() -> {
                    return new EmailDoesNotExistException("User not found");
                }); 

        if (user.getOtp() == null || user.getOtpExpire() == null) {
            throw new InvalidOtpException("OTP not found");
        }

        if (!user.getOtp().equals(verifyOTP_Request_DTO.getOtp())) {
            throw new InvalidOtpException("Invalid OTP");
        }

        if (user.getOtpExpire().isBefore(LocalDateTime.now())) {
            throw new OtpExpiredException("OTP expired");
        }

        final String resetToken = UUID.randomUUID().toString();

        user.setResetToken(resetToken);

        authRepository.save(user);

        return new VerifyOtp_Here_is_resetToken(resetToken);
    }


    // Reset Password
    @Transactional
    public ResetPassword_Response_DTO resetPassword(final ResetPassword_Request_DTO resetPassword_Request_DTO) {

        final User user = authRepository.findByResetToken(resetPassword_Request_DTO.getResetToken())
                .orElseThrow(() -> {
                    return new InvalidResetTokenException("Unable to reset password. Please request a new password reset link.");
                });

        user.setPassword(passwordEncoder.encode(resetPassword_Request_DTO.getNewPassword()));

        user.setOtp(null);
        user.setOtpExpire(null);
        user.setResetToken(null);

        authRepository.save(user);

        return new ResetPassword_Response_DTO("Password reset successfully");
    }


}
