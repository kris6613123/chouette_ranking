package com.chouette.rankingWeb.service.std;

import com.chouette.rankingWeb.dao.std.StdWinningBuyinDAO;
import com.chouette.rankingWeb.service.GameService;
import com.chouette.rankingWeb.service.UserService;
import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.std.StdWinningBuyinVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StdWinningBuyinService {
    private final StdWinningBuyinDAO stdWinningBuyinDAO;
    private final UserService userService;
    private final GameService gameService;

    @Transactional
    public void pushIdx() {
        stdWinningBuyinDAO.pushIdx();
    }

    @Transactional
    public void add( StdWinningBuyinVO vo ) {
        stdWinningBuyinDAO.add( vo );
    }

    public Double getWeight( GameVO vo ) {
        return stdWinningBuyinDAO.getWeight( vo );
    }

    public List<StdWinningBuyinVO> getList() {
        return stdWinningBuyinDAO.getList();
    }

    public List<StdWinningBuyinVO> getRecentList() {
        return stdWinningBuyinDAO.getRecentList();
    }

    @Transactional
    public void save( List<StdWinningBuyinVO> vo ) {
        gameService.updateStateToReady( vo.get(0).getApplyDttm() );
        pushIdx();
        for( StdWinningBuyinVO v : vo) {
            v.setRegUser( userService.getUser() );
            add( v );
        }
    }

    public boolean checkDuplicate( List<StdWinningBuyinVO> vo ) {
        List<Integer> grades = vo.stream()
                .map(StdWinningBuyinVO::getGrade)
                .collect(Collectors.toList());
        return grades.size() != new HashSet<>(grades).size();
    }
}
