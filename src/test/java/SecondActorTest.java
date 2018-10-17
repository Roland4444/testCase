import Essentials.Photo;
import Essentials.Voice;
import RawImplements.callEBS_sound;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SecondActorTest {

    @Test
    public void receive() throws IOException, InterruptedException {
        BLOCK bl = new BLOCK();
        bl.getsType().tell("HI", bl.getsType());
        Thread.sleep(200);
        assertEquals(forTest.nothing(), getRes());
        bl.getsType().tell(new Voice(null, "1.wav"), bl.getsType());
        Thread.sleep(200);
        assertEquals(forTest.voice(), getRes());
        bl.getsType().tell(new Photo(null, "1.jpg"), bl.getsType());
        Thread.sleep(200);
        assertEquals(forTest.photo(), getRes());

    }

    public String getRes() throws IOException {
        return new String(Files.readAllBytes(Paths.get("outs")));
    }



    @Test
    public void call_ebs() throws InterruptedException {
        callEBS_sound cebs = new callEBS_sound();
        callEBS_sound.CLibrary.ResultCheck rc = new callEBS_sound.CLibrary.ResultCheck();
        rc = cebs.call_ebs("./cv_configuration.json", "./5.wav");

        assertEquals(rc.lastErrorInSession, 0);
        assertEquals(rc.ResultLoadingSoSymbols, 0);
        printEBS(callEBS_sound.CLibrary.INSTANCE.lets_check("./cv_configuration.json", "./5.wav"));
        printEBS(callEBS_sound.CLibrary.INSTANCE.lets_check("./cv_configuration.json", "./a2002011001_e02_16kHz.wav.wav"));
        printEBS(callEBS_sound.CLibrary.INSTANCE.lets_check("./cv_configuration.json000", "./5.wav"));
    }

    void printEBS(callEBS_sound.CLibrary.ResultCheck rc){
        System.out.println("\n\nPROC RET    "+rc.lastErrorInSession);
        System.out.println("CHECK  =    "+rc.checkResult);
        System.out.println("INCALL      "+rc.ResultLoadingSoSymbols+"\n\n");

    }

    @Test
    public void call_ebs1() {
        callEBS_sound ebs = new callEBS_sound();
        assertNotEquals(null, ebs);
    }





}