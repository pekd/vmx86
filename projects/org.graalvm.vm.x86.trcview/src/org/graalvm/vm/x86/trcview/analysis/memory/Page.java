package org.graalvm.vm.x86.trcview.analysis.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.graalvm.vm.util.io.Endianess;
import org.graalvm.vm.x86.trcview.io.Node;

public class Page {
    private final long firstPC;
    private final long firstInstructionCount;
    private final Node firstNode;

    private final long address;
    private final byte[] data = new byte[4096]; // initial data
    private final List<MemoryUpdate> updates = new ArrayList<>();

    public Page(long address, long pc, long instructionCount, Node node) {
        this.address = address;
        this.firstPC = pc;
        this.firstInstructionCount = instructionCount;
        this.firstNode = node;
    }

    public Page(long address, byte[] data, long pc, long instructionCount, Node node) {
        this(address, pc, instructionCount, node);
        assert data.length == 4096;
        System.arraycopy(data, 0, this.data, 0, 4096);
    }

    public long getAddress() {
        return address;
    }

    public byte[] getData() {
        return data;
    }

    public void addUpdate(long addr, byte size, long value, long pc, long instructionCount, Node node) {
        assert addr >= address && addr < (address + data.length);
        assert addr + size <= (address + data.length);
        updates.add(new MemoryUpdate(addr, size, value, pc, instructionCount, node));
    }

    public void clear(long pc, long instructionCount, Node node) {
        for (int i = 0; i < 4096; i += 8) {
            addUpdate(address + i, (byte) 8, 0, pc, instructionCount, node);
        }
    }

    public void overwrite(byte[] update, long pc, long instructionCount, Node node) {
        for (int i = 0; i < 4096; i += 8) {
            long value = Endianess.get64bitLE(update, i);
            addUpdate(address + i, (byte) 8, value, pc, instructionCount, node);
        }
    }

    public byte getByte(long addr, long instructionCount) throws MemoryNotMappedException {
        if (addr < address || addr >= address + 4096) {
            throw new AssertionError(String.format("wrong page for address 0x%x", addr));
        }

        if (updates.isEmpty() || instructionCount == firstInstructionCount) {
            return data[(int) (addr - address)];
        } else if (instructionCount < firstInstructionCount) {
            throw new MemoryNotMappedException("memory is not mapped at this time");
        }

        // find update timestamp
        MemoryUpdate target = new MemoryUpdate(addr, (byte) 1, 0, 0, instructionCount, null);
        int idx = Collections.binarySearch(updates, target, (a, b) -> {
            return Long.compareUnsigned(a.instructionCount, b.instructionCount);
        });

        if (idx > 0) {
            // timestamp found: search first update with matching timestamp
            while (idx > 0 && updates.get(idx).instructionCount == instructionCount) {
                idx--;
            }

            assert idx >= 0;
            assert updates.get(idx).instructionCount < instructionCount;

            // check updates with same timestamp
            for (int i = idx; i < updates.size(); i++) {
                MemoryUpdate update = updates.get(i);
                if (update.instructionCount > instructionCount) {
                    break;
                }
                if (update.contains(addr)) {
                    return update.getByte(addr);
                }
            }
        } else {
            // no match: points to next update after the timestamp we're looking for
            idx = ~idx - 1;
            if (idx < 0) {
                idx = 0;
            }
        }

        assert idx >= 0;
        assert updates.get(idx).instructionCount < instructionCount;

        // go backwards in time
        for (int i = idx; i >= 0; i--) {
            MemoryUpdate update = updates.get(i);
            if (update.contains(addr)) {
                return update.getByte(addr);
            }
        }

        // no update found
        return data[(int) (addr - address)];
    }

    public long getInitialPC() {
        return firstPC;
    }

    public long getInitialInstruction() {
        return firstInstructionCount;
    }

    public Node getInitialNode() {
        return firstNode;
    }
}