package org.codec.integrationtest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.io.FileParsingParameters;
import org.biojava.nbio.structure.io.LocalPDBDirectory.FetchBehavior;
import org.biojava.nbio.structure.io.mmcif.ChemCompGroupFactory;
import org.biojava.nbio.structure.io.mmcif.DownloadChemCompProvider;
import org.codec.biojavaencoder.EncodeStructure;
import org.codec.decoder.BioJavaStructureInflator;
import org.codec.decoder.DecodeStructure;
import org.codec.decoder.ParsingParams;
import org.junit.Test;

public class ParseMMCIf {


	private AtomCache cache;
	private FileParsingParameters params;

	public ParseMMCIf(){

		cache = new AtomCache();
		cache.setUseMmCif(true);
		cache.setFetchBehavior(FetchBehavior.FETCH_FILES);
		params = cache.getFileParsingParams();
		params.setCreateAtomBonds(true);
		params.setAlignSeqRes(true);
		params.setParseBioAssembly(true);
		DownloadChemCompProvider dcc = new DownloadChemCompProvider();
		ChemCompGroupFactory.setChemCompProvider(dcc);
		dcc.setDownloadAll(true);
		dcc.checkDoFirstInstall();
		params.setLoadChemCompInfo(true);
		params.setUseInternalChainId(true);
		cache.setFileParsingParams(params);
	}


	@Test
	public void testAsymChainIds() throws IOException, StructureException, IllegalAccessException, InvocationTargetException {
		// Set the params
		params.setUseInternalChainId(true);
		cache.setFileParsingParams(params);
		testAll();
	}

	@Test
	public void testAuthChainIds() throws IOException, StructureException, IllegalAccessException, InvocationTargetException {
		// Set the params
		params.setUseInternalChainId(false);
		cache.setFileParsingParams(params);
		testAll();
	}


	/**
	 * Function to round trip everything based on the current params
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws StructureException
	 */
	private void testAll() throws IllegalAccessException, InvocationTargetException, IOException, StructureException{

		ParsingParams pp = new ParsingParams();
		pp.setParseInternal(params.isUseInternalChainId());
		StructureIO.setAtomCache(cache);
		// Standard structure
		String pdbId ="4cup";
		assertTrue(checkIfAtomsSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp)));
		// Weird NMR structure
		pdbId ="1O2F";
		assertTrue(checkIfAtomsSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp)));
		// Another weird structure (jose's suggestion) 
		pdbId ="3zyb";
		assertTrue(checkIfAtomsSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp)));
		// B-DNA structure
		pdbId ="1bna";
		assertTrue(checkIfAtomsSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp)));	
		// DNA strucute
		pdbId = "4Y60";
		assertTrue(checkIfAtomsSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp)));	
		// Large viral capsid
		pdbId ="3j3q";
		assertTrue(checkIfAtomsSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp)));
		// Ribosome
		pdbId = "4v5a";
		assertTrue(checkIfAtomsSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp)));		
	}

	/**
	 * 
	 * @return
	 * @throws IOException 
	 * @throws StructureException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private Structure roundTripStruct(String pdbId, ParsingParams pp) throws IOException, IllegalAccessException, InvocationTargetException, StructureException{
		EncodeStructure es = new EncodeStructure();
		FileUtils.writeByteArrayToFile(new File("pathname"), es.encodeFromBiojava(StructureIO.getStructure(pdbId)));
		DecodeStructure ds = new DecodeStructure();
		byte[] inArr = FileUtils.readFileToByteArray(new File("pathname"));
		BioJavaStructureInflator bjsi = new BioJavaStructureInflator();
		ds.getStructFromByteArray(inArr, bjsi, pp);
		Structure struct= bjsi.getStructure();
		return struct;
	}


	private boolean checkIfAtomsSame(Structure structOne, Structure structTwo) {
		int numModels = structOne.nrModels();
		if(numModels!=structTwo.nrModels()){
			System.out.println("ERROR - diff number models");
			return false;
		}
		for(int i=0;i<numModels;i++){
			List<Chain> chainsOne = structOne.getChains(i);
			List<Chain> chainsTwo = structTwo.getChains(i);
			if(chainsOne.size()!=chainsTwo.size()){
				System.out.println("ERROR - diff number chains");
				return false;
			}
			// Now loop over
			for(int j=0; j<chainsOne.size();j++){
				Chain chainOne = chainsOne.get(j);
				Chain chainTwo = chainsTwo.get(j);
				// Check they have the same chain id
				assertEquals(chainOne.getChainID(), chainTwo.getChainID());
				List<Group> groupsOne = chainOne.getAtomGroups();
				List<Group> groupsTwo = chainTwo.getAtomGroups();
				if(groupsOne.size()!=groupsTwo.size()){
					System.out.println("ERROR - diff number groups");
					return false;
				}
				for(int k=0; k<groupsOne.size();k++){
					Group groupOne = groupsOne.get(k);
					Group groupTwo = groupsTwo.get(k);
					// Get the first conf
					List<Atom> atomsOne = groupOne.getAtoms();
					List<Atom> atomsTwo = groupTwo.getAtoms();
					if(groupOne.getAltLocs().size()!=0){
						if(groupTwo.getAltLocs().size()!=groupOne.getAltLocs().size()){
							System.out.println("ERROR - diff number alt locs");
						}
						for(Group altLocOne: groupOne.getAltLocs()){
							for(Atom atomAltLocOne: altLocOne.getAtoms()){
								atomsOne.add(atomAltLocOne);
							}
						}
						for(Group altLocTwo: groupTwo.getAltLocs()){
							for(Atom atomAltLocTwo: altLocTwo.getAtoms()){
								atomsTwo.add(atomAltLocTwo);
							}
						}
					}


					if(atomsOne.size()!=atomsTwo.size()){
						System.out.println("ERROR - diff number atoms");
						System.out.println(groupOne.getPDBName()+" vs "+groupTwo.getPDBName());
						System.out.println(atomsOne.size()+" vs "+atomsTwo.size());
						return false;						
					}
					for(int l=0;l<atomsOne.size();l++){
						Atom atomOne = atomsOne.get(l);
						Atom atomTwo = atomsTwo.get(l);
						if(atomOne.toPDB().equals(atomTwo.toPDB())){

						}
						else{
							System.out.println("MINE"+atomOne.toPDB());
							System.out.println("BIOJAVA"+atomTwo.toPDB());
							return false;
						}
						if(i==0){
							if(atomOne.getBonds().size()!=atomTwo.getBonds().size()){

								System.out.println("DIFFERENT NUMBER OF BONDS: "+atomOne.getBonds().size()+" VS "+atomTwo.getBonds().size());
								return false;
							}
						}
					}
				}

			}
		}	
		return true;

	}
}
