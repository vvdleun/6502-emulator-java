package nl.vincentvanderleun.emulator6502.core.cpu;

public class InstructionHelper {
	InstructionHelper() {
	}

	public void adc(Registers registers, StatusFlags statusFlags, int value) {
		int registerA = registers.getA();
		value = value & 0xFF;

		int result = registerA + value + statusFlags.getCarry();

		statusFlags.setCarry(result > 255);

		result = result & 0xFF;
		
		registers.setA(result);

		statusFlags.setZero(result == 0);
		statusFlags.setNegative((result & 0x80) != 0);
		setOverflow(statusFlags, registerA, value, result);		 
	}

	private void setOverflow(StatusFlags statusFlags, int value1, int value2, int result) {
		// For full theory on the Overflow flag, see the most excellent article at
		// http://www.righto.com/2012/12/the-6502-overflow-flag-explained.html by Ken Shirriff.
		// This is usually implemented in emulators with a smarter one-liner: (M^result)&(N^result)&0x80
		statusFlags.setOverflow((value1 ^ result) >> 7 != 0 && (value2 ^ result) >> 7 != 0);
	}
}
