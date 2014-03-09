package com.jnape.palatable.lambda.builtin.dyadic;

import org.junit.Test;

import static com.jnape.palatable.lambda.tuples.Tuple2.tuple;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Tupler2Test {

    @Test
    public void createsTupleOfTwoThings() {
        Tupler2<String, Integer> tupler = new Tupler2<String, Integer>();
        assertThat(tupler.apply("a", 1), is(tuple("a", 1)));
    }
}
