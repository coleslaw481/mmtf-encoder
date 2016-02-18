package org.codec.dataholders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4d;

import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Bond;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Element;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.GroupType;
import org.biojava.nbio.structure.JournalArticle;
import org.biojava.nbio.structure.PDBCrystallographicInfo;
import org.biojava.nbio.structure.PDBHeader;
import org.biojava.nbio.structure.ResidueNumber;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.io.mmcif.chem.PolymerType;
import org.biojava.nbio.structure.quaternary.BioAssemblyInfo;
import org.biojava.nbio.structure.quaternary.BiologicalAssemblyTransformation;
import org.biojava.nbio.structure.secstruc.DSSPParser;
import org.biojava.nbio.structure.secstruc.SecStrucCalc;
import org.biojava.nbio.structure.secstruc.SecStrucState;
import org.biojava.nbio.structure.xtal.CrystalCell;
import org.biojava.nbio.structure.xtal.SpaceGroup;
import org.codec.dataholders.BioAssemblyInfoNew;
import org.codec.dataholders.BiologicalAssemblyTransformationNew;
import org.codec.dataholders.PDBGroup;
import org.codec.dataholders.CodeHolders;


public class CreateBasicStructure {
	
	// Instances availble to the class of the main, calpha and header data structures
	private BioDataStruct bioStruct = new BioDataStruct();
	private CalphaBean calphaStruct = new CalphaBean();
	private HeaderBean headerStruct = new HeaderBean();
	// A class to store encoding information
	private CodeHolders codeHolder = new CodeHolders();
	// Now a list to store the bonds
	private  List<Bond> totBonds = new ArrayList<Bond>();
	
	public BioDataStruct getBioStruct() {
		return bioStruct;
	}

	
	public void setBioStruct(BioDataStruct bioStruct) {
		this.bioStruct = bioStruct;
	}


	public CalphaBean getCalphaStruct() {
		return calphaStruct;
	}


	public void setCalphaStruct(CalphaBean calphaStruct) {
		this.calphaStruct = calphaStruct;
	}


	public HeaderBean getHeaderStruct() {
		return headerStruct;
	}


	public void setHeaderStruct(HeaderBean headerStruct) {
		this.headerStruct = headerStruct;
	}
	
	// A map for the different names of groups
	private static final Map<String, String> myMap;
	static {
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put("hetatm", "HETATM");
		aMap.put("amino", "ATOM");
		aMap.put("nucleotide", "ATOM");
		myMap = Collections.unmodifiableMap(aMap);
	}



	/**
	 * Helper function to generate a main, calpha and header data form a PDB id
	 * @param input_id
	 * @param bioStructMap
	 * @throws IOException
	 * @throws StructureException
	 */
	public void createFromJavaStruct(String input_id, Map<Integer, PDBGroup> bioStructMap) throws IOException, StructureException{		
		// Get the structure from here
		Structure bioJavaStruct = StructureIO.getStructure(input_id);
		genFromJs(bioJavaStruct, bioStructMap);
	}


