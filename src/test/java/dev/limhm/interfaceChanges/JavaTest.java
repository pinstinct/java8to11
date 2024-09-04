package dev.limhm.interfaceChanges;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("자바 8 API 기본 메소드와 스태틱 메소드")
public class JavaTest {
  static List<String> names = new ArrayList<>();

  @BeforeAll
  static void setUp() {
    names.add("kim");
    names.add("white");
    names.add("toby");
    names.add("foo");
  }

  @Test
  @DisplayName("forEach")
  void forEachTest() {
    // 람다 표현식
    names.forEach(s -> System.out.println(s));
    // 메소드 레퍼런스
    names.forEach(System.out::println);
  }

  @Test
  @DisplayName("spliterator")
  void spliteratorTest() {
    // 두 개의 splitter
    Spliterator<String> spliterator = names.spliterator();
    Spliterator<String> spliterator1 = spliterator.trySplit();

    // 병렬 처리 시 사용
    while (spliterator.tryAdvance(System.out::println));
    System.out.println("===");
    while (spliterator1.tryAdvance(System.out::println));
  }

  @Test
  @DisplayName("stream")
  void streamTest() {
    long k = names.stream().map(String::toUpperCase)
        .filter(s -> s.startsWith("K"))
        .count();
    System.out.println(k);
  }

  @Test
  @DisplayName("removeIf")
  void removeIfTest() {
    names.removeIf(s -> s.startsWith("k"));
    names.forEach(System.out::println);
  }

  @Test
  @DisplayName("sort")
  void sortTest() {
    Comparator<String> comparator = String::compareToIgnoreCase;
    names.sort(comparator.reversed());
    names.forEach(System.out::println);
  }

}
