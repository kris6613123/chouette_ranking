package com.chouette.rankingWeb.scheduler;

import com.chouette.rankingWeb.dao.DailySchedulerDAO;
import com.chouette.rankingWeb.service.NewsService;
import com.chouette.rankingWeb.service.RankingHistoryService;
import com.chouette.rankingWeb.service.GameService;
import com.chouette.rankingWeb.service.SchedulerService;
import com.chouette.rankingWeb.service.std.StdAgingService;
import com.chouette.rankingWeb.service.std.StdVolumeService;
import com.chouette.rankingWeb.service.std.StdWinningBuyinService;
import com.chouette.rankingWeb.service.std.StdWinningRewardService;
import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.file.NewsFileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncScheduler {

    private final SchedulerService schedulerService;

    @Scheduled(cron = "0 0 11 * * *")  //매일 오전 11시 실행
    public void calculateDaily() {
        schedulerService.calculateScore();
    }

    @Scheduled(cron = "0 30 11 * * MON")   //월요일 오후 11시 30분마다 실행
    public void delNewsSchedule() {
        schedulerService.delNewsSchedule();
    }

}
