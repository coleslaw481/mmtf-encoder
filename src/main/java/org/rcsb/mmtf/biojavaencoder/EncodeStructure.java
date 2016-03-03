package org.rcsb.mmtf.biojavaencoder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.rcsb.mmtf.biojavaencoder.EncoderUtils;
import org.rcsb.mmtf.dataholders.PDBGroup;

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
		ParseFromBiojava cbs = new ParseFromBiojava();
		Map<Integer, PDBGroup> totMap = new HashMap<Integer, PDBGroup>();
		// Parse the data into the basic data structure
		cbs.createFromJavaStruct(pdbId, totMap);
		// Compress the data and get it back out
		return eu.compressMainData(cbs.getBioStruct(), cbs.getHeaderStruct());
	}
	
	/**
	 * Function to generate an MMTF from a biojava structure
	 * @param bioJavaStruct
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws StructureException
	 */
	public byte[] encodeFromBiojava(Structure bioJavaStruct) throws IllegalAccessException, InvocationTargetException, IOException, StructureException{
		// Get the two utility classes
		EncoderUtils eu = new EncoderUtils();
		ParseFromBiojava cbs = new ParseFromBiojava();
		Map<Integer, PDBGroup> totMap = new HashMap<Integer, PDBGroup>();
		// Parse the data into the basic data structure
		cbs.genFromJs(bioJavaStruct, totMap);
		// Compress the data and get it back out
		return eu.compressMainData(cbs.getBioStruct(), cbs.getHeaderStruct());
	}
	
	
	/**
	 * Function to encode the backbond data given a PDB id
	 * @param pdbId
	 * @return
	 * @throws IOException
	 * @throws StructureException
	 */
	public byte[] encodeBackBoneFromPdbId(String pdbId) throws IOException, StructureException{
		// Get the two utility classes
		EncoderUtils eu = new EncoderUtils();
		ParseFromBiojava cbs = new ParseFromBiojava();
		Map<Integer, PDBGroup> totMap = new HashMap<Integer, PDBGroup>();
		// Parse the data into the basic data structure
		cbs.createFromJavaStruct(pdbId, totMap);
		// Compress the data and get it back out
		return eu.getMessagePack(eu.compCAlpha(cbs.getCalphaStruct(), cbs.getHeaderStruct()));
	}
	
	
	
}
