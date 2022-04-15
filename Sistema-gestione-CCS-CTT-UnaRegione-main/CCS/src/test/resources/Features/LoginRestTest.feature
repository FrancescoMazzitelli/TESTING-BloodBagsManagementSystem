Feature: Login REST

  Scenario: Test per verificare la presenza di un amministratoreCCS all'interno del database tramite username e password passate nel form
    Given Viene compilato il form per il login
    Then Viene sottomesso il form e garantito l'accesso

  Scenario: Test per verificare la non presenza di un amministratoreCCS all'interno del database tramite username e password passate nel form
    Given Viene compilato il form per il login di un admin non presente
    Then Viene sottomesso il form e non garantito l'accesso

