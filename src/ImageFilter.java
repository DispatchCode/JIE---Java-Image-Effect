/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * Descrizione: Interfaccia implementata dalle classi che applicano un filtro
 * --------------------------------------------------------------------------
 * Licenza    : GNU/GPL V.3 (Leggere file 'Licens.txt')
 * --------------------------------------------------------------------------
 * Data       : 18/7/2014
 * --------------------------------------------------------------------------
 * Autore     : Marco 'RootkitNeo' C.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 */

interface ImageFilter 
{
  RawRGBImage filter(RawRGBImage rgbImage);
  void setKernel(int[][] kernel);
}