package com.ntd.bank.authenticationservice.service;

import com.ntd.bank.authenticationservice.dto.UserDetailCustom;
import com.ntd.bank.authenticationservice.model.Users;
import com.ntd.bank.authenticationservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Users> userOptional = userRepo.findByUsername(s);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(s);
        }
        Users user = userOptional.get();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserDetailCustom(user, grantedAuthorityList);
    }
}
