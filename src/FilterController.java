/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Controller; è l'intermediario tra la GUI ed il codice interno
 *            : che manipola le singole immagini
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */



import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;


class FilterController implements ActionListener 
{
  // ---------------------------------------------------------------------------------------------
  private FilterGUI filterGui;
  private ManageFilter manageFilter;
  // ---------------------------------------------------------------------------------------------

  // ---------------------------------------------------------------------------------------------
  FilterController(ManageFilter manageFilter, FilterGUI filterGui) 
  {
    this.manageFilter = manageFilter;
    this.filterGui    = filterGui;
    
    // Crea il frame
    filterGui.init();
    // Popola i pannelli del frame
    filterGui.creaPannelli(this);
    // Popola il combobox
    filterGui.setFilters(manageFilter.getFilters());
  }
  // ---------------------------------------------------------------------------------------------
  
  // Implementato da ActionListener, gestisce la pressione dei bottoni
  // ----------------------------------------------------------------------------------------------
  public void actionPerformed(ActionEvent ae) 
  {
    String cmd = null;
    if(ae.getSource() instanceof JButton) 
    {
      JButton btn = (JButton) ae.getSource();
      cmd = btn.getActionCommand();
    }
    else 
    {
      cmd = "filter";
    }
    
    File f = null;
    String filterName = "";
    switch(cmd) 
    {
      case "InputFile":
        f = filterGui.selectFile();
        if(f == null) return;
        manageFilter.setInputFile(f);
        break;
      case "OutputFile":
        f = filterGui.saveFile();
        if(f == null) return;
        manageFilter.setOutputFile(f);
        break;
      case "filter":
        filterName = filterGui.getSelectedFilter();
        filterGui.setOption(manageFilter.loadOption(filterName));
        break;
      case "personalizza":
        filterName = filterGui.getSelectedFilter();
        filterGui.personalizzaKernel(filterName,this);
        break;
      case "Applica":
        String selectedOption = filterGui.getSelectedOption();
        filterGui.showPreviewImage(manageFilter.run(filterGui.getSelectedFilter(),selectedOption).getBufferedImage());
        break;
      case "Save":
        if(manageFilter.save(filterGui.openImageChecked())) filterGui.showSuccessMessage();
        break;
      case "?":
        filterName = filterGui.getSelectedFilter();
        filterGui.showHelpMessage(Help.getHelp(filterName));
        break;
    }
  }
  // -----------------------------------------------------------------------------------------------
}