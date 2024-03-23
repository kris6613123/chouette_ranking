package com.chouette.rankingWeb.dao;

import com.chouette.rankingWeb.vo.GameVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface GameDAO {

    void add( GameVO vo );

    void mod( GameVO vo );

    void dailyMod( GameVO vo );

    void updateStateToReady( LocalDate applyDttm );

    void del( GameVO vo );

    void delByCustomer( Integer customer );

    GameVO getItem( GameVO vo );

    int getCount( @Param("keyword") String keyword, @Param("state") String state, @Param("start") String start, @Param("end") String end );

    List<GameVO> getList();

    List<GameVO> getPagingList( @Param("pagenum") int pagenum, @Param("contentnum") int contentnum, @Param("keyword") String keyword, @Param("state") String state, @Param("start") String start, @Param("end") String end );

    List<GameVO> getListByCustomer( int customer );

    List<GameVO> getSearchListByCustomer( int customer, @Param("keyword") String keyword );

}
