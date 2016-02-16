package org.codec.dataholders;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.biojava.nbio.structure.AminoAcidImpl;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.AtomImpl;
import org.biojava.nbio.structure.Bond;
import org.biojava.nbio.structure.Calc;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.ChainImpl;
import org.biojava.nbio.structure.Element;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.GroupType;
import org.biojava.nbio.structure.HetatomImpl;
import org.biojava.nbio.structure.NucleotideImpl;
import org.biojava.nbio.structure.ResidueNumber;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.StructureImpl;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.io.FileParsingParameters;
import org.codec.dataholders.PDBGroup;


public class BioDataStruct extends BioDataStructBean implements CoreSingleStructure {
	// Function to update the BioDataStruct for a given 
	private static final Map<String, String> myMap;
	static {
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put("hetatm", "HETATM");
		aMap.put("amino", "ATOM");
		aMap.put("nucleotide", "ATOM");
		myMap = Collections.unmodifiableMap(aMap);
	}

	public BioDataStruct() {
	}
	public BioDataStruct(String in_code) throws IOException, StructureException{
		getBioDataStructFromPDBId(in_code);
	}


	//// ADD THE FUNCTIONS REQUIRED
	@SuppressWarnings("static-access")
	public BioDataStructBean findDataAsBean() throws IllegalAccessException, InvocationTargetException{
		// Cast this to the pure data
		BioDataStructBean newData = new BioDataStructBean();
		BeanUtils bu = new BeanUtils();
		bu.copyProperties(newData, this);
		return newData;
	}	


	private Structure getBioJavaFromBioDS() {
		// Function to create a Biojava object from a BioDS
		// create the Empty structures
		Structure my_structure = new StructureImpl();
		// Get the number of items in each list
		Chain curr_chain = null;
		Group curr_group = null;
		// Now lets loop through the atoms
		for (int i = 0; i < get_atom_site_Cartn_x().size(); i++) {
			// NOW MAKE AN ATOM
			// Make a new atom
			HashMap<String, Object> info_map = set_atom_props(i);
			Atom new_atom = (Atom) info_map.get("ATOM");
			Group new_group = (Group) info_map.get("GROUP");
			Chain new_chain = (Chain) info_map.get("CHAIN");
			// Now add t
			// Check if it's a new chain
			if (curr_chain == null){
				curr_chain  = new_chain;
				my_structure.addChain(curr_chain);		    		
			}
			else if (new_chain.getChainID() != curr_chain.getChainID()){
				curr_chain  = new_chain;
				my_structure.addChain(curr_chain);
			}
			// Check if it's a new group -> make it if it is
			if (curr_group == null){
				curr_group = new_group;
				curr_chain.addGroup(curr_group);		    		
			}
			else if (new_group.getResidueNumber().hashCode() != curr_group.getResidueNumber().hashCode()){
				// Check the group
				curr_group = new_group;
				curr_chain.addGroup(curr_group);
			}
			// Add an atom to a group
			curr_group.addAtom(new_atom);

		}
		return my_structure;
	}

