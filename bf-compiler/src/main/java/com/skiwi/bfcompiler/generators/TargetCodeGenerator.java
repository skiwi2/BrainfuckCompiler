package com.skiwi.bfcompiler.generators;

import com.skiwi.bfcompiler.ast.AST;
import com.skiwi.bfcompiler.ast.ASTNode;
import com.skiwi.bfcompiler.expression.Expression;
import com.skiwi.bfcompiler.expression.IntegerExpression;
import com.skiwi.bfcompiler.expression.RootExpression;
import com.skiwi.bfcompiler.expression.StringExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryInputExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryLoopExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryOutputExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryPointerChangeExpression;
import com.skiwi.bfcompiler.expression.intermediate.MemoryValueChangeExpression;
import com.skiwi.bfcompiler.expression.target.AddExpression;
import com.skiwi.bfcompiler.expression.target.BssSectionExpression;
import com.skiwi.bfcompiler.expression.target.CallExpression;
import com.skiwi.bfcompiler.expression.target.DataSectionExpression;
import com.skiwi.bfcompiler.expression.target.DefineByteExpression;
import com.skiwi.bfcompiler.expression.target.DwordExpression;
import com.skiwi.bfcompiler.expression.target.ExternExpression;
import com.skiwi.bfcompiler.expression.target.FunctionExpression;
import com.skiwi.bfcompiler.expression.target.GlobalExpression;
import com.skiwi.bfcompiler.expression.target.IdentifierExpression;
import com.skiwi.bfcompiler.expression.target.JmpExpression;
import com.skiwi.bfcompiler.expression.target.JnzExpression;
import com.skiwi.bfcompiler.expression.target.JzExpression;
import com.skiwi.bfcompiler.expression.target.LabelExpression;
import com.skiwi.bfcompiler.expression.target.MemoryAddressExpression;
import com.skiwi.bfcompiler.expression.target.MovExpression;
import com.skiwi.bfcompiler.expression.target.OperandExpression;
import com.skiwi.bfcompiler.expression.target.PushExpression;
import com.skiwi.bfcompiler.expression.target.Register;
import com.skiwi.bfcompiler.expression.target.RegisterExpression;
import com.skiwi.bfcompiler.expression.target.ReserveDoubleExpression;
import com.skiwi.bfcompiler.expression.target.JmpShortExpression;
import com.skiwi.bfcompiler.expression.target.RetExpression;
import com.skiwi.bfcompiler.expression.target.TestExpression;
import com.skiwi.bfcompiler.expression.target.TextSectionExpression;
import com.skiwi.bfcompiler.expression.target.ValueExpression;
import com.skiwi.bfcompiler.expression.target.ValueListExpression;
import com.skiwi.bfcompiler.options.BFOptions;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Frank van Heeswijk
 */
public class TargetCodeGenerator {
    private final BFOptions bfOptions;

    public TargetCodeGenerator(final BFOptions bfOptions) {
        this.bfOptions = bfOptions;
    }

