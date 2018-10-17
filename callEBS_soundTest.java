import RawImplements.callEBS_sound;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class callEBS_soundTest {

    @Test
    public void call_ebs() throws InterruptedException {
        callEBS_sound cebs = new callEBS_sound();
        callEBS_sound.CLibrary.ResultCheck rc = new callEBS_sound.CLibrary.ResultCheck();
        rc = cebs.call_ebs("./cv_configuration.json", "./5.wav");

        assertEquals(rc.lastErrorInSession, 0);
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