	private HashMap<String, Object> set_atom_props(int i) {		    
		// Loop through the attributes of this atom
		Atom a = new AtomImpl();
		Chain c = new ChainImpl();
		// ATOM LEVEL INFORMATION
		// This data item is a reference to item _chem_comp_atom.atom_id defined in category CHEM_COMP_ATOM which is stored in the Chemical Component Dictionary. This atom identifier uniquely identifies each atom within each chemical component.
		String chem_name = (String) get_atom_site_label_atom_id().get(i);
		a.setName(chem_name);
		// Cartesian coordinate components describing the position of this atom site.
		try {
			a.setX(get_atom_site_Cartn_x().get(i));
			a.setY(get_atom_site_Cartn_y().get(i));
			a.setZ(get_atom_site_Cartn_z().get(i));
			// Isotropic atomic displacement parameter
			a.setTempFactor(get_atom_site_B_iso_or_equiv().get(i));
			// The fraction of the atom present at this atom position.
			a.setOccupancy(get_atom_site_occupancy().get(i));
		} catch (Exception ClassCastException) {
			a.setX(Double.parseDouble(get_atom_site_Cartn_x().get(i).toString()));
			a.setY(Double.parseDouble(get_atom_site_Cartn_y().get(i).toString()));
			a.setZ(Double.parseDouble(get_atom_site_Cartn_z().get(i).toString()));
			// Isotropic atomic displacement parameter
			a.setTempFactor(Float.parseFloat(get_atom_site_B_iso_or_equiv().get(i).toString()));
			// The fraction of the atom present at this atom position.
			a.setOccupancy(Float.parseFloat(get_atom_site_occupancy().get(i).toString()));
		} 


		Integer seq_num;
		try {
			a.setPDBserial((Integer) get_atom_site_id().get(i));
			seq_num = (Integer) get_atom_site_auth_seq_id().get(i);
		} catch (Exception ClassCastException) {
			a.setPDBserial(Integer.parseInt(get_atom_site_id().get(i).toString()));
			seq_num = Integer.parseInt(get_atom_site_auth_seq_id().get(i).toString());

		}

		a.setElement(Element.valueOfIgnoreCase(get_atom_site_symbol().get(i)));


		// This item is a uniquely identifies for each alternative site for this atom position.
		a.setAltLoc(get_atom_site_label_alt_id().get(i).toString().charAt(0));

		// NOW LET'S CONSIDER GROUP LEVEL DATA
		// This data item is a place holder for the tags used by the PDB to identify coordinate records (e.g. ATOM or HETATM).        
		String group_type = (String) get_atom_site_group_PDB().get(i);
		Group new_g = null;
		if (group_type.equals("ATOM".hashCode())){
			new_g = new AminoAcidImpl();
		}
		else if(group_type.equals("ATOM")){
			new_g = new NucleotideImpl();
		}
		else if(group_type.equals("HETATM")){
			new_g = new HetatomImpl();
		}
		else{
			System.out.println(group_type.hashCode());
			System.out.println("ATOM".hashCode());

			// Throw an exception here
		}
		//        // This data item is an author defined alternative to the value of _atom_site.label_comp_id. This item holds the PDB 3-letter-code residue names
		//		try{
		//			new_g.setPDBName((String) get_atom_site_auth_comp_id().get(i));
		//
		//		}
		//		catch(Exception NullPointerException) {
		//			Object me = get_atom_site_auth_comp_id().get(i);
		//			@SuppressWarnings("unused")
		//			Object me2 = get_atom_site_auth_comp_id().get(i);
		//			
		//		}
		ResidueNumber res_num = new ResidueNumber();
		res_num.setChainId((String) get_atom_site_asym_id().get(i));
		if (get_atom_site_pdbx_PDB_ins_code().get(i)==null){
			res_num.setInsCode("?".charAt(0));
		}
		else{
			res_num.setInsCode((Character) get_atom_site_pdbx_PDB_ins_code().get(i).charAt(0));        	
		}

		// This data item is an author defined alternative to the value of _atom_site.label_seq_id. This item holds the PDB residue number.
		res_num.setSeqNum(seq_num);
		new_g.setResidueNumber(res_num);
		// This data item corresponds to the PDB insertion code.


		//////// SOME UNUSED DATA HERE
		// This data item is a reference to item _chem_comp.id defined in category CHEM_COMP. This item is the primary identifier for chemical components which may either be mononers in a polymeric entity or complete non-polymer entities.
		//String res_name = (String) my_data.get_atom_site_label_comp_id().get(i);
		// This data item is a reference to _entity_poly_seq.num defined in the ENTITY_POLY_SEQ category. This item is used to maintain the correspondence between the chemical sequence of a polymeric entity and the sequence information in the coordinate list and in may other structural categories. This identifier has no meaning for non-polymer entities.
		//int seq_num = (Integer) my_data.get_atom_site_label_entity_poly_seq_num().get(i);

		//// NOW LET'S CONSIDER CHAIN RELATED INFORMATION
		//  This data item is an author defined alternative to the value of _atom_site.label_asym_id. This item holds the PDB chain identifier.
		c.setChainID((String) get_atom_site_asym_id().get(i));
		// This data item is reference to item _struct_asym.id defined in category STRUCT_ASYM. This item identifies an instance of particular entity in the deposited coordinate set. For a structure determined by crystallographic method this corresponds to a unique identifier within the cyrstallographic asymmetric unit.
		c.setInternalChainID((String) get_atom_site_label_asym_id().get(i));
		// This data item identifies the model number in an ensemble of coordinate data.
		//my_data.get_atom_site_pdbx_PDB_model_num().get(i));

		// The net integer charge assigned to this atom.
		// Optional uncertainties assoicated with coordinate positions, occupancies and temperature factors.
		// Cartesian coordinate components describing the position of this atom site.
		// THIS INFO CANNOT BE ADDED TO THE COLUMN
		//        my_data.get_atom_site_pdbx_formal_charge").add("?");
		//        my_data.get_atom_site_Cartn_x_esd").add("?");
		//        my_data.get_atom_site_Cartn_y_esd").add("?");
		//        my_data.get_atom_site_Cartn_z_esd").add("?");
		//        // Isotropic atomic displacement parameter
		//        my_data.get_atom_site_B_iso_or_equiv_esd").add("?");
		//        // The fraction of the atom present at this atom position.
		//        my_data.get_atoam_site.occupancy_esd").add("?");
		HashMap<String, Object> hashMap= new HashMap<String, Object>();
		hashMap.put("ATOM", a);
		hashMap.put("CHAIN", c);
		hashMap.put("GROUP", new_g);
		return hashMap;
	}


