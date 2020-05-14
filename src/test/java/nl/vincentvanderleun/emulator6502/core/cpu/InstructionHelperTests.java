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

	private StatusFlags statusFlags = new StatusFlags();
	private InstructionHelper instructionHelper = new InstructionHelper(statusFlags);
		
	// ADC tests

	private int adc(int input1, int input2) {
		int[] output = { 0 };
		instructionHelper.adc(() -> input1, () -> input2, (result) -> output[0] = result);
		return output[0];
	}

	@Test
	public void adcNonDecimal00plus00() {
		int actual = adc(0x00, 0x00);

		assertEquals(0x00, actual);
		assertCzvnFlags(CARRY_RESET, ZERO_SET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal01plus00() {
		int actual = adc(0x01, 0x00);

		assertEquals(0x01, actual);
		assertCzvnFlags(CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal01plusFE() {
		int actual = adc(0x01, 0xFE);

		assertEquals(0xFF, actual);
		assertCzvnFlags(CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimalFFplus01() {
		int actual = adc(0xFF, 0x01);

		assertEquals(0x00, actual);
		assertCzvnFlags(CARRY_SET, ZERO_SET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal50plus10() {
		int actual = adc(0x50, 0x10);

		assertEquals(0x60, actual);
		assertCzvnFlags(CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal50plus50() {
		int actual = adc(0x50, 0x50);

		assertEquals(0xA0, actual);
		assertCzvnFlags(CARRY_RESET, ZERO_RESET, OVERFLOW_SET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimal50plus90() {
		int actual = adc(0x50, 0x90);

		assertEquals(0xE0, actual);
		assertCzvnFlags(CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimal50plusD0() {
		int actual = adc(0x50, 0xD0);

		assertEquals(0x20, actual);
		assertCzvnFlags(CARRY_SET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimalD0plus10() {
		int actual = adc(0xD0, 0x10);

		assertEquals(0xE0, actual);
		assertCzvnFlags(CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimalD0plus50() {
		int actual = adc(0xD0, 0x50);

		assertEquals(0x20, actual);
		assertCzvnFlags(CARRY_SET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimalD0plus90() {
		int actual = adc(0xD0, 0x90);

		assertEquals(0x60, actual);
		assertCzvnFlags(CARRY_SET, ZERO_RESET, OVERFLOW_SET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimalD0plusD0() {
		int actual = adc(0xD0, 0xD0);

		assertEquals(0xA0, actual);
		assertCzvnFlags(CARRY_SET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_SET);
	}

	@Test
	public void adcNonDecimal80plusFF() {
		int actual = adc(0x80, 0xFF);

		assertEquals(0x7F, actual);
		assertCzvnFlags(CARRY_SET, ZERO_RESET, OVERFLOW_SET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal00plus00withCarry() {
		statusFlags.setCarry();

		int actual = adc(0x00, 0x00);

		assertEquals(0x01, actual);
		assertCzvnFlags(CARRY_RESET, ZERO_RESET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	@Test
	public void adcNonDecimal00plusFFwithCarry() {
		statusFlags.setCarry();
		int actual = adc(0x00, 0xFF);

		assertEquals(0x00, actual);
		assertCzvnFlags(CARRY_SET, ZERO_SET, OVERFLOW_RESET, NEGATIVE_RESET);
	}

	// AND tests

	private int and(int input1, int input2) {
		int[] output = { 0 };
		instructionHelper.and(() -> input1, () -> input2, (result) -> output[0] = result);
		return output[0];
	}

	@Test
	public void and03and01() {
		int actual = and(3, 1);

		assertEquals(1, actual);
		assertZnFlags(ZERO_RESET, NEGATIVE_RESET);
	}

	@Test
	public void andFFandEF() {
		int actual = and(0xFF, 0xEF);

		assertEquals(0xEF, actual);
		assertZnFlags(ZERO_RESET, NEGATIVE_SET);
	}

	@Test
	public void and00and00() {
		int actual = and(0, 0);

		assertEquals(0, actual);
		assertZnFlags(ZERO_SET, NEGATIVE_RESET);
	}
	
	private void assertCzvnFlags(int carry, int zero, int overflow, int negative) {
		assertEquals(carry, statusFlags.getCarry());
		assertEquals(zero, statusFlags.getZero());
		assertEquals(overflow, statusFlags.getOverflow());
		assertEquals(negative, statusFlags.getNegative());
		assertEquals(0, statusFlags.getDecimal());
		assertEquals(0, statusFlags.getInterruptDisable());
	}
	
	private void assertZnFlags(int zero, int negative) {
		assertEquals(0, statusFlags.getCarry());
		assertEquals(zero, statusFlags.getZero());
		assertEquals(0, statusFlags.getOverflow());
		assertEquals(negative, statusFlags.getNegative());
		assertEquals(0, statusFlags.getDecimal());
		assertEquals(0, statusFlags.getInterruptDisable());
	}
}
