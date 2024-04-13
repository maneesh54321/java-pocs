package com.example.hugefilestreamingheapanalysis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class FileStreamController {

	@Value("${file.stream.location}")
	private String fileLocation;

	@Value("${file.json.location}")
	private String jsonFileLocation;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/hello")
	public String sayHello(){
		return "Server: hello!!!!";
	}

	@GetMapping("/stream")
	public InputStreamResource stream(){
		FileInputStream fis;
		try {
			fis = new FileInputStream(fileLocation);
			return new InputStreamResource(fis);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping("/increase-load")
	public String increaseLoad(){
		try (FileInputStream fis = new FileInputStream(jsonFileLocation)) {
			JsonNode node = objectMapper.reader().readTree(fis);
			Thread.sleep(30000);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
		return "Load increased";
	}
}
