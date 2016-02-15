package org.codec.dataholders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codec.dataholders.PDBGroup;


public class NonAtomDataStruct extends CoreDataStruct {

	public List<String> get_atom_site_symbol() {
		return _atom_site_symbol;
	}
	public void set_atom_site_symbol(List<String> _atom_site_symbol) {
		this._atom_site_symbol = _atom_site_symbol;
	}
	public List<String> get_atom_site_asym_id() {
		return _atom_site_asym_id;
	}
	public void set_atom_site_asym_id(List<String> _atom_site_asym_id) {
		this._atom_site_asym_id = _atom_site_asym_id;
	}
	public List<Integer> get_atom_site_auth_seq_id() {
		return _atom_site_auth_seq_id;
	}
	public void set_atom_site_auth_seq_id(List<Integer> _atom_site_auth_seq_id) {
		this._atom_site_auth_seq_id = _atom_site_auth_seq_id;
	}
	public List<String> get_atom_site_pdbx_PDB_ins_code() {
		return _atom_site_pdbx_PDB_ins_code;
	}
	public void set_atom_site_pdbx_PDB_ins_code(List<String> _atom_site_pdbx_PDB_ins_code) {
		this._atom_site_pdbx_PDB_ins_code = _atom_site_pdbx_PDB_ins_code;
	}
	public List<String> get_atom_site_group_PDB() {
		return _atom_site_group_PDB;
	}
	public void set_atom_site_group_PDB(List<String> _atom_site_group_PDB) {
		this._atom_site_group_PDB = _atom_site_group_PDB;
	}
	public List<String> get_atom_site_label_alt_id() {
		return _atom_site_label_alt_id;
	}
	public void set_atom_site_label_alt_id(List<String> _atom_site_label_alt_id) {
		this._atom_site_label_alt_id = _atom_site_label_alt_id;
	}
	public List<String> get_atom_site_label_asym_id() {
		return _atom_site_label_asym_id;
	}
	public void set_atom_site_label_asym_id(List<String> _atom_site_label_asym_id) {
		this._atom_site_label_asym_id = _atom_site_label_asym_id;
	}
	public List<String> get_atom_site_label_atom_id() {
		return _atom_site_label_atom_id;
	}
	public void set_atom_site_label_atom_id(List<String> _atom_site_label_atom_id) {
		this._atom_site_label_atom_id = _atom_site_label_atom_id;
	}
	public List<String> get_atom_site_label_comp_id() {
		return _atom_site_label_comp_id;
	}
	public void set_atom_site_label_comp_id(List<String> _atom_site_label_comp_id) {
		this._atom_site_label_comp_id = _atom_site_label_comp_id;
	}
	public List<String> get_atom_site_label_entity_id() {
		return _atom_site_label_entity_id;
	}
	public void set_atom_site_label_entity_id(List<String> _atom_site_label_entity_id) {
		this._atom_site_label_entity_id = _atom_site_label_entity_id;
	}
	public List<Integer> get_atom_site_label_entity_poly_seq_num() {
		return _atom_site_label_entity_poly_seq_num;
	}
	public void set_atom_site_label_entity_poly_seq_num(List<Integer> _atom_site_label_entity_poly_seq_num) {
		this._atom_site_label_entity_poly_seq_num = _atom_site_label_entity_poly_seq_num;
	}
	
	
	private List<String> _atom_site_symbol = new ArrayList<String>();
	// This data item is an author defined alternative to the value of
	// _atom_site_label_asym_id_ This item holds the PDB chain
	// identifier_
	private List<String> _atom_site_asym_id = new ArrayList<String>();
	// This data item is an author defined alternative to the value of
	// _atom_site_label_atom_id_ This item holds the PDB atom name_
//	private List<String> _atom_site_auth_atom_id = new ArrayList<String>();
	// This data item is an author defined alternative to the value of
	// _atom_site_label_comp_id_ This item holds the PDB 3-letter-code
	// residue names
//	private List<String> _atom_site_auth_comp_id = new ArrayList<String>();
	// This data item is an author defined alternative to the value of
	// _atom_site_label_seq_id_ This item holds the PDB residue number_
	private List<Integer> _atom_site_auth_seq_id = new ArrayList<Integer>();
	private List<Integer> _atom_site_label_entity_poly_seq_num= new ArrayList<Integer>();
	// This data item corresponds to the PDB insertion code_
	private List<String> _atom_site_pdbx_PDB_ins_code = new ArrayList<String>();
	// This data item identifies the model number in an ensemble of
	// coordinate data_
//	private List<Integer> _atom_site_pdbx_PDB_model_num = new ArrayList<Integer>();
	// This data item is a place holder for the tags used by the PDB to
	// identify coordinate records (e_g_ ATOM or HETATM)_
	private List<String> _atom_site_group_PDB = new ArrayList<String>();
	// This item is a uniquely identifies for each alternative site for
	// this atom position_
	private List<String> _atom_site_label_alt_id= new ArrayList<String>();
	// This data item is reference to item _struct_asym_id defined in
	private List<String> _atom_site_label_asym_id= new ArrayList<String>();
	// This data item is a reference to item _chem_comp_atom_atom_id
	// defined in category CHEM_COMP_ATOM which is stored in the
	// Chemical Component Dictionary_ This atom identifier uniquely
	// identifies each atom within each chemical component_
	private List<String> _atom_site_label_atom_id= new ArrayList<String>();
	// This data item is a reference to item _chem_comp_id defined in
	// category CHEM_COMP_ This item is the primary identifier for
	// chemical components which may either be mononers in a polymeric
	// entity or complete non-polymer entities_
	private List<String> _atom_site_label_comp_id= new ArrayList<String>();
	// This data item is a reference to _entity_id defined in the ENTITY
	// category_ This item is used to identify chemically distinct
	// portions of the molecular structure (e_g_ polymer chains,
	// ligands, solvent)_
	// This data item is a reference to _entity_poly_seq_num defined in
	// the ENTITY_POLY_SEQ category_ This item is used to maintain the
	// correspondence between the chemical sequence of a polymeric
	// entity and the sequence information in the coordinate list and in
	// may other structural categories_ This identifier has no meaning
	// for non-polymer entities_
	private List<String> _atom_site_label_entity_id= new ArrayList<String>();
	//
	private List<Integer> interGroupBondInds = new ArrayList<Integer>();
	private List<Integer> interGroupBondOrders = new ArrayList<Integer>();
	private List<Integer> secStruct = new ArrayList<Integer>();