	public int findNumAtoms() {
		return get_atom_site_Cartn_x().size();
	}

	private BioDataStruct getBioDataStructFromPDBId(String input_id) throws IOException, StructureException {
		AtomCache cache = new AtomCache();
		cache.setUseMmCif(true);
		FileParsingParameters params = cache.getFileParsingParams();
		params.setLoadChemCompInfo(true);
		params.setCreateAtomBonds(true);
		StructureIO.setAtomCache(cache);
		Structure my_struct = StructureIO.getStructure(input_id);
		return getBioDataStructFromBioJava(my_struct);
		// and let's count how many chains are in this structure.
	}

	private BioDataStruct getBioDataStructFromBioJava(Structure my_struct){
		// Get the number of models
		Integer numModels = my_struct.nrModels();
		// 
		List<List<Integer>> thisList= new ArrayList<List<Integer>>();
		this.setNumModels(numModels);
		Map<Integer, PDBGroup> thisMap = new HashMap<Integer, PDBGroup>();
		for (int i=0; i<numModels; i++){
			// Now let's loop over all the atom site record
			List<Chain> chains = my_struct.getModel(i);
			// Get the number of chains
			System.out.println(" # chains: " + chains.size());
			// Get the residue list
			// Set the PDB Code
			this.setPdbCode(my_struct.getPDBCode());
			// Take the atomic information and place in a Hashmap
			for (Chain c : chains) {
				// 
				List<Integer> newChainList = new ArrayList<Integer>();
				thisList.add(newChainList);
				// Get the groups
				String chain_id = c.getChainID();
				for (Group g : c.getAtomGroups()) {
					// Now loop through anf get the coords
					String res_id = g.getPDBName();
					// Get the atomic info required - this is the unique identifier of the group 
					List<String> atomInfo = getAtomInfo(g);
					int hashCode = getHashFromStringList(atomInfo);
					newChainList.add(hashCode);
					// If we need this new information 
					if (thisMap.containsKey(hashCode)==false){
						PDBGroup outGroup = new PDBGroup();
						outGroup.setAtomInfo(atomInfo);
						// Now get the bond list (lengths, orders and indices)
						createBondList(g,outGroup);
						thisMap.put(hashCode, outGroup);
					}
					// 
					ResidueNumber res_num = g.getResidueNumber();
					for (Atom a : g.getAtoms()) {
						updateStruct(a, chain_id, res_id, res_num, c, g);
					}

				}
			}
		}
		this.setGroupList(thisList);
		this.setGroupMap(thisMap);
		return this;
	}

