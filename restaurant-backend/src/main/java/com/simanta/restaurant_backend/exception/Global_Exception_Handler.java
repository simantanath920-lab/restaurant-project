package com.simanta.restaurant_backend.exception;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Global_Exception_Handler {

    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>>handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

	    Map<String,String> errors = new HashMap<>();

	    exception.getBindingResult().getFieldErrors()
	            .forEach(error -> {
	                errors.put(error.getField(),error.getDefaultMessage());
	            });
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

    // EmailExists Exception
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Response_Exception_DTO> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(),LocalDateTime.now(),HttpStatus.CONFLICT.value());

        return new ResponseEntity<> (exception_DTO,HttpStatus.CONFLICT);
    }

    // InvalidDataException
    @ExceptionHandler(InvalidDataException.class) 
    public ResponseEntity<Response_Exception_DTO> handleInvalidDataException(InvalidDataException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // EmailSendingFailedException
    @ExceptionHandler(EmailSendingFailedException.class)
    public ResponseEntity<Response_Exception_DTO> handleEmailSendingFailedException(EmailSendingFailedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.SERVICE_UNAVAILABLE.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.SERVICE_UNAVAILABLE);
    }

    // RegistrationFailedException
    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<Response_Exception_DTO> handleRegistrationFailedException(RegistrationFailedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // EmailDoesNotExistException
    @ExceptionHandler(EmailDoesNotExistException.class)
    public ResponseEntity<Response_Exception_DTO> handleEmailDoesNotExistException(EmailDoesNotExistException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // UserAlreadyVerifiedException
    @ExceptionHandler(UserAlreadyVerifiedException.class)
    public ResponseEntity<Response_Exception_DTO> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // ResendVerificationLinkFailedException
    @ExceptionHandler(ResendVerificationLinkFailedException.class)
    public ResponseEntity<Response_Exception_DTO> handleResendVerificationLinkFailedException(ResendVerificationLinkFailedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // TokenNotFoundException
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Response_Exception_DTO> handleTokenNotFoundException(TokenNotFoundException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // EmailAlreadyVerified
    @ExceptionHandler(EmailAlreadyVerified.class)
    public ResponseEntity<Response_Exception_DTO> handleEmailAlreadyVerified(EmailAlreadyVerified exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // EmailVerifiedFailedException
    @ExceptionHandler(EmailVerifiedFailedException.class)
    public ResponseEntity<Response_Exception_DTO> handleEmailVerifiedFailedException(EmailVerifiedFailedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // TokenAlreadyExpired
    @ExceptionHandler(TokenAlreadyExpired.class)
    public ResponseEntity<Response_Exception_DTO> handleTokenAlreadyExpired(TokenAlreadyExpired exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response_Exception_DTO> handleUserNotFoundException(UserNotFoundException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // EmailNotVerifiedException
    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<Response_Exception_DTO> handleEmailNotVerifiedException(EmailNotVerifiedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }


    // BadCredentialsException / UsernameNotFoundException
   @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleAuthException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // InvalidEmailOrPassword
    @ExceptionHandler(InvalidEmailOrPassword.class)
    public ResponseEntity<Response_Exception_DTO> handleInvalidEmailOrPassword(InvalidEmailOrPassword exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // ForgotPasswordFailedException
    @ExceptionHandler(ForgotPasswordFailedException.class)
    public ResponseEntity<Response_Exception_DTO> handleForgotPasswordFailedException(ForgotPasswordFailedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // InvalidOtpException
    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<Response_Exception_DTO> handleInvalidOtpException(InvalidOtpException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }


    // OtpExpiredException
    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<Response_Exception_DTO> handleOtpExpiredException(OtpExpiredException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // InvalidResetTokenException
    @ExceptionHandler(InvalidResetTokenException.class)
    public ResponseEntity<Response_Exception_DTO> handleInvalidResetTokenException(InvalidResetTokenException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // CategoryNameAlreadyExists
    @ExceptionHandler(CategoryNameAlreadyExists.class)
    public ResponseEntity<Response_Exception_DTO> handleCategoryNameAlreadyExists(CategoryNameAlreadyExists exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(),LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }
    
    // CategoryCreationFailedException
    @ExceptionHandler(CategoryCreationFailedException.class)
    public ResponseEntity<Response_Exception_DTO> handleCategoryCreationFailedException(CategoryCreationFailedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(),LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // CategoryFetchFailedException
    @ExceptionHandler(CategoryFetchFailedException.class)
    public ResponseEntity<Response_Exception_DTO> handleCategoryFetchFailedException(CategoryFetchFailedException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(),LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // CategoryIdNotFoundException
    @ExceptionHandler(CategoryIdNotFoundException.class)
    public ResponseEntity<Response_Exception_DTO> handleCategoryIdNotFoundException(CategoryIdNotFoundException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(),LocalDateTime.now(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<> (exception_DTO,HttpStatus.BAD_REQUEST);
    }
 
    // HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNumber(HttpMessageNotReadableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid input. Please check your values: price must be a number, availability must be Available or Not Available, and IDs must be correct");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // NameAlreadyExistException
    @ExceptionHandler(NameAlreadyExistException.class)
    public ResponseEntity<Response_Exception_DTO> handleNameAlreadyExistException(NameAlreadyExistException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }
 
    // MenuIdNotFoundException
    @ExceptionHandler(MenuIdNotFoundException.class)
    public ResponseEntity<Response_Exception_DTO> handleMenuIdNotFoundException(MenuIdNotFoundException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // Address Exception
    @ExceptionHandler(AddressException.class)
    public ResponseEntity<Response_Exception_DTO> hadleAddressException(AddressException exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // ADMIN_Customer_Setting_AdminAndUser_Management
    @ExceptionHandler(ADMIN_Customer_Setting_AdminAndUser_Management.class)
    public ResponseEntity<Response_Exception_DTO> handleADMIN_Customer_Setting_AdminAndUser_Management(ADMIN_Customer_Setting_AdminAndUser_Management exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // ADMIN_Customer_Setting
    @ExceptionHandler(ADMIN_Customer_Setting.class)
    public ResponseEntity<Response_Exception_DTO> handleADMIN_ADMIN_Customer_Setting(ADMIN_Customer_Setting exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // ADMIN_Profile_changePassword_Exception
    @ExceptionHandler(DMIN_Profile_changePassword_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handleADMIN_ADMIN_Profile_changePassword_Exception(DMIN_Profile_changePassword_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // ADMIN_Profile_Service_Exception
    @ExceptionHandler(ADMIN_Profile_Service_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_ADMIN_Profile_Service_Exception(ADMIN_Profile_Service_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // ADMIN_Update_Profile_Service_Exception
    @ExceptionHandler(ADMIN_Update_Profile_Service_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_ADMIN_Update_Profile_Service_Exception(ADMIN_Update_Profile_Service_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // CartService_USER_Exception
    @ExceptionHandler(CartService_USER_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_CartService_USER_Exception(CartService_USER_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // CategoryService_ADMIN_Exception
    @ExceptionHandler(CategoryService_ADMIN_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_CategoryService_ADMIN_Exception(CategoryService_ADMIN_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // Checkout_Summery_Exception
    @ExceptionHandler(Checkout_Summery_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_Checkout_Summery_Exception(Checkout_Summery_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // FileUploadService_Exception
    @ExceptionHandler(FileUploadService_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_FileUploadService_Exception(FileUploadService_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // MenuService_ADMIN_Exception
    @ExceptionHandler(MenuService_ADMIN_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_MenuService_ADMIN_Exception(MenuService_ADMIN_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // OrderService_ADMIN_Exception
    @ExceptionHandler(OrderService_ADMIN_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_OrderService_ADMIN_Exception(OrderService_ADMIN_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // OrderService_USER_Exception
    @ExceptionHandler(OrderService_USER_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_OrderService_USER_Exception(OrderService_USER_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // PaymentService_USER_Exception
    @ExceptionHandler(PaymentService_USER_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_PaymentService_USER_Exception(PaymentService_USER_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // USER_OrderPage_Service_Exception
    @ExceptionHandler(USER_OrderPage_Service_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_USER_OrderPage_Service_Exception(USER_OrderPage_Service_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }

    // USER_Profile_MainPage_Service_Exception
    @ExceptionHandler(USER_Profile_MainPage_Service_Exception.class)
    public ResponseEntity<Response_Exception_DTO> handle_USER_Profile_MainPage_Service_Exception(USER_Profile_MainPage_Service_Exception exception){
        Response_Exception_DTO exception_DTO = new Response_Exception_DTO(exception.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception_DTO,HttpStatus.BAD_REQUEST);
    }
}