    public AST generateAST(final AST intermediateAST) {
        int stderr = 2;
        int bfMemoryCellAmount = bfOptions.getMemoryCellAmount();

        ASTNode rootNode = new ASTNode(new RootExpression());
        AST ast = new AST(rootNode);

        // Header of the BF.exe program -do not modify-
        ASTNode externNode = new ASTNode(new ExternExpression());
        externNode.addChild(ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_calloc"))));
        externNode.addChild(ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_fdopen"))));
        externNode.addChild(ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_fprintf"))));
        externNode.addChild(ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_getchar"))));
        externNode.addChild(ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_putchar"))));
        rootNode.addChild(externNode);

        ASTNode dataSectionNode = new ASTNode(new DataSectionExpression());
        dataSectionNode.addChild(ASTNode.newWithChildren(new DefineByteExpression(), Arrays.asList(
            ASTNode.newWithChild(new IdentifierExpression(), new ASTNode(new StringExpression("write_mode"))),
            ASTNode.newWithChild(new ValueExpression(), ASTNode.newWithChildren(new ValueListExpression(), Arrays.asList(
                new ASTNode(new StringExpression("w")),
                new ASTNode(new IntegerExpression(0))
            )))
        )));
        dataSectionNode.addChild(ASTNode.newWithChildren(new DefineByteExpression(), Arrays.asList(
            ASTNode.newWithChild(new IdentifierExpression(), new ASTNode(new StringExpression("error_outofmemory"))),
            ASTNode.newWithChild(new ValueExpression(), ASTNode.newWithChildren(new ValueListExpression(), Arrays.asList(
                new ASTNode(new StringExpression("Fatal: The Operating System does not have enough memory available.")),
                new ASTNode(new IntegerExpression(0))
            )))
        )));
        rootNode.addChild(dataSectionNode);

        ASTNode bssSectionNode = new ASTNode(new BssSectionExpression());
        bssSectionNode.addChild(ASTNode.newWithChildren(new ReserveDoubleExpression(), Arrays.asList(
            ASTNode.newWithChild(new IdentifierExpression(), new ASTNode(new StringExpression("bf_memory"))),
            ASTNode.newWithChild(new ValueExpression(), new ASTNode(new IntegerExpression(1)))
        )));
        rootNode.addChild(bssSectionNode);

        ASTNode textSectionNode = new ASTNode(new TextSectionExpression());
        textSectionNode.addChild(ASTNode.newWithChild(new GlobalExpression(),
            ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("_main")))));
        textSectionNode.addChild(ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("_main"))));
        textSectionNode.addChild(ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EBP))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.ESP)))
        )));
        textSectionNode.addChild(ASTNode.newWithChild(new PushExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new DwordExpression(), new ASTNode(new IntegerExpression(1))))));
        textSectionNode.addChild(ASTNode.newWithChild(new PushExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new DwordExpression(), new ASTNode(new IntegerExpression(bfMemoryCellAmount))))));
        textSectionNode.addChild(ASTNode.newWithChild(new CallExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_calloc"))))));
        textSectionNode.addChild(ASTNode.newWithChildren(new AddExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.ESP))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new IntegerExpression(8)))
        )));
        textSectionNode.addChild(ASTNode.newWithChildren(new TestExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EAX))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EAX)))
        )));
        textSectionNode.addChild(ASTNode.newWithChild(new JzExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("error_exit_outofmemory"))))));
        textSectionNode.addChild(ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new MemoryAddressExpression(),
                    ASTNode.newWithChild(new IdentifierExpression(), new ASTNode(new StringExpression("bf_memory"))))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EAX)))
        )));
        textSectionNode.addChild(ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EDI))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EAX)))
        )));

        // Code Generated from the Intermediate AST
        convertIntermediateToTargetNodes(intermediateAST.getRoot(), "").forEach(textSectionNode::addChild);

        // Bottom of the BF.exe program -do not modify-
        textSectionNode.addChild(ASTNode.newWithChild(new JmpExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("normal_exit"))))));

        textSectionNode.addChild(ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("error_exit_outofmemory"))));
        textSectionNode.addChild(ASTNode.newWithChild(new PushExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new IdentifierExpression(), new ASTNode(new StringExpression("write_mode"))))));
        textSectionNode.addChild(ASTNode.newWithChild(new PushExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new DwordExpression(), new ASTNode(new IntegerExpression(stderr))))));
        textSectionNode.addChild(ASTNode.newWithChild(new CallExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_fdopen"))))));
        textSectionNode.addChild(ASTNode.newWithChildren(new AddExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.ESP))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new IntegerExpression(8)))
        )));
        textSectionNode.addChild(ASTNode.newWithChild(new PushExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new IdentifierExpression(), new ASTNode(new StringExpression("error_outofmemory"))))));
        textSectionNode.addChild(ASTNode.newWithChild(new PushExpression(),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EAX)))));
        textSectionNode.addChild(ASTNode.newWithChild(new CallExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_fprintf"))))));
        textSectionNode.addChild(ASTNode.newWithChildren(new AddExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.ESP))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new IntegerExpression(8)))
        )));
        textSectionNode.addChild(ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EAX))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new IntegerExpression(-1)))
        )));
        textSectionNode.addChild(ASTNode.newWithChild(new JmpShortExpression(),
            ASTNode.newWithChild(new OperandExpression(),
                ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("exit"))))));

        textSectionNode.addChild(ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("normal_exit"))));
        textSectionNode.addChild(ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EAX))),
            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new IntegerExpression(0)))
        )));

        textSectionNode.addChild(ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("exit"))));
        textSectionNode.addChild(new ASTNode(new RetExpression()));
        rootNode.addChild(textSectionNode);

        return ast;
    }

    private Stream<ASTNode> convertIntermediateToTargetNodes(final ASTNode node, final String uniqueIndex) {
        Expression expression = node.getExpression();
        if (expression instanceof RootExpression) {
            return IntStream.range(0, node.getChildren().size())
                .boxed()
                .flatMap(i -> convertIntermediateToTargetNodes(node.getChildren().get(i), uniqueIndex + "_" + i));
        }
        if (expression instanceof MemoryLoopExpression) {
            return Stream.concat(
                Stream.of(
                    ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
                        ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL))),
                        ASTNode.newWithChild(new OperandExpression(),
                            ASTNode.newWithChild(new MemoryAddressExpression(), new ASTNode(new RegisterExpression(Register.EDI))))
                    )),
                    ASTNode.newWithChildren(new TestExpression(), Arrays.asList(
                        ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL))),
                        ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL)))
                    )),
                    ASTNode.newWithChild(new JzExpression(),
                        ASTNode.newWithChild(new OperandExpression(),
                            ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("loop_end" + uniqueIndex))))),
                    ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("loop_start" + uniqueIndex)))
                ),
                Stream.concat(
                    IntStream.range(0, node.getChildren().size())
                        .boxed()
                        .flatMap(i -> convertIntermediateToTargetNodes(node.getChildren().get(i), uniqueIndex + "_" + i)),
                    Stream.of(
                        ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
                            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL))),
                            ASTNode.newWithChild(new OperandExpression(),
                                ASTNode.newWithChild(new MemoryAddressExpression(), new ASTNode(new RegisterExpression(Register.EDI))))
                        )),
                        ASTNode.newWithChildren(new TestExpression(), Arrays.asList(
                            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL))),
                            ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL)))
                        )),
                        ASTNode.newWithChild(new JnzExpression(),
                            ASTNode.newWithChild(new OperandExpression(),
                                ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("loop_start" + uniqueIndex))))),
                        ASTNode.newWithChild(new LabelExpression(), new ASTNode(new StringExpression("loop_end" + uniqueIndex)))
                    )
                )
            );
        }
        if (expression instanceof MemoryPointerChangeExpression) {
            int changeValue = node.getChildren().get(0).getExpression(IntegerExpression.class).getInteger();
            if (changeValue == 0) {
                return Stream.empty();
            }
            return Stream.of(
                ASTNode.newWithChildren(new AddExpression(), Arrays.asList(
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EDI))),
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new IntegerExpression(changeValue)))
                ))
            );
        }
        if (expression instanceof MemoryValueChangeExpression) {
            int changeValue = node.getChildren().get(0).getExpression(IntegerExpression.class).getInteger();
            if (changeValue == 0) {
                return Stream.empty();
            }
            return Stream.of(
                ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL))),
                    ASTNode.newWithChild(new OperandExpression(),
                        ASTNode.newWithChild(new MemoryAddressExpression(), new ASTNode(new RegisterExpression(Register.EDI))))
                )),
                ASTNode.newWithChildren(new AddExpression(), Arrays.asList(
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL))),
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new IntegerExpression(changeValue)))
                )),
                ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
                    ASTNode.newWithChild(new OperandExpression(),
                        ASTNode.newWithChild(new MemoryAddressExpression(), new ASTNode(new RegisterExpression(Register.EDI)))),
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL)))
                ))
            );
        }
        if (expression instanceof MemoryInputExpression) {
            return Stream.of(
                ASTNode.newWithChild(new CallExpression(),
                    ASTNode.newWithChild(new OperandExpression(),
                        ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_getchar"))))),
                ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
                    ASTNode.newWithChild(new OperandExpression(),
                        ASTNode.newWithChild(new MemoryAddressExpression(), new ASTNode(new RegisterExpression(Register.EDI)))),
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL)))
                ))
            );
        }
        if (expression instanceof MemoryOutputExpression) {
            return Stream.of(
                ASTNode.newWithChildren(new MovExpression(), Arrays.asList(
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.AL))),
                    ASTNode.newWithChild(new OperandExpression(),
                        ASTNode.newWithChild(new MemoryAddressExpression(), new ASTNode(new RegisterExpression(Register.EDI))))
                )),
                ASTNode.newWithChild(new PushExpression(),
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.EAX)))),
                ASTNode.newWithChild(new CallExpression(),
                    ASTNode.newWithChild(new OperandExpression(),
                        ASTNode.newWithChild(new FunctionExpression(), new ASTNode(new StringExpression("_putchar"))))),
                ASTNode.newWithChildren(new AddExpression(), Arrays.asList(
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new RegisterExpression(Register.ESP))),
                    ASTNode.newWithChild(new OperandExpression(), new ASTNode(new IntegerExpression(4)))
                ))
            );
        }
        throw new IllegalArgumentException("Node with unsupported expression type: " + expression);
    }
}
