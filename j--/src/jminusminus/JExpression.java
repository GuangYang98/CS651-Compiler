// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for an expression. The syntax says all expressions are
 * statements, but a semantic check throws some (those without a side-effect)
 * out.
 * <p>
 * Every expression has a type and a flag saying whether or not it's a
 * statement-expression.
 */

abstract class JExpression extends JStatement {

    /** Expression type. */
    protected Type type;

    /** Whether or not this expression is a statement. */
    protected boolean isStatementExpression;

    /**
     * Constructs an AST node for an expression given its line number.
     * 
     * @param line
     *            line in which the expression occurs in the source file.
     */

    protected JExpression(int line) {
        super(line);
        isStatementExpression = false; // by default
    }

    /**
     * Returns the expression type.
     * 
     * @return the expression type.
     */

    public Type type() {
        return type;
    }

    /**
     * Is this a statement expression?
     * 
     * @return {@code true} if this is being used as a statement; 
     *         {@code false} otherwise.
     */

    public boolean isStatementExpression() {
        return isStatementExpression;
    }

    /**
     * The analysis of any JExpression returns a JExpression. That's all this
     * (re-)declaration of {@code analyze} method says.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public abstract JExpression analyze(Context context);

    /**
     * Performs (short-circuit) code generation for a boolean expression, given
     * the code emitter, a target label, and whether we branch to that label on
     * true or on false.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     * @param targetLabel
     *            the label to which we should branch.
     * @param onTrue
     *            do we branch on true?
     */

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        // We should never reach here, i.e., all boolean
        // (including
        // identifier) expressions must override this method.
        System.err.println("Error in code generation");
    }

}

class JConditionalExpression extends JExpression {

    /** The binary operator. */
    protected String operator;

    /** The testExpression operand. */
    protected JExpression testExp;

    /** The trueExpression operand. */
    protected JExpression trueExp;

    /** The falseExpression operand. */
    protected JExpression falseExp;

    /**
     * Constructs an AST node for a binary expression given its line number, the
     * binary operator, and lhs and rhs operands.
     * 
     * @param line
     *            line in which the binary expression occurs in the source file.
     * @param operator
     *            the binary operator.
     * @param testExp
     *            the testExp operand.
     * @param trueExp
     *            the trueExp operand.
     *
     * @param falseExp
     *            the falseExp operand.
     */

    protected JConditionalExpression(int line, JExpression testExp, JExpression trueExp,
            JExpression falseExp) {
        super(line);
        this.operator = "?";
        this.testExp = testExp;
        this.trueExp = trueExp;
        this.falseExp = falseExp;
    }

    public JExpression analyze(Context context) {
        testExp = (JExpression) testExp.analyze(context);
        trueExp = (JExpression) trueExp.analyze(context);
        falseExp = (JExpression) falseExp.analyze(context);
        type = Type.INT;
        return this;
    }

    public void codegen(CLEmitter output) {
        String elseLabel = output.createLabel();
        String endIfLabel = output.createLabel();
        this.codegen(output, elseLabel, false);
        output.addNoArgInstruction(ICONST_1); // true
        output.addBranchInstruction(GOTO, endIfLabel);
        output.addLabel(elseLabel);
        output.addNoArgInstruction(ICONST_0); // false
        output.addLabel(endIfLabel);

        /*testExp.codegen(output);
        output.addNoArgInstruction(ICONST_1);
        output.addBranchInstruction(onTrue ? IF_ICMPEQ : IF_ICMPNE,
                    targetLabel);

        String falseLabel = output.createLabel();
        rhs.codegen(output);
        if (lhs.type().isReference()) {
            
        } else {
            output.addBranchInstruction(onTrue ? IF_ICMPEQ : IF_ICMPNE,
                    targetLabel);
        }*/
    }

    /**
     * {@inheritDoc}
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JConditionalExpression line=\"%d\" type=\"%s\" "
                + "operator=\"%s\">\n", line(), ((type == null) ? "" : type
                .toString()), Util.escapeSpecialXMLChars(operator));
        p.indentRight();
        p.printf("<TestExpression>\n");
        p.indentRight();
        testExp.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");
        p.printf("<TrueClause>\n");
        p.indentRight();
        trueExp.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TrueClause>\n");
        p.printf("<FalseClause>\n");
        p.indentRight();
        falseExp.writeToStdOut(p);
        p.indentLeft();
        p.printf("</FalseClause>\n");
        p.indentLeft();
        p.printf("</JConditionalExpression>\n");
    }

}