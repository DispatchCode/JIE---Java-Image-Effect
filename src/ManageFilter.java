/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione  : E' la classe model; comunica con i filtri e con il Controller
 * --------------------------------------------------------------------------
 * Licenza      : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data         : 18/07/2014
 * Aggiornamento: 07/08/2015
 * --------------------------------------------------------------------------
 * Autore       : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */



import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import java.awt.Desktop;

class ManageFilter
{
  //
  // --------------------------------------------------------------------------------------------
  private String[] filters;                 // Elenco dei filtri utilizzati
  private File inputFile, outputFile;
  private RawRGBImage storeImage;
  
  // Elenco dei Kernel personalizzati con le relative opzioni (se ci sono)
  private HashMap<String, ArrayList<Kernel>> personalizedKernels;
  
  // Utilizzato temporaneamente come store, contiene i kernel
  private ArrayList<Kernel> tempKernel;
  // --------------------------------------------------------------------------------------------
  
  //
  // --------------------------------------------------------------------------------------------
  ManageFilter() 
  {
    filters = loadFilters();
    
    personalizedKernels = new HashMap<String, ArrayList<Kernel>>();
    
    loadPersonalizedKernels();
    
  }
  // --------------------------------------------------------------------------------------------
  
  // Metodi di utilita'
  // --------------------------------------------------------------------------------------------
  private String[] loadFilters() 
  {
    return new String[] {"Bilinear", "GrayScale", "Inversion", "Contrast", "Blur","Motion Blur Left","Motion Blur Right","Sharpen","Median","Salt And Pepper Noise"};
  }
  
  String[] getFilters() 
  {
    return filters;
  }
  
  void setInputFile(File inputFile) 
  {
    this.inputFile = inputFile;
  }
  
  void setOutputFile(File outputFile) 
  {
    this.outputFile = outputFile;
  }
  
    
  // Opzioni dei filtri
  String[] loadOption(String filterName) 
  {
    
    ArrayList<String> listOfKernels;
    
    switch(filterName) 
    {
      case "Blur":
        listOfKernels = new ArrayList<String>(Arrays.asList(new String[] {"3x3 Kernel", "5x5 Kernel", "7x7 Kernel", "9x9 Kernel", "15x15 Kernel"}));
        break;
      case "Motion Blur Left":
        listOfKernels = new ArrayList<String>(Arrays.asList(new String[] {"3x3 Kernel", "5x5 Kernel", "7x7 Kernel", "9x9 Kernel"}));
        break;
      case "Motion Blur Right":
        listOfKernels = new ArrayList<String>(Arrays.asList(new String[] {"3x3 Kernel", "5x5 Kernel", "7x7 Kernel", "9x9 Kernel"}));
        break;
      case "Sharpen":
        listOfKernels = new ArrayList<String>(Arrays.asList(new String[] {"3x3 Kernel", "5x5 Kernel"}));
        break;
      case "Median":
        listOfKernels = new ArrayList<String>(Arrays.asList(new String[] {"3x3 Filter", "5x5 Filter", "7x7 Filter", "9x9 Filter"}));
        break;
      case "Salt And Pepper Noise":
        listOfKernels = new ArrayList<String>(Arrays.asList(new String[] {"5%","25%","50%","75%", "95%"}));
        break;
      default:
        return null;
    }
    
    // Ottiene tutte le opzioni personalizzate per il filtro scelto
    ArrayList<Kernel> kernelList = personalizedKernels.get(filterName);
    // Se non ci sono opzioni, vado oltre
    if(kernelList != null) 
    {
      for(int i=0; i<kernelList.size(); i++) 
      {
        listOfKernels.add(kernelList.get(i).getOptionName());
      }
    }
    
    // La variabile tmpKernel (ArrayList<Kernel>) memorizza
    // le opzioni sottoforma di Kernel
    tempKernel = kernelList;
    
    String[] options = new String[listOfKernels.size()];
    listOfKernels.toArray(options);
    
    return options;
  }
  // -----------------------------------------------------------------------------------------------
  
  
  // Sceglie il filtro da applicare
  // -----------------------------------------------------------------------------------------------
  RawRGBImage run(String filter, String option) 
  {
    if(inputFile == null) return null;
  
    ImageFilter idf = null;
    RawRGBImage img = null;
    
    // Il nuovo kernel da settare, inizialmente nullo
    int[][] newKernel = null;
    // Se l'array list dei kernel personalizzati e' vuoto
    // passo oltre; in caso contrario prendo dall'array list l'opzione selezionata
    if(tempKernel != null) 
    {
      for(int i=0; i<tempKernel.size(); i++) 
      {
        if(tempKernel.get(i).getOptionName().equals(option)) 
        {
          newKernel = tempKernel.get(i).getKernel();
        }
      }
    }
    
    switch(filter) 
    {
      case "Bilinear":
        idf = new BilinearInterpolationBayerDemosaicingFilter();
        img = new RawRGBImage(inputFile);
      break;
      case "GrayScale":
        idf = new GrayScaleFilter();
        img = new RawRGBImage(inputFile);
        break;
      case "Inversion":
        idf = new PixelInversionFilter();
        img = new RawRGBImage(inputFile);
        break;
      case "Contrast":
        idf = new ContrastFilter();
        img = new RawRGBImage(inputFile);
        break;
      case "Blur":
        if(newKernel == null) 
        {
          idf = new BlurFilter(getIndexOption(filter,option));
        }
        else {
          idf = new BlurFilter();
          idf.setKernel(newKernel);
        }
        
        img = new RGBImage(inputFile);
        break;
      case "Motion Blur Left":
        if(newKernel == null) 
        {
          idf = new MotionBlurLeftFilter(getIndexOption(filter,option));
        }
        else {
          idf = new MotionBlurLeftFilter();
          idf.setKernel(newKernel);
        }
        
        img = new RGBImage(inputFile);
        break;
      case "Motion Blur Right":
        if(newKernel == null) 
        { 
          idf = new MotionBlurRightFilter(getIndexOption(filter,option));
        }
        else 
        {
          idf = new MotionBlurRightFilter();
          idf.setKernel(newKernel);
        }
        
        img = new RGBImage(inputFile);
        break;
      case "Sharpen":
        if(newKernel == null) 
        {
          idf = new SharpenFilter(getIndexOption(filter,option));
        }
        else {
          idf = new SharpenFilter();
          idf.setKernel(newKernel);
        }
        
        img = new RGBImage(inputFile);
        break;
      case "Median":
        idf = new MedianFilter(getIndexOption(filter,option));
        img = new RawRGBImage(inputFile);
        break;
      case "Salt And Pepper Noise":
        idf = new SaltAndPepperNoise(getIndexOption(filter,option));
        img = new RawRGBImage(inputFile);
    }
    
    storeImage = null;
    storeImage = idf.filter(img);

    return storeImage;
  }
  // ------------------------------------------------------------------------------------------------
  
