package com.chouette.rankingWeb.service;

import com.chouette.rankingWeb.dao.DailySchedulerDAO;
import com.chouette.rankingWeb.service.std.StdAgingService;
import com.chouette.rankingWeb.service.std.StdVolumeService;
import com.chouette.rankingWeb.service.std.StdWinningBuyinService;
import com.chouette.rankingWeb.service.std.StdWinningRewardService;
import com.chouette.rankingWeb.vo.CustomerVO;
import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.file.CustomerFileVO;
import com.chouette.rankingWeb.vo.file.NewsFileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {
    private final DailySchedulerDAO dailySchedulerDAO;

    private final StdVolumeService stdVolumeService;
    private final StdWinningBuyinService stdWinningBuyinService;
    private final StdWinningRewardService stdWinningRewardService;
    private final StdAgingService stdAgingService;
    private final GameService gameService;
    private final RankingHistoryService rankingHistoryService;
    private final NewsService newsService;

    @Transactional
    public void calculateScore() {
        List<GameVO> gameList = gameService.getList();
        for( GameVO game : gameList ) {
            if( game.getState().equals("READY") ) {

                Double volume = stdVolumeService.getWeight( game );
                Double winning_buyin = stdWinningBuyinService.getWeight( game );
                Double winning_reward = stdWinningRewardService.getWeight( game );

                if( volume == null || winning_buyin == null || winning_reward == null ) {
                    game.setState("FAIL");
                    //fail이면 다른 곳에 정보 뜨지 않게 cpi null로 변경
                    game.setCpi(null);
                }
                else {
                    double score = game.getTotalBuyin() * volume + game.getReward() * winning_buyin * winning_reward * 0.0001;
                    game.setScore( BigDecimal.valueOf( score ).setScale(2, RoundingMode.HALF_UP) );
                    game.setState("SUCCESS");
                }
            }

            //계산할 필요가 없거나 이미 실패한거를 제외하고 aging 계산
            if( game.getState().equals("SUCCESS") ) {
                Double aging = stdAgingService.getWeight( game );
                if( aging == null ) {
                    game.setState("FAIL");
                    game.setCpi(null);
                    gameService.dailyMod( game );
                    continue;
                }
                if( aging == 0 ) {
                    game.setState("OLD");
                }
                log.info("game : " + game);
                game.setCpi( BigDecimal.valueOf( game.getScore().doubleValue() * aging ).setScale(2, RoundingMode.HALF_UP) );
            }
            gameService.dailyMod( game );
        }

        rankingHistoryService.save();
        dailySchedulerDAO.add();
    }


    @Transactional
    public void delNewsSchedule() {
        List<NewsFileVO> fileList = newsService.getOldFileList();
        for (NewsFileVO newsFile : fileList) {
            newsService.del(newsFile);
        }
        dailySchedulerDAO.add();
    }

}
