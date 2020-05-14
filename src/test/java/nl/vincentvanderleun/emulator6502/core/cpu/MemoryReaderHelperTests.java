package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.vincentvanderleun.emulator6502.core.Bus;

class MemoryReaderHelperTests {
	private static final int PROGRAM_COUNTER = 10;
	private static final int X_REGISTER = 20;
	private static final int Y_REGISTER = 30;
	private static final int ADDRESS = 40;
	private static final int ADDRESS_VALUE = 42;

	private Bus bus = mock(Bus.class);
	private Registers registers = mock(Registers.class);
	private AdressingModeHelper adressingModeHelper = mock(AdressingModeHelper.class);
	private MemoryReaderHelper memoryReaderHelper = new MemoryReaderHelper(adressingModeHelper, registers, bus);
	
	@BeforeEach
	public void init() {
		when(registers.getPc()).thenReturn(PROGRAM_COUNTER);
		when(registers.getX()).thenReturn(X_REGISTER);
		when(registers.getY()).thenReturn(Y_REGISTER);

		when(bus.readAsUnsignedByte(ADDRESS)).thenReturn(ADDRESS_VALUE);
	}
	
	@Test
	public void readingFromAbsoluteAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchAbsoluteAddress(PROGRAM_COUNTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromAbsoluteAddress();
		
		assertEquals(42, result);
	}

	@Test
	public void readingFromAbsoluteXAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchAbsoluteXAddress(PROGRAM_COUNTER, X_REGISTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromAbsoluteXAddress();

		assertEquals(42, result);
	}
	
	@Test
	public void readingFromAbsoluteYAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchAbsoluteYAddress(PROGRAM_COUNTER, Y_REGISTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromAbsoluteYAddress();

		assertEquals(42, result);
	}
	
	@Test
	public void readingImmediateValueShouldDelegateToCorrectMethod() {
		// Let's make it clear that fetchImmediateValue() is supposed to return a value, not an address
		when(adressingModeHelper.fetchImmediateValue(PROGRAM_COUNTER)).thenReturn(ADDRESS_VALUE);

		int result = memoryReaderHelper.readImmediateValue();

		assertEquals(42, result);

		// One of two methods that shouldn't resolve any address
		verify(bus, never()).readAsUnsignedByte(any(Integer.class));
		verify(bus, never()).read(any(Integer.class));
	}
	
	@Test
	public void readingFromIndirectAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchIndirectAddress(PROGRAM_COUNTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromIndirectAddress();

		assertEquals(42, result);
	}
	
	@Test
	public void readingFromIndirectXAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchIndirectXAddress(PROGRAM_COUNTER, X_REGISTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromIndirectXAddress();

		assertEquals(42, result);
	}

	@Test
	public void readingFromIndirectYAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchIndirectYAddress(PROGRAM_COUNTER, Y_REGISTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromIndirectYAddress();

		assertEquals(42, result);
	}
	
	@Test
	public void readingFromZeroPageAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchZeroPageAddress(PROGRAM_COUNTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromZeroPageAddress();

		assertEquals(42, result);
	}

	@Test
	public void readingFromZeroPageXAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchZeroPageXAddress(PROGRAM_COUNTER, X_REGISTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromZeroPageXAddress();

		assertEquals(42, result);
	}

	@Test
	public void readingFromZeroPageYAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchZeroPageYAddress(PROGRAM_COUNTER, Y_REGISTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readFromZeroPageYAddress();

		assertEquals(42, result);
	}

	@Test
	public void readingRelativeAddressShouldDelegateToCorrectMethod() {
		when(adressingModeHelper.fetchRelativeAddress(PROGRAM_COUNTER)).thenReturn(ADDRESS);

		int result = memoryReaderHelper.readRelativeAddress();

		// readRelativeAddress returns an address!
		assertEquals(40, result);
		
		// Another method that shouldn't try to resolve any address
		verify(bus, never()).readAsUnsignedByte(any(Integer.class));
		verify(bus, never()).read(any(Integer.class));
	}
}

