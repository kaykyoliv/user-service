package com.kayky.controller;

import com.kayky.commons.FileUtils;
import com.kayky.commons.UserUtils;
import com.kayky.domain.User;
import com.kayky.repository.UserData;
import com.kayky.repository.UserHardCodedRepository;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(controllers = UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"com.kayky"})
class UserControllerTest {
    private static final String URL = "/v1/users";
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserData userData;
    @MockitoSpyBean
    private UserHardCodedRepository repository;
    private List<User> userList;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private UserUtils userUtils;

    @BeforeEach
    void init() {
        userList = userUtils.newUserList();
    }

    @Test
    @DisplayName("GET v1/users returns a list with all users when argument is null")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        var response = fileUtils.readResourceFile("user/get-user-null-first-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/users?firstName=Toyohisa returns list with found object when first name exists")
    @Order(2)
    void findAll_ReturnsFoundUserInList_WhenFirstNameIsFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        var response = fileUtils.readResourceFile("user/get-user-toyohisa-first-name-200.json");
        var firstName = "Toyohisa";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/users?firstName=x returns empty list when first name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenFirstNameIsNotFound() throws Exception {
        BDDMockito.when(userData.getUsers()).thenReturn(userList);
        var response = fileUtils.readResourceFile("user/get-user-x-first-name-200.json");
        var firstName = "x";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("firstName", firstName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }
}