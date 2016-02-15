package org.codec.dataholders;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResidueGraphDataStructBean implements BioBean {
	// A bean to add a hashmap of the data structure - e.g. that stores RES name 
	// So we need a hashmap and a list
	public Set<String> getResToInfo() {
		return resToInfo;
	}
	public void setResToInfo(Set<String> resToInfo) {
		this.resToInfo = resToInfo;
	}
	public List<Integer> getResOrder() {
		return resOrder;
	}
	public void setResOrder(List<Integer> resOrder) {
		this.resOrder = resOrder;
	}
	// Second the list of integers - indicating to the values of this residue
	private List<Integer> resOrder = new ArrayList<Integer>();
	//  Hashmap - corresponds the unqiue info to the corresponding information
	private Set<String> resToInfo = new LinkedHashSet<String>();
	public List<Integer> get_atom_site_Cartn_xInt() {
		return _atom_site_Cartn_xInt;
	}
	public void set_atom_site_Cartn_xInt(List<Integer> _atom_site_Cartn_xInt) {
		this._atom_site_Cartn_xInt = _atom_site_Cartn_xInt;
	}
	public List<Integer> get_atom_site_Cartn_yInt() {
		return _atom_site_Cartn_yInt;
	}
	public void set_atom_site_Cartn_yInt(List<Integer> _atom_site_Cartn_yInt) {
		this._atom_site_Cartn_yInt = _atom_site_Cartn_yInt;
	}
	public List<Integer> get_atom_site_Cartn_zInt() {
		return _atom_site_Cartn_zInt;
	}
	public void set_atom_site_Cartn_zInt(List<Integer> _atom_site_Cartn_zInt) {
		this._atom_site_Cartn_zInt = _atom_site_Cartn_zInt;
	}
	public List<Integer> get_atom_site_B_iso_or_equivInt() {
		return _atom_site_B_iso_or_equivInt;
	}
	public void set_atom_site_B_iso_or_equivInt(List<Integer> _atom_site_B_iso_or_equivInt) {
		this._atom_site_B_iso_or_equivInt = _atom_site_B_iso_or_equivInt;
	}
	public List<Integer> get_atom_site_occupancyInt() {
		return _atom_site_occupancyInt;
	}
	public void set_atom_site_occupancyInt(List<Integer> _atom_site_occupancyInt) {
		this._atom_site_occupancyInt = _atom_site_occupancyInt;
	}
	// A unique identifier for each atom position_
	public List<Integer> get_atom_site_id() {
		return _atom_site_id;
	}
	public void set_atom_site_id(List<Integer> _atom_site_id) {
		this._atom_site_id = _atom_site_id;
	}
	public List<Integer> get_atom_site_auth_seq_id() {
		return _atom_site_auth_seq_id;
	}
	public void set_atom_site_auth_seq_id(List<Integer> _atom_site_auth_seq_id) {
		this._atom_site_auth_seq_id = _atom_site_auth_seq_id;
	}
	public List<Integer> get_atom_site_label_entity_poly_seq_num() {
		return _atom_site_label_entity_poly_seq_num;
	}
	public void set_atom_site_label_entity_poly_seq_num(List<Integer> _atom_site_label_entity_poly_seq_num) {
		this._atom_site_label_entity_poly_seq_num = _atom_site_label_entity_poly_seq_num;
	}
	public List<String> get_atom_site_label_alt_id() {
		return _atom_site_label_alt_id;
	}
	public void set_atom_site_label_alt_id(List<String> _atom_site_label_alt_id) {
		this._atom_site_label_alt_id = _atom_site_label_alt_id;
	}
	public List<String> get_atom_site_label_entity_id() {
		return _atom_site_label_entity_id;
	}
	public void set_atom_site_label_entity_id(List<String> _atom_site_label_entity_id) {
		this._atom_site_label_entity_id = _atom_site_label_entity_id;
	}
	public List<String> get_atom_site_pdbx_PDB_ins_code() {
		return _atom_site_pdbx_PDB_ins_code;
	}
	public void set_atom_site_pdbx_PDB_ins_code(List<String> _atom_site_pdbx_PDB_ins_code) {
		this._atom_site_pdbx_PDB_ins_code = _atom_site_pdbx_PDB_ins_code;
	}
	public List<Integer> get_atom_site_pdbx_PDB_model_num() {
		return _atom_site_pdbx_PDB_model_num;
	}
	public void set_atom_site_pdbx_PDB_model_num(List<Integer> _atom_site_pdbx_PDB_model_num) {
		this._atom_site_pdbx_PDB_model_num = _atom_site_pdbx_PDB_model_num;
	}
	private List<String> _atom_site_label_alt_id= new ArrayList<String>();
	private List<String> _atom_site_label_entity_id= new ArrayList<String>();
	private List<String> _atom_site_pdbx_PDB_ins_code = new ArrayList<String>();
	private List<Integer> _atom_site_pdbx_PDB_model_num = new ArrayList<Integer>();
	private List<Integer> _atom_site_auth_seq_id = new ArrayList<Integer>();
	private List<Integer> _atom_site_label_entity_poly_seq_num= new ArrayList<Integer>();
	private List<Integer> _atom_site_id =  new ArrayList<Integer>();
	// Atom symbol
	// All this information is stored as Integer arrays
	private List<Integer> _atom_site_Cartn_xInt = new ArrayList<Integer>();
	private List<Integer> _atom_site_Cartn_yInt  = new ArrayList<Integer>();
	private List<Integer>  _atom_site_Cartn_zInt  = new ArrayList<Integer>();
	// Isotropic atomic displacement parameter
	private List<Integer>  _atom_site_B_iso_or_equivInt  = new ArrayList<Integer>();
	// The fraction of the atom present at this atom position_
	private List<Integer>  _atom_site_occupancyInt  = new ArrayList<Integer>();
}
