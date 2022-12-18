package com.mateo9x.shop.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Slf4j
public class PhotoServiceImpl {

    public static final String PATH_TO_PHOTOS_FOLDER = "C:\\Users\\Mateusz\\Images\\Shop-App\\";

    public byte[] getPhotoFromResourceFolder(String folderName, String fileName) {
        try {
           File image = new File(PATH_TO_PHOTOS_FOLDER + folderName + "\\" + fileName);
            return Files.readAllBytes(image.toPath());
        } catch (Exception e) {
            log.error("Can't parse file path to byte[] !");
            return null;
        }
    }

    public String saveMultipartFileInResourceFolder(String folderName, MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getResource().getFilename();
            File file = new File(PATH_TO_PHOTOS_FOLDER + folderName + "\\" + fileName);
            if (!file.exists()) {
                file.mkdirs();
            }
            multipartFile.transferTo(file);
            return fileName;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
