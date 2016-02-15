package org.codec.arraycompressors;

import java.util.ArrayList;

/**
 * Class to encode an integer array with deltas
 * @author abradley
 *
 */
public class FindDeltas implements IntArrayCompressor {


	public ArrayList<Integer> compressIntArray(ArrayList<Integer> inArray) {
		// 
    	ArrayList<Integer> outArray =  new ArrayList<Integer>();
		int old_int = 0;
	    for (int i = 0; i < inArray.size(); i++) {
	    	// Get the value out here
			int num_int = inArray.get(i);
			if (i==0){
				old_int = num_int;
				outArray.add(num_int);
			}
			else{
				int this_int = num_int - old_int;
				outArray.add((int) this_int);
				old_int = num_int;
			}
	    }
		return outArray;
	}


}
