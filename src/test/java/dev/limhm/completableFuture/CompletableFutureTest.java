package dev.limhm.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CompletableFuture 테스트")
public class CompletableFutureTest {

  @Nested
  @DisplayName("기초")
  class Basic {

    @Test
    @DisplayName("명시적으로 Executors 만들어 쓸 필요가 없다.")
    void case1() throws ExecutionException, InterruptedException {
      CompletableFuture<String> future = new CompletableFuture<>();
      future.complete("hello");  // 기본 값 설정
      System.out.println(future.get());
    }

    @Test
    @DisplayName("case1과 같지만, static factory method 사용")
    void case2() throws ExecutionException, InterruptedException {
      CompletableFuture<String> future = CompletableFuture.completedFuture("hello");
      System.out.println(future.get());
    }

    @Test
    @DisplayName("runAsync() - 비동기로 리턴값이 없는 작업 실행하기")
    void case3() throws ExecutionException, InterruptedException {
      CompletableFuture<Void> future = CompletableFuture.runAsync(
          () -> System.out.println("Hello " + Thread.currentThread().getName()));
      future.get();
    }

    @Test
    @DisplayName("supplyAsync() - 비동기로 리턴값이 있는 작업 실행하기")
    void case4() throws ExecutionException, InterruptedException {
      CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
        System.out.println("Hello " + Thread.currentThread().getName());
        return "Hello";
      });
      System.out.println(future.get());
    }

    @Test
    @DisplayName("thenApply() - 리턴값을 받아서 다른 값으로 바꾸는 콜백")
    void case5() throws ExecutionException, InterruptedException {
      CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
        System.out.println("Hello " + Thread.currentThread().getName());
        return "Hello";
      }).thenApply(s -> {  // 콜백을 get() 호출전에 정의하는게 불가능 했었다.
        System.out.println(Thread.currentThread().getName());
        return s.toUpperCase();
      });
      System.out.println(future.get());
    }

    @Test
    @DisplayName("thenAccept() - 리턴값을 받아서 리턴없이 다른 작업을 처리하는 콜백")
    void case6() throws ExecutionException, InterruptedException {
      CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
        System.out.println("Hello " + Thread.currentThread().getName());
        return "Hello";
      }).thenAccept(s -> {
        System.out.println(Thread.currentThread().getName());
        System.out.println(s.toUpperCase());
      });

      future.get();
    }

    @Test
    @DisplayName("thenRun() - 리턴값을 받지 않고 리턴없이 다른 작업을 처리하는 콜백")
    void case7() throws ExecutionException, InterruptedException {
      // 별다른 Executors를 사용하지 않아도 내부적으로 ForkJoinPool.commonPool을 사용
      CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
        System.out.println("Hello " + Thread.currentThread().getName());
        return "Hello";
      }).thenRun(() -> System.out.println(Thread.currentThread().getName()));

      future.get();
    }

    @Test
    @DisplayName("스레드 풀을 만들어서 사용하기")
    void case8() throws ExecutionException, InterruptedException {
      ExecutorService service = Executors.newFixedThreadPool(4);
      CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
        System.out.println("Hello " + Thread.currentThread().getName());
        return "Hello";
      }, service).thenRunAsync(() -> System.out.println(Thread.currentThread().getName()), service);
      future.get();
      service.shutdown();
    }
  }

  @Nested
  @DisplayName("API 사용법")
  class Api {

    @Nested
    @DisplayName("조합하기")
    class Combine {

      /**
       * 콜백을 줄 수 없었기 때문에, 비동기 작업을 이어서 처리하는게 어려웠다.
       */

      private static CompletableFuture<String> getWord(String message) {
        return CompletableFuture.supplyAsync(() -> {
          System.out.println("World " + Thread.currentThread().getName());
          return message + " World";
        });
      }

      @Test
      @DisplayName("thenCompose() - 두 작업간 의존성이 있는 경우, 이어서 실행해야 하는 경우")
      void case1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
          System.out.println("Hello " + Thread.currentThread().getName());
          return "Hello";
        });

        CompletableFuture<String> future = hello.thenCompose(Combine::getWord);
        System.out.println(future.get());
      }

      @Test
      @DisplayName("thenCombine() - 작업간 연관관계가 없는 경우, 두 작업을 독립적으로 실행 후 모두 종료 시 콜백")
      void case2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
          System.out.println("Hello " + Thread.currentThread().getName());
          return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
          System.out.println("World " + Thread.currentThread().getName());
          return "World";
        });

        CompletableFuture<String> future = hello.thenCombine(world, (h, w) -> h + " " + w);
        System.out.println(future.get());
      }

      @Test
      @DisplayName("allOf() - 여러 작업을 모두 실행하고 무든 작업 결과에 콜백 실행")
      void case3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
          System.out.println("Hello " + Thread.currentThread().getName());
          return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
          System.out.println("World " + Thread.currentThread().getName());
          return "World";
        });

        // 모든 작업의 결과의 반환 값이 다르기도 하고, 예러나는 작업도 있기 때문에
        // 결과 값은 null 반환
        CompletableFuture<Void> future = CompletableFuture.allOf(hello, world)
            .thenAccept(System.out::println);
        System.out.println(future.get());

        System.out.println("===");

        // 모든 작업의 결과를 컬렉션으로 받는 방법
        List<CompletableFuture<String>> futures = Arrays.asList(hello, world);
        CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);
        CompletableFuture<List<String>> result = CompletableFuture.allOf(futuresArray)
            .thenApply(v -> {  // 결과는 무의미, thenApply 호출 시점에는 이미 모든 작업이 완료
              return futures.stream().map(
                      CompletableFuture::join)  // get()은 checked 예외가 발생하므로 join() 사용, join()은 unchecked 예외 발생
                  .collect(Collectors.toList());
            });
        result.get().forEach(System.out::println);
      }

      @Test
      @DisplayName("anyOf() - 여러 작업 중 가장 빨리 끝난 작업의 결과를 콜백")
      void case4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
          System.out.println("Hello " + Thread.currentThread().getName());
          return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
          System.out.println("World " + Thread.currentThread().getName());
          return "World";
        });

        CompletableFuture<Void> future = CompletableFuture.anyOf(hello, world)
            .thenAccept(System.out::println);
        future.get();
      }
    }

    @Nested
    @DisplayName("예외처리")
    class Exception {

      @Test
      @DisplayName("exceptionally")
      void case1() throws ExecutionException, InterruptedException {
        boolean throwError = true;  // 무조건 에러를 던지게 처리

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
          if (throwError) {
            throw new IllegalArgumentException();
          }
          System.out.println("Hello " + Thread.currentThread().getName());
          return "Hello";
        }).exceptionally(e -> {
          // 에러 타입을 받아서 작업을 처리
          System.out.println(e);
          return "Error!";
        });

        System.out.println(hello.get());
      }

      @Test
      @DisplayName("handle - 정상/에러 두 개의 결과를 핸들링 할 수 있다.")
      void case2() throws ExecutionException, InterruptedException {
        boolean throwError = true;

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
          if (throwError) {
            throw new IllegalArgumentException();
          }
          System.out.println("Hello " + Thread.currentThread().getName());
          return "Hello";
        }).handle((result, ex) -> {
          if (ex != null) {
            System.out.println(ex);
            return "Error";
          }
          return result;
        });

        System.out.println(hello.get());
      }
    }
  }
}