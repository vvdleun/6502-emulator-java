package nl.vincentvanderleun.emulator6502.core.cpu;

public class RegisterReaderHelper {
	private final Registers registers;
	
	RegisterReaderHelper(Registers registers) {
		this.registers = registers;
	}
	
	public int readA() {
		return registers.getA();
	}
}
