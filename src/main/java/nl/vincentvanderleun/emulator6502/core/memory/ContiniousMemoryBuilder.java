package nl.vincentvanderleun.emulator6502.core.memory;

import java.util.ArrayList;
import java.util.List;

import nl.vincentvanderleun.emulator6502.core.Memory;

public class ContiniousMemoryBuilder {
	private int nextStartAddress = 0;
	private List<Memory> memory = new ArrayList<>();

	public ContiniousMemoryBuilder addRam(int size) {
		return addMemory(new Ram(nextStartAddress, size));
	}

	public ContiniousMemoryBuilder addRam(byte[] data) {
		return addMemory(new Ram(nextStartAddress, data));
	}
	
	public ContiniousMemoryBuilder addRom(byte[] data) {
		return addMemory(new Rom(nextStartAddress, data));
	}

	public ContiniousMemoryBuilder addMirroredRam(int index) {
		Ram ram = ((Ram)memory.get(index));
		return addMemory(new MirrorredRam(nextStartAddress, ram));
	}

	public ContiniousMemoryBuilder addMirroredRom(int index) {
		Rom rom = ((Rom)memory.get(index));
		return addMemory(new MirrorredRom(nextStartAddress, rom));
	}
	
	public ContiniousMemoryBuilder addZeroRom(int size) {
		return addMemory(new ZeroRom(nextStartAddress, size));
	}
	
	public ContiniousMemoryBuilder skipBytes(int countBytes) {
		nextStartAddress += countBytes;
		return this;
	}

	public List<Memory> build() {
		return memory;
	}

	// Helper methods
	
	private ContiniousMemoryBuilder addMemory(Memory nextMemory) {
		memory.add(nextMemory);
		nextStartAddress += nextMemory.getSize();
		return this;
	}
}
