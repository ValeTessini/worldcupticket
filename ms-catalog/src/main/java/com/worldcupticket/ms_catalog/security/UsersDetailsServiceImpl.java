package com.worldcupticket.ms_catalog.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.worldcupticket.ms_catalog.domain.UserAccount;
import com.worldcupticket.ms_catalog.repository.UserAccountRepository;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public UsersDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount account = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRoles().toArray(new String[0]))
                .disabled(!account.isEnabled())
                .build();
    }
}
