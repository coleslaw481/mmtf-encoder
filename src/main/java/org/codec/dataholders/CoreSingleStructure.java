package org.codec.dataholders;

import java.lang.reflect.InvocationTargetException;

/**
 * An interface required for core single structure types
 * @author abradley
 *
 */
public interface CoreSingleStructure {
	// Interface to deal with core structural data
	// Return the data as a HashMap
	BioBean findDataAsBean() throws IllegalAccessException, InvocationTargetException;

	String findStructureCode();

	int findNumAtoms();
}
