package com.chouette.rankingWeb.dao.file;

import com.chouette.rankingWeb.vo.file.BranchFileVO;
import com.chouette.rankingWeb.vo.file.CustomerFileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BranchFileDAO {

    void add( BranchFileVO vo );

    void del( BranchFileVO vo );

    BranchFileVO getItem( BranchFileVO vo );

}
