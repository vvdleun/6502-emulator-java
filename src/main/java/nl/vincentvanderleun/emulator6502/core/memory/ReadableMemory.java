package nl.vincentvanderleun.emulator6502.core.memory;

public interface ReadableMemory extends Memory {
	byte read(int address);
	int readAsUnsignedByte(int address);
}
