# AppIO - Dispatcher Unificato

## Introduzione
Lo scopo del progetto è l'invio di messaggi AppIO ai privati cittadini con il fine di migliorare e semplificare la comunicazione con le istituzioni pubbliche. Tale invio viene effettuato con l'ausilio di specifici servizi di backend realizzati da PagoPA. Il campo di applicazione è molteplice, visto che titolo e testo sono arbitrari e quindi adattabili ad ogni esigenza informativa, compresa l'opzione di pagamento di un bollettino PagoPA già emesso e di cui si conosce il codice IUV e l'importo. Il software permette sia di inviare messaggi in maniera sincrona, invocando API dedicate, sia in maniera asincrona riempiendo una coda di invio su DB.

## Tecnologie
Il sistema è web-based e sfrutta su varie tecnologie:
1. Java + Spring Boot + Apache Tomcat per le componenti di caricamento della coda e di scheduling degli invii
2. WSO2 Enterprise Integrator per la componente che invia i messaggi appoggiandosi al backend di PagoPA
3. Database Oracle per le tabelle di configurazione, di coda degli invii asincroni e degli esiti degli invii

## Struttura del repository
Il repository contiene le seguenti cartelle:
* Docs: cartella di documenti di alto livello che contiene in particolare il diagramma con la struttura dell'intera applicazione, l'elenco dei requisiti software dettagliati di versioni richieste al corretto funzionamento e il manuale d'uso che spiega come interfacciarsi con le varie API esposte
* ClientDispatcher: componente WSO2 Enterprise Integrator 6.4.0 per l'invio dei messaggi tramite API pubbliche che invocano i servizi di PagoPA
* LoaderMessageQueue: component Java+Spring Boot per il caricamento della coda di invio
* SenderScheduler: componente Java+Spring Boot per schedulare gli invii
* DB: file .sql per creare le tabelle sul DB Oracle 12
* Properties: file di property per la parte Java
* Swagger: file .yaml che dettagliano le varie risorse presenti nelle API pubbliche

## Il Database
La corrente implementazione del progetto prevede l'uso di un DB Oracle 12, che può però essere sostituito da qualsiasi altro DB SQL, previo allineamento del codice Java e WSO2.
L'elenco delle tabelle usate è il seguente:
* IO_CONFIGURAZIONE
* IO_CODA
* IO_ESITI
* IO_INTERVALLO_INVIO

## Contatti

* Amministrazione proprietaria: Comune di Genova
* Soggetto incaricato al mantenimento: Liguria Digitale spa
* Contatto e-mail referente di progetto (per segnalazioni e richiesta informazioni): r.delorenzi@liguriadigitale.it
* Contatto e-mail presso l'amministrazione: mbabbei@comune.genova.it
* Gestione segnalazioni: **tramite issues github**

