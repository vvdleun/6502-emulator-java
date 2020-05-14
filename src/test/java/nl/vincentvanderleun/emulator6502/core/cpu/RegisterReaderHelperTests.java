package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RegisterReaderHelperTests {

	private Registers registers = new Registers();
	private RegisterReaderHelper registerReaderHelper = new RegisterReaderHelper(registers);

	@Test
	public void shouldReadARegister() {
		registers.setA(123);
		
		int actual = registerReaderHelper.readA();
		
		assertEquals(123, actual);
	}
}
