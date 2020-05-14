package nl.vincentvanderleun.emulator6502.core.cpu;

public class RegisterWriterHelper {
	private final Registers registers;
	
	RegisterWriterHelper(Registers registers) {
		this.registers = registers;
	}
	
	public void writeA(int value) {
		registers.setA(value);
	}
}
