package org.graalvm.vm.trcview.script.ast.expr;

import org.graalvm.vm.trcview.script.ast.Expression;
import org.graalvm.vm.trcview.script.rt.Context;

public class LogicAndNode extends Expression {
    private final Expression left;
    private final Expression right;

    public LogicAndNode(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public long execute(Context ctx) {
        return (left.execute(ctx) != 0) && (right.execute(ctx) != 0) ? 1 : 0;
    }
}
