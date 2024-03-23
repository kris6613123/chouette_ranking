package com.chouette.rankingWeb.dao.std;

import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.std.StdVolumeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StdVolumeDAO {

    void pushIdx();

    void add( StdVolumeVO vo );

    Double getWeight( GameVO vo );

    StdVolumeVO getRecentItem();

    List<StdVolumeVO> getList();

}
