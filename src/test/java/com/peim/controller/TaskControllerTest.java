package com.peim.controller;

import com.peim.model.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaskControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private MockHttpSession session;

	@Autowired
	private MockHttpServletRequest request;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void getAllTaskTest() {
	}

	@Test
	public void getTaskByIdTest() throws Exception {
		mockMvc
				.perform(get("/api/task/id/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void addTaskTest() throws Exception {
		Task newTask = new Task(16, "testFile", "md5", "NEW", null);
		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.post("/api/task/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJson(newTask));

		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("id").value(newTask.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("src").value(newTask.getSrc()))
				.andExpect(MockMvcResultMatchers.jsonPath("algo").value(newTask.getAlgo()))
				.andExpect(MockMvcResultMatchers.jsonPath("status").value(newTask.getStatus()));
	}

	@Test
	public void updateTaskTest() throws Exception {
		Task newTask = new Task(1, "testFile", "md5", "NEW", null);
		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.put("/api/task/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJson(newTask));

		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void deleteTaskTest() throws Exception {
		mockMvc
				.perform(delete("/api/task/delete/1"))
				.andExpect(status().isOk());
	}

	private static String getJson(Task task) {
		return "{ \"id\":" + task.getId() + "," +
				"\"src\":\"" + task.getSrc() + "\"," +
				"\"algo\":\"" + task.getAlgo() + "\"," +
				"\"status\":\"" + task.getStatus() + "\"," +
				"\"description\":\"" + task.getDescription() + "\"}";
	}
}
