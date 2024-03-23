package com.chouette.rankingWeb.service;

import com.chouette.rankingWeb.dao.file.BranchFileDAO;
import com.chouette.rankingWeb.dao.file.CustomerFileDAO;
import com.chouette.rankingWeb.dao.file.FileDAO;
import com.chouette.rankingWeb.dao.file.NewsFileDAO;
import com.chouette.rankingWeb.vo.file.CustomerFileVO;
import com.chouette.rankingWeb.vo.file.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final Environment env;
    private final FileDAO fileDAO;

    @Transactional
    public Integer add(FileVO vo) {
        fileDAO.add(vo);
        return vo.getFile();
    }

    @Transactional
    public void del( FileVO vo ) {
        fileDAO.del( vo );
    }


    @Transactional
    public void delTierList( List<Integer> delFiles ) {
        List<FileVO> fileList = getDelTierList( delFiles );
//        log.info("삭제할 파일 리스트: " + fileList);
        for (FileVO vo: fileList) {
            fileDAO.del( vo );
            deleteRealFile( vo, "tier" );
        }
    }

    public FileVO getItem( FileVO vo ) {
        return fileDAO.getItem( vo );
    }


    public List<FileVO> getDelTierList( List<Integer> delFiles ) {
        return fileDAO.getDelTierList( delFiles );
    }

    @Transactional
    public Integer uploadTier( MultipartFile file ) throws IOException {
        return upload( file, "tier" );
    }

    @Transactional
    public Integer upload( MultipartFile file, String type) throws IOException {
        FileVO vo = new FileVO();
        String extension = getExtension( file );
        String serverName = UUID.randomUUID() + "." + extension;
        String filePath = getFilePath(type, serverName);

        saveFile(file, filePath);

//        log.info("서버에" + type +  " 파일 저장 완료");
//        add( vo );
        return add( makeFile( vo, file.getOriginalFilename(), serverName, type ) );
    }

    private FileVO makeFile( FileVO vo, String name, String serverName, String path ) {
        vo.setName( name );
        vo.setServerName( serverName );
        vo.setPath( path );
        return vo;
    }

    private String getDir( String type ) {
        return env.getProperty( "file.save." + type );
    }

    private String getExtension( MultipartFile file ) {
        return StringUtils.getFilenameExtension( file.getOriginalFilename() );
    }
    private String getFilePath(String type, String serverName) {
        String folderPath = getDir("root") + File.separator + getDir( type );
        File folder = new File( folderPath );
        if( !folder.exists() ) {
            folder.mkdir();
        }
        return folderPath + File.separator + serverName;
    }

    @Transactional
    public void saveFile(MultipartFile file, String filePath) throws IOException {
        File newFile = new File(filePath);
        file.transferTo(newFile);
    }
    @Transactional
    public void deleteRealFile( FileVO vo, String type ) {
        String path = getFilePath( type, vo.getServerName() );
        File delFile = new File( path );
        if (delFile.exists()) {
            boolean s = delFile.delete();
//            log.info("del realFile success? :  " + s);
        }
    }


}
