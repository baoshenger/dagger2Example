package com.evia.dagger2sampleapplication.clicker.userinputfragment;

/**
 * Created by Evgenii Iashin on 17.07.18.
 */
public class ClickUserInput {

    private String clicksInput;

    public ClickUserInput(String clicksInput) {
        this.clicksInput = clicksInput;
    }

    public String getClicksInput() {
        return clicksInput;
    }

    public void setClicksInput(String input) {
        clicksInput = input;
    }
}
