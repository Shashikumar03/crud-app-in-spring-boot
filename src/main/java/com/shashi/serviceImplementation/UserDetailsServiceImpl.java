package com.shashi.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shashi.config.CustomUserDetails;
import com.shashi.entities.User;
import com.shashi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        System.out.println(user);
        if(user==null) {
            throw new UsernameNotFoundException("User Not found");
        }
        CustomUserDetails customUserDetails= new CustomUserDetails(user);
        return customUserDetails;
    }

}