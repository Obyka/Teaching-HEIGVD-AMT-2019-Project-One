package ch.heig.amt.project.one.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    private final String username = "admin";
    private final String password = "admin1234";
    private final long id = 1890000;

    User HelperCreateUser() {
        User user = User.builder()
                .username(username)
                .password(password)
                .id(id)
                .build();

        return user;
    }

    void HelperAssertUser(User user) {
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(id, user.getId());
    }

    @Test
    void itShouldBePossibleToCreateAUser() {
        User user = HelperCreateUser();
        HelperAssertUser(user);
    }

    @Test
    void itShouldBePossibleToSetAUser() {
        User user = User.builder().build();

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        HelperAssertUser(user);
    }

    @Test
    void itShouldBePossibleToHaveTwoUsersEqual() {
        User user = HelperCreateUser();
        User user2 = HelperCreateUser();

        assertEquals(true, user.equals(user2));
    }
}
