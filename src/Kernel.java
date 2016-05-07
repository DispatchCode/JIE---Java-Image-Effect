/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Rappresenta un kernel associato al nome del filtro
 *            : ed al nome dell'opzione
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 07/08/2015
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


import java.io.*;

class Kernel implements Serializable 
{
  
  // Variabili di istanza
  // ------------------------------------------------------------------------
  private String filterName, optionName;
  private int[][] kernel;
  // ------------------------------------------------------------------------
  
  
  // Costruttore
  // ------------------------------------------------------------------------
  Kernel(String filterName, String optionName, int[][] kernel) 
  {
    this.filterName = filterName;
    this.optionName = optionName;
    this.kernel     = kernel;
  }
  // ------------------------------------------------------------------------
  
  
  // Metodi getter...
  String getFilterName() 
  {
    return filterName;    
  }
  
  String getOptionName() 
  {
    return optionName;
  }
  
  int[][] getKernel() 
  {
    return kernel;
  }

}