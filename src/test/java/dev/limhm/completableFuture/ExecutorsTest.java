package dev.limhm.completableFuture;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Executors 테스트")
public class ExecutorsTest {

  private static Runnable getRunnable(String message) {
    return () -> System.out.println(message + Thread.currentThread().getName());
  }

  @Test
  @DisplayName("Executors 소개 - Runnable 구현")
  void case1() {
    /* *
     * Thread, Runnable 처럼 raw 레벨 API 가 아닌, 조금 더 고수준의 API 인 Executors 에게 위임한다.
     * Executors 가 쓰레들 생성, 관리, 작업 처리 및 실행을 해주기 때문에, Runnable 제공해주면 된다.
     * (Runnable 에 해야 할 일만 정의)
     * */
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(new Runnable() {
      @Override
      public void run() {
        System.out.println("Thread " + Thread.currentThread().getName());
      }
    });
  }

  @Test
  @DisplayName("submit 사용")
  void case2() {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    // submit(): 다음 작업이 들어올 때 까지 대기
    executorService.submit(() -> System.out.println("Thread " + Thread.currentThread().getName()));
    // 명시적으로 종료해줘야 함, graceful shutdown
    executorService.shutdown();
  }

  @Test
  @DisplayName("thread pool")
  void case3() {
    /* *
     * main -> ExecutorService -> Blocking Queue(여기서 대기하다가 빈 Thread 할당돼 실행) -> Thread Pool
     * */
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.submit(getRunnable("Hello"));
    executorService.submit(getRunnable("Work"));
    executorService.submit(getRunnable("The"));
    executorService.submit(getRunnable("Java"));
    executorService.submit(getRunnable("Kim"));
    executorService.shutdown();
  }

  @Test
  @DisplayName("일정 시간 후 실행")
  void case4() throws InterruptedException {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    service.schedule(getRunnable("Hello"), 3, TimeUnit.SECONDS);
    service.shutdown();

    /* *
     * shutdown() 메소드는 제출된 작업은 마치지만, 더 이상 새로운 작업을 받지 않도록 스레드 풀을 종료한다.
     * 하지만 schedule() 로 등록한 작업은 지연된 후에 실행되기 때문에
     * 테스트 코드가 실행되는 시점에 스레드 풀을 바로 죵로해버리면 작업이 실행되기 전에 프로그램이 끝날 수 있다.
     * 그렇기 때문에 스레드 풀 종료 전에 대기하도록 코드 추가
     * */
    if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
      System.out.println("작업이 완료되지 않았습니다.");
    }
  }

  @Test
  @DisplayName("일정 시간 후 일정 횟수 실행")
  void case5() throws InterruptedException {
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(getRunnable("Hello"), 1, 2, TimeUnit.SECONDS);

    if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
      System.out.println("작업이 완료되지 않았습니다.");
    }
  }
}
