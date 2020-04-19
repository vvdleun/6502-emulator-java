package nl.vincentvanderleun.emulator6502.core.cpu;

public class Registers {
	// 6502 registers
	// TODO change to signed :'( primitive byte type later?
	private int a;
	private int x;
	private int y;
	private int sp;
	private int pc;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a & 0xFF;			// Accumulator is 8 bits
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {		// X is 8 bits
		this.x = x & 0xFF;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {		// Y is 8 bits
		this.y = y & 0xFF;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {		// Stack pointer is 8 bits
		this.sp = sp & 0xFF;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) { 	// Program counter is 16 bits
		this.pc = pc & 0xFFFF;
	}

	public void increasePc() {
		setPc(++this.pc);
	}

	public void increasePc(int value) {
		setPc(this.pc += value);
	}
}
