package khan.mobile.service;

import khan.mobile.dto.CustomUserDetail;
import khan.mobile.dto.UserDto;
import khan.mobile.entity.Users;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Users userData = userRepository.findByEmail(userEmail);

        if (userData != null) {
            return new CustomUserDetail(userData);
        }

        return null;
    }
}
