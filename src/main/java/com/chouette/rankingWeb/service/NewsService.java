package com.chouette.rankingWeb.service;

import com.chouette.rankingWeb.dao.NewsDAO;
import com.chouette.rankingWeb.dao.file.NewsFileDAO;
import com.chouette.rankingWeb.vo.NewsVO;
import com.chouette.rankingWeb.vo.PagerVO;
import com.chouette.rankingWeb.vo.file.FileVO;
import com.chouette.rankingWeb.vo.file.NewsFileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsDAO newsDAO;
    private final NewsFileDAO newsFileDAO;

    private final FileService fileService;
    private final UserService userService;

    @Transactional
    public int add( NewsVO vo ) {
        newsDAO.add( vo );
        return vo.getNews();
    }

    @Transactional
    public void add( NewsFileVO vo ) {
        newsFileDAO.add( vo );
    }

    @Transactional
    public void mod( NewsVO vo ) {
        newsDAO.mod( vo );
    }

    @Transactional
    public void mod( NewsFileVO vo ) {
        newsFileDAO.mod( vo );
    }

    @Transactional
    public void del( NewsVO vo ) {
        newsDAO.del( vo );
    }

    @Transactional
    public void del( NewsFileVO vo ) {
        FileVO file = new FileVO( vo.getFile() );
        fileService.deleteRealFile( fileService.getItem( file ), "news" );
        fileService.del( file );
        newsFileDAO.del( vo );
    }

    public NewsVO getItem( NewsVO vo ) {
        return newsDAO.getItem( vo );
    }

    public List<NewsVO> getList() {
        return newsDAO.getList();
    }

    public List<NewsFileVO> getOldFileList() {
        return newsFileDAO.getOldFileList();
    }

    public List<NewsVO> getRecentList3() {
        return newsDAO.getRecentList3();
    }

    public void getPagingList(Model model, Integer pagenum, Integer contentnum, String keyword ) {
        PagerVO page = new PagerVO();
        page.calculatePaging( 10, newsDAO.getCount( keyword ), pagenum, contentnum );

        //마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정한다.
        List<NewsVO> newsList = newsDAO.getPagingList(( pagenum - 1 ) * contentnum, contentnum, keyword);
        model.addAttribute("page",page);
        model.addAttribute("newsList", newsList);
    }

    @Transactional
    public void save( NewsVO vo ) throws IOException {
        List<Integer> imgList = extractImageList( vo.getContent() );

        if( !imgList.isEmpty() ) {
            vo.setFile( imgList.get(0) );   //가장 첫번째 사진
        }

        vo.setRegUser( userService.getUser() );
        if ( vo.getNews() != null ) {
            mod( vo );
            if( !imgList.isEmpty() ) {
                List<NewsFileVO> fileList = getDelFileList(imgList, vo.getNews());
                for (NewsFileVO newsFile : fileList) {
                    del(newsFile);
                }
            }

        } else {
            add( vo );
        }
        //저장된 img에 news값 추가하기
        for( Integer img : imgList ) {
            NewsFileVO newsFile = new NewsFileVO( img, vo.getNews() );
            mod( newsFile );
        }
    }

    @Transactional
    public Integer saveFile( MultipartFile file ) throws IOException {
        Integer id = fileService.upload( file, "news" );
        add( new NewsFileVO( id ) );
        return id;
    }

    public static List<Integer> extractImageList(String htmlContent) {
        List<Integer> imgList = new ArrayList<>();
        String regex = "(?i)< *img[^>]*src *= *[\"']{0,1}/images/([^\"><']*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlContent);

        while (matcher.find()) {
            String idString = matcher.group(1);
            try {
                int id = Integer.parseInt(idString);
                imgList.add(id);
            } catch (NumberFormatException e) {
                // 정수로 변환할 수 없는 경우 무시
            }
        }

        return imgList;
    }

    public List<NewsFileVO> getDelFileList( List<Integer> delFiles, Integer news ) {
        return newsFileDAO.getDelFileList( delFiles, news );
    }


}
