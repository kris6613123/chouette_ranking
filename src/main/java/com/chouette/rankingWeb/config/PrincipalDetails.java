package com.chouette.rankingWeb.config;

import com.chouette.rankingWeb.vo.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PrincipalDetails implements UserDetails {
    private final UserVO vo;

    public PrincipalDetails(UserVO userVO) {
        this.vo = userVO;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(vo.getAuth()));
        return authorities;
    }

    // get Password 메서드
    @Override
    public String getUsername() {
        return vo.getId();
    }

    // get Username 메서드 (생성한 User은 loginId 사용)
    @Override
    public String getPassword() {
        return vo.getPwd();
    }

    // 계정이 만료 되었는지 (true: 만료X)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되었는지 (true: 만료X)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
