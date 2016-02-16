package org.codec.dataholders;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.biojava.nbio.structure.Structure;

/**
 * An interface required for coresingle structure types
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
