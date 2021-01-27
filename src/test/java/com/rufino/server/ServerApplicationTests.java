package com.rufino.server;

import java.io.File;

import com.rufino.server.services.AwsServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

	@Autowired
	AwsServices aws;

	@Test
	void itShouldUpdateAFile() {

		File img = new File("tmp/fox-test.jpg");
		aws.uploadFileToS3("images/" + img.getName(), img);

	}

}
