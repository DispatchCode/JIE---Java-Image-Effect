# JIE - Java Image Effect #

**JIE** è stato rilasciato il *19/07/2014* con alcuni filtri applicabili ad immagini ed una grafica totalmente differente dall'attuale. A distanza di un anno, nel 2015, riscrissi la GUI e la resi molto più comoda da utilizzare. Pochi giorni fa l'ultimo aggiornamento: i pannelli che mostrano l'immagine caricata e l'anteprima dell'elaborazione si adattano alla risoluzione dello schermo.
**I filtri non sono built-in ma li ho scritti io; non hanno pretese di ottimabilità temporale.**

Fix della nuova versione:
* risolto il problema con le risoluzioni riguardanti i JPanel contenitori;
* il pulsante "Applica" ora appare selezionando la sola immagine di input;

Un futuro fix sarà anche relativo ai pixel sui bordi dell'immagine, dato che al momento non li considero (e su immagini grandi si nota il contorno non elaborato).

## Cos'è JIE? ##
Ho parlato più sopra di *filtri*. Questi filtri, praticamente, sono delle matrici che applicate ai pixel dell'immagine ne alterano i pixel stessi generando quelli che sono l'effetto blur, lo smooth, l'inversione di pixel etc.

Più formalmente un filtro come Blur è composto da una matrice denominata kernel; in base alla sua composizione ed alla sua dimensione, il filtro applicato è più o meno forte.

Va da sè, che più l'immagine è grande e più la matrice kernel è grande, e maggiore sarà il tempo di elaborazione.

## Filtri Disponibili ##
Al momento i filtri disponibili sono i seguenti:

* Bilinear;
* Grayscale;
* Inversion;
* Contrast;
* Blur (3x3, 5x5, 7x7, 9x9, 15x15)
* Motion Blur Lef (3x3, 5x5, 7x7, 9x9)
* Motion Blur Right (3x3, 5x5, 7x7, 9x9)
* Sharpen (3x3, 5x5)
* Median (3x3, 5x5, 7x7, 9x9)
* Salt And Pepper Noise (5%, 25%, 50%, 75%, 95%)

Ne verranno aggiunti altri in future release, e verranno molto probabilmente aggiunti altri kernels ad alcuni filtri.
Per descrizioni esaustive sui filtri e dettagli sul funzionamento consultare il Wiki.

***

## Esempi ##

### Blur e Sharpen ###

*Originale*

![flower.png](https://bitbucket.org/repo/jadeeM/images/4203564186-flower.png)

*Blur con kernel 5x5*

![flower_modificata.png](https://bitbucket.org/repo/jadeeM/images/884386616-flower_modificata.png)


*con Sharpen 5x5*

![flower_modificata.png](https://bitbucket.org/repo/jadeeM/images/3473419834-flower_modificata.png)


### Bilinear ###

*originale*

![bayer4.png](https://bitbucket.org/repo/jadeeM/images/2084993153-bayer4.png)

*bilinear filter*

![bayer4_modificata.png](https://bitbucket.org/repo/jadeeM/images/287008665-bayer4_modificata.png)