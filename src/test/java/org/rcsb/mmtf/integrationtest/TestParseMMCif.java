package org.rcsb.mmtf.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.io.FileParsingParameters;
import org.biojava.nbio.structure.io.LocalPDBDirectory.FetchBehavior;
import org.biojava.nbio.structure.io.mmcif.ChemCompGroupFactory;
import org.biojava.nbio.structure.io.mmcif.DownloadChemCompProvider;
import org.biojava.nbio.structure.quaternary.BioAssemblyInfo;
import org.biojava.nbio.structure.quaternary.BiologicalAssemblyTransformation;
import org.junit.Test;
import org.rcsb.mmtf.biojavaencoder.EncodeStructure;
import org.rcsb.mmtf.decoder.BioJavaStructureDecoder;
import org.rcsb.mmtf.decoder.DecodeStructure;
import org.rcsb.mmtf.decoder.ParsingParams;

public class TestParseMMCif {

  private DecodeStructure decodeStructure;
  private AtomCache cache;
  private FileParsingParameters params;

  public TestParseMMCif(){

    cache = new AtomCache();
    cache.setUseMmCif(true);
    cache.setFetchBehavior(FetchBehavior.FETCH_FILES);
    params = cache.getFileParsingParams();
    params.setCreateAtomBonds(true);
    params.setAlignSeqRes(true);
    params.setParseBioAssembly(true);
    DownloadChemCompProvider dcc = new DownloadChemCompProvider();
    ChemCompGroupFactory.setChemCompProvider(dcc);
    dcc.setDownloadAll(true);
    dcc.checkDoFirstInstall();
    params.setLoadChemCompInfo(true);
    params.setUseInternalChainId(true);
  }


  @Test
  public void testAsymChainIds() throws IOException, StructureException, IllegalAccessException, InvocationTargetException {
    // Set the params
    params.setUseInternalChainId(true);
    cache.setFileParsingParams(params);
    testAll();
  }

  @Test
  public void testAuthChainIds() throws IOException, StructureException, IllegalAccessException, InvocationTargetException {
    // Set the params
    params.setUseInternalChainId(false);
    cache.setFileParsingParams(params);
    testAll();
  }


