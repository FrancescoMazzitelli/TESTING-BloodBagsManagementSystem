Feature: Aggiunta dipendente

    @Cucumber @Selenium
    Scenario: Test per il metodo rest/amministratore/aggiuntaDipendente del magazziniere, che va a buon fine
      #Given BeforeAll
      Given L'utente effettua l'accesso sull'apposito portale
      When Viene compilato il form per l'aggiunta di un magazziniere
      Then Viene sottomesso il form e creato un nuovo magazziniere

    @Cucumber @Selenium
    Scenario: Test per il metodo rest/amministratore/aggiuntaDipendente dell'operatore, che va a buon fine
      When Viene compilato il form per l'aggiunta di un nuovo operatore
      Then Viene sottomesso il form e creato un novo operatore

    @Cucumber @Selenium
    Scenario: Test per il metodo rest/amministratore/aggiuntaDipendente dell'amministratoreCTT, non funzionante a causa del formato errato della data di nascita
      When Viene compilato il form errato per l'aggiunta di un nuovo amministratore
      Then Viene sottomesso il form e non creato il nuovo amministratore