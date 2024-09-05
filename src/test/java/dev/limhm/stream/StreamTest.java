package dev.limhm.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Stream 기초")
public class StreamTest {

  static List<String> names = new ArrayList<>();

  @BeforeAll
  static void setUp() {
    names.add("kim");
    names.add("white");
    names.add("toby");
    names.add("foo");
  }

  @Test
  @DisplayName("스트림이 처리하는 데이터 소스를 변경하지 않는다.")
  void case1() {
    // names 변수가 변경되는 것이 아니라 새로운 Stream 객체가 생성된다.
    Stream<String> stream = names.stream().map(String::toUpperCase);

    System.out.println("===");
    names.forEach(System.out::println);
  }

  @Test
  @DisplayName("중개 오퍼레이션은 근본적으로 lazy 하다.")
  void case2() {
    // 중개형 오퍼레이션은 여러개 올 수 있다.
    // 종료형 오퍼레이션을 만날 때 까지 실행되지 않는다.
    Stream<String> stream = names.stream().map(s -> {
      // 실행 안됨
      System.out.println(s);
      return s.toUpperCase();
    });

    System.out.println("===");

    List<String> collect = names.stream().map(s -> {
      System.out.println(s);
      return s.toUpperCase();
    }).collect(Collectors.toList());
    System.out.println("---");
    collect.forEach(System.out::println);
  }

  @Test
  @DisplayName("손쉽게 병렬 처리를 할 수 있다.")
  void case3() {
    // 자바가 알아서 병렬적으로 처리해 준다.
    // 쓰레드 컨텍스트 스위치 비용이 들기 때문에, 데이터가 방대하게 큰 경우에만 유용하다.
    // 대부분의 경우 stream 사용해도 충분하다.
    // 케이스마다 성능 측정해서 사용하는게 좋다.
    List<String> collect = names.parallelStream().map(s -> {
      System.out.println(s + " " + Thread.currentThread().getName());
      return s.toUpperCase();
    }).toList();
    collect.forEach(System.out::println);

    names.stream().map(s -> {
      System.out.println(s + " " + Thread.currentThread().getName());
      return s.toUpperCase();
    }).toList();
  }
}
