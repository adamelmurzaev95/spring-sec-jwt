package com.adamelmurzaev.springsecurirysuleymanov.security;

import com.adamelmurzaev.springsecurirysuleymanov.model.Status;
import com.adamelmurzaev.springsecurirysuleymanov.model.User;
import com.adamelmurzaev.springsecurirysuleymanov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User doesn't exists")
        );
        System.out.println("now");
        return fromUser(user);
    }

    public UserDetails fromUser(User user){
        return new SecurityUser(
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().getAuthorities(),
                user.getStatus().equals(Status.ACTIVE)
        );
    }
}
