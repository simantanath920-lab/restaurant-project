package com.simanta.restaurant_backend.service;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;

@Service
public class Custom_UserDetails_Service implements UserDetailsService {
 
    private final AuthRepository authRepository;

    public Custom_UserDetails_Service(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email)throws UsernameNotFoundException {

        final User user = authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password."));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }
}