  /**
   * Function to round trip everything based on the current params
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws IOException
   * @throws StructureException
   */
  private void testAll() throws IllegalAccessException, InvocationTargetException, IOException, StructureException{

    ParsingParams pp = new ParsingParams();
    pp.setParseInternal(params.isUseInternalChainId());
    StructureIO.setAtomCache(cache);
    String pdbId;
    //Standard structure
    pdbId ="4cup";
    checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // Weird NMR structure
    pdbId ="1o2f";
    checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // Another weird structure (jose's suggestion) 
    pdbId ="3zyb";
    checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // B-DNA structure
    pdbId ="1bna";
    checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));	
    // DNA structure
    pdbId = "4y60";
    checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // Sugar structure
    pdbId = "1skm";
    checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));		
    // Ribosome
    pdbId = "4v5a";
    checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));		
  }

  /**
   * 
   * @return
   * @throws IOException 
   * @throws StructureException 
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   */
  private Structure roundTripStruct(String pdbId, ParsingParams pp) throws IOException, IllegalAccessException, InvocationTargetException, StructureException{
    // We need to set the parsing params to this
    boolean oldValue = params.isUseInternalChainId();
    params.setUseInternalChainId(true);
    cache.setFileParsingParams(params);
    EncodeStructure es = new EncodeStructure();
    FileUtils.writeByteArrayToFile(new File("pathname"), es.encodeFromBiojava(StructureIO.getStructure(pdbId)));
    byte[] inArr = FileUtils.readFileToByteArray(new File("pathname"));
    BioJavaStructureDecoder bjsi = new BioJavaStructureDecoder();
    decodeStructure = new DecodeStructure(inArr);
    decodeStructure.getStructFromByteArray(bjsi, pp);
    Structure struct = bjsi.getStructure();
    // Revert back
    params.setUseInternalChainId(oldValue);
    cache.setFileParsingParams(params);
    return struct;
  }

  /**
   * Checks if the bioassembly data between two Biojava structures are equivalent
   * @param structOne
   * @param structTwo
   * @return
   */
  private void checkIfBioassemblySame(Structure structOne, Structure structTwo){

    // Get the headers
    Map<Integer, BioAssemblyInfo> bioassembliesOne = structOne.getPDBHeader().getBioAssemblies();
    Map<Integer, BioAssemblyInfo> bioassembliesTwo = structTwo.getPDBHeader().getBioAssemblies();
    assertEquals(bioassembliesOne.keySet(), bioassembliesTwo.keySet());
    for(Entry<Integer, BioAssemblyInfo> entry: bioassembliesOne.entrySet()){
      // Get the bioassembly info
      BioAssemblyInfo valueOne = entry.getValue();
      BioAssemblyInfo valueTwo = bioassembliesTwo.get(entry.getKey());
      assertEquals(valueOne.getId(), valueTwo.getId());
      assertEquals(valueOne.getMacromolecularSize(), valueTwo.getMacromolecularSize());
      // Check there's the same number of transforms
      assertEquals(valueOne.getTransforms().size(), valueTwo.getTransforms().size());
      // Build a map of chain id to matrix 4d
      Set<String> keySetOne = new TreeSet<>();
      Set<String> keySetTwo = new TreeSet<>();
      Set<String> valSetOne = new TreeSet<>();
      Set<String> valSetTwo = new TreeSet<>();
      for(int i= 0; i< valueOne.getTransforms().size();i++){
        BiologicalAssemblyTransformation transformOne = valueOne.getTransforms().get(i);
        BiologicalAssemblyTransformation transformTwo = valueTwo.getTransforms().get(i);
        // Check these are the same
        keySetOne.add(transformOne.getChainId());
        keySetTwo.add(transformTwo.getChainId());
        valSetOne.add(transformOne.getTransformationMatrix().toString());
        valSetTwo.add(transformTwo.getTransformationMatrix().toString());
      }
      assertEquals(keySetOne, keySetTwo);
      assertEquals(valSetOne, valSetTwo);

    }
  }

  /**
   * Test of sequence and seqres group level information
   * @param structOne
   * @param structTwo
   * @return
   */
  private void checkIfSeqResInfoSame(Structure biojavaStruct){
    if(params.isUseInternalChainId()){
      // Get the seqres group list
      int[] decodedSeqResGroupList = decodeStructure.getSeqResGroupList();
      // Get the string sequences
      List<String> sequenceStrings = decodeStructure.getSequenceInfo();
      int groupCounter = 0;
      int chainCounter = 0;
      // Get the sequence information
      for(int currentModelIndex = 0; currentModelIndex < biojavaStruct.nrModels(); currentModelIndex++){
        for(Chain currentChain : biojavaStruct.getChains(currentModelIndex)){
          // Get the sequence
          assertEquals(sequenceStrings.get(chainCounter), currentChain.getSeqResSequence());
          List<Group> thisChainSeqResList = new ArrayList<>();
          for(Group seqResGroup : currentChain.getSeqResGroups()){
            thisChainSeqResList.add(seqResGroup);
          }
          // Now go through and check the indices line up
          for(int i = 0; i < currentChain.getAtomGroups().size(); i++){
            // Get the group
            Group testGroup = currentChain.getAtomGroup(i);
            int testGroupInd = thisChainSeqResList.indexOf(testGroup);
            assertEquals(testGroupInd, decodedSeqResGroupList[groupCounter]);
            groupCounter++;
          }
          chainCounter++;
        }
      }
    }
    // Otherwise we need to parse in a different
    else{
      System.out.println("Using public facing chain ids -> seq res not tested");
    }

  }


  /**
   * Check if all features between the two structures  are the same
   * @param biojavaStruct the input biojava structure parsed from the  mmcif file
   * @param structTwo the BioJava structure parsed from the MMTF file
   */
  private void checkIfStructuresSame(Structure biojavaStruct, Structure structTwo){
    assertTrue(checkIfAtomsSame(biojavaStruct, structTwo));
    checkIfSeqResInfoSame(biojavaStruct);
  }


  /**
   * Broad test of atom similarity
   * @param structOne
   * @param structTwo
   * @return
   */
  private boolean checkIfAtomsSame(Structure structOne, Structure structTwo) {
    // Firt check the bioassemblies
    checkIfBioassemblySame(structOne, structTwo);
    // Now check the sequence
    checkIfSequenceSame(structOne, structTwo);
    int numModels = structOne.nrModels();
    if(numModels!=structTwo.nrModels()){
      System.out.println("ERROR - diff number models");
      return false;
    }
    for(int i=0;i<numModels;i++){
      List<Chain> chainsOne = structOne.getChains(i);
      List<Chain> chainsTwo = structTwo.getChains(i);

      if(chainsOne.size()!=chainsTwo.size()){
        System.out.println("ERROR - diff number chains");
        return false;
      }
      // Now loop over
      for(int j=0; j<chainsOne.size();j++){
        Chain chainOne = chainsOne.get(j);
        Chain chainTwo = chainsTwo.get(j);
        // Check they have the same chain id
        assertEquals(chainOne.getChainID(), chainTwo.getChainID());
        List<Group> groupsOne = chainOne.getAtomGroups();
        List<Group> groupsTwo = chainTwo.getAtomGroups();
        if(groupsOne.size()!=groupsTwo.size()){
          System.out.println("ERROR - diff number groups");
          return false;
        }
        for(int k=0; k<groupsOne.size();k++){
          Group groupOne = groupsOne.get(k);
          Group groupTwo = groupsTwo.get(k);
          // Check if the groups are of the same type
          assertEquals(groupOne.getType(), groupTwo.getType());    
          // Get the first conf
          List<Atom> atomsOne = groupOne.getAtoms();
          List<Atom> atomsTwo = groupTwo.getAtoms();
          if(groupOne.getAltLocs().size()!=0){
            if(groupTwo.getAltLocs().size()!=groupOne.getAltLocs().size()){
              System.out.println("ERROR - diff number alt locs");
            }
            for(Group altLocOne: groupOne.getAltLocs()){
              for(Atom atomAltLocOne: altLocOne.getAtoms()){
                atomsOne.add(atomAltLocOne);
              }
            }
            for(Group altLocTwo: groupTwo.getAltLocs()){
              for(Atom atomAltLocTwo: altLocTwo.getAtoms()){
                atomsTwo.add(atomAltLocTwo);
              }
            }
          }
          if(atomsOne.size()!=atomsTwo.size()){
            System.out.println("ERROR - diff number atoms");
            System.out.println(groupOne.getPDBName()+" vs "+groupTwo.getPDBName());
            System.out.println(atomsOne.size()+" vs "+atomsTwo.size());
            return false;						
          }
          for(int l=0;l<atomsOne.size();l++){
            Atom atomOne = atomsOne.get(l);
            Atom atomTwo = atomsTwo.get(l);
            if(atomOne.toPDB().equals(atomTwo.toPDB())){

            }
            else{
              System.out.println("MINE"+atomOne.toPDB());
              System.out.println("BIOJAVA"+atomTwo.toPDB());
              return false;
            }
            if(i==0){
              if(atomOne.getBonds()==null){
                if(atomTwo.getBonds()!=null){
                  return false;
                }
              }
              else if(atomTwo.getBonds()==null){
                return false;
              }
              else if(atomOne.getBonds().size()!=atomTwo.getBonds().size()){
                System.out.println("DIFFERENT NUMBER OF BONDS: "+atomOne.getBonds().size()+" VS "+atomTwo.getBonds().size());
                System.out.println(atomOne);
                System.out.println(atomTwo);
                return false;
              }
            }
          }
        }

      }
    }	
    return true;

  }
}
