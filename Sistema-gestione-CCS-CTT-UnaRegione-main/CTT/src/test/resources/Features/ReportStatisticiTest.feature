Feature: Report statistici CTT

  Scenario: Report statistico magazzinieri
    Given L'amministratore accede al portale dei report
    Then Vengono filtrati i magazzinieri

  Scenario: Report statistico operatori
    Then Vengono filtrati gli operatori

  Scenario: Report statistico amministratori
    Then Vengono filtrati gli amministratori

  Scenario: Report statistico giacenza media
    Then Viene restituita la giacenza media delle sacche

  Scenario: Report statistico sacche inviate
    Then Viene restituito il report sacche inviate
    Then Non viene restituito il report sacche inviate a causa di un formato errato

  Scenario: Report statistico sacche ricevute
    Then Viene restituito il report sacche ricevute
    Then Non viene restituito il report sacche ricevute a causa di un formato errato

  Scenario: Report statistico sacche
    Then Viene restituito il report statistico sacche