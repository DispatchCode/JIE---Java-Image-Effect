/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Rappresenta l'immagine di output in un nuovo array di pixel.
 *            : la classe estende RawRGBImage
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */
 

import java.awt.image.BufferedImage;
import java.io.File;

class RGBImage extends RawRGBImage 
{
  // Rappresenta l'immagine di output in un nuovo array di pixel
  // ----------------------------------------------------------
  private int[] newpixels;
  // ----------------------------------------------------------
  
  //
  // ----------------------------------------------------------
  RGBImage(String fileName) 
  {
    super(fileName);
    newpixels = new int[getWidth() * getHeight()];
  }
  
  RGBImage(File file) 
  {
    super(file);
    newpixels = new int[getWidth() * getHeight()];
  }
  // -----------------------------------------------------------
  
  void setPixel(int x, int y, int r, int g, int b) 
  {
    newpixels[y*getWidth()+x] = (0xFF000000 | r << 16 | g << 8 | b);
  }

  int getRed(int p) 
  {
    return (p >> 16) & 0xFF;
  }
  
  int getGreen(int p) 
  {
    return (p >> 8) & 0xFF;
  }
  
  int getBlue(int p) 
  {
    return (p & 0xFF);
  }
  
  BufferedImage getBufferedImage() 
  {
    BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    bi.setRGB(0,0,getWidth(), getHeight(), newpixels, 0, getWidth());
    return bi;
  }
}