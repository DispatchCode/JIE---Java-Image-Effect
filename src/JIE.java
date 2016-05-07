/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Classe Main, crea Model, View e Controller
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


class JIE 
{
  public static void main(String[] args) 
  {
    ManageFilter mf = new ManageFilter();
    FilterGUI fg = new FilterGUI();
    
    FilterController fc = new FilterController(mf,fg);
  }
}