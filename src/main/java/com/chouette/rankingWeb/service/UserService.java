package com.chouette.rankingWeb.service;


import com.chouette.rankingWeb.dao.*;
import com.chouette.rankingWeb.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDAO userDAO;

    @Transactional
    public void add( UserVO vo ) { userDAO.add(vo); }

    @Transactional
    public void del( UserVO vo ) { userDAO.del(vo); }

    public UserVO getItemById( String id ) { return userDAO.getItemById( id ); }

    public List<UserVO> getList() {
        return userDAO.getList();
    }

    public List<UserVO> getAllList() {
        return userDAO.getAllList();
    }

//    public void getPagingList( Model model, Integer pagenum, Integer contentnum, String keyword ) {
//        PagerVO page = new PagerVO();
//        page.calculatePaging( 10, userDAO.getCount( keyword ), pagenum, contentnum );
//        List<UserVO> userList = userDAO.getPagingList(( pagenum - 1 ) * contentnum, contentnum, keyword);
//
//        model.addAttribute("page",page);
//        model.addAttribute("userList", userList);
//    }

    public int getUser() {
        String id = ( (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername();
        return getItemById( id ).getUser();
    }


}
