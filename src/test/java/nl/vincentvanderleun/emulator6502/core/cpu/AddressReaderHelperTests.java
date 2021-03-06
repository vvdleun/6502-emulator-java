package nl.vincentvanderleun.emulator6502.core.cpu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import nl.vincentvanderleun.emulator6502.core.Bus;
import nl.vincentvanderleun.emulator6502.core.Cpu;
import nl.vincentvanderleun.emulator6502.core.Memory;
import nl.vincentvanderleun.emulator6502.core.bus.DynamicBus;
import nl.vincentvanderleun.emulator6502.core.memory.Rom;

public class AddressReaderHelperTests {
	private static final int PROGRAM_COUNTER = 0x0A;

	@Test
	public void shouldFetchAbsoluteAddress() {
		byte[] programData = { (byte)0x8D, (byte)0xC0, (byte)1 };

		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);

		int actual = adressingModeHelper.fetchAbsoluteAddress(PROGRAM_COUNTER);
		
		assertEquals(0x1C0, actual);
	}

	@Test
	public void shouldFetchZeroPageAddress() {
		byte[] programData = { (byte)0x85, (byte)0xA0 };
	
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		
		int actual = adressingModeHelper.fetchZeroPageAddress(PROGRAM_COUNTER);
		
		assertEquals(0x00A0, actual);
	}
	
	@Test
	public void shouldFetchZeroPageXAddress() {
		byte[] programData = { (byte)0x95, (byte)0xA0 };
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		final int REGISTER_X = 5;
		
		int actual = adressingModeHelper.fetchZeroPageXAddress(PROGRAM_COUNTER, REGISTER_X);
		
 		assertEquals(0x00A5, actual);
	}

	@Test
	public void shouldFetchZeroPageYAddress() {
		byte[] programData = { (byte)0x95, (byte)0xB0 };
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		final int REGISTER_Y = 6;
		
		int actual = adressingModeHelper.fetchZeroPageYAddress(PROGRAM_COUNTER, REGISTER_Y);
		
		assertEquals(0x00B6, actual);
	}
		
	@Test
	public void shouldFetchAbsoluteXAddress() {
		byte[] programData = { (byte)0x9D, (byte)0x02, (byte)0x01 };
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		final int REGISTER_X = 0xD0;
		
		int actual = adressingModeHelper.fetchAbsoluteXAddress(PROGRAM_COUNTER, REGISTER_X);
		
		assertEquals(0x01D2, actual);
	}
	
	@Test
	public void shouldFetchAbsoluteYAddress() {
		byte[] programData = { (byte)0x99, (byte)0x03, (byte)0x02 };
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		final int REGISTER_Y = 0xE0;

		int actual = adressingModeHelper.fetchAbsoluteYAddress(PROGRAM_COUNTER, REGISTER_Y);
		
		assertEquals(0x02E3, actual);
	}
	
	@Test
	public void shouldFetchImmediateValue() {
		byte[] programData = { (byte)0xE9, (byte)0xAA };
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		
		int actual = adressingModeHelper.fetchImmediateValue(PROGRAM_COUNTER);
		
		assertEquals(0xAA, actual);
	}

	@Test
	public void shouldFetchRelativeAddress() {
		byte[] programData = { (byte)0xD0, (byte)0xFA };
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		
		int actual = adressingModeHelper.fetchRelativeAddress(PROGRAM_COUNTER);
		
		assertEquals(-6, actual);
	}

	@Test
	public void shouldFetchIndirectAddress() {
		byte[] programData = { (byte)0xD0, (byte)0x01, (byte)0xB0 };	// Range: 0x0A..0x0C
		byte[] data = { 0, 0xF };										// Range: 0xB00..0xB01
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData, data);
		
		int actual = adressingModeHelper.fetchIndirectAddress(PROGRAM_COUNTER);
		
		assertEquals(15, actual);
	}

	@Test
	public void shouldFetchIndexedIndirectAddress() {
		byte[] programData = { (byte)0xA1, 6, 0, 0, 0, 0, 0x02, (byte)0xB0 };	// Range: 0x0A..0x11
		byte[] data = { 0, 0, 0xF };											// Range: 0xB00..0xB02
		
		final int REGISTER_X = 0xA;
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData, data);
	
		int actual = adressingModeHelper.fetchIndirectXAddress(PROGRAM_COUNTER, REGISTER_X);
		
		// Memory map:
		//   0Ah)   Opcode: A1h
		//   0Bh)   		06h
		//   10h)  			02h
		//   11h)  			B0h
		// B002h) 			0Fh

		// Register X: 		06h
		
		// Ah + 6h = 10h --> Address on 10h+11h: B002h
		assertEquals(0xB002, actual);
	}
	
	@Test
	public void shouldFetchIndirectIndexedAddress() {
		byte[] programData = { (byte)0xA1, 0xF, 0, 0, 0, 0x02, (byte)0xB0 };	// Range: 0x0A..0x10
		
		final int REGISTER_Y = 0x2;
		
		AdressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
	
		int actual = adressingModeHelper.fetchIndirectYAddress(PROGRAM_COUNTER, REGISTER_Y);
		
		// On address 0x0F the stored address is 0xB002 --> 0xB002 + register Y's 2 = 0xB004
		assertEquals(0xB004, actual);
	}
	

	// Helper methods

	private AdressingModeHelper createAddressingModesHelper(byte[] programBytes) {
		return createAddressingModesHelper(programBytes, null);
	}

	private AdressingModeHelper createAddressingModesHelper(byte[] programBytes, byte[] dataBytes) {
		List<Memory> memory = new ArrayList<>();

		final int START_ADDRESS_PROGRAM = PROGRAM_COUNTER;
		final int START_ADDRESS_DATA = 0xB000;
		
		Rom rom1 = new Rom(START_ADDRESS_PROGRAM, programBytes);
		memory.add(rom1);
		
		if(dataBytes != null) {
			Rom rom2 = new Rom(START_ADDRESS_DATA, dataBytes);
			memory.add(rom2);
		}

		Cpu cpu = Mockito.mock(Cpu.class);

		Bus bus = new DynamicBus(cpu, memory);

		return new AdressingModeHelper(bus);
	}
}
