package nl.vincentvanderleun.emulator6502.core.memory;

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
	public void writeAsUnsignedByte(int address, int value) {
		write(address, (byte)value);
	}
	
	@Override
	public byte read(int address) {
		int readAddress = calcAddress(address);
		
		return originalRam.read(readAddress);
	}

	@Override
	public int readAsUnsignedByte(int address) {
		return read(address) & 0xFF;
	}

	private int calcAddress(int address) {
		return originalRam.getStartAddress() + (address - startAddress);
	}
	
	@Override
	public int getSize() {
		return originalRam.getSize();
	}
}
