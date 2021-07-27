package com.techprimers.springbatchexample1.controller;


import com.techprimers.springbatchexample1.utils.FileUploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class FileUploadController {
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	@PostMapping("/upload-file")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
		
		try{
		
		if(file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request must have a file");
		}
		else {
			System.out.println(file.getName());
			System.out.println(file.getContentType());
			System.out.println(file.getSize());
			boolean f=fileUploadHelper.uploadFile(file);
			
			if(f) {
				return ResponseEntity.ok("File uploaded successfully");
			}
			else {
				System.out.println("Error");
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	}

}
