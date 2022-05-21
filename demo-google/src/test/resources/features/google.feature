Feature: Google Search

  @GoogleSearchList
  Scenario Outline: Extract result of search list
    Given Google is displayed
    When User input "<search>" Automation list
    And User extract the result is a txt file
    Then Text file generated contains 10 results and not from "<Text>" guru99.com page

    Examples:
      | search          | Text       |
      | Automation list | guru99.com |