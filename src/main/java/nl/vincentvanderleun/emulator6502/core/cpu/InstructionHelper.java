package nl.vincentvanderleun.emulator6502.core.cpu;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstructionHelper {
	private final StatusFlags statusFlags;
	
	InstructionHelper(StatusFlags statusFlags) {
		this.statusFlags = statusFlags;
	}

	public void adc(Supplier<Integer> input1, Supplier<Integer> input2, Consumer<Integer> output) {
		int result = input1.get() + input2.get() + statusFlags.getCarry();

		statusFlags.setCarry(result > 255);

		result = result & 0xFF;
		
		statusFlags.setZero(result == 0);
		statusFlags.setNegative((result & 0x80) != 0);
		setOverflow(statusFlags, input1.get(), input2.get(), result);

		output.accept(result);
	}
	
	public void and(Supplier<Integer> input1, Supplier<Integer> input2, Consumer<Integer> output) {
		final int result = input1.get() & input2.get();
		
		statusFlags.setNegative((result & 0x80) != 0);
		statusFlags.setZero(result == 0);

		output.accept(result);
	}
	
	public void asl(Supplier<Integer> input, Consumer<Integer> output) {
		statusFlags.setCarry((input.get() & 0x80) != 0);

		final int result = (input.get() << 1) & 0xFF;
		
		statusFlags.setNegative((result & 0x80) != 0);
		statusFlags.setZero(result == 0);
		
		output.accept(result);
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
