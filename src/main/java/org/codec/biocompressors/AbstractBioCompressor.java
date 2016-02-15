package org.codec.biocompressors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.commons.beanutils.BeanUtils;
import org.codec.dataholders.CoreSingleStructure;
import org.codec.dataholders.NonAtomDataStruct;
import org.codec.dataholders.OrderedDataStruct;
import org.codec.dataholders.ResidueGraphDataStruct;

public class AbstractBioCompressor {
	public void updateInfo(ResidueGraphDataStruct resGraphDS, CoreSingleStructure coress, NonAtomDataStruct noatomDS) throws IllegalAccessException, InvocationTargetException {
		//  Take in the appropriate arrays 
		BeanUtils bu = new BeanUtils();
		// Copy over shared properties
		bu.copyProperties(resGraphDS, coress);
		//		int oldNum = -1;
		//		String resName = "";
		//		String infoValue = "";
		//		// Now loop over and create the hashmaps
		//		for(int i=0; i<coress.findNumAtoms(); i++){
		//			resName = noatomDS.get_atom_site_label_comp_id().get(i);
		//			int resNum = noatomDS.get_atom_site_auth_seq_id().get(i);
		//			if(resNum!=oldNum){
		//				if(oldNum != -1){
		//					// Update the information
		//					updateHash(resGraphDS, resName, infoValue);
		//				}
		//				// Set it to zero
		//				infoValue="";
		//				// First line is info shared across the residue
		//				infoValue+=allInfo(noatomDS,i);
		//				oldNum=resNum;
		//			}
		//			// Function to get the required information - for each line
		//			infoValue+=packInfo(noatomDS, i);
		//		}
		//		// Now do the last one\
		//		updateHash(resGraphDS, resName, infoValue);
		//		// The out object

	}

	public void updateInfo(OrderedDataStruct resGraphDS, CoreSingleStructure coress, NonAtomDataStruct noatomDS) throws IllegalAccessException, InvocationTargetException {
		//  Take in the appropriate arrays 
		BeanUtils bu = new BeanUtils();
		// Copy over shared properties
		bu.copyProperties(resGraphDS, coress);
		//		int oldNum = -1;
		//		String resName = "";
		//		ArrayList<String> infoValue = new ArrayList<String>();
		//		// Now loop over and create the hashmaps
		//		for(int i=0; i<coress.findNumAtoms(); i++){
		//			resName = noatomDS.get_atom_site_label_comp_id().get(i);
		//			int resNum = noatomDS.get_atom_site_auth_seq_id().get(i);
		//			if(resNum!=oldNum){
		//				if(oldNum >-1){
		//					//
		//					updateHash(resGraphDS, resName, infoValue);
		//				}
		//				infoValue.clear();
		//				// First line is info shared across the residue
		//				infoValue.addAll(allInfo(noatomDS,i));
		//				oldNum=resNum;
		//			}
		//			// Function to get the required information - for each line
		//			infoValue.addAll(packInfo(noatomDS, i));
		//		}
		//		// Now do the last one\
		//		updateHash(resGraphDS, resName, infoValue);
		//		// The out object

	}


	private void updateHash(ResidueGraphDataStruct resGraphDS, String resName, String infoValue) {
		// First check if it exists and add it if not
		resGraphDS.getResToInfo().add(infoValue);
		List<String> nameList = new ArrayList<String>(resGraphDS.getResToInfo());
		resGraphDS.getResOrder().add(nameList.indexOf(infoValue));
	}

	private void updateHash(OrderedDataStruct resGraphDS, String resName, ArrayList<String> infoValue) {
		// First check if it exists and add it if not
		Integer thisKey = -1;
		ArrayList<String> newArr = new ArrayList<String>();

		for(Entry<Integer, ArrayList<String>> thisEntry: resGraphDS.getResToInfo().entrySet()){
			// 

			if(areSame(thisEntry.getValue(), infoValue) == true){
				thisKey=thisEntry.getKey();
			}

		}

		for(String info: infoValue){
			newArr.add(info);
		}
		// Get the new key
		if(thisKey == -1){
			if(resGraphDS.getResToInfo().keySet().size()==0){
				thisKey = 0;
			}
			else{
				thisKey = getKeyIfNew(toInt(resGraphDS.getResToInfo().keySet()));
			}
			resGraphDS.getResToInfo().put(thisKey, newArr);
		}


		resGraphDS.getResOrder().add(thisKey);
	}

	private boolean areSame(ArrayList<String> value, ArrayList<String> infoValue) {
		// TODO Auto-generated method stub
		if(value.size()!=infoValue.size()){
			return false;
		}
		for(int i =0; i<value.size(); i++){

			if(value.get(i).equals(infoValue.get(i))!=true){
				return false;
			}
		}
		return true;
	}

	private Integer getKeyIfNew(int[] intArr) {
		int maxValue = 0;
		for(int i : intArr){
			if(i>maxValue){
				maxValue =i;
			}
		}
		return maxValue+1;
	}

	private int[] toInt(Set<Integer> set) {
		int[] a = new int[set.size()];
		int i = 0;
		for (Integer val : set) a[i++] = val;
		return a;
	}

}
