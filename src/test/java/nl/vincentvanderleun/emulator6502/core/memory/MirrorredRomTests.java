package nl.vincentvanderleun.emulator6502.core.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MirrorredRomTests {
	private byte[] originalRomData = { 0, 1, 2, 3 };
	private Rom originalRom = new Rom(0, originalRomData);
	private MirrorredRom mirrorredRom = new MirrorredRom(10, originalRom);
	
	@Test
	public void shouldBeAbleToReadMirroredDataThatWasWrittenInitially() {
		assertEquals(0, mirrorredRom.read(10));
		assertEquals(1, mirrorredRom.read(11));
		assertEquals(2, mirrorredRom.read(12));
		assertEquals(3, mirrorredRom.read(13));
	}
	
	@Test
	public void shouldThrowWhenReadingOrWritingOutOfRangeAddresses() {
		// Make sure shadow RAM cannot read outside its range
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRom.readAsUnsignedByte(14);
		});
		// Make sure shadow RAM cannot somehow read from original RAM addresses
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRom.readAsUnsignedByte(0);
		});
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			mirrorredRom.readAsUnsignedByte(3);
		});
	}

	@Test
	public void shouldReturnCorrectStartAndEndAddresses() {
		assertEquals(10, mirrorredRom.getStartAddress());
		assertEquals(13, mirrorredRom.getEndAddress());
		assertEquals(4, mirrorredRom.getSize());
	}
}
