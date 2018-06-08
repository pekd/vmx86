package org.graalvm.vm.x86.isa.instruction;

import static org.graalvm.vm.x86.Options.getBoolean;

import org.graalvm.vm.x86.AMD64Register;
import org.graalvm.vm.x86.ArchitecturalState;
import org.graalvm.vm.x86.Options;
import org.graalvm.vm.x86.isa.AMD64Instruction;
import org.graalvm.vm.x86.isa.Register;
import org.graalvm.vm.x86.isa.RegisterOperand;
import org.graalvm.vm.x86.isa.ReturnException;
import org.graalvm.vm.x86.node.MemoryReadNode;
import org.graalvm.vm.x86.node.RegisterReadNode;
import org.graalvm.vm.x86.node.RegisterWriteNode;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;

public class Ret extends AMD64Instruction {
    @Child private RegisterReadNode readRSP;
    @Child private RegisterWriteNode writeRSP;
    @Child private MemoryReadNode readMemory;

    @CompilationFinal public static boolean TRUFFLE_CALLS = getBoolean(Options.TRUFFLE_CALLS);

    public Ret(long pc, byte[] instruction) {
        super(pc, instruction);

        setGPRReadOperands(new RegisterOperand(Register.RSP));
        setGPRWriteOperands(new RegisterOperand(Register.RSP));
    }

    private void createChildrenIfNecessary() {
        if (readRSP == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ArchitecturalState state = getContextReference().get().getState();
            AMD64Register rsp = state.getRegisters().getRegister(Register.RSP);
            readRSP = rsp.createRead();
            writeRSP = rsp.createWrite();
            readMemory = state.createMemoryRead();
        }
    }

    @Override
    public long executeInstruction(VirtualFrame frame) {
        createChildrenIfNecessary();

        long rsp = readRSP.executeI64(frame);
        long npc = readMemory.executeI64(rsp);
        writeRSP.executeI64(frame, rsp + 8);
        if (TRUFFLE_CALLS) {
            throw new ReturnException(npc);
        } else {
            return npc;
        }
    }

    @Override
    public boolean isControlFlow() {
        return true;
    }

    @Override
    protected String[] disassemble() {
        return new String[]{"ret"};
    }
}
