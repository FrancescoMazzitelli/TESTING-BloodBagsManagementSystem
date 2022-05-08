Feature: Aggiunta sacca in magazzino

  @Cucumber @Selenium
  Scenario: Test della funzionalità aggiunta sacca, non va a buon fine poichè la nuova sacca è scaduta
    Given Viene compilato il form per l'accesso al portale del magazziniere
    When Viene compilato il form errato per l'aggiunta di una nuova sacca
    Then Viene sottomesso il form e non creata una nuova sacca


  @Cucumber @Selenium
  Scenario: Test della funzionalità aggiunta sacca, va a buon fine
    When Viene compilato il form per l'aggiunta di una nuova sacca
    Then Viene sottomesso il form e creata una nuova sacca

