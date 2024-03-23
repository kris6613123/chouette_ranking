package com.chouette.rankingWeb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public abstract class CtrlBase {

    @ModelAttribute
    public void baseAttribute( Model model ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isUser = false;
        boolean isAdmin = false;
        boolean isRoot = false;

        if( !authentication.getName().equals("anonymousUser") ) {
            isUser = true;
            if( authentication.getAuthorities().contains( new SimpleGrantedAuthority( "ADMIN" ) ) ) {
                isAdmin = true;
            }
            else {
                isRoot = true;
            }
        }

        model.addAttribute( "isUser", isUser );
        model.addAttribute( "isAdmin", isAdmin );
        model.addAttribute( "isRoot", isRoot );
    }
}