	private List<Integer> resOrder = new ArrayList<Integer>();
	private List<List<Integer>> groupList = new ArrayList<List<Integer>>();
	public Map<Integer, PDBGroup> getGroupMap() {
		return groupMap;
	}
	public void setGroupMap(Map<Integer, PDBGroup> groupMap) {
		this.groupMap = groupMap;
	}
	public List<List<Integer>> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<List<Integer>> groupList) {
		this.groupList = groupList;
	}

	
	public List<Integer> getResOrder() {
		return resOrder;
	}
	public void setResOrder(List<Integer> resOrder) {
		this.resOrder = resOrder;
	}


	public List<Integer> getSecStruct() {
		return secStruct;
	}
	public void setSecStruct(List<Integer> secStruct) {
		this.secStruct = secStruct;
	}


	public List<Integer> getInterGroupBondInds() {
		return interGroupBondInds;
	}
	public void setInterGroupBondInds(List<Integer> interGroupBondInds) {
		this.interGroupBondInds = interGroupBondInds;
	}


	public List<Integer> getInterGroupBondOrders() {
		return interGroupBondOrders;
	}
	public void setInterGroupBondOrders(List<Integer> interGroupBondOrders) {
		this.interGroupBondOrders = interGroupBondOrders;
	}
	protected Map<Integer, PDBGroup> groupMap = new HashMap<Integer, PDBGroup>();
}
