package dev.limhm.functionalInterfaceAndLambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("자바에서 제공하는 함수형 인터페이스")
class Plus10Test {

  @Test
  @DisplayName("구현체")
  void implementFunctionTest() {
    Plus10 plus10 = new Plus10();
    System.out.println(plus10.apply(1));
  }

  @Test
  @DisplayName("람다로 구현")
  void lambdaTest() {
    Function<Integer, Integer> plus10 = (number) -> number + 10;
    System.out.println(plus10.apply(1));
  }

  @Test
  @DisplayName("함수 조합하기 - Function compose")
  void composeTest() {
    Function<Integer, Integer> plus10 = integer -> integer + 10;
    Function<Integer, Integer> multiply2 = integer -> integer * 2;

    // multiply2 실행한 후, plus10 실행
    Function<Integer, Integer> multiply2AndPlus10 = plus10.compose(multiply2);
    System.out.println(multiply2AndPlus10.apply(2));
  }

  @Test
  @DisplayName("함수 조합하기 - Function andThen")
  void andThenTest() {
    Function<Integer, Integer> plus10 = integer -> integer + 10;
    Function<Integer, Integer> multiply2 = integer -> integer * 2;
    System.out.println(plus10.andThen(multiply2).apply(2));
  }

  @Test
  @DisplayName("T 타입의 값을 받고, 리턴이 없는 함수 인터페이스 - Consumer")
  void acceptTest() {
    Consumer<Integer> printT = integer -> System.out.println(integer);
    printT.accept(10);
  }

  @Test
  @DisplayName("T 타입의 값을 가져오는 함수 인터페이스 - Supplier")
  void getTest() {
    Supplier<Integer> supplier = () -> 10;
    System.out.println(supplier.get());
  }

  @Test
  @DisplayName("T 타입을 받아서 boolean 리턴하는 함수 인터페이스 - Predicate")
  void testTest() {
    Predicate<String> startWithKim = s -> s.startsWith("kim");
    Predicate<Integer> isEven = integer -> integer % 2 == 0;

    System.out.println(startWithKim.test("abc"));
    System.out.println(isEven.test(2));
  }
}
