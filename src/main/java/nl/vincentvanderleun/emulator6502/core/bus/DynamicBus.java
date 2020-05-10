package nl.vincentvanderleun.emulator6502.core.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.vincentvanderleun.emulator6502.core.Bus;
import nl.vincentvanderleun.emulator6502.core.Cpu;
import nl.vincentvanderleun.emulator6502.core.Memory;
import nl.vincentvanderleun.emulator6502.core.ReadableMemory;
import nl.vincentvanderleun.emulator6502.core.WritableMemory;

public class DynamicBus extends Bus {
	private final List<Memory> memory;
	
	public DynamicBus(Cpu cpu, List<Memory> memory) {
		super(cpu);
		cpu.connectToBus(this);

		this.memory = new ArrayList<>(memory);
	}
	
	@Override
	public byte read(int address) {
		Memory memory = findMemoryThatCoversAddress(address);
		if(!(memory instanceof ReadableMemory)) {
			throw new IllegalStateException("Memory at address " + address + " exists, but is not readable");
		}
		
		return ((ReadableMemory)memory).read(address);
	}
	
	@Override
	public void write(int address, byte value) {
		Memory memory = findMemoryThatCoversAddress(address);
		if(!(memory instanceof WritableMemory)) {
			throw new IllegalStateException("Memory at address " + address + " exists, but is not writeable");
		}
		
		((WritableMemory)memory).write(address, value);
	}
	
	private Memory findMemoryThatCoversAddress(int address) {
		List<Memory> memory = this.memory.stream()
				.filter(a -> address >= a.getStartAddress() && address <= a.getEndAddress())
				.limit(2)
				.collect(Collectors.toList());

		if(memory.isEmpty()) {
			throw new IllegalStateException("Address " + address + " is not covered");
		} else if(memory.size() > 1) {
			throw new IllegalStateException("Internal error: Address " + address + " is covered by multiple memory modules");
		}
		
		return memory.get(0);
	}
}
