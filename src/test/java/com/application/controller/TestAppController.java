package com.application.controller;

import com.application.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(AppController.class)
public class TestAppController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAllUsers() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/app/service1/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("admin"));

    }

    @Test
    public void testUserById() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/app/service1/users/{userId}","admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"));
    }

    @Test
    public void testDeleteUserById() throws Exception{
        mvc.perform(MockMvcRequestBuilders.delete("/app/service1/users/{userId}","admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testRegisterUser() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/app/service1/users")
            .content(jsonObject())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    public String jsonObject() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("rambow","pas$w0rd");
        return mapper.writeValueAsString(user);
    }

}
