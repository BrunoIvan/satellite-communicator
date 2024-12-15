Feature: Location feature

  Scenario: Caso feliz
    When Pruebo con un caso feliz
    Then El resultado debería ser: x: 99.80 y: -215.50

  Scenario: Caso feliz con redondeo
    When Pruebo con un caso feliz con redondeo de 3
    Then El resultado debería ser: x: 99.8 y: -215.5

  Scenario: Caso feliz con posiciones predeterminadas
    When Pruebo con un caso feliz con posiciones predeterminadas
    Then El resultado debería ser: x: 99.80 y: -215.50

  Scenario: Caso no feliz (no intersectan los dos primeros satelites)
    When Pruebo con un caso de no intersección en los dos primeros satelites
    Then El resultado debería ser una excepción de tipo no-intersection

  Scenario: Caso no feliz (no intersecta el tercer satelite)
    When Pruebo con un caso de no intersección en el tercer satelite
    Then El resultado debería ser una excepción de tipo no-intersection

  Scenario: Caso no feliz (un círculo dentro de otro)
    When Pruebo con un caso donde un círculo está dentro de otro
    Then El resultado debería ser una excepción de tipo circle-within

  Scenario: Caso no feliz (primer círculo igual al segundo)
    When Pruebo con un caso donde primer círculo es igual al segundo
    Then El resultado debería ser una excepción de tipo equal-circle

  Scenario: Caso no feliz (primer círculo igual al tercero)
    When Pruebo con un caso donde primer círculo es igual al tercero
    Then El resultado debería ser una excepción de tipo equal-circle