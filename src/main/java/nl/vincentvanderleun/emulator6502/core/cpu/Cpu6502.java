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
	private final InstructionHelper instructionHelper;

	private Bus bus = null;
	private Stack stack = null;
	private AddressingModeHelper addressingModes = null;
	
	public Cpu6502() {
		this(new Registers(), new StatusFlags(), new InstructionHelper());
  	}
	
	Cpu6502(Registers registers, StatusFlags statusFlags, InstructionHelper instructionHelper) {
		this.registers = registers;
		this.statusFlags = statusFlags;
		this.instructionHelper = instructionHelper;
	}
	
	@Override
	public void connectToBus(Bus bus) {
		this.bus = bus;
		this.addressingModes = new AddressingModeHelper(bus);
		this.stack = new Stack(registers, bus);
	}

	@Override
	public void clock() {
		runNextInstruction();
	}
	
	private void runNextInstruction() {
		int opcode = getOpcode();
		
		switch(opcode) {
			// ADC (Add with Carry)
			case 0x69:
				// Immediate
				adc(readImmediateValue());
				increasePc(2);
				break;
			case 0x65:
				// Zero Page
				adc(readFromZeroPageAddress());
				increasePc(2);
				break;
			case 0x75:
				// Zero Page, X
				adc(readFromZeroPageXAddress());
				increasePc(2);
				break;
			case 0x6D:
				// Absolute
				adc(readFromAbsoluteAddress());
				increasePc(3);
				break;
			case 0x7D:
				// Absolute,X
				adc(readFromAbsoluteXAddress());
				increasePc(3);
				break;
			case 0x79:
				// Absolute,Y
				adc(readFromAbsoluteYAddress());
				increasePc(3);
				break;
			case 0x61:
				// Indirect,X
				adc(readFromIndirectXAddress());
				increasePc(2);
				break;
			case 0x71:
				// Indirect,Y
				adc(readFromIndirectYAddress());
				increasePc(2);
				break;
			default:
				throw new IllegalStateException("Unknown opcode: " + opcode);
		}
	}

	// Helper methods

	// - Address modes
	
	private int readImmediateValue() {
		final int address = addressingModes.fetchImmediateValue(registers.getPc());
		return readMemoryAt(address); 
	}
	
	private int readFromZeroPageAddress() {
		final int address = addressingModes.fetchZeroPageAddress(registers.getPc());
		return readMemoryAt(address);
	}
	
	private int readFromZeroPageXAddress() {
		final int address = addressingModes.fetchZeroPageXAddress(registers.getPc(), registers.getX());
		return readMemoryAt(address);
	}
	
	private int readFromAbsoluteAddress() {
		final int address = addressingModes.fetchAbsoluteAddress(registers.getPc());
		return readMemoryAt(address);
	}

	private int readFromAbsoluteXAddress() {
		final int address = addressingModes.fetchAbsoluteXAddress(registers.getPc(), registers.getX());
		return readMemoryAt(address);
	}

	private int readFromAbsoluteYAddress() {
		final int address = addressingModes.fetchAbsoluteXAddress(registers.getPc(), registers.getY());
		return readMemoryAt(address);
	}
	
	private int readFromIndirectXAddress() {
		final int address = addressingModes.fetchIndirectXAddress(registers.getPc(), registers.getX());
		return readMemoryAt(address);
	}

	private int readFromIndirectYAddress() {
		final int address = addressingModes.fetchIndirectXAddress(registers.getPc(), registers.getY());
		return readMemoryAt(address);
	}
	
	// - Instructions
	
	private void adc(int value) {
		instructionHelper.adc(registers, statusFlags, value);
	}

	// - Registers

	private void increasePc() {
		increasePc(1);
	}

	private void increasePc(int value) {
		registers.setPc((registers.getPc() + value) & 0xFFFF);
	}
	
	// - Other

	private int getOpcode() {
		return readMemoryAt(registers.getSp());		
	}
	
	private int readMemoryAt(int address) {
		return bus.readAsUnsignedByte(address & 0xFFFF);
	}
	
	// Getters / setters
	
	public Registers getRegisters() {
		return registers;
	}

	public StatusFlags getStatusFlags() {
		return statusFlags;
	}
}
