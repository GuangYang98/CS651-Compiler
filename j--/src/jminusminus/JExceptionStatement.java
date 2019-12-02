// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;
import java.util.ArrayList;

/**
 * The AST node for a while-statement.
 */

class JExceptionStatement extends JStatement {

    /** try block. */
    private JBlock tryBlock;

    /** catch parameters. */
    private ArrayList<JFormalParameter> parameters;

    /** catch blocks. */
    private ArrayList<JBlock> catchBlocks;

    /** final blocks. */
    private JBlock finalBlock;

    /**
     * Constructs an AST node for a while-statement given its line number, the
     * test expression, and the body.
     * 
     * @param line
     *            line in which the while-statement occurs in the source file.
     * @param condition
     *            test expression.
     * @param body
     *            the body.
     */

    public JExceptionStatement(int line, JBlock tryBlock, ArrayList<JFormalParameter> parameters,
        ArrayList<JBlock> catchBlocks, JBlock finalBlock) {
        super(line);
        this.tryBlock = tryBlock;
        this.parameters = parameters;
        this.catchBlocks = catchBlocks;
        this.finalBlock = finalBlock;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExceptionStatement analyze(Context context) {
        tryBlock = (JBlock) tryBlock.analyze(context);
        for (JFormalParameter parameter : parameters) {
            parameter = (JFormalParameter) parameter.analyze(context);
        }
        for (JBlock block : catchBlocks) {
            block = (JBlock) block.analyze(context);
        }
        if (finalBlock != null)
            finalBlock = (JBlock) finalBlock.analyze(context);
        return this;
    }

    /**
     * Generates code for the while loop.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        /*// Need two labels
        String test = output.createLabel();
        String out = output.createLabel();

        // Branch out of the loop on the test condition
        // being false
        output.addLabel(test);
        condition.codegen(output, out, false);

        // Codegen body
        body.codegen(output);

        // Unconditional jump back up to test
        output.addBranchInstruction(GOTO, test);

        // The label below and outside the loop
        output.addLabel(out);*/
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JTryCatchFinallyStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<TryBlock>\n");
        p.indentRight();
        tryBlock.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TryBlock>\n");
        for (int i = 0; i < catchBlocks.size(); i++) {
            p.printf("<CatchBlock>\n");
            p.indentRight();
            parameters.get(i).writeToStdOut(p);
            catchBlocks.get(i).writeToStdOut(p);
            p.indentLeft();
            p.printf("</CatchBlock>\n");
        }
        if (finalBlock != null) {
            p.printf("<FinallyBlock>\n");
            p.indentRight();
            finalBlock.writeToStdOut(p);
            p.indentLeft();
            p.printf("</FinallyBlock>\n");
        }
        p.indentLeft();
        p.printf("</JTryCatchFinallyStatement>\n");
    }

}
