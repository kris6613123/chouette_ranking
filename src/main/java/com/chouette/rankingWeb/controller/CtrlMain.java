package com.chouette.rankingWeb.controller;
import com.chouette.rankingWeb.service.*;
import com.chouette.rankingWeb.vo.BranchVO;
import com.chouette.rankingWeb.vo.RankingHistoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping( method = RequestMethod.GET )
public class CtrlMain extends CtrlBase{
    private final BranchService branchService;
    private final NewsService newsService;
    private final RankingHistoryService rankingHistoryService;


    @RequestMapping( value = "/" )
    public String home ( Model model ) {
        model.addAttribute( "totalList", rankingHistoryService.getListBySetTier( rankingHistoryService.getTopt10ListByTotal() ) );
        model.addAttribute( "onlineList", rankingHistoryService.getListBySetTier( rankingHistoryService.getTopt10ListByOnline() ) );
        model.addAttribute( "offlineList", rankingHistoryService.getListBySetTier( rankingHistoryService.getTopt10ListByOffline() ) );
        model.addAttribute( "newsList", newsService.getRecentList3() );
        return "home";

    }

    @RequestMapping( value = "/ranking" )
    public String ranking ( Model model ) {
        model.addAttribute( "totalList", rankingHistoryService.getListBySetTier( rankingHistoryService.getTopt10ListByTotal() ) );
        model.addAttribute( "onlineList", rankingHistoryService.getListBySetTier( rankingHistoryService.getTopt10ListByOnline() ) );
        model.addAttribute( "offlineList", rankingHistoryService.getListBySetTier( rankingHistoryService.getTopt10ListByOffline() ) );
        model.addAttribute( "branchList", branchService.getListIncludeFile() );

        Map<Integer, List<RankingHistoryVO>> totalListByBranch = new HashMap<>();
        List<BranchVO> branchOffList = branchService.getOffList();
        for ( BranchVO b : branchOffList ) {
            totalListByBranch.put( b.getBranch(), rankingHistoryService.getTopt10ListByBranch( b.getBranch() ) );
        }
        model.addAttribute("totalListByBranch", totalListByBranch);
        return "ranking";
    }

    @RequestMapping( value = "/news" )
    public String news ( Model model ) {
        model.addAttribute( "newsList", newsService.getList() );
        return "news";
    }

    @RequestMapping( value = { "/search" } )
    public String search ( Model model, @RequestParam(defaultValue = "1", required = false) Integer pagenum, @RequestParam(defaultValue = "10", required = false) Integer contentnum, @RequestParam(value="customer", required = false ) String keyowrd ) {
        rankingHistoryService.getPagingList(model, pagenum, contentnum, keyowrd);
        return "search";
    }

    @RequestMapping( value = "/location" )
    public String location ( Model model ) {
        model.addAttribute( "branchList", branchService.getListIncludeFile() );
        return "location";
    }

}
