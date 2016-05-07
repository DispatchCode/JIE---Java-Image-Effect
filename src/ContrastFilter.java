/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Mette in evidenza i pixel piu' luminosi.
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


class ContrastFilter implements ImageFilter 
{
  public RawRGBImage filter(RawRGBImage ri) 
  {
    int w = ri.getWidth();
    int h = ri.getHeight();
    
    for(int i=0; i<w; i++) 
    {
      for(int j=0; j<h; j++) 
      {
        int r = light(ri.getRed(i,j));
        int g = light(ri.getGreen(i,j));
        int b = light(ri.getBlue(i,j));
        
        ri.setPixel(i,j, r,g,b);
      }
    }
    
    return ri;
  }
  
  private int light(int l) 
  {
    return (l < 128) ? (int)(l/1.2) : (((int) l*1.2) > 255 ? 255 : (int)(l*1.2));
  }
  
  public void setKernel(int[][] kernel) {}
}