package fr.umlv.java.inside;

class Foo {

    private static final Logger LOGGER = Logger.of(Foo.class, System.err::println);

    public void hello() {
        LOGGER.log("hello");  // print hello on System.err
    }

}