package dev.limhm.functionalInterfaceAndLambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("순수 함수란")
class PureFunctionClassTest {

  @Test
  @DisplayName("입력 값이 동일한 경우, 결과 값이 같아야 한다.")
  void pureFunctionTest() {

    PureFunctionClass pureFunction = new PureFunctionClass() {
      @Override
      public int doIt(int number) {
        return number + 10;
      }
    };

    System.out.println(pureFunction.doIt(1));
    System.out.println(pureFunction.doIt(1));
    System.out.println(pureFunction.doIt(1));
  }

  @Test
  @DisplayName("결과 값이 달라지는 원인")
  void functionTest() {

    PureFunctionClass function = new PureFunctionClass() {
      final int baseNumber = 10;

      @Override
      public int doIt(int number) {
        // baseNumber 변수에 의존하게 되므로 순수 함수가 아님
        return number + baseNumber;
      }
    };

    System.out.println(function.doIt(1));
    System.out.println(function.doIt(1));
    System.out.println(function.doIt(1));
  }
}