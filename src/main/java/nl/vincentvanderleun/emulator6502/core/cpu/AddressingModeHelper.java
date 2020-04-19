package nl.vincentvanderleun.emulator6502.core.cpu;

import nl.vincentvanderleun.emulator6502.core.Bus;

public class AddressingModeHelper {
	private final Bus bus;
	
	public AddressingModeHelper(Bus bus) {
		this.bus = bus;
	}
	
	// PC register must be set at the first byte of the instruction that is currently being interpret
	
	public int fetchAbsoluteAddress(int pc) {
		int addressLow = readAddressFrom(pc + 1);
		int addressHigh = readAddressFrom(pc + 2);
		
		return addressLow + (addressHigh << 8);
	}
	
	public int fetchZeroPageAddress(int pc) {
		return readAddressFrom(pc + 1);
	}
	
	public int fetchZeroPageXAddress(int pc, int x) {
		int addressByte = readAddressFrom(pc + 1);
		return calcAddress(addressByte + x);
	}
	
	public int fetchZeroPageYAddress(int pc, int y) {
		int addressByte = readAddressFrom(pc + 1);
		return calcAddress(addressByte + y);
	}

	public int fetchAbsoluteXAddress(int pc, int x) {
		int address = fetchAbsoluteAddress(pc);
		return calcAddress(address + x);
	}

	public int fetchAbsoluteYAddress(int pc, int y) {
		int address = fetchAbsoluteAddress(pc);
		return address + y;
	}
	
	public int fetchImmediateValue(int pc) {
		return readAddressFrom(pc + 1);
	}
	
	public int fetchRelativeAddress(int pc) {
		int address = calcAddress(pc + 1);
		return bus.read(address);	// Must be signed byte
	}
	
	public int fetchIndirectAddress(int pc) {
		int address = fetchAbsoluteAddress(pc);
		return bus.readAsUnsignedByte(address);
	}

	public int fetchIndexedIndirectAddress(int pc, int x) {
		int zeroPageAddress = readAddressFrom(pc + 1) + x;
		int addressLow = bus.readAsUnsignedByte(calcByte(zeroPageAddress));
		int addressHigh = bus.readAsUnsignedByte(calcByte(zeroPageAddress + 1));
		return readAddressFrom(addressLow + (addressHigh << 8));
	}

//	public int fetchIndirectIndexedAddress(int pc, int y) {
//		
//	}

	// Helper methods
	
	private int readAddressFrom(int address) {
		// Make sure program counter does not overflow :-/
		int readAddress = calcAddress(address);
		return bus.readAsUnsignedByte(readAddress);
	}

	private int calcByte(int b) {
		return b & 0xFF;
	}	

	private int calcAddress(int address) {
		return address & 0xFFFF;
	}	
}
