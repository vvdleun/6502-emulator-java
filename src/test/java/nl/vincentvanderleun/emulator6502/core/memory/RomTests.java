package nl.vincentvanderleun.emulator6502.core.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RomTests {
	private final byte[] memory = { -128 , -127, -126, -125 };
	private final Rom rom = new Rom(0x8000, memory); // Range: $8000 to $8003

	@Test
	public void validRangeMustBeReadable() {
		assertEquals(-128, rom.read(0x8000));
		assertEquals(-127, rom.read(0x8001));
		assertEquals(-126, rom.read(0x8002));
		assertEquals(-125, rom.read(0x8003));
	}
	
	@Test
	public void readingBytesAsUnsignedBytesMustWork() {
		assertEquals(128, rom.readAsUnsignedByte(0x8000));
		assertEquals(129, rom.readAsUnsignedByte(0x8001));
		assertEquals(130, rom.readAsUnsignedByte(0x8002));
		assertEquals(131, rom.readAsUnsignedByte(0x8003));
	}
	
	@Test
	public void shouldThrowWhenReadingOutOfRangeAddress() {
		  assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
				rom.readAsUnsignedByte(0x8004);
		  });
	}
	
	@Test
	public void shouldReturnCorrectStartAndEndAddresses() {
		assertEquals(0x8000, rom.getStartAddress());
		assertEquals(0x8003, rom.getEndAddress());
		assertEquals(4, rom.getSize());
	}
	
	@Test
	public void shouldNotPickUpChangesMadeInPassedArray() {
		memory[0] = 42;
		assertEquals(-128, rom.read(0x8000));
	}
}
