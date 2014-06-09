//
// Copyright (C) 2008 United States Government as represented by the
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

package gov.nasa.jpf.vm;

import gov.nasa.jpf.jvm.bytecode.extended.Conditional;
import gov.nasa.jpf.jvm.bytecode.extended.One;
import gov.nasa.jpf.util.HashData;
import gov.nasa.jpf.util.IntVector;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * element values for int[] objects
 */
public class IntArrayFields extends ArrayFields {

  Conditional<Integer>[] values;

  public IntArrayFields (int length) {
    values = new Conditional[length];
  }

  public int[] asIntArray() {
	  int[] array = new int[values.length];
	  int i = 0;
	  for (Conditional<Integer> v : values) {
		  array[i++] = v.getValue();
	  }
	  return array;
	  
//    return values;
  }

  protected void printValue(PrintStream ps, int idx){
    ps.print(values[idx]);
  }
  
  public Object getValues(){
    return values;
  }

  public int arrayLength() {
    return values.length;
  }

  public int getHeapSize() {  // in bytes
    return values.length * 4;
  }

  public void appendTo (IntVector v) {
    v.append(asIntArray());
  }

  public IntArrayFields clone(){
    IntArrayFields f = (IntArrayFields)cloneFields();
    f.values = values.clone();
    return f;
  }

  public boolean equals (Object o) {
    if (o instanceof IntArrayFields) {
      IntArrayFields other = (IntArrayFields)o;

      int[] v = asIntArray();
      int[] vOther = other.asIntArray();
      if (v.length != vOther.length) {
        return false;
      }

      for (int i=0; i<v.length; i++) {
        if (v[i] != vOther[i]) {
          return false;
        }
      }

      return compareAttrs(other);

    } else {
      return false;
    }
  }

  public void setIntValue (int pos, Conditional<Integer> newValue) {
    values[pos] = newValue;
  }

  public Conditional<Integer> getIntValue (int pos) {
    return values[pos];
  }


  public void hash(HashData hd) {
    int[] v = asIntArray();
    for (int i=0; i < v.length; i++) {
      hd.add(v[i]);
    }
  }

}
