package ExceptionTest;

import com.railway.ticket.office.webapp.exceptions.CommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommandExceptionTest {
    private final Exception e = new Exception();

    @Test
    void simpleTest() {
        Exception commandException = new CommandException();
        assertNotNull(commandException);
        String message = "error";
        Exception commandExc = new CommandException(message);
        assertEquals(message, commandExc.getMessage());
        Exception commandEx = new CommandException(message, e);
        assertEquals(message, commandEx.getMessage());
        assertEquals(e, commandEx.getCause());
    }

}
