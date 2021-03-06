package org.graalvm.vm.trcview.arch.pdp11.io;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.graalvm.vm.trcview.arch.io.InstructionType;
import org.graalvm.vm.trcview.arch.io.StepEvent;
import org.graalvm.vm.trcview.arch.io.StepFormat;
import org.graalvm.vm.trcview.arch.pdp11.PDP11;
import org.graalvm.vm.trcview.arch.pdp11.disasm.PDP11Disassembler;
import org.graalvm.vm.util.io.Endianess;

public abstract class PDP11StepEvent extends StepEvent {
    private byte[] machinecode = null;

    protected PDP11StepEvent(int tid) {
        super(tid);
    }

    @Override
    public byte[] getMachinecode() {
        if (machinecode != null) {
            return machinecode;
        }

        short[] code = getState().getMachinecodeWords();
        int length = PDP11Disassembler.getLength(code);
        machinecode = new byte[length * 2];
        for (int i = 0; i < length; i++) {
            Endianess.set16bitLE(machinecode, 2 * i, code[i]);
        }
        return machinecode;
    }

    @Override
    public String getDisassembly() {
        String[] code = getDisassemblyComponents();
        if (code.length == 1) {
            return code[0];
        } else {
            return code[0] + "\t" + Stream.of(code).skip(1).collect(Collectors.joining(",\t"));
        }
    }

    @Override
    public String[] getDisassemblyComponents() {
        return PDP11Disassembler.getDisassembly(getState().getMachinecodeWords(), (short) getPC());
    }

    @Override
    public String getMnemonic() {
        return getDisassemblyComponents()[0];
    }

    @Override
    public boolean isCall() {
        return getType() == InstructionType.CALL;
    }

    @Override
    public boolean isReturn() {
        return getType() == InstructionType.RET;
    }

    @Override
    public boolean isSyscall() {
        return getType() == InstructionType.SYSCALL;
    }

    @Override
    public boolean isReturnFromSyscall() {
        return getType() == InstructionType.RTI;
    }

    @Override
    public InstructionType getType() {
        return PDP11Disassembler.getType(getState().getMachinecodeWords()[0]);
    }

    @Override
    public abstract PDP11CpuState getState();

    @Override
    public StepFormat getFormat() {
        return PDP11.FORMAT;
    }

    @Override
    public String toString() {
        return String.format("%06o: %s ; step %d", getPC(), getDisassembly(), getStep());
    }
}
