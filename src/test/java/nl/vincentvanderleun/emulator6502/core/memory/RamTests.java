package nl.vincentvanderleun.emulator6502.core.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RamTests {
	private Ram ram = new Ram(0x8000, 16); // Range: $8000 to $800F

	@Test
	public void validRangeMustBeAddressable() {
		for(int i = 0; i <= 15; i++) {
			ram.writeAsUnsignedByte(0x8000 + i, i);
		}
		
		assertEquals(0, ram.readAsUnsignedByte(0x8000));
		assertEquals(1, ram.readAsUnsignedByte(0x8001));
		assertEquals(2, ram.readAsUnsignedByte(0x8002));
		assertEquals(3, ram.readAsUnsignedByte(0x8003));
		assertEquals(4, ram.readAsUnsignedByte(0x8004));
		assertEquals(5, ram.readAsUnsignedByte(0x8005));
		assertEquals(6, ram.readAsUnsignedByte(0x8006));
		assertEquals(7, ram.readAsUnsignedByte(0x8007));
		assertEquals(8, ram.readAsUnsignedByte(0x8008));
		assertEquals(9, ram.readAsUnsignedByte(0x8009));
		assertEquals(10, ram.readAsUnsignedByte(0x800A));
		assertEquals(11, ram.readAsUnsignedByte(0x800B));
		assertEquals(12, ram.readAsUnsignedByte(0x800C));
		assertEquals(13, ram.readAsUnsignedByte(0x800D));
		assertEquals(14, ram.readAsUnsignedByte(0x800E));
		assertEquals(15, ram.readAsUnsignedByte(0x800F));
	}
	
	@Test
	public void unsignedByteConversionShouldWork() {
		// Test writing unsigned byte and reading as signed byte
		
		ram.writeAsUnsignedByte(0x8000, 246);

		byte actualSignedByte = ram.read(0x8000);
		byte expectedSignedByte = -10;

		assertEquals(expectedSignedByte, actualSignedByte);

		// Test writing signed byte and reading unsigned byte

		ram.write(0x8000, (byte)-11);

		int actualUnsignedByte = ram.readAsUnsignedByte(0x8000);
		int expectedUnsignedByte = 245;
		
		assertEquals(expectedUnsignedByte, actualUnsignedByte);
	}
	
	@Test
	public void shouldThrowWhenReadingOrWritingOutOfRangeAddresses() {
		  assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
				ram.readAsUnsignedByte(0x8010);
		  });
		  assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
				ram.writeAsUnsignedByte(0x8010, 0);
		  });
	}
	
	@Test
	public void shouldReturnCorrectStartAndEndAddresses() {
		assertEquals(0x8000, ram.getStartAddress());
		assertEquals(0x800F, ram.getEndAddress());
		assertEquals(16, ram.getSize());
	}
}
