package com.chouette.rankingWeb.dao.file;

import com.chouette.rankingWeb.vo.file.CustomerFileVO;
import com.chouette.rankingWeb.vo.file.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerFileDAO {

    void add( CustomerFileVO vo );

    void del( CustomerFileVO vo );

    CustomerFileVO getItem( CustomerFileVO vo );

}
