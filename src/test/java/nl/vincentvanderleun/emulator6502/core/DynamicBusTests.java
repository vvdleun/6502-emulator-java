package nl.vincentvanderleun.emulator6502.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.vincentvanderleun.emulator6502.core.memory.Memory;
import nl.vincentvanderleun.emulator6502.core.memory.Ram;
import nl.vincentvanderleun.emulator6502.core.memory.Rom;

public class DynamicBusTests {
	private final Cpu cpu = mock(Cpu.class);
	private final byte[] memory1Data = { 1, 2, 3 };
	private final Rom memory1 = new Rom(0, memory1Data);	// Covers bytes 0..2
	private final byte[] memory2Data = { 4, 5, 6 };
	private final Ram memory2 = new Ram(3, memory2Data);	// Covers bytes 3..5
	private final byte[] memory3Data = { 8, 9, 10 };
	private final Rom memory3 = new Rom(7, memory3Data); 	// Covers bytes 7..9 (byte 6 is not addressed)
	private final List<Memory> memory = new ArrayList<>();
	private final DynamicBus bus;

	public DynamicBusTests() {
		memory.add(memory1);
		memory.add(memory2);
		memory.add(memory3);

		bus = new DynamicBus(cpu, memory);
	}
	
	@Test
	public void shouldReadAllAddressableReadableBytes() {
		assertEquals(1, bus.read(0));	// memory1 instance
		assertEquals(2, bus.read(1));
		assertEquals(3, bus.read(2));

		assertEquals(4, bus.read(3));	// memory2 instance
		assertEquals(5, bus.read(4));
		assertEquals(6, bus.read(5));
		
		// address 6 is not addressed by any Memory instance...
		
		assertEquals(8, bus.read(7));	// memory3 instance
		assertEquals(9, bus.read(8));
		assertEquals(10, bus.read(9));
	}

	@Test
	public void shouldWriteToAllAddressableWritableBytes() {
		bus.write(3, (byte) 0);	// memory2 (only Ram) instance
		bus.write(4, (byte) 0);
		bus.write(5, (byte) 0);
	}

	@Test
	public void shouldThrowWhenWritingToReadOnlyBytes() {
		  assertThrows(IllegalStateException.class, () -> {
			  bus.write(0, (byte) 0);	// memory1 (Rom) instance...
		  });
		  assertThrows(IllegalStateException.class, () -> {
			  bus.write(1, (byte) 0);
		  });
		  assertThrows(IllegalStateException.class, () -> {
			  bus.write(2, (byte) 0);
		  });

		  assertThrows(IllegalStateException.class, () -> {
			  bus.write(7, (byte) 0);	// memory3 (Rom) instance...
		  });
		  assertThrows(IllegalStateException.class, () -> {
			  bus.write(8, (byte) 0);
		  });
		  assertThrows(IllegalStateException.class, () -> {
			  bus.write(9, (byte) 0);
		  });
	}
	
	@Test
	public void shouldThrowWhenReadingOrWritingUnaddressedMemory() {
		  assertThrows(IllegalStateException.class, () -> {
			  bus.read(6);
		  });
		  assertThrows(IllegalStateException.class, () -> {
			  bus.read(10);
		  });

		  assertThrows(IllegalStateException.class, () -> {
			  bus.write(6, (byte)0);
		  });
		  assertThrows(IllegalStateException.class, () -> {
			  bus.write(10, (byte)0);
		  });
	}
}
