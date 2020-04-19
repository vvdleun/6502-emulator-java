package nl.vincentvanderleun.emulator6502.core.memory;

import nl.vincentvanderleun.emulator6502.core.ReadableMemory;

public class ZeroRom implements ReadableMemory {
	private static final byte FIXED_VALUE = 0;
	
	private final int startAddress;
	private final int size;
	
	public ZeroRom(int startAddress, int size) {
		this.startAddress = startAddress;
		this.size = size;
	}
	
	@Override
	public int getStartAddress() {
		return startAddress;
	}

	@Override
	public int getEndAddress() {
		return startAddress + size - 1;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public byte read(int address) {
		if(address < startAddress || address > getEndAddress()) {
			// ArrayIndexOutOfBoundsException for consistency reasons with other RAM types :-/
			throw new ArrayIndexOutOfBoundsException("Address " + address + " is out of range");
		}
		return FIXED_VALUE;
	}
}
