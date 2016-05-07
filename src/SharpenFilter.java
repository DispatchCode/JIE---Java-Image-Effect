/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Applica il filtro sharpen. Mette in evidenza i bordi degli elementi
 * --------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * --------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


class SharpenFilter implements ImageFilter 
{
  // Variabili di istanza
  // ----------------------------------------------------------------------------
  public static final int FILTER_3X3 = 1; // Scelta kernel
  public static final int FILTER_5X5 = 2;
  
  private int[][] kernel;    // kernel per filtro sharpen
  private int sum;
  // -----------------------------------------------------------------------------
  
  // Costruttori
  // -----------------------------------------------------------------------------
  SharpenFilter() {}
  
  SharpenFilter(int filter) 
  {
    switch(filter) 
    {
      case FILTER_3X3:
        kernel = new int[][] {{0,-1,0},{-1,5,-1}, {0,-1,0}};
        break;
      case FILTER_5X5:
        kernel = new int[][] {{-1,-1,-1,-1,-1}, {-1, 2, 2, 2, -1}, {-1, 2, 8, 2, -1}, {-1, 2, 2, 2, -1}, {-1, -1, -1, -1, -1}};
        break;
      default:
        kernel = new int[][] {{0,-1,0},{-1,5,-1}, {0,-1,0}};
    }
    sumKernel();
  }
  // -----------------------------------------------------------------------------
  
  
  public void setKernel(int[][] kernel) {
    this.kernel = kernel;
    sumKernel();
  }
  
  
  
  public RawRGBImage filter(RawRGBImage rawImage) 
  {
    RGBImage img = (RGBImage) rawImage;
    
    int w = img.getWidth();
    int h = img.getHeight();
    
    int fw = kernel.length;
    
    for(int y=0; y<h; y++) 
    {
      for(int x=0; x<w; x++) 
      {
        int r = 0;
        int g = 0;
        int b = 0;
        
        for(int i=0; i < fw; i++) 
        {
          for(int j=0; j < fw; j++) 
          {
            int partial_x = (x - fw / 2 + j + w) % w;
            int partial_y = (y - fw / 2 + i + h) % h;
            
            int r1 = img.getRed(partial_x, partial_y);
            int g1 = img.getGreen(partial_x, partial_y);
            int b1 = img.getBlue(partial_x, partial_y);

            r1*=kernel[i][j];
            g1*=kernel[i][j];
            b1*=kernel[i][j];
            
            r += r1;
            g += g1;
            b += b1;
          }
        }
        
        r/=sum;
        g/=sum;
        b/=sum;
        
        img.setPixel(x, y, control(r), control(g), control(b));
      }
    }
    
    return (RawRGBImage) img;
  }
  
  private void sumKernel() 
  {
    for(int i=0; i<kernel.length; i++) 
    {
      for(int j=0; j<kernel.length; j++) 
      {
        sum+=kernel[i][j];
      }
    }
  }
  
  private int control(int p) 
  {
    return (p > 255 ? 255 : (p < 0 ? 0 : p));
  }
}