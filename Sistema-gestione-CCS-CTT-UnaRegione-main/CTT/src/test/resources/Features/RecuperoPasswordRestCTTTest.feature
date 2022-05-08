Feature: Recupero password REST CTT

  @Cucumber @Selenium
  Scenario: Test per verificare il corretto recupero della password ottenendone una nuova
    Given L'admin si autentica sul portale
    When Viene inserita una nuova password
    Then Viene sottomesso il form e ottenuta una nuova password

  @Cucumber @Selenium
  Scenario: Test per verificare che il recupero password non vada a buon fine se tentato su un utente non presente
    Then Non viene sottomesso il form

  @Cucumber @Selenium
  Scenario: Test per verificare che il recupero password non vada a buon fine se tentato su un altro utente
    Then Non viene sottomesso il form per altro utente