	private int getHashFromStringList(List<String> strings){
		int prime = 31;
		int result = 1;
		for( String s : strings )
		{
			result = result * prime + s.hashCode();
		}
		return result;
	}
	private List<String> getAtomInfo(Group g){
		int numAtoms = g.getAtoms().size();
		int arraySize = numAtoms*2+2;
		List<String> outString = new ArrayList<String>(arraySize);
		outString.add(g.getPDBName());
		GroupType gType = g.getType();
		// A string indicating if it is HETARM or ATOM
		String gS = gType.toString();
		String gss = myMap.get(gS);
		outString.add(gss);
		for (Atom a: g.getAtoms()){
			outString.add(a.getElement().toString());
		}
		for (Atom a: g.getAtoms()){
			outString.add(a.getName());
		}
		return outString;
	}
	private void createBondList(Group g, PDBGroup outGroup) {
		List<Atom> atoms = g.getAtoms();
		int n = atoms.size();
		if (n == 0) {
			System.out.println("creating empty bond list");
		}
		int[] bondList = new int[n];
		int[] bondDist = new int[n];
		int[] bondOrder = new int[n];

		Arrays.fill(bondList, -1);

		for (int i = 0; i < n; i++) {
			Atom a = atoms.get(i);
			for (Bond b: a.getBonds()) {
				Atom other = b.getOther(a);
				int index = atoms.indexOf(other);
				int order = b.getBondOrder();
				if (index == -1) {
					continue;
				}
				if (index > i && bondList[index] == -1) {
					//
					bondList[index] = i;
					bondDist[index] = (int)Math.round(Calc.getDistance(a, other)*1000);
					bondOrder[index] = order;
				}
			}
		}
		outGroup.setBondOrders(intLToList(bondOrder));
		//		outGroup.setBondLengths(intLToList(bondDist));
		outGroup.setBondIndices (intLToList(bondList));
	}

