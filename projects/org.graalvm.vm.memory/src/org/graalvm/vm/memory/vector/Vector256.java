package org.graalvm.vm.memory.vector;

import java.util.Arrays;

import com.oracle.truffle.api.CompilerDirectives.ValueType;

@ValueType
public class Vector256 {
    public static final Vector256 ZERO = new Vector256();

    private final long[] data = new long[4];

    public Vector256() {
    }

    public Vector256(long[] data) {
        assert data.length == 4;
        for (int i = 0; i < 4; i++) {
            this.data[i] = data[i];
        }
    }

    public Vector256(Vector128 high, Vector128 low) {
        this.data[0] = high.getI64(0);
        this.data[1] = high.getI64(1);
        this.data[2] = low.getI64(0);
        this.data[3] = low.getI64(1);
    }

    public Vector128 getI128(int i) {
        assert i >= 0 && i < 2;
        long[] val = new long[2];
        val[0] = data[2 * i + 0];
        val[1] = data[2 * i + 1];
        return new Vector128(val);
    }

    public void setI128(int i, Vector128 val) {
        assert i >= 0 && i < 2;
        data[2 * i + 0] = val.getI64(0);
        data[2 * i + 1] = val.getI64(1);
    }

    public double getF64(int i) {
        assert i >= 0 && i < 4;
        return Double.longBitsToDouble(data[i]);
    }

    public void setF64(int i, double val) {
        assert i >= 0 && i < 4;
        data[i] = Double.doubleToLongBits(val);
    }

    public long getI64(int i) {
        assert i >= 0 && i < 4;
        return data[i];
    }

    public void setI64(int i, long val) {
        assert i >= 0 && i < 4;
        data[i] = val;
    }

    public int getI32(int i) {
        assert i >= 0 && i < 8;
        long val = data[i / 2];
        if ((i & 1) == 0) {
            return (int) (val >>> 32);
        } else {
            return (int) val;
        }
    }

    public void setI32(int i, int val) {
        assert i >= 0 && i < 8;
        long old = data[i / 2];
        long mask;
        int shift;
        if ((i & 1) == 0) {
            mask = 0x00000000FFFFFFFFL;
            shift = 32;
        } else {
            mask = 0xFFFFFFFF00000000L;
            shift = 0;
        }
        long result = (old & mask) | (Integer.toUnsignedLong(val) << shift);
        data[i / 2] = result;
    }

    public float getF32(int i) {
        assert i >= 0 && i < 8;
        return Float.intBitsToFloat(getI32(i));
    }

    public void setF32(int i, float val) {
        assert i >= 0 && i < 8;
        setI32(i, Float.floatToRawIntBits(val));
    }

    public short getI16(int i) {
        assert i >= 0 && i < 16;
        long val = data[i / 4];
        int shift = (3 - (i & 3)) << 4;
        return (short) (val >>> shift);
    }

    public void setI16(int i, short val) {
        assert i >= 0 && i < 16;
        long old = data[i / 4];
        int shift = (3 - (i & 3)) << 4;
        long mask = ~(0xFFFFL << shift);
        long result = (old & mask) | ((Short.toUnsignedLong(val) & 0xFFFFL) << shift);
        data[i / 4] = result;
    }

    public byte getI8(int i) {
        assert i >= 0 && i < 32;
        long val = data[i / 8];
        int shift = (7 - (i & 7)) << 3;
        return (byte) (val >>> shift);
    }

    public Vector256 xor(Vector256 x) {
        long[] result = new long[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i] ^ x.data[i];
        }
        return new Vector256(result);
    }

    private static long eq(long x, long y, long mask) {
        if ((x & mask) == (y & mask)) {
            return mask;
        } else {
            return 0;
        }
    }

    public Vector256 eq8(Vector256 x) {
        long[] result = new long[data.length];
        for (int i = 0; i < data.length; i++) {
            long r = 0;
            r |= eq(data[i], x.data[i], 0xFF00000000000000L);
            r |= eq(data[i], x.data[i], 0x00FF000000000000L);
            r |= eq(data[i], x.data[i], 0x0000FF0000000000L);
            r |= eq(data[i], x.data[i], 0x000000FF00000000L);
            r |= eq(data[i], x.data[i], 0x00000000FF000000L);
            r |= eq(data[i], x.data[i], 0x0000000000FF0000L);
            r |= eq(data[i], x.data[i], 0x000000000000FF00L);
            r |= eq(data[i], x.data[i], 0x00000000000000FFL);
            result[i] = r;
        }
        return new Vector256(result);
    }

    public Vector256 eq16(Vector256 x) {
        long[] result = new long[data.length];
        for (int i = 0; i < data.length; i++) {
            long r = 0;
            r |= eq(data[i], x.data[i], 0xFFFF000000000000L);
            r |= eq(data[i], x.data[i], 0x0000FFFF00000000L);
            r |= eq(data[i], x.data[i], 0x00000000FFFF0000L);
            r |= eq(data[i], x.data[i], 0x000000000000FFFFL);
            result[i] = r;
        }
        return new Vector256(result);
    }

    public Vector256 eq32(Vector256 x) {
        long[] result = new long[data.length];
        for (int i = 0; i < data.length; i++) {
            long r = 0;
            r |= eq(data[i], x.data[i], 0xFFFFFFFF00000000L);
            r |= eq(data[i], x.data[i], 0x00000000FFFFFFFFL);
            result[i] = r;
        }
        return new Vector256(result);
    }

    @Override
    public Vector256 clone() {
        long[] value = Arrays.copyOf(data, data.length);
        return new Vector256(value);
    }

    @Override
    public String toString() {
        return String.format("0x%016x%016x%016x%016", data[0], data[1], data[2], data[3]);
    }
}
