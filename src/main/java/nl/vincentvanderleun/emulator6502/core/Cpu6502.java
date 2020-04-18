package nl.vincentvanderleun.emulator6502.core;

public class Cpu6502 implements Cpu {
	private final Bus bus;
	
	public Cpu6502(Bus bus) {
		this.bus = bus;
  	}

	@Override
	public void clock() {
		
	}

	private byte read(int address) {
		return bus.read(address);
	}
		
	private int readAsUnsignedByte(int address) {
		return bus.readAsUnsignedByte(address);
	}

	private void write(int address, byte value) {
		bus.write(address,  value);
	}
	
	private void writeAsUnsignedByte(int address, int value) {
		bus.writeAsUnsignedByte(address, value);
	}

	@Override
	public Bus getBus() {
		return bus;
	}
	
}
