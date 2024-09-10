package dev.limhm.etc;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("애노테이션의 변화")
public class AnnotationChangeTest {


  @Test
  @DisplayName("TYPE_PARAMETER - 타입 변수에만 사용 가능")
  void case1() {
    class FeelsLikeChicken<@Chicken T> {

      public static <@Chicken C> void print(C c) {
        System.out.println(c);
      }
    }
  }

  @Test
  @DisplayName("TYPE_USE - 모든 타입 선언부에 사용 가능")
  void case2() throws @Pizza RuntimeException {
    List<@Pizza String> names = Arrays.asList("kim", "lim");

    class FeelsLikeNuget<@Pizza N> {

      public static <@Pizza N> void print(@Pizza N n) {
        System.out.println(n);
      }
    }
  }

  @Test
  @DisplayName("애너테이션을 중복해서 사용 가능")
  void case3() {

    @Nuget("고추장")
    @Nuget("간장")
    class Nested {

      public void print() {
        Nuget[] nugets = Nested.class.getAnnotationsByType(Nuget.class);
        Arrays.stream(nugets).forEach(nuget -> System.out.println(nuget.value()));
      }

      public void print1() {
        // 컨테이너로 가져오기
        NugetContainer nugetContainer = Nested.class.getAnnotation(NugetContainer.class);
        Arrays.stream(nugetContainer.value()).forEach(c -> System.out.println(c.value()));
      }

    }

    Nested nested = new Nested();
    nested.print();
    System.out.println("===");
    nested.print1();
  }
}
