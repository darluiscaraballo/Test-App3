package com.demo.google.logic;

public class Google {

    public static GoogleSearchLogic googleSearch(){
        return new GoogleSearchLogic();
    }
    public void staticWait(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
