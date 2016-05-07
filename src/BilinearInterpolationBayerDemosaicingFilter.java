/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Mappa l'immagine utilizzando l'array di bayer ed effettua il
 *            : bayer demosaicing. Lo scopo: da un immagine RAW ottenere
 *            : un'immagine colorata, applicando un array del tipo:
 *            :
 *            :     R11 G12
 *            :     G21 B22
 *            :
 *            : L'algoritmo tuttavia utilizza una matrice di 3x3, in modo da
 *            : avere un immagine piu' precisa. Il pixel considerato e' quello
 *            : centrale, e l'immagine e' mappata in questo modo:
 *            :
 *            :     R11  G12  R13
 *            :     G21 [B22] G23
 *            :     R31  G32  R33
 *            :
 *            : Ad ogni iterazione la configurazione cambia; nel secondo caso diventa:
 *            :
 *            :     G11  R12  G13
 *            :     B21 [G22] B23
 *            :     G31  R32  G33
 *            : 
 *            : In tutti il colore del pixel centrale è stimato dalla media dei pixel
 *            : attorno al pixel centrale.
 *            :
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ----------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


class BilinearInterpolationBayerDemosaicingFilter implements ImageFilter
{
  /*
   * Il fastDemosaicing permette di calcolare la media dei pixel senza considerare 
   * il pixel verde centrale (quando G è al centro). E' più performante rispetto
   * all'altra versione, e la differenza è minima.
   * Per questo motivo di default è abilitato
   */


  private boolean fastDemosaicing;
  
  //
  // -------------------------------------------------------------------------------------------
  BilinearInterpolationBayerDemosaicingFilter() 
  {
    fastDemosaicing = true;
  }
  
  BilinearInterpolationBayerDemosaicingFilter(boolean fastDemosaicing) 
  {
    this.fastDemosaicing = fastDemosaicing;
  }
  // -------------------------------------------------------------------------------------------
  
  
  public RawRGBImage filter(RawRGBImage rawImage) 
  {
    int r=0, g=0, b=0;
    int w = rawImage.getWidth();
    int h = rawImage.getHeight();
    
    --w; --h;
    
    for(int x=0; x<w-1; x++) 
    {
      for(int y=0; y<h-1; y++) 
      {
        // Verifico se il pixel esiste; se non esiste setto i colori a 0
        if(!rawImage.pixelExist(x,y)) 
        {
          r = 0;
          g = 0;
          b = 0;
        }
        
        // Pari && Pari
        if((x%2==0) && (y%2==0)) 
        {
          // R G R
          // G B G
          // R G R
          r = (rawImage.getRed(x,y)     + rawImage.getRed(x,y+2)   + rawImage.getRed(x+2, y)     + rawImage.getRed(x+2,y+2))   >> 2;
          g = (rawImage.getGreen(x,y+1) + rawImage.getGreen(x+1,y) + rawImage.getGreen(x+1,y+2) + rawImage.getGreen(x+2,y+1)) >> 2;
          b =  rawImage.getBlue(x+1,y+1);
        }
        // Dispari && Dispari
        else if((x%2!=0) && (y%2!=0)) 
        {
          // B G B
          // G R G
          // B G B
          r = rawImage.getRed(x+1,y+1);
          g = (rawImage.getGreen(x+1,y) + rawImage.getGreen(x,y+1) + rawImage.getGreen(x+1,y+2) + rawImage.getGreen(x+2,y+1)) >> 2;
          b = (rawImage.getBlue(x,y)    + rawImage.getBlue(x,y+2)  + rawImage.getBlue(x+2,y)    + rawImage.getBlue(x+2,y+2))  >> 2;
        }
        // Pari && Dispari
        else if((x%2==0) && (y%2!=0)) 
        {
          // G R G
          // B G B
          // G R G
          r = (rawImage.getRed(x,y+1) + rawImage.getRed(x+2,y+1)) >> 1;
          if(!fastDemosaicing) 
            g = (rawImage.getGreen(x,y) + rawImage.getGreen(x+1,y+1) + rawImage.getGreen(x,y+2) + rawImage.getGreen(x+2,y) + rawImage.getGreen(x+2,y+2)) / 5;
          else 
            g = (rawImage.getGreen(x,y) + rawImage.getGreen(x,y+2) + rawImage.getGreen(x+2,y) + rawImage.getGreen(x+2,y+2)) >> 2;
          b = (rawImage.getBlue(x+1,y) + rawImage.getBlue(x+1,y+2)) >> 1;
        }
        // Dispari && Pari
        else if((x%2!=0) && (y%2==0))
        {
          // G B G
          // R G R
          // G B G
          r = (rawImage.getRed(x+1,y) + rawImage.getRed(x+1,y+2)) >> 1;
          if(!fastDemosaicing) 
            g = (rawImage.getGreen(x,y) + rawImage.getGreen(x+1,y+1) + rawImage.getGreen(x,y+2) + rawImage.getGreen(x+2,y) + rawImage.getGreen(x+2,y+2)) / 5;
          else 
            g = (rawImage.getGreen(x,y) + rawImage.getGreen(x,y+2) + rawImage.getGreen(x+2,y) + rawImage.getGreen(x+2,y+2)) >> 2;
          b = (rawImage.getBlue(x,y+1) + rawImage.getBlue(x+2,y+1)) >> 1;
        }
        
        rawImage.setPixel(x+1,y+1,r,g,b);
      }
    }
    
    return rawImage;
  }
  
  public void setKernel(int[][] kernel) {}
}