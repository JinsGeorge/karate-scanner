Feature: Sample feature with lint issues

  Scenario: Duplicate steps
    Given url 'http://example.com'
    When method get
    Then status 200
    Then status 200