	/**
	 * Function to generate a main, calpha and header data form a biojava structure
	 * @param bioJavaStruct
	 * @param bioStructMap
	 * @throws IOException
	 * @throws StructureException
	 */
	public void genFromJs(Structure bioJavaStruct, Map<Integer, PDBGroup> bioStructMap) throws IOException, StructureException{
		SecStrucCalc ssp = new SecStrucCalc();
		try{
			ssp.calculate(bioJavaStruct, true);
		}

		catch(StructureException e) {
			try{
				DSSPParser.fetch(bioJavaStruct.getPDBCode(), bioJavaStruct, true); //download from PDB the DSSP result
			}
			catch(FileNotFoundException enew){
			}
			catch(Exception bige){
				System.out.println(bige);
			}
		}
		// Get the number of models
		Integer numModels = bioJavaStruct.nrModels();
		headerStruct.setPdbCode(bioJavaStruct.getPDBCode());

		// Now get hte xtalographic info
		PDBCrystallographicInfo xtalInfo = bioJavaStruct.getCrystallographicInfo();
		CrystalCell xtalCell = xtalInfo.getCrystalCell();
		SpaceGroup spaceGroup = xtalInfo.getSpaceGroup();
		if(xtalCell==null){

		}else{

			headerStruct.getUnitCell().add((float) xtalCell.getA());
			headerStruct.getUnitCell().add((float) xtalCell.getB());
			headerStruct.getUnitCell().add((float) xtalCell.getC());
			headerStruct.getUnitCell().add((float) xtalCell.getAlpha());
			headerStruct.getUnitCell().add((float) xtalCell.getBeta());
			headerStruct.getUnitCell().add((float) xtalCell.getGamma());

			if(spaceGroup==null){
				// This could be the I21 shown here
				headerStruct.setSpaceGroup("NA");
			}
			else{
				headerStruct.setSpaceGroup(spaceGroup.getShortSymbol());
			}
		}


		// GET THE HEADER INFORMATION
		PDBHeader header = bioJavaStruct.getPDBHeader();
		Map<Integer, BioAssemblyInfoNew> outMap = transformBioAssembly(header);
		headerStruct.setBioAssembly(outMap);
		List<List<Integer>> bioStructList= new ArrayList<List<Integer>>();
		bioStruct.setNumModels(numModels);
		Map<Integer,Integer> hashToRes = new HashMap<Integer,Integer>();
		Map<Integer,Integer> hashToCalphaRes = new HashMap<Integer,Integer>();
		headerStruct.setTitle(header.getTitle());
		headerStruct.setDescription(header.getDescription());
		headerStruct.setClassification(header.getClassification());
		headerStruct.setDepDate(header.getDepDate());
		headerStruct.setModDate(header.getModDate());
		headerStruct.setResolution(header.getResolution());
		headerStruct.setrFree(header.getRfree());
		JournalArticle myJournal = header.getJournalArticle();
		try{
			headerStruct.setDoi(myJournal.getDoi());
		}
		catch(NullPointerException e){

		}
		// headerStruct.setAuthors(header.getAuthors());
		// Set these containers
		int atomCounter = 0;
		int chainCounter =0;
		int resCounter =0;
		int totChains = 0;
		// Get the total number of chains
		for (int i=0; i<numModels; i++){		
			totChains += bioJavaStruct.getChains().size();
		}

		Map<Integer, PDBGroup> calphaBioStructMap = new HashMap<Integer, PDBGroup>();
		// Get these lists to keep track of everthing - and to give  a datastrcutrue at the end
		// List of chains per model
		int[] chainsPerModel = new int[numModels];
		// Set this list
		headerStruct.setChainsPerModel(chainsPerModel);
		byte[] charChainList = new byte[totChains*4];
		byte[] charAsymChainList = new byte[totChains*4];
		headerStruct.setChainList(charChainList);
		headerStruct.setAsymChainList(charAsymChainList);
		int[] groupsPerChain = new int[totChains];
		headerStruct.setGroupsPerChain(groupsPerChain);
		int[] calphaGroupsPerChain = new int[totChains];

		calphaStruct.setGroupsPerChain(calphaGroupsPerChain);
		headerStruct.setSequence(new ArrayList<String>());
		int thisResNum = 0;
		int calphaResCounter = 0;
		int bondCounter = 0;
		char[] outChar = new char[4];

		// Get all the atoms
		List<Atom> totAtoms = getAllAtoms(bioJavaStruct);
		for (int i=0; i<numModels; i++){
			// Now let's loop over all the atom site record
			List<Chain> chains = bioJavaStruct.getModel(i);
			// Get the number of chains
			// Get the residue list
			// Set the PDB Code
			bioStruct.setPdbCode(bioJavaStruct.getPDBCode());
			ArrayList<String> chainList = new ArrayList<String>();
			// Set the number of chains in this model
			chainsPerModel[i] = chains.size();
			//headerStruct.getModelList().add(chainList);
			// Take the atomic information and place in a Hashmap
			for (Chain c : chains) {
				headerStruct.getSequence().add(c.getSeqResSequence());
				// Set the author chain ID
				int chainIdLen = c.getChainID().length();
				c.getChainID().getChars(0, chainIdLen, outChar, 0);
				// Set the bytrarrat
				charChainList[chainCounter*4+0] = (byte) outChar[0];
				if(chainIdLen>1){
					charChainList[chainCounter*4+1] = (byte) outChar[1];
				}
				else{
					charChainList[chainCounter*4+1] = (byte) 0;
				}
				if(chainIdLen>2){
					charChainList[chainCounter*4+2] = (byte) outChar[2];
				}				
				else{
					charChainList[chainCounter*4+2] = (byte) 0;
				}
				if(chainIdLen>3){
					charChainList[chainCounter*4+3] = (byte) outChar[3];
				}				
				else{
					charChainList[chainCounter*4+3] =  (byte) 0;
				}
				// Set the asym chain id
				groupsPerChain[chainCounter] = c.getAtomGroups().size();
				calphaGroupsPerChain[chainCounter] = 0;
				// Add this chain to the list
				chainList.add(c.getChainID());
				// Now put this in this map
				//				headerStruct.getChainMap().put(c.getChainID(), c.getAtomGroups().size());
				chainCounter+=1;
				List<Integer> newChainList = new ArrayList<Integer>();
				bioStructList.add(newChainList);
				// Get the groups
				String chain_id = c.getChainID();
				int numBonds = 0;
				for (Group g : c.getAtomGroups()) {
					// Get any bonds between groups
					getInterGroupBond(g, totAtoms, atomCounter);
					// Count the number of bonds
					// Now loop through and get the coords
					String res_id = g.getPDBName();
					// Get the atomic info required - bioStruct is the unique identifier of the group 
					List<String> atomInfo = getAtomInfo(g);
					int hashCode = getHashFromStringList(atomInfo);
					newChainList.add(hashCode);
					// If we need bioStruct new information 
					if (hashToRes.containsKey(hashCode)==false){
						// Make a new group
						PDBGroup outGroup = new PDBGroup();
						// 
						if(atomInfo.remove(0)=="ATOM"){
							outGroup.setHetFlag(false);
						}
						else{
							outGroup.setHetFlag(true);
						}
						outGroup.setResName(atomInfo.remove(0));
						outGroup.setAtomInfo(atomInfo);
						// Now get the bond list (lengths, orders and indices)
						createBondList(g, outGroup); 
						getCharges(g, outGroup);
						// 
						bioStructMap.put(resCounter, outGroup);
						hashToRes.put(hashCode, resCounter);
						bioStruct.getResOrder().add(resCounter);
						resCounter+=1;
						numBonds = outGroup.getBondOrders().size();
					}
					else{
						// Add this to the residue order
						bioStruct.getResOrder().add(hashToRes.get(hashCode));	
						numBonds = bioStructMap.get(hashToRes.get(hashCode)).getBondOrders().size();
					}

					// Add the number of bonds 
					bondCounter+=numBonds;

					ResidueNumber res_num = g.getResidueNumber();
					SecStrucState props = (SecStrucState) g.getProperty("secstruc");
					//
					if(props==null){
						bioStruct.getSecStruct().add(codeHolder.dsspMap.get("NA"));
					}
					else{
						bioStruct.getSecStruct().add(codeHolder.dsspMap.get(props.getType().name));
					}
					// Now add the residue sequnece number
					bioStruct.get_atom_site_auth_seq_id().add(res_num.getSeqNum());


					// Set whether or not this is a calpha
					List<Atom> cAlphaGroup = new ArrayList<Atom>();
					boolean isInCalpha = false;
					for (Atom a : g.getAtoms()) {
						// Update the structure
						updateStruct(a, chain_id, res_id, res_num, c, g);

						// Now we can deal with altloc - but ignore for now
						if (a.getAltLoc()!=" ".charAt(0) || a.getAltLoc()==1){
							// Ignore alternate locations for now

						}

						// NOW THE CALPHA / PHOSPHATE / LIGAND STUFF
						// Update the backbone atoms

						if(g.getChemComp().getPolymerType()!=null){
							if(g.getChemComp().getPolymerType().equals(PolymerType.peptide)==true){
								if (a.getName().equals("CA") && a.getElement().toString().equals("C")){
									// Now add the calpha
									cAlphaGroup.add(a);
									isInCalpha= true;
								}
							}
							// GET THE PHOSPHATE
							else if(g.getChemComp().getPolymerType().equals(PolymerType.POLYNUCLEOTIDE_ONLY)==true){
								// Nucleotide core co-ordinates	
								if(a.getName().equals("P")){	
									cAlphaGroup.add(a);
									isInCalpha= true;
								}
							}
						}
						else{
							// Get the Ligands
							if(g.isWater()==false && g.getType().name().equals("HETATM")){
								cAlphaGroup.add(a);
								isInCalpha= true;
							}
						}
						atomCounter+=1;
					}
					// Now 
					if(isInCalpha){
						calphaGroupsPerChain[chainCounter-1] = calphaGroupsPerChain[chainCounter-1]+1;
						List<String> calphaAtomInfo = getAtomInfo(cAlphaGroup);
						/// Now consider the C-Alpha, phosophate and ligand cases
						int calphaHashCode = getHashFromStringList(calphaAtomInfo);
						newChainList.add(calphaHashCode);
						// If we need bioStruct new information 
						if (hashToCalphaRes.containsKey(calphaHashCode)==false){
							// Make a new group
							PDBGroup outGroup = new PDBGroup();
							// 
							if(calphaAtomInfo.remove(0)=="ATOM"){
								outGroup.setHetFlag(false);
							}
							else{
								outGroup.setHetFlag(true);
							}
							outGroup.setResName(calphaAtomInfo.remove(0));
							outGroup.setAtomInfo(calphaAtomInfo);
							// Now get the bond list (lengths, orders and indices)
							createBondList(g, outGroup); 
							getCharges(g, outGroup);
							// 
							calphaBioStructMap.put(calphaResCounter, outGroup);
							hashToCalphaRes.put(calphaHashCode, calphaResCounter);
							thisResNum = calphaResCounter;
							calphaResCounter+=1;
						}
						else{
							// Add this to the residue order
							thisResNum = hashToCalphaRes.get(calphaHashCode);						
						}						
						// Now set this as the answer
						calphaStruct.getResOrder().add(thisResNum);
						// Now add all these atoms to the calpha
						for(Atom a: cAlphaGroup){
							addCalpha(a, g, props, res_num, thisResNum);
						}
					}
				}
			}

		}
		// Set this  final information in the total datastruct
		bioStruct.setGroupList(bioStructList);
		bioStruct.setGroupMap(bioStructMap);	
		calphaStruct.setGroupMap(calphaBioStructMap);
		// Now set this header info
		headerStruct.setNumBonds(bondCounter+bioStruct.getInterGroupBondInds().size());
		headerStruct.setNumAtoms(atomCounter);
		headerStruct.setNumChains(chainCounter);
		headerStruct.setPdbCode(bioJavaStruct.getPDBCode());
	}

