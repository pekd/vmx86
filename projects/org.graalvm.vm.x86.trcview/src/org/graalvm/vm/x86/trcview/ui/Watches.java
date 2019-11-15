package org.graalvm.vm.x86.trcview.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.graalvm.vm.util.HexFormatter;
import org.graalvm.vm.x86.node.debug.trace.StepRecord;
import org.graalvm.vm.x86.trcview.analysis.memory.VirtualMemorySnapshot;
import org.graalvm.vm.x86.trcview.expression.EvaluationException;
import org.graalvm.vm.x86.trcview.expression.ExpressionContext;
import org.graalvm.vm.x86.trcview.expression.Parser;
import org.graalvm.vm.x86.trcview.expression.ast.Expression;
import org.graalvm.vm.x86.trcview.expression.ast.ValueNode;
import org.graalvm.vm.x86.trcview.net.TraceAnalyzer;

@SuppressWarnings("serial")
public class Watches extends JPanel {
    private List<String> watches;
    private List<Expression> expressions;
    private Model model;
    private TraceAnalyzer trc;
    private StepRecord step;
    private long insn;
    private Consumer<String> status;

    public Watches(Consumer<String> status) {
        super(new BorderLayout());
        this.status = status;
        watches = new ArrayList<>();
        expressions = new ArrayList<>();

        JTable table = new JTable(model = new Model());
        table.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        add(BorderLayout.CENTER, new JScrollPane(table));

        JPanel buttons = new JPanel(new FlowLayout());
        JButton add = new JButton("+");
        JButton remove = new JButton("-");
        add.addActionListener(e -> {
            watches.add("0");
            expressions.add(new ValueNode(0));
            model.changed();
            remove.setEnabled(true);
        });
        remove.addActionListener(e -> {
            if (watches.size() > 0) {
                int last = watches.size() - 1;
                watches.remove(last);
                expressions.remove(last);
                model.changed();
                if (last == 0) {
                    remove.setEnabled(false);
                }
            }
        });
        remove.setEnabled(false);
        buttons.add(add);
        buttons.add(remove);
        add(BorderLayout.SOUTH, buttons);
    }

    public void setTraceAnalyzer(TraceAnalyzer trc) {
        this.trc = trc;
        model.changed();
    }

    public void setStep(StepRecord step) {
        if (this.step == step) {
            return;
        }
        this.step = step;
        this.insn = step.getInstructionCount();
        model.changed();
    }

    private long eval(Expression expr) {
        if (expr == null) {
            return 0;
        } else if (step == null) {
            return 0;
        } else {
            VirtualMemorySnapshot mem = new VirtualMemorySnapshot(trc, insn);
            ExpressionContext ctx = new ExpressionContext(step, mem);
            try {
                return expr.evaluate(ctx);
            } catch (EvaluationException e) {
                status.accept("Error evaluating expression: " + e.getMessage());
                return 0;
            }
        }
    }

    @SuppressWarnings("serial")
    private class Model extends AbstractTableModel {
        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return watches.size();
        }

        @Override
        public Object getValueAt(int row, int column) {
            if (column == 0) {
                return watches.get(row);
            } else {
                Expression expr = expressions.get(row);
                long value = eval(expr);
                return "0x" + HexFormatter.tohex(value) + " [" + value + "]";
            }
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            watches.set(row, (String) value);
            try {
                Expression expr = new Parser((String) value).parse();
                expressions.set(row, expr);
                changed();
            } catch (ParseException e) {
                status.accept("Parse error: " + e.getMessage());
                expressions.set(row, null);
            }
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "Expression";
            } else {
                return "Value";
            }
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }

        public void changed() {
            fireTableDataChanged();
        }
    }
}
