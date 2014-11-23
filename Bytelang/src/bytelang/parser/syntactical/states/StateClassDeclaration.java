package bytelang.parser.syntactical.states;

import static bytelang.parser.syntactical.Utils.next;

import java.util.ArrayList;
import java.util.LinkedList;

import bytelang.CompilationErrorException;
import bytelang.parser.container.TreeBuilder;
import bytelang.parser.container.code.SourceLineInstruction;
import bytelang.parser.container.code.SourceLineLabel;
import bytelang.parser.container.elements.ElementClass;
import bytelang.parser.container.elements.ElementField;
import bytelang.parser.container.elements.ElementMethod;
import bytelang.parser.container.values.ValueLabel;
import bytelang.parser.lexical.states.LexicalState;
import bytelang.parser.lexical.states.LexicalStateType;
import bytelang.parser.lexical.states.StateInteger;
import bytelang.parser.lexical.states.StateReference;
import bytelang.parser.lexical.states.StateWord;
import bytelang.parser.syntactical.keywords.InstructionType;
import bytelang.parser.syntactical.keywords.KeywordType;

public class StateClassDeclaration implements SyntacticalState {
	private TreeBuilder treeBuilder = new TreeBuilder();
	
	@Override
	public SyntacticalStateResult match(LinkedList<LexicalState> tokens) {
		ArrayList<KeywordType> modifiers   = new ArrayList<KeywordType>();
		String                 className   = "";
		KeywordType            classType   = null;
		String                 extended    = null;
		ArrayList<String>      implemented = new ArrayList<String>();
		
		while (next(tokens, "Expected class declaration.").getType() == LexicalStateType.WORD) {
			KeywordType keyword = KeywordType.findKeyword(((StateWord) tokens.getFirst()).getWord());
			
			if (keyword == null || !keyword.isClassModifier()) {
				break;
			}
			
			modifiers.add(keyword);
			tokens.removeFirst();
		}
		
		classType = KeywordType.findKeyword(((StateWord) next(tokens, LexicalStateType.WORD, "Class type expected in the class declaration.")).getWord());
		if (classType == null || !classType.isClassType()) {
			throw new CompilationErrorException("Class type expected in the class declaration.");
		}

		className = ((StateWord) next(tokens, LexicalStateType.WORD, "Class name expected in the class declaration.")).getWord();
		
		while (next(tokens, "Class body expected.").getType() != LexicalStateType.S_LBRACE) {
			StateWord   tmpWord = (StateWord) next(tokens, LexicalStateType.WORD, "Class body or interaces / super class expected.");
			KeywordType keyword = KeywordType.findKeyword(tmpWord.getWord());
			if (keyword == null) {
				throw new CompilationErrorException("Keyword \"implements\" or \"extends\" expected instead.");
			}

			switch (keyword) {
				case IMPLEMENTS: {
					if (implemented.size() > 0) {
						throw new CompilationErrorException("Keyword \"implements\" can be only typed once.");
					}
					
					boolean nextInterface = true;
					while (nextInterface) {
						nextInterface = false;
						
						boolean nextPart      = true;
						String  interfaceName = "";
						
						while (next(tokens, "").getType() == LexicalStateType.WORD && nextPart) {
							interfaceName += ((StateWord) tokens.removeFirst()).getWord();
							nextPart       = false;
							
							if (next(tokens, "").getType() == LexicalStateType.S_DOT) {
								tokens.removeFirst();
								interfaceName += ".";
								nextPart       = true;
							}
						}
						
						implemented.add(interfaceName);
						interfaceName = "";
						
						if (next(tokens, "").getType() == LexicalStateType.S_COMMA) {
							tokens.removeFirst();
							nextInterface = true;
						}
					}
					
					break;
				}
				
				case EXTENDS: {
					if (extended != null) {
						throw new CompilationErrorException("Keyword \"extends\" can be only typed once.");
					}
					
					boolean nextPart  = true;
					extended          = "";
					
					while (next(tokens, "").getType() == LexicalStateType.WORD && nextPart) {
						extended += ((StateWord) tokens.removeFirst()).getWord();
						nextPart  = false;
						
						if (next(tokens, "").getType() == LexicalStateType.S_DOT) {
							tokens.removeFirst();
							extended += ".";
							nextPart  = true;
						}
					}

					break;
				}

				default:
					throw new CompilationErrorException("Keyword \"implements\" or \"extends\" expected instead.");
			}
		}

		treeBuilder.setClass(new ElementClass(className, extended, implemented));

		modifiers.add(classType);
		treeBuilder.setClassModifiers(modifiers);
		
		next(tokens, LexicalStateType.S_LBRACE, "Opening brace expected in the class definition.");
		if (tokens.size() == 0 || tokens.getLast().getType() != LexicalStateType.S_RBRACE) {
			throw new CompilationErrorException("Source code should end with closing brace '{'.");
		}
		tokens.removeLast();
		
		parseContents(tokens);
		return new SyntacticalStateResult(tokens, null);
	}
	
