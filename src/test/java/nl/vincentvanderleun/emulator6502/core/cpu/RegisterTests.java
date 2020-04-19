package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegisterTests {
	private final Registers registers = new Registers();
	
	@Test
	public void accumulatorShouldTake8BitValue() {
		registers.setA(0xFF);
		assertEquals(0xFF, registers.getA());
		
		registers.setA(0xFFFF);
		assertEquals(0xFF, registers.getA());
	}

	@Test
	public void xRegisterShouldTake8BitValue() {
		registers.setX(0xFF);
		assertEquals(0xFF, registers.getX());
		
		registers.setX(0xFFFF);
		assertEquals(0xFF, registers.getX());
	}

	@Test
	public void yRegisterShouldTake8BitValue() {
		registers.setY(0xFF);
		assertEquals(0xFF, registers.getY());
		
		registers.setY(0xFFFF);
		assertEquals(0xFF, registers.getY());
	}

	@Test
	public void stackPointRegisterShouldTake8BitValue() {
		registers.setSp(0xFF);
		assertEquals(0xFF, registers.getSp());
		
		registers.setSp(0xFFFF);
		assertEquals(0xFF, registers.getSp());
	}
	
	@Test
	public void programCounterRegisterShouldTake16BitValue() {
		registers.setPc(0xFFFF);
		assertEquals(0xFFFF, registers.getPc());
		
		registers.setSp(0xFFFF_FFFF);
		assertEquals(0xFFFF, registers.getPc());
	}
}
