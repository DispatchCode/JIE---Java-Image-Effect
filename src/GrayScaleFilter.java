/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Converte un immagine in Grayscale
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


class GrayScaleFilter implements ImageFilter 
{
  public RawRGBImage filter(RawRGBImage ri) 
  {
    int w = ri.getWidth();
    int h = ri.getHeight();
    
    int r=0, g=0, b=0;
    
    for(int i=0; i<w; i++) 
    {
      for(int j=0; j<h; j++) 
      {
        if(ri.pixelExist(i,j)) 
        {
          r = ri.getRed(i,j);
          g = ri.getGreen(i,j);
          b = ri.getBlue(i,j);
          int k = (int) (.56 * g + .33 * r + .11 * b);
          ri.setPixel(i,j, k, k, k);
        }
      }
    }

    return ri;
  }
  
  public void setKernel(int[][] kernel) {}
}