	private List<Integer> intLToList(int[] ints){
		List<Integer> intList = new ArrayList<Integer>();
		for (int index = 0; index < ints.length; index++)
		{
			intList.add(ints[index]);
		}
		return intList;
	}
	private void updateStruct(Atom a, String chain_id, String res_id,
			ResidueNumber res_num, Chain c, Group g) {

		this.get_atom_site_id().add(a.getPDBserial());
		// Atom symbol
		Element ele = a.getElement();
		this.get_atom_site_symbol().add(ele.toString());
		// This data item is an author defined alternative to the value of
		// _atom_site.label_asym_id. This item holds the PDB chain
		// identifier.
		this.get_atom_site_asym_id().add(chain_id);
		// This data item is an author defined alternative to the value of
		// _atom_site.label_atom_id. This item holds the PDB atom name.
		//		this.get_atom_site_auth_atom_id().add(a.getName());
		//		// This data item is an author defined alternative to the value of
		//		// _atom_site.label_comp_id. This item holds the PDB 3-letter-code
		//		// residue names
		//		this.get_atom_site_auth_comp_id().add(res_id);
		// This data item is an author defined alternative to the value of
		// _atom_site.label_seq_id. This item holds the PDB residue number.
		this.get_atom_site_auth_seq_id().add(res_num.getSeqNum());
		// This data item corresponds to the PDB insertion code.
		Character me = res_num.getInsCode();
		if (res_num.getInsCode()==null){
			this.get_atom_site_pdbx_PDB_ins_code().add(null);

		}
		else{
			this.get_atom_site_pdbx_PDB_ins_code().add(me.toString());
		}
		// This data item identifies the model number in an ensemble of
		// coordinate data.
		//		this.get_atom_site_pdbx_PDB_model_num().add(1);
		// This data item is a place holder for the tags used by the PDB to
		// identify coordinate records (e.g. ATOM or HETATM).
		this.get_atom_site_group_PDB().add(myMap.get(g.getType().toString()));
		// This item is a uniquely identifies for each alternative site for
		// this atom position.
		if (a.getAltLoc()==" ".charAt(0)){
			this.get_atom_site_label_alt_id().add("?");
		}
		else{
			this.get_atom_site_label_alt_id().add(a.getAltLoc().toString());

		}
		// This data item is reference to item _struct_asym.id defined in
		// category STRUCT_ASYM. This item identifies an instance of
		// particular entity in the deposited coordinate set. For a
		// structure determined by crystallographic method this corresponds
		// to a unique identifier within the cyrstallographic asymmetric
		// unit.
		this.get_atom_site_label_asym_id().add(c.getInternalChainID().toString());
		// This data item is a reference to item _chem_comp_atom.atom_id
		// defined in category CHEM_COMP_ATOM which is stored in the
		// Chemical Component Dictionary. This atom identifier uniquely
		// identifies each atom within each chemical component.
		this.get_atom_site_label_atom_id().add(a.getName());
		// This data item is a reference to item _chem_comp.id defined in
		// category CHEM_COMP. This item is the primary identifier for
		// chemical components which may either be mononers in a polymeric
		// entity or complete non-polymer entities.
		this.get_atom_site_label_comp_id().add(g.getPDBName());
		// This data item is a reference to _entity.id defined in the ENTITY
		// category. This item is used to identify chemically distinct
		// portions of the molecular structure (e.g. polymer chains,
		// ligands, solvent).
		this.get_atom_site_label_entity_id().add(myMap.get(g.getType().toString()));
		// This data item is a reference to _entity_poly_seq.num defined in
		// the ENTITY_POLY_SEQ category. This item is used to maintain the
		// correspondence between the chemical sequence of a polymeric
		// entity and the sequence information in the coordinate list and in
		// may other structural categories. This identifier has no meaning
		// for non-polymer entities.
		this.get_atom_site_label_entity_poly_seq_num().add(res_num.getSeqNum());
		// Cartesian coordinate components describing the position of this
		// atom site.
		this.get_atom_site_Cartn_x().add(a.getX());
		this.get_atom_site_Cartn_y().add(a.getY());
		this.get_atom_site_Cartn_z().add(a.getZ());
		// Isotropic atomic displacement parameter
		this.get_atom_site_B_iso_or_equiv().add(a.getTempFactor());
		// The fraction of the atom present at this atom position.
		this.get_atom_site_occupancy().add(a.getOccupancy());
		// The net integer charge assigned to this atom.
		//		this.get_atom_site_pdbx_formal_charge().add(null);
		//		// Optional uncertainties assoicated with coordinate positions,
		//		// occupancies and temperature factors.
		//		// Cartesian coordinate components describing the position of this
		//		// atom site.
		//		this.get_atom_site_Cartn_x_esd().add(null);
		//		this.get_atom_site_Cartn_y_esd().add(null);
		//		this.get_atom_site_Cartn_z_esd().add(null);
		//		// Isotropic atomic displacement parameter
		//		this.get_atom_site_B_iso_or_equiv_esd().add(null);
		//		// The fraction of the atom present at this atom position.
		//		this.get_atom_site_occupancy_esd().add(null);
	}

