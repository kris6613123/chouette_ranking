package com.chouette.rankingWeb.dao;

import com.chouette.rankingWeb.vo.NewsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsDAO {

    void add(NewsVO vo);

    void mod(NewsVO vo);

    void del(NewsVO vo);

    NewsVO getItem(NewsVO vo);

    int getCount( @Param("keyword") String keyword );

    List<NewsVO> getList();

    List<NewsVO> getRecentList3();

    List<NewsVO> getPagingList( @Param("pagenum") int pagenum, @Param("contentnum") int contentnum, @Param("keyword") String keyword );

}
