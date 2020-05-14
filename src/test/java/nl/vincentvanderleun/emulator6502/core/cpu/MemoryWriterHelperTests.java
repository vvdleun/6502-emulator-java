package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.vincentvanderleun.emulator6502.core.Bus;

public class MemoryWriterHelperTests {
	private static final int PROGRAM_COUNTER = 10;
	private static final int X_REGISTER = 20;
	private static final int ADDRESS = 40;
	private static final int VALUE = 42;
	
	private Bus bus = mock(Bus.class);
	private Registers registers = mock(Registers.class);
	private AddressReaderHelper adressingModeHelper = mock(AddressReaderHelper.class);
	private MemoryWriterHelper memoryWriterHelper = new MemoryWriterHelper(adressingModeHelper, registers, bus);
	
	@BeforeEach
	public void setup() {
		when(registers.getPc()).thenReturn(PROGRAM_COUNTER);
		when(registers.getX()).thenReturn(X_REGISTER);
	}
	
	@Test
	public void shouldWriteToAbsoluteAddress() {
		when(adressingModeHelper.fetchAbsoluteAddress(PROGRAM_COUNTER)).thenReturn(ADDRESS);
		
		memoryWriterHelper.writeToAbsoluteAddress(VALUE);
		
		verify(bus).writeAsUnsignedByte(ADDRESS, VALUE);
	}

	@Test
	public void shouldWriteToAbsoluteXAddress() {
		when(adressingModeHelper.fetchAbsoluteXAddress(PROGRAM_COUNTER, X_REGISTER)).thenReturn(ADDRESS);
		
		memoryWriterHelper.writeToAbsoluteXAddress(VALUE);
		
		verify(bus).writeAsUnsignedByte(ADDRESS, VALUE);
	}

	@Test
	public void shouldWriteToZeroPageAddress() {
		when(adressingModeHelper.fetchZeroPageAddress(PROGRAM_COUNTER)).thenReturn(ADDRESS);
		
		memoryWriterHelper.writeToZeroPageAddress(VALUE);
		
		verify(bus).writeAsUnsignedByte(ADDRESS, VALUE);
	}

	@Test
	public void shouldWriteToZeroPageXAddress() {
		when(adressingModeHelper.fetchZeroPageXAddress(PROGRAM_COUNTER, X_REGISTER)).thenReturn(ADDRESS);
		
		memoryWriterHelper.writeToZeroPageXAddress(VALUE);
		
		verify(bus).writeAsUnsignedByte(ADDRESS, VALUE);
	}
}
