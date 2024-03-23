package com.chouette.rankingWeb.service.std;

import com.chouette.rankingWeb.dao.std.StdVolumeDAO;
import com.chouette.rankingWeb.service.GameService;
import com.chouette.rankingWeb.service.UserService;
import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.std.StdVolumeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StdVolumeService {
    private final StdVolumeDAO stdVolumeDAO;
    private final UserService userService;
    private final GameService gameService;

    @Transactional
    public void pushIdx() {
        stdVolumeDAO.pushIdx();
    }

    @Transactional
    public void add( StdVolumeVO vo ) {
        stdVolumeDAO.add( vo );
    }

    public Double getWeight( GameVO vo ) {
        return stdVolumeDAO.getWeight( vo );
    }

    public StdVolumeVO getRecentItem() {
        return stdVolumeDAO.getRecentItem();
    }

    public List<StdVolumeVO> getList() {
        return stdVolumeDAO.getList();
    }

    @Transactional
    public void save( StdVolumeVO vo ) {
        gameService.updateStateToReady( vo.getApplyDttm() );
        pushIdx();
        vo.setRegUser( userService.getUser() );
        add( vo );
    }
}
