package com.example.expressfood.security;

import com.example.expressfood.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

        private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.expressfood.entities.User user=userService.getUserByUserName(username);
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(r-> authorities.add(new SimpleGrantedAuthority(r.getRoleName())));
        return new User(user.getUserName(),user.getEncryptedPassword(), user.getIsActivated(), true, true, true, authorities);
    }
}
