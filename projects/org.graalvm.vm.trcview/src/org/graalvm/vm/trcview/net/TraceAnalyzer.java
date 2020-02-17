package org.graalvm.vm.trcview.net;

import java.awt.Color;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.graalvm.vm.posix.elf.Symbol;
import org.graalvm.vm.trcview.analysis.ComputedSymbol;
import org.graalvm.vm.trcview.analysis.SymbolRenameListener;
import org.graalvm.vm.trcview.analysis.memory.MemoryNotMappedException;
import org.graalvm.vm.trcview.analysis.memory.MemoryRead;
import org.graalvm.vm.trcview.analysis.memory.MemoryUpdate;
import org.graalvm.vm.trcview.analysis.type.Prototype;
import org.graalvm.vm.trcview.arch.Architecture;
import org.graalvm.vm.trcview.arch.io.CpuState;
import org.graalvm.vm.trcview.expression.EvaluationException;
import org.graalvm.vm.trcview.io.BlockNode;
import org.graalvm.vm.trcview.io.Node;
import org.graalvm.vm.trcview.ui.event.ChangeListener;

public interface TraceAnalyzer {
    // symbol
    Symbol getSymbol(long pc);

    ComputedSymbol getComputedSymbol(long pc);

    void renameSymbol(ComputedSymbol sym, String name);

    void setPrototype(ComputedSymbol sym, Prototype prototype);

    Set<ComputedSymbol> getSubroutines();

    Set<ComputedSymbol> getLocations();

    Collection<ComputedSymbol> getSymbols();

    Map<String, List<ComputedSymbol>> getNamedSymbols();

    void addSymbolRenameListener(SymbolRenameListener listener);

    void addSymbolChangeListener(ChangeListener listener);

    void addSubroutine(long pc, String name, Prototype prototype);

    void reanalyze();

    void refresh();

    // misc
    long getInstructionCount();

    // node
    BlockNode getRoot();

    BlockNode getParent(Node node);

    BlockNode getChildren(BlockNode node);

    Node getNode(Node node);

    // search
    Node getInstruction(long insn);

    Node getNextStep(Node node);

    Node getPreviousStep(Node node);

    Node getNextPC(Node node, long pc);

    // memory
    byte getI8(long address, long insn) throws MemoryNotMappedException;

    long getI64(long address, long insn) throws MemoryNotMappedException;

    MemoryRead getLastRead(long address, long insn) throws MemoryNotMappedException;

    MemoryRead getNextRead(long address, long insn) throws MemoryNotMappedException;

    MemoryUpdate getLastWrite(long address, long insn) throws MemoryNotMappedException;

    MemoryUpdate getNextWrite(long address, long insn) throws MemoryNotMappedException;

    List<MemoryUpdate> getPreviousWrites(long address, long insn, long count) throws MemoryNotMappedException;

    Node getMapNode(long address, long insn) throws MemoryNotMappedException;

    // files
    long getBase(long pc);

    long getLoadBias(long pc);

    long getOffset(long pc);

    long getFileOffset(long pc);

    String getFilename(long pc);

    Architecture getArchitecture();

    void addCommentChangeListener(ChangeListener l);

    void setCommentForPC(long pc, String comment);

    String getCommentForPC(long pc);

    void setCommentForInsn(long insn, String comment);

    String getCommentForInsn(long insn);

    Map<Long, String> getCommentsForInsns();

    Map<Long, String> getCommentsForPCs();

    void setExpression(long pc, String expression) throws ParseException;

    String getExpression(long pc);

    String evaluateExpression(CpuState state) throws EvaluationException;

    Map<Long, String> getExpressions();

    void setColor(long pc, Color color);

    Color getColor(CpuState state);

    Map<Long, Color> getColors();
}
