package nl.vincentvanderleun.emulator6502.core.cpu;

import java.util.function.Supplier;

public class InstructionHelper {
	private final Registers registers;
	private final StatusFlags statusFlags;
	
	InstructionHelper(Registers registers, StatusFlags statusFlags) {
		this.registers = registers;
		this.statusFlags = statusFlags;
	}

	public void adc(Supplier<Integer> memory) {
		int result = registers.getA() + memory.get() + statusFlags.getCarry();

		statusFlags.setCarry(result > 255);

		result = result & 0xFF;
		
		statusFlags.setZero(result == 0);
		statusFlags.setNegative((result & 0x80) != 0);
		setOverflow(statusFlags, registers.getA(), memory.get(), result);
		
		registers.setA(result);
	}
	
	public void and(Supplier<Integer> memory) {
		final int result = registers.getA() & memory.get();
		
		statusFlags.setNegative((result & 0x80) != 0);
		statusFlags.setZero(result == 0);

		registers.setA(result);
	}

	private void setOverflow(StatusFlags statusFlags, int value1, int value2, int result) {
		// For full theory on the Overflow flag, see the most excellent article at
		// http://www.righto.com/2012/12/the-6502-overflow-flag-explained.html by Ken Shirriff.
		// This is usually implemented in emulators with a smarter one-liner: (M^result)&(N^result)&0x80
		statusFlags.setOverflow(
				(value1 ^ result) >> 7 != 0 && 
				(value2 ^ result) >> 7 != 0);
	}
}
