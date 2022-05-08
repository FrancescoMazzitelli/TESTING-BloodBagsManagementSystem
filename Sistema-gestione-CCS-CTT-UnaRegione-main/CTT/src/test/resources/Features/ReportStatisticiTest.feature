Feature: Report statistici CTT

  @Cucumber @Selenium
  Scenario: Report statistico magazzinieri
    Given L'amministratore accede al portale dei report
    Then Vengono filtrati i magazzinieri

  @Cucumber @Selenium
  Scenario: Report statistico operatori
    Then Vengono filtrati gli operatori

  @Cucumber @Selenium
  Scenario: Report statistico amministratori
    Then Vengono filtrati gli amministratori

  @Cucumber @Selenium
  Scenario: Report statistico giacenza media
    Then Viene restituita la giacenza media delle sacche

  @Cucumber @Selenium
  Scenario: Report statistico sacche inviate
    Then Viene restituito il report sacche inviate
    Then Non viene restituito il report sacche inviate a causa di un formato errato

  @Cucumber @Selenium
  Scenario: Report statistico sacche ricevute
    Then Viene restituito il report sacche ricevute
    Then Non viene restituito il report sacche ricevute a causa di un formato errato

  @Cucumber @Selenium
  Scenario: Report statistico sacche
    Then Viene restituito il report statistico sacche