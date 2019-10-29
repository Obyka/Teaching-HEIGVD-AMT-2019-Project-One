package ch.heig.amt.project.one.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    private final String username = "admin";
    private final String password = "admin1234";
    private final long id = 1890000;

    @Test
    void itShouldBePossibleToCreateAUser() {
        User user = User.builder()
            .username(username)
            .password(password)
                .id(id)
                .build();

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(id, user.getId());
    }

    @Test
    void itShouldBePossibleToSetAUser() {
        User user = User.builder().build();

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(id, user.getId());
    }

    @Test
    void itShouldBePossibleToHaveTwoUsersEqual() {
        User user = User.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();

        User user2 = User.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();

        assertEquals(true, user.equals(user2));
    }
}
