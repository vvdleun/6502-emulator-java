package nl.vincentvanderleun.emulator6502.core.memory;

public interface WritableMemory extends Memory {
	void write(int address, byte value);
	void writeAsUnsignedByte(int address, int value);
}
