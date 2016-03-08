package org.rcsb.mmtf.integrationtest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.io.FileUtils;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.io.FileParsingParameters;
import org.biojava.nbio.structure.io.LocalPDBDirectory.FetchBehavior;
import org.biojava.nbio.structure.io.mmcif.ChemCompGroupFactory;
import org.biojava.nbio.structure.io.mmcif.DownloadChemCompProvider;
import org.junit.Test;
import org.rcsb.mmtf.biojavaencoder.EncodeStructure;
import org.rcsb.mmtf.decoder.BioJavaStructureDecoder;
import org.rcsb.mmtf.decoder.CheckOnBiojava;
import org.rcsb.mmtf.decoder.CheckOnRawApi;
import org.rcsb.mmtf.decoder.DecodeStructure;
import org.rcsb.mmtf.decoder.ParsingParams;

public class TestParseMMCif {

  private DecodeStructure decodeStructure;
  private AtomCache cache;
  private FileParsingParameters params;
  private CheckOnBiojava checkEquiv;

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
    checkEquiv = new CheckOnBiojava();
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
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // Weird NMR structure
    pdbId ="1o2f";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // Another weird structure (jose's suggestion) 
    pdbId ="3zyb";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // B-DNA structure
    pdbId ="1bna";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));	
    // DNA structure
    pdbId = "4y60";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // Sugar structure
    pdbId = "1skm";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));		
    // Ribosome
    pdbId = "4v5a";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // Biosynthetic protein
    pdbId = "5emg";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // Calpha atom is missing (not marked as calpha)
    pdbId = "1lpv";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));
    // NMR structure with multiple models - one of which has chain missing
    pdbId = "1msh";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));  
    // No ATOM records just HETATM records (in PDB). Opposite true for MMCif. It's a D-Peptide.
    pdbId = "1r9v";
    checkEquiv.checkIfStructuresSame(StructureIO.getStructure(pdbId),roundTripStruct(pdbId, pp));  
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
    Structure mmcifStruct  = StructureIO.getStructure(pdbId);
    FileUtils.writeByteArrayToFile(new File("pathname"), es.encodeFromBiojava(mmcifStruct));
    byte[] inArr = FileUtils.readFileToByteArray(new File("pathname"));
    // Now do the checks on the Raw data
    CheckOnRawApi checkRaw = new CheckOnRawApi(inArr);
    checkRaw.checkIfSeqResInfoSame(mmcifStruct, params);
    // Now decode the data and return this new structure
    BioJavaStructureDecoder bjsi = new BioJavaStructureDecoder();
    decodeStructure = new DecodeStructure(inArr);
    decodeStructure.getStructFromByteArray(bjsi, pp);
    Structure struct = bjsi.getStructure();
    // Revert back
    params.setUseInternalChainId(oldValue);
    cache.setFileParsingParams(params);
    return struct;
  }


}
