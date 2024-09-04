package dev.limhm.interfaceChanges;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("인터페이스 default method & static method")
class FooTest {

  @Test
  @DisplayName("기본 메소드(default method) 사용")
  void case1() {
    Foo foo = new DefaultFoo("kim");
    foo.printName();
    foo.printNameUpperCase();
  }

  @Test
  @DisplayName("스태틱 메소드(static method) 사용")
  void case2() {
    Foo.printAnything();
  }
}