Feature: Rimuovi amministratore CCS REST

  @Cucumber @Selenium
  Scenario: Test del metodo REST rest/CCS/rimozioneAmministratore, deve andare a buon fine in quanto si tenta di eliminare un Dipendente inserito nel db
    Given L'amministratore3 si autentica sul portale
    Then Viene rimosso il dipendente selezionato

  @Cucumber @Selenium
  Scenario: Test del metodo REST rest/CCS/rimozioneAmministratore, non deve andare a buon fine in quanto si tenta di eliminare un Dipendente non presente nel database
    Then Non viene rimosso il dipendente selezionato poichè non presente nel db

  @Cucumber @Selenium
  Scenario: Test del metodo REST rest/CCS/rimozioneAmministratore, non deve andare a buon fine in quanto l'AmministratoreCCS tenta di eliminare se stesso mentre è loggato
    Then Non viene rimosso il dipendente selezionato perchè non è possibile rimuovere se stessi