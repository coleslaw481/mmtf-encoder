package org.codec.biocompressors;

import java.lang.reflect.InvocationTargetException;

import org.codec.dataholders.CoreSingleStructure;

public interface BioCompressor {
	
	public CoreSingleStructure compresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException;

}