  // Salva l'immagine appena calcolata
  // ------------------------------------------------------------------------------------------------
  boolean save(boolean openImage) 
  {
    
    if(outputFile == null) return false;
    
    try {
      ImageIO.write(storeImage.getBufferedImage(), "PNG", outputFile);
      if(openImage) {
        Desktop d = Desktop.getDesktop();
        d.open(outputFile);
      }
      storeImage = null;
    } catch(Exception e) {
      return false;
    }
    return true;
  }
  // ------------------------------------------------------------------------------------------------
  
  
  // Indice dell'opzione di un filtro all'interno dell'array
  private int getIndexOption(String filter, String option) 
  {
    String[] f = loadOption(filter);
    
    for(int i=0; i<f.length; i++) 
    {
      if(f[i].equals(option)) 
      {
        return i+1;
      }
    }
    return -1;
  }
  
  
  // Caricamento kernel personalizzati dai file di testo
  // -------------------------------------------------------------------------------------------------
  private void loadPersonalizedKernels() 
  {
    // Path alla directory che contiene i filtri personalizzati
    File path = new File("../filters/");
    
    /*
     * Elenco delle directory:
     *   Ogni directory e' un filtro
     *
     */
    File[] directories = path.listFiles();
    
    for(int i=0; i<directories.length; i++) {
      // Estraggo l'elenco dei files da una directory
      File[] files = directories[i].listFiles();
      
      // Rimuovo l'elemento dalla mappa; se non e' presente
      // restituisce "null".
      ArrayList<Kernel> options = personalizedKernels.remove(directories[i]);
      if(options == null) {
        options = new ArrayList<Kernel>();
      }
      
      // Inizio a scorrere le singole opzioni, caricando effettivamente
      // i dati salvati in precedenza (i kernels)
      Kernel kernelObj = null;
      for(int j=0; j<files.length; j++) {
        // Deserializzazione
        try {
          ObjectInputStream ois = new ObjectInputStream(new FileInputStream(files[j]));
          kernelObj = (Kernel) ois.readObject();
          ois.close();
          
        } catch(Exception e) {e.printStackTrace();}
        
        options.add(kernelObj);
      }
      
      // Se ci sono opzioni, inserisco la coppia (NomeFiltro, Opzioni) nella mappa.
      if(kernelObj != null) personalizedKernels.put(kernelObj.getFilterName(), options);
      
    }

  }
  
}