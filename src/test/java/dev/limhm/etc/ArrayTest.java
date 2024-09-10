package dev.limhm.etc;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("배열 병렬 정렬")
public class ArrayTest {

  @Test
  @DisplayName("")
  void case1() {
    int size = 1500;
    int[] numbers = new int[size];
    Random random = new Random();

    IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
    long start = System.nanoTime();
    /* *
     * sort()는 기본적으로 스레드 1개만 사용
     * Dual-Pivot Quicksort 사용, O(n log(n))
     * */
    Arrays.sort(numbers);
    System.out.println("serial sorting took " + (System.nanoTime() - start));

    IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
    start = System.nanoTime();
    // 알고리즘은 동일
    Arrays.parallelSort(numbers);
    System.out.println("parallel sorting took " + (System.nanoTime() - start));
  }
}
