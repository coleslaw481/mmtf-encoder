package org.rcsb.mmtf.dataholders;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.io.FileParsingParameters;
import org.biojava.nbio.structure.io.LocalPDBDirectory.FetchBehavior;
import org.biojava.nbio.structure.io.mmcif.ChemCompGroupFactory;
import org.biojava.nbio.structure.io.mmcif.DownloadChemCompProvider;
import org.biojava.nbio.structure.quaternary.BioAssemblyInfo;
import org.junit.Test;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.rcsb.mmtf.biojavaencoder.EncoderUtils;
import org.rcsb.mmtf.biojavaencoder.ParseFromBiojava;
import org.unitils.reflectionassert.ReflectionAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class TestDataHolders {

  private EncoderUtils encoderUtils;
  private PodamFactory factory;
  private AtomCache cache;
  private FileParsingParameters params;

  public TestDataHolders() {
    encoderUtils = new EncoderUtils();
    factory = new PodamFactoryImpl(); 

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
  public void testSerializable() {
    // MmtfBean
    assertTrue(testClass(MmtfBean.class));
    // This one fails - make sure it still does
    assertFalse(testClass(BioAssemblyInfo.class));
    // The bean to store calpha data
    assertTrue(testClass(CalphaDistBean.class));
    // The old matrix data - this should fail
    assertFalse(testClass(Matrix4d.class));
    // Now test round tripping data
    testDataRoundTrip(MmtfBean.class);
    // Now test if all fields in the mmtf are generated
    testDataComplete("4cup");
    // Now check that the failure bean fails this
    // Now test round tripping data
    assertFalse(testDataRoundTrip(FailureBean.class));
  }

  @SuppressWarnings("unchecked")
  private boolean testClass(@SuppressWarnings("rawtypes") Class class1) {


    Object inBean = null;
    try {
      inBean = class1.newInstance();
    } catch (InstantiationException | IllegalAccessException e2) {
      // Weirdness
      System.out.println("Weirdness in generating instance of generic class");
      e2.printStackTrace();
    }
    byte[] outArr = null;

    try {
      outArr = encoderUtils.getMessagePack(inBean);
    } catch (JsonProcessingException e1) {
      System.out.println("Error generating messagepack");
      e1.printStackTrace();
    }

    // 
    Object  outBean = null;
    try {
      outBean = new ObjectMapper(new  MessagePackFactory()).readValue(outArr, class1);
    } catch( JsonMappingException jsonE){
      System.out.println("Error reading messagepack - is part of test if test doesn't fail");
      return false;
    }
    catch (IOException e) {
      // This would be very weird
      System.out.println("Weird IO exceptipn on reading byte array...");
      e.printStackTrace();
    }

    // Now check they're the same
    ReflectionAssert.assertReflectionEquals(inBean, outBean); 
    return true;
  }

  @SuppressWarnings("unchecked")
  /**
   * Test round tripping dummy data
   * @param class1
   */
  private  boolean testDataRoundTrip(@SuppressWarnings("rawtypes") Class class1) {
    Object inBean = factory.manufacturePojo(class1);
    byte[] outArr = null;

    try {
      outArr = encoderUtils.getMessagePack(inBean);
    } catch (JsonProcessingException e1) {
      System.out.println("Error generating messagepack");
      e1.printStackTrace();
    }

    // 
    Object  outBean = null;
    try {
      outBean = new ObjectMapper(new  MessagePackFactory()).readValue(outArr, class1);
    } catch( JsonMappingException jsonE){
      System.out.println("Error reading messagepack.");
    }
    catch (IOException e) {
      // This would be very weird
      System.out.println("Weird IO exceptipn on reading byte array...");
      e.printStackTrace();
    }
    // Make the failure bean fail
    try{
    ReflectionAssert.assertPropertyReflectionEquals("fieldWithNoGetters",null, outBean);
    ReflectionAssert.assertPropertyReflectionEquals("fieldWithRefactoredGetters",null, outBean);
    return false;
    }
    catch(Exception e){
      
    }
    // Make sure all fields are re-populated
    ReflectionAssert.assertPropertiesNotNull("Some properties are null in re-read object", outBean);
    
    // Now check they're the same
    ReflectionAssert.assertReflectionEquals(inBean, outBean); 
    return true;
  } 

  /**
   * A specific mmtf test - to make sure none of the fields are empty when the thing is encoded
   * @throws StructureException 
   * @throws IOException 
   */
  private void testDataComplete(String pdbId) {
    
    // Utility functions for encoding stuff
    EncoderUtils eu = new EncoderUtils();
    // Get the utility class to get the strucutes
    ParseFromBiojava parsedDataStruct = new ParseFromBiojava();
    Map<Integer, PDBGroup> totMap = new HashMap<Integer, PDBGroup>();
    // Parse the data into the basic data structure
    parsedDataStruct.createFromJavaStruct(pdbId, totMap);
    MmtfBean mmtfBean = null;
    // Compress the data and get it back out
    try {
      mmtfBean = eu.compressMainData(parsedDataStruct.getBioStruct(), parsedDataStruct.getHeaderStruct());
    } catch (IOException e) {
      // Here we've failed to read or write a byte array
      e.printStackTrace();
      System.err.println("Error reading or writing byte array - file bug report");
      throw new RuntimeException(e);
    }
    // Make sure all fields are re-populated
    ReflectionAssert.assertPropertiesNotNull("Some properties are null in mmtf generated from biojava object",  mmtfBean);
  }
}



