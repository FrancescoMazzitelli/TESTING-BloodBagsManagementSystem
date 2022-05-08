Feature: Cambio password REST CTT

    @Cucumber @Selenium
    Scenario: Test per verificare la corretta modifica della password inserendone una nuova
      Given Un amministratore si autentica sul portale
      When viene inserita una nuova password
      Then Viene sottomessa la nuova password

    @Cucumber @Selenium
    Scenario: Test per verificare che il Cambio password non vada a buon fine se tentato con un token errato
      Then Non viene sottomessa la nuova password a causa di un token errato

    @Cucumber @Selenium
    Scenario: Test per verificare che il Cambio password non vada a buon fine se non rispetto il formato della password
      When viene inserita una password errata
      Then viene sottomessa la nuova password ma non cambiata a causa del formato

    @Cucumber @Selenium
    Scenario: Test per verificare che il Cambio password non vada a buon fine se tentato su un altro utente
      Then Non viene sottomessa la nuova password poichè è specificato un codice fiscale diverso da quello dell'utente autenticato