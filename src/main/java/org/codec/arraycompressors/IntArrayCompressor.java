package org.codec.arraycompressors;

import java.util.ArrayList;


public interface IntArrayCompressor {
	// WE DO WANT SPECIFICALLY AN ARRAYLIST HERE
	public ArrayList<Integer> compressIntArray(ArrayList<Integer> inArray);
}
