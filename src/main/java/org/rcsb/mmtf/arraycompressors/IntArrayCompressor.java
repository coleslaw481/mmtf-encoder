package org.rcsb.mmtf.arraycompressors;

import java.util.ArrayList;


public interface IntArrayCompressor {
	/**
	 * Generic function to compress an integer array
	 * @param inArray
	 * @return
	 */
	public ArrayList<Integer> compressIntArray(ArrayList<Integer> inArray);
}
