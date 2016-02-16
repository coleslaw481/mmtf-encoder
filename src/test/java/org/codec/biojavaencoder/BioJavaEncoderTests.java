package org.codec.biojavaencoder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.biojava.nbio.structure.StructureException;

import static org.junit.Assert.*;


public class BioJavaEncoderTests {

	
	@Test
	public void testOverall() throws InvocationTargetException, IOException, StructureException, IllegalAccessException {
		
		EncodeStructure es = new EncodeStructure();
		byte[] cupData = es.encodeFromPdbId("4CUP");
		assertEquals(10821, cupData.length);
		byte[] aq1Data = es.encodeFromPdbId("1AQ1");
		assertEquals(21835, aq1Data.length);
	}
	
}


