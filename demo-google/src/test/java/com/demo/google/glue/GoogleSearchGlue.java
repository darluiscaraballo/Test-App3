package com.demo.google.glue;

import com.demo.google.logic.Google;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GoogleSearchGlue {

    @Given("Google is displayed")
    public void googleIsDisplayed() {
        Google.googleSearch().displayPage();
    }

    @When("User input {string} Automation list")
    public void userInputAutomationList(String search) {
        Google.googleSearch().inputGoogleSearch(search);
    }

    @And("User extract the result is a txt file")
    public void userExtractTheResultIsATxtFile() {
        Google.googleSearch().extractResultAssTXT();
    }

    @Then("Text file generated contains 10 results and not from {string} guru99.com page")
    public void verifyResultsGenerated(String Text ) {
        Google.googleSearch().verifyResultGenerated(Text);
    }
}