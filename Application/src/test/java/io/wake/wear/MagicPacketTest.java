package io.wake.wear;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MagicPacketTest {

    @Before
    public void setUp() throws Exception {
        mock(DatagramSocket.class);
    }

    @Test
    public void shouldCreateMagicPacket() {
        // Given
        MagicPacket packet = new MagicPacket("50:E5:49:BD:34:E2", MagicPacket.BROADCAST);

        // When
        byte[] magicPacket = packet.createMagicPacket();

        // Then
        assertEquals(102, magicPacket.length);
        assertEquals((byte) 0xFF, magicPacket[0]);
        assertEquals((byte) 0xFF, magicPacket[5]);
        assertEquals((byte) 0x50, magicPacket[6]);
        assertEquals((byte) 0xE2, magicPacket[magicPacket.length - 1]);
    }
}