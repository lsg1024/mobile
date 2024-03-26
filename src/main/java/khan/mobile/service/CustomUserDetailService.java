package khan.mobile.service;

import khan.mobile.dto.PrincipalDetails;
import khan.mobile.entity.Users;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Users userData = userRepository.findByEmail(userEmail);

        log.info("CustomUserDetailService userData = {}", userData.getEmail());

        if (userData != null) {
            return new PrincipalDetails(userData);
        }

        return null;
    }
}
