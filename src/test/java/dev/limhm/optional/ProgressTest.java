package dev.limhm.optional;

import dev.limhm.stream.OnlineClass;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Optional 기초")
class ProgressTest {

  static List<OnlineClass> classes = new ArrayList<>();

  @BeforeAll
  static void setUp() {
    classes.add(new OnlineClass(1, "spring boot", true));
    classes.add(new OnlineClass(2, "spring data jap", true));
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

}