	private void parseContents(LinkedList<LexicalState> tokens) {
		while (tokens.size() > 0) {
			switch (tokens.getFirst().getType()) {
				case S_AT: {
					StateAnnotation        annotation = new StateAnnotation();
					SyntacticalStateResult result     = annotation.match(tokens);
					
					if (result == null) {
						throw new CompilationErrorException("Invalid annotation found inside the class declaration.");
					}
					
					treeBuilder.addAnnotation(annotation.getElementAnnotation());
					break;
				}
					
				case WORD: {
					ArrayList<KeywordType> modifiers = new ArrayList<KeywordType>();
					String                 type      = null;
					String                 name      = null;
					
					while (tokens.size() > 0 && tokens.getFirst().getType() == LexicalStateType.WORD) {
						KeywordType keyword = KeywordType.findKeyword(((StateWord) tokens.getFirst()).getWord());
						
						if (keyword != null && (keyword.isFieldModifier() || keyword.isMethodModifier())) {
							modifiers.add(keyword);
							tokens.removeFirst();
						} else {
							break;
						}
					}
					
					type = parseTypeName(tokens);
					name = parseFieldOrMethodName(tokens);
					
					LexicalState next = next(tokens, new LexicalStateType[]{
						LexicalStateType.S_SEMICOLN, LexicalStateType.S_LPARENTHESIS
					}, "Semicoln expected for field declaration.");
					
					if (next.getType() == LexicalStateType.S_SEMICOLN) {
						ElementField elementField = new ElementField(name, type, modifiers);
						treeBuilder.addField(elementField);
					} else {
						ArrayList<String> parameters    = new ArrayList<String>();
						boolean           nextParameter = true;
						
						while (next(tokens, "Right parenthesis expected in the method declaration.").getType() != LexicalStateType.S_RPARENTHESIS && nextParameter) {
							nextParameter = false;
							parameters.add(parseTypeName(tokens));
							
							if (tokens.size() > 0 && tokens.getFirst().getType() == LexicalStateType.S_COMMA) {
								tokens.removeFirst();
								nextParameter = true;
							}
						}
						
						tokens.removeFirst();

						if (next(tokens, new LexicalStateType[]{
								LexicalStateType.S_SEMICOLN, LexicalStateType.S_LBRACE
						}, "Semicoln or method body expected.").getType() == LexicalStateType.S_LBRACE) {
							treeBuilder.addMethod(new ElementMethod(name, type, modifiers, parameters));
							parseCode(tokens);
						} else {
							treeBuilder.addMethod(new ElementMethod(name, type, modifiers, parameters));
						};
					}
					
					break;
				}
					
				default:
					throw new CompilationErrorException("Invalid token found inside the class declaration.");
			}
		}
	}
	
	@Override
	public SyntacticalStateType getType() {
		return SyntacticalStateType.CLASS_DECLARATION;
	}
	
	private String parseTypeName(LinkedList<LexicalState> tokens) {
		boolean      next    = true;
		String       result  = "";
		
		while (next(tokens, "Type name expected.").getType() == LexicalStateType.WORD && next) {
			next = false;
			
			result += ((StateWord) tokens.getFirst()).getWord();
			tokens.removeFirst();
			
			if (tokens.size() > 0 && tokens.getFirst().getType() == LexicalStateType.S_DOT) {
				next    = true;
				result += ".";
				tokens.removeFirst();
			} else if (tokens.size() > 0 && tokens.getFirst().getType() == LexicalStateType.S_LBRACKET) {
				tokens.removeFirst();
				next(tokens, LexicalStateType.S_RBRACKET, "Right bracket expected in the array parameter declaration.");
				result += "[]";
			}
		}
		
		if (result.endsWith(".")) {
			throw new CompilationErrorException("Invalid type name.");
		}
		
		return result;
	}
	
	private String parseFieldOrMethodName(LinkedList<LexicalState> tokens) {
		String result = "";
		
		if (tokens.size() > 0 && tokens.getFirst().getType() == LexicalStateType.S_LANGLEBRACKET) {
			result += "<";
			tokens.removeFirst();
		}
		
		result += ((StateWord) next(tokens, LexicalStateType.WORD, "Name of method or field expected.")).getWord();
		
		if (tokens.size() > 0 && tokens.getFirst().getType() == LexicalStateType.S_RANGLEBRACKET) {
			result += ">";
			tokens.removeFirst();
		}
		
		return result;
	}
	
	private void parseCode(LinkedList<LexicalState> tokens) {
		while (next(tokens, "Closing brace expected.").getType() != LexicalStateType.S_RBRACE) {
			LexicalState next = tokens.removeFirst();
			
			switch (next.getType()) {
				case WORD: {
					if (InstructionType.findInstruction(((StateWord) next).getWord()) != null) {
						treeBuilder.addSourceLine(new SourceLineInstruction(
							InstructionType.findInstruction(((StateWord) next).getWord())
						));
					} else {
						throw new CompilationErrorException("Invalid instruction (" + ((StateWord) next).getWord() + ") found in the method implementation.");
					}
				} break;
					
				case S_DOT: {
					StateWord labelName  = (StateWord) next(tokens, LexicalStateType.WORD, "Label name expected.");
					
					if (tokens.size() > 0 && tokens.getFirst().getType() == LexicalStateType.S_COLLON) {
						tokens.removeFirst();
						treeBuilder.addSourceLine(new SourceLineLabel(labelName.getWord()));
					} else {
						treeBuilder.addInstructionParameter(new ValueLabel(labelName.getWord()));
					}
				} break;
				
				case S_COMMA: {
					// nasleduje dalsi parametr
				} break;
				
				case REFERENCE: {
					treeBuilder.addInstructionParameter(((StateReference) next).toTypedValue());
				} break;
				
				case INTEGER: {
					treeBuilder.addInstructionParameter(((StateInteger) next).toTypedValue());
				} break;
					
				default:
					throw new CompilationErrorException("Invalid token (" + next.getType() + ") found in the method implementation.");
			}
		}
		
		next(tokens, LexicalStateType.S_RBRACE, "Closing brace expected.");
	}
	
	public ElementClass getElementClass() {
		return this.treeBuilder.getElementClass();
	}
}
