/*
 * Copyright (c) 2011 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.common.truth;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for Integer Subjects.
 *
 * @author David Saff
 * @author Christian Gruber
 * @author Kurt Alfred Kluever
 */
@RunWith(JUnit4.class)
public class IntegerSubjectTest extends BaseSubjectTestCase {

  @Test
  public void simpleEquality() {
    assertThat(4).isEqualTo(4);
  }

  @Test
  public void simpleInequality() {
    assertThat(4).isNotEqualTo(5);
  }

  @Test
  public void equalityWithLongs() {
    assertThat(0).isEqualTo(0L);
    expectFailureWhenTestingThat(0).isNotEqualTo(0L);
  }

  @Test
  public void equalityFail() {
    expectFailureWhenTestingThat(4).isEqualTo(5);
  }

  @Test
  public void inequalityFail() {
    expectFailureWhenTestingThat(4).isNotEqualTo(4);
  }

  @Test
  public void equalityOfNulls() {
    assertThat((Integer) null).isEqualTo(null);
  }

  @Test
  public void equalityOfNullsFail_nullActual() {
    expectFailureWhenTestingThat(null).isEqualTo(5);
  }

  @Test
  public void equalityOfNullsFail_nullExpected() {
    expectFailureWhenTestingThat(5).isEqualTo(null);
  }

  @Test
  public void inequalityOfNulls() {
    assertThat(4).isNotEqualTo(null);
    assertThat((Integer) null).isNotEqualTo(4);
  }

  @Test
  public void inequalityOfNullsFail() {
    expectFailureWhenTestingThat(null).isNotEqualTo(null);
  }

  @Test
  public void overflowOnPrimitives() {
    assertThat(Long.MIN_VALUE).isNotEqualTo(Integer.MIN_VALUE);
    assertThat(Long.MAX_VALUE).isNotEqualTo(Integer.MAX_VALUE);

    assertThat(Integer.MIN_VALUE).isNotEqualTo(Long.MIN_VALUE);
    assertThat(Integer.MAX_VALUE).isNotEqualTo(Long.MAX_VALUE);

    assertThat(Integer.MIN_VALUE).isEqualTo((long) Integer.MIN_VALUE);
    assertThat(Integer.MAX_VALUE).isEqualTo((long) Integer.MAX_VALUE);
  }

  @Test
  public void overflowOnPrimitives_shouldBeEqualAfterCast_min() {
    expectFailureWhenTestingThat(Integer.MIN_VALUE).isNotEqualTo((long) Integer.MIN_VALUE);
  }

  @Test
  public void overflowOnPrimitives_shouldBeEqualAfterCast_max() {
    expectFailureWhenTestingThat(Integer.MAX_VALUE).isNotEqualTo((long) Integer.MAX_VALUE);
  }

  @Test
  public void overflowBetweenIntegerAndLong_shouldBeDifferent_min() {
    expectFailureWhenTestingThat(Integer.MIN_VALUE).isEqualTo(Long.MIN_VALUE);
  }

  @Test
  public void overflowBetweenIntegerAndLong_shouldBeDifferent_max() {
    expectFailureWhenTestingThat(Integer.MAX_VALUE).isEqualTo(Long.MAX_VALUE);
  }

  @SuppressWarnings("TruthSelfEquals")
  @Test
  public void testPrimitivesVsBoxedPrimitivesVsObject_int() {
    int int42 = 42;
    Integer integer42 = new Integer(42);
    Object object42 = (Object) 42;

    assertThat(int42).isEqualTo(int42);
    assertThat(integer42).isEqualTo(int42);
    assertThat(object42).isEqualTo(int42);

    assertThat(int42).isEqualTo(integer42);
    assertThat(integer42).isEqualTo(integer42);
    assertThat(object42).isEqualTo(integer42);

    assertThat(int42).isEqualTo(object42);
    assertThat(integer42).isEqualTo(object42);
    assertThat(object42).isEqualTo(object42);
  }

  @SuppressWarnings("TruthSelfEquals")
  @Test
  public void testPrimitivesVsBoxedPrimitivesVsObject_long() {
    long longPrim42 = 42;
    Long long42 = new Long(42);
    Object object42 = (Object) 42L;

    assertThat(longPrim42).isEqualTo(longPrim42);
    assertThat(long42).isEqualTo(longPrim42);
    assertThat(object42).isEqualTo(longPrim42);

    assertThat(longPrim42).isEqualTo(long42);
    assertThat(long42).isEqualTo(long42);
    assertThat(object42).isEqualTo(long42);

    assertThat(longPrim42).isEqualTo(object42);
    assertThat(long42).isEqualTo(object42);
    assertThat(object42).isEqualTo(object42);
  }

