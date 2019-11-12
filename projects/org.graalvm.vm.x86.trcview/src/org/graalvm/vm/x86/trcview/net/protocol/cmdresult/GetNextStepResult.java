package org.graalvm.vm.x86.trcview.net.protocol.cmdresult;

import java.io.IOException;

import org.graalvm.vm.util.io.WordInputStream;
import org.graalvm.vm.util.io.WordOutputStream;
import org.graalvm.vm.x86.trcview.io.Node;
import org.graalvm.vm.x86.trcview.net.protocol.IO;
import org.graalvm.vm.x86.trcview.net.protocol.cmd.Command;

public class GetNextStepResult extends Result {
    private Node node;

    public GetNextStepResult() {
        super(Command.GET_NEXT_STEP);
    }

    public GetNextStepResult(Node node) {
        super(Command.GET_NEXT_STEP);
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public void read(WordInputStream in) throws IOException {
        node = IO.readNode(in);
    }

    @Override
    public void write(WordOutputStream out) throws IOException {
        IO.writeNode(out, node);
    }
}