package dev.limhm.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Callable과 Future")
public class CallableAndFutureTest {

  @Test
  @DisplayName("get() - 결과 가져오기")
  void case1() throws ExecutionException, InterruptedException {
    // Runnable 은 return void
    // Callable 은 Runnable 이랑 같지만, return 가능(Future)
    ExecutorService service = Executors.newSingleThreadExecutor();

    Callable<String> hello = () -> {
      Thread.sleep(2000L);
      return "Hello";
    };

    Future<String> submit = service.submit(hello);
    System.out.println("Started!!");

    // 이 코드에서 멈춰서 결과를 기다림
    String result = submit.get();  // 블로킹 콜
    System.out.println(result);

    System.out.println("End!!");
    service.shutdown();
  }

  @Test
  @DisplayName("isDone() - 작업 상태 확인하기")
  void case2() throws ExecutionException, InterruptedException {
    ExecutorService service = Executors.newSingleThreadExecutor();

    Callable<String> hello = () -> {
      Thread.sleep(2000L);
      return "Hello";
    };

    Future<String> future = service.submit(hello);
    System.out.println(future.isDone());
    System.out.println("Started!!");

    future.get();

    System.out.println(future.isDone());
    System.out.println("End!!");
    service.shutdown();
  }

  @Test
  @DisplayName("cancel() - 작업 취소하기")
  void case3() {
    ExecutorService service = Executors.newSingleThreadExecutor();

    Callable<String> hello = () -> {
      Thread.sleep(2000L);
      return "Hello";
    };

    Future<String> future = service.submit(hello);
    System.out.println("Started!!");

    // ture: interrupt 종료
    future.cancel(true);

    System.out.println("End!!");
    service.shutdown();
  }

  @Test
  @DisplayName("invokeAll() - 여러 작업 동시에 실행하기")
  void case4() throws InterruptedException, ExecutionException {
    ExecutorService service = Executors.newSingleThreadExecutor();

    Callable<String> hello = () -> {
      Thread.sleep(2000L);
      return "Hello";
    };

    Callable<String> java = () -> {
      Thread.sleep(3000L);
      return "Java";
    };

    Callable<String> the = () -> {
      Thread.sleep(1000L);
      return "The";
    };

    // invokeAll 은 동시에 실행한 작업 중에 제일 오래 걸리는 작업이 끝날 때까지 기다린다.
    List<Future<String>> futures = service.invokeAll(Arrays.asList(hello, java, the));

    for (Future<String> future : futures) {
      System.out.println(future.get());
    }

    service.shutdown();
  }

  @Test
  @DisplayName("invokeAny() - 여러 작업 동시에 실행 후 응답이 하나라도 오면 끝내기")
  void case5() throws ExecutionException, InterruptedException {
    // 예를 들아, 여러 서버에서 동일한 요청을 보내 가장 빠른 응답만 받아 처리하는 경우
    ExecutorService service = Executors.newFixedThreadPool(4);

    Callable<String> hello = () -> {
      Thread.sleep(2000L);
      return "Hello";
    };

    Callable<String> java = () -> {
      Thread.sleep(3000L);
      return "Java";
    };

    Callable<String> the = () -> {
      Thread.sleep(1000L);
      return "The";
    };

    String future = service.invokeAny(Arrays.asList(hello, java, the));
    System.out.println(future);

    service.shutdown();
  }
}
