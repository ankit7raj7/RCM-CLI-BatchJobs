package com.techprimers.springbatchexample1.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUploadHelper {
	
	public final String UPLOAD_DIR="D:\\Study\\spring-batch-example-1\\src\\main\\resources\\UploadedData";
	//public final String UPLOAD_DIR=new ClassPathResource("/UploadedData").getFile().getAbsolutePath();
	
	public FileUploadHelper() throws IOException{
		
	}

	public boolean uploadFile(MultipartFile multipartFile) {
		 boolean f=false;
		 
		 
		 try {
			 InputStream is = multipartFile.getInputStream();
			 byte [] data= new byte[is.available()];
			 is.read(data);
			 
			 
			 //write
			 FileOutputStream fos= new FileOutputStream(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename());
			 fos.write(data);
			 
			 fos.flush();
			 fos.close();
			 is.close();
			 f=true;
		 }catch(Exception e) {
			 e.printStackTrace();
			 
		 }
		 return f;
	}

}
