package com.simanta.restaurant_backend.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.simanta.restaurant_backend.securtity.JwtFilter;
import com.simanta.restaurant_backend.service.Custom_UserDetails_Service;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtFilter jwtfilter;
    private final Custom_UserDetails_Service custom_UserDetails_Service;

    public SecurityConfiguration(JwtFilter jwtfilter,Custom_UserDetails_Service custom_UserDetails_Service) {
        this.jwtfilter = jwtfilter;
        this.custom_UserDetails_Service = custom_UserDetails_Service;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(custom_UserDetails_Service);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.cors(cors -> cors.configurationSource(request -> {
            var config = new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedOrigins(java.util.List.of("https://incomparable-beijinho-e46688.netlify.app","http://localhost:5500","http://127.0.0.1:5500"));
            config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(java.util.List.of("*"));
            config.setAllowCredentials(true);
            return config;
        }));

        
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //                [------------------]
        // Authentication [   PABLIC AUTH    ]
        //                [------------------] 
        .requestMatchers("/images/**").permitAll()
        .requestMatchers(HttpMethod.POST,"/restaurant/api/auth/register").permitAll()
        .requestMatchers(HttpMethod.POST,"/restaurant/api/auth/resend-verification-Link").permitAll()
        .requestMatchers(HttpMethod.GET,"/restaurant/api/auth/verify-email").permitAll()
        .requestMatchers(HttpMethod.POST,"/restaurant/api/auth/login").permitAll()
        .requestMatchers(HttpMethod.POST,"/restaurant/api/auth/forgot-password").permitAll()
        .requestMatchers(HttpMethod.POST,"/restaurant/api/auth/verify-otp").permitAll()
        .requestMatchers(HttpMethod.POST,"/restaurant/api/auth/reset-password").permitAll()
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
   
        

        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //             [------------------]
        // ADMIN PANEL [     CATEGORY     ]
        //             [------------------]
        .requestMatchers(HttpMethod.POST,"/restaurant/admin/api/auth/create-category").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/api/auth/get-all-Available-category").hasRole("ADMIN") 
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/api/auth/get-all-category").hasRole("ADMIN") 
        .requestMatchers(HttpMethod.PATCH,"/restaurant/admin/api/auth/delete-category/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT,"/restaurant/admin/api/auth/update-category/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/api/auth/check-auth").hasRole("ADMIN")
        //             [------------------]
        //  USER PANEL [     CATEGORY     ]
        //             [------------------]
        .requestMatchers(HttpMethod.GET,"/restaurant/user/api/auth/get-all-category").hasRole("USER") 
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->





        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //             [------------------]
        // ADMIN PANEL [       ORDER      ]
        //             [------------------]
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/orders").hasRole("ADMIN")
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->

        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //             [------------------]
        // ADMIN PANEL [     CUSTOMER     ]
        //             [------------------]
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/customer").hasRole("ADMIN")
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->

        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //             [------------------]
        // ADMIN PANEL [      SETTING     ]
        //             [------------------]
        .requestMatchers(HttpMethod.POST,"/restaurant/admin/setting").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/getDetailsOfsetting").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT,"/restaurant/admin/getDetailsOfsetting/AdminAndUser/management").hasRole("ADMIN")
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->

        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //             [------------------]
        // ADMIN PANEL [   ADMIN PROFILE  ]
        //             [------------------]
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/AdminProfile/viewAdminDetails/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT,"/restaurant/admin/AdminProfile/ChangeAdminPassword/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT,"/restaurant/admin/AdminProfile/updateAdminProfile/**").hasRole("ADMIN")
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->








 
 
         

        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //             [------------------]
        // USER PANEL  [  USER HOME PAGE  ]
        //             [------------------]
        .requestMatchers(HttpMethod.GET,"/restaurant/user/homepage/orderViewPanel").hasRole("USER")
        .requestMatchers(HttpMethod.GET,"/restaurant/user/homepage/currentOrder/**").hasRole("USER")
        .requestMatchers(HttpMethod.GET,"/restaurant/user/homepage/userProfile/**").hasRole("USER")
        .requestMatchers(HttpMethod.PUT,"/restaurant/user/homepage/updateUserProfile/**").hasRole("USER")
        .requestMatchers(HttpMethod.PUT,"/restaurant/user/homepage/userChangePassword/**").hasRole("USER")
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->



        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //             [------------------]
        // ADMIN PANEL [       MENU       ]
        //             [------------------]
        .requestMatchers(HttpMethod.POST,"/restaurant/admin/api/create-menu").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/api/get-all-menu").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/api/get-all-Available-menu").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT,"/restaurant/admin/api/update-menu/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PATCH,"/restaurant/admin/api/delete-menu/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/restaurant/admin/api/check-menu").hasRole("ADMIN")
        //             [------------------]
        // USER PANEL  [       MENU       ]
        //             [------------------]
        .requestMatchers(HttpMethod.GET,"/restaurant/user/api/auth/get-all-Menu-By-Category/**").hasRole("USER")
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->



        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //                  [------------------]
        // USER CART PANEL  [       CART       ]
        //                  [------------------]
        .requestMatchers(HttpMethod.POST,"/restaurant/user/api/cart/add/**").authenticated()
        .requestMatchers(HttpMethod.PUT,"/restaurant/user/api/cart/update/**").authenticated()
        .requestMatchers(HttpMethod.GET,"/restaurant/user/api/cart/view/**").authenticated()
        .requestMatchers(HttpMethod.DELETE,"/restaurant/user/api/cart/delete/*/*").authenticated()
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->



        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //                     [------------------]
        // USER ADDRESS PANEL  [     ADDRESS      ]
        //                     [------------------]
        .requestMatchers(HttpMethod.POST,  "/restaurant/user/api/address/add/**").authenticated()
        .requestMatchers(HttpMethod.PUT,   "/restaurant/user/api/address/update/*/*").authenticated()
        .requestMatchers(HttpMethod.PUT,   "/restaurant/user/api/address/setIsDefault/*/*").authenticated()
        .requestMatchers(HttpMethod.GET,   "/restaurant/user/api/address/view/**").authenticated() // --> Unused Api
        .requestMatchers(HttpMethod.DELETE,"/restaurant/user/api/address/delete/*/*").authenticated() // --> Unused Api
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->

 

        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //                              [------------------]
        // USER CHECKOUT SUMMERY PANEL  [ CHECKOUT SUMMERY ]
        //                              [------------------]
        .requestMatchers(HttpMethod.GET, "/restaurant/user/api/checkout-summary/view/*").authenticated()
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->


 
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //                              [------------------]
        //      ADMIN ORDER PANEL       [       ORDER      ]
        //                              [------------------]
        .requestMatchers(HttpMethod.PATCH,"/restaurant/user/api/order/admin/updateOrder-status/*").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PATCH,"/restaurant/user/api/order/admin/updatepayment-status/*").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET,"/restaurant/user/api/order/admin/orderstatuspaymentstatus/*").hasRole("ADMIN")
        //                              [------------------]
        //      USER ORDER PANEL        [       ORDER      ]
        //                              [------------------]
        .requestMatchers(HttpMethod.POST,"/restaurant/user/api/order/placed-order/**").authenticated()
        .requestMatchers(HttpMethod.GET, "/restaurant/user/api/order/get-order-by-user/**").authenticated()
        .requestMatchers(HttpMethod.GET, "/restaurant/user/api/order/get-order-by-id/*/*").authenticated()
        .requestMatchers(HttpMethod.PATCH,"/restaurant/user/api/order/cancel-order/*/*").authenticated()
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->



        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
        //                       [------------------]
        //      PAYMENT USER     [      PAYMENT     ]
        //                       [------------------]
        .requestMatchers(HttpMethod.POST,"/restaurant/user/api/payment/create-payment/*/*").authenticated()
        .requestMatchers(HttpMethod.POST,"/restaurant/user/api/payment/verify-payment/**").authenticated()
        // <-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=->
       

        .anyRequest().authenticated()
            );
        http.addFilterBefore(jwtfilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();        
    }
}
