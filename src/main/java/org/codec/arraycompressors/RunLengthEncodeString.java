package org.codec.arraycompressors;

import java.util.ArrayList;

public class RunLengthEncodeString implements StringArrayCompressor{
	public ArrayList<String> compressStringArray(ArrayList<String> inArray) {
		// TODO Auto-generated method stub
    	ArrayList<String> outArray =  new ArrayList<String>();
    	String oldVal = "";
    	int counter = 0;
    	// Loop through the vals
	    for (int i = 0; i < inArray.size(); i++) {
	    	// Get the value out here
			String inString = inArray.get(i);
			if (inString!=oldVal){
				if(oldVal!=""){
					// Add the counter to the array
					outArray.add(Integer.toString(counter));
				}
				// If it's a new number add it to the array
				outArray.add(inString);
				counter=1;
				oldVal=inString;
			}
			else{
				counter+=1;
			}
	    }
		outArray.add(Integer.toString(counter));
		return outArray;
	}

}
