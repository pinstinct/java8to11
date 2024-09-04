package dev.limhm.functionalInterfaceAndLambda;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("메소드 레퍼런스")
class GreetingTest {

  @Test
  @DisplayName("스태틱 메소드 참조")
  void case1() {
    UnaryOperator<String> operator = Greeting::hi;
    System.out.println(operator.apply("kim"));
  }

  @Test
  @DisplayName("특정 객체의 인스턴스 메소드 참조")
  void case2() {
    Greeting greeting = new Greeting();
    UnaryOperator<String> operator = greeting::hello;
    System.out.println(operator.apply("kim"));
  }

  @Test
  @DisplayName("생성자 참조 - 입력이 없는 경우")
  void case3() {
    Supplier<Greeting> supplier = Greeting::new;
    System.out.println(supplier.get().getName());
  }

  @Test
  @DisplayName("생성자 참조- 입력이 있는 경우")
  void case4() {
    Function<String, Greeting> function = Greeting::new;
    Greeting greeting = function.apply("kim");
    System.out.println(greeting.getName());
  }

  @Test
  @DisplayName("임의 객체의 인스턴스 메소드 참조")
  void case5() {
    String[] names = {"kim", "lee", "lim"};
    Arrays.sort(names, String::compareToIgnoreCase);
    System.out.println(Arrays.toString(names));
  }

}