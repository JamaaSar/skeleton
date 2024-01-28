package com.nnk.springboot.services;

import com.nnk.springboot.dto.CurrentUserDTO;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import com.nnk.springboot.domain.User;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
        }

        private List<GrantedAuthority> getGrantedAuthorities(String role) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(role));
            return authorities;
        }
    public void creatUserFromOauth2(Authentication authentication)
    {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().equals("OAUTH2_USER")){
                DefaultOAuth2User defaultOAuth2User =
                        (DefaultOAuth2User) SecurityContextHolder.getContext().
                                getAuthentication().getPrincipal();
                String username = defaultOAuth2User.getAttribute("login");
                User user = userRepository.findByUsername(username.toLowerCase());
                if(user == null){
                    User newUser =  new User();
                    newUser.setUsername(username.toLowerCase());
                    newUser.setPassword(passwordEncoder().encode("password"));
                    newUser.setFullname(username);
                    newUser.setRole("USER");
                    userRepository.save(newUser);
                }
            }
        }
    }
    public CurrentUserDTO getCurrentUser(Authentication authentication)
    {
        List<GrantedAuthority> authorities =
            (List<GrantedAuthority>) authentication.getAuthorities();
        String username = authentication.getName();
        Boolean isAdmin = false;

        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().equals("OAUTH2_USER")){
                DefaultOAuth2User defaultOAuth2User =
                        (DefaultOAuth2User) SecurityContextHolder.getContext().
                                getAuthentication().getPrincipal();
                username = defaultOAuth2User.getAttribute("login");
            }
            if(authority.getAuthority().equals("ADMIN")){
                isAdmin =  true;
            }
        }
        return new CurrentUserDTO(username, isAdmin );
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}