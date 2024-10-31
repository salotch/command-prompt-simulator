import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
// import static org.junit.jupiter.api.Assertions.*;
// import org.junit.jupiter.api.*;

public class PwdCommandTest {
    @Test
    void path(){
        PwdCommand p=new PwdCommand();
        String[] args = {};
        assertEquals(System.getProperty("user.dir"), p.execute(args));
    }
}