	/**
	 * Function to get all the atoms in the strucutre as a list
	 * @param bioJavaStruct
	 * @return
	 */
	private List<Atom> getAllAtoms(Structure bioJavaStruct) {
		// Get all the atoms
		List<Atom> theseAtoms = new ArrayList<Atom>();
		for (int i=0; i<bioJavaStruct.nrModels(); i++){

			List<Chain> chains = bioJavaStruct.getModel(i);
			for (Chain c : chains) {

				for (Group g : c.getAtomGroups()) {

					for(Atom a: g.getAtoms()){
						theseAtoms.add(a);					}
				}
			}
		}

		return theseAtoms;
	}

	/**
	 * Function to add a new calpha / phosophate / ligand atom
	 * @param a
	 * @param g
	 * @param props
	 * @param res_num
	 * @param thisRes
	 */
	private void addCalpha(Atom a, Group g, SecStrucState props, ResidueNumber res_num, int thisRes) {
		calphaStruct.setNumAtoms(calphaStruct.getNumAtoms()+1); 
		calphaStruct.getCartn_x().add((int) (a.getX()*1000));
		calphaStruct.getCartn_y().add((int) (a.getY()*1000));
		calphaStruct.getCartn_z().add((int) (a.getZ()*1000));
		// Get the residue name
		calphaStruct.get_atom_site_auth_seq_id().add(res_num.getSeqNum());
		calphaStruct.get_atom_site_label_entity_poly_seq_num().add(res_num.getSeqNum());
		// Now set the sec structure
		//
		if(props==null){
			calphaStruct.getSecStruct().add(codeHolder.dsspMap.get("NA"));

		}
		else{
			calphaStruct.getSecStruct().add(codeHolder.dsspMap.get(props.getType().name));
		}

	}

	
	/**
	 * Function to find the atomic charge information
	 * @param g
	 * @param outGroup
	 */
	private void getCharges(Group g, PDBGroup outGroup) {
		for(Atom a: g.getAtoms()){
			outGroup.getAtomCharges().add((int) a.getCharge());
		}

	}


