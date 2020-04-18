package nl.vincentvanderleun.emulator6502.core.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ContiniousMemoryBuilderTests {
	private ContiniousMemoryBuilder builder = new ContiniousMemoryBuilder();
	private List<Memory> memory;
	
	public ContiniousMemoryBuilderTests() {
		byte[] ramIndex1Data = { 1, 2, 3 };
		byte[] romIndex2Data = { 4, 5, 6 };
		
		memory = builder.addRamMemory(2)	// index 0: 0..1
			.addRamMemory(ramIndex1Data)	// index 1: 2..4
			.addRomMemory(romIndex2Data)	// index 2: 5..7
			.addMirroredRam(1)				// index 3: 8..10
			.skipBytes(2)					// <not addressed>: 11..12
			.addMirroredRom(2)				// index 4: 13..15
			.build();
	}
	
	@Test
	public void builtMemoryMustBeContiniousOfAddedTypesAndContainData() {
		assertEquals(5, memory.size());
		
		assertEquals(0, memory.get(0).getStartAddress());
		assertEquals(1, memory.get(0).getEndAddress());
		assertTrue(memory.get(0) instanceof Ram);

		assertEquals(2, memory.get(1).getStartAddress());
		assertEquals(4, memory.get(1).getEndAddress());
		assertTrue(memory.get(1) instanceof Ram);

		assertEquals(5, memory.get(2).getStartAddress());
		assertEquals(7, memory.get(2).getEndAddress());
		assertTrue(memory.get(2) instanceof Rom);

		assertEquals(8, memory.get(3).getStartAddress());
		assertEquals(10, memory.get(3).getEndAddress());
		assertTrue(memory.get(3) instanceof MirrorredRam);

		// addresses 11 and 12 were skipped
		
		assertEquals(13, memory.get(4).getStartAddress());
		assertEquals(15, memory.get(4).getEndAddress());
		assertTrue(memory.get(4) instanceof MirrorredRom);
	}
}
