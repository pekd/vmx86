package org.graalvm.vm.trcview.analysis.type;

import org.graalvm.vm.trcview.expression.ast.Expression;

public class Type {
    private final boolean isConst;
    private final DataType type;
    private final Type pointee;
    private final Struct struct;
    private Representation representation;
    private Expression expr;

    public Type(DataType type) {
        this(type, false);
    }

    public Type(DataType type, boolean isConst) {
        this.type = type;
        this.pointee = null;
        this.struct = null;
        this.isConst = isConst;
        this.representation = getDefaultRepresentation();
    }

    public Type(Struct struct) {
        this(struct, false);
    }

    public Type(Struct struct, boolean isConst) {
        this.type = DataType.STRUCT;
        this.pointee = null;
        this.struct = struct;
        this.isConst = isConst;
        this.representation = getDefaultRepresentation();
    }

    public Type(Type type) {
        this(type, false);
    }

    public Type(Type type, boolean isConst) {
        this.type = DataType.PTR;
        this.pointee = type;
        this.struct = null;
        this.isConst = isConst;
        if (type.getType() == DataType.U8 || type.getType() == DataType.S8) {
            this.representation = Representation.STRING;
        } else {
            this.representation = getDefaultRepresentation();
        }
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

    public DataType getType() {
        return type;
    }

    public Struct getStruct() {
        assert type == DataType.STRUCT;
        return struct;
    }

    public Type getPointee() {
        assert type == DataType.PTR;
        return pointee;
    }

    public boolean isConst() {
        return isConst;
    }

    public int getSize() {
        switch (type) {
            case VOID:
                return 0;
            case STRING:
            case PTR:
                return 8;
            case U8:
            case S8:
                return 1;
            case U16:
            case S16:
                return 2;
            case U32:
            case S32:
                return 4;
            case U64:
            case S64:
                return 8;
            case STRUCT:
                return struct.getSize();
        }
        throw new AssertionError("this should be unreachable");
    }

    private Representation getDefaultRepresentation() {
        switch (type) {
            case PTR:
                return Representation.HEX;
            case STRING:
                return Representation.STRING;
            default:
                return Representation.DEC;
        }
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setExpression(Expression expr) {
        this.expr = expr;
    }

    public Expression getExpression() {
        return expr;
    }

    @Override
    public String toString() {
        String conststr = isConst ? "const " : "";
        String reprstr = "";
        String exprstr = expr == null ? "" : ("<" + expr + ">");
        if (representation != getDefaultRepresentation()) {
            switch (representation) {
                case DEC:
                    reprstr = " $dec";
                    break;
                case OCT:
                    reprstr = " $oct";
                    break;
                case HEX:
                    reprstr = " $hex";
                    break;
                case CHAR:
                    reprstr = " $char";
                    break;
            }
        } else if (type == DataType.PTR && pointee.getType() == DataType.S8 && representation == Representation.HEX) {
            // special handling for strings (char*) which were declared as $out
            reprstr = " $out";
        }
        switch (type) {
            case VOID:
                return "void";
            case STRING:
                return "char*" + exprstr;
            case U8:
                return conststr + "u8" + exprstr + reprstr;
            case S8:
                return conststr + "s8" + exprstr + reprstr;
            case U16:
                return conststr + "u16" + exprstr + reprstr;
            case S16:
                return conststr + "s16" + exprstr + reprstr;
            case U32:
                return conststr + "u32" + exprstr + reprstr;
            case S32:
                return conststr + "s32" + exprstr + reprstr;
            case U64:
                return conststr + "u64" + exprstr + reprstr;
            case S64:
                return conststr + "s64" + exprstr + reprstr;
            case PTR:
                if (isConst) {
                    return pointee.toString() + "* const" + exprstr + reprstr;
                } else {
                    return pointee.toString() + "*" + exprstr + reprstr;
                }
            default:
                return "/* unknown */";
        }
    }
}