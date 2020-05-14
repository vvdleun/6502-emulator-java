package nl.vincentvanderleun.emulator6502.core.cpu;

import nl.vincentvanderleun.emulator6502.core.Bus;

public class MemoryReaderHelper {
	private final AddressReaderHelper addressingModes;
	private final Registers registers;
	private final Bus bus;

	MemoryReaderHelper(Registers registers, Bus bus) {
		this(new AddressReaderHelper(bus), registers, bus);
	}

	MemoryReaderHelper(AddressReaderHelper addressingModeHelper, Registers registers, Bus bus) {
		this.addressingModes = addressingModeHelper;
		this.registers = registers;
		this.bus = bus;
	}
	
	// All addressing mode-related methods should adhere to the Supplier<Integer> functional interface
	
	public int readImmediateValue() {
		return addressingModes.fetchImmediateValue(registers.getPc());
	}
	
	public int readFromZeroPageAddress() {
		final int address = addressingModes.fetchZeroPageAddress(registers.getPc());
		return readMemoryAt(address);
	}
	
	public int readFromZeroPageXAddress() {
		final int address = addressingModes.fetchZeroPageXAddress(registers.getPc(), registers.getX());
		return readMemoryAt(address);
	}

	public int readFromZeroPageYAddress() {
		final int address = addressingModes.fetchZeroPageYAddress(registers.getPc(), registers.getY());
		return readMemoryAt(address);
	}
	
	public int readFromAbsoluteAddress() {
		final int address = addressingModes.fetchAbsoluteAddress(registers.getPc());
		return readMemoryAt(address);
	}

	public int readFromAbsoluteXAddress() {
		final int address = addressingModes.fetchAbsoluteXAddress(registers.getPc(), registers.getX());
		return readMemoryAt(address);
	}

	public int readFromAbsoluteYAddress() {
		final int address = addressingModes.fetchAbsoluteYAddress(registers.getPc(), registers.getY());
		return readMemoryAt(address);
	}
	
	public int readFromIndirectAddress() {
		final int address = addressingModes.fetchIndirectAddress(registers.getPc());
		return readMemoryAt(address);
	}
	
	public int readFromIndirectXAddress() {
		final int address = addressingModes.fetchIndirectXAddress(registers.getPc(), registers.getX());
		return readMemoryAt(address);
	}

	public int readFromIndirectYAddress() {
		final int address = addressingModes.fetchIndirectYAddress(registers.getPc(), registers.getY());
		return readMemoryAt(address);
	}
	
	public int readRelativeAddress() {
		return addressingModes.fetchRelativeAddress(registers.getPc());
	}
	
	private int readMemoryAt(int address) {
		return bus.readAsUnsignedByte(address & 0xFFFF);
	}
}
