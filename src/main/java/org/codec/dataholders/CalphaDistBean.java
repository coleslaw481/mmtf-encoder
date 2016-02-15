package org.codec.dataholders;

import java.util.HashMap;
import java.util.Map;

import org.codec.dataholders.PDBGroup;

public class CalphaDistBean {
	// The list of sequence information
	private  Map<Integer, PDBGroup> groupMap = new HashMap<Integer, PDBGroup>();
	// Delta and run length
	private byte[]  _atom_site_auth_seq_id;
	// Delta and run length encoded
	private byte[] _atom_site_label_entity_poly_seq_num;
	private byte[] resOrder;
	private byte[] secStruct;
	// For the big arrays split into two -> one of 32 bit ints, one of 16
	private byte[] cartn_x_big;
	private byte[] cartn_y_big;
	private byte[] cartn_z_big;
	// Now for the small ints -> 16 bit
	private byte[] cartn_x_small;
	private byte[] cartn_y_small;
	private byte[] cartn_z_small;
	
	
	// Add this header info
	// Total data for memory allocation
	private int numAtoms;
	// Add this to store the model information
	private int[] chainsPerModel;
	// List to store the chainids
	private byte[] chainList;
	// List to store the number of groups per chain
	private int[] groupsPerChain;
	
	
	public int getNumAtoms() {
		return numAtoms;
	}
	public void setNumAtoms(int numAtoms) {
		this.numAtoms = numAtoms;
	}
	public int[] getChainsPerModel() {
		return chainsPerModel;
	}
	public void setChainsPerModel(int[] chainsPerModel) {
		this.chainsPerModel = chainsPerModel;
	}
	public byte[] getChainList() {
		return chainList;
	}
	public void setChainList(byte[] chainList) {
		this.chainList = chainList;
	}
	public int[] getGroupsPerChain() {
		return groupsPerChain;
	}
	public void setGroupsPerChain(int[] groupsPerChain) {
		this.groupsPerChain = groupsPerChain;
	}
	public Map<Integer, PDBGroup> getGroupMap() {
		return groupMap;
	}
	public void setGroupMap(Map<Integer, PDBGroup> groupMap) {
		this.groupMap = groupMap;
	}
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
	public byte[] getResOrder() {
		return resOrder;
	}
	public void setResOrder(byte[] resOrder) {
		this.resOrder = resOrder;
	}
	public byte[] getSecStruct() {
		return secStruct;
	}
	public void setSecStruct(byte[] secStruct) {
		this.secStruct = secStruct;
	}
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

}
