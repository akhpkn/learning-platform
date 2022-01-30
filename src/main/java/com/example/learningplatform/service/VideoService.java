package com.example.learningplatform.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.learningplatform.model.Video;
import com.example.learningplatform.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final FileRepository fileRepository;

    private final AmazonS3 awsS3Client;

    @Value("${jsa.s3.bucket}")
    private String bucketName;

    private static final String ENDPOINT_URL = "https://lacbucket.s3.eu-west-2.amazonaws.com";

    public Video store(MultipartFile multipartFile) throws IOException {
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));

        String fileName = generateFileName(multipartFile.getOriginalFilename());

        String url = upload(fileName + extension, multipartFile);
        Video video = new Video(url, multipartFile.getContentType());

        return fileRepository.save(video);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String upload(String fileName, MultipartFile multipartFile) throws IOException {
        String fileUrl = ENDPOINT_URL + "/videos/" + fileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        awsS3Client.putObject(new PutObjectRequest(bucketName, "videos/" + fileName,
                multipartFile.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return fileUrl;
    }

    public void deleteVideo(Video video) {
        String key = video.getUrl().substring(ENDPOINT_URL.length() + 1);
        fileRepository.delete(video);
        awsS3Client.deleteObject(bucketName, key);
    }

    public Video saveUrl(String url) {
        Video video = new Video(url, "empty");
        fileRepository.save(video);
        return video;
    }

    private String generateFileName(String originalName) {
        return DigestUtils.md5Hex(originalName + LocalDateTime.now());
    }

    public Video getFile(Long fileId) {
        return (Video) fileRepository.findByFileId(fileId);
    }
}