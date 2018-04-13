package org.graalvm.vm.x86.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.graalvm.vm.x86.isa.AMD64Instruction;
import org.graalvm.vm.x86.isa.AMD64InstructionDecoder;
import org.graalvm.vm.x86.isa.CodeReader;
import org.graalvm.vm.x86.isa.instruction.Cvtdq2pd;
import org.graalvm.vm.x86.isa.test.CodeArrayReader;
import org.junit.Test;

public class CvtTest {
    public static final byte[] MACHINECODE1 = {(byte) 0xf3, 0x0f, (byte) 0xe6, (byte) 0xc1};
    public static final String ASSEMBLY1 = "cvtdq2pd\txmm0,xmm1";

    @Test
    public void test1() {
        CodeReader reader = new CodeArrayReader(MACHINECODE1, 0);
        AMD64Instruction insn = AMD64InstructionDecoder.decode(0, reader);
        assertNotNull(insn);
        assertTrue(insn instanceof Cvtdq2pd);
        assertEquals(ASSEMBLY1, insn.toString());
        assertEquals(MACHINECODE1.length, reader.getPC());
    }
}
