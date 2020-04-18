package nl.vincentvanderleun.emulator6502.core.memory;

public class MirrorredRom implements ReadableMemory {
	private final Rom originalRom;
	private final int startAddress;
	
	public MirrorredRom(int startAddress, Rom originalRom) {
		this.originalRom = originalRom;
		this.startAddress = startAddress;
	}
	
	@Override
	public int getStartAddress() {
		return startAddress;
	}

	@Override
	public int getEndAddress() {
		return startAddress + originalRom.getSize() - 1;
	}

	@Override
	public byte read(int address) {
		int readAddress = originalRom.getStartAddress() + (address - startAddress);
		return originalRom.read(readAddress);
	}

	@Override
	public int readAsUnsignedByte(int address) {
		return read(address) & 0xFF;
	}

	@Override
	public int getSize() {
		return originalRom.getSize();
	}
}
