package com.fuzzymeme.uitools;

import static org.junit.Assert.*;

import org.junit.Test;

public class FirstClassTests {

	@Test
	public void test() {
		
		FirstClass firstClass = new FirstClass();
		assertEquals(54, firstClass.someMethod());
	}

}
