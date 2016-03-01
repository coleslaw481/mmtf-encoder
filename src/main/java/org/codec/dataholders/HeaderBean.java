package org.codec.dataholders;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codec.dataholders.BioAssemblyInfoNew;

/**
 * Class to store header information in a bean that can be converted to a messagepack
 * @author abradley
 *
 */
public class HeaderBean {
	
	// The number of chains
	private int numChains;
	// The number of atoms
	private int numAtoms;
	// The number of bonds
	private int numBonds;
	// The PDBCode
	private String idCode;
	// The title of the structure
	private String title;
	// PDB HEADER INFORMATION
	private String description;
	private String classification;	
	private Date depDate;
	private Date modDate;
	private float resolution;
	private float rFree;
	private String doi;
	private String authors;
	private List<String> sequence;
	// Add this to store the model information
	private int[] chainsPerModel;
	private int[] asymChainsPerModel;
	// List to store the chainids
	private byte[] chainList;
	// List to store the chainids
	private byte[] asymChainList;
	// List to store the number of groups per chain
	private int[] groupsPerChain;
	private int[] asymGroupsPerChain;
	// LOTS OF OTHER STUFF HERE -> TBD
	private String spaceGroup;
	private List<Float> unitCell = new ArrayList<Float>(); 
	private Map<Integer, BioAssemblyInfoNew> bioAssembly = new HashMap<Integer, BioAssemblyInfoNew>(); 
	// Getters and setters
	
	public int getNumChains() {
		return numChains;
	}
	public void setNumChains(int numChains) {
		this.numChains = numChains;
	}
	public int getNumAtoms() {
		return numAtoms;
	}
	public void setNumAtoms(int numAtoms) {
		this.numAtoms = numAtoms;
	}
	public String getPdbCode() {
		return idCode;
	}

	public void setPdbCode(String pdbCode) {
		this.idCode = pdbCode;
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
	public int[] getChainsPerModel() {
		return chainsPerModel;
	}
	public void setChainsPerModel(int[] chainsPerModel) {
		this.chainsPerModel = chainsPerModel;
	}
	public int[] getGroupsPerChain() {
		return groupsPerChain;
	}
	public void setGroupsPerChain(int[] groupsPerChain) {
		this.groupsPerChain = groupsPerChain;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public Date getDepDate() {
		return depDate;
	}
	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public float getResolution() {
		return resolution;
	}
	public void setResolution(float resolution) {
		this.resolution = resolution;
	}
	public float getrFree() {
		return rFree;
	}
	public void setrFree(float rFree) {
		this.rFree = rFree;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public Map<Integer, BioAssemblyInfoNew> getBioAssembly() {
		return bioAssembly;
	}
	public void setBioAssembly(Map<Integer, BioAssemblyInfoNew> bioAssembly) {
		this.bioAssembly = bioAssembly;
	}
	public byte[] getChainList() {
		return chainList;
	}
	public void setChainList(byte[] chainList) {
		this.chainList = chainList;
	}
	public byte[] getAsymChainList() {
		return asymChainList;
	}
	public void setAsymChainList(byte[] asymChainList) {
		this.asymChainList = asymChainList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getSequence() {
		return sequence;
	}
	public void setSequence(List<String> sequence) {
		this.sequence = sequence;
	}
	public int getNumBonds() {
		return numBonds;
	}
	public void setNumBonds(int numBonds) {
		this.numBonds = numBonds;
	}
	public int[] getAsymChainsPerModel() {
		return asymChainsPerModel;
	}
	public void setAsymChainsPerModel(int[] asymChainsPerModel) {
		this.asymChainsPerModel = asymChainsPerModel;
	}
	public int[] getAsymGroupsPerChain() {
		return asymGroupsPerChain;
	}
	public void setAsymGroupsPerChain(int[] asymGroupsPerChain) {
		this.asymGroupsPerChain = asymGroupsPerChain;
	}	
}
