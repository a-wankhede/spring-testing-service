package com.spring.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class TestingAppApplicationTests {

	@BeforeEach
	void setUp() {
		log.info("start method");
	}

	@AfterEach
	void tearDown() {
		log.info("tearing down");
	}

	@BeforeAll
	static void setUpOnce() {
		log.info("Setup once...");
	}

	@AfterAll
	static void tearDownOnce() {
		log.info("tear once...");
	}

//	@Test
////	@Disabled
//	void testNumberOne() {
//		int a = 5;
//		int b = 3;
//
//		int result = addTwoNumbers(a, b);

//		Assertions.assertEquals(8, result);

//		assertThat(result)
//				.isEqualTo(7)
//				.isCloseTo(9, Offset.offset(1));

//		assertThat("Apple")
//				.isEqualTo("Apple")
//				.startsWith("Ap")
//				.endsWith("le")
//				.hasSize(5);

	}

//	@Test
////	@DisplayName("testTwo")
//	void testDivideTwoNumbers_whenDenominatorIsZero_ThenArithmeticException() {
//
//		int a = 5, b = 0;
//		double result = divideTwoNumbers(a,b);
//
//		Assertions.assertThatThrownBy(() -> divideTwoNumbers(a, b))
//				.isInstanceOf(ArithmeticException.class)
//				.hasMessage("Tried to divide by zero");
//
//
//	}
//
//	int addTwoNumbers(int a, int b) {
//		return a + b;
//	}
//
//	double divideTwoNumbers(int a, int b) {
//		try {
//			return (double) a /b;
//		} catch (ArithmeticException e) {
//            log.error("Arithmetic Exception : " + e.getLocalizedMessage());
//			throw new ArithmeticException("Tried to divide by zero");
//        }
//    }
























