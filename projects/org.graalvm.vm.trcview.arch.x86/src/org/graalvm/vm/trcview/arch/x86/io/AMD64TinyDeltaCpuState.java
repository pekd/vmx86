package org.graalvm.vm.trcview.arch.x86.io;

import org.graalvm.vm.memory.vector.Vector128;
import org.graalvm.vm.x86.node.debug.trace.StepRecord;

public class AMD64TinyDeltaCpuState extends AMD64CpuState {
    private final long rip;
    private final int efl;

    private AMD64CpuState previous;

    public AMD64TinyDeltaCpuState(AMD64CpuState previous, StepRecord current) {
        super(current.getTid(), current.getMachinecode());
        this.previous = previous;
        org.graalvm.vm.x86.isa.CpuState state = current.getState().getState();
        step = current.getInstructionCount();
        rip = current.getPC();
        efl = (int) state.getRFL();
    }

    @Override
    public long getRAX() {
        return previous.getRAX();
    }

    @Override
    public long getRBX() {
        return previous.getRBX();
    }

    @Override
    public long getRCX() {
        return previous.getRCX();
    }

    @Override
    public long getRDX() {
        return previous.getRDX();
    }

    @Override
    public long getRBP() {
        return previous.getRBP();
    }

    @Override
    public long getRSP() {
        return previous.getRSP();
    }

    @Override
    public long getRIP() {
        return rip;
    }

    @Override
    public long getRSI() {
        return previous.getRSI();
    }

    @Override
    public long getRDI() {
        return previous.getRDI();
    }

    @Override
    public long getR8() {
        return previous.getR8();
    }

    @Override
    public long getR9() {
        return previous.getR9();
    }

    @Override
    public long getR10() {
        return previous.getR10();
    }

    @Override
    public long getR11() {
        return previous.getR11();
    }

    @Override
    public long getR12() {
        return previous.getR12();
    }

    @Override
    public long getR13() {
        return previous.getR13();
    }

    @Override
    public long getR14() {
        return previous.getR14();
    }

    @Override
    public long getR15() {
        return previous.getR15();
    }

    @Override
    public long getRFL() {
        return efl;
    }

    @Override
    public long getFS() {
        return previous.getFS();
    }

    @Override
    public long getGS() {
        return previous.getGS();
    }

    @Override
    public Vector128 getXMM(int i) {
        return previous.getXMM(i);
    }
}
