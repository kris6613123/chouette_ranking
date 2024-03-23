package com.chouette.rankingWeb.service.std;

import com.chouette.rankingWeb.dao.std.StdAgingDAO;
import com.chouette.rankingWeb.service.GameService;
import com.chouette.rankingWeb.service.UserService;
import com.chouette.rankingWeb.vo.GameVO;
import com.chouette.rankingWeb.vo.std.StdAgingVO;
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
public class StdAgingService {
    private final StdAgingDAO stdAgingDAO;
    private final UserService userService;
    private final GameService gameService;

    @Transactional
    public void pushIdx() {
        stdAgingDAO.pushIdx();
    }

    @Transactional
    public void add( StdAgingVO vo ) {
        stdAgingDAO.add( vo );
    }

    public Double getWeight( GameVO vo ) {
        return stdAgingDAO.getWeight( vo );
    }

    public List<StdAgingVO> getList() {
        return stdAgingDAO.getList();
    }

    public List<StdAgingVO> getRecentList() {
        return stdAgingDAO.getRecentList();
    }

    @Transactional
    public void save( List<StdAgingVO> vo ) {
        gameService.updateStateToReady( vo.get(0).getApplyDttm() );
        pushIdx();
        for( StdAgingVO v : vo) {
            v.setRegUser( userService.getUser() );
            add( v );
        }
    }

    public boolean checkDuplicate( List<StdAgingVO> vo ) {
        List<Integer> grades = vo.stream()
                .map(StdAgingVO::getGrade)
                .collect(Collectors.toList());
        return grades.size() != new HashSet<>(grades).size();
    }

}