	/**
	 * Function to generate a serializable biotransformation for storing
	 * in the messagepack
	 * @param header
	 * @return
	 */
	private Map<Integer, BioAssemblyInfoNew> transformBioAssembly(PDBHeader header) {
		// Here we need to iterate through and get the chain ids and the matrices
		Map<Integer, BioAssemblyInfo> inputBioAss = header.getBioAssemblies();
		Map<Integer, BioAssemblyInfoNew> outMap = new HashMap<Integer,BioAssemblyInfoNew>();
		// 

		for (Map.Entry<Integer, BioAssemblyInfo> entry : inputBioAss.entrySet()) {
			Map<Matrix4d,BiologicalAssemblyTransformationNew> matSet = new HashMap<Matrix4d,BiologicalAssemblyTransformationNew>();

			Integer key = entry.getKey();
			BioAssemblyInfo value = entry.getValue();
			// Make a new one of these
			BioAssemblyInfoNew newValue = new BioAssemblyInfoNew();
			outMap.put(key, newValue);
			// Copy across this info
			newValue.setMacromolecularSize(value.getMacromolecularSize());
			newValue.setId(value.getId());
			List<BiologicalAssemblyTransformationNew> outTrans = new ArrayList<BiologicalAssemblyTransformationNew>();
			for(BiologicalAssemblyTransformation transform: value.getTransforms()){

				// Get the chain and the matrix
				String thisId = transform.getId();
				String thisChain = transform.getChainId();
				Matrix4d thisMat = transform.getTransformationMatrix();
				double[] outList = new double[16];
				// 
				for(int i=0; i<4; i++){
					for(int j=0; j<4; j++){
						// Now set this element
						outList[j*4+i] = thisMat.getElement(i,j);
					}
				}
				if(matSet.containsKey(thisMat)){
					// Get it 
					BiologicalAssemblyTransformationNew bioTransNew = matSet.get(thisMat);
					bioTransNew.getChainId().add(thisChain);
				}
				else{
					// Create a new one
					BiologicalAssemblyTransformationNew bioTransNew = new BiologicalAssemblyTransformationNew();
					bioTransNew.setTransformation(outList);
					bioTransNew.setId(thisId);
					bioTransNew.getChainId().add(thisChain);
					matSet.put(thisMat, bioTransNew);
				}
			}
			for(BiologicalAssemblyTransformationNew thisTrans: matSet.values()){
				outTrans.add(thisTrans);
			}
			// Set the transform information
			newValue.setTransforms(outTrans);
		}

		return outMap;
	}


