/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Offre all'utente una GUI per manipolare le immagini
 * --------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * --------------------------------------------------------------------------
 * Data       : 18/7/2014
 * Modifica   : 07/08/2015
 * --------------------------------------------------------------------------
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
import java.beans.PropertyVetoException;


class FilterGUI 
{
  // Variabili di istanza
  // --------------------------------------------------------------------------
  private JFrame frame;                                  // Finestra
  private JPanel optionArea;                             // Pannello componenti
  private JPanelImages pannelloImmagini;                 // Disegna le immagini
  private JTextField inputPath, outputPath;              // I/O paths
  private JButton scegli1, scegli2, applica, help, save,personalizza; // Bottone file dialogs 1 e 2; applicazione effetto, salvataggio.
  private JComboBox<String> filterBox, filterOptionBox;  // Combo dei filtri; combo delle opzioni dei filtri (kernels)
  private JFileChooser fileChooser;
  private JCheckBox openNewImage;                        // Apertura immagine al termine
  // ---------------------------------------------------------------------------
  
  // Costruttore
  // ----------------------------------------------------------------------------
  FilterGUI() 
  {
    try {
      // Setta Nimbus come L&F
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
      {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
      }
    } catch (Exception e) {}
    
    fileChooser = new JFileChooser();
    fileChooser.addChoosableFileFilter(new PNGSaveFilter());
  }
  // -----------------------------------------------------------------------------
  
