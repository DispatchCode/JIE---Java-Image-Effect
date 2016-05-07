/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Filtro PNG per JFileChooser
 * --------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * --------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


import java.io.*;
import javax.swing.filechooser.FileFilter;   
 
class PNGSaveFilter extends FileFilter
{ 
  public boolean accept(File f)
  {
    if (f.isDirectory())
    {
      return false;
    }
    String s = f.getName();

    return s.endsWith(".png")||s.endsWith(".PNG");
  }

  public String getDescription() 
  {
    return "*.png,*.PNG";
  }
}