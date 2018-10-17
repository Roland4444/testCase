import Essentials.Photo;
import Essentials.Voice;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SecondActorTest {

    @org.junit.Test
    public void receive() throws IOException, InterruptedException {
        BLOCK bl = new BLOCK();
        bl.getsType().tell("HI", bl.getsType());
        Thread.sleep(200);
        assertEquals(forTest.nothing(), getRes());
        bl.getsType().tell(new Voice(null), bl.getsType());
        Thread.sleep(200);
        assertEquals(forTest.voice(), getRes());
        bl.getsType().tell(new Photo(null), bl.getsType());
        Thread.sleep(200);
        assertEquals(forTest.photo(), getRes());

    }

    public String getRes() throws IOException {
        return new String(Files.readAllBytes(Paths.get("outs")));
    }
}