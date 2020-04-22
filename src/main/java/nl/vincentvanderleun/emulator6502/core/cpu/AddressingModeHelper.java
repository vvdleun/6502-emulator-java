package nl.vincentvanderleun.emulator6502.core.cpu;

import nl.vincentvanderleun.emulator6502.core.Bus;

public class AddressingModeHelper {
	private final Bus bus;
	
	public AddressingModeHelper(Bus bus) {
		this.bus = bus;
	}
	
	// PC register must be set at the first byte of the instruction that is currently being interpret
	
	public int fetchAbsoluteAddress(int pc) {
		int addressLow = readMemoryFrom(pc + 1);
		int addressHigh = readMemoryFrom(pc + 2);
		
		return addressLow + (addressHigh << 8);
	}
	
	public int fetchZeroPageAddress(int pc) {
		return readMemoryFrom(pc + 1);
	}
	
	public int fetchZeroPageXAddress(int pc, int x) {
		int zeroPageAddress = readMemoryFrom(pc + 1);
		return toZeroPageAddress(zeroPageAddress + x);
	}
	
	public int fetchZeroPageYAddress(int pc, int y) {
		return fetchZeroPageXAddress(pc, y);
	}

	public int fetchAbsoluteXAddress(int pc, int x) {
		int absoluteAddress = fetchAbsoluteAddress(pc);
		return toAddress(absoluteAddress + x);
	}

	public int fetchAbsoluteYAddress(int pc, int y) {
		return fetchAbsoluteXAddress(pc, y);
	}
	
	public int fetchImmediateValue(int pc) {
		return readMemoryFrom(pc + 1);
	}
	
	public int fetchRelativeAddress(int pc) {
		return (byte)readMemoryFrom(pc + 1); // Must be signed byte
	}
	
	public int fetchIndirectAddress(int pc) {
		int address = fetchAbsoluteAddress(pc);
		return bus.readAsUnsignedByte(address);
	}

	public int fetchIndexedIndirectAddress(int pc, int x) {
		// Address must stay in zero page		
		int zeroPageAddress = toZeroPageAddress(readMemoryFrom(pc + 1) + x);
		int addressLow = bus.readAsUnsignedByte(zeroPageAddress);
		int addressHigh = bus.readAsUnsignedByte(toZeroPageAddress(zeroPageAddress + 1));
 		return readMemoryFrom(addressLow + (addressHigh << 8));
	}

	public int fetchIndirectIndexedAddress(int pc, int y) {
		int indirectAddress = readMemoryFrom(pc + 1);
		// Memory does not wrap at zero page, resulting address can be anywhere
		return toAddress(indirectAddress + y);
	}

	// Helper methods
	
	private int readMemoryFrom(int address) {
		// Make sure program counter does not overflow :-/
		int readAddress = toAddress(address);
		return bus.readAsUnsignedByte(readAddress);
	}

	private int toZeroPageAddress(int b) {
		return b & 0xFF;
	}	

	private int toAddress(int address) {
		return address & 0xFFFF;
	}	
}
