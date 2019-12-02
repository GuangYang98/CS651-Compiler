// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;
import java.util.ArrayList;

/**
 * The AST node for a for-statement.
 */

class JForStatement extends JStatement {

    /** forInit expression. */
    private ArrayList<JStatement> forInit;

    /** forInitDecl expression. */
    private JVariableDeclaration forInitDecl;

    /** condition. */
    private JExpression condition;

    /** forUpdate expression. */
    private ArrayList<JStatement> forUpdate;

    /** body statement. */
    private JStatement body;

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

    public JForStatement(int line, ArrayList<JStatement> forInit, JVariableDeclaration forInitDecl, 
        JExpression condition, ArrayList<JStatement> forUpdate, JStatement body) {
        super(line);
        this.forInit = forInit;
        this.forInitDecl = forInitDecl;
        this.condition = condition;
        this.forUpdate = forUpdate;
        this.body = body;
    }

    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JForStatement analyze(Context context) {
        for (JStatement statement : forInit) {
            statement = (JStatement) statement.analyze(context);
        }
        if (forInitDecl != null) {
            forInitDecl.analyze(context);
        }
        condition = condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        for (JStatement statement : forUpdate) {
            statement = (JStatement) statement.analyze(context);
        }
        body = (JStatement) body.analyze(context);
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
        p.printf("<JForStatement line=\"%d\">\n", line());
        p.indentRight();
        if (forInitDecl != null || forInit.size() > 0) {
            p.printf("<InitialExpression>\n");
            p.indentRight();
            for (JStatement statement : forInit) {
                statement.writeToStdOut(p);
            }
            if (forInitDecl != null) {
                forInitDecl.writeToStdOut(p);
            }
            p.indentLeft();
            p.printf("</InitialExpression>\n");
        }
        p.printf("<TestExpression>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");
        p.printf("<UpdateExpression>\n");
        p.indentRight();
        for (JStatement statement : forUpdate) {
            statement.writeToStdOut(p);
        }
        p.indentLeft();
        p.printf("</UpdateExpression>\n");
        p.printf("<Statement>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Statement>\n");
        p.indentLeft();
        p.printf("</JForStatement>\n");
    }

}
