package dev.limhm.functionalInterfaceAndLambda;

public class Greeting {

  private String name;

  public Greeting() {
  }

  public Greeting(String name) {
    this.name = name;
  }

  public static String hi(String name) {
    return "hi " + name;
  }

  public String getName() {
    return name;
  }

  public String hello(String name) {
    return "hello " + name;
  }
}
