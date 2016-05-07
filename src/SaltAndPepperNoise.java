/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione  : Aggiunge un'errore all'immagine inserendo pixel bianchi e neri.
 *              : La codifica prende il nome di Salt And Pepper Noise
 * ---------------------------------------------------------------------------
 * Licenza      : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data         : 07/08/2015
 * --------------------------------------------------------------------------
 * Autore       : Guglielmoqwerty (adattato da Marco 'RootkitNeo' C.)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */



import java.util.Random;

class SaltAndPepperNoise implements ImageFilter
{
  // Variabili di istanza - 
  // ----------------------------------------------------------------------
  // Opzioni del filtro
  private final int CINQUE         = 5;
  private final int VENTICINQUE    = 25;
  private final int CINQUANTA      = 50;
  private final int SETTANTACINQUE = 75;
  private final int NOVANTACINQUE  = 95;
  
  // Opzione selezionata
  private int p;
  // -----------------------------------------------------------------------
  
  // Costruttore
  // -----------------------------------------------------------------------
  SaltAndPepperNoise(int p)
  {
    switch(p) 
    {
      case 1:
        this.p = CINQUE;
        break;
      case 2:
        this.p = VENTICINQUE;
        break;
      case 3:
        this.p = CINQUANTA;
        break;
      case 4:
        this.p = SETTANTACINQUE;
        break;
      case 5:
        this.p = NOVANTACINQUE;
        break;
      default:
        this.p = CINQUANTA;
    }
  }
  // ------------------------------------------------------------------------
  
       
  public RawRGBImage filter(RawRGBImage rgbImage)
  {
    Random r = new Random();
    for(int y=0; y<rgbImage.getHeight(); y++)
    {
      for(int x=0; x<rgbImage.getWidth(); x++)
      {
        if(r.nextInt(100)<p)
        {
          if(r.nextBoolean()) {
            // Bianco
            rgbImage.setPixel(x, y, 255, 255, 255);
          } else {
            // Nero
            rgbImage.setPixel(x, y, 0, 0, 0);
          }
        }
      }
    }
    return rgbImage;
  }
 
 
  public void setKernel(int[][] kernel) {}
}