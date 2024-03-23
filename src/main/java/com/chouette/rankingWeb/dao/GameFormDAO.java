package com.chouette.rankingWeb.dao;

import com.chouette.rankingWeb.vo.GameFormVO;
import com.chouette.rankingWeb.vo.GameVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameFormDAO {
    void add( GameFormVO vo );

    void del( GameFormVO vo );

//    GameFormVO getItem( GameFormVO vo );

    List<GameFormVO> getList();
}
