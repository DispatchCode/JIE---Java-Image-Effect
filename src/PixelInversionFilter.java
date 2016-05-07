/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Inverte i pixel dell'immagine
 * --------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * --------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


class PixelInversionFilter implements ImageFilter 
{
  public RawRGBImage filter(RawRGBImage ri) 
  {
    int w = ri.getWidth();
    int h = ri.getHeight();
    
    for(int i=0; i<w; i++) 
    {
      for(int j=0; j<h; j++) 
      {
        int r = 0xFF - ri.getRed(i,j);
        int g = 0xFF - ri.getGreen(i,j);
        int b = 0xFF - ri.getBlue(i,j);
        
        ri.setPixel(i,j,r,g,b);
      }
    }
    
    return ri;
  }
  
  public void setKernel(int[][] kernel) {}
}