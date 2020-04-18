package nl.vincentvanderleun.emulator6502.core;

public abstract class Bus {
	protected final Cpu cpu;
	
	public Bus(Cpu cpu) {
		this.cpu = cpu;
	}
	
	public abstract byte read(int address);

	public int readAsUnsignedByte(int address) {
		return read(address) & 0xFF;
	}
	
	public abstract void write(int address, byte value);

	public void writeAsUnsignedByte(int address, int value) {
		write(address, (byte)value);
	}
}
