package com.kayky.repository;

import com.kayky.commons.UserUtils;
import com.kayky.domain.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserHardCodedRepositoryTest {

    @InjectMocks
    private UserHardCodedRepository repository;

    @Mock
    private UserData userData;

    @InjectMocks
    private UserUtils userUtils;
    private List<User> userList;

    @BeforeEach
    void init(){
        userList = userUtils.newUserList();
    }

    @Test
    @DisplayName("findAll returns a list with all users")
    @Order(1)
    void findAll_ReturnsAllUsers_WhenSuccessful(){
        BDDMockito.when(userData.getUsers()).thenReturn(userList);

        var users = repository.findAll();
        assertThat(users).isNotNull().hasSameElementsAs(userList);
    }

}