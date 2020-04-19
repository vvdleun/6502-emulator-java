package nl.vincentvanderleun.emulator6502.core;

public interface Memory {
	int getStartAddress();	
	int getEndAddress();
	int getSize();
}
