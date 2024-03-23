package com.chouette.rankingWeb.service.std;

import com.chouette.rankingWeb.dao.std.StdTierDAO;
import com.chouette.rankingWeb.service.FileService;
import com.chouette.rankingWeb.service.UserService;
import com.chouette.rankingWeb.vo.std.StdTierVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StdTierService {
    private final StdTierDAO stdTierDAO;
    private final FileService fileService;
    private final UserService userService;

    @Transactional
    public void add( StdTierVO vo ) {
        stdTierDAO.add( vo );
    }

    @Transactional
    public void delAll() {
        stdTierDAO.delAll();
    }

    public StdTierVO getTypeSItem() {
        return stdTierDAO.getTypeSItem();
    }

    public StdTierVO getItemByCustomer( Integer customer ) {
        return stdTierDAO.getItemByCustomer( customer );
    }

    public List<StdTierVO> getList() {
        return stdTierDAO.getList();
    }

    public List<StdTierVO> getTypeRList() {
        return stdTierDAO.getTypeRList();
    }

    @Transactional
    public void save( List<StdTierVO> vo, List<Integer> notDelList, List<MultipartFile> fileList ) throws IOException {
        if( !notDelList.isEmpty() ) {
            fileService.delTierList( notDelList );  //delList가 del할 번호가 아니고 살려둘 번호임
        }
        delAll();
        int fileCount = 0;
        for ( StdTierVO v : vo ) {
            if ( v.getFileStatus().equals("add") ) {
                v.setFile( fileService.uploadTier( fileList.get( fileCount++ ) ) );
            }
            v.setRegUser( userService.getUser() );
            add( v );
        }
    }
//DuplicateKeyException key 중복될 때 에러발생.
    public boolean checkDuplicate( List<StdTierVO> vo ) {
        List<Double> weights = vo.stream()
                .map(StdTierVO::getWeight)
                .collect(Collectors.toList());
        return weights.size() != new HashSet<>(weights).size();
    }
}
