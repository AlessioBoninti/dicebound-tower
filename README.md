# Dicebound Tower

## Breve descrizione

Dicebound Tower è un applicativo JavaFX sviluppato per il progetto d'esame di Metodologie di Programmazione / Modellazione e Gestione della Conoscenza.

Il progetto implementa un gioco di ruolo fantasy a turni. Il giocatore crea un personaggio scegliendo una classe, affronta una torre composta da più piani, combatte nemici, ottiene esperienza e oro, usa oggetti dell'inventario e può salvare o caricare la partita tramite persistenza JSON.

## Funzionalità principali

- Creazione di un nuovo personaggio con scelta della classe.
- Progressione all'interno di una torre composta da più piani.
- Combattimento a turni contro nemici e boss.
- Gestione di esperienza, livello, punti vita e oro.
- Inventario con oggetti utilizzabili, come pozioni di cura.
- Salvataggio, caricamento ed eliminazione delle partite salvate.
- Interfaccia grafica realizzata con JavaFX.

## Requisiti

- Java 21.
- Gradle Wrapper incluso nel progetto.
- JavaFX configurato tramite Gradle.

## Come eseguire il progetto

Da terminale, nella root del repository:

```bash
./gradlew build
./gradlew run
```

Su Windows:

```bash
gradlew.bat build
gradlew.bat run
```

## Persistenza dei dati

Il progetto utilizza una persistenza su file JSON. Le partite vengono salvate nella cartella `saves`, utilizzando un identificativo associato allo stato della partita.

La persistenza permette di salvare e ricaricare lo stato principale del gioco, inclusi personaggio, classe, statistiche, livello, esperienza, oro, punti vita, inventario e piano corrente della torre.

## Regolamento, prima release e uso dell'AI

Sono stati utilizzati strumenti di AI come supporto durante lo sviluppo del progetto.

L'AI è stata usata per chiarimenti teorici, revisione architetturale, suggerimenti di refactoring e supporto nella documentazione. È stata inoltre usata come supporto nella progettazione iniziale del regolamento di gioco, in particolare per ragionare su: combattimento a turni, bilanciamento delle classi, uso delle statistiche, progressione nella torre, ricompense e persistenza dello stato della partita.

Il regolamento proposto inizialmente è stato poi semplificato, adattato e implementato manualmente in base agli obiettivi della prima release del progetto. Non tutte le idee emerse nella fase di progettazione sono presenti nella versione attuale: alcune sono state mantenute come possibili estensioni future.

Il codice è stato compreso, verificato, adattato e testato manualmente dallo studente. L'AI non è stata usata come sostituto dello sviluppo personale, ma come strumento di supporto per analisi, progettazione e revisione.

Per una descrizione più dettagliata dell'uso di strumenti di AI e delle scelte progettuali, consultare la Wiki del repository.

## Nota sulla Wiki

La Wiki del repository contiene la documentazione di progetto con una descrizione più dettagliata delle funzionalità implementate, delle responsabilità delle classi, dell'organizzazione dei dati, della persistenza e dei meccanismi previsti per integrare nuove funzionalità.

La Wiki distingue inoltre tra funzionalità effettivamente implementate nella prima release e idee progettuali pensate come possibili sviluppi futuri, come nuove torri, nuovi eventi, nuovi oggetti, equipaggiamenti, abilità e un regolamento di combattimento più articolato.