//
// Copyright (C) 2006 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
// 
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
// 
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//
package gov.nasa.jpf.jvm.bytecode;

import gov.nasa.jpf.jvm.JVMInstruction;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;
import cmu.conditional.Conditional;
import cmu.conditional.One;
import cmu.utils.ComplexityPrinter;
import de.fosd.typechef.featureexpr.FeatureExpr;


/**
 * Push short
 * ... => ..., value
 */
public class SIPUSH extends JVMInstruction {
  protected int value;

  public SIPUSH(int value){
    this.value = value;
  }

  @Override
  public Conditional<Instruction> execute (FeatureExpr ctx, ThreadInfo ti) {
    StackFrame frame = ti.getModifiableTopFrame();
    
    frame.push(ctx, new One<>(value));
    ComplexityPrinter.addComplex(frame.stack.getStackWidth(), getClass().getSimpleName(), ctx, ti.getTopFrameMethodInfo());
    return getNext(ctx, ti);
  }

  public int getLength() {
    return 3; // opcode, b1, b2
  }
  
  @Override
  public int getByteCode () {
    return 0x11;
  }
  
  @Override
  public void accept(InstructionVisitor insVisitor) {
	  insVisitor.visit(this);
  }

  public int getValue() {
	return value;
  }
}
