Feature: Evasione sacca

    @Cucumber @Selenium
    Scenario: Test per il metodo rest/magazziniere/evasione del magazziniereCTT, che va a buon fine
      Given Viene compilato il form per l'autenticazione del magazziniere ed effettuato l'accesso
      When Viene create una notifica di evasione sacche
      Then La notifica veiene accettata e le sacche vengono evase

    @Cucumber @Selenium
    Scenario: Test per il metodo rest/magazziniere/evasione del magazziniereCTT, non funzionante siccome si cerca di evadere una Sacca non presente nel database delle Sacche
      When Viene createa un altra notifica di evasione sacche con dei seriali non presenti nel DB
      Then La notifica viene accettata e la sacche non vengono evase