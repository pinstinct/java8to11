package dev.limhm.functionalInterfaceAndLambda;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class LambdaVariableCapture {

  public void run() {
    /*
    * final 변수인 경우 로컬 클래스, 익명 클래스, 람다 모두에서 접근 가능
    * (final 키워드는 뒤에서 더 이상 변경할 수 없음)
    * */
    final int baseNumber = 10;

    // 1. 로컬 클래스
    class LocalClass {
      void printBaseNumber() {
        int baseNumber = 11;
        System.out.println(baseNumber); // 11
      }
    }
    LocalClass localClass = new LocalClass();
    localClass.printBaseNumber();

    // 2. 익명 클래스
    Consumer<Integer> integerConsumer = new Consumer<Integer>() {
      @Override
      public void accept(Integer baseNumber) {
        System.out.println(baseNumber);
      }
    };
    integerConsumer.accept(11);

    // 3. 람다
    IntConsumer consumer = (i) -> {
      // int baseNumber = 11;  // 컴파일 에러: 바깥의 baseNumber 와 같은 scope 이므로, 이렇게 사용할 수 없음
      System.out.println(i + baseNumber);
    };
    consumer.accept(10);
  }
}
