package com.chouette.rankingWeb.dao.file;

import com.chouette.rankingWeb.vo.file.CustomerFileVO;
import com.chouette.rankingWeb.vo.file.FileVO;
import com.chouette.rankingWeb.vo.file.NewsFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsFileDAO {

    void add( NewsFileVO vo );

    void mod( NewsFileVO vo );

    void del( NewsFileVO vo );

    NewsFileVO getItem( NewsFileVO vo );

    List<NewsFileVO> getDelFileList( @Param("delFiles") List<Integer> delFiles, @Param("news") Integer news );

    List<NewsFileVO> getOldFileList();

}
