package nl.vincentvanderleun.emulator6502.core;

public interface ReadableMemory extends Memory {
	byte read(int address);

	default public int readAsUnsignedByte(int address) {
		return read(address) & 0xFF;
	}
}
