package com.rufino.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rufino.server.services.ApiServices;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ServerApplicationTests {

	@Autowired
	ApiServices apiServices;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void itShouldUploadAFile() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());

		mockMvc.perform(multipart("/api/v1/upload").file(file)).andExpect(status().isOk());
	}

	@Test
	void itShouldUploadAFile_base64() throws Exception {
		JSONObject my_obj = new JSONObject();

		my_obj.put("name", "test.txt");
		my_obj.put("contentType", "application/text");
		my_obj.put("size", "15");
		my_obj.put("encodedString", "aGVsbG8gd29yZCB0ZXN0");

		mockMvc.perform(post("/api/v1/upload/base64").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
				.andExpect(status().isOk()).andReturn();

	}

}
