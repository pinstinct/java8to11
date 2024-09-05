package dev.limhm.optional;

import dev.limhm.stream.OnlineClass;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Optional")
class ProgressTest {

  static List<OnlineClass> classes = new ArrayList<>();

  @BeforeAll
  static void setUp() {
    classes.add(new OnlineClass(1, "spring boot", true));
    classes.add(new OnlineClass(2, "spring data jpa", true));
    classes.add(new OnlineClass(3, "spring mvc", false));
    classes.add(new OnlineClass(4, "spring core", false));
    classes.add(new OnlineClass(5, "rest api development", false));
  }

  @Test
  @DisplayName("생긴 이유")
  void case1() {
    OnlineClass onlineClass = new OnlineClass(1, "spring boot", true);

    /* *
     * Cannot invoke "dev.limhm.optional.Progress.getStudyDuration()"
     * because the return value of "dev.limhm.stream.OnlineClass.getProgress()" is null
     * */
//    Duration duration = onlineClass.getProgress().getStudyDuration();
//    System.out.println(duration);

    /* *
     * NPE 발생하기 때문에 아래와 같이 처리해 왔으나,
     * null 체크를 잊을 수 있기 때문(1)에 에러를 발생시키기 좋은 코드이다.
     * */
//    Progress progress = onlineClass.getProgress();
//    if (progress != null) {
//      System.out.println(progress.getStudyDuration());
//    }
  }

  private static OnlineClass createNewClass() {
    System.out.println("creating new online class");
    return new OnlineClass(10, "New class", false);
  }

  @Test
  @DisplayName("Optional에 값이 있는지 없는지 확인하기 - isPresent")
  void case2() {
    Optional<OnlineClass> spring = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("spring"))
        .findFirst();
    boolean present = spring.isPresent();
    System.out.println(present);
  }

  @Test
  @DisplayName("Optional에 값이 있는지 없는지 확인하기 - isEmpty")
  void case3() {
    Optional<OnlineClass> jpa = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("jpa"))
        .findFirst();
    // isPresent 반대
    boolean empty = jpa.isEmpty();
    System.out.println(empty);
    System.out.println(!empty);
  }

  @Test
  @DisplayName("Optional 객체 가져오기 - 있는 경우")
  void case4() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("spring"))
        .findFirst();
    OnlineClass onlineClass = optional.get();
    System.out.println(onlineClass.getTitle());
  }

  @Test
  @DisplayName("Optional 객체 가져오기 - 없는 경우")
  void case5() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("abc"))
        .findFirst();

    if (optional.isPresent()) {
      OnlineClass onlineClass = optional.get();
      System.out.println(onlineClass.getTitle());
    }
  }

  @Test
  @DisplayName("Optional 객체 가져오기 - 있는 경우 + 처리")
  void case6() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("spring"))
        .findFirst();

    optional.ifPresent(onlineClass -> System.out.println(onlineClass.getTitle()));
  }

  @Test
  @DisplayName("Optional 객체 가져오기 - orElse")
  void case7() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("jpa"))
        .findFirst();

    OnlineClass onlineClass = optional.orElse(createNewClass());
    System.out.println(onlineClass.getTitle());

    // 객체가 있으나 없으나 createNewClass 메서드를 실행하는 문제가 있다.
    Optional<OnlineClass> optional1 = classes.stream()
        .filter(o -> o.getTitle().contains("abc"))
        .findFirst();
    OnlineClass onlineClass1 = optional1.orElse(createNewClass());
    System.out.println(onlineClass1.getTitle());
  }

  @Test
  @DisplayName("Optional 객체 가져오기 - orElseGet")
  void case8() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("jpa"))
        .findFirst();

    // 있는 경우 Supplier 실행 안 함
    OnlineClass onlineClass = optional.orElseGet(ProgressTest::createNewClass);
    System.out.println(onlineClass.getTitle());

    Optional<OnlineClass> optional1 = classes.stream()
        .filter(o -> o.getTitle().contains("abc"))
        .findFirst();
    OnlineClass onlineClass1 = optional1.orElseGet(ProgressTest::createNewClass);
    System.out.println(onlineClass1.getTitle());
  }

  @Test
  @DisplayName("Optional 객체 가져오기 - orElseThrow")
  void case9() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(o -> o.getTitle().contains("abc"))
        .findFirst();
//    OnlineClass onlineClass = optional.orElseThrow(IllegalStateException::new);
  }

  @Test
  @DisplayName("Optional에 들어있는 값 걸러내기")
  void case10() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().startsWith("spring"))
        .findFirst();

    Optional<OnlineClass> onlineClass = optional.filter(OnlineClass::isClosed);
    System.out.println(onlineClass.isPresent());
  }

  @Test
  @DisplayName("Optional에 들어있는 값 변환하기")
  void case11() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("spring"))
        .findFirst();

    Optional<Integer> onlineClass = optional.map(OnlineClass::getId);
    System.out.println(onlineClass.isPresent());
  }


  @Test
  @DisplayName("Optional에 들어있는 값 변환하기 - nested Optional인 경우")
  void case12() {
    Optional<OnlineClass> optional = classes.stream()
        .filter(onlineClass -> onlineClass.getTitle().contains("spring"))
        .findFirst();

    // 계속 꺼내야 하므로 불편함
    Optional<Optional<Progress>> progress = optional.map(OnlineClass::getProgress);
    Optional<Progress> progress1 = progress.orElse(Optional.empty());
    // progress1과 flat 동일
    Optional<Progress> flat = optional.flatMap(OnlineClass::getProgress);
  }
}