	public void fillDataStruct(String key, Object part) {
		// Function to fill a BioDataStrcut given a key -> which maps onto a field and an object (the value)
		if (key=="_atom_site_id"){
			get_atom_site_id().add((Integer) part);
		}
		else if (key=="_atom_site_symbol"){
			get_atom_site_symbol().add((String) part);
		}
		else if (key=="_atom_site_asym_id"){
			get_atom_site_asym_id().add((String) part);
		}
		//			else if (key=="_atom_site_auth_atom_id"){
		//			get_atom_site_auth_atom_id().add((String) part);
		//			}
		//			else if (key=="_atom_site_auth_comp_id"){
		//			get_atom_site_auth_comp_id().add((String) part);
		//			}
		else if (key=="_atom_site_auth_seq_id"){
			get_atom_site_auth_seq_id().add((Integer) part);
		}
		else if (key=="_atom_site_pdbx_PDB_ins_code"){
			get_atom_site_pdbx_PDB_ins_code().add((String) part);
		}
		//			else if (key=="_atom_site_pdbx_PDB_model_num"){
		//			get_atom_site_pdbx_PDB_model_num().add((Integer) part);
		//			}
		else if (key=="_atom_site_group_PDB"){
			get_atom_site_group_PDB().add((String) part);
		}
		else if (key=="_atom_site_label_alt_id"){
			get_atom_site_label_alt_id().add((String) part);
		}
		else if (key=="_atom_site_label_asym_id"){
			get_atom_site_label_asym_id().add((String) part);
		}
		else if (key=="_atom_site_label_atom_id"){
			get_atom_site_label_atom_id().add((String) part);
		}
		else if (key=="_atom_site_label_comp_id"){
			get_atom_site_label_comp_id().add((String) part);
		}
		else if (key=="_atom_site_label_entity_id"){
			get_atom_site_label_entity_id().add((String) part);
		}
		else if (key=="_atom_site_label_entity_poly_seq_num"){
			get_atom_site_label_entity_poly_seq_num().add((Integer) part);
		}
		else if (key=="_atom_site_Cartn_x"){
			get_atom_site_Cartn_x().add((Double) part);
		}
		else if (key=="_atom_site_Cartn_y"){
			get_atom_site_Cartn_y().add((Double) part);
		}
		else if (key=="_atom_site_Cartn_z"){
			get_atom_site_Cartn_z().add((Double) part);
		}
		else if (key=="_atom_site_B_iso_or_equiv"){
			get_atom_site_B_iso_or_equiv().add((Float) part);
		}
		else if (key=="_atom_site_occupancy"){
			get_atom_site_occupancy().add((Float) part);
		}
		//			else if (key=="_atom_site_pdbx_formal_charge"){
		//			get_atom_site_pdbx_formal_charge().add((Double) part);
		//			}
		//			else if (key=="_atom_site_Cartn_x_esd"){
		//			get_atom_site_Cartn_x_esd().add((Double) part);
		//			}
		//			else if (key=="_atom_site_Cartn_y_esd"){
		//			get_atom_site_Cartn_y_esd().add((Double) part);
		//			}
		//			else if (key=="_atom_site_Cartn_z_esd"){
		//			get_atom_site_Cartn_z_esd().add((Double) part);
		//			}
		//			else if (key=="_atom_site_B_iso_or_equiv_esd"){
		//			get_atom_site_B_iso_or_equiv_esd().add((Double) part);
		//			}
		//			else if (key=="_atom_site_occupancy_esd"){
		//			get_atom_site_occupancy_esd().add((Double) part);
		//			}
	}

	public NoFloatDataStruct findDataAsNoFloatStruct() {
		//
		return null;
	}
	public Structure findDataAsBioJava() {
		// TODO Auto-generated method stub
		// Function to generate a Biojava structure from the available data
		Structure my_structure = getBioJavaFromBioDS();
		my_structure.setPDBCode(this.getPdbCode());
		return my_structure;
	}

	public BioDataStruct findDataAsBioDataStruct() {
		// TODO Auto-generated method stub
		return this;
	}
	public String findStructureCode() {
		// TODO Auto-generated method stub
		return this.getPdbCode();
	}
	public void setStructureCode(String my_code) {
		this.setPdbCode(my_code);

	}
	public Map<String, Object> findDataAsHashMap()
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// TODO Auto-generated method stub
		return null;
	}


}
