package org.codec.biocompressors;

import java.lang.reflect.InvocationTargetException;

import org.codec.dataholders.CoreSingleStructure;

public interface BioCompressor {
	
	/**
	 * Generic function to modify the data structure of a protein to enhance compression
	 * @param coress
	 * @return The updated data structure
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public CoreSingleStructure compresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException;

}
