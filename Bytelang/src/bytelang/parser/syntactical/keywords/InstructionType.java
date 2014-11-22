package bytelang.parser.syntactical.keywords;

public enum InstructionType {
	AALOAD          (0x32, new int[]{            }),
	AASTORE         (0x53, new int[]{            }),
	ACONST_NULL     (0x01, new int[]{            }),
	ALOAD           (0x19, new int[]{  1         }),
	ALOAD_0         (0x2A, new int[]{            }),
	ALOAD_1         (0x2B, new int[]{            }),
	ALOAD_2         (0x2C, new int[]{            }),
	ALOAD_3         (0x2D, new int[]{            }),
	ANEWARRAY       (0xBD, new int[]{  2         }),
	ARETURN         (0xB0, new int[]{            }),
	ARRAYLENGTH     (0xBE, new int[]{            }),
	ASTORE          (0x3A, new int[]{  1         }),
	ASTORE_0        (0x4B, new int[]{            }),
	ASTORE_1        (0x4C, new int[]{            }),
	ASTORE_2        (0x4D, new int[]{            }),
	ASTORE_3        (0x4E, new int[]{            }),
	ATHROW          (0xBF, new int[]{            }),
	BALOAD          (0x33, new int[]{            }),
	BASTORE         (0x54, new int[]{            }),
	BIPUSH          (0x10, new int[]{ -1         }),
	CALOAD          (0x34, new int[]{            }),
	CASTORE         (0x55, new int[]{            }),
	CHECKCAST       (0xC0, new int[]{  2         }),
	D2F             (0x90, new int[]{            }),
	D2I             (0x8E, new int[]{            }),
	D2L             (0x8F, new int[]{            }),
	DADD            (0x63, new int[]{            }),
	DALOAD          (0x31, new int[]{            }),
	DASTORE         (0x52, new int[]{            }),
	DCMPG           (0x98, new int[]{            }),
	DCMPL           (0x97, new int[]{            }),
	DCONST_0        (0x0E, new int[]{            }),
	DCONST_1        (0x0F, new int[]{            }),
	DDIV            (0x6F, new int[]{            }),
	DLOAD           (0x18, new int[]{  1         }),
	DLOAD_0         (0x26, new int[]{            }),
	DLOAD_1         (0x27, new int[]{            }),
	DLOAD_2         (0x28, new int[]{            }),
	DLOAD_3         (0x29, new int[]{            }),
	DMUL            (0x6B, new int[]{            }),
	DNEG            (0x77, new int[]{            }),
	DREM            (0x73, new int[]{            }),
	DRETURN         (0xAF, new int[]{            }),
	DSTORE          (0x39, new int[]{  1         }),
	DSTORE_0        (0x47, new int[]{            }),
	DSTORE_1        (0x48, new int[]{            }),
	DSTORE_2        (0x49, new int[]{            }),
	DSTORE_3        (0x4A, new int[]{            }),
	DSUB            (0x67, new int[]{            }),
	DUP             (0x59, new int[]{            }),
	DUP_X1          (0x5A, new int[]{            }),
	DUP_X2          (0x5B, new int[]{            }),
	DUP2            (0x5C, new int[]{            }),
	DUP2_X1         (0x5D, new int[]{            }),
	DUP2_X2         (0x5E, new int[]{            }),
	F2D             (0x8D, new int[]{            }),
	F2I             (0x8B, new int[]{            }),
	F2L             (0x8C, new int[]{            }),
	FADD            (0x62, new int[]{            }),
	FALOAD          (0x30, new int[]{            }),
	FASTORE         (0x51, new int[]{            }),
	FCMPG           (0x96, new int[]{            }),
	FCMPL           (0x95, new int[]{            }),
	FCONST_0        (0x0B, new int[]{            }),
	FCONST_1        (0x0C, new int[]{            }),
	FCONST_2        (0x0D, new int[]{            }),
	FDIV            (0x6E, new int[]{            }),
	FLOAD           (0x17, new int[]{  1         }),
	FLOAD_0         (0x22, new int[]{            }),
	FLOAD_1         (0x23, new int[]{            }),
	FLOAD_2         (0x24, new int[]{            }),
	FLOAD_3         (0x25, new int[]{            }),
	FMUL            (0x6A, new int[]{            }),
	FNEG            (0x76, new int[]{            }),
	FREM            (0x72, new int[]{            }),
	FRETURN         (0xAE, new int[]{            }),
	FSTORE          (0x38, new int[]{  1         }),
	FSTORE_0        (0x43, new int[]{            }),
	FSTORE_1        (0x44, new int[]{            }),
	FSTORE_2        (0x45, new int[]{            }),
	FSTORE_3        (0x46, new int[]{            }),
	FSUB            (0x66, new int[]{            }),
	GETFIELD        (0xB4, new int[]{  2         }),
	GETSTATIC       (0xB2, new int[]{  2         }),
	GOTO            (0xA7, new int[]{ -2         }),
	GOTO_W          (0xC8, new int[]{ -4         }),
	I2B             (0x91, new int[]{            }),
	I2C             (0x92, new int[]{            }),
	I2D             (0x87, new int[]{            }),
	I2F             (0x86, new int[]{            }),
	I2L             (0x85, new int[]{            }),
	I2S             (0x93, new int[]{            }),
	IADD            (0x60, new int[]{            }),
	IALOAD          (0x2E, new int[]{            }),
	IAND            (0x7E, new int[]{            }),
	IASTORE         (0x4F, new int[]{            }),
	ICONST_M1       (0x02, new int[]{            }),
	ICONST_0        (0x03, new int[]{            }),
	ICONST_1        (0x04, new int[]{            }),
	ICONST_2        (0x05, new int[]{            }),
	ICONST_3        (0x06, new int[]{            }),
	ICONST_4        (0x07, new int[]{            }),
	ICONST_5        (0x08, new int[]{            }),
	IDIV            (0x6C, new int[]{            }),
	IF_ACMPEQ       (0xA5, new int[]{ -2         }),
	IF_ACMPNE       (0xA6, new int[]{ -2         }),
	IF_ICMPEQ       (0x9F, new int[]{ -2         }),
	IF_ICMPNE       (0xA0, new int[]{ -2         }),
	IF_ICMPLT       (0xA1, new int[]{ -2         }),
	IF_ICMPGE       (0xA2, new int[]{ -2         }),
	IF_ICMPGT       (0xA3, new int[]{ -2         }),
	IF_ICMPLE       (0xA4, new int[]{ -2         }),
	IFEQ            (0x99, new int[]{ -2         }),
	IFNE            (0x9A, new int[]{ -2         }),
	IFLT            (0x9B, new int[]{ -2         }),
	IFGE            (0x9C, new int[]{ -2         }),
	IFGT            (0x9D, new int[]{ -2         }),
	IFLE            (0x9E, new int[]{ -2         }),
	IFNONNULL       (0xC7, new int[]{ -2         }),
	IFNULL          (0xC6, new int[]{ -2         }),
	IINC            (0x84, new int[]{  1,  -1    }),
	ILOAD           (0x15, new int[]{  1         }),
	ILOAD_0         (0x1A, new int[]{            }),
	ILOAD_1         (0x1B, new int[]{            }),
	ILOAD_2         (0x1C, new int[]{            }),
	ILOAD_3         (0x1D, new int[]{            }),
	IMUL            (0x68, new int[]{            }),
	INEG            (0x74, new int[]{            }),
	INSTANCEOF      (0xC1, new int[]{  2         }),
	INVOKEDYNAMIC   (0xBA, new int[]{  2         }),
	INVOKEINTERFACE (0xB9, new int[]{  2,  1,  1 }),
	INVOKESPECIAL   (0xB7, new int[]{  2         }),
	INVOKESTATIC    (0xB8, new int[]{  2         }),
	INVOKEVIRTUAL   (0xB6, new int[]{  2         }),
	IOR             (0x80, new int[]{            }),
	IREM            (0x70, new int[]{            }),
	IRETURN         (0xAC, new int[]{            }),
	ISHL            (0x78, new int[]{            }),
	ISHR            (0x7A, new int[]{            }),
	ISTORE          (0x36, new int[]{ 1          }),
	ISTORE_0        (0x3B, new int[]{            }),
	ISTORE_1        (0x3C, new int[]{            }),
	ISTORE_2        (0x3D, new int[]{            }),
	ISTORE_3        (0x3E, new int[]{            }),
	ISUB            (0x64, new int[]{            }),
	IUSHR           (0x7C, new int[]{            }),
	IXOR            (0x82, new int[]{            }),
	JSR             (0xA8, new int[]{ 2          }),
	JSR_W           (0xC9, new int[]{ 4          }),
	L2D             (0x8A, new int[]{            }),
	L2F             (0x89, new int[]{            }),
	L2I             (0x88, new int[]{            }),
	LADD            (0x61, new int[]{            }),
	LALOAD          (0x2F, new int[]{            }),
	LAND            (0x7F, new int[]{            }),
	LASTORE         (0x50, new int[]{            }),
	LCMP            (0x94, new int[]{            }),
	LCONST_0        (0x09, new int[]{            }),
	LCONST_1        (0x0A, new int[]{            }),
	LDC             (0x12, new int[]{ 1          }),
	LDC_W           (0x13, new int[]{ 2          }),
	LDC2_W          (0x14, new int[]{ 2          }),
	LDIV            (0x6D, new int[]{            }),
	LLOAD           (0x16, new int[]{ 1          }),
	LLOAD_0         (0x1E, new int[]{            }),
	LLOAD_1         (0x1F, new int[]{            }),
	LLOAD_2         (0x20, new int[]{            }),
	LLOAD_3         (0x21, new int[]{            }),
	LMUL            (0x69, new int[]{            }),
	LNEG            (0x75, new int[]{            }),
	LOOKUPSWITCH    (0xAB, new int[]{            }),
	LOR             (0x81, new int[]{            }),
	LREM            (0x71, new int[]{            }),
	LRETURN         (0xAD, new int[]{            }),
	LSHL            (0x79, new int[]{            }),
	LSHR            (0x7B, new int[]{            }),
	LSTORE          (0x37, new int[]{ 1          }),
	LSTORE_0        (0x3F, new int[]{            }),
	LSTORE_1        (0x40, new int[]{            }),
	LSTORE_2        (0x41, new int[]{            }),
	LSTORE_3        (0x42, new int[]{            }),
	LSUB            (0x65, new int[]{            }),
	LUSHR           (0x7D, new int[]{            }),
	LXOR            (0x83, new int[]{            }),
	MONITORENTER    (0xC1, new int[]{            }),
	MONITOREXIT     (0xC3, new int[]{            }),
	MULTIANEWARRAY  (0xC5, new int[]{ 2, 1       }),
	NEW             (0xBB, new int[]{ 2          }),
	NEWARRAY        (0xBC, new int[]{ 1          }),
	NOP             (0x00, new int[]{            }),
	POP             (0x57, new int[]{            }),
	POP2            (0x58, new int[]{            }),
	PUTFIELD        (0xB5, new int[]{ 2          }),
	PUTSTATIC       (0xB3, new int[]{ 2          }),
	RET             (0xA9, new int[]{ 1          }),
	RETURN          (0xB1, new int[]{            }),
	SALOAD          (0x35, new int[]{            }),
	SASTORE         (0x56, new int[]{            }),
	SIPUSH          (0x11, new int[]{ -2         }),
	SWAP            (0x5F, new int[]{            }),
	TABLESWITCH     (0xAA, new int[]{            }),
	WIDE            (0xC4, new int[]{            });
	
	private short opcode;
	private int[] parameters;
	
	private InstructionType(int opcode, int[] parameters) {
		this.opcode     = (short) opcode;
		this.parameters = parameters;
	}

	public static InstructionType findInstruction(String name) {
		for (InstructionType instruction : InstructionType.values()) {
			if (instruction.toString().toLowerCase().equals(name)) {
				return instruction;
			}
		}
		
		return null;
	}
	
	public boolean isInstruction(String name) {
		return findInstruction(name) != null;
	}
	
	public short getOpcode() {
		return opcode;
	}
	
	public int[] getParameters() {
		return parameters;
	}
	
	public int getPrefferedOperandSize(int index) {
		if (index >= 0 && index < parameters.length) {
			return Math.abs(parameters[index]);
		} else {
			return -1;
		}
	}
	
	public boolean isUnsigned(int index) {
		if (index >= 0 && index < parameters.length) {
			return parameters[index] < 0;
		} else {
			throw new RuntimeException("Internal error: instruction doesn't have parameter with number " + index + ".");
		}
	}
}
