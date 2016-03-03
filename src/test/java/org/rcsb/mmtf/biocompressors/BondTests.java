package org.rcsb.mmtf.biocompressors;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Bond;
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

public class BondTests {

	
	@Test
	public void testInterGroupBonds() throws IOException, StructureException {
		// Normal
		assertEquals(getInterBonds("1QMZ"), 2236);
		// 	Disulphide
		assertEquals(getInterBonds("2QWO"), 954);
		// Covalent ligand
		assertEquals(getInterBonds("4QDV"), 2290);
		// DNA 
		assertEquals(getInterBonds("4XSN"), 0);
		
	}
	
	public int getInterBonds(String pdbId) throws IOException, StructureException{
		AtomCache cache = new AtomCache();
		cache.setUseMmCif(true);
		cache.setFetchBehavior(FetchBehavior.FETCH_FILES);
		FileParsingParameters params = cache.getFileParsingParams();
		params.setCreateAtomBonds(true);
		params.setAlignSeqRes(true);
		params.setParseBioAssembly(true);
		DownloadChemCompProvider dcc = new DownloadChemCompProvider();
		ChemCompGroupFactory.setChemCompProvider(dcc);
		dcc.checkDoFirstInstall();
		params.setLoadChemCompInfo(true);
		cache.setFileParsingParams(params);
		StructureIO.setAtomCache(cache);
		int counter =0;
		// Now get the structure
		Structure newStruc = StructureIO.getStructure(pdbId);
		// Now loop through the atoms
		for(Chain c: newStruc.getChains()){
			for(Group g: c.getAtomGroups()){
				List<Atom> theseAtoms = g.getAtoms();
				for(Atom a: theseAtoms){
					for(Bond b: a.getBonds()){
						Atom other = b.getOther(a);
						int indexOther = theseAtoms.indexOf(other);
						// Check if the index is within the group
						if(indexOther<0 || indexOther >= theseAtoms.size()){
							counter++;
						}
					}
				}
			}
		}
		return counter;
	}
}
