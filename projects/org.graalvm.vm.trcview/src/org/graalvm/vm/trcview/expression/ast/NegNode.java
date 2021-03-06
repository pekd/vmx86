package org.graalvm.vm.trcview.expression.ast;

import java.util.Map;

import org.graalvm.vm.trcview.expression.EvaluationException;
import org.graalvm.vm.trcview.expression.ExpressionContext;

public class NegNode extends Expression {
    public final Expression child;

    public NegNode(Expression child) {
        this.child = child;
    }

    @Override
    public long evaluate(ExpressionContext ctx) throws EvaluationException {
        return -child.evaluate(ctx);
    }

    @Override
    public Expression materialize(Map<String, Long> vars) {
        Expression c = child.materialize(vars);
        if (c != child) {
            return new NegNode(c);
        } else {
            return c;
        }
    }

    @Override
    protected String str(boolean par) {
        return "-" + child.str(true);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof NegNode)) {
            return false;
        }
        NegNode n = (NegNode) o;
        return child.equals(n.child);
    }

    @Override
    public int hashCode() {
        return child.hashCode();
    }
}
