package nl.vincentvanderleun.emulator6502.core.memory;

public class Ram implements ReadableMemory, WritableMemory {
	private int startAddress;
	
	private byte[] memory;
	
	public Ram(int startAddress, int size) {
		this(startAddress, new byte[size]);
	}
	
	public Ram(int startAddress, byte[] data) {
		this.startAddress = startAddress;
		
		memory = data;
	}
	
	@Override
	public byte read(int address) {
		return memory[address - startAddress];
	}

	@Override
	public int readAsUnsignedByte(int address) {
		return read(address) & 0xff;
	}
	
	@Override
	public void write(int address, byte value) {
		memory[address - startAddress] = value;
	}
	
	@Override
	public void writeAsUnsignedByte(int address, int value) {
		write(address, (byte)value);
	}
	
	@Override
	public int getStartAddress() {
		return startAddress;
	}
	
	@Override
	public int getEndAddress() {
		return startAddress + memory.length - 1;
	}
	
	@Override
	public int getSize() {
		return memory.length;
	}
}
