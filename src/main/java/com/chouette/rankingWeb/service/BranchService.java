package com.chouette.rankingWeb.service;

import com.chouette.rankingWeb.dao.BranchDAO;
import com.chouette.rankingWeb.dao.file.BranchFileDAO;
import com.chouette.rankingWeb.vo.BranchVO;
import com.chouette.rankingWeb.vo.file.BranchFileVO;
import com.chouette.rankingWeb.vo.file.CustomerFileVO;
import com.chouette.rankingWeb.vo.file.FileVO;
import com.chouette.rankingWeb.vo.std.StdWinningRewardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchService {

    private final BranchDAO branchDAO;
    private final BranchFileDAO branchFileDAO;

    private final FileService fileService;

    @Transactional
    public void add( BranchVO vo ) {
        branchDAO.add( vo );
    }

    @Transactional
    public void add( BranchFileVO vo ) {
        branchFileDAO.add( vo );
    }

    @Transactional
    public void mod( BranchVO vo ) {
        branchDAO.mod( vo );
    }

    @Transactional
    public void del( BranchVO vo, String type ) {
        branchDAO.del( vo );
    }

    @Transactional
    public void del( BranchVO vo ) {
        branchDAO.del( vo );
    }

    @Transactional
    public void del( BranchFileVO vo ) {
        BranchFileVO branchFile = getItem( vo );
        if( branchFile != null ) {
            FileVO file = new FileVO( branchFile.getFile() );
            fileService.deleteRealFile( fileService.getItem( file ), "branch" );
            fileService.del( file );
            branchFileDAO.del( branchFile );
        }
    }

    public BranchVO getItem( BranchVO vo ) {
        return branchDAO.getItem( vo );
    }

    public BranchFileVO getItem( BranchFileVO vo ) {
        return branchFileDAO.getItem( vo );
    }

    public List<BranchVO> getList() {
        return branchDAO.getList();
    }

    public List<BranchVO> getAllList() {
        return branchDAO.getAllList();
    }

    public List<BranchVO> getListIncludeFile() {
        return branchDAO.getListIncludeFile();
    }

    public List<BranchVO> getOffList() {
        return branchDAO.getOffList();
    }

    @Transactional
    public void save( BranchVO vo, MultipartFile file, MultipartFile fileLogo ) throws IOException {
        if ( vo.getBranch() != null ) {
            mod( vo );
            if ( vo.getFile() == null ) {               //기존에 파일이 존재하지 않았거나 존재했는데 다른 사진으로 바뀌었거나 기본이미지를 클릭했거나.
                BranchFileVO branchFile = new BranchFileVO();
                branchFile.setBranch( vo.getBranch() );
                branchFile.setType("BRANCH");
                del( branchFile );
            }
            if ( vo.getFileLogo() == null ) {
                BranchFileVO branchFile = new BranchFileVO();
                branchFile.setBranch( vo.getBranch() );
                branchFile.setType("LOGO");
                del( branchFile );
            }
        }
        else {
            add( vo );
        }

        if ( file != null && !file.isEmpty() ) {
            add( new BranchFileVO( fileService.upload( file, "branch" ), vo.getBranch(), "BRANCH" ) );
        }
        if ( fileLogo != null && !fileLogo.isEmpty() ) {
            add( new BranchFileVO( fileService.upload( fileLogo, "branch" ), vo.getBranch(), "LOGO" ) );
        }
    }

}
