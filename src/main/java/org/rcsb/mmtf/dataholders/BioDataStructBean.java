package org.rcsb.mmtf.dataholders;

import java.util.ArrayList;
import java.util.List;

/**
 * A bean to store the information about the protein structure
 * @author abradley
 *
 */
public class BioDataStructBean extends NoCoordDataStruct implements BioBean {

	// Coordinate infroamtion
	protected List<Double> _atom_site_Cartn_x = new ArrayList<Double>();
	protected List<Double> _atom_site_Cartn_y = new ArrayList<Double>();
	protected List<Double> _atom_site_Cartn_z = new ArrayList<Double>();
	// Isotropic atomic displacement parameter
	protected List<Float> _atom_site_B_iso_or_equiv= new ArrayList<Float>();
	// The fraction of the atom present at this atom position_
	protected List<Float> _atom_site_occupancy= new ArrayList<Float>();
	// An array to store the secondary structure data
	private List<Integer> secStruct = new ArrayList<Integer>();
	// An array to store the sequence of residues
	private List<Integer> resOrder = new ArrayList<Integer>();
	// Arrays to store the indices and bond orders of inter residue bonds
	private List<Integer> interGroupBondInds = new ArrayList<Integer>();
	private List<Integer> interGroupBondOrders = new ArrayList<Integer>();
	
	
	public List<Integer> get_atom_site_id() {
		return _atom_site_id;
	}
	public void set_atom_site_id(List<Integer> _atom_site_id) {
		this._atom_site_id = _atom_site_id;
	}
	private List<Integer> _atom_site_id =  new ArrayList<Integer>();
	public List<Double> get_atom_site_Cartn_x() {
		return _atom_site_Cartn_x;
	}
	public void set_atom_site_Cartn_x(ArrayList<Double> _atom_site_Cartn_x) {
		this._atom_site_Cartn_x = _atom_site_Cartn_x;
	}
	public List<Double> get_atom_site_Cartn_y() {
		return _atom_site_Cartn_y;
	}
	public void set_atom_site_Cartn_y(ArrayList<Double> _atom_site_Cartn_y) {
		this._atom_site_Cartn_y = _atom_site_Cartn_y;
	}
	public List<Double> get_atom_site_Cartn_z() {
		return _atom_site_Cartn_z;
	}
	public void set_atom_site_Cartn_z(ArrayList<Double> _atom_site_Cartn_z) {
		this._atom_site_Cartn_z = _atom_site_Cartn_z;
	}
	public List<Float> get_atom_site_B_iso_or_equiv() {
		return _atom_site_B_iso_or_equiv;
	}
	public void set_atom_site_B_iso_or_equiv(ArrayList<Float> _atom_site_B_iso_or_equiv) {
		this._atom_site_B_iso_or_equiv = _atom_site_B_iso_or_equiv;
	}
	public List<Float> get_atom_site_occupancy() {
		return _atom_site_occupancy;
	}
	public void set_atom_site_occupancy(ArrayList<Float> _atom_site_occupancy) {
		this._atom_site_occupancy = _atom_site_occupancy;
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
	public List<Integer> getInterGroupBondOrders() {
		return interGroupBondOrders;
	}
	public void setInterGroupBondOrders(List<Integer> interGroupBondOrders) {
		this.interGroupBondOrders = interGroupBondOrders;
	}
	public List<Integer> getInterGroupBondInds() {
		return interGroupBondInds;
	}
	public void setInterGroupBondInds(List<Integer> interGroupBondInds) {
		this.interGroupBondInds = interGroupBondInds;
	}
}
