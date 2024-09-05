package dev.limhm.dateAndTime;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
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

  @Nested
  @DisplayName("API")
  class ApiTest {

    @Test
    @DisplayName("기계 시간 (실행시간 측정 시 사용)")
    void case1() {
      Instant instant = Instant.now();
      // GMT == UTC
      System.out.println(instant);
      System.out.println(instant.atZone(ZoneId.of("UTC")));

      ZoneId zoneId = ZoneId.systemDefault();
      System.out.println(zoneId);
      ZonedDateTime zonedDateTime = instant.atZone(zoneId);
      System.out.println(zonedDateTime);
    }

    @Test
    @DisplayName("인간 시간")
    void case2() {
      LocalDateTime now = LocalDateTime.now();
      System.out.println(now);

      LocalDateTime birthDay = LocalDateTime.of(1987, Month.JANUARY, 28, 4, 20, 0);
      System.out.println(birthDay);

      ZonedDateTime nowInKorea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
      System.out.println(nowInKorea);

      // Instant와 LocalDatTime은 호환이 가능
      Instant nowInstant = Instant.now();
      ZonedDateTime zonedDateTime = nowInstant.atZone(ZoneId.of("Asia/Seoul"));
      System.out.println(zonedDateTime);
    }

    @Test
    @DisplayName("기간 표현 - Period (인간용)")
    void case3() {
      LocalDate today = LocalDate.now();
      LocalDate birthDay = LocalDate.of(today.getYear() + 1, Month.DECEMBER, 28);

      Period period = Period.between(today, birthDay);
      System.out.println(period.getDays());

      // 위와 동일한 기능
      Period until = today.until(birthDay);
      System.out.println(until.get(ChronoUnit.DAYS));
    }

    @Test
    @DisplayName("기간 표현 - Duration (기계용)")
    void case4() {
      Instant now = Instant.now();
      Instant plus = Instant.now().plus(10, ChronoUnit.SECONDS);
      Duration between = Duration.between(now, plus);
      System.out.println(between.getSeconds());
    }

    @Test
    @DisplayName("포매팅")
    void case5() {
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      System.out.println(now.format(formatter));
    }

    @Test
    @DisplayName("파싱")
    void case6() {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      LocalDate parse = LocalDate.parse("01/28/1987", formatter);
      System.out.println(parse);
    }

    @Test
    @DisplayName("레거시 API 지원")
    void case7() {
      Date date = new Date();
      Instant instant = date.toInstant();
      Date newDate = Date.from(instant);

      GregorianCalendar gregorianCalendar = new GregorianCalendar();
      ZonedDateTime dateTime = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());
      GregorianCalendar from = GregorianCalendar.from(dateTime);

      ZoneId zoneId = TimeZone.getTimeZone("PST").toZoneId();
      TimeZone timeZone = TimeZone.getTimeZone(zoneId);
    }
  }
}
