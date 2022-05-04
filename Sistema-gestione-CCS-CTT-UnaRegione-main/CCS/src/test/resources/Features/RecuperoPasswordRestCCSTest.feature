Feature: Recupero password REST CCS

  @Cucumber @Selenium
  Scenario: Test per verificare il corretto recupero della password ottenendone una nuova
    Given Viene immesso l'username
    Then Viene sottomesso l'username e recuperata la password

  @Cucumber @Selenium
  Scenario: Test per verificare che il recupero password non vada a buon fine se tentato su un utente non presente
    Given Viene immesso l'username di un utente presente il cui codice fiscale è errato
    Then Viene sottomessso l'username e non recuperata la password

  @Cucumber @Selenium
  Scenario: Test per verificare che il recupero password non vada a buon fine se tentato su un altro utente
    Given Viene immesso l'username di un utente presente nel db ma viene associato al CF di un altro utente
    Then Viene sottomessso l'username e non recuperata la password perchè il CF non corrisponde