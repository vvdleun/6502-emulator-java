package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import nl.vincentvanderleun.emulator6502.core.Bus;
import nl.vincentvanderleun.emulator6502.core.Cpu;
import nl.vincentvanderleun.emulator6502.core.Memory;
import nl.vincentvanderleun.emulator6502.core.bus.DynamicBus;
import nl.vincentvanderleun.emulator6502.core.memory.Ram;

class StackTests {
	private final Registers registers = new Registers();
	private final Ram ram = new Ram(0x100, 256);
	private Stack stack;
	
	@BeforeEach
	public void setup() {
		Cpu cpu = Mockito.mock(Cpu.class);
		List<Memory> memory = Collections.singletonList(ram);
		Bus bus = new DynamicBus(cpu, memory);

		stack = new Stack(registers, bus);

		// SP is not initialized by Stack implementation
		assertEquals(0x00, registers.getSp());
		registers.setSp(0xff);
	}
	
	@Test
	public void pushShouldDecreaseSp() {
		stack.push((byte)1);
		assertEquals(0xFE, registers.getSp());
	}
	
 	@Test
	public void popShouldIncreaseSp() {
		stack.push((byte)1); 	// Now: sp=FE
		stack.push((byte)2); 	// Now: sp=FD
		stack.push((byte)3); 	// Now: sp=FC
		stack.pop();			// Now: sp=FD
		assertEquals(0xFD, registers.getSp());
	}
 	
 	@Test
 	public void shouldPopPushedValues() {
 		stack.push((byte)123);
 		stack.push((byte)42);

 		int actual = stack.pop();
 		assertEquals(42, actual);

 		actual = stack.pop();
 		assertEquals(123, actual);
 	}
 	
 	@Test
 	public void shouldPushAndPopUnsignedBytesCorrectly() {
 		stack.pushAsUnsignedByte(255);
 		stack.push((byte)-127);
 		
 		assertEquals(129, stack.popAsUnsignedByte());
 		assertEquals(-1, stack.pop());
 	}
 	
 	@Test
 	public void shouldAddressMemoryCorrectly() {
 		stack.push((byte)10);
 		stack.push((byte)20);
 		stack.push((byte)30);
 		
 		assertEquals(10, ram.read(0x01FF));
 		assertEquals(20, ram.read(0x01FE));
 		assertEquals(30, ram.read(0x01FD));
 		
 		stack.pop();
 		stack.push((byte)42);
 		
 		assertEquals(42, ram.read(0x01FD));
 	}
 	
 	@Test
 	public void shouldWrapWhenCrossingPageBoundary() {
 		stack.pop();

 		assertEquals(0x00, registers.getSp());

 		stack.push((byte)1);
 		
 		assertEquals(0xFF, registers.getSp());

 		stack.push((byte)2);

 		assertEquals(1, ram.read(0x0100));
 		assertEquals(2, ram.read(0x01FF));
 	}
}
