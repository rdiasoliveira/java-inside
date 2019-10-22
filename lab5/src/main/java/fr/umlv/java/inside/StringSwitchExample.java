package fr.umlv.java.inside;

public class StringSwitchExample {

    public static int stringSwitch(String str) {
        switch (str) {
            case ("foo"): return 0;
            case ("bar"): return 1;
            case ("bazz"): return 2;
            default: return -1;
        }
    }
}
