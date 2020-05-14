package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RegisterWriterHelperTests {
	private Registers registers = new Registers();
	private RegisterWriterHelper registerWriterHelper = new RegisterWriterHelper(registers);
	
	@Test
	public void shouldWriteARegister() {
		registerWriterHelper.writeA(42);
		
		assertEquals(42, registers.getA());
	}
}
