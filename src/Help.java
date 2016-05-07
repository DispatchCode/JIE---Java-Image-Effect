/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: La seguente classe offre una descrizione di un particolare filtro
 * ---------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * ---------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */


class Help {
  private Help() {}
  
  static String getHelp(String filterName) {
    switch(filterName) {
      case "Bilinear":
      return "<html>Applica il bayer demosaicing (demosaicizzazione). Solitamente una fotocamera salva immagini in formato RAW, formato che non è direttamente"+
             "utilizzabile da un<br>software di immagini.<br><br>Questo algoritmo utilizzando un CFA (Color Filter Array) ricostruisce l'immagine, mostrandola così a colori.<br>"+
             "I pixel vengono considerati come RGGB, o meglio RGR, GBG, RGR ad indicare rispettamente prima, seconda e terza riga della matrice."+
             "Il software utilizza la seguente matrice:<br><br>"+
             "<span style='color:red'>R11</span>  <span style='color:green'>G12</span>  <span style='color:red'>R13</span><br>"+
             "<span style='color:green'>G21</span>  <span style='color:blue'>B22</span>  <span style='color:green'>G23</span><br>"+
             "<span style='color:red'>R31</span>  <span style='color:green'>G22</span>  <span style='color:red'>R33</span><br><br></html>";

      
      case "Median":
      return "<html>Il filtro Median permette in motli casi di ridurre il 'noise' dalle immagini. L'effetto che si puo' ottenere non e' sempre identico.</html>";
      default:
      return "";
    }
  }
}