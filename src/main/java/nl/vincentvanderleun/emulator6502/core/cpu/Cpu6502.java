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
	private Stack stack = null;
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
		this.stack = new Stack(registers, bus);
	}

	@Override
	public void clock() {
		runNextInstruction();
	}
	
	private void runNextInstruction() {
		int temp;

		int instruction = getOpcode();
		final Registers previousRegisters = registers.clone();
		
		switch(instruction) {
		// ADC (Add with Carry)
		case 0x69:
			// Immediate
			// TODO IMPLEMENT DECIMAL MODE
			temp = registers.getA() + readAtImmediateAddress() + statusFlags.getCarry();
			registers.setA(temp);
			// updateFlagsNZCV(previousRegisters);
		}
		
	}

	// Helper methods

	// - Address modes
	
	private int readAtImmediateAddress() {
		final int address = addressingModes.fetchAbsoluteAddress(registers.getPc());
		return readMemoryAt(address); 
	}

	// - CPU flags

//	 *		NVss DIZC
//	 * 		|||| ||||
//	 *		|||| |||+- Carry
//	 *		|||| ||+-- Zero
//	 *		|||| |+--- Interrupt Disable
//	 *		|||| +---- Decimal
//	 *		||++------ No CPU effect, see: the B flag
//	 *		|+-------- Overflow
//	 *		+--------- Negative

		

	// - Registers

	private void increaseSp(int value) {
		registers.setSp((registers.getSp() + 1) & 0xFFF);
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
