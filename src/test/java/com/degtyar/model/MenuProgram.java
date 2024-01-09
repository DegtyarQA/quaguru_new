package com.degtyar.model;
public class MenuProgram {
    public Menu menu;

    public static class Menu {
        public String id;
        public String value;
        public  Popup popup;
    }

    public static class Popup {
        public Menuitem [] menuitem;
    }

    public static class Menuitem {
        public String value;
        public String onclick;

    }
}
