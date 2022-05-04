Feature: Aggiungi amministratore REST test

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/aggiuntaAmministratore dell'amministratoreCCS, va a buon fine
    #Given BeforeAll
    Given L'utente si autentica sull'apposito portale tramite form
    When Viene compilato il form per l'aggiunta di un nuovo amministratore
    Then Viene sottomesso il form e creato un nuovo amministratore

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/aggiuntaAmministratore dell'amministratoreCCS, non va a buon fine, siccome i dati dell'AmministratoreCCS da aggiungere sono in un formato errato
    Given Viene compilato il form sbagliato per l'aggiunta di un nuovo amministratore
    Then Viene sottomesso il form e NON viene creato un nuovo amministratore