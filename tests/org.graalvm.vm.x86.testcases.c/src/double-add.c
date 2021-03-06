/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.
 */
#include <math.h>
#include <float.h>
#include <stdio.h>
#include <stdint.h>

typedef union {
	double	d;
	int64_t	i;
} DOUBLE;

void __attribute__((noinline)) test(DOUBLE a, DOUBLE b)
{
	DOUBLE sum = { .d = a.d + b.d };
	printf("%016lx:%016lx:%016lx\n", a.i, b.i, sum.i);
}

#define TEST(x, y)	test((DOUBLE) { .d = (x) }, (DOUBLE) { .d = (y) })

int main(void)
{
	TEST(0, 0);
	TEST(1, 0);
	TEST(1, 1);
	TEST(21, 42);
	TEST(-21, 42);
	TEST(21, -42);
	TEST(-21, -42);
	TEST(21, 0);
	TEST(0, 21);
	TEST(-21, 0);
	TEST(0, -21);
	TEST(1E-8, 1);
	TEST(1E-18, 1);
	TEST(1, 1E-8);
	TEST(1, 1E-18);
	TEST(1E8, 1E-18);
	TEST(1E-8, 1E18);

	TEST(NAN, 0);
	TEST(NAN, 1);
	TEST(NAN, -1);
	TEST(NAN, 42);
	TEST(NAN, -42);
	TEST(NAN, 1E27F);
	TEST(NAN, -1E27F);
	TEST(0, NAN);
	TEST(1, NAN);
	TEST(-1, NAN);
	TEST(42, NAN);
	TEST(-42, NAN);
	TEST(1E27F, NAN);
	TEST(-1E27F, NAN);
	TEST(NAN, NAN);

	TEST(INFINITY, 0);
	TEST(INFINITY, 1);
	TEST(INFINITY, -1);
	TEST(INFINITY, 42);
	TEST(INFINITY, -42);
	TEST(INFINITY, 1E27F);
	TEST(INFINITY, -1E27F);
	TEST(0, INFINITY);
	TEST(1, INFINITY);
	TEST(-1, INFINITY);
	TEST(42, INFINITY);
	TEST(-42, INFINITY);
	TEST(1E27F, INFINITY);
	TEST(-1E27F, INFINITY);
	TEST(INFINITY, INFINITY);

	TEST(-NAN, 0);
	TEST(-NAN, 1);
	TEST(-NAN, -1);
	TEST(-NAN, 42);
	TEST(-NAN, -42);
	TEST(-NAN, 1E27F);
	TEST(-NAN, -1E27F);
	TEST(0, -NAN);
	TEST(1, -NAN);
	TEST(-1, -NAN);
	TEST(42, -NAN);
	TEST(-42, -NAN);
	TEST(1E27F, -NAN);
	TEST(-1E27F, -NAN);
	TEST(NAN, -NAN);
	TEST(-NAN, -NAN);
	TEST(-NAN, NAN);

	TEST(-INFINITY, 0);
	TEST(-INFINITY, 1);
	TEST(-INFINITY, -1);
	TEST(-INFINITY, 42);
	TEST(-INFINITY, -42);
	TEST(-INFINITY, 1E27F);
	TEST(-INFINITY, -1E27F);
	TEST(0, -INFINITY);
	TEST(1, -INFINITY);
	TEST(-1, -INFINITY);
	TEST(42, -INFINITY);
	TEST(-42, -INFINITY);
	TEST(1E27F, -INFINITY);
	TEST(-1E27F, -INFINITY);
	TEST(INFINITY, -INFINITY);
	TEST(-INFINITY, -INFINITY);
	TEST(-INFINITY, INFINITY);

	TEST(1E27F, 1E27F);
	TEST(1E27F, -1E27F);
	TEST(-1E27F, 1E27F);
	TEST(-1E27F, -1E27F);

	TEST(DBL_MAX, 0);
	TEST(DBL_MAX, 1);
	TEST(DBL_MAX, -1);
	TEST(DBL_MAX, 42);
	TEST(DBL_MAX, -42);
	TEST(DBL_MAX, 1E27F);
	TEST(DBL_MAX, -1E27F);
	TEST(0, DBL_MAX);
	TEST(1, DBL_MAX);
	TEST(-1, DBL_MAX);
	TEST(42, DBL_MAX);
	TEST(-42, DBL_MAX);
	TEST(1E27F, DBL_MAX);
	TEST(-1E27F, DBL_MAX);
	TEST(DBL_MAX, DBL_MAX);
	TEST(DBL_MAX, -DBL_MAX);
	TEST(DBL_MAX, DBL_MIN);
	TEST(DBL_MAX, -DBL_MIN);

	TEST(-DBL_MAX, 0);
	TEST(-DBL_MAX, 1);
	TEST(-DBL_MAX, -1);
	TEST(-DBL_MAX, 42);
	TEST(-DBL_MAX, -42);
	TEST(-DBL_MAX, 1E27F);
	TEST(-DBL_MAX, -1E27F);
	TEST(0, -DBL_MAX);
	TEST(1, -DBL_MAX);
	TEST(-1, -DBL_MAX);
	TEST(42, -DBL_MAX);
	TEST(-42, -DBL_MAX);
	TEST(1E27F, -DBL_MAX);
	TEST(-1E27F, -DBL_MAX);
	TEST(-DBL_MAX, DBL_MAX);
	TEST(-DBL_MAX, -DBL_MAX);
	TEST(-DBL_MAX, DBL_MIN);
	TEST(-DBL_MAX, -DBL_MIN);

	TEST(DBL_MIN, 0);
	TEST(DBL_MIN, 1);
	TEST(DBL_MIN, -1);
	TEST(DBL_MIN, 42);
	TEST(DBL_MIN, -42);
	TEST(DBL_MIN, 1E27F);
	TEST(DBL_MIN, -1E27F);
	TEST(0, DBL_MIN);
	TEST(1, DBL_MIN);
	TEST(-1, DBL_MIN);
	TEST(42, DBL_MIN);
	TEST(-42, DBL_MIN);
	TEST(1E27F, DBL_MIN);
	TEST(-1E27F, DBL_MIN);
	TEST(DBL_MIN, DBL_MIN);
	TEST(DBL_MIN, -DBL_MIN);
	TEST(DBL_MIN, DBL_MIN);
	TEST(DBL_MIN, -DBL_MIN);

	TEST(-DBL_MIN, 0);
	TEST(-DBL_MIN, 1);
	TEST(-DBL_MIN, -1);
	TEST(-DBL_MIN, 42);
	TEST(-DBL_MIN, -42);
	TEST(-DBL_MIN, 1E27F);
	TEST(-DBL_MIN, -1E27F);
	TEST(0, -DBL_MIN);
	TEST(1, -DBL_MIN);
	TEST(-1, -DBL_MIN);
	TEST(42, -DBL_MIN);
	TEST(-42, -DBL_MIN);
	TEST(1E27F, -DBL_MIN);
	TEST(-1E27F, -DBL_MIN);
	TEST(-DBL_MIN, DBL_MIN);
	TEST(-DBL_MIN, -DBL_MIN);
	TEST(-DBL_MIN, DBL_MAX);
	TEST(-DBL_MIN, -DBL_MAX);
	return 0;
}
