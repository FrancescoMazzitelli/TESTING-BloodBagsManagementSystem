Feature: Lista CTT REST

  Scenario: Test per il metodo rest/CCS/centers dell'amministratoreCCS, va a buon fine
    Given L'amministratore si autenitica sul portale
    When Viene compilato il form per l'aggiunta di un nuovo CTT per aumentare il numero in lista
    Then Viene sottomesso il form e restituita la dimensione di una lista di CTT

  Scenario: Test per il metodo rest/CCS/centers dell'amministratoreCCS, non va a buon fine siccome il parametro telefono è in un formato errato
    When Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato per auementare il numero in lista
    Then Viene sottomesso il form e non restituita la dimensione di una lista di CTT