// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

/**
 * The AST node for a block, which delimits a nested level of scope.
 *
 * @see LocalContext
 */

class JSwitchBlockStatementGroup extends JStatement {

    /** List of statements forming the block body. */
    private ArrayList<JExpression> label;

    /** List of statements forming the block body. */
    private ArrayList<JStatement> body;

    /**
     * The new context (built in analyze()) represented by this block.
     */
    private LocalContext context;

    private boolean def;

    /**
     * Constructs an AST node for a block given its line number, and the list of
     * statements forming the block body.
     * 
     * @param line
     *            line in which the block occurs in the source file.
     * @param statements
     *            list of statements forming the block body.
     */

    public JSwitchBlockStatementGroup(int line, ArrayList<JExpression> label, ArrayList<JStatement> body, boolean def) {
        super(line);
        this.label = label;
        this.body = body;
        this.def = def;
    }

    /**
     * Returns the list of statements comprising the block.
     * 
     * @return list of statements.
     */

    public ArrayList<JStatement> body() {
        return body;
    }

    /**
     * Analyzing a block consists of creating a new nested context for that
     * block and analyzing each of its statements within that context.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JSwitchBlockStatementGroup analyze(Context context) {
        // { ... } defines a new level of scope.
        this.context = new LocalContext(context);

        for (int i = 0; i < label.size(); i++) {
            label.set(i, (JExpression) label.get(i).analyze(
                    this.context));
        }
        for (int i = 0; i < body.size(); i++) {
            body.set(i, (JStatement) body.get(i).analyze(
                    this.context));
        }
        return this;
    }

    /**
     * Generating code for a block consists of generating code for each of its
     * statements.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        /*for (JStatement statement : body) {
            statement.codegen(output);
        }*/
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<SwitchBlockStatementGroup>\n");
        p.indentRight();
        for (JExpression i : label) {
            p.printf("<CaseLabel>\n");
            p.indentRight();
            i.writeToStdOut(p);
            p.indentLeft();
            p.printf("</CaseLabel>\n");
        }
        if (def) {
            p.printf("<DefaultLabel/>\n");
        }
        for (JStatement statement : body) {
            p.printf("<Body>\n");
            p.indentRight();
            statement.writeToStdOut(p);
            p.indentLeft();
            p.printf("</Body>\n");
        }
        p.indentLeft();
        p.printf("</SwitchBlockStatementGroup>\n");
    }

}
