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
	private final RegisterReaderHelper regReader;
	private final RegisterWriterHelper regWriter;
	
	// Not final, because they depend on the Bus, which requires a call to connectToBus()
	private Bus bus = null;
	private Stack stack = null;
	private MemoryReaderHelper memReader = null;
	private MemoryWriterHelper memWriter = null;
	
	public Cpu6502() {
		this(new Registers(), new StatusFlags());
  	}

	private Cpu6502(Registers registers, StatusFlags statusFlags) {
		this(registers, statusFlags, new InstructionHelper(statusFlags), new RegisterReaderHelper(registers), new RegisterWriterHelper(registers));
	}

	Cpu6502(Registers registers, StatusFlags statusFlags, InstructionHelper instructionHelper, RegisterReaderHelper registerReaderHelper, RegisterWriterHelper registerWriterHelper) {
		this.registers = registers;
		this.statusFlags = statusFlags;
		this.instructions = instructionHelper;
		this.regReader = registerReaderHelper;
		this.regWriter = registerWriterHelper;
	}

	
	@Override
	public void connectToBus(Bus bus) {
		this.bus = bus;
		this.stack = new Stack(registers, bus);
		this.memReader = new MemoryReaderHelper(registers, bus);
		this.memWriter = new MemoryWriterHelper(registers, bus);
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
				instructions.adc(regReader::readA, memReader::readImmediateValue, regWriter::writeA);
				registers.increasePc(2);
				break;
			case 0x65:
				// Zero Page
				instructions.adc(regReader::readA, memReader::readFromZeroPageAddress, regWriter::writeA);
				registers.increasePc(2);
				break;
			case 0x75:
				// Zero Page, X
				instructions.adc(regReader::readA, memReader::readFromZeroPageXAddress, regWriter::writeA);
				registers.increasePc(2);
				break;
			case 0x6D:
				// Absolute
				instructions.adc(regReader::readA, memReader::readFromAbsoluteAddress, regWriter::writeA);
				registers.increasePc(3);
				break;
			case 0x7D:
				// Absolute,X
				instructions.adc(regReader::readA, memReader::readFromAbsoluteXAddress, regWriter::writeA);
				registers.increasePc(3);
				break;
			case 0x79:
				// Absolute,Y
				instructions.adc(regReader::readA, memReader::readFromAbsoluteYAddress, regWriter::writeA);
				registers.increasePc(3);
				break;
			case 0x61:
				// Indirect,X
				instructions.adc(regReader::readA, memReader::readFromIndirectXAddress, regWriter::writeA);
				registers.increasePc(2);
				break;
			case 0x71:
				// Indirect,Y
				instructions.adc(regReader::readA, memReader::readFromIndirectYAddress, regWriter::writeA);
				registers.increasePc(2);
				break;
			// AND
			case 0x29:
				// Immediate
				instructions.and(regReader::readA, memReader::readImmediateValue, regWriter::writeA);
				registers.increasePc(2);
				break;
			case 0x25:
				// Zero Page
				instructions.and(regReader::readA, memReader::readFromZeroPageAddress, regWriter::writeA);
				registers.increasePc(2);
				break;
			case 0x35:
				// Zero Page,X
				instructions.and(regReader::readA, memReader::readFromZeroPageXAddress, regWriter::writeA);
				registers.increasePc(2);
				break;
			case 0x2D:
				// Absolute
				instructions.and(regReader::readA, memReader::readFromAbsoluteAddress, regWriter::writeA);
				registers.increasePc(3);
				break;
			case 0x3D:
				// Absolute,X
				instructions.and(regReader::readA, memReader::readFromAbsoluteXAddress, regWriter::writeA);
				registers.increasePc(3);
				break;
			case 0x39:
				// Absolute,Y
				instructions.and(regReader::readA, memReader::readFromAbsoluteYAddress, regWriter::writeA);
				registers.increasePc(3);
				break;
			case 0x21:
				// Indirect,X
				instructions.and(regReader::readA, memReader::readFromIndirectXAddress, regWriter::writeA);
				registers.increasePc(2);
				break;
			case 0x31:
				// Indirect,Y
				instructions.and(regReader::readA, memReader::readFromIndirectYAddress, regWriter::writeA);
				registers.increasePc(2);
				break;
			// ASL (Arithmetic Shift Left)
			case 0x0A:
				// Accumulator
				instructions.asl(regReader::readA, regWriter::writeA);
				registers.increasePc();
				break;
			case 0x06:
				// Zero Page
				instructions.asl(memReader::readFromZeroPageAddress, memWriter::writeToZeroPageAddress);
				registers.increasePc(2);
				break;
			case 0x16:
				// Zero Page,X
				instructions.asl(memReader::readFromZeroPageXAddress, memWriter::writeToZeroPageXAddress);
				registers.increasePc(2);
				break;
			case 0x0E:
				// Absolute
				instructions.asl(memReader::readFromAbsoluteAddress, memWriter::writeToAbsoluteAddress);
				registers.increasePc(3);
				break;
			case 0x1E:
				// Absolute,X
				instructions.asl(memReader::readFromAbsoluteXAddress, memWriter::writeToAbsoluteXAddress);
				registers.increasePc(3);
				break;
			// BIT (test BITs)
			case 0x24:
				// Zero Page
				instructions.bit(memReader::readFromZeroPageAddress, regReader::readA);
				registers.increasePc(2);
				break;
			case 0x2C:
				// Absolute
				instructions.bit(memReader::readFromAbsoluteAddress, regReader::readA);
				registers.increasePc(3);
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
