/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Pannello di disegno. Qui le immagini vengono disposte l'una
 *            : accanto all'altra e ridimensionate prima di essere mostrate;
 *            : l'immagine di output avrà dimensione (w/h) identici all'originale
 * ---------------------------------------------------------------------------
 * TODO       : In future versioni verra' probabilmente gestito tutto diversamente
 *            : utilizzando due pannelli, e gestendo il disegno in maniera differente.
 *            : cosi' da implementare una funzionalita' citata tempo fa...
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 07/08/2015
 * ---------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.UIManager.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



class JPanelImages extends JPanel 
{
  // -------------------------------------------------------
  private int max_width, max_height;
  private Image originalImage, filteredImage;
  // -------------------------------------------------------
  
  
  // --------------------------------------------------------
  JPanelImages(int max_width, int max_height) 
  {
    super(new BorderLayout());
    
    this.max_width  = max_width;
    this.max_height = max_height;
    
    setOpaque(true);
    setBackground(Color.WHITE);
  }
  // --------------------------------------------------------
  
  // Setta l'immagine selezionata e ridisegna il pannello
  // --------------------------------------------------------
  public void setInputImage(BufferedImage bi) 
  {
    originalImage = (Image) bi;
    
    repaint();
  }
  // --------------------------------------------------------
  
  // Identico al precedente, ma riceve un File
  public void setInputImage(File file) 
  {
    try 
    {
      originalImage = (Image) ImageIO.read(file);
    } catch(IOException ioex) {}
    
    repaint();
  }
  // ---------------------------------------------------------
  
  // Immagine di output (filtro applicato)
  // ---------------------------------------------------------
  public void setOutputImage(BufferedImage bi) 
  {
    filteredImage = (Image) bi;
    
    repaint();
  }
  // ---------------------------------------------------------
  

  // Disegno le immagini sul pannello
  // ---------------------------------------------------------
  @Override
  public void paintComponent(Graphics g) 
  {
    super.paintComponent(g);
 
    Graphics2D g2 = (Graphics2D) g;
    
    // Se l'immagine non e' ancora stata selezionata disegno
    // un rettangolo grigio
    if(originalImage != null) 
    {
      int w = originalImage.getWidth(null);
      int h = originalImage.getHeight(null);
      
      // Se l'immagine e' troppo grande ottengo una versione riscalata
      if(w > max_width || h > max_height) 
      {
        w = max_width;
        h = max_height;
        originalImage = originalImage.getScaledInstance(w,h,Image.SCALE_REPLICATE);
      }
      
      g2.drawImage(originalImage, 250,10,w, h,null);
      
    } 
    else 
    {
      g2.setColor(Color.LIGHT_GRAY);
      g2.fill(new Rectangle2D.Float(250,10,max_width,max_height));
      
    }
    
    // Come sopra... se l'immagine di output non e' stato selezionato
    // disegno un rettangolo grigio
    if(filteredImage != null) 
    {
      int w = filteredImage.getWidth(null);
      int h = filteredImage.getHeight(null);
      
      // Se troppo grande, ottengo la versione riscalata
      if(w > max_width || h > max_height) 
      {
        w = max_width;
        h = max_height;
        filteredImage = filteredImage.getScaledInstance(w,h,Image.SCALE_REPLICATE);
      }
      
      g2.drawImage(filteredImage,w+270,10,w,h,null);
    } 
    else 
    {
      g2.setColor(Color.LIGHT_GRAY);
      g2.fill(new Rectangle2D.Float(max_width+270,10,max_width,max_height));
    }
    
    
  }
  // ---------------------------------------------------------------


}