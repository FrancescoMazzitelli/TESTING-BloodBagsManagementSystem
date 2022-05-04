Feature: Cambio Password Rest CCS

  @Cucumber @Selenium
  Scenario: Test per verificare la corretta modifica della password inserendone una nuova
    Given L'utente si autentica sull'apposito portale tramite form admin1
    When Viene inserita la nuova password
    Then Viene sottomessa e cambiata la password

  @Cucumber @Selenium
  Scenario: Test per verificare che il Cambio password non vada a buon fine se tentato con un token errato
    #Given L'utente si autentica sull'apposito portale tramite form
    #When Viene inserita la nuova password
    Then Viene sottomessa e non cambiata la password a causa del token errato

  @Cucumber @Selenium
  Scenario: Test per verificare che il Cambio password non vada a buon fine se non rispetta il formato della password
    #Given L'utente si autentica sull'apposito portale tramite form
    When Viene inserita la nuova password errata
    Then Viene sottomessa e non cambiata la password a causa del suo formato

  @Cucumber @Selenium
  Scenario: Test per verificare che il Cambio password non vada a buon fine se tentato su un altro utente
    #Given L'utente si autentica sull'apposito portale tramite form
    #When Viene inserita la nuova password
    Then Viene sottomessa e non cambiata la password perchè il CF inserito non è lo stesso dell'utente autenticato

