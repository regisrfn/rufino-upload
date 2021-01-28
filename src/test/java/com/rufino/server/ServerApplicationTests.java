package com.rufino.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import com.rufino.server.services.ApiServices;
import com.rufino.server.services.FilesStorageService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class ServerApplicationTests {

	@Autowired
	ApiServices apiServices;

	@Autowired
	FilesStorageService fileService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	void itShouldUpdateAFile() {
		File img = new File("tmp/fox-test.jpg");
		apiServices.uploadFile("images/" + img.getName(), img);

	}

	@Test
	void itShouldCreateFolder() {
		fileService.deleteAll();
		fileService.init();
	}

	@Test
	public void whenFileUploaded_thenVerifyStatus() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/api/v1/upload").file(file)).andExpect(status().isOk());
	}

}
