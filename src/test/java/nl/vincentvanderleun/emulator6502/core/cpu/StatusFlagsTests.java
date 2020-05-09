package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StatusFlagsTests {
	private final StatusFlags flags = new StatusFlags();
	
	@Test
	public void carryFlagMustBeReadableAndWritable() {
		assertEquals(0, flags.getCarry());
		
		flags.setCarry();
		
		assertEquals(1, flags.getCarry());
		assertEquals(0b0000_0001, flags.getFlags());
		
		flags.resetCarry();

		assertEquals(0, flags.getCarry());
		assertEquals(0, flags.getFlags());
	}
	

	@Test
	public void zeroFlagMustBeReadableAndWritable() {
		assertEquals(0, flags.getZero());
		
		flags.setZero();
		
		assertEquals(1, flags.getZero());
		assertEquals(0b0000_0010, flags.getFlags());
		
		flags.resetZero();

		assertEquals(0, flags.getZero());
		assertEquals(0, flags.getFlags());
	}
	
	@Test
	public void interruptDisableFlagMustBeReadableAndWritable() {
		assertEquals(0, flags.getInterruptDisable());
		
		flags.setInterruptDisable();
		
		assertEquals(1, flags.getInterruptDisable());
		assertEquals(0b0000_0100, flags.getFlags());
		
		flags.resetInterruptDisable();

		assertEquals(0, flags.getInterruptDisable());
		assertEquals(0, flags.getFlags());
	}
	
	@Test
	public void decimalFlagMustBeReadableAndWritable() {
		assertEquals(0, flags.getDecimal());
		
		flags.setDecimal();
		
		assertEquals(1, flags.getDecimal());
		assertEquals(0b0000_1000, flags.getFlags());
		
		flags.resetDecimal();

		assertEquals(0, flags.getDecimal());
		assertEquals(0, flags.getFlags());
	}

	@Test
	public void overflowFlagMustBeReadableAndWritable() {
		assertEquals(0, flags.getOverflow());
		
		flags.setOverflow();
		
		assertEquals(1, flags.getOverflow());
		assertEquals(0b0100_0000, flags.getFlags());
		
		flags.resetOverflow();

		assertEquals(0, flags.getOverflow());
		assertEquals(0, flags.getFlags());
	}

	@Test
	public void negativeFlagMustBeReadableAndWritable() {
		assertEquals(0, flags.getNegative());
		
		flags.setNegative();
		
		assertEquals(1, flags.getNegative());
		assertEquals(0b1000_0000, flags.getFlags());
		
		flags.resetNegative();

		assertEquals(0, flags.getNegative());
		assertEquals(0, flags.getFlags());
	}
	
	@Test
	public void usingBooleanParametersShouldWork() {
		// Carry
		flags.setCarry(true);
		assertEquals(1, flags.getCarry());
		assertEquals(0b0000_0001, flags.getFlags());

		flags.setCarry(false);
		assertEquals(0, flags.getCarry());
		assertEquals(0b0000_0000, flags.getFlags());

		// Zero
		flags.setZero(true);
		assertEquals(1, flags.getZero());
		assertEquals(0b0000_0010, flags.getFlags());

		flags.setZero(false);
		assertEquals(0, flags.getZero());
		assertEquals(0b0000_0000, flags.getFlags());
		
		// Interrupt Disable
		flags.setInterruptDisable(true);
		assertEquals(1, flags.getInterruptDisable());
		assertEquals(0b0000_0100, flags.getFlags());

		flags.setInterruptDisable(false);
		assertEquals(0, flags.getInterruptDisable());
		assertEquals(0b0000_0000, flags.getFlags());

		// Decimal		
		flags.setDecimal(true);
		assertEquals(1, flags.getDecimal());
		assertEquals(0b0000_1000, flags.getFlags());

		flags.setDecimal(false);
		assertEquals(0, flags.getDecimal());
		assertEquals(0b0000_0000, flags.getFlags());

		// Overflow
		flags.setOverflow(true);
		assertEquals(1, flags.getOverflow());
		assertEquals(0b0100_0000, flags.getFlags());

		flags.setOverflow(false);
		assertEquals(0, flags.getOverflow());
		assertEquals(0b0000_0000, flags.getFlags());

		// Negative
		flags.setNegative(true);
		assertEquals(1, flags.getNegative());
		assertEquals(0b1000_0000, flags.getFlags());

		flags.setNegative(false);
		assertEquals(0, flags.getNegative());
		assertEquals(0b0000_0000, flags.getFlags());
	}
	
}
