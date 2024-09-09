package dev.limhm.completableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("자바 Concurrent 프로그래밍 소개")
public class ConcurrentTest {

  @Test
  @DisplayName("Thread 생성 - 상속")
  void case1() {
    MyThread myThread = new MyThread();
    myThread.start();
    System.out.println("Hello: " + Thread.currentThread().getName());
  }

  @Test
  @DisplayName("Thread 생성 - Thread 생성자 with 람다 표현식")
  void case2() {
    Thread thread = new Thread(() ->
        System.out.println("Thread: " + Thread.currentThread().getName()));
    thread.start();
    System.out.println("Hello: " + Thread.currentThread().getName());
  }

  @Test
  @DisplayName("Thread 주요 기능 - 현재 쓰레드 멈춰두기(sleep)")
  void case3() throws InterruptedException {
    Thread thread = new Thread(() -> {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println("Thread: " + Thread.currentThread().getName());
    });

    thread.start();

    System.out.println("Hello: " + Thread.currentThread().getName());

    /* *
     * JUnit 테스트 메서드가 끝나면 실행 중인 다른 스레드를 기다리지 않고 바로 테스트를 종료한다.
     * 이를 해결하기 위해 join()을 사용하여 테스트 메서드가 종료되기 전에 기다리게 해야 한다.
     * */
    thread.join();
  }

  static class MyThread extends Thread {

    @Override
    public void run() {
      System.out.println("Thread: " + Thread.currentThread().getName());
    }
  }
}
