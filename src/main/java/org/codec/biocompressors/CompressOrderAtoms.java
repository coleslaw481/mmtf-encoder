package org.codec.biocompressors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.codec.dataholders.CoreSingleStructure;
import org.codec.dataholders.NonAtomDataStruct;
import org.codec.dataholders.OrderedDataStruct;

public class CompressOrderAtoms extends AbstractBioCompressor implements BioCompressor {

		public CoreSingleStructure compresStructure(CoreSingleStructure coress) throws IllegalAccessException, InvocationTargetException {
			// The in object
			OrderedDataStruct ordGraphDS = new OrderedDataStruct();
			NonAtomDataStruct noatomDS = (NonAtomDataStruct) coress;
			// Now update the information
			updateInfo(ordGraphDS, coress, noatomDS);
			// Now alter the order of the 
//			// Get the list of infos
//			List<String> infoArray = new ArrayList<String>(ordGraphDS.getResToInfo());
//			// The array(s) to alter
//			int listSize = ordGraphDS.get_atom_site_Cartn_xInt().size();
//			List<Integer> outArrayXInt = fillArrayList(listSize);
//			List<Integer> outArrayYInt = fillArrayList(listSize); 
//			List<Integer> outArrayZInt =  fillArrayList(listSize);
//			List<Integer> outArrayTempF =  fillArrayList(listSize);
//			List<Integer> outArrayOcc =  fillArrayList(listSize);
//
//
//			Integer index = 0;
//			// Now update the order of the atoms so they are as close together as possible
//			for(Integer resNum: ordGraphDS.getResOrder()){
//				// Get the atom map for this residue - simple implementation - move atoms 2 and 3 (0 index) to the end - C and O
//				Integer numAtoms = getNumAtoms(infoArray.get(resNum));
//				Integer newIndex = 0;
//				for(int i=0; i<numAtoms; i++){
//					if(i<2){
//						newIndex=i+index;
//					}
//					else if(i==2){
//						// Second last
//						newIndex=numAtoms+index-2;
//						
//					}
//					else if(i==3){
//						// Last
//						newIndex=numAtoms+index-1;
//					}
//					else if(i>3){
//						// Draw it back two
//						newIndex=i+index-2;
//					}
//					// Update all the values
//					outArrayXInt.set(newIndex, ordGraphDS.get_atom_site_Cartn_xInt().get(index+i));
//					outArrayYInt.set(newIndex, ordGraphDS.get_atom_site_Cartn_yInt().get(index+i));
//					outArrayZInt.set(newIndex, ordGraphDS.get_atom_site_Cartn_zInt().get(index+i));
//					outArrayTempF.set(newIndex, ordGraphDS.get_atom_site_B_iso_or_equivInt().get(index+i));
//					outArrayOcc.set(newIndex, ordGraphDS.get_atom_site_occupancyInt().get(index+i));
//
//				}
//				// Now update the index
//				index+=numAtoms;
//			}
//			
//			
//			// Now set up the coords
//			// Lets test
//			System.out.println("BEFORE");
//			System.out.println(sumDeltas(ordGraphDS.get_atom_site_Cartn_xInt()));
//			System.out.println("AFTER");
//			System.out.println(sumDeltas(outArrayXInt));
//			System.out.println("DIFFERENCE");
//			System.out.println(sumDeltas(ordGraphDS.get_atom_site_Cartn_xInt())-sumDeltas(outArrayXInt));
//			ordGraphDS.set_atom_site_Cartn_xInt(outArrayXInt);
//			
//			
//			
//			ordGraphDS.set_atom_site_Cartn_yInt(outArrayYInt);
//			ordGraphDS.set_atom_site_Cartn_zInt(outArrayZInt);
//			// Now set the temp factor
//			ordGraphDS.set_atom_site_B_iso_or_equivInt(outArrayTempF);
//			ordGraphDS.set_atom_site_occupancyInt(outArrayOcc);
			return ordGraphDS;
		}
		private List<Integer> fillArrayList(int arraySize) {
			// TODO Auto-generated method stub
			ArrayList<Integer> outList = new ArrayList<Integer>(arraySize);
			for(int i=0; i<arraySize; i++){
				outList.add(0);
			}
			return outList;
		}

		private int getNumAtoms(String resInfo){
			int numLines=0;
			for(String item: resInfo.split("\n")){
				if(item.startsWith("ATOM") | item.startsWith("HETATM")){					
				}
				else{
					numLines+=1;
				}
			}
			return numLines;
			
		}
		private Integer sumDeltas(List<Integer> list){
		// Function that getd th 
		int old_int = 0;
		int outSum = 0;
	    for (int i = 0; i < list.size(); i++) {
	    	// Get the value out here
			int num_int = list.get(i);
			if (i==0){
				old_int = num_int;
				outSum+=num_int;
			}
			else{
				int this_int = num_int - old_int;
				outSum +=(int) this_int;
				old_int = num_int;
			}
	    }
		return outSum;
}
}
