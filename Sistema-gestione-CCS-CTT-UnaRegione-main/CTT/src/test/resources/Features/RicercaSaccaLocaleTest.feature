Feature: Ricerca sacca locale

  @Cucumber @Selenium
  Scenario: Test per il metodo /rest/operatore/ricerca dell'operatoreCTT, va a buon fine
    Given L'amministratore si logga sul portale
    When Viene compilato il form per la ricerca
    Then Viene sottomesso il form e ricercata la sacca

  @Cucumber @Selenium
  Scenario: Test per il metodo /rest/operatore/ricerca dell'operatoreCTT, non va a buon fine, siccome non esiste nessuna Sacca ZERO- nel database delle Sacche
    When Viene compilato il form errato per la ricerca
    Then Viene sottomesso il form e non ricercata la sacca