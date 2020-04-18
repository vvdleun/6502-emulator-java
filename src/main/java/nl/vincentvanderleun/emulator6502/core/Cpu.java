package nl.vincentvanderleun.emulator6502.core;

public interface Cpu {
	Bus getBus();
	void clock();
}
