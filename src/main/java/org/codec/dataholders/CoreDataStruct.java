package org.codec.dataholders;

public class CoreDataStruct {
	// The core data in all modules
	protected String pdbCode = null;	
	public String getPdbCode() {
		return pdbCode;
	}
	public void setPdbCode(String pdbCode) {
		this.pdbCode = pdbCode;
	}
	
	private int numModels = 0;
	public int getNumModels() {
		return numModels;
	}
	public void setNumModels(int numModels) {
		this.numModels = numModels;
	}

}
