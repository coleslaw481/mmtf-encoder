package org.codec.biojavaencoder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.biojava.nbio.structure.StructureException;
import org.codec.dataholders.PDBGroup;
import org.codec.dataholders.CreateBasicStructure;
import org.codec.biojavaencoder.EncoderUtils;

public class EncodeStructure {

	/**
	 * Function to return a byte array of the messagepack data
	 * from an input PDB id
	 * @param PdbId
	 * @return
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws StructureException 
	 */
	public byte[] encodeFromPdbId(String pdbId) throws IllegalAccessException, InvocationTargetException, IOException, StructureException{
		// Get the two utility classes
		EncoderUtils eu = new EncoderUtils();
		CreateBasicStructure cbs = new CreateBasicStructure();
		Map<Integer, PDBGroup> totMap = new HashMap<Integer, PDBGroup>();
		// Parse the data into the basic data structure
		cbs.createFromJavaStruct(pdbId, totMap);
		// Compress the data and get it back out
		return eu.compressMainData(cbs.getBioStruct(), cbs.getHeaderStruct());
		
	}
	
}
