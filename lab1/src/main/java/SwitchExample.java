public class SwitchExample {

    public static int switchExample(String s) {
        int result;
        switch (s) {
            case "dog":
                result = 1;
                break;
            case "cat":
                result = 2;
                break;
            default:
                result = 4;
                break;
        }
        return result;
    }

}