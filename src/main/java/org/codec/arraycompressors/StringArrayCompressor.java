package org.codec.arraycompressors;

import java.util.ArrayList;

public interface StringArrayCompressor {
	/**
	 * Generic function to compress a string array
	 * @param inArray
	 * @return
	 */
	public ArrayList<String> compressStringArray(ArrayList<String> inArray);
}
