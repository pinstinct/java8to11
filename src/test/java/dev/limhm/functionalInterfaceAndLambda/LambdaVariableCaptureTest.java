package dev.limhm.functionalInterfaceAndLambda;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("람다 표현식")
class LambdaVariableCaptureTest {

  @Test
  @DisplayName("변수 캡쳐")
  void variableCaptureTest() {
    LambdaVariableCapture lambdaVariableCapture = new LambdaVariableCapture();
    lambdaVariableCapture.run();
  }
}