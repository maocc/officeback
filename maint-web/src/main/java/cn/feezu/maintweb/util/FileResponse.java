package cn.feezu.maintweb.util;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
/**
 * 下载文件
 * @author nalis
 *
 */
public class FileResponse {
	
	public static ResponseEntity<InputStreamResource> back(FileSystemResource file) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",
				new String(file.getFilename().getBytes("UTF-8"), "ISO8859-1")));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
	}

}

