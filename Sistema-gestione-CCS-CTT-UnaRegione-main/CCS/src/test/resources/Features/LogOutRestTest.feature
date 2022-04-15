Feature: Log out REST

  Scenario: Test per verificare la corretta rimozione del token in seguito ad una richiesta di logout da parte dell'amministratoreCCS
    Given L'admin si autentica tramite form
    Then Viene rimosso il token generato dal il login precedente

  Scenario: Test per verificare che il Logout non vada a buon fine in presenza di un Token errato
    Then Non viene rimosso il token poichè è errato