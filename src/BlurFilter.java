/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Applica un filtro blur, rendendo l'immagine meno nitida
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


class BlurFilter implements ImageFilter 
{
  //
  // ----------------------------------------------------------------------------
  private int[][] kernel;
  private int sum;
  
  public static final int FILTER_3X3   = 1;     // Dimensione kernel
  public static final int FILTER_5X5   = 2;
  public static final int FILTER_7X7   = 3;
  public static final int FILTER_9X9   = 4;
  public static final int FILTER_15X15 = 5;
  // ----------------------------------------------------------------------------
 
  //
  // ----------------------------------------------------------------------------
  BlurFilter(int filter) 
  {
    switch(filter) 
    {
      case FILTER_3X3:
        kernel = new int[][] {{1,2,1},{2,4,2},{1,2,1}};
        break;
      case FILTER_5X5:
        kernel = new int[][] {{1,1,1,1,1}, {1,1,1,1,1}, {1, 1, 1, 1, 1}, {1,1,1,1,1}, {1,1,1,1,1}};
        break;
      case FILTER_7X7:
        kernel = new int[][] {{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1, 1, 1, 1, 1,1,1}, {1,1,1,1,1,1,1}, {1,1,1,1,1,1,1},{1,1,1,1,1,1,1}, {1,1,1,1,1,1,1}};
        break;
      case FILTER_9X9:
        kernel = new int[][] {{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1, 1, 1, 1, 1,1,1,1,1}, {1,1,1,1,1,1,1,1,1}, {1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}, {1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}};
        break;
      case FILTER_15X15:
        kernel = new int[15][15];
        for(int i=0; i<15; i++) {
          for(int j=0; j<15; j++) {
            kernel[i][j] = 1;
          }
        }
        break;
      default:
        kernel = new int[][] {{1,2,1},{2,4,2},{1,2,1}};
    }
    sumKernel();
  }
  
  BlurFilter(){}
  
  // ------------------------------------------------------------------------------
  
  public void setKernel(int[][] kernel) {
    this.kernel = kernel;
    sumKernel();
  }
  
  
  
  public RawRGBImage filter(RawRGBImage rawImage) 
  {
    RGBImage rgbImage = (RGBImage) rawImage;
    
    int w = rgbImage.getWidth();
    int h = rgbImage.getHeight();
    
    int wf = kernel.length;
    
    for(int y = 0; y < h; y++) 
    {
      for(int x = 0; x < w; x++) 
      {
        int tr = 0;
        int tg = 0;
        int tb = 0;
        
        for(int i = 0; i < wf; i++) 
        {
          for(int j = 0; j < wf; j++) 
          {
            int partial_x = (x - wf / 2 + j + w) % w;
            int partial_y = (y - wf / 2 + i + h) % h;
            
            int r = rgbImage.getRed(partial_x, partial_y);
            int g = rgbImage.getGreen(partial_x, partial_y);
            int b = rgbImage.getBlue(partial_x, partial_y);
            
            r*=kernel[i][j];
            g*=kernel[i][j];
            b*=kernel[i][j];
            
            tr+=r;
            tg+=g;
            tb+=b;
          }
        }
        
        tr/=sum;
        tg/=sum;
        tb/=sum;
        
        rgbImage.setPixel(x,y, control(tr), control(tg), control(tb));
      }
    }

    return (RawRGBImage) rgbImage;
  }
  
  private int control(int c) {
    return (c > 255 ? 255 : (c < 0 ? 0 : c));
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
}