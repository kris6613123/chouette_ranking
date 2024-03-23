package com.chouette.rankingWeb.service;

import com.chouette.rankingWeb.config.PrincipalDetails;
import com.chouette.rankingWeb.dao.UserDAO;
import com.chouette.rankingWeb.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        UserVO vo = userDAO.getItemById( username );
        if( vo == null ) {
            throw new UsernameNotFoundException("User not found with id: " + username);
        }
        return new PrincipalDetails(vo);
    }

}
