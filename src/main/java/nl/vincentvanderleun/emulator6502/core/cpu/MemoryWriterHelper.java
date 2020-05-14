package nl.vincentvanderleun.emulator6502.core.cpu;

import nl.vincentvanderleun.emulator6502.core.Bus;

public class MemoryWriterHelper {
	private final AdressingModeHelper addressingModes;
	private final Registers registers;
	private final Bus bus;

	MemoryWriterHelper(Registers registers, Bus bus) {
		this(new AdressingModeHelper(bus), registers, bus);
	}

	MemoryWriterHelper(AdressingModeHelper addressingModeHelper, Registers registers, Bus bus) {
		this.addressingModes = addressingModeHelper;
		this.registers = registers;
		this.bus = bus;
	}

	public void writeToAbsoluteAddress(int value) {
		int address = addressingModes.fetchAbsoluteAddress(registers.getPc());
		bus.writeAsUnsignedByte(address, value);
	}

	public void writeToAbsoluteXAddress(int value) {
		int address = addressingModes.fetchAbsoluteXAddress(registers.getPc(), registers.getX());
		bus.writeAsUnsignedByte(address, value);
	}
	
	public void writeToZeroPageAddress(int value) {
		int address = addressingModes.fetchZeroPageAddress(registers.getPc());
		bus.writeAsUnsignedByte(address, value);
	}

	public void writeToZeroPageXAddress(int value) {
		int address = addressingModes.fetchZeroPageXAddress(registers.getPc(), registers.getX());
		bus.writeAsUnsignedByte(address, value);
	}
	
}
