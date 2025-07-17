Feature: Sample feature with lint issues

  Background:
    Given url 'http://example.com'

  Scenario: Duplicate steps
    Given url 'http://example.com'
    When method get
    Then status 200
    When method get
    Then status 200
