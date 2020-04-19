package nl.vincentvanderleun.emulator6502.core.memory;

import nl.vincentvanderleun.emulator6502.core.ReadableMemory;
import nl.vincentvanderleun.emulator6502.core.WritableMemory;

public class MirrorredRam implements WritableMemory, ReadableMemory {
	private final Ram originalRam;
	private final int startAddress;
	
	public MirrorredRam(int startAddress, Ram originalRam) {
		this.originalRam = originalRam;
		this.startAddress = startAddress;
	}
	
	@Override
	public int getStartAddress() {
		return startAddress;
	}

	@Override
	public int getEndAddress() {
		return startAddress + originalRam.getSize() - 1;
	}

	@Override
	public void write(int address, byte value) {
		int writeAddress = calcAddress(address);

		originalRam.write(writeAddress, value);
	}

	@Override
	public byte read(int address) {
		int readAddress = calcAddress(address);
		
		return originalRam.read(readAddress);
	}

	private int calcAddress(int address) {
		return originalRam.getStartAddress() + (address - startAddress);
	}
	
	@Override
	public int getSize() {
		return originalRam.getSize();
	}
}