	/**
	 * Function to find a hash code from a list of strings
	 * @param strings
	 * @return
	 */
	private int getHashFromStringList(List<String> strings){
		int prime = 31;
		int result = 1;
		for( String s : strings )
		{
			result = result * prime + s.hashCode();
		}
		return result;
	}

	/**
	 * Function to find the atomic information from a group
	 * @param g
	 * @return
	 */
	private List<String> getAtomInfo(Group g){
		int numAtoms = g.getAtoms().size();
		int arraySize = numAtoms*2+2;
		List<String> outString = new ArrayList<String>(arraySize);
		GroupType gType = g.getType();
		// A string indicating if it is HETARM or ATOM
		String gS = gType.toString();
		String gss = myMap.get(gS);
		// A
		outString.add(gss);
		outString.add(g.getPDBName());
		for (Atom a: g.getAtoms()){
			outString.add(a.getElement().toString());
			outString.add(a.getName());
		}
		return outString;
	}


	/**
	 * Function to find the atomic information from a list of Atoms
	 * @param atomList
	 * @return
	 */
	private List<String> getAtomInfo(List<Atom> atomList){
		int numAtoms = atomList.size();
		int arraySize = numAtoms*2+2;
		List<String> outString = new ArrayList<String>(arraySize);
		GroupType gType = atomList.get(0).getGroup().getType();
		// A string indicating if it is HETARM or ATOM
		String gS = gType.toString();
		String gss = myMap.get(gS);
		// A
		outString.add(gss);
		outString.add(atomList.get(0).getGroup().getPDBName());
		for (Atom a: atomList){
			outString.add(a.getElement().toString());
			outString.add(a.getName());
		}
		return outString;
	}


