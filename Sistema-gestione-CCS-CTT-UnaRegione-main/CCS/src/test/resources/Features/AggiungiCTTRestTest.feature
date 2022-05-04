Feature: Aggiungi CTT REST

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/aggiuntaCTT dell'amministratoreCCS, va a buon fine
    Given L'amministratore accede al portale tramite form
    When Viene compilato il form per l'aggiunta di un nuovo CTT
    Then Viene sottomesso il form e creato un nuovo CTT

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/aggiuntaCTT dell'amministratoreCCS, non va a buon fine siccome mancano i parametri numero_ctt e nome_ctt
    When Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, numero e nome
    Then Viene sottomesso il form e non viene creato un nuovo CTT a causa del numero e nome CTT

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/aggiuntaCTT dell'amministratoreCCS, non va a buon fine siccome il parametro telefono è in un formato errato
    When Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, telefono
    Then Viene sottomesso il form e non viene creato un nuovo CTT a causa del telefono

  @Cucumber @Selenium
  Scenario: Test per il metodo rest/CCS/aggiuntaCTT dell'amministratoreCCS, non va a buon fine siccome la letitudine inserita non esiste e non è valida
    When Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, latitudine
    Then Viene sottomesso il form e non viene creato un nuovo CTT a causa della latitudine


