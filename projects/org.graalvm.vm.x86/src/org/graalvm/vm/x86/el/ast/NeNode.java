package org.graalvm.vm.x86.el.ast;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;

public class NeNode extends Expression {
    @Child private Expression left;
    @Child private Expression right;

    public NeNode(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public long execute(VirtualFrame frame, long pc) {
        return left.execute(frame, pc) != execute(frame, pc) ? 1 : 0;
    }

    @Override
    public String toString() {
        CompilerAsserts.neverPartOfCompilation();
        return "(" + left + " != " + right + ")";
    }

    @Override
    public NeNode clone() {
        CompilerAsserts.neverPartOfCompilation();
        return new NeNode(left.clone(), right.clone());
    }
}
