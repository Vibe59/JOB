package com.jobconnect;

import com.jobconnect.model.User;
import com.jobconnect.repository.UserRepository;
import com.jobconnect.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    public void testRegisterUser_NewUser() {
        User user = new User("Vaibhavi", "vaibhavi@example.com", "password", "EMPLOYER");

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("Vaibhavi", result.getName());
    }

    @Test
    public void testRegisterUser_ExistingUser() {
        User existing = new User("Existing", "test@example.com", "pass", "EMPLOYER");

        Mockito.when(userRepository.findByEmail(existing.getEmail())).thenReturn(existing);

        User result = userService.registerUser(existing);
        assertNull(result);
    }
}
