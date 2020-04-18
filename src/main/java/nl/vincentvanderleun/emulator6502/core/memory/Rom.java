package nl.vincentvanderleun.emulator6502.core.memory;

import java.util.Arrays;

public class Rom implements ReadableMemory {
	private final Ram memory;
	
	public Rom(int startAddress, byte[] data) {
		byte[] copyOfData = Arrays.copyOf(data, data.length);

		memory = new Ram(startAddress, copyOfData);
	}

	@Override
	public byte read(int address) {
		return memory.read(address);
	}

	@Override
	public int readAsUnsignedByte(int address) {
		return memory.readAsUnsignedByte(address);
	}
	
	@Override
	public int getStartAddress() {
		return memory.getStartAddress();
	}
	
	@Override
	public int getEndAddress() {
		return memory.getEndAddress();
	}

	@Override
	public int getSize() {
		return memory.getSize();
	}
}
