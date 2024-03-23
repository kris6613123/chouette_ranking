package com.chouette.rankingWeb.dao.file;

import com.chouette.rankingWeb.vo.file.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileDAO {

    void add(FileVO vo);

    void del(FileVO vo);

    FileVO getItem( FileVO vo );

    List<FileVO> getTierList();

    List<FileVO> getDelTierList( List<Integer> delFiles );

}
