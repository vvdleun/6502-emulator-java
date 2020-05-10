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
	
	@Test
	public void shouldIncreaseProgramCounter() {
		registers.setPc(0x0A);
		registers.increasePc();

		assertEquals(0x0B, registers.getPc());
	}

	@Test
	public void shouldIncreaseProgramCounterWith2() {
		registers.setPc(0x0A);
		registers.increasePc(2);

		assertEquals(0x0C, registers.getPc());
	}
	
	@Test
	public void shouldBeClonable() {
		registers.setA(1);
		registers.setPc(2);
		registers.setSp(3);
		registers.setX(4);
		registers.setY(5);
		
		Registers cloned = registers.clone();

		registers.setA(10);
		registers.setPc(20);
		registers.setSp(30);
		registers.setX(40);
		registers.setY(50);
		
		assertEquals(1, cloned.getA());		
		assertEquals(2, cloned.getPc());		
		assertEquals(3, cloned.getSp());		
		assertEquals(4, cloned.getX());		
		assertEquals(5, cloned.getY());		
	}
}
