package nl.vincentvanderleun.emulator6502.core.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ZeroRomTests {
	private final ZeroRom rom = new ZeroRom(0x8000, 4); // Range: $8000 to $8003

	@Test
	public void validRangeMustBeReadable() {
		assertEquals(0, rom.read(0x8000));
		assertEquals(0, rom.read(0x8001));
		assertEquals(0, rom.read(0x8002));
		assertEquals(0, rom.read(0x8003));
	}
	
	@Test
	public void readingBytesAsUnsignedBytesMustWork() {
		assertEquals(0, rom.readAsUnsignedByte(0x8000));
		assertEquals(0, rom.readAsUnsignedByte(0x8001));
		assertEquals(0, rom.readAsUnsignedByte(0x8002));
		assertEquals(0, rom.readAsUnsignedByte(0x8003));
	}
	
	@Test
	public void shouldThrowWhenReadingOutOfRangeAddress() {
		  assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
				rom.read(0x8004);
		  });
	}
	
	@Test
	public void shouldReturnCorrectStartAndEndAddresses() {
		assertEquals(0x8000, rom.getStartAddress());
		assertEquals(0x8003, rom.getEndAddress());
		assertEquals(4, rom.getSize());
	}
}
