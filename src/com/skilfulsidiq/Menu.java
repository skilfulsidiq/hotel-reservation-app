package com.skilfulsidiq;

public abstract class Menu {
    
    public abstract void showMenus();

    public static  boolean yesIsSelected(String input){
        if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")){
            return true;
        }
        return false;
    }
}
