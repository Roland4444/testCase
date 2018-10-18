package Essentials;

import org.junit.Test;

import static org.junit.Assert.*;

public class VoiceTest {

    @Test
    public void create() {
        String fullpath = "/home/roland/Downloads/download/.build_l64/tests_data/S00000000001/fujimi_-10_dB_back_37-40-dB.wav";
        Voice v = new Voice(null, fullpath);
        Voice created = v.create(fullpath);
        assertEquals(created.filename(), "fujimi_-10_dB_back_37-40-dB.wav" );
        assertTrue(created.wavContent().length>0);
        System.out.println(created.wavContent().length);
    }
}