	/**
	 * Function to find bonds between groups
	 * @param g
	 * @param totAtoms
	 * @param atomCounter
	 */
	private void getInterGroupBond(Group g, List<Atom> totAtoms, int atomCounter){
		// Get the atoms
		List<Atom> atoms = g.getAtoms();
		int n = atoms.size();
		if (n == 0) {
			System.out.println("creating empty bond list");
		}
		for (int i = 0; i < n; i++) {
			// Get the  atom
			Atom a = atoms.get(i);
			for (Bond b: a.getBonds()) {
				Atom other = b.getOther(a);
				int index = atoms.indexOf(other);
				int order = b.getBondOrder();
				if (index<0){
					int newInd = totAtoms.indexOf(other);
					if(newInd > -1){
						// 
						if(totBonds.indexOf(b)==-1){
							return;
						}
						totBonds.add(b);
						bioStruct.getInterGroupBondInds().add(newInd);
						bioStruct.getInterGroupBondInds().add(i+atomCounter);
						bioStruct.getInterGroupBondOrders().add(order);
					}
				}
			}
		}

	}

	/**
	 * Function to generate lists for the bonds in the group
	 * @param g
	 * @param outGroup
	 */
	private void createBondList(Group g, PDBGroup outGroup) {
		List<Atom> atoms = g.getAtoms();
		int n = atoms.size();
		if (n == 0) {
			System.out.println("creating empty bond list");
		}

		// Lists to hold bond indices and orders
		List<Integer> bondList = new ArrayList<Integer>();
		List<Integer> bondOrder = new ArrayList<Integer>();

		for (int i = 0; i < n; i++) {
			// Get the  atom
			Atom a = atoms.get(i);
			for (Bond b: a.getBonds()) {
				Atom other = b.getOther(a);
				int index = atoms.indexOf(other);
				int order = b.getBondOrder();

				if (index > i) {
					// Add the information
					bondList.add(index);
					bondList.add(i);
					bondOrder.add(order);
				}
			}
		}
		outGroup.setBondOrders(bondOrder);
		outGroup.setBondIndices(bondList);
	}


