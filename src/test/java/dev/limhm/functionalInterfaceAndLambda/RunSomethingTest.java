package dev.limhm.functionalInterfaceAndLambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("함수형 인터페이스 구현하는 방법")
class RunSomethingTest {

  @Test
  @DisplayName("익명 내부 클래스")
  void anonymousFunctionTest() {
    RunSomething anonymous = new RunSomething() {
      @Override
      public void doIt() {
        System.out.println("Hello");
      }
    };
    anonymous.doIt();
  }

  @Test
  @DisplayName("람다 표현식")
  void lambdaTest() {
    RunSomething lambda = () -> System.out.println("hello");
    lambda.doIt();
  }
}