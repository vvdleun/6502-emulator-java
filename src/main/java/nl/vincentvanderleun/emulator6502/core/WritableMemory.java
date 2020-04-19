package nl.vincentvanderleun.emulator6502.core;

public interface WritableMemory extends Memory {
	void write(int address, byte value);

	default public void writeAsUnsignedByte(int address, int value) {
		write(address, (byte)value);
	}
}
