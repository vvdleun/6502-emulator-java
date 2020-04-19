package nl.vincentvanderleun.emulator6502.core.cpu;

public class StatusFlags {
	/*
	 * Source: https://wiki.nesdev.com/w/index.php/Status_flags
	 * 
	 *		NVss DIZC
	 * 		|||| ||||
	 *		|||| |||+- Carry
	 *		|||| ||+-- Zero
	 *		|||| |+--- Interrupt Disable
	 *		|||| +---- Decimal
	 *		||++------ No CPU effect, see: the B flag
	 *		|+-------- Overflow
	 *		+--------- Negative
	 */
	
	private int flags;
	
	// Carry flag
	
	public StatusFlags setCarry() {
		return setSingleFlag(1);
	}

	public StatusFlags resetCarry() {
		return resetSingleFlag(0b1111_1110);
	}
	
	public int getCarry() {
		return (flags & 1);
	}

	// Zero flag

	public StatusFlags setZero() {
		return setSingleFlag(2);
	}

	public StatusFlags resetZero() {
		return resetSingleFlag(0b1111_1101);
	}
	
	public int getZero() {
		return (flags & 2) >> 1;
	}
	
	// Interrupt disable flag
	
	public StatusFlags setInterruptDisable() {
		return setSingleFlag(4);
	}

	public StatusFlags resetInterruptDisable() {
		return resetSingleFlag(0b1111_1011);
	}

	public int getInterruptDisable() {
		return (flags & 4) >> 2;
	}

	// Decimal flag

	public StatusFlags setDecimal() {
		return setSingleFlag(8);
	}

	public StatusFlags resetDecimal() {
		return resetSingleFlag(0b1111_0111);
	}

	public int getDecimal() {
		return (flags & 8) >> 3;
	}
	
	// Overflow flag

	public StatusFlags setOverflow() {
		return setSingleFlag(64);
	}

	public StatusFlags resetOverflow() {
		return resetSingleFlag(0b1011_1111);
	}

	public int getOverflow() {
		return (flags & 64) >> 6;
	}
	
	// Negative flag

	public StatusFlags setNegative() {
		return setSingleFlag(128);
	}

	public StatusFlags resetNegative() {
		return resetSingleFlag(0b0111_1111);
	}

	public int getNegative() {
		return (flags & 128) >> 7;
	}
	
	// Helper methods
	
	private StatusFlags setSingleFlag(int orWith) {
		flags |= orWith;
		return this;
	}
	
	private StatusFlags resetSingleFlag(int andWith) {
		flags &= andWith;
		return this;
	}

	// Etc.
	
	public int getFlags() {
		return flags;
	}
	
	public void setFlags(int flags) {
		this.flags = flags & 0xFF;
	}
}
