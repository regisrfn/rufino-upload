package com.rufino.server;

import java.io.File;

import com.rufino.server.services.ApiServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

	@Autowired
	ApiServices apiServices;

	@Test
	void itShouldUpdateAFile() {

		File img = new File("tmp/fox-test.jpg");
		apiServices.uploadFile("images/" + img.getName(), img);

	}

}
