package nl.vincentvanderleun.emulator6502.core.cpu;

import nl.vincentvanderleun.emulator6502.core.Bus;

public class Stack {
	private static final int STACK_MEMORY_PAGE = 0x0100;

	private final Registers registers;
	private final Bus bus;

	Stack(Registers registers, Bus bus) {
		this.registers = registers;
		this.bus = bus;
	}

	public void pushAsUnsignedByte(int value) {
		push((byte)value);
	}

	public void push(byte value) {
		int address = calcAddress();
		bus.write(address, value);
		decrSp();
	}

	private int calcAddress() {
		return (STACK_MEMORY_PAGE + registers.getSp()) & 0xFFFF;
	}

	private void decrSp() {
		registers.setSp((registers.getSp() - 1) & 0xFF);
	}

	public int popAsUnsignedByte() {
		return pop() & 0xFF;
	}

	public byte pop() {
		incrSp();
		int address = calcAddress();
		return bus.read(address);
	}

	private void incrSp() {
		registers.setSp((registers.getSp() + 1) & 0xFF);
	}
}
