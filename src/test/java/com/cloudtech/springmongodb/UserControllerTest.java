package com.cloudtech.springmongodb;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository repository;

    @Test
    public void getAllTest() throws Exception {
        List<User> result = Arrays.asList(
                new User("5eb6d80332d96054ee993635", "firstName0", "lastName0", "email0", "password0", Arrays.asList(
                        new Course("CourseName0", 123l),
                        new Course("CourseName1", 234l),
                        new Course("CourseName2", 345l))),
                new User("5eb6d80332d96054ee993636", "firstName1", "lastName1", "email1", "password1", Arrays.asList(
                        new Course("CourseName1", 123l),
                        new Course("CourseName3", 234l))),
                new User("5eb6d80332d96054ee993637", "firstName2", "lastName2", "email2", "password2", Arrays.asList(
                        new Course("CourseName1", 123l))),
                new User("5eb6d80332d96054ee993638", "firstName3", "lastName3", "email3", "password3", Arrays.asList()));
        when(repository.findAll()).thenReturn(result);
        mockMvc.perform(get("/user/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"5eb6d80332d96054ee993635\",\"firstName\":\"firstName0\",\"lastName\":\"lastName0\",\"email\":\"email0\",\"password\":\"password0\",\"courseList\": [{\"id\": null,\"name\":\"CourseName0\",\"price\": 123},{\"id\": null,\"name\":\"CourseName1\",\"price\": 234},{\"id\": null,\"name\":\"CourseName2\",\"price\": 345}]},{\"id\":\"5eb6d80332d96054ee993636\",\"firstName\":\"firstName1\",\"lastName\":\"lastName1\",\"email\":\"email1\",\"password\":\"password1\",\"courseList\": [{\"id\": null,\"name\":\"CourseName1\",\"price\": 123},{\"id\": null,\"name\":\"CourseName3\",\"price\": 234}]},{\"id\":\"5eb6d80332d96054ee993637\",\"firstName\":\"firstName2\",\"lastName\":\"lastName2\",\"email\":\"email2\",\"password\":\"password2\",\"courseList\": [{\"id\": null,\"name\":\"CourseName1\",\"price\": 123}]},{\"id\":\"5eb6d80332d96054ee993638\",\"firstName\":\"firstName3\",\"lastName\":\"lastName3\",\"email\":\"email3\",\"password\":\"password3\",\"courseList\":[]}]"))
                .andExpect(jsonPath("$.*", Matchers.hasSize(4)))
                .andExpect(jsonPath("$.[0].*", Matchers.hasSize(6)))
                .andExpect(jsonPath("$.[0].id", Matchers.is("5eb6d80332d96054ee993635")))
                .andExpect(jsonPath("$.[0].firstName", Matchers.is("firstName0")))
                .andExpect(jsonPath("$.[0].lastName", Matchers.is("lastName0")))
                .andExpect(jsonPath("$.[0].email", Matchers.is("email0")))
                .andExpect(jsonPath("$.[0].password", Matchers.is("password0")))
                .andExpect(jsonPath("$.[0].courseList.*", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].courseList.[0].*", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].courseList.[0].id", Matchers.emptyOrNullString()))
                .andExpect(jsonPath("$.[0].courseList.[0].name", Matchers.is("CourseName0")))
                .andExpect(jsonPath("$.[0].courseList.[0].price", Matchers.is(123)))
                .andExpect(jsonPath("$.[1].courseList.*", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[2].courseList.*", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[3].courseList.*", Matchers.hasSize(0)));
    }

    @Test
    public void getByIdTest() throws Exception {
        User user = new User("5eb69c37af26a064b69ad0d0", "firstName0", "lastName0", "email0", "password0", Arrays.asList(
                new Course("CourseName0", 123l),
                new Course("CourseName1", 234l),
                new Course("CourseName2", 345l)));
        Optional<User> result = Optional.of(user);
        when(repository.findById("5eb69c37af26a064b69ad0d0")).thenReturn(result);
        this.mockMvc.perform(get("/user/5eb69c37af26a064b69ad0d0").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"5eb69c37af26a064b69ad0d0\",\"firstName\":\"firstName0\",\"lastName\":\"lastName0\",\"email\":\"email0\",\"password\":\"password0\",\"courseList\":[{\"id\":null,\"name\":\"CourseName0\",\"price\":123},{\"id\":null,\"name\":\"CourseName1\",\"price\":234},{\"id\":null,\"name\":\"CourseName2\",\"price\":345}]}"))
                .andExpect(jsonPath("$.*", Matchers.hasSize(6)))
                .andExpect(jsonPath("$.id", Matchers.is("5eb69c37af26a064b69ad0d0")))
                .andExpect(jsonPath("$.firstName", Matchers.is("firstName0")))
                .andExpect(jsonPath("$.lastName", Matchers.is("lastName0")))
                .andExpect(jsonPath("$.email", Matchers.is("email0")))
                .andExpect(jsonPath("$.password", Matchers.is("password0")))
                .andExpect(jsonPath("$.courseList.*", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.courseList.[0].*", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.courseList.[0].id", Matchers.emptyOrNullString()))
                .andExpect(jsonPath("$.courseList.[0].name", Matchers.is("CourseName0")))
                .andExpect(jsonPath("$.courseList.[0].price", Matchers.is(123)));
    }
}