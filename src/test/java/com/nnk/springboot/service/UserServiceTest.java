package com.nnk.springboot.service;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.CurrentUserDTO;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.nio.CharBuffer;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private User admin = new User();
    private User testUser = new User();
    private CurrentUserDTO currentUserDTO;

    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    List<GrantedAuthority> authorities;
    DefaultOAuth2User defaultOAuth2User;
    SecurityContextHolder securityContextHolder;

    private List<User> userList = new ArrayList<>();


    @BeforeEach
    public void setUp() {

        admin.setId(1);
        admin.setUsername("admin");
        admin.setRole("ADMIN");
        admin.setFullname("Admin");
        admin.setPassword("password");

        testUser.setId(2);
        testUser.setUsername("user");
        testUser.setRole("USER");
        testUser.setFullname("User");
        testUser.setPassword("test123");

        currentUserDTO = new CurrentUserDTO();
        currentUserDTO.setUsername("User");
        currentUserDTO.setIsAdmin(false);

        userList.add(admin);
        userList.add(testUser);

        authorities = new ArrayList<>();




    }

    @Test
    @DisplayName("load user by username")
    public void testLoadUser() {

        // Given.

        // When.
        when(userRepository.findByUsername(admin.getUsername())).thenReturn(
                admin);
        UserDetails userDetails = userService.loadUserByUsername(admin.getUsername());

        // Then.
        assertEquals(admin.getUsername(), userDetails.getUsername());
        assertEquals(admin.getPassword(), userDetails.getPassword());
        assertNotNull(userDetails.getAuthorities());


    }

    @Test
    public void testCreatUserFromOauth2() {

        // Given.
        String attrName = "oauth";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(attrName, "login");
        attributes.put("login", "login");
        defaultOAuth2User = new DefaultOAuth2User(authorities,attributes, attrName );
        authorities.add(new SimpleGrantedAuthority("OAUTH2_USER"));
        // When.


        when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContextHolder.setContext(securityContext);
        when((List<GrantedAuthority>) authentication.getAuthorities()).thenReturn(authorities);
        when((DefaultOAuth2User) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);

        userService.creatUserFromOauth2(authentication);
        // Then.
        verify(userRepository, times(1)).findByUsername("login");


    }
    @Test
    public void testCreatUserFromOauth2UserExist() {

        // Given.
        String attrName = "oauth";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(attrName, "user");
        attributes.put("login", "user");
        defaultOAuth2User = new DefaultOAuth2User(authorities,attributes, attrName );
        authorities.add(new SimpleGrantedAuthority("OAUTH2_USER"));
        // When.


        when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContextHolder.setContext(securityContext);
        when((List<GrantedAuthority>) authentication.getAuthorities()).thenReturn(authorities);
        when((DefaultOAuth2User) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
        when(userRepository.findByUsername("user")).thenReturn(testUser);

        userService.creatUserFromOauth2(authentication);
        // Then.
        verify(userRepository, times(1)).findByUsername("user");


    }
    @Test
    public void testCreatUserFromOauth2Admin() {

        // Given.
        String attrName = "oauth";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(attrName, "user");
        attributes.put("login", "user");
        defaultOAuth2User = new DefaultOAuth2User(authorities,attributes, attrName );
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // When.


        when((List<GrantedAuthority>) authentication.getAuthorities()).thenReturn(authorities);

        userService.creatUserFromOauth2(authentication);
        // Then.


    }

    @Test
    public void testGetCurrentUser() {

        // Given.
        String attrName = "oauth";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(attrName, "login");
        attributes.put("login", "login");
        defaultOAuth2User = new DefaultOAuth2User(authorities,attributes, attrName );
        authorities.add(new SimpleGrantedAuthority("OAUTH2_USER"));


        when(securityContext.getAuthentication()).thenReturn(authentication);
        securityContextHolder.setContext(securityContext);
        // When.
        when((List<GrantedAuthority>) authentication.getAuthorities()).thenReturn(authorities);
        when((DefaultOAuth2User) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);

        CurrentUserDTO resultat = userService.getCurrentUser(authentication);
        // Then.
        assertEquals(resultat.getIsAdmin(), currentUserDTO.getIsAdmin());


    }
    @Test
    public void testGetCurrentUserAdmin() {

        // Given.
        String attrName = "oauth";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(attrName, "login");
        attributes.put("login", "login");
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // When.
        when((List<GrantedAuthority>) authentication.getAuthorities()).thenReturn(authorities);

        CurrentUserDTO resultat = userService.getCurrentUser(authentication);
        // Then.
        assertEquals(resultat.getIsAdmin(), true);


    }


    @Test
    public void testGetAllUser() {

        // Given.

        // When.
        when(userRepository.findAll()).thenReturn(userList);
        List<User> lists = userService.getAll();

        // Then.
        assertEquals(userList.size(), lists.size());
        verify(userRepository, times(1)).findAll();


    }
    @Test
    public void testGetUserById() {

        // Given.

        // When.
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(admin));
        User res = userService.get(1);

        // Then.
        assertEquals(res.getId(), admin.getId());
        assertEquals(res.getRole(), admin.getRole());
        assertEquals(res.getFullname(), admin.getFullname());
        verify(userRepository, times(1)).findById(1);


    }

    @Test
    public void testSaveUser() {

        // Given.
        User user2 = new User();
        user2.setId(3);
        user2.setUsername("user3");
        user2.setFullname("userTest");
        user2.setRole("USER");

        // When.
        userService.save(user2);

        // Then.
        verify(userRepository, times(1)).save(user2);


    }
    @Test
    public void testDeleteUser() {


        // When.
        userService.delete(3);

        // Then.
        verify(userRepository, times(1)).deleteById(3);


    }

}
