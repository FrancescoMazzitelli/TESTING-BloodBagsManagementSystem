Feature: Aggiunta sacca in magazzino

    Scenario: Test della funzionalità aggiunta sacca, va a buon fine
      Given Viene compilato il form per l'accesso al portale del magazziniere
      When Viene compilato il form per l'aggiunta di una nuova sacca
      Then Viene sottomesso il form e creata una nuova sacca

    Scenario: Test della funzionalità aggiunta sacca, non va a buon fine poichè la nuova sacca è scaduta
      When Viene compilato il form errato per l'aggiunta di una nuova sacca
      Then Viene sottomesso il form e non creata una nuova sacca