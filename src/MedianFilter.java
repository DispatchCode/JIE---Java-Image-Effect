/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Applica il filtro Median ad un immagine. E' valido in molti casi
 *            : in cui l'immagine sorgente (input) contenga degli errori.
 *            : Un caso che puo' essere citato come esempio è il salt and pepper noise
 *            : Il test puo' essere condotto sull'immagine "scacchiera.png"
 * --------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


import java.util.Arrays;


class MedianFilter implements ImageFilter 
{
  // Variabili di istanza
  // ------------------------------------------------------------------------------------
  public static final int FILTER_3X3 = 1;    // Dimensioni dei kernel
  public static final int FILTER_5X5 = 2;
  public static final int FILTER_7X7 = 3;
  public static final int FILTER_9X9 = 4;

  private int[] red, green, blue;            // Somma dei rispettivi colori
  private int filter;
  // ------------------------------------------------------------------------------------
  
  // Costruttori
  // ------------------------------------------------------------------------------------
  MedianFilter(int dimension) 
  {
    switch(dimension) 
    {
      case FILTER_3X3:
        filter = 9;
        break;
      case FILTER_5X5:
        filter = 25;
        break;
      case FILTER_7X7:
        filter = 49;
        break;
      case FILTER_9X9:
        filter = 81;
        break;
      default:
        filter = 9;
    }
  }
  
  MedianFilter() {}
  // --------------------------------------------------------------------------------------
  
  
  
  // Definito in ImageFilter
  // --------------------------------------------------------------------------------------
  public RawRGBImage filter(RawRGBImage rawImage) 
  {
  
    int w = rawImage.getWidth();
    int h = rawImage.getHeight();
    
    int lf = (int) Math.sqrt(filter);
    
    for(int y=0; y < h; y++) 
    {
      for(int x=0; x < w; x++) 
      {
        
        // Crea nuovi array
        newArray(filter);
        
        // Prende i colori dei pixel, li ordina, e considera
        // come rappresentativo il mediano
        int k = 0;
        for(int i=0; i<lf; i++) 
        {
          for(int j=0; j<lf; j++) 
          {
            int partial_x = (x - lf / 2 + j + w) % w;
            int partial_y = (y - lf / 2 + i + h) % h;
            
            red[k]   = rawImage.getRed(partial_x, partial_y);
            green[k] = rawImage.getGreen(partial_x, partial_y);
            blue[k]  = rawImage.getBlue(partial_x, partial_y);
            k++;
          }
        }
        
        Arrays.sort(red);
        Arrays.sort(green);
        Arrays.sort(blue);
        
        rawImage.setPixel(x,y,median(red), median(green), median(blue));
      }
    }
    
    return rawImage;
  }
  // ----------------------------------------------------------------------
  
  // Il valore mediano e' al centro se l'array è dispari, ed è in posizione
  // array.length/2 - 1 in caso l'array è pari
  // ---------------------------------------------------------------------
  private int median(int[] array) 
  {
    if(array.length % 2 == 0) 
    {
      return (array[array.length/2] + array[array.length/2-1]) / 2;
    }
    return array[array.length/2];
  }
  // ----------------------------------------------------------------------
  
  // ----------------------------------------------------------------------
  private void newArray(int n) 
  {
    red   = null;
    green = null;
    blue  = null;
  
    red   = new int[n];
    green = new int[n];
    blue  = new int[n];
  }
  // -----------------------------------------------------------------------
  
  public void setKernel(int[][] kernel) {}
}