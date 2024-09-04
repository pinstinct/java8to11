package dev.limhm.interfaceChanges;

public interface Foo {

  void printName();

  // 공통으로 제공하는 기능을 추가하고 싶을 때, default method 사용
  // 리스크가 있기 때문에 문서화를 해두고, 필요하다면 구현체에서 재정의 가능
  /**
   * @implSpec 이 구현체는 getName()으로 가져온 문자열을 대문자로 바꿔 출력한다.
   */
  default void printNameUpperCase() {
    System.out.println(getName().toUpperCase());
  }

  String getName();

  static void printAnything() {
    System.out.println("Foo");
  }
}
