package com.chouette.rankingWeb.dao;

import com.chouette.rankingWeb.vo.CustomerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerDAO {

    void add( CustomerVO vo );

    void mod( CustomerVO vo );

    void del( CustomerVO vo );

    CustomerVO getItem( CustomerVO vo );

    CustomerVO getItemByTel( String tel );

    int getCount( @Param("keyword") String keyword );

    Boolean checkValidate( CustomerVO vo );

    List<CustomerVO> getPagingListIncludeTier( @Param("pagenum") int pagenum, @Param("contentnum") int contentnum, @Param("keyword") String keyword );


    List<CustomerVO> getPagingList( @Param("pagenum") int pagenum, @Param("contentnum") int contentnum, @Param("keyword") String keyword );

}