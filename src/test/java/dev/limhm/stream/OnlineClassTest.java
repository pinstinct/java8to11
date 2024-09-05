package dev.limhm.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Stream 응용")
class OnlineClassTest {

  static List<OnlineClass> springClasses = new ArrayList<>();
  static List<OnlineClass> javaClasses = new ArrayList<>();

  @BeforeAll
  static void setUp() {
    springClasses.add(new OnlineClass(1, "spring boot", true));
    springClasses.add(new OnlineClass(2, "spring data jpa", true));
    springClasses.add(new OnlineClass(3, "spring mvc", false));
    springClasses.add(new OnlineClass(4, "spring core", false));
    springClasses.add(new OnlineClass(5, "rest api development", false));

    javaClasses.add(new OnlineClass(6, "The Java, Test", true));
    javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
    javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));
  }

  @Test
  @DisplayName("spring 으로 시작하는 수업")
  void case1() {
    springClasses.stream()
        // filter(Predicate)
        .filter(onlineClass -> onlineClass.getTitle().startsWith("spring"))
        .forEach(onlineClass -> System.out.println(onlineClass.getId()));
  }

  @Test
  @DisplayName("close 되지 않은 수업")
  void case2() {
    springClasses.stream()
        .filter(onlineClass -> !onlineClass.isClosed())
        .forEach(onlineClass -> System.out.println(onlineClass.getId()));

    System.out.println("===");

    // 위와 동일
    springClasses.stream()
        .filter(Predicate.not(OnlineClass::isClosed))
        .forEach(onlineClass -> System.out.println(onlineClass.getId()));
  }

  @Test
  @DisplayName("수업 이름만 모아서 스트림 만들기")
  void case3() {
    Stream<String> stream = springClasses.stream()
        // map(Function)
        .map(OnlineClass::getTitle);
    stream.forEach(System.out::println);
  }

  @Test
  @DisplayName("두 수업 목록에 들어있는 모든 수업 아이디 출력")
  void case4() {
    List<List<OnlineClass>> allClasses = new ArrayList<>();
    allClasses.add(springClasses);
    allClasses.add(javaClasses);

    allClasses.stream().flatMap(Collection::stream)  // nested list 를 list stream 으로 변환
        .forEach(onlineClass -> System.out.println(onlineClass.getId()));
  }

  @Test
  @DisplayName("10 부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만")
  void case5() {
    Stream.iterate(0, integer -> integer + 1)
        .skip(10)
        .limit(10)
        .forEach(System.out::println);
  }

  @Test
  @DisplayName("자바 수업 중에 Test가 들어있는 수업이 있는지 확인")
  void case6() {
    javaClasses.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("Test"))
        .forEach(onlineClass -> System.out.println(onlineClass.getTitle()));

    boolean test = javaClasses.stream()
        // anyMatch, allMatch, nonMatch 는 true/false 반환
        .anyMatch(onlineClass -> onlineClass.getTitle().contains("Test"));
    System.out.println(test);
  }

  @Test
  @DisplayName("스프링 수업 중에 제목에 spring이 들어간 제목만 모아서 List로 만들기")
  void case7() {
    List<String> spring = springClasses.stream()
        .map(OnlineClass::getTitle)
        .filter(title -> title.contains("spring"))
        .toList();
    System.out.println(spring);
  }
}