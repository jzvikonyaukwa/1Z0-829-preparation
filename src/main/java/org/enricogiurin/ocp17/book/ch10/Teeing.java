package org.enricogiurin.ocp17.book.ch10;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Teeing {

  public static void main(String[] args) {
    new Teeing().teeing();
  }

  //partially generated by chatGPT

  /**
   * In the context of programming and specifically Java streams, "teeing" refers to the ability to
   * split a stream into two separate streams, perform independent operations on each stream, and
   * then combine the results of those operations into a single result. The term "teeing" is derived
   * from the concept of splitting a stream like a "T" shape, where the input stream branches into
   * two.
   * <p>
   * In Java, the Collectors.teeing() method was introduced in Java 12 to support this operation.
   * This method takes three arguments:
   * <p>
   * The first argument is the original stream. The second argument is a Collector for the first
   * branch of the split. The third argument is a Collector for the second branch of the split. The
   * teeing() method returns a Collector that can be used with the Collectors.collectingAndThen()
   * method to perform a final transformation.
   */
  void teeing() {
    IntStream intStream = IntStream.of(12, 5, 6, 7, 8, 10, 50);
    Collector<Integer, ?, Data> teeing = Collectors.teeing(
        Collectors.summingInt(Integer::intValue),  //with the first collector I collect the sum
        Collectors.reducing(1, (a, b) -> a * b), ////with the 2nd collector I collect the product
        (s, p) -> new Data(s, p));  //BiFunction to create the result Data
    Data collect = intStream
        .boxed()
        .collect(teeing);
    System.out.println(collect); //Data[sum=98, product=10080000]
  }

  //here I want to have a report with the count and the sum of all the values
  void teeing2() {
    record DataReport(long count, long sum) {

    }
    record DataWrapper(int value) {

    }
    Stream<DataWrapper> stream = Stream.of(
        new DataWrapper(1),
        new DataWrapper(3),
        new DataWrapper(5),
        new DataWrapper(15));

    DataReport dataReport = stream.collect(Collectors.teeing(
        Collectors.counting(),
        Collectors.summingLong(DataWrapper::value),
        DataReport::new));
    System.out.println(dataReport);
  }

  record Data(int sum, int product) {
  }
}


