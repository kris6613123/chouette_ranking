package com.chouette.rankingWeb.dao.std;

import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.std.StdAgingVO;
import com.chouette.rankingWeb.vo.std.StdWinningBuyinVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StdAgingDAO {

    void pushIdx();

    void add( StdAgingVO vo );

    Double getWeight( GameVO vo );

    List<StdAgingVO> getList();

    List<StdAgingVO> getRecentList();

}
