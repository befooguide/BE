package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Role;
import com.befoo.befoo.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("닉네임 중복 확인 - 사용 가능한 닉네임")
    void isUsernameAvailable_Available() {
        // given
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // when
        boolean result = userService.isUsernameAvailable(username);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복 확인 - 이미 사용 중인 닉네임")
    void isUsernameAvailable_AlreadyExists() {
        // given
        String username = "existingUser";
        User existingUser = User.builder()
                .username(username)
                .email("test@test.com")
                .role(Role.ROLE_USER)
                .build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        // when
        boolean result = userService.isUsernameAvailable(username);

        // then
        assertThat(result).isFalse();
    }
} 