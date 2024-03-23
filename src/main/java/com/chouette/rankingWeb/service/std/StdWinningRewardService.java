package com.chouette.rankingWeb.service.std;

import com.chouette.rankingWeb.dao.std.StdWinningRewardDAO;
import com.chouette.rankingWeb.service.GameService;
import com.chouette.rankingWeb.service.UserService;
import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.std.StdWinningRewardVO;
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
public class StdWinningRewardService {
    private final StdWinningRewardDAO stdWinningRewardDAO;
    private final UserService userService;
    private final GameService gameService;

    @Transactional
    public void pushIdx() {
        stdWinningRewardDAO.pushIdx();
    }

    @Transactional
    public void add( StdWinningRewardVO vo ) {
        stdWinningRewardDAO.add( vo );
    }

    public Double getWeight( GameVO vo ) {
        return stdWinningRewardDAO.getWeight( vo );
    }

    public List<StdWinningRewardVO> getList() {
        return stdWinningRewardDAO.getList();
    }

    public List<StdWinningRewardVO> getRecentList() {
        return stdWinningRewardDAO.getRecentList();
    }

    @Transactional
    public void save( List<StdWinningRewardVO> vo ) {
        gameService.updateStateToReady( vo.get(0).getApplyDttm() );
        pushIdx();
        for( StdWinningRewardVO v : vo) {
            v.setRegUser( userService.getUser() );
            add( v );
        }
    }

    public boolean checkDuplicate( List<StdWinningRewardVO> vo ) {
        List<Double> rewardweights = vo.stream()
                .map(StdWinningRewardVO::getRewardWeight)
                .collect(Collectors.toList());
        return rewardweights.size() != new HashSet<>(rewardweights).size();
    }
}
