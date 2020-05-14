package nl.vincentvanderleun.emulator6502.core.cpu;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstructionHelper {
	private final StatusFlags statusFlags;
	
	InstructionHelper(StatusFlags statusFlags) {
		this.statusFlags = statusFlags;
	}

	public void adc(Supplier<Integer> input1, Supplier<Integer> input2, Consumer<Integer> output) {
		final int input1Value = input1.get();
		final int input2Value = input2.get();
		
		int result = input1Value + input2Value + statusFlags.getCarry();

		statusFlags.setCarry(result > 255);

		result = result & 0xFF;
		
		statusFlags.setZero(result == 0);
		statusFlags.setNegative((result & 0x80) != 0);
		setOverflow(statusFlags, input1Value, input2Value, result);

		output.accept(result);
	}
	
	public void and(Supplier<Integer> input1, Supplier<Integer> input2, Consumer<Integer> output) {
		final int result = input1.get() & input2.get();
		
		statusFlags.setNegative((result & 0x80) != 0);
		statusFlags.setZero(result == 0);

		output.accept(result);
	}
	
	public void asl(Supplier<Integer> input, Consumer<Integer> output) {
		final int inputValue = input.get();
		
		statusFlags.setCarry((inputValue & 0x80) != 0);

		final int result = (inputValue << 1) & 0xFF;
		
		statusFlags.setNegative((result & 0x80) != 0);
		statusFlags.setZero(result == 0);
		
		output.accept(result);
	}

	public void bit(Supplier<Integer> input1, Supplier<Integer> input2) {
		final int input1Value = input1.get();
		
		statusFlags.setZero((input1Value & input2.get()) == 0);
		statusFlags.setNegative((input1Value & 0x80) != 0);	// Bit 7 set?
		statusFlags.setOverflow((input1Value & 0x40) != 0);	// Bit 6 set?
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
