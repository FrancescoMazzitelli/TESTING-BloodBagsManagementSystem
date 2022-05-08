Feature: Rimozione dipendenti CTT

  @Cucumber @Selenium
  Scenario: Test del metodo REST rest/amministratore/rimozioneDipendente
    Given L'amministratore si autentica sul portale
    Then Vengono eliminati due utenti

  @Cucumber @Selenium
  Scenario: Questo test non deve andare a buon fine in quanto si tenta di eliminare un Dipendente non presente nel database
    Then Non vengono eliminati gli utenti

  @Cucumber @Selenium
  Scenario: Questo test non deve andare a buon fine in quanto l'AmministratoreCTT tenta di eliminare se stesso dal database dei Dipendenti
    Then Non viene eliminato l'utente poichè non è possibile eliminare se stessi