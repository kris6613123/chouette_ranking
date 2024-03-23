package com.chouette.rankingWeb.dao.std;

import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.std.StdWinningBuyinVO;
import com.chouette.rankingWeb.vo.std.StdWinningRewardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StdWinningRewardDAO {

    void pushIdx();

    void add( StdWinningRewardVO vo );

    Double getWeight( GameVO vo );

    List<StdWinningRewardVO> getList();

    List<StdWinningRewardVO> getRecentList();

}
