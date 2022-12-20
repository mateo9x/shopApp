package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.configuration.AdditionalAppProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Slf4j
@AllArgsConstructor
public class PhotoServiceImpl {

    private final AdditionalAppProperties appProperties;

    public byte[] getPhotoFromResourceFolder(String folderName, String fileName) {
        try {
            File image = new File(appProperties.getPhotoPathUrl() + folderName + "\\" + fileName);
            return Files.readAllBytes(image.toPath());
        } catch (Exception e) {
            try {
                File imageNotFound = ResourceUtils.getFile("classpath:404_photo.png");
                return Files.readAllBytes(imageNotFound.toPath());
            } catch (IOException ex) {
                log.error("Can't parse file path to byte[] !");
                return null;
            }
        }
    }

    public void saveMultipartFileInResourceFolder(String folderName, MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getResource().getFilename();
            File file = new File(appProperties.getPhotoPathUrl() + folderName + "\\" + fileName);
            if (!file.exists()) {
                file.mkdirs();
            }
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
