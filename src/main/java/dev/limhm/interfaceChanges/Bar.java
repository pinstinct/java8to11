package dev.limhm.interfaceChanges;

public interface Bar extends Foo {

  // 기본 구현체를 제공하고 싶지 않을때, 추상 메서드로 선언
  // 단, 이런경우 Bar 를 구현하는 모든 인스턴스가 모두 재정의 해야 함
  // (없으면 기본 구현체 제공)
  void printNameUpperCase();
}
