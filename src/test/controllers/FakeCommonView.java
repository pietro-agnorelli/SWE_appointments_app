package test.controllers;

import views.CommonView;

public class FakeCommonView extends CommonView{
	String lastMessage;
	
    @Override
    public void displayMessage(String message) {
        lastMessage = message;
    }
    @Override
    public void clearScreen() {}
}
