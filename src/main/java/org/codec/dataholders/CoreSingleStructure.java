package org.codec.dataholders;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.biojava.nbio.structure.Structure;
public interface CoreSingleStructure {
	// Interface to deal with core structural data
	// Return the data as a HashMap
	BioBean findDataAsBean() throws IllegalAccessException, InvocationTargetException;
	Map<String, Object> findDataAsHashMap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	// Return the data as a BioJava structure object
	Structure findDataAsBioJava();
	// Return the data as a JSON
	// Return the strign
	String findStructureCode();
	// Return the biodatastruct
	BioDataStruct findDataAsBioDataStruct();
	// Return the data as a nonFloatStruct
	NoFloatDataStruct findDataAsNoFloatStruct();
	// Set the code of this data object
	void setStructureCode(String my_code);
	int findNumAtoms();
	void fillDataStruct(String key, Object part);	
}
