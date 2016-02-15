package org.codec.dataholders;
//package org.compression.domstructureholders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.biojava.nbio.structure.Atom;
//import org.biojava.nbio.structure.Bond;
//import org.biojava.nbio.structure.Calc;
//import org.biojava.nbio.structure.Group;
//
//public class ResidueSummarisers {
//	// Class of methods to summarise residue information
//	/**
//	 * Function to condense available Group information - output can be used to append to available list	
//	 * @param g
//	 * @return
//	 */
//	private List<Integer> condenseGroupInfo(Group g) {
//		// Function to condense a Group into the required information -> Bond Orders, 
//		List<Atom> atoms = g.getAtoms();
//		int n = atoms.size();
//		if (n == 0) {
//			// Store 
//			System.out.println("creating empty bond list");
//		}
//		int[] bondList = new int[2*n];
//		Arrays.fill(bondList, -1);
//		for (int i = 0; i < n; i++) {
//			Atom a = atoms.get(i);
//			for (Bond b: a.getBonds()) {
//				Atom other = b.getOther(a);
//				int index = atoms.indexOf(other);
//				if (index == -1) {
//					continue;
//				}
//				// Greater than i ensures it's further along
//				if (index > i && bondList[index] == -1) {
//					// Store the index
//					bondList[index] = i;
//					// Store the distance
//					bondList[n+index] = (int)Math.round(Calc.getDistance(a, other)*1000);
//				}
//			}
//		}
//		return intLToList(bondList);
//	}
//
//}
