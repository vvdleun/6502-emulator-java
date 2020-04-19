package nl.vincentvanderleun.emulator6502.core;

public interface Cpu {
	void connectToBus(Bus bus);
	void clock();
}
