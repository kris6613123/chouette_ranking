package com.chouette.rankingWeb.service;

import com.chouette.rankingWeb.dao.CustomerDAO;
import com.chouette.rankingWeb.dao.file.CustomerFileDAO;
import com.chouette.rankingWeb.vo.*;
import com.chouette.rankingWeb.vo.file.CustomerFileVO;
import com.chouette.rankingWeb.vo.file.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final CustomerFileDAO customerFileDAO;

    private final FileService fileService;
    private final UserService userService;

    @Transactional
    public void add( CustomerVO vo ) {
        customerDAO.add( vo );
    }

    @Transactional
    public void add( CustomerFileVO vo ) {
        customerFileDAO.add( vo );
    }

    @Transactional
    public void mod( CustomerVO vo ) {
        customerDAO.mod( vo );
    }

    @Transactional
    public void del( CustomerVO vo ) {
        customerDAO.del( vo );
    }

    @Transactional
    public void del( CustomerFileVO vo ) {
        CustomerFileVO customerFile = getItem( vo );
        if( customerFile != null ) {
            FileVO file = new FileVO( customerFile.getFile() );
            fileService.deleteRealFile( fileService.getItem( file ), "customer" );
            fileService.del( file );
            customerFileDAO.del( customerFile );
        }
    }

    public CustomerFileVO getItem( CustomerFileVO vo ) {
        return customerFileDAO.getItem( vo );
    }

    public CustomerVO getItem( CustomerVO vo ) {
        return customerDAO.getItem( vo );
    }

    public CustomerVO getItemByTel( String tel ) {
        return customerDAO.getItemByTel( tel );
    }

    public Boolean checkValidate( CustomerVO vo ) {
        return !customerDAO.checkValidate(vo);
    }

    public void getPagingList( Model model, Integer pagenum, Integer contentnum, String keyword ) {
        PagerVO page = new PagerVO();
        page.calculatePaging( 10, customerDAO.getCount( keyword ), pagenum, contentnum );

        //마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정한다.
        List<CustomerVO> customerList = customerDAO.getPagingListIncludeTier(( pagenum - 1 ) * contentnum, contentnum, keyword);
        model.addAttribute("page",page);
        model.addAttribute("customerList", customerList);
    }

    @Transactional
    public void save( CustomerVO vo, MultipartFile file ) throws IOException {
        vo.setRegUser( userService.getUser() );
        if ( vo.getCustomer() != null ) {
            mod( vo );
            if ( vo.getFile() == null ) {               //기존에 파일이 존재하지 않았거나 존재했는데 다른 사진으로 바뀌었거나 기본이미지를 클릭했거나.
                CustomerFileVO customerFile = new CustomerFileVO();
                customerFile.setCustomer( vo.getCustomer() );
                del( customerFile );
            }
        } else {
            add( vo );
        }

        if ( file != null && !file.isEmpty() ) {
            add( new CustomerFileVO( fileService.upload( file, "customer" ), vo.getCustomer() ) );
        }
    }

    @Transactional
    public void delete( CustomerVO vo ) {
        CustomerFileVO customerFile = new CustomerFileVO();
        customerFile.setCustomer( vo.getCustomer() );
        del( customerFile );
        del( vo );
    }


}