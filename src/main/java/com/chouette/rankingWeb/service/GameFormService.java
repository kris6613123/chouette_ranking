package com.chouette.rankingWeb.service;


import com.chouette.rankingWeb.dao.GameFormDAO;
import com.chouette.rankingWeb.vo.GameFormVO;
import com.chouette.rankingWeb.vo.GameVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameFormService {

    private final GameFormDAO gameFormDAO;
    @Transactional
    public void add( GameFormVO vo ) {
        gameFormDAO.add( vo );
    }

    @Transactional
    public void del( GameFormVO vo ) {
        gameFormDAO.del( vo );
    }

    public List<GameFormVO> getList() {
        return gameFormDAO.getList();
    }
}
