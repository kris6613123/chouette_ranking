package com.chouette.rankingWeb.service;

import com.chouette.rankingWeb.dao.GameDAO;
import com.chouette.rankingWeb.vo.PagerVO;
import com.chouette.rankingWeb.vo.GameVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameDAO gameDAO;

    private final GameFormService gameFormService;

    @Transactional
    public void add( GameVO vo ) {
        gameDAO.add( vo );
    }

    @Transactional
    public void addList( List<GameVO> gameList ) {
        for( GameVO game : gameList ) {
            gameDAO.add( game );
        }
    }

    @Transactional
    public void mod( GameVO vo ) {
        gameDAO.mod( vo );
    }

    @Transactional
    public void dailyMod( GameVO vo ) {
        gameDAO.dailyMod( vo );
    }

    @Transactional
    public void updateStateToReady( LocalDate applyDttm ) {
        gameDAO.updateStateToReady( applyDttm );
    }

    @Transactional
    public void del( GameVO vo ) {
        gameDAO.del( vo );
    }

    @Transactional
    public void delByCustomer( Integer customer ) {
        gameDAO.delByCustomer( customer );
    }


    public GameVO getItem( GameVO vo ) {
        return gameDAO.getItem( vo );
    }

    public List<GameVO> getList() {
        return gameDAO.getList();
    }

    public void getPagingList( Model model, Integer pagenum, Integer contentnum, String keyword, String state, String start, String end ) {
        PagerVO page = new PagerVO();
        page.calculatePaging( 10, gameDAO.getCount( keyword, state, start, end ), pagenum, contentnum );

        //마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정한다.
        List<GameVO> gameList = gameDAO.getPagingList(( pagenum - 1 ) * contentnum, contentnum, keyword, state, start, end );
        model.addAttribute( "page",page );
        model.addAttribute( "gameList", gameList );
        model.addAttribute( "gameFormList", gameFormService.getList() );
    }

    public List<GameVO> getListByCustomer( int customer ) {
        return gameDAO.getListByCustomer( customer );
    }


    public List<GameVO> getListByCustomer( int customer, String keyword ) {
        return gameDAO.getSearchListByCustomer( customer, keyword );
    }

}
