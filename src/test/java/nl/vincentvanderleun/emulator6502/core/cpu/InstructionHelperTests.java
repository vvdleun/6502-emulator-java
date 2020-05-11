package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InstructionHelperTests {
	private static final int CARRY_SET = 1;
	private static final int CARRY_RESET = 0;
	private static final int ZERO_SET = 1;
	private static final int ZERO_RESET = 0;
	private static final int OVERFLOW_SET = 1;
	private static final int OVERFLOW_RESET = 0;
	private static final int NEGATIVE_SET = 1;
	private static final int NEGATIVE_RESET = 0;

	private Registers registers = new Registers();
	private StatusFlags statusFlags = new StatusFlags();
	private InstructionHelper instructionHelper = new InstructionHelper(registers, statusFlags);

	// ADC tests

	private void adc(int registerA, int memoryValue) {
		registers.setA(registerA);
		instructionHelper.adc(() -> memoryValue);
	}

	@Test
	public void adcNonDecimal00plus00() {
		adc(0x00, 0x00);

		assertARegisterAndCzvnFlags(0x00, CARRY_RESET, ZERO_SET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal01plus00() {
		adc(0x01, 0x00);

		assertARegisterAndCzvnFlags(0x01, CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal01plusFE() {
		adc(0x01, 0xFE);

		assertARegisterAndCzvnFlags(0xFF, CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimalFFplus01() {
		adc(0xFF, 0x01);

		assertARegisterAndCzvnFlags(0x00, CARRY_SET, ZERO_SET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal50plus10() {
		adc(0x50, 0x10);

		assertARegisterAndCzvnFlags(0x60, CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal50plus50() {
		adc(0x50, 0x50);

		assertARegisterAndCzvnFlags(0xA0, CARRY_RESET, ZERO_RESET, OVERFLOW_SET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimal50plus90() {
		adc(0x50, 0x90);

		assertARegisterAndCzvnFlags(0xE0, CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimal50plusD0() {
		adc(0x50, 0xD0);

		assertARegisterAndCzvnFlags(0x20, CARRY_SET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimalD0plus10() {
		adc(0xD0, 0x10);

		assertARegisterAndCzvnFlags(0xe0, CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimalD0plus50() {
		adc(0xD0, 0x50);

		assertARegisterAndCzvnFlags(0x20, CARRY_SET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimalD0plus90() {
		adc(0xD0, 0x90);

		assertARegisterAndCzvnFlags(0x60, CARRY_SET, ZERO_RESET, OVERFLOW_SET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimalD0plusD0() {
		adc(0xD0, 0xD0);

		assertARegisterAndCzvnFlags(0xA0, CARRY_SET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimal80plusFF() {
		adc(0x80, 0xFF);

		assertARegisterAndCzvnFlags(0x7F, CARRY_SET, ZERO_RESET, OVERFLOW_SET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal00plus00withCarry() {
		statusFlags.setCarry();
		adc(0x00, 0x00);

		assertARegisterAndCzvnFlags(0x01, CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal00plusFFwithCarry() {
		statusFlags.setCarry();
		adc(0x00, 0xFF);

		assertARegisterAndCzvnFlags(0x00, CARRY_SET, ZERO_SET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	// AND tests

	private void and(int registerA, int memoryValue) {
		registers.setA(registerA);
		instructionHelper.and(() -> memoryValue);
	}

	@Test
	public void and03and01() {
		and(3, 1);
		assertARegisterAndZnFlags(1, ZERO_RESET, NEGATIVE_RESET);
	}

	@Test
	public void andFFandEF() {
		and(0xFF, 0xEF);
		assertARegisterAndZnFlags(0xEF, ZERO_RESET, NEGATIVE_SET);
	}

	@Test
	public void and00and00() {
		and(0, 0);
		assertARegisterAndZnFlags(0, ZERO_SET, NEGATIVE_RESET);
	}
	
	private void assertARegisterAndCzvnFlags(int a, int carry, int zero, int overflow, int negative) {
		assertEquals(a, registers.getA());

		assertEquals(carry, statusFlags.getCarry());
		assertEquals(zero, statusFlags.getZero());
		assertEquals(overflow, statusFlags.getOverflow());
		assertEquals(negative, statusFlags.getNegative());
		assertEquals(0, statusFlags.getDecimal());
		assertEquals(0, statusFlags.getInterruptDisable());
	}
	
	private void assertARegisterAndZnFlags(int a, int zero, int negative) {
		assertEquals(a, registers.getA());

		assertEquals(0, statusFlags.getCarry());
		assertEquals(zero, statusFlags.getZero());
		assertEquals(0, statusFlags.getOverflow());
		assertEquals(negative, statusFlags.getNegative());
		assertEquals(0, statusFlags.getDecimal());
		assertEquals(0, statusFlags.getInterruptDisable());
	}
}
