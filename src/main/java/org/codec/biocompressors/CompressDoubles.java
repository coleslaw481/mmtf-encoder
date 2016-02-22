package org.codec.biocompressors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.codec.dataholders.BioDataStruct;
import org.codec.dataholders.CoreSingleStructure;
import org.codec.dataholders.NoFloatDataStruct;

/**
 * Class to compress a structure by turning doubles to ints
 * @author abradley
 *
 */
public class CompressDoubles implements BioCompressor {

	@SuppressWarnings("static-access")
	public CoreSingleStructure compresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException {
		//  Take in the appropriate arrays 
		BeanUtils bu = new BeanUtils();
		BioDataStruct bioDataS = (BioDataStruct) coress;
		NoFloatDataStruct noFloatDataS = new NoFloatDataStruct();
		bu.copyProperties(noFloatDataS, bioDataS);
		// Get all the arrays we want to compress
		// Set the coordinates
		noFloatDataS.set_atom_site_Cartn_xInt(getIntArrayFromDouble(bioDataS.get_atom_site_Cartn_x(),1000.0));
		noFloatDataS.set_atom_site_Cartn_yInt(getIntArrayFromDouble(bioDataS.get_atom_site_Cartn_y(),1000.0));
		noFloatDataS.set_atom_site_Cartn_zInt(getIntArrayFromDouble(bioDataS.get_atom_site_Cartn_z(),1000.0));
		// Now set the temperature factors and occupancy
		noFloatDataS.set_atom_site_B_iso_or_equivInt(getIntArrayFromFloat(bioDataS.get_atom_site_B_iso_or_equiv(),(float) 100.0));
		noFloatDataS.set_atom_site_occupancyInt(getIntArrayFromFloat(bioDataS.get_atom_site_occupancy(),(float) 100.0));
		// Now assign these to the new dataStructure
		return noFloatDataS;
	}
	
	/**
	 * Function to return an int array from a float array
	 * @param inArray
	 * @param multiplier - the multiplication factor for conversion
	 * @return
	 */
	public List<Integer> getIntArrayFromFloat(List<Float> inArray, float multiplier) {
		// Initialise the out array
		List<Integer> outArray = new ArrayList<Integer>(inArray.size());
		for(Float oldDouble: inArray){
			Integer newInt = (int) Math.round(oldDouble * multiplier);
			outArray.add(newInt);
		}
		return outArray;
		
	}

	/**
	 * Function to return an int array from a double array
	 * @param inArray
	 * @param multiplier
	 * @return
	 */
	public List<Integer> getIntArrayFromDouble(List<Double> inArray, Double multiplier){
		// Initialise the out array
		List<Integer> outArray = new ArrayList<Integer>(inArray.size());
		for(Double oldDouble: inArray){
			Integer newInt = (int) Math.round(oldDouble * multiplier);
			outArray.add(newInt);
		}
		return outArray;
		
	}

}
