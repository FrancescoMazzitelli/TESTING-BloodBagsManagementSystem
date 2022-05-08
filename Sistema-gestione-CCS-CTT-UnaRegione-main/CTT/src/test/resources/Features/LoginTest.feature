Feature: Login

    @Cucumber @Selenium
    Scenario: Test per verificare la presenza di un DipendenteCTT creato nel setUp e aggiunto nel database dei Dipendenti
      Given Viene compilato il form per l'accesso di un dipendente corretto
      Then Viene sottomesso il form ed effettuato l'accesso

    @Cucumber @Selenium
    Scenario: Test per verificare l'assenza di un DipendenteCTT all'interno del database tramite username e password passate nel form
      Given Viene compilato un form errato per l'accesso di un dipendente
      Then Viene sottomesso il form e non effettuato l'accesso