package nl.vincentvanderleun.emulator6502.core.memory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MirroredRamTests {
	private byte[] originalRamData = { 0, 1, 2, 3 };
	private Ram originalRam = new Ram(0, originalRamData);
	private MirrorredRam mirrorredRam = new MirrorredRam(10, originalRam);
	
	@Test
	public void shouldBeAbleToReadMirroredDataThatWasWrittenInitially() {
		assertEquals(0, mirrorredRam.read(10));
		assertEquals(1, mirrorredRam.read(11));
		assertEquals(2, mirrorredRam.read(12));
		assertEquals(3, mirrorredRam.read(13));
	}
	
	@Test
	public void shouldBeAbleToReadDataWrittenToMirroredRamAddresses() {
		mirrorredRam.writeAsUnsignedByte(10, 30);
		mirrorredRam.writeAsUnsignedByte(11, 20);
		mirrorredRam.writeAsUnsignedByte(12, 10);
		mirrorredRam.writeAsUnsignedByte(13, 0);
		
		// Test that both original RAM and mirrored RAM read back the correct values
		assertEquals(30, mirrorredRam.read(10));
		assertEquals(20, mirrorredRam.read(11));
		assertEquals(10, mirrorredRam.read(12));
		assertEquals(0, mirrorredRam.read(13));

		assertEquals(30, originalRam.read(0));
		assertEquals(20, originalRam.read(1));
		assertEquals(10, originalRam.read(2));
		assertEquals(0, originalRam.read(3));
	}

	@Test
	public void shouldBeAbleToReadDataWrittenToOriginalRamAddresses() {
		originalRam.writeAsUnsignedByte(0, 6);
		originalRam.writeAsUnsignedByte(1, 12);
		originalRam.writeAsUnsignedByte(2, 18);
		originalRam.writeAsUnsignedByte(3, 24);
		
		// Test that mirrored RAM can read back the correct values
		assertEquals(6, mirrorredRam.read(10));
		assertEquals(12, mirrorredRam.read(11));
		assertEquals(18, mirrorredRam.read(12));
		assertEquals(24, mirrorredRam.read(13));
	}
	
	@Test
	public void shouldThrowWhenReadingOrWritingOutOfRangeAddresses() {
		// Make sure shadow RAM cannot read outside its range
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRam.readAsUnsignedByte(14);
		});
		// Make sure shadow RAM cannot somehow read from original RAM addresses
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRam.readAsUnsignedByte(0);
		});
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRam.readAsUnsignedByte(3);
		});
		
		// Make sure shadow RAM cannot write outside its range
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRam.writeAsUnsignedByte(14, 0);
		});
		// Make sure shadow RAM cannot write to original RAM addresses
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRam.writeAsUnsignedByte(0, 0);
		});
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRam.writeAsUnsignedByte(3, 0);
		});
	}

	@Test
	public void shouldReturnCorrectStartAndEndAddresses() {
		assertEquals(10, mirrorredRam.getStartAddress());
		assertEquals(13, mirrorredRam.getEndAddress());
		assertEquals(4, mirrorredRam.getSize());
	}
}
