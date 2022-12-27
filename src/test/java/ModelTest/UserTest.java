package ModelTest;

import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.utils.security.PasswordEncryption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void getAndSetTest() {
        User user = new User();
        assertEquals(0, user.getId());
        assertNull( user.getLogin());
        assertNull( user.getPassword());
        assertNull( user.getPhone());
        assertNull(  user.getFirstName());
        assertNull(  user.getLastName());

        user = User.newBuilder()
                .setId(1)
                .setLogin("Test")
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setPassword(PasswordEncryption.getEncrypted("password"))
                .setPhone("3802211111")
                .setRole(User.Role.USER)
                .build();

        assertEquals(1, user.getId());
        assertEquals("Test", user.getLogin());
        assertEquals( PasswordEncryption.getEncrypted("password"), user.getPassword());
        assertEquals( "3802211111", user.getPhone());
        assertEquals( "FirstName", user.getFirstName());
        assertEquals( "LastName", user.getLastName());
    }

    @Test
    void wrongInputTest() {
        User user = new User();
        assertThrows(IllegalArgumentException.class,
                () -> user.setId(-1));

    }


}
