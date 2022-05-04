Feature: Report CCS REST

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/reportDipendentiCCS dell'amministratoreCCS, va a buon fine*/
    Given Viene effettuata l'autenticazione dell'amministratore
    Then Viene richiesto il report dipendenti

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/giacenzaMediaSaccheCCS dell'amministratoreCCS, va a buon fine
    Then Viene richiesto il report giagenza media

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/reportSaccheInviateCCS dell'amministratoreCCS, va a buon fine
    Then Viene richiesto il report sacche inviate

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/reportSaccheRicevuteCCS dell'amministratoreCCS, va a buon fine
    Then Viene richiesto il report sacche ricevute

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/reportStatisticoSaccheCCS dell'amministratoreCCS, va a buon fine
    Then Viene richiesto il report statistico sacche