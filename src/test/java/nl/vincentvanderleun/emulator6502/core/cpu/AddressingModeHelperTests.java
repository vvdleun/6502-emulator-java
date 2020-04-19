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

public class AddressingModeHelperTests {
	private static final int PROGRAM_COUNTER = 10;

	@Test
	public void shouldFetchAbsoluteAddress() {
		byte[] programData = { (byte)0x8D, (byte)0xC0, (byte)1 };

		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);

		int actual = adressingModeHelper.fetchAbsoluteAddress(PROGRAM_COUNTER);
		
		assertEquals(0x1C0, actual);
	}

	@Test
	public void shouldFetchZeroPageAddress() {
		byte[] programData = { (byte)0x85, (byte)0xA0 };
	
		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		
		int actual = adressingModeHelper.fetchZeroPageAddress(PROGRAM_COUNTER);
		
		assertEquals(0x00A0, actual);
	}
	
	@Test
	public void shouldFetchZeroPageXAddress() {
		byte[] programData = { (byte)0x95, (byte)0xA0 };
		
		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		final int REGISTER_X = 5;
		
		int actual = adressingModeHelper.fetchZeroPageXAddress(PROGRAM_COUNTER, REGISTER_X);
		
 		assertEquals(0x00A5, actual);
	}

	@Test
	public void shouldFetchZeroPageYAddress() {
		byte[] programData = { (byte)0x95, (byte)0xB0 };
		
		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		final int REGISTER_Y = 6;
		
		int actual = adressingModeHelper.fetchZeroPageYAddress(PROGRAM_COUNTER, REGISTER_Y);
		
		assertEquals(0x00B6, actual);
	}
		
	@Test
	public void shouldFetchAbsoluteXAddress() {
		byte[] programData = { (byte)0x9D, (byte)0x02, (byte)0x01 };
		
		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		final int REGISTER_X = 0xD0;
		
		int actual = adressingModeHelper.fetchAbsoluteXAddress(PROGRAM_COUNTER, REGISTER_X);
		
		assertEquals(0x01D2, actual);
	}
	
	@Test
	public void shouldFetchAbsoluteYAddress() {
		byte[] programData = { (byte)0x99, (byte)0x03, (byte)0x02 };
		
		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		final int REGISTER_Y = 0xE0;

		int actual = adressingModeHelper.fetchAbsoluteYAddress(PROGRAM_COUNTER, REGISTER_Y);
		
		assertEquals(0x02E3, actual);
	}
	
	@Test
	public void shouldFetchImmediateValue() {
		byte[] programData = { (byte)0xE9, (byte)0xAA };
		
		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		
		int actual = adressingModeHelper.fetchImmediateValue(PROGRAM_COUNTER);
		
		assertEquals(0xAA, actual);
	}

	@Test
	public void shouldFetchRelativeAddress() {
		byte[] programData = { (byte)0xD0, (byte)0xFA };
		
		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData);
		
		int actual = adressingModeHelper.fetchRelativeAddress(PROGRAM_COUNTER);
		
		assertEquals(-6, actual);
	}

	@Test
	public void shouldFetchIndexedIndirectAddress() {
		byte[] programData = { (byte)0xA1, 6, 0, 0, 0, 0, 0x02, (byte)0xB };	// Range: 0x0A..0x11
		byte[] data = { 0, 0, 0xF };											// Range: 0xB00..0xB02
		
		final int REGISTER_X = 0xA;
		
		AddressingModeHelper adressingModeHelper = createAddressingModesHelper(programData, data);
	
		int actual = adressingModeHelper.fetchIndexedIndirectAddress(PROGRAM_COUNTER, REGISTER_X);
		
		// Memory map:
		//	Ah)   Opcode: A1h
		//	Bh)   06h
		//  10h)  02h
		//  11h)   Bh
		//  B02h)  Fh

		// Register X: 6h
		
		// Ah + 6h = 10h --> Address on 10h+11h: B02h --> Value: Fh
		assertEquals(0xF, actual);
	}
	

	// Helper methods

	private AddressingModeHelper createAddressingModesHelper(byte[] programBytes) {
		return createAddressingModesHelper(programBytes, null);
	}

	private AddressingModeHelper createAddressingModesHelper(byte[] programBytes, byte[] dataBytes) {
		List<Memory> memory = new ArrayList<>();

		final int START_ADDRESS_PROGRAM = PROGRAM_COUNTER;
		final int START_ADDRESS_DATA = 0xB00;
		
		Rom rom1 = new Rom(START_ADDRESS_PROGRAM, programBytes);
		memory.add(rom1);
		
		if(dataBytes != null) {
			Rom rom2 = new Rom(START_ADDRESS_DATA, dataBytes);
			memory.add(rom2);
		}

		Cpu cpu = Mockito.mock(Cpu.class);

		Bus bus = new DynamicBus(cpu, memory);

		return new AddressingModeHelper(bus);
	}
}
