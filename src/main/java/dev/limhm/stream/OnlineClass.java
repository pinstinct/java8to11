package dev.limhm.stream;

import dev.limhm.optional.Progress;
import java.util.Optional;

public class OnlineClass {

  private Integer id;

  private String title;

  private boolean closed;

  public Progress progress;

  public OnlineClass(Integer id, String title, boolean closed) {
    this.id = id;
    this.title = title;
    this.closed = closed;
  }

  public Optional<Progress> getProgress() {
    /* *
     * null 을 리턴하는 것(2)도 문제다.
     * 자바 8 이전에는 대안이 없었다.
     * 에러를 던지거나 null 을 리턴했었다.
     *
     * 에러를 던질 때, 문제점
     * 1. 런타임 에러는 괜찮지만, checked 에러는 에러 처리 강제한다.
     * 2. stack trace 에 에러를 담게 되며 그 만큼 리소스를 사용하게 된다.
     * 때문에 진짜 필요할 때만 예외를 사용해야지 로직을 처리할 때 사용하는건 좋은 습관은 아니다.
     *
     * null 을 리턴할 때, 문제점
     * 1. 코드를 사용하는 클라이언트 코드가 주의해야 한다.
     *
     * 자바 8 부터 Optional 을 제공해 이런 경우 사용할 수 있다.
     * 대신 리턴값에만 사용하자.
     * */
    return Optional.ofNullable(progress);
  }

  public void setProgress(Progress progress) {
    this.progress = progress;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }
}
