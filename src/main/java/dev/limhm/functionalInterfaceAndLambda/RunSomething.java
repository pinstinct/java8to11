package dev.limhm.functionalInterfaceAndLambda;

@FunctionalInterface
public interface RunSomething {

  // 추상 메서드가 딱 1개 있으면, 함수형 인터페이스
  // 인터페이스에서는 abstract 생략 가능
  abstract void doIt();

  static void printName() {
    System.out.println("Kim");
  }

  default void printAge() {
    System.out.println("10");
  }
}