	/**
	 * Function to update the structure with this atomic information
	 * @param a
	 * @param chain_id
	 * @param res_id
	 * @param res_num
	 * @param c
	 * @param g
	 */
	private void updateStruct(Atom a, String chain_id, String res_id,
			ResidueNumber res_num, Chain c, Group g) {

		bioStruct.get_atom_site_id().add(a.getPDBserial());
		// Atom symbol
		Element ele = a.getElement();
		bioStruct.get_atom_site_symbol().add(ele.toString());
		bioStruct.get_atom_site_asym_id().add(chain_id);
		// bioStruct data item corresponds to the PDB insertion code.
		Character me = res_num.getInsCode();
		if (res_num.getInsCode()==null){
			bioStruct.get_atom_site_pdbx_PDB_ins_code().add(null);
		}
		else{
			bioStruct.get_atom_site_pdbx_PDB_ins_code().add(me.toString());
		}
		// identify coordinate records (e.g. ATOM or HETATM).
		bioStruct.get_atom_site_group_PDB().add(myMap.get(g.getType().toString()));
		// bioStruct item is a uniquely identifies for each alternative site for
		// bioStruct atom position.
		if (a.getAltLoc()==" ".charAt(0)){
			bioStruct.get_atom_site_label_alt_id().add("?");
		}
		else{
			bioStruct.get_atom_site_label_alt_id().add(a.getAltLoc().toString());
		}
		// bioStruct data item is reference to item _struct_asym.id defined in
		// category STRUCT_ASYM. bioStruct item identifies an instance of
		// particular entity in the deposited coordinate set. For a
		// structure determined by crystallographic method bioStruct corresponds
		// to a unique identifier within the cyrstallographic asymmetric
		// unit.
		bioStruct.get_atom_site_label_asym_id().add(c.getInternalChainID().toString());
		// bioStruct data item is a reference to item _chem_comp_atom.atom_id
		// defined in category CHEM_COMP_ATOM which is stored in the
		// Chemical Component Dictionary. bioStruct atom identifier uniquely
		// identifies each atom within each chemical component.
		bioStruct.get_atom_site_label_atom_id().add(a.getName());
		// bioStruct data item is a reference to item _chem_comp.id defined in
		// category CHEM_COMP. bioStruct item is the primary identifier for
		// chemical components which may either be mononers in a polymeric
		// entity or complete non-polymer entities.
		bioStruct.get_atom_site_label_comp_id().add(g.getPDBName());
		// bioStruct data item is a reference to _entity.id defined in the ENTITY
		// category. bioStruct item is used to identify chemically distinct
		// portions of the molecular structure (e.g. polymer chains,
		// ligands, solvent).
		bioStruct.get_atom_site_label_entity_id().add(myMap.get(g.getType().toString()));
		// bioStruct data item is a reference to _entity_poly_seq.num defined in
		// the ENTITY_POLY_SEQ category. bioStruct item is used to maintain the
		// correspondence between the chemical sequence of a polymeric
		// entity and the sequence information in the coordinate list and in
		// may other structural categories. bioStruct identifier has no meaning
		// for non-polymer entities.
		bioStruct.get_atom_site_label_entity_poly_seq_num().add(res_num.getSeqNum());
		// Cartesian coordinate components describing the position of bioStruct
		// atom site.
		bioStruct.get_atom_site_Cartn_x().add(a.getX());
		bioStruct.get_atom_site_Cartn_y().add(a.getY());
		bioStruct.get_atom_site_Cartn_z().add(a.getZ());
		// Isotropic atomic displacement parameter
		bioStruct.get_atom_site_B_iso_or_equiv().add(a.getTempFactor());
		// The fraction of the atom present at bioStruct atom position.
		bioStruct.get_atom_site_occupancy().add(a.getOccupancy());
		// The net integer charge assigned to bioStruct atom.
	}



}
