package org.graalvm.vm.x86.el.ast;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LogicAndNode extends Expression {
    @Child private Expression left;
    @Child private Expression right;

    public LogicAndNode(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public long execute(VirtualFrame frame, long pc) {
        return left.execute(frame, pc) != 0 && right.execute(frame, pc) != 0 ? 1 : 0;
    }

    @Override
    public String toString() {
        CompilerAsserts.neverPartOfCompilation();
        return "(" + left + " && " + right + ")";
    }

    @Override
    public LogicAndNode clone() {
        CompilerAsserts.neverPartOfCompilation();
        return new LogicAndNode(left.clone(), right.clone());
    }
}
