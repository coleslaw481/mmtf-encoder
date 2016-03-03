package org.codec.dataholders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rcsb.mmtf.dataholders.BioAssemblyInfoNew;
import org.rcsb.mmtf.dataholders.PDBGroup;

/**
 * A bean to store C-Alpha / DNA backbone and ligand information - in a format that can 
 * then be efficiently sent as messagepack
 * @author abradley
 *
 */
public class CalphaDistBean {
	
	// The version of the format
	private String mmtfVersion = "0.1";
	// The producer
	private String mmtfProducer;
	// The number of bonds
	private int numBonds;
	// The PDBCode
	private String pdbId;
	// The title of the structure
	private String title;
	// String for the space group
	private String spaceGroup;
	// The unit cell information
	private List<Float> unitCell = new ArrayList<Float>(); 
	// A map of Bioassembly -> new class so serializable
	private Map<Integer, BioAssemblyInfoNew> bioAssembly = new HashMap<Integer, BioAssemblyInfoNew>(); 
	// The list of sequence information
	private  Map<Integer, PDBGroup> groupMap = new HashMap<Integer, PDBGroup>();
	// Delta and run length
	private byte[] groupNumList;
	private byte[] groupTypeList;
	private byte[] secStructList;
	// For the big arrays split into two -> one of 32 bit ints, one of 16
	private byte[] xCoordBig;
	private byte[] yCoordBig;
	private byte[] zCoordBig;
	// Now for the small ints -> 16 bit
	private byte[] xCoordSmall;
	private byte[] yCoordSmall;
	private byte[] zCoordSmall;
	// Add this header info
	// Total data for memory allocation
	private int numAtoms;
	// Add this to store the model information
	private int[] chainsPerModel;
	// List to store the chainids
	private byte[] chainList;
	// List to store the number of groups per chain
	private int[] groupsPerChain;
	// Store the one letter amino acid sequence of the protein
	private char[] oneLetterAminSeq;
	
	public String getPdbId() {
		return pdbId;
	}
	public void setPdbId(String pdbId) {
		this.pdbId = pdbId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSpaceGroup() {
		return spaceGroup;
	}
	public void setSpaceGroup(String spaceGroup) {
		this.spaceGroup = spaceGroup;
	}
	public List<Float> getUnitCell() {
		return unitCell;
	}
	public void setUnitCell(List<Float> unitCell) {
		this.unitCell = unitCell;
	}
	public Map<Integer, BioAssemblyInfoNew> getBioAssembly() {
		return bioAssembly;
	}
	public void setBioAssembly(Map<Integer, BioAssemblyInfoNew> bioAssembly) {
		this.bioAssembly = bioAssembly;
	}
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
	public byte[] getGroupNumList() {
		return groupNumList;
	}
	public void setGroupNumList(byte[] _atom_site_auth_seq_id) {
		this.groupNumList = _atom_site_auth_seq_id;
	}
	public byte[] getGroupTypeList() {
		return groupTypeList;
	}
	public void setGroupTypeList(byte[] resOrder) {
		this.groupTypeList = resOrder;
	}
	public byte[] getSecStructList() {
		return secStructList;
	}
	public void setSecStructList(byte[] secStruct) {
		this.secStructList = secStruct;
	}
	public byte[] getxCoordBig() {
		return xCoordBig;
	}
	public void setxCoordBig(byte[] cartn_x_big) {
		this.xCoordBig = cartn_x_big;
	}
	public byte[] getyCoordBig() {
		return yCoordBig;
	}
	public void setyCoordBig(byte[] cartn_y_big) {
		this.yCoordBig = cartn_y_big;
	}
	public byte[] getzCoordBig() {
		return zCoordBig;
	}
	public void setzCoordBig(byte[] cartn_z_big) {
		this.zCoordBig = cartn_z_big;
	}
	public byte[] getxCoordSmall() {
		return xCoordSmall;
	}
	public void setxCoordSmall(byte[] cartn_x_small) {
		this.xCoordSmall = cartn_x_small;
	}
	public byte[] getyCoordSmall() {
		return yCoordSmall;
	}
	public void setyCoordSmall(byte[] cartn_y_small) {
		this.yCoordSmall = cartn_y_small;
	}
	public byte[] getzCoordSmall() {
		return zCoordSmall;
	}
	public void setzCoordSmall(byte[] cartn_z_small) {
		this.zCoordSmall = cartn_z_small;
	}
	public String getMmtfVersion() {
		return mmtfVersion;
	}
	public void setMmtfVersion(String mmtfVersion) {
		this.mmtfVersion = mmtfVersion;
	}
	public String getMmtfProducer() {
		return mmtfProducer;
	}
	public void setMmtfProducer(String mmtfProducer) {
		this.mmtfProducer = mmtfProducer;
	}
	public int getNumBonds() {
		return numBonds;
	}
	public void setNumBonds(int numBonds) {
		this.numBonds = numBonds;
	}
	public char[] getOneLetterAminSeq() {
		return oneLetterAminSeq;
	}
	public void setOneLetterAminSeq(char[] oneLetterAminSeq) {
		this.oneLetterAminSeq = oneLetterAminSeq;
	}

}