  @Test
  public void testAllCombinations_pass() {
    assertThat(42).isEqualTo(42L);
    assertThat(42).isEqualTo(new Long(42L));
    assertThat(new Integer(42)).isEqualTo(42L);
    assertThat(new Integer(42)).isEqualTo(new Long(42L));
    assertThat(42L).isEqualTo(42);
    assertThat(42L).isEqualTo(new Integer(42));
    assertThat(new Long(42L)).isEqualTo(42);
    assertThat(new Long(42L)).isEqualTo(new Integer(42));

    assertThat(42).isEqualTo(42);
    assertThat(42).isEqualTo(new Integer(42));
    assertThat(new Integer(42)).isEqualTo(42);
    assertThat(new Integer(42)).isEqualTo(new Integer(42));
    assertThat(42L).isEqualTo(42L);
    assertThat(42L).isEqualTo(new Long(42L));
    assertThat(new Long(42L)).isEqualTo(42L);
    assertThat(new Long(42L)).isEqualTo(new Long(42L));
  }

  @Test
  public void testNumericTypeWithSameValue_shouldBeEqual_int_long() {
    expectFailureWhenTestingThat(42).isNotEqualTo(42L);
  }

  @Test
  public void testNumericTypeWithSameValue_shouldBeEqual_int_int() {
    expectFailureWhenTestingThat(42).isNotEqualTo(42);
  }

  @Test
  public void testNumericPrimitiveTypes_isNotEqual_shouldFail_intToChar() {
    expectFailureWhenTestingThat(42).isNotEqualTo((char) 42);
    // 42 in ASCII is '*'
    assertFailureValue("expected not to be", "*");
    assertFailureValue("but was; string representation of actual value", "42");
  }

  @Test
  public void testNumericPrimitiveTypes_isNotEqual_shouldFail_charToInt() {
    // Uses Object overload rather than Integer.
    expectFailure.whenTesting().that((char) 42).isNotEqualTo(42);
    // 42 in ASCII is '*'
    assertFailureValue("expected not to be", "42");
    assertFailureValue("but was; string representation of actual value", "*");
  }

  private static final Subject.Factory<DefaultSubject, Object> DEFAULT_SUBJECT_FACTORY =
      new Subject.Factory<DefaultSubject, Object>() {
        @Override
        public DefaultSubject createSubject(FailureMetadata metadata, Object that) {
          return new DefaultSubject(metadata, that);
        }
      };

  private static void expectFailure(
      ExpectFailure.SimpleSubjectBuilderCallback<DefaultSubject, Object> callback) {
    AssertionError unused = ExpectFailure.expectFailureAbout(DEFAULT_SUBJECT_FACTORY, callback);
  }

  @Test
  public void testNumericPrimitiveTypes() {
    byte byte42 = (byte) 42;
    short short42 = (short) 42;
    char char42 = (char) 42;
    int int42 = 42;
    long long42 = (long) 42;

    ImmutableSet<Object> fortyTwos =
        ImmutableSet.<Object>of(byte42, short42, char42, int42, long42);
    for (Object actual : fortyTwos) {
      for (Object expected : fortyTwos) {
        assertThat(actual).isEqualTo(expected);
      }
    }

    ImmutableSet<Object> fortyTwosNoChar = ImmutableSet.<Object>of(byte42, short42, int42, long42);
    for (final Object actual : fortyTwosNoChar) {
      for (final Object expected : fortyTwosNoChar) {
        ExpectFailure.SimpleSubjectBuilderCallback<DefaultSubject, Object> actualFirst =
            new ExpectFailure.SimpleSubjectBuilderCallback<DefaultSubject, Object>() {
              @Override
              public void invokeAssertion(SimpleSubjectBuilder<DefaultSubject, Object> expect) {
                expect.that(actual).isNotEqualTo(expected);
              }
            };
        ExpectFailure.SimpleSubjectBuilderCallback<DefaultSubject, Object> expectedFirst =
            new ExpectFailure.SimpleSubjectBuilderCallback<DefaultSubject, Object>() {
              @Override
              public void invokeAssertion(SimpleSubjectBuilder<DefaultSubject, Object> expect) {
                expect.that(expected).isNotEqualTo(actual);
              }
            };
        expectFailure(actualFirst);
        expectFailure(expectedFirst);
      }
    }

    byte byte41 = (byte) 41;
    short short41 = (short) 41;
    char char41 = (char) 41;
    int int41 = 41;
    long long41 = (long) 41;

    ImmutableSet<Object> fortyOnes =
        ImmutableSet.<Object>of(byte41, short41, char41, int41, long41);

    for (Object actual : fortyTwos) {
      for (Object expected : fortyOnes) {
        assertThat(actual).isNotEqualTo(expected);
        assertThat(expected).isNotEqualTo(actual);
      }
    }
  }

  private IntegerSubject expectFailureWhenTestingThat(Integer actual) {
    return expectFailure.whenTesting().that(actual);
  }
}
