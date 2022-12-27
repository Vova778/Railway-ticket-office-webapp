package ExceptionTest;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBExceptionTest {
    private final Exception e = new Exception();

    @Test
    void simpleTest() {
        String message = "error";
        Exception daoEx = new DAOException(message, e);
        assertEquals(message, daoEx.getMessage());
        assertEquals(e, daoEx.getCause());
    }
}
