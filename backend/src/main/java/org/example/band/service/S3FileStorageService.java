package org.example.band.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService {

	private final AmazonS3 amazonS3;
	private final RestTemplate restTemplate;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Override
	public String uploadFile(MultipartFile file) {
		String fileName = generateFileName(file);
		File fileObj = convertMultiPartFileToFile(file);
		try {
			amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
		} catch (Exception e) {
			throw new RuntimeException("Error uploading file to S3", e);
		} finally {
			// 임시 파일 삭제
			fileObj.delete();
		}
		return amazonS3.getUrl(bucketName, fileName).toString();
	}

	@Override
	public void deleteFile(String fileUrl) {
		String fileName = extractFileNameFromUrl(fileUrl);
		amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
	}

	private File convertMultiPartFileToFile(MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Error converting MultipartFile to File", e);
		}
		return convFile;
	}

	private String generateFileName(MultipartFile file) {
		return System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
	}

	private String extractFileNameFromUrl(String fileUrl) {
		return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	}
}
