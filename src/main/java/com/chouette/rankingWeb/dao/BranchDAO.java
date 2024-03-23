package com.chouette.rankingWeb.dao;

import com.chouette.rankingWeb.vo.BranchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BranchDAO {

    void add( BranchVO vo );

    void mod( BranchVO vo );

    void del( BranchVO vo );

    BranchVO getItem( BranchVO vo );

    List<BranchVO> getList();

    List<BranchVO> getAllList();

    List<BranchVO> getListIncludeFile();

    List<BranchVO> getOffList();

}
