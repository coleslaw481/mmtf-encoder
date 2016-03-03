package org.codec.dataholders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rcsb.mmtf.dataholders.PDBGroup;

/**
 * A bean to store C-Alpha / DNA backbone and ligand information
 * @author abradley
 *
 */
public class CalphaBean {
	// Two integers to store the number of bonds and numeb
	private int numBonds = 0;
	private int numAtoms = 0;
	// Convert this information
	private  Map<Integer, PDBGroup> groupMap = new HashMap<Integer, PDBGroup>();
	// Delta and run length
	private List<Integer>  _atom_site_auth_seq_id = new ArrayList<Integer>();
	// Delta and run length encoded
	private List<Integer>  _atom_site_label_entity_poly_seq_num = new ArrayList<Integer>();
	private List<Integer>  resOrder = new ArrayList<Integer>();
	// The list of c-alpha coords
	private List<Integer> cartn_x = new ArrayList<Integer>();
	private List<Integer> cartn_y = new ArrayList<Integer>();
	private List<Integer> cartn_z = new ArrayList<Integer>();
	//secondary structure (on a per reisude basis
	private List<Integer> secStruct = new ArrayList<Integer>();
	// A list of integers indicating the number of groups in a chain
	private int[] groupsPerChain;	
	// Now the getters and setters
	public List<Integer> getCartn_x() {
		return cartn_x;
	}
	public void setCartn_x(List<Integer> cartn_x) {
		this.cartn_x = cartn_x;
	}
	public List<Integer> getCartn_y() {
		return cartn_y;
	}
	public void setCartn_y(List<Integer> cartn_y) {
		this.cartn_y = cartn_y;
	}
	public List<Integer> getCartn_z() {
		return cartn_z;
	}
	public void setCartn_z(List<Integer> cartn_z) {
		this.cartn_z = cartn_z;
	}
	public List<Integer> getSecStruct() {
		return secStruct;
	}
	public void setSecStruct(List<Integer> secStruct) {
		this.secStruct = secStruct;
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
	public List<Integer> getResOrder() {
		return resOrder;
	}
	public void setResOrder(List<Integer> resOrder) {
		this.resOrder = resOrder;
	}
	public Map<Integer, PDBGroup> getGroupMap() {
		return groupMap;
	}
	public void setGroupMap(Map<Integer, PDBGroup> groupMap) {
		this.groupMap = groupMap;
	}
	public int[] getGroupsPerChain() {
		return groupsPerChain;
	}
	public void setGroupsPerChain(int[] groupsPerChain) {
		this.groupsPerChain = groupsPerChain;
	}
	public int getNumAtoms() {
		return numAtoms;
	}
	public void setNumAtoms(int numAtoms) {
		this.numAtoms = numAtoms;
	}
	public int getNumBonds() {
		return numBonds;
	}
	public void setNumBonds(int numBonds) {
		this.numBonds = numBonds;
	}
}
