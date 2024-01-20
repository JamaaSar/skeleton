package com.nnk.springboot.services;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);
            System.out.println("userload");

            System.out.println(user);

            return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
        }

        private List<GrantedAuthority> getGrantedAuthorities(String role) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            System.out.println(authorities);
            authorities.add(new SimpleGrantedAuthority(role));
            System.out.println(authorities);

            return authorities;
        }
}