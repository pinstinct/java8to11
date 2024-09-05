package dev.limhm.dateAndTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Date와 Time")
class DateAndTimeTest {

  @Nested
  @DisplayName("기존의 문제점")
  class ProblemTest {

    @Test
    @DisplayName("클래스 이름이 불명확하다")
    void case1() {
      Date date = new Date();
      long time = date.getTime();
    }

    @Test
    @DisplayName("time이 epoch 시간으로 나온다")
    void case2() {
      Date date = new Date();
      long time = date.getTime();

      System.out.println(date);
      System.out.println(time);
    }

    @Test
    @DisplayName("mutable 하기 때문에 thread safe 하지 않다")
    void case3() throws InterruptedException {
      // mutable: 똑같은 인스턴스인데 값을 변경할 수 있다.
      // 여러 쓰레드(멀티쓰레드 환경)가 동시에 접근해 값을 변경할 수 있기 때문에 thread safe하지 않다.
      Date current = new Date();
      long time = current.getTime();
      System.out.println(current);

      Thread.sleep(1000 * 3);  // 3초
      Date after3seconds = new Date();
      System.out.println(after3seconds);

      after3seconds.setTime(time);
      System.out.println(after3seconds);

      /* *
       * 자바 8에서 immutable 하게 변경
       * 값을 변경하게 되면 기존 인스턴스가 변경되는 것이 아니라,
       * 새로운 인스턴스가 생성된다.
       * */
    }

    @Test
    @DisplayName("type safe 하지 않다")
    void case4() {
      // 월이 0부터 시작
      // 월이 int를 받으므로 -1, 100 모두 가능한 구조이므로 type safe하지 않다.
      // (정의된 ENUM만 받게 하던지 해야 한다.)
      Calendar birth = new GregorianCalendar(1987, 1, 28);
      System.out.println(birth.getTime());
    }
  }
}
