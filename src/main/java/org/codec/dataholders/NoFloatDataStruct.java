package org.codec.dataholders;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;


/**
 * Class to store biological data once the floats have been converted to ints
 * @author abradley
 *
 */
public class NoFloatDataStruct extends NoFloatDataStructBean implements CoreSingleStructure {

	@SuppressWarnings("static-access")
	public BioBean findDataAsBean() throws IllegalAccessException, InvocationTargetException {
		// Cast this to the pure data
		NoFloatDataStructBean newData = new NoFloatDataStructBean();
		BeanUtils bu = new BeanUtils();
		bu.copyProperties(newData, this);
		return newData;
	}

	@Override
	public String findStructureCode() {
		return this.findStructureCode();
	}

	@Override
	public int findNumAtoms() {
		return this.findNumAtoms();
	}


}
