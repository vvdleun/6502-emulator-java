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
	private final InstructionHelper instructions;
	
	// Not final, because they depend on the Bus, which requires a call to connectToBus()
	private Bus bus = null;
	private Stack stack = null;
	private MemoryReaderHelper memoryReader = null;
	
	public Cpu6502() {
		this(new Registers(), new StatusFlags());
  	}

	private Cpu6502(Registers registers, StatusFlags statusFlags) {
		this(registers, statusFlags, new InstructionHelper(registers, statusFlags));
	}

	Cpu6502(Registers registers, StatusFlags statusFlags, InstructionHelper instructionHelper) {
		this.registers = registers;
		this.statusFlags = statusFlags;
		this.instructions = instructionHelper;
	}

	
	@Override
	public void connectToBus(Bus bus) {
		this.bus = bus;
		this.stack = new Stack(registers, bus);
		this.memoryReader = new MemoryReaderHelper(registers, bus);
	}

	@Override
	public void clock() {
		runNextInstruction();
	}
	
	private void runNextInstruction() {
		int opcode = getOpcode();
		
		// It's a way too long switch case block for now...
		// When this class is stable and fully unit-tested, let's see if there's something to gain
		// (performance/code-wise) to make this implementation smarter (i.e. by making use of the bits
		// of the opcode).
		
		switch(opcode) {
			// ADC (Add with Carry)
			case 0x69:
				// Immediate
				instructions.adc(memoryReader::readImmediateValue);
				registers.increasePc(2);
				break;
			case 0x65:
				// Zero Page
				instructions.adc(memoryReader::readFromZeroPageAddress);
				registers.increasePc(2);
				break;
			case 0x75:
				// Zero Page, X
				instructions.adc(memoryReader::readFromZeroPageXAddress);
				registers.increasePc(2);
				break;
			case 0x6D:
				// Absolute
				instructions.adc(memoryReader::readFromAbsoluteAddress);
				registers.increasePc(3);
				break;
			case 0x7D:
				// Absolute,X
				instructions.adc(memoryReader::readFromAbsoluteXAddress);
				registers.increasePc(3);
				break;
			case 0x79:
				// Absolute,Y
				instructions.adc(memoryReader::readFromAbsoluteYAddress);
				registers.increasePc(3);
				break;
			case 0x61:
				// Indirect,X
				instructions.adc(memoryReader::readFromIndirectXAddress);
				registers.increasePc(2);
				break;
			case 0x71:
				// Indirect,Y
				instructions.adc(memoryReader::readFromIndirectYAddress);
				registers.increasePc(2);
				break;
			// AND
			case 0x29:
				// Immediate
				instructions.and(memoryReader::readImmediateValue);
				registers.increasePc(2);
				break;
			case 0x25:
				// Zero Page
				instructions.and(memoryReader::readFromZeroPageAddress);
				registers.increasePc(2);
				break;
			case 0x35:
				// Zero Page,X
				instructions.and(memoryReader::readFromZeroPageXAddress);
				registers.increasePc(2);
				break;
			case 0x2D:
				// Absolute,X
				instructions.and(memoryReader::readFromAbsoluteAddress);
				registers.increasePc(3);
				break;
			case 0x3D:
				// Absolute,X
				instructions.and(memoryReader::readFromAbsoluteXAddress);
				registers.increasePc(3);
				break;
			case 0x39:
				// Absolute,Y
				instructions.and(memoryReader::readFromAbsoluteYAddress);
				registers.increasePc(3);
				break;
			case 0x21:
				// Indirect,X
				instructions.and(memoryReader::readFromIndirectXAddress);
				registers.increasePc(2);
				break;
			case 0x31:
				// Indirect,Y
				instructions.and(memoryReader::readFromIndirectYAddress);
				registers.increasePc(2);
				break;
			default:
				throw new IllegalStateException("Unknown opcode: " + opcode);
		}
	}

	// - Other

	private int getOpcode() {
		return bus.readAsUnsignedByte(registers.getSp());
	}
	
	// Getters / setters
	
	public Registers getRegisters() {
		return registers;
	}

	public StatusFlags getStatusFlags() {
		return statusFlags;
	}
}
