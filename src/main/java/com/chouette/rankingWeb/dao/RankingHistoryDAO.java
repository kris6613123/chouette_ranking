package com.chouette.rankingWeb.dao;

import com.chouette.rankingWeb.vo.CustomerVO;
import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.RankingHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RankingHistoryDAO {

    void add();
    void add2();

    void add3();

    void createView();

    void delAll();

    RankingHistoryVO getItemByCustomer( int customer );

    int getPagingCount( @Param("keyword") String keyword );

    List<RankingHistoryVO> getPagingList(@Param("pagenum") int pagenum, @Param("contentnum") int contentnum, @Param("keyword") String keyword );

    List<RankingHistoryVO> getTopt10ListByBranch( int branch );

    List<RankingHistoryVO> getTopt10ListByTotal();

    List<RankingHistoryVO> getTopt10ListByOnline();

    List<RankingHistoryVO> getTopt10ListByOffline();

    List<RankingHistoryVO> getListByCustomer( int customer );


}
