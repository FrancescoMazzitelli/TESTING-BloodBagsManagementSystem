Feature: Rimozione CTT REST

  @Cucumber @Selenium
  Scenario: Test del metodo REST rest/CCS/rimozioneCTT, va a buon fine in quanto si tenta di eliminare un CTT inserito nel db
    Given L'amministratore accede al portale
    Then Vengono eliminati due CTT

  @Cucumber @Selenium
  Scenario: Test del metodo REST rest/CCS/rimozioneCTT, non deve andare a buon fine in quanto si tenta di eliminare un CTT non presente nel database
    Then Non viene eliminato il CTT target
