import Essentials.Photo;
import Essentials.Voice;
import RawImplements.callEBS_sound;
import static org.junit.Assert.*;

import akka.actor.ActorRef;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class SecondActorTest {

    @Test
    public void receive() throws IOException, InterruptedException {
        BLOCK bl = new BLOCK();
        byte [] d=new byte[] { 0x00, 0x01, 0x02};
        bl.getsType().tell("HI", bl.simple());
        Thread.sleep(200);
        assertEquals(forTest.nothing(), getRes());
        bl.getsType().tell(new Voice(d, "1.wav"), bl.simple());
        Thread.sleep(200);
        assertEquals(forTest.voice(), getRes());
        bl.getsType().tell(new Photo(d, "1.jpg"), bl.simple());
        Thread.sleep(200);
        assertEquals(forTest.photo(), getRes());

        System.out.println("###################################");
        String fullpath = "/home/roland/Downloads/download/.build_l64/tests_data/S00000000001/fujimi_-10_dB_back_37-40-dB.wav";
        Voice v = new Voice(null, fullpath);
        Voice created = v.create(fullpath);

        bl.getsType().tell(created, bl.simple());

        Thread.sleep(200);
        bl.getsType().tell("HI", bl.simple());

        Thread.sleep(200);

    }

    public String getRes() throws IOException {
        return new String(Files.readAllBytes(Paths.get("outs")));
    }

    @Test
    public void mustPassed() throws InterruptedException {
        String fullpath = "/home/roland/Downloads/download/.build_l64/tests_data/S00000000001/fujimi_-10_dB_back_37-40-dB.wav";
        Voice v = new Voice(null, fullpath);
        Voice created = v.create(fullpath);
        BLOCK bl = new BLOCK();
        bl.getsType().tell(created, bl.simple());
        bl.getsType().tell("HI", bl.simple());
        Thread.sleep(200);
    }








}