/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Rappresenta un immagine ed offre metodi getter/setter per
 *            : accedere ai pixel.
 * --------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 *---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class RawRGBImage 
{
  //
  // ------------------------------------------------------------------------------------
  private final int width;
  private final int height;
  private final int[] pixels;   // pixels dell'immagine
  // ------------------------------------------------------------------------------------
  
  //
  // ------------------------------------------------------------------------------------
  RawRGBImage(int width, int height) 
  {
    this.width  = width;
    this.height = height;
    pixels      = new int[width * height];
  }
  
  RawRGBImage(BufferedImage bi) 
  {
    width  = bi.getWidth();
    height = bi.getHeight();
    pixels = bi.getRGB(0,0,width, height, null,0, width);
  }
  
  RawRGBImage(String fileName) 
  {
    BufferedImage bi = null;
    try {
      bi = ImageIO.read(new File(fileName));
    } catch(IOException ioex) {}
    
    width  = bi.getWidth();
    height = bi.getHeight();
    pixels = bi.getRGB(0,0,width, height, null, 0, width);

  }
  
  RawRGBImage(File file) 
  {
    BufferedImage bi = null;
    try {
      bi = ImageIO.read(file);
    } catch(IOException ioex) {}
    
    width  = bi.getWidth();
    height = bi.getHeight();
    pixels = bi.getRGB(0,0,width, height, null, 0, width);
  }
  // --------------------------------------------------------------------------------------
  
  int getWidth() 
  {
    return width;
  }
  
  int getHeight() 
  {
    return height;
  }
  
  // Verifica che le coordinate passate siano 
  // entro i due limiti (inf. sup.) dell'array
  boolean pixelExist(int x, int y) 
  {
    return x>=0 && x<width && y>=0 && y<height;
  }
  
  int getPixel(int x, int y) 
  {
    return pixels[y*width + x];
  }
  
  int getRed(int x, int y) 
  {
    return (pixels[y*width+x] >> 16) & 0xFF;
  }
  
  int getAlpha(int x, int y) 
  {
    return (pixels[y*width+x] >> 24) & 0xFF;
  }
  
  int getGreen(int x, int y) 
  {
    return (pixels[y*width+x] >> 8) & 0xFF;
  }
  
  int getBlue(int x, int y) 
  {
    return pixels[y*width+x] & 0xFF;
  }
  
  void setPixel(int x, int y, int pixel) 
  {
    pixels[y*width+x] = pixel;
  }
  
  void setPixel(int x, int y, int r, int g, int b) {
    setPixel(x,y,0xFF000000, r, g, b);
  }
  
  void setPixel(int x, int y,int a, int r, int g, int b) 
  {
    pixels[y*width+x] = (a | r << 16 | g << 8 | b);
  }
  
  BufferedImage getBufferedImage() 
  {
    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    bi.setRGB(0,0,width, height, pixels, 0, width);
    return bi;
  }
}