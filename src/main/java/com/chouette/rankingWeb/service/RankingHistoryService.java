package com.chouette.rankingWeb.service;

import com.chouette.rankingWeb.dao.RankingHistoryDAO;
import com.chouette.rankingWeb.service.std.StdTierService;
import com.chouette.rankingWeb.vo.PagerVO;
import com.chouette.rankingWeb.vo.RankingHistoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankingHistoryService {

    private final RankingHistoryDAO rankingHistoryDAO;
    private final StdTierService stdTierService;

    @Transactional
    public void add() {
        rankingHistoryDAO.add();
    }

    @Transactional
    public void add2() {
        rankingHistoryDAO.add2();
    }

    @Transactional
    public void add3() {
        rankingHistoryDAO.add3();
    }

    @Transactional
    public void createView() {
        rankingHistoryDAO.createView();
    }

    @Transactional
    public void delAll() {
        rankingHistoryDAO.delAll();
    }

    public RankingHistoryVO getItemByCustomer( int customer ) {
        return rankingHistoryDAO.getItemByCustomer( customer );
    }

    public void getPagingList(Model model, Integer pagenum, Integer contentnum, String keyword ) {
        PagerVO page = new PagerVO();
        page.calculatePaging( 10, rankingHistoryDAO.getPagingCount( keyword ), pagenum, contentnum );
        //마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정한다.
        List<RankingHistoryVO> customerList = rankingHistoryDAO.getPagingList(( pagenum - 1 ) * contentnum, contentnum, keyword);
        model.addAttribute("page",page);
        model.addAttribute("customerList", customerList);
    }

    public List<RankingHistoryVO> getTopt10ListByBranch( int branch ) {
        return rankingHistoryDAO.getTopt10ListByBranch( branch );
    }

    public List<RankingHistoryVO> getTopt10ListByTotal() {
        return rankingHistoryDAO.getTopt10ListByTotal();
    }

    public List<RankingHistoryVO> getTopt10ListByOnline() {
        return rankingHistoryDAO.getTopt10ListByOnline();
    }

    public List<RankingHistoryVO> getTopt10ListByOffline() {
        return rankingHistoryDAO.getTopt10ListByOffline();
    }



    public List<RankingHistoryVO> getListByCustomer( int customer ) {
        return rankingHistoryDAO.getListByCustomer( customer );
    }

    public List<RankingHistoryVO> getListBySetTier( List<RankingHistoryVO> rankingHistoryList ) {
        for( RankingHistoryVO rh : rankingHistoryList ) {
            rh.setTier( stdTierService.getItemByCustomer( rh.getCustomer() ).getTier() );
        }
        return rankingHistoryList;
    }

    @Transactional
    public void save() {
        delAll();
        add();
        add2();
        add3();
        createView();
    }

}
