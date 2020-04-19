package nl.vincentvanderleun.emulator6502.core.cpu;

import nl.vincentvanderleun.emulator6502.core.Bus;
import nl.vincentvanderleun.emulator6502.core.Cpu;

public class Cpu6502 implements Cpu {
	/**
	 * This code was written with porting to non-Java powered embedded systems (with minimal RAM memory) in mind
	 * Therefore, no lookup tables and only the bare minimum of variables. I've split the implementation in multiple classes, though,
	 * to ease unit-testing and keeping the code readable.
	 * 
	 * Also a disclaimer: I'm not a 6502 expert by any means and new to creating emulators, let alone a 6502 emulator, so don't use this code
	 * to teach yourself 6502 programming or emulation. Study a more mature project instead...
	 * 
	 * Also, this is very much WIP.
	 */
	private final Registers registers;
	private final StatusFlags statusFlags;

	private Bus bus = null;
	private AddressingModeHelper addressingModes = null;

	public Cpu6502() {
		this(new Registers(), new StatusFlags());
  	}
	
	Cpu6502(Registers registers, StatusFlags statusFlags) {
		this.registers = registers;
		this.statusFlags = statusFlags;
	}
	
	@Override
	public void connectToBus(Bus bus) {
		this.bus = bus;
		this.addressingModes = new AddressingModeHelper(bus);
	}

	@Override
	public void clock() {
		
	}
	
	public Registers getRegisters() {
		return registers;
	}

	public StatusFlags getStatusFlags() {
		return statusFlags;
	}
	
//	private byte read(int address) {
//		return bus.read(address);
//	}
//		
//	private int readAsUnsignedByte(int address) {
//		return bus.readAsUnsignedByte(address);
//	}
//
//	private void write(int address, byte value) {
//		bus.write(address,  value);
//	}
//	
//	private void writeAsUnsignedByte(int address, int value) {
//		bus.writeAsUnsignedByte(address, value);
//	}
//
//	@Override
//	public Bus getBus() {
//		return bus;
//	}
}
