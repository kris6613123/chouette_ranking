package com.chouette.rankingWeb.dao;

import com.chouette.rankingWeb.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDAO {

    void add( UserVO vo );

    void del( UserVO vo );

    UserVO getItemById( String id );

    int getCount( @Param("keyword") String keyword );

    List<UserVO> getList();

    List<UserVO> getAllList();

    List<UserVO> getPagingList( @Param("pagenum") int pagenum, @Param("contentnum") int contentnum, @Param("keyword") String keyword );

}
