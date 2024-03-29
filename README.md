# Jetop recruitment
Programma realizzato in occasione del recruitment per Jetop Sept/Oct 2021

## Task1

Il task richiedeva di analizzare degli oggetti in JSON, ovvero un comune formato per la rappresentazione di oggetti in programmazione, eliminandone alcune properietà ('edges' e 'node').

In particolare, i file di input erano 4 diversi oggetti JSON, ciascuno caratterizzato da una diversa struttura non nota a priori. Pertanto, l'unico modo efficace per la rappresentazione e l'analisi di una struttura dati del genere è in maniera ricorsiva.

Sfruttando il paradigma ad oggetti del linguaggio Java, ho realizzato una classe astratta che rappresenta un generico data type ammesso in JSON e che funge da interfaccia per tutte i specifici tipi di dato ammessi dal formato; dunque ho realizzato una classe specifica per ciascuno di questi tipi (oggetto, array, stringa, boolean, numero o 'null') e che estende la suddetta classe astratta.

In questo modo posso far riferimento ad un generico oggetto JSON, specificando delle strutture dati generiche che possano contenere qualsiasi tipo di dato.

Ho realizzato anche un oggetto JSONparser, contenente i metodi ricorsivi in grado di analizzare il file in input e costruire gradualmente l'oggetto JSON corrispondente. 

La classe Program contiene il main() e avvia dunque l'esecuzione del programma: legge i file in input contenuti in un'opportuna tabella, ne analizza il contenuto e memorizza gli oggetti JSONcomponent corrispondenti, per mezzo dei metodi della classe JSONparser; una volta costruiti gli oggetti, un opportuno metodo ricorsivo removeProperties() viene chiamato su ognuno degli oggetti per eliminarne le proprietà indesiderate ('edges','node' e in questo caso anche 'data', visto che dai file di output è stata rimossa anch'essa). Infine, i nuovi oggetti 'ripuliti' così ottenuti vengono stampati in dei file di testo opportuni.

Ciascun generico oggetto JSON rappresenta di fatto una struttura ricorsiva, contenente un insieme di oggetti generici (nel caso di array), un insieme di chiavi e valori generici (nel caso di oggetti) o un semplice valore (nel caso di stringa, numero, boolean e 'null'). Per cui, i metodi che agiscono sugli oggetti JSON vengono richiamati ricorsivamente sui loro elementi 'figli': è il caso del metodo 'removeProperties()' e 'print()'. Il primo rimuove le proprietà da ogni elemento figlio (nel caso di numero, stringa, 'null' e boolean questo metodo non fa nulla), mentre il secondo stampa l'output del generico oggetto richiamando se stesso sugli elementi figli.

Per costruire ricorsivamente la struttura di un oggetto, viene analizzato riga per riga il file in input; se il primo carattere è una graffa '{' seguirà un altro oggetto (array associativo), se invece è una quadra '[' seguirà un array, se sono virgolette (") si tratta di una stringa, e così via. Nel caso fosse un oggetto, le righe successive conterranno coppie chiave-valore delimitate dai ':', in cui la chiave è una stringa e il valore è un oggetto qualsiasi, a cui si ri-applica lo stesso algoritmo. Nel caso di un array, invece, quest'ultimo contiene un elenco di generici oggetti, separati da virgola, a cui si ri-applica lo stesso algoritmo per identificarne la tipologia.

Particolare attenzione va prestata nel caso di righe vuote (come nel caso del primo file di input), che devono essere scartate. Si presuppone inoltre che il file sia corretto.

Per eliminare le proprietà indesiderate, invece, si analizza la struttura dati da cima a fondo, applicando la stessa funzione ricorsivamente e restituendo infine un riferimento al nuovo oggetto privato delle proprietà indesiderate. Nel caso di un nodo 'oggetto JSON', tale metodo andrà a richiamare se stesso su tutte le sue proprietà; poi, se questo contiene una sola proprietà, e questa è una di quelle indesiderate, allora il metodo restituirà il valore corrispondente a tale proprietà, anziché restituire un riferimento a se stesso. In questo modo, il nodo 'padre' riceverà non più un riferimento allo stesso oggetto, che conteneva una proprietà indesiderata, bensì un riferimento all'oggetto figlio 'puntato' dalla proprietà indesiderata.

Nell'effettuare queste sostituzioni bisogna fare attenzione a ciclare sulle dovute proprietà, che nel frattempo vengono modificate dal codice stesso.

La stampa degli oggetti rispetta anche l'indentazione corretta, come nei file di input, per mezzo di un parametro intero che viene passato di padre in figlio durante la stampa del risultato.

I file di output sono contenuti nella directory 'outputFiles_task1'
