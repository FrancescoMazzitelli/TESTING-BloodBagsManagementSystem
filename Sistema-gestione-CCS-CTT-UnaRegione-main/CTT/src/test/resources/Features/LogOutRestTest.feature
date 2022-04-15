Feature: Logout

  Scenario: Test per verificare la corretta rimozione del token in seguito ad una richiesta di logout da parte dell'amministratoreCTT
    Given L'utente si autentica sul portale
    Then L'utente si disconnette

  Scenario: Test per verificare che il Logout non vada a buon fine in presenza di un Token errato
    Then Il logout non va a buon fine