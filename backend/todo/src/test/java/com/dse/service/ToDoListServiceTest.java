package com.dse.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.todo.model.ToDoList;
import com.todo.service.ToDoListService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringTestConfig.class })
class ToDoListServiceTest {
	@Autowired
	private ToDoListService service;

	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;

	@BeforeEach
	public void init() {
		mockServer = MockRestServiceServer.createServer(this.restTemplate);
	}

	@Test
	void notifyMs1Test() throws JsonProcessingException, URISyntaxException {
		mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/todo/123")))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body("test"));

		String result = this.service.notifyMs1("123");
		mockServer.verify();
		assertEquals("test", result);
	}

	@Test
	void notifyMs1PutTest() throws JsonProcessingException, URISyntaxException {
		ToDoList list = new ToDoList("user", "123", null, "test");
		mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8080/todo/" + list.getId())))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body("test"));

		String result = this.service.notifyMs1(list);
		mockServer.verify();
		assertEquals("test", result);
	}

}
