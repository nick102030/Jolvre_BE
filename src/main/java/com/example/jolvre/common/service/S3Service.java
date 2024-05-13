package com.example.jolvre.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.jolvre.common.error.common.FileNotUploadException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile multipart) {
        verifyExtension(multipart);
        
        try {
            String originalFilename = multipart.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipart.getSize());
            metadata.setContentType(multipart.getContentType());

            amazonS3.putObject(bucket, originalFilename, multipart.getInputStream(), metadata);
            return amazonS3.getUrl(bucket, originalFilename).toString();
        } catch (IOException e) {
            throw new FileNotUploadException();
        }

    }

    public List<String> uploadImages(List<MultipartFile> multiparts) {
        List<String> urls = new ArrayList<>();
        multiparts.forEach(
                multipart -> urls.add(uploadImage(multipart))
        );
        return urls;
    }

    public void verifyExtension(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();

        // 확장자가 jpeg, png인 파일들만 받아서 처리
        if (ObjectUtils.isEmpty(contentType) | (!Objects.requireNonNull(contentType).contains("image/jpeg")
                & !contentType.contains(
                "image/png"))) {
            throw new FileNotUploadException();
        }
    }
}
