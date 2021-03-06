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
package java.io;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gov.nasa.jpf.annotation.FilterField;


/**
 * MJI model class for java.io.File
 *
 * NOTE - a number of methods are only stubbed out here to make Eclipse compile
 * JPF code that uses java.io.File (there is no way to tell Eclipse to exclude the
 * model classes from ths build-path)
 *
 * @author Owen O'Malley
 */
public class File
{
  public static final String separator = System.getProperty("file.separator");
  public static final char separatorChar = separator.charAt(0);
  public static final String pathSeparator = System.getProperty("path.separator");
  public static final char pathSeparatorChar = pathSeparator.charAt(0);

  @FilterField int id; // link to the real File object
  private String path;

  public File(String path) {
    if (path == null){
      throw new NullPointerException();
    }
    
    this.path = path;
  }

  public File (String parent, String child) {
  	path = parent + separator + child;
  }
  
  public File (File parent, String child) {
    path = parent.path + separator + child;
  }
  
  public File(java.net.URI uri) { throw new UnsupportedOperationException(); }
  
  public String getName() {
    int idx = path.lastIndexOf(separatorChar);
    if (idx >= 0){
      return path.substring(idx+1);
    } else {
      return path;
    }
  }

  public String getParent() {
    int idx = path.lastIndexOf(separatorChar);
    if (idx >= 0){
      return path.substring(0,idx);
    } else {
      return null;
    }
  }
  
  public int compareTo(File that) {
    return this.path.compareTo(that.path);
  }
  
  public boolean equals(Object o) {
    if (o instanceof File){
      File otherFile = (File) o;
      return path.equals(otherFile.path);
    } else {
      return false;
    }
  }
  
  public int hashCode() {
    return path.hashCode();
  }
  
  public String toString()  {
    return path;
  }
  
  
  //--- native peer intercepted (hopefully)
  
  int getPrefixLength() { return 0; }
  public native File getParentFile();
  
  public String getPath() {
    return path;
  }

  public native boolean isAbsolute();
  public native String getAbsolutePath();
  public native File getAbsoluteFile();
  public native String getCanonicalPath() throws java.io.IOException;

  public native File getCanonicalFile() throws java.io.IOException;

  private native String getURLSpec();
  public java.net.URL toURL() throws java.net.MalformedURLException {
    return new URL(getURLSpec());
  }

  private native String getURISpec();
  public java.net.URI toURI() {
    try {
      return new URI(getURISpec());
    } catch (URISyntaxException x){
      return null;
    }
  }

  public native boolean canRead();
  public native boolean canWrite();
  public native boolean exists();
  public boolean isDirectory() { return false; }
  public boolean isFile() { return false; }
  public boolean isHidden() { return false; }
  public long lastModified() { return -1L; }
  public long length() { return -1; }
  public native boolean createNewFile() throws java.io.IOException;
  public native boolean delete(); 
  public void deleteOnExit() {}
  public String[] list()  { return null; }
  public String[] list(FilenameFilter fnf)  { return null; }
//  public File[] listFiles()  { return null; }
  public native File[] listFiles();
//  public File[] listFiles(FilenameFilter fnf)  { return null; }
  public native File[] listFiles(FilenameFilter fnf);
//  public File[] listFiles(FileFilter ff)  { return null; }
  public File[] listFiles(FileFilter ff) {
	  File[] files = listFiles();
	  List<Object> matchingFiles = new ArrayList<>();
	  for (File f: files) {
		  if (ff.accept(f)) {
			matchingFiles.add(f);  
		  }
	  }
	  return matchingFiles.toArray(new File[matchingFiles.size()]);
  }
//  public boolean mkdir()  { return false; }
  public native boolean mkdir();
//  public boolean mkdirs() { return false; }
  public native boolean mkdirs();
//  public boolean renameTo(File f)  { return false; }
  public native boolean renameTo(File f);
  public boolean setLastModified(long t)  { return false; }
  public boolean setReadOnly()  { return false; }
  
  public static native File[] listRoots();
  
  public static File createTempFile(String prefix, String suffix, File dir) throws IOException  {
    if (prefix == null){
      throw new NullPointerException();
    }
    
    String tmpDir;
    if (dir == null){
      tmpDir = System.getProperty("java.io.tmpdir");
      if (tmpDir == null){
        tmpDir = ".";
      }
      if (tmpDir.charAt(tmpDir.length()-1) != separatorChar){
        tmpDir += separatorChar;
      }
      
      if (suffix == null){
        suffix = ".tmp";
      }
    } else {
      tmpDir = dir.getPath();
    }
    
    return new File(tmpDir + prefix + suffix);
  }
  
  public static File createTempFile(String prefix, String suffix) throws IOException  {
    return createTempFile(prefix, suffix, null);
  }
}
