package com.chouette.rankingWeb.dao.std;

import com.chouette.rankingWeb.vo.CustomerVO;
import com.chouette.rankingWeb.vo.RankingHistoryVO;
import com.chouette.rankingWeb.vo.std.StdTierVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StdTierDAO {

    void add( StdTierVO vo );

    void delAll();

    StdTierVO getTypeSItem();

    StdTierVO getItemByCustomer( Integer customer );

    List<StdTierVO> getList();

    List<StdTierVO> getTypeRList();

}
