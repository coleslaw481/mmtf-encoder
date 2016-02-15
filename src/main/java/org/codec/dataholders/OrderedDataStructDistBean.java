package org.codec.dataholders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codec.dataholders.PDBGroup;

public class OrderedDataStructDistBean {

	public byte[] getCartn_x_big() {
		return cartn_x_big;
	}
	public void setCartn_x_big(byte[] cartn_x_big) {
		this.cartn_x_big = cartn_x_big;
	}
	public byte[] getCartn_y_big() {
		return cartn_y_big;
	}
	public void setCartn_y_big(byte[] cartn_y_big) {
		this.cartn_y_big = cartn_y_big;
	}
	public byte[] getCartn_z_big() {
		return cartn_z_big;
	}
	public void setCartn_z_big(byte[] cartn_z_big) {
		this.cartn_z_big = cartn_z_big;
	}
	public byte[] getCartn_x_small() {
		return cartn_x_small;
	}
	public void setCartn_x_small(byte[] cartn_x_small) {
		this.cartn_x_small = cartn_x_small;
	}
	public byte[] getCartn_y_small() {
		return cartn_y_small;
	}
	public void setCartn_y_small(byte[] cartn_y_small) {
		this.cartn_y_small = cartn_y_small;
	}
	public byte[] getCartn_z_small() {
		return cartn_z_small;
	}
	public void setCartn_z_small(byte[] cartn_z_small) {
		this.cartn_z_small = cartn_z_small;
	}
	public byte[] getB_factor_big() {
		return b_factor_big;
	}
	public void setB_factor_big(byte[] b_factor_big) {
		this.b_factor_big = b_factor_big;
	}
	public byte[] getB_factor_small() {
		return b_factor_small;
	}
	public void setB_factor_small(byte[] b_factor_small) {
		this.b_factor_small = b_factor_small;
	}
	public byte[] getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(byte[] occupancy) {
		this.occupancy = occupancy;
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


//	public HashMap<Integer, ArrayList<String>> getResToInfo() {
//		return resToInfo;
//	}
//	public void setResToInfo(HashMap<Integer, ArrayList<String>> resToInfo) {
//		this.resToInfo = resToInfo;
//	}
	public Map<Integer, PDBGroup> getGroupMap() {
		return groupMap;
	}
	public void setGroupMap(Map<Integer, PDBGroup> groupMap) {
		this.groupMap = groupMap;
	}
	public byte[] getSecStruct() {
		return secStruct;
	}
	public void setSecStruct(byte[] secStruct) {
		this.secStruct = secStruct;
	}

	public byte[] getResOrder() {
		return resOrder;
	}
	public void setResOrder(byte[] resOrder) {
		this.resOrder = resOrder;
	}

	public byte[] get_atom_site_id() {
		return _atom_site_id;
	}
	public void set_atom_site_id(byte[] _atom_site_id) {
		this._atom_site_id = _atom_site_id;
	}

	// For the big ints -> format BIG INT -> NUMBER OF SMALL INTS
	private byte[] cartn_x_big = new byte[2048];
	private byte[] cartn_y_big = new byte[2048];
	private byte[] cartn_z_big = new byte[2048];
	// Now for the small ints
	private byte[] cartn_x_small = new byte[2048];
	private byte[] cartn_y_small = new byte[2048];
	private byte[] cartn_z_small = new byte[2048];
	// Isotropic atomic displacement parameter
	private byte[] b_factor_big = new byte[2048];
	private byte[] b_factor_small = new byte[2048];
	// Byte array for the secondary structure
	private byte[] secStruct = new byte[2048];
	
	// These are all small any way
	private byte[] occupancy = new byte[2048];
	// Second the list of integers - indicating to the values of this residue
//	private List<Integer> resOrder = new ArrayList<Integer>();
	private byte[] resOrder = new byte[2048];
	/// THUS STYFF JUST COPIES ACROSS
	//  Set - corresponds the unqiue info to the corresponding information
//	private HashMap<Integer, ArrayList<String>> resToInfo = new HashMap<Integer, ArrayList<String>>();
	// Now this information is small

	public byte[] get_atom_site_auth_seq_id() {
		return _atom_site_auth_seq_id;
	}
	public void set_atom_site_auth_seq_id(byte[] _atom_site_auth_seq_id) {
		this._atom_site_auth_seq_id = _atom_site_auth_seq_id;
	}
	public byte[] get_atom_site_label_entity_poly_seq_num() {
		return _atom_site_label_entity_poly_seq_num;
	}
	public void set_atom_site_label_entity_poly_seq_num(byte[] _atom_site_label_entity_poly_seq_num) {
		this._atom_site_label_entity_poly_seq_num = _atom_site_label_entity_poly_seq_num;
	}
	public List<String> get_atom_site_label_alt_id() {
		return _atom_site_label_alt_id;
	}
	public void set_atom_site_label_alt_id(List<String> _atom_site_label_alt_id) {
		this._atom_site_label_alt_id = _atom_site_label_alt_id;
	}

	private List<String> _atom_site_label_alt_id= new ArrayList<String>(); //ARRAY OF CHARS
	private byte[]  _atom_site_auth_seq_id = new byte[2048]; //Arr of ints
	private byte[] _atom_site_label_entity_poly_seq_num = new byte[2048];//Arr of ints
	private byte[] _atom_site_id =  new byte[2048]; //Arr of ints
	private  Map<Integer, PDBGroup> groupMap = new HashMap<Integer, PDBGroup>();
	private List<String> _atom_site_pdbx_PDB_ins_code = new ArrayList<String>();
	private List<Integer> _atom_site_pdbx_PDB_model_num =new ArrayList<Integer>();
//	// Atom symbol
//	// All this information is stored as Integer arrays
//	private List<Integer> _atom_site_Cartn_xInt = new ArrayList<Integer>();
//	private List<Integer> _atom_site_Cartn_yInt  = new ArrayList<Integer>();
//	private List<Integer>  _atom_site_Cartn_zInt  = new ArrayList<Integer>();
//	// Isotropic atomic displacement parameter
//	private List<Integer>  _atom_site_B_iso_or_equivInt  = new ArrayList<Integer>();
//	// The fraction of the atom present at this atom position_
//	private List<Integer>  _atom_site_occupancyInt  = new ArrayList<Integer>();
}
