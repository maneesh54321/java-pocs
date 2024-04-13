package com.example.hugefilestreamingheapanalysis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HugeFileStreamingHeapAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HugeFileStreamingHeapAnalysisApplication.class, args);
	}

	@Bean
	public ObjectMapper mapper(){
		return new ObjectMapper();
	}

}
