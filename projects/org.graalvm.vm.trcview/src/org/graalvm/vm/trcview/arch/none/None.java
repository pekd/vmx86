package org.graalvm.vm.trcview.arch.none;

import java.io.InputStream;

import org.graalvm.vm.posix.elf.Elf;
import org.graalvm.vm.trcview.arch.Architecture;
import org.graalvm.vm.trcview.arch.io.ArchTraceReader;
import org.graalvm.vm.trcview.arch.io.DefaultEventParser;
import org.graalvm.vm.trcview.arch.io.EventParser;
import org.graalvm.vm.trcview.arch.io.StepFormat;
import org.graalvm.vm.trcview.arch.none.decode.GenericCallDecoder;
import org.graalvm.vm.trcview.arch.none.decode.GenericSyscallDecoder;
import org.graalvm.vm.trcview.arch.none.io.GenericTraceReader;
import org.graalvm.vm.trcview.decode.CallDecoder;
import org.graalvm.vm.trcview.decode.SyscallDecoder;

public class None extends Architecture {
    public static final StepFormat FORMAT = new StepFormat(StepFormat.NUMBERFMT_HEX, 16, 16, 1, false);
    public static final short ID = Elf.EM_NONE;

    private static final EventParser DEFAULT_PARSER = new DefaultEventParser();

    @Override
    public short getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "none";
    }

    @Override
    public String getDescription() {
        return "Generic architecture independent traces";
    }

    @Override
    public ArchTraceReader getTraceReader(InputStream in) {
        return new GenericTraceReader(in);
    }

    @Override
    public EventParser getEventParser() {
        return DEFAULT_PARSER;
    }

    @Override
    public SyscallDecoder getSyscallDecoder() {
        return new GenericSyscallDecoder();
    }

    @Override
    public CallDecoder getCallDecoder() {
        return new GenericCallDecoder();
    }

    @Override
    public int getTabSize() {
        return 16;
    }

    @Override
    public StepFormat getFormat() {
        return FORMAT;
    }

    @Override
    public boolean isSystemLevel() {
        return false;
    }

    @Override
    public boolean isStackedTraps() {
        return false;
    }

    @Override
    public boolean isTaggedState() {
        return true;
    }
}
