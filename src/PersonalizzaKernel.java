/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Finestra aggiunta kernel
 * --------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * --------------------------------------------------------------------------
 * Data       : 07/08/2015
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;

import java.util.ArrayList;

import java.io.*;

class PersonalizzaKernel extends JDialog implements ActionListener,WindowListener 
{
  //
  // ------------------------------------------------------------------------
  private String filterName;                 // Nome del filtro selezionato
  private JButton btnCreateMatrix, salva;    // Pulsante creazione matrice e salvataggio
  private JPanel matrixPanel;           
  private JTextField dimension, optionName;  // Dimensione matrice e nome opzione
  private ArrayList<JTextField> cellsMatrix; // Elenco delle celle
  private JFrame frame;                      // Frame padre
  // ------------------------------------------------------------------------
  
  
  //
  // ------------------------------------------------------------------------
  PersonalizzaKernel(String filterName,JFrame frame) 
  {
    super(frame, "Personalizza Kernel (Filter: "+filterName+")");
    this.frame      = frame;
    this.filterName = filterName;
    
    cellsMatrix = new ArrayList<JTextField>();
    
    // Contenitore generale
    JPanel container = new JPanel();
    // Numero righe/colonne
    dimension = new JTextField(5);
    // Genera la matrice NxN
    btnCreateMatrix = new JButton("Genera Matrice");
    btnCreateMatrix.setActionCommand("createMatrix");
    btnCreateMatrix.addActionListener(this);
    
    optionName = new JTextField(10);
    
    ImageIcon saveImg = null;
    try 
    {
      saveImg = new ImageIcon(ImageIO.read(new File("Save-icon.png")));
    } catch(IOException e) {}
    
    salva = new JButton("Salva");
    if(saveImg != null) salva.setIcon(saveImg);
    salva.setActionCommand("nuovoKernel");
    salva.addActionListener(this);
    
    container.add(dimension);
    container.add(btnCreateMatrix);
    container.add(optionName);
    container.add(salva);
    
    add(container);
    addWindowListener(this);
    
    setResizable(false);
    
    pack();
    
  }
  // ------------------------------------------------------------------------
  
  // Risponde agli eventi interni al dialog
  // ------------------------------------------------------------------------
  public void actionPerformed(ActionEvent ae) 
  {
    boolean state = false;
    
    JButton btn = (JButton) ae.getSource();
    
    if(btn == btnCreateMatrix) 
    {
    
      if(matrixPanel != null) 
      {
        
        for(JTextField jtf : cellsMatrix) 
        {
          matrixPanel.remove(jtf);
        }
        cellsMatrix.clear();
        remove(matrixPanel);
        matrixPanel = null;
        pack();
      }
    
      if(dimension.getText().equals("")) return;
    
      int n = Integer.parseInt(dimension.getText());
      matrixPanel = new JPanel(new GridLayout(n,n));
    
      for(int i=0; i<n*n; i++) 
      {
        JTextField jtf = new JTextField(4);
        matrixPanel.add(jtf);

        cellsMatrix.add(jtf);
      }
    
      JPanel general = new JPanel(new BorderLayout());
      general.add(matrixPanel);
    
      add(general, BorderLayout.SOUTH);
      pack();
    
    } 
    else 
    {
      int n = Integer.parseInt(dimension.getText());
      
      int[][] kernel = new int[n][n];
      int k = 0;
      for(int i=0; i<kernel.length; i++) 
      {
        for(int j=0; j<kernel[0].length; j++) 
        {
          kernel[i][j] = Integer.parseInt(cellsMatrix.get(k).getText());
          k++; 
        }
      }
      
      // Serializzazione
      try 
      {
        Kernel kernelObj = new Kernel(filterName, optionName.getText(), kernel);
        File f = new File("../filters/"+filterName+"/"+optionName.getText()+".txt");
        f.setWritable(true);
        FileOutputStream fos = new FileOutputStream(f);        
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(kernelObj);
        oos.flush();
        oos.close();
        
        state = true;
      } catch(IOException ex) {state = false;}
      
      showMessageState(state);
      
    }
  }
  // ------------------------------------------------------------------------
  
  
  private void showMessageState(boolean state) 
  {
    if(state) JOptionPane.showMessageDialog(this,"Salvataggio Andato a Buon Fine!\nPer visualizzare il nuovo filtro, chiudi e riapri il software!", "Operazione Riuscita!", JOptionPane.INFORMATION_MESSAGE);
    else JOptionPane.showMessageDialog(this,"Si sono verificati problemi durante il salvataggio!","Operazione Non Riuscita!",JOptionPane.ERROR_MESSAGE);
  }
  

  // Eventi sul frame (chiusura)
  public void windowClosing(WindowEvent we) 
  {
    System.gc();
    dispose();
  }
  
  public void windowActivated(WindowEvent we) {}
  public void windowClosed(WindowEvent we) {}
  public void windowDeactivated(WindowEvent we) {}
  public void windowDeiconified(WindowEvent we) {}
  public void windowIconified(WindowEvent we) {}
  public void windowOpened(WindowEvent we) {}
  
}