  // Crea i componenti grafici e li posiziona nel frame
  // -----------------------------------------------------------------------------
  void creaPannelli(ActionListener al) 
  { 
    
    // Contenitore immagini
    pannelloImmagini = new JPanelImages(frame.getWidth()/3,frame.getHeight()-250);
    
    // Contenitore pulsanti opzioni
    JPanel component = new JPanel();
    component.setOpaque(true);
    component.setBackground(Color.WHITE);
    JPanel componentPanel = new JPanel(new GridBagLayout());
    componentPanel.setOpaque(true);
    componentPanel.setBackground(Color.WHITE);
    
    scegli1 = new JButton("Percorso Input");
    scegli1.setActionCommand("InputFile");
    scegli1.addActionListener(al);
    
    inputPath = new JTextField(40);
    
    scegli2 = new JButton("Percorso Output");
    scegli2.setActionCommand("OutputFile");
    scegli2.addActionListener(al);
    
    outputPath = new JTextField(40);

    filterBox = new JComboBox<String>();
    filterBox.setActionCommand("filter");
    filterBox.addActionListener(al);
    
    filterOptionBox = new JComboBox<String>();
    filterOptionBox.setVisible(false);
    
    personalizza = new JButton("Aggiungi Kernel");
    personalizza.setActionCommand("personalizza");
    personalizza.addActionListener(al);
    personalizza.setVisible(false);
    
    applica = new JButton("Applica");
    applica.setActionCommand("Applica");
    applica.addActionListener(al);
    applica.setVisible(false);
    
    ImageIcon saveImg = null;
    try {
      saveImg = new ImageIcon(ImageIO.read(new File("Save-icon.png")));
    } catch(IOException e) {}
    
    save = new JButton("Salva");
    if(saveImg != null) save.setIcon(saveImg);
    save.addActionListener(al);
    save.setActionCommand("Save");
    save.setVisible(false);
    
    openNewImage = new JCheckBox("Aprire l'immagine Salvata?",true);
    
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.insets = new Insets(2,2,2,2);
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    componentPanel.add(scegli1,gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 0;
    componentPanel.add(inputPath,gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    componentPanel.add(scegli2, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 1;
    componentPanel.add(outputPath,gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 2;
    componentPanel.add(filterBox, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 2;
    componentPanel.add(filterOptionBox, gbc);
    
    gbc.gridx = 2;
    gbc.gridy = 2;
    componentPanel.add(personalizza,gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 3;
    componentPanel.add(applica, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 3;
    componentPanel.add(save, gbc);
    
    gbc.gridx = 2;
    gbc.gridy = 3;
    componentPanel.add(openNewImage,gbc);
    
    
    component.add(componentPanel);
    
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,pannelloImmagini, component);
    splitPane.setDividerLocation(1500);
    
    frame.getContentPane().add(splitPane);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);

    
  }
  // -----------------------------------------------------------------------------
  
  // Setta i filtri nel combobox
  // -----------------------------------------------------------------------------
  void setFilters(String[] filters) 
  {
    filterBox.removeAllItems(); 
    
    for(String s : filters) 
    {
      filterBox.addItem(s);
    }
  }
  // -----------------------------------------------------------------------------
  
  // Apre la dialog per la selezione del file di input
  // -----------------------------------------------------------------------------
  File selectFile() 
  {
    int returnValue = fileChooser.showOpenDialog(frame);
    File file = null;

    if(returnValue == JFileChooser.APPROVE_OPTION) 
    {
      file = fileChooser.getSelectedFile();
      inputPath.setText(file.getPath());
      
      pannelloImmagini.setInputImage(file);
      
      applica.setVisible(true);
    }
    return file;
  }
  // -----------------------------------------------------------------------------
  
  // Setta il file di salvataggio
  // -----------------------------------------------------------------------------
  File saveFile() 
  {
    int returnValue = fileChooser.showSaveDialog(frame);
    File file = null;
    
    if(returnValue == JFileChooser.APPROVE_OPTION) 
    {
      file = fileChooser.getSelectedFile();
      outputPath.setText(file.getPath());
      
      save.setVisible(true);
    }
    
    return file;
  }
  // -----------------------------------------------------------------------------
  
  // Ottiene il filtro selezionato nel menu a tendina
  // -----------------------------------------------------------------------------
  String getSelectedFilter() 
  {
    applica.setEnabled(true); // Mostro il pulsante "Applica"
    
    String f = (String) filterBox.getSelectedItem();
    return f;
  }
  // -----------------------------------------------------------------------------
  
  // Ottiene le opzioni del filtro
  // -----------------------------------------------------------------------------
  String getSelectedOption() 
  {
    if(filterOptionBox.getSelectedIndex() < 0) return null;
    return (String) filterOptionBox.getSelectedItem();
  }
  // -----------------------------------------------------------------------------
  
  // Se la checkbox e' stata selezionata, apre l'immagine salvata
  // -----------------------------------------------------------------------------
  boolean openImageChecked() 
  {
    return openNewImage.isSelected();
  }
  // -----------------------------------------------------------------------------
  
  // Setta le nuove opzioni
  // -----------------------------------------------------------------------------
  void setOption(String[] option) 
  {
    String filter = getSelectedFilter();
    
    filterOptionBox.removeAllItems();
    // Se il filtro non ha opzioni, nascondo il pulsante
    // di creazione nuovo kernel
    if(option == null) 
    {
      personalizza.setVisible(false);
      filterOptionBox.setVisible(false);
      return;
    }
    
    
    for(String f : option) 
    {
      filterOptionBox.addItem(f);
    }
    
    // Nascondo anche il pulsante in presenza dei seguenti filtri; al momento non possono essere personalizzati
    if(filter.equals("Salt And Pepper Noise") || filter.equals("Median")) personalizza.setVisible(false);
    else personalizza.setVisible(true);
    
    filterOptionBox.setVisible(true);
  }
  // -----------------------------------------------------------------------------
  
  // Apre la dialog che consente di aggiungere un nuovo kernel
  // // --------------------------------------------------------------------------
  void personalizzaKernel(String filterName, ActionListener al) 
  {
    PersonalizzaKernel pk = new PersonalizzaKernel(filterName, frame);
    pk.setLocationRelativeTo(null);
    pk.setVisible(true);
  }
  // -----------------------------------------------------------------------------
  
  // Mostra l'immagine all'Applcazione del risultato
  // -----------------------------------------------------------------------------
  void showPreviewImage(BufferedImage bi) 
  {
    pannelloImmagini.setOutputImage(bi);
  }
  // -----------------------------------------------------------------------------
  

  void showHelpMessage(String message) 
  {
    JOptionPane.showMessageDialog(frame,message, "Spiegazione!", JOptionPane.PLAIN_MESSAGE);
  }

  void showSuccessMessage() {
    JOptionPane.showMessageDialog(frame, "Operazione eseguita con successo!", "Operazione riuscita!", JOptionPane.INFORMATION_MESSAGE);
  }
  
  void showErrorMessage() {
    JOptionPane.showMessageDialog(frame, "E' necessario selezionare il file di input e salvare il file di output!","Errore!", JOptionPane.ERROR_MESSAGE);
  }
  
  
  public void makeGUI() 
  {
    frame = new JFrame("Java Image Effects - JIE | Developed by Marco 'RootkitNeo' C. | V. 1.0.1");
     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setBounds(0,0,screenSize.width, screenSize.height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public void init() 
  {
    try {
      SwingUtilities.invokeAndWait(new Runnable() 
      {
        public void run() 
        {
          makeGUI();
        }
      });
    } catch(Exception e) {}
  }
}