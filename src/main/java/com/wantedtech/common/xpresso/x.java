/*
 * Copyright (c) 2015 Wanted Technologies, The Xpresso Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wantedtech.common.xpresso;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.lang.Iterable;
import java.lang.Number;

import com.wantedtech.common.xpresso.helpers.*;
import com.wantedtech.common.xpresso.json.*;
import com.wantedtech.common.xpresso.strings.*;
import com.wantedtech.common.xpresso.types.*;
import com.wantedtech.common.xpresso.comprehension.*;
import com.wantedtech.common.xpresso.csv.*;
import com.wantedtech.common.xpresso.functional.*;
import com.wantedtech.common.xpresso.functional.lambda.*;
import com.wantedtech.common.xpresso.regex.*;
import com.wantedtech.common.xpresso.time.*;
import com.wantedtech.common.xpresso.types.strs.*;
import com.wantedtech.common.xpresso.types.tuples.*;
import com.wantedtech.common.xpresso.token.*;

/**
 * This is the entry point of xpresso. x contains all important high-level static utility methods of the library. It is recommended to start exploring the Javadoc from here. The class x can be seen as a container of the highest-level methods of xpresso, similar to Python's __builtin__ namespace.
 * 
 * @author Andriy Burkov
 * @since 0.01
 */

public class x {
	
	/**
	 * Contains an instance of {@link Assert} class object that has useful assertion methods.
	 */
	public static Assert Assert = new Assert();
	
	/**
	 * Gets the Assert class object and calls its True method.
	 * 
	 * @param expression	the expression to assert true
	 */
	public static void assertTrue(boolean expression){
		Assert.True(expression);
	}
	
	/**
	 * Gets the Assert class object and calls its True method.
	 * 
	 * @param expression	the expression to assert true
	 * @param message		the message to print in case of false
	 */
	public static void assertTrue(boolean expression, String message){
		Assert.True(expression, message);
	}
	
	/**
	 * Gets the Assert class object object and calls its notNull method.
	 * 
	 * @param <T>			input type
	 * @param expression	the expression to assert not null
	 */
	public static <T> void assertNotNull(T expression){
		Assert.notNull(expression);
	}
	
	/**
	 * Gets the Assert class object and calls its notNull method.
	 * 
	 * @param <T>			input type
	 * @param expression	the expression to assert not null
	 * @param message		the message to print in case of null
	 */
	public static <T> void assertNotNull(T expression, String message){
		Assert.notNull(expression, message);
	}
	
	/**
	 * Gets the Assert class object and calls its notEmpty method.
	 * 
	 * @param <T>			input type
	 * @param expression	the expression to assert  not empty
	 */
	public static <T> void assertNotEmpty(T expression){
		Assert.notEmpty(expression);
	}
	
	/**
	 * Gets the Assert class object and calls its notEmpty method.
	 * 
	 * @param <T>			input type
	 * @param expression	the expression to assert  not empty
	 * @param message		the message to print in case of empty
	 */
	public static <T> void assertNotEmpty(T expression, String message){
		Assert.notEmpty(expression, message);
	}
	
	/*
	public static <T> Generator0<T> generate(Class<? extends Generator0<T>> generator, Object... params) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		x.assertNotNull(generator);
		boolean foundGenerateMethod = false;
		for (Method m : generator.getMethods()){
			if (m.getName().equals("generator")) {
				foundGenerateMethod = true;
				x.assertTrue(x.len(m.getParameterTypes()) == x.len(params), "The number of parameters sent to x.generate has to be the same as that used in the generator method of " + generator.getName());
				for (tuple items : x.enumerate(m.getParameterTypes())){
					int idx = items.get(0, int.class);
					Class<?> type = items.get(1, Class.class);
					Class<?> cls = params[idx].getClass();
					if (cls.equals(Integer.class)) {
						cls = int.class;
					} else if (cls.equals(Float.class)) {
						cls = float.class;
					} else if (cls.equals(Double.class)) {
						cls = double.class;
					} else if (cls.equals(Boolean.class)) {
						cls = boolean.class;
					}
					x.assertTrue(type.equals(cls), "Types of parameters sent to x.generate have to be the same as those used in the generator method of " + generator.getName());
				}
			}
		}
		x.assertTrue(foundGenerateMethod, "The class " + generator.getName() + " does not have the generate method. It's not a valid generator.");
		Constructor<?> m = generator.getDeclaredConstructor();
		m.setAccessible(true);
		Generator0<T> g = generator.cast(m.newInstance()).input(params);
		return g;

	}
	*/
	
	/**
	 * 
	 * Creates an instance of a HappySQL object.
	 * 
	 * @param	dbHost		db host
	 * @param	userName	user name
	 * @param	password	password
	 * @param	dbName		db name
	 * @return	an object that has the {@link HappySQL#execute(String)} method
	 * 		  	that takes the query and params as input;
	 * 
	 * 			Example:
	 * 			<pre>
	 * 			{@code
	 * 			try (HappySQL sql = x.mysql("localhost", "user", "password", "db")) {
	 * 					sql.execute("SELECT * FROM Table WHERE ID BETWEEN ? and ? and UserName LIKE ?", 1000, 2000, "John %");
	 * 			}
	 * 			}
	 * 			</pre>
	 * 
	 * 
	 * @throws ClassNotFoundException	in case the jdbc driver for mysql is not found on the system
	 * @throws SQLException				in case of bad SQL request
	 */
	public static HappySQL mysql(String dbHost, String userName, String password, String dbName) throws ClassNotFoundException, SQLException{
		return new HappyMySQL(dbHost, userName, password, dbName);
	} 
	
	/**
	 * 
	 * Creates a new instance of a HappyMySQL object with the same parameters as
	 * those of the input HappyMySQL object.
	 * 
	 * @param sql	A {@link HappyMySQL} object to clone
	 * 
	 * @return	an object that has the {@link HappySQL#execute(String)} method
	 * 		  	that takes the query and params as input;
	 * 
	 * 			Example:
	 * 			<pre>
	 * 			{@code
	 * 			try (HappySQL sql2 = x.mysql(sql1)) {
	 * 					sql2.execute("SELECT * FROM Table WHERE ID BETWEEN ? and ? and UserName LIKE ?", 1000, 2000, "John %");
	 * 			}
	 * 			}
	 * 			</pre>
	 * 
	 * 
	 * @throws ClassNotFoundException	in case the jdbc driver for mysql is not found on the system
	 * @throws SQLException				in case of bad SQL request
	 */
	public static HappySQL mysql(HappyMySQL sql) throws ClassNotFoundException, SQLException{
		return new HappyMySQL(sql.dbHost, sql.userName, sql.password, sql.dbName);
	} 
	
	/**
	 * Creates an instance of a HappyObject class. This class has utility methods 
	 * equals, compareTo and HashCode.
	 * 
	 * Examples:
	 * 
	 * When defining a class:
	 * 
	 * <pre>
	 * {@code \@Override
	 * int hashCode(){
	 * 		return x.Object(this).hashCode();
	 * }
	 * }
	 * </pre>
	 * In the above code, xpresso first finds the members of this (via reflections) and then
	 * dynamically computes the hash code for this based on the values of its members.
	 * 
	 * <pre>
	 * {@code
	 * \@Override
	 * boolean equals(Object obj){
	 * 		return x.Object(this).equals(obj);
	 * }
	 * }
	 * </pre>
	 * In the above code, xpresso first finds the members of the
	 * two objects (this and obj), and then compares the values of those members.
	 * 
	 * <pre>
	 * {@code
	 * \@Override
	 * public int compareTo(Object obj){
	 *      return x.Object(this).compareTo(obj, fieldName0, fieldName1, ...);
	 * }
	 * }
	 * </pre>
	 * 
	 * In the above code, xpresso first finds the members of the
	 * two objects (this and obj). It then compares the values of those
	 * members between the two objects if those members' names are listed
	 * among the input field names fieldName0, fieldName1, etc.
	 * The order of comparisons between the member's values is the same
	 * as the order of input field names.
	 * 
	 * @param o	the {@link Object} whose properties we want to extend
	 * @return	an instance of {@link HappyObject} object that wraps the input object o
	 */
	public static HappyObject Object(Object o){
		return new HappyObject(o);
	}
	
	/**
	 * Opens a {@link HappyFile} for reading or writing, in binary or text mode.
	 * 
	 * Example:
	 * 
	 * <pre>
	 * {@code
	 * try (HappyFile f = x.open("filename.txt","r","utf-8"){
	 * 		//do stuff
	 * }
	 * }
	 * </pre>
	 * 
	 * In case of a text file, the {@link HappyFile} object is also an Iterable containing 
	 * lines of the file:
	 * 
	 * <pre>
	 * {@code
	 * for(String line : f){
	 * 		x.print(line);
	 * }
	 * }
	 * </pre>
	 * 
	 * @param path			a {@link String} object containing the path to the file
	 * @param operation		can be "r" (read in text mode), "rb" (read in binary mode),
	 * 						"w" (write in text mode), "wb" write in binary mode
	 * 						"a" append in text mode, "ab" append in binary mode
	 * @param encoding		the String object containing the encoding of the file
	 * 						(can be "utf-8" or "latin-1")
	 * @throws IOException	in case there's a problem opening file
	 * @return 				a {@link HappyFile} object
	 */
	public static HappyFile open(String path,String operation,String encoding) throws IOException{
		return new HappyFile(path,operation,encoding);
	}
	
	/**
	 * Opens a {@link HappyFile} for reading or writing, in binary or text mode.
	 * 
	 * Example:
	 * 
	 * <pre>
	 * {@code
	 * HappyFile f = x.open("filename.txt","r","utf-8");
	 * }
	 * </pre>
	 * 
	 * In case of a text file, the {@link HappyFile} object is also an Iterable containing 
	 * lines of the file:
	 * 
	 * <pre>
	 * {@code
	 * for(String line : x.open("filename.txt","r","utf-8")){
	 * 		x.print(line);
	 * }
	 * }
	 * </pre>
	 * 
	 * @param path			a {@link String} object containing the path to the file
	 * @param operation		can be "r" (read in text mode with utf-8 encoding), "rb" (read in binary mode),
	 * 						"w" (write in text mode), "wb" write in binary mode
	 * 						"a" append in text mode, "ab" append in binary mode
	 * @throws IOException	in case there's a problem opening file
	 * @return 				a {@link HappyFile} object
	 */
	public static HappyFile open(String path,String operation) throws IOException{
		try{
			return new HappyFile(path,operation);
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * Opens a {@link HappyFile} for reading or writing in csv format.
	 * 
	 * Example 1:
	 * <pre>
	 * {@code
	 * try (csv f = x.csv("filename.txt","r","utf-8"){
	 * 		for(list<String> line : f){
	 * 			x.print(line);
	 * 		}
	 * }
	 * }
	 * </pre>
	 * 
	 * Example 2:
	 * <pre>
	 * {@code
	 * try (csv f = x.csv("filename.txt","w","utf-8"){
	 * 		for(list<?> line : iterable>){
	 * 			csv.writerow(line);
	 * 		}
	 * }
	 * }
	 * </pre>
	 * 
	 * In case of a text file, the {@link CSV} object is also an Iterable containing 
	 * {@code list<String>} objects for each line of the file:
	 * 
	 * <pre>
	 * {@code
	 * for(list<String> line : f){
	 * 		x.print(line);
	 * }
	 * }
	 * </pre>
	 * 
	 * @param path			a {@link String} object containing the path to the file
	 * @param operation		can be "r" (read in text mode), "rb" (read in binary mode),
	 * 						"w" (write in text mode), "wb" write in binary mode
	 * 						"a" append in text mode, "ab" append in binary mode
	 * @param encoding		the String object containing the encoding of the file
	 * 						(can be "utf-8" or "latin-1")
	 * @throws IOException	in case there's a problem opening file
	 * @return 				a {@link CSV} object
	 */
	public static CSV csv(String path,String operation,String encoding) throws IOException{
		return new CSV(path,operation,encoding);
	}
	
	/**
	 * Opens a {@link HappyFile} for reading or writing in csv format with the
	 * default encoding "utf-8".
	 * 
	 * Example:
	 * <pre>
	 * {@code
	 * try (csv f = x.csv("filename.txt","r","utf-8"){
	 * 		//do stuff
	 * }
	 * }
	 * </pre>
	 * 
	 * In case of a text file, the {@link CSV} object is also an {@link Iterable} containing 
	 * {@code list<String>} objects for each line of the file:
	 * 
	 * <pre>
	 * {@code
	 * for(list<String> line : f){
	 * 		x.print(line);
	 * }
	 * }
	 * </pre>
	 * 
	 * @param path			a {@link String} object containing the path to the file
	 * @param operation		can be "r" (read in text mode), "rb" (read in binary mode),
	 * 						"w" (write in text mode), "wb" write in binary mode
	 * 						"a" append in text mode, "ab" append in binary mode
	 * @throws IOException	in case there's a problem opening file
	 * @return 				a {@link CSV} object
	 */
	public static CSV csv(String path,String operation) throws IOException{
		try{
			return new CSV(path,operation);
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 * Creates a {@link CSV} object from an iterable.
	 * 
	 * The {@link Iterable} can be either an instance of {@link HappyFile} or
	 * an {@code Iterable<list<?>>}.
	 * 
	 * When the input {@link Iterable} is a HappyFile, the csv object reads or writes
	 * from/into the given HappyFile object.
	 * 
	 * When the input {@link Iterable} is an {@code Iterable<list<?>>}, the csv object can only
	 * be used with the toString() function. It will generate the csv String representation
	 * of the input {@link Iterable} and {@code toString()} will return this csv String representation
	 * 
	 * @param iterable		a {@link HappyFile} object or an {@code Iterable<list<?>>}
	 * @return 				a {@link CSV} object
	 */
	public static CSV csv(Iterable<?> iterable){
		return new CSV(iterable);
	}
	
	/**
	 * Creates a {@link CSV} object from a {@link StringBuilder}.
	 * 
	 * Example:
	 * <pre>
	 * {@code
	 * StringBuilder builder = new StringBuilder();
	 * 
	 * csv c = x.csv(builder);
	 * 
	 * for (list<?> line : iterable){
	 * 		c.writerow(line);
	 * }
	 * 
	 * String mycsv = c.toString();
	 * }
	 * </pre>
	 * 
	 * @param builder		a {@link StringBuffer} object to write csv to
	 * @return 				a {@link CSV} object
	 */
	public static CSV csv(StringBuilder builder){
		return new CSV(builder);
	}
	
	/**
	 * Returns a {@link HappyString HappyString} object that extends the String object
	 * with additional methods, such as {@link HappyString#join}, {@link HappyString#split}, and {@link HappyString#in}.
	 * 
	 * 
	 * Example 1: 
	 * <pre>
	 * {@code
	 * 			  boolean q = x.String("na").in("banana");
	 *            x.print(q);
	 * }
	 * </pre>
	 *                   
	 * Console:   true
	 * 
	 * <pre>
	 * {@code
	 * Example 2: String s = x.String("|").join(x.listOf("a","b","c"));
	 *            x.print(s);
	 * }
	 * </pre>
	 *                   
	 * Console:   a|b|c
	 * 
	 * @param character    	char to wrap
	 * @return    			a {@link HappyString} object that wraps char
	 */
	public static HappyString String(char character){
		return new HappyString(String.valueOf(character));
	}
	
	/**
	 * Returns a {@link HappyString HappyString} object that extends the String object
	 * with additional methods, such as {@link HappyString#join}, {@link HappyString#split}, and {@link HappyString#in}.
	 * 
	 * <pre>
	 * {@code
	 * Example 1: boolean q = x.String("na").in("banana");
	 *            x.print(q);
	 *                   
	 * Console:   true
	 * 
	 * 
	 * Example 2: String s = x.String("|").join(x.listOf("a","b","c"));
	 *            x.print(s);
	 *                   
	 * Console:   a|b|c
	 * }
	 * </pre>
	 * 
	 * @param character		char to wrap
	 * @return    			a HappyString object that wraps char
	 */
	public static HappyString String(Character character){
		return new HappyString(String.valueOf(character));
	}
	
	/**
	 * Returns a {@link HappyString HappyString} object that extends the String object
	 * with additional methods, such as {@link HappyString#join}, {@link HappyString#split}, and {@link HappyString#in}.
	 * 
	 * <pre>
	 * {@code
	 * Example 1: boolean q = x.String("na").in("banana");
	 *            x.print(q);
	 *                   
	 * Console:   true
	 * 
	 * 
	 * Example 2: String s = x.String("|").join(x.listOf("a","b","c"));
	 *            x.print(s);
	 *                   
	 * Console:   a|b|c
	 * }
	 * </pre>
	 * 
	 * @param     string to wrap
	 * @return    a HappyString object that wraps string
	 */
	public static HappyString String(String string){
		return new HappyString(string);
	}
	
	/**
	 * Returns a HappyString object that extends the String object
	 * with additional methods, such as {@link HappyString#join}, {@link HappyString#split}, and {@link HappyString#in}.
	 * 
	 * 
	 * <pre>
	 * {@code
	 * Example 1: boolean q = x.String(x.str("na")).in("banana");
	 *            x.print(q);
	 *                   
	 * Console:   true
	 * 
	 * 
	 * Example 2: String s = x.String("|").join(x.listOf("a","b","c"));
	 *            x.print(s);
	 *                   
	 * Console:   a|b|c
	 * }
	 * </pre>
	 * 
	 * @param     str to wrap
	 * @return    a {@link HappyString} object that wraps the str
	 */
	public static HappyString String(str str){
		return new HappyString(str.toString());
	}
	
	/**
	 * For consistency, {@link x#String x.String} contains an instance of a utility
	 * {@link HappyStringStatic} object that implements some static methods
	 * that a usual String class has as well plus some new Function and Predicate
	 * objects that take {@link String} as input.
	 * 
	 * <pre>
	 * {@code
	 * Example 1: String three = x.String.valueOf(3);
	 *            x.print(three);
	 *                   
	 * Console:   3
	 * 
	 * Example 2: Function<Object,String> upperFun = x.String.upper;
	 * }
	 * </pre>
	 * 
	 */
	public static HappyStringStatic String = new HappyStringStatic();
	
	/**
	 * {@link x#str str} contains an instance of a utility
	 * {@link strStatic} object that implements some Function and Predicate
	 * objects that take a {@link str} as input.
	 * 
	 * <pre>
	 * {@code
	 * Example 1: Function<Object,str> upperFun = x.str.upper;
	 * }
	 * </pre>
	 * 
	 */
	public static strStatic str = new strStatic();
	
	/**
	 * Factory method that creates a new empty {@link str} object.
	 * 
	 * Example 1:
	 * 
	 * <pre>
	 * {@code
	 * str newStr = x.str();
	 * 
	 * x.print(newStr);
	 * 
	 * Console:
	 * }
	 * </pre>
	 * 
	 * @return the new {@link str} object
	 * 
	 */
	public static str str(){
		return new str();
	}
	
	/**
	 * Factory method that creates a new {@link str} object
	 * from the input {@link String} string.
	 * 
	 * Example 1:
	 * 
	 * <pre>
	 * {@code
	 * str newStr = x.str("I like xpresso!");
	 * 
	 * x.print(newStr.sliceFrom(7));
	 * 
	 * Console: xpresso!
	 * }
	 * </pre>
	 *
	 * @param string	a {@link String} that initialized the new {@link str} object
	 * @return the new {@link str} object containing the string from the input {@link String}
	 * 
	 */
	public static str str(String string){
		return new str(string);
	}
	
	/**
	 * Factory method that creates a new {@link str} object
	 * from the input {@link Iterable} of type {@link String}.
	 * 
	 * Example 1:
	 * 
	 * <pre>
	 * {@code
	 * str newStr = x.str(x.list("hello", " ", "world", "!"));
	 * 
	 * x.print(newStr);
	 * 
	 * Console: hello world!
	 * }
	 * </pre>
	 * @param iterable	the Iterable that contains data to initialize the new {@link str} object
	 * @return the new {@link str} object that represent a concatenation
	 * of all elements of the input {@link Iterable}
	 * 
	 */
	public static str str(Iterable<String> iterable){
		return new str(x.String("").join(iterable));
	}
	
	
	/**
	 * Factory method that creates a new {@link set} object
	 * from the input {@link Iterable} iterable of type T. The elements
	 * of the input iterable will become the members of the new set object.
	 *  
	 *  @param iterable	an {@link Iterable} containing elements to be put into new {@link set} object
	 *  @param <T>	type of elements in the input {@link Iterable}
	 *  @return new {@link set}
	 */
	public static <T> set<T> set(Iterable<T> iterable){
		set<T> constructor = new set<T>();
		for (T element:iterable){
			constructor.put(element);
		}
		return constructor;
	}
	
	/**
	 * Factory method that creates a new empty {@link set} object.
	 *  
	 * @param <T>	type of elements of the new {@link set}
	 * @return new empty {@link set}
	 */
	public static <T> set<T> set(){
		return new set<T>();
	}
	
	/**
	 * Factory method that creates a new {@link set} object
	 * from all input elements of type T. The elements
	 * of the input will become the members of the new set object.
	 *  
	 *  @param element0	an element to put into new {@link set}
	 *  @param element1 an element to put into new {@link set}
	 *  @param elements elements to put into new {@link set}
	 *  @param <T>	type of elements of the new {@link set}
	 *  @return new set
	 */
	@SafeVarargs
	public static <T> set<T> set(T element0, T element1, T... elements){
		return new set<T>(Helpers.newHashSet(elements)).put(element0).put(element1);
	}
	
	/**
	 * Factory method that creates a new {@link set} object
	 * from the array of input elements of type T. The elements
	 * of the input array will become the members of the new set object.
	 *  
	 *  @param <T>	the type of elements in the new set
	 *  @param elements	an array of elements to put into the new {@link set}
	 *  @return a {@link set}
	 */
	public static <T> set<T> set(T[] elements){
		return new set<T>(Helpers.newHashSet(elements));
	}
	
	/**
	 * Factory method that creates a new {@link set} object
	 * and puts into the set the input element.
	 *  
	 *  @param <T>	the type of elements in the {@link set}
	 *  @param element	the element to put into the new set
	 *  @return	the new {@link set} 
	 */
	public static <T> set<T> setOf(T element){
		return new set<T>().put(element);
	}

	/**
	 * Factory method that creates a new empty {@link list} object.
	 *  
	 *  @param <T>	the type of elements in the {@link list}
	 *  @return	the new {@link list}
	 */
	public static <T> list<T> list(){
		return new list<T>();
	}
	
	/**
	 * Factory method that creates a new {@link list} containing the input element.
	 *  
	 *  @param <T>	the type of elements in the {@link list}
	 *  @param element	the element to put into the new list
	 *  @return	the new {@link list}
	 */
	public static <T> list<T> listOf(T element){
		return (new list<T>()).append(element);
	}
	
	/**
	 * Factory method that creates a new {@link list} object
	 * from the input elements of type T. The input elements
	 * will become the members of the new list object
	 * in the same order.
	 * 
	 *  @param element0	the first element to put into new {@link list}
	 *  @param element1 teh second element to put into new {@link list}
	 *  @param elements all other elements to put into new {@link list}
	 *  @param <T>	type of elements of the new {@link list}
	 *  @return new list
	 *  
	 */
	@SafeVarargs
	public static <T> list<T> list(T element0, T element1, T... elements){
		list<T> list = (new list<T>()).append(element0).append(element1);
		for(T element : elements){
			list.append(element);	
		}
		return list;
	}
	
	/**
	 * Factory method that creates a new {@link list} object
	 * from the input array of type T.
	 * 
	 * The elements of the input array become the elements of new list.
	 * 
	 * The order of elements in the input array is preserved in the new list.
	 * 
	 *  @param values	the input array of elements of type T
	 *  @param <T>	type of elements of the new {@link list}
	 *  @return new list
	 *  
	 */
	public static <T> list<T> list(T[] values){ 
		return new list<T>(Helpers.newArrayList(values));
	}
	
	/**
	 * Factory method that creates a new {@link list} object
	 * from the input {@link Iterable} iterable of type T.
	 * 
	 * The elements of the input iterable become the elements of new list.
	 * 
	 * The order of elements in the input iterable is preserved in the new list.
	 *  @param iterable	the input iterable containing elements to put in the new list
	 *  @param <T>	type of elements of the new {@link list}
	 *  @return new list
	 */
	public static <T> list<T> list(Iterable<T> iterable){ 
		return new list<T>(iterable);
	}
	
	/**
	 * Factory method that starts a new scalar comprehension.
	 * 
	 * The difference of a scalar comprehension from a tuple comprehension 
	 * is that scalar comprehension sees the elements of the input iterable
	 * as scalars even if the latter are tuples, or iterables or other 
	 * collection types.
	 * 
	 * Example 1, a list comprehension:
	 * 
	 * Python:
	 * <pre>
	 * {@code
	 * foreign_trips_lower = [element.lower() for element in trips if element not in russian_cities]
	 * }
	 * </pre>
	 * 
	 * xpresso:
	 * <pre>
	 * {@code
	 * list<String> foreignTripsLower = x.list(x.yield().apply(x.lower).forEach(trips).unless(x.in(russianCities)));
	 * }
	 * </pre>
	 * 
	 * @param <O>	the type of elements in the output iterable
	 * 
	 * @return the fist object in the sequence that builds the comprehension expression
	 */
	public static <O> ScalarComprehensionStart<O> yield(){
		return ComprehensionFactory.scalar();
	}
	
	/**
	 * Factory method that starts a new tuple comprehension.
	 * 
	 * The difference of a scalar comprehension from a tuple comprehension 
	 * is that the tuple comprehension returns tuples of values.
	 * 
	 * Usually it's used when the elements of the input iterable
	 * are themselves tuples, or iterables or other collection types.
	 * But it freely can be used with iterables of scalars if needed.
	 * 
	 * Example 1, a list comprehension:
	 * 
	 * Python:
	 * <pre>
	 * {@code
	 * list1 = [a.lower() for a, b, c, d in list0]
	 * }
	 * </pre>
	 * 
	 * xpresso:
	 * <pre>
	 * {@code
	 * list<tuple> list1 = x.list(x.yield("a").apply(x.lower).where("a", "b", "c", "d").in(list0));
	 * }
	 * </pre>
	 * 
	 * @param fieldName the name of the "field" of each element of the input Iterable we want in our output Iterable
	 * @return the fist object in the sequence that builds the comprehension expression
	 */
	public static Tuple1ComprehensionStart yield(String fieldName) {
        return ComprehensionFactory.tuple(fieldName);
    }
	
	/**
	 * Factory method that starts a new tuple comprehension.
	 * 
	 * The difference of a scalar comprehension from a tuple comprehension 
	 * is that the tuple comprehension returns tuples of values.
	 * 
	 * Usually it's used when the elements of the input iterable
	 * are themselves tuples, or iterables or other collection types.
	 * But it freely can be used with iterables of scalars if needed.
	 * 
	 * Example 1, a list comprehension:
	 * 
	 * Python:
	 * <pre>
	 * {@code
	 * list1 = [a.lower(), b.upper() for a, b, c, d in list0]
	 * }
	 * </pre>
	 * 
	 * xpresso:
	 * <pre>
	 * {@code
	 * list<tuple> list1 = x.list(x.yield("a", "b").apply(x.lower, x.upper).where("a", "b", "c", "d").in(list0));
	 * }
	 * </pre>
	 * 
	 * Example 2, a list comprehension:
	 * 
	 * Python:
	 * <pre>
	 * {@code
	 * list1 = [a.lower(), true for a, b, c, d in list0]
	 * }
	 * </pre>
	 * 
	 * xpresso:
	 * <pre>
	 * {@code
	 * list<tuple> list1 = x.list(x.yield("a", "b").apply(x.lower).replace(True).where("a", "b", "c", "d").in(list0));
	 * }
	 * </pre>
	 * @param fieldName0 and
	 * @param fieldName1 are the names of the "fields" of each element of the input Iterable
	 * we want in our output Iterable
	 * @return the fist object in the sequence that builds the comprehension expression
	 */
	public static Tuple2ComprehensionStart yield(String fieldName0, String fieldName1) {
        return ComprehensionFactory.tuple(fieldName0, fieldName1);
    }
	
	/**
	 * Creates and returns a {@link Function} that takes as a parameter an {@link java.lang.Iterable} 
	 * and uses the Function's input value as key to get a value from the
	 * {@link java.lang.Iterable}
	 * 
	 * @param iterable	an {@link java.lang.Iterable}
	 * @param <O>			the type of elements in the 
	 * @return a {@link Function}
	 *                   
	 */
	public static <O> Function<Object,O> asKey(final Iterable<?> iterable){
		return new Function<Object,O>() {
			@SuppressWarnings("unchecked")
			public O apply(Object key) {
				try{
					return ((dict<O>)(iterable)).get((String)key);	
				}catch(Exception e0){
					try{
						return ((set<O>)(iterable)).get((O)key);	
					}catch(Exception e2){
						try{
							return ((list<O>)(iterable)).get((O)key);	
						}catch(Exception e3){
							try{
								return (Helpers.newArrayList((Iterable<O>)(iterable))).get((Integer)key);	
							}catch(Exception e4){
								throw new IllegalArgumentException("asKeyOn could not interpret the input object as a container of values.");
							}	
						}	
					}
				}
			}
		};
	}
	
	/**
	 * Creates and returns a {@link Function} that takes as a parameter a {@link java.util.Map} 
	 * and uses the Function's input value as key to get a value from the
	 * corresponding {@link java.util.Map}
	 * 
	 * @param map			a {@link Map}
	 * @param <O>			the type of values in the map
	 * @return a {@link Function}
	 *                   
	 */
	public static <O> Function<Object,O> asKey(final Map<?,O> map){
		return new Function<Object,O>() {
			public O apply(Object key) {
					try{
						return ((Map<?,O>)(map)).get(key);	
					}catch(Exception e1){
						throw new IllegalArgumentException("asKeyOn could not interpret the input object as a container of values.");
					}
			}
		};
	}
	
	/**
	 * Creates and returns a {@link Function} that takes as a parameter a {@link String} separator 
	 * and concatinates the Function's input {@link java.lang.Iterable} using the separator
	 * 
	 * @param separator	an {@link String} separator
	 * @return a {@link Function}
	 * 
	 */
	public static Function<Object,String> joinOn(final String separator){
		return new Function<Object,String>() {
			public String apply(Object iterable) {
				if(iterable instanceof String && separator.equals("")){
					return (String)iterable;
				}
				try{
					return x.String(separator).join((Iterable<?>)iterable);	
				}catch(Exception e){
					throw new IllegalArgumentException("Could not interpret the input object as an Iterable.");
				}
			}
		};
	}
	
	/**
	 * Creates and returns new {@link Predicate} with the NOT logic applied to the input {@link Predicate}
	 * 
	 * @param predicate	a {@link Predicate} to apply the NOT logic to
	 * @return a {@link Predicate} that negates the input @param predicate 
	 *              
	 */
	public static Predicate<Object> NOT(final Predicate<Object> predicate){
		return new Predicate<Object>() {
			public Boolean apply(Object bool) {
				return !(predicate.apply(bool));
			}
		};
	}
	
	
	/**
	 * Creates and returns new {@link Predicate} that returns true
	 * only if the input value of the predicate (key) is contained within in the {@link java.lang.Iterable} <pre>{@code iterable}</pre> parameter.
	 * 
	 * @param iterable	an {@link java.lang.Iterable} of type {@code <T>}
	 * @param <T>		the type of iterable's element
	 * @return a new {@link Predicate}
	 *             
	 */
	public static <T> Predicate<Object> in(final Iterable<T> iterable){
		return new Predicate<Object>() {
			@SuppressWarnings("unchecked")
			public Boolean apply(Object key) {
				return contains(iterable, (T)key);
			}
		};
	}
	
	/**
	 * Creates and returns new {@link Predicate} that returns true
	 * only if the input value of the predicate, <pre>{@code stringOrChar}</pre> is contained within in the {@code <pre>string</pre>} parameter.
	 * 
	 * @param string	the {@link String} to search in 
	 * @return a new {@link Predicate}
	 *             
	 */
	public static Predicate<Object> in(final String string){
		return new Predicate<Object>() {
			public Boolean apply(Object stringOrChar) {
				try{
					return x.String((char)stringOrChar).in(string);
				}catch(Exception e0){
					try{
						return x.String((Character)stringOrChar).in(string);
					}catch(Exception e1){
						try{
							return x.String((String)stringOrChar).in(string);
						}catch(Exception e2){
							throw new IllegalArgumentException("Could not intepret the input value as a character or a string");
						}
					}
				}
			}
		};
	}
	
	/**
	 * Creates and returns new {@link Predicate} that returns true
	 * only if the input value of the predicate, <pre>{@code stringOrChar}</pre> is contained within in the {@code <pre>str</pre>} parameter.
	 * 
	 * @param str	a {@link str} object
	 * @return a new {@link Predicate}
	 *             
	 */
	public static Predicate<Object> in(final str str){
		return new Predicate<Object>() {
			public Boolean apply(Object stringOrChar) {
				try{
					return x.String((char)stringOrChar).in(str);
				}catch(Exception e0){
					try{
						return x.String((Character)stringOrChar).in(str);
					}catch(Exception e1){
						try{
							return x.String((String)stringOrChar).in(str);
						}catch(Exception e2){
							throw new IllegalArgumentException("Could not intepret the input value as a character or a string.");
						}
					}
				}
			}
		};
	}
	
	/**
	 * Creates and returns new {@link LambdaPredicate} for a given {@link String} lambda expression
	 * 
	 * Example 1: <pre>{@code Predicate\<Object\> isNonEmptyLongString = x.lambdaP("x : f0(x) > 5 || f1(x) != 0",x.len,x.len);}</pre>
	 *
	 * @param lambdaExpression	a {@link String} containing a lambda expression
	 * @param functions optional {@link Function} objects
	 * @return a LambdaPredicate that realizes the description given in @param lambdaExpression
	 * and using @param functions 
	 *                   
	 */
	@SafeVarargs
	public static LambdaPredicate lambdaP(String lambdaExpression,Function<Object,?>... functions){
		return new LambdaPredicate(lambdaExpression,functions);
	}
	public static LambdaPredicate lambdaP(String lambdaExpression){
		return new LambdaPredicate(lambdaExpression);
	}
	
	/**
	 * Creates and returns new {@link LambdaFunction} for a given {@link String} lambda expression
	 * 
	 * Example 1: <pre>{@code Function<Object,Integer> increment = x.\<Integer\>lambdaF("x : x + 1");}</pre>
	 * 
	 * @param lambdaExpression	a {@link String} with a lambda expression
	 * @param functions 		functions to use in the lambda expressions (optional)
	 * @param <O>		 		output type of the LambdaFunction
	 * @return a LambdaFunction that realizes the description given in @param lambdaExpression
	 * and using @param functions 
	 *                   
	 */
	@SafeVarargs
	public static <O> LambdaFunction<O> lambdaF(String lambdaExpression,Function<Object,?>... functions){
		return new LambdaFunction<O>(lambdaExpression,functions);
	}
	public static <O> LambdaFunction<O> lambdaF(String lambdaExpression){
		return new LambdaFunction<O>(lambdaExpression);
	}
	
	/**
	 * Chains functions in the following way: {@code f2(f1(f0(x)))} where {@code x} is the input
	 * of the chained function and f0, f1, ... are the functions to apply to x in chain.
	 * 
	 * Example 1: <pre>{@code Function<Integer,Integer> incrementAndMultiplyBy5 = x.chain(increment,x.\<Integer\>lambdaF("x : x * 5"));}</pre>
	 *
	 * @param functions	functions to chain (optional)
	 * @param <I>		input type for the final chained function
	 * @param <O>		output type for the final chained function
	 * @return 			a {@link Function} that chains the input functions
	 *                   
	 */
	@SafeVarargs
	public static <I,O> Function<I,O> chain(final Function<Object,?>... functions){
		return new Function<I,O>(){
			@SuppressWarnings("unchecked")
			@Override
			public O apply(I value) {
				Object newValue = x.doNothing.apply(value);
				for (Function<Object,?> func : functions) {
					newValue = func.apply(newValue);
				}
				return (O)newValue;
			}
		};
	}
	
	/**
	 * Returns an {@link java.lang.Iterable} that chains input iterables
	 * of the chained function and f0, f1, ... are the functions to apply to {@code x} in chain.
	 *
	 * @param iterable0	the first iterable
	 * @param iterable1	the second iterable
	 * @param iterables	the remaining iterables
	 * @param <T>		the type of values returned by the input iterables
	 * @return 			an {@link java.lang.Iterable} ot type {@code T}
	 */
	@SafeVarargs
	public static <T> Iterable<T> chain(final Iterable<T> iterable0, final Iterable<T> iterable1, final Iterable<T>... iterables){
		Iterable<T> generator = new Iterable<T>(){
			public Iterator<T> iterator(){
				final ArrayList<Iterator<T>> iterators = new ArrayList<Iterator<T>>();
				iterators.add(iterable0.iterator());
				iterators.add(iterable1.iterator());
				int i = 2;
				for(Iterable<T> iterable : iterables){
					iterators.add(iterable.iterator());
					i++;
				}
				final int numberOfInputIters = i;
				return new Iterator<T>(){
					int currentIter = 0;
					@Override
					public boolean hasNext() {
						if(currentIter < numberOfInputIters){
							if(iterators.get(currentIter).hasNext()){
								return true;
							}
						}
						return false;
					}

					@Override
					public T next() {
						currentIter++;
						return iterators.get(currentIter).next();
					}
				};
			}
		};
		return generator;
	}
	
	/**
	 * Returns an {@link java.lang.Iterable} that iterates over the characters of
	 * the sequence of input strings, starting from the first character of the string0
	 *
	 * @param string0		the first string
	 * @param string1		the second string
	 * @param otherStrings	the remaining strings
	 * @return 				an {@link java.lang.Iterable} or {@link String}
	 */
	@SafeVarargs
	public static Iterable<String> chain(final String string0,final String string1,final String... otherStrings){
		String[] stingArr = otherStrings;
		str[] strArr = new str[stingArr.length];
		int counter = 0;
		for(String string : otherStrings){
			strArr[counter] = x.str(string);
			counter++;
		}
		return chain(x.str(string0),x.str(string1),strArr);
	}

	/**
	 * Factory method that returns an {@link tuple} of one element.
	 *
	 * @param value			a value of any type
	 * @return 				a {@link tuple} that contains @param value
	 */
	public static tuple tupleOf(tuple value) {
		return value.copy();
    }
	
	/**
	 * Factory method that returns an {@link tuple} of one element.
	 *
	 * @param value			a value of any type
	 * @param <T0>			the type of value
	 * @return 				a {@link tuple} that contains @param value
	 */
	public static <T0> tuple tuple(T0 value) {
        return tuple1.valueOf(value);
    }

	/**
	 * Factory method that returns an {@link tuple} of two elements.
	 *
	 * @param value0		a value of any type
	 * @param value1		a value of any type (can be different of the type of value0)
	 * @param <T0>			the type of value0
	 * @param <T1>			the type of value1
	 * @return 				a {@link tuple} that contains @param value0 as the first dimension
	 * 						and @param value1 as the second dimension
	 */
    public static <T0, T1> tuple tuple(T0 value0, T1 value1) {
        return tuple2.valueOf(value0, value1);
    }

	/**
	 * Factory method that returns an {@link tuple} of three elements.
	 *
	 * @param value0		a value of any type
	 * @param value1		a value of any type (can be different of the type of value0)
	 * @param value2		a value of any type (can be different of the types of value0 and value1)
	 * @param <T0>			the type of value0
	 * @param <T1>			the type of value1
	 * @param <T2>			the type of value2
	 * @return 				a {@link tuple} that contains @param value0 as the first dimension,
	 * 						@param value1 as the second dimension, and @param value2 as the third dimension
	 */
    public static <T0, T1, T2> tuple tuple(T0 value0, T1 value1, T2 value2) {
        return tuple3.valueOf(value0, value1, value2);
    }
    
	/**
	 * Factory method that returns an {@link tuple} of three elements.
	 *
	 * @param value0		a value of any type
	 * @param value1		a value of any type (can be different of the type of value0)
	 * @param value2		a value of any type (can be different of the types of value0 and value1)
	 * @param value3		a value of any type (can be different of the types of value0, value1, and value3)
	 * @param <T0>			the type of value0
	 * @param <T1>			the type of value1
	 * @param <T2>			the type of value2
	 * @param <T3>			the type of value2
	 * 
	 * @return 				a {@link tuple} that contains @param value0 as the first dimension,
	 * 						@param value1 as the second dimension, @param value2 as the third dimension,
	 * 						and @param value3 as the fourth dimension
	 */
    public static <T0, T1, T2, T3> tuple tuple(T0 value0, T1 value1, T2 value2, T3 value3) {
        return tuple4.valueOf(value0, value1, value2, value3);
    }

	/**
	 * Factory method that returns an new empty {@link dict}.
	 * @param <T>	the type of values in the dict
	 * @return a new empty {@link dict}
	 */
	public static <T> dict<T> dict(){
		return new dict<T>();
	}
	
	/**
	 * Factory method that returns an new {@link dict} filled using the
	 * values of the input {@link Iterable} of tuples.
	 * 
	 * The input tuples have to be instances of {@link tuple2}{@code <String,T>}. The first dimension
	 * of each tuple is used as key and the second as value for the dict.
	 *
	 * @param tuples	an {@link Iterable} of {@link tuple}s
	 * @param <T> 		the type of values in the new dict
	 * @return a new {@link dict}
	 *
	 */
	public static <T> dict<T> dict(Iterable<tuple> tuples){
		dict<T> dict = new dict<T>();
		dict.update(tuples);
		return dict;
	}
	
	/**
	 * Factory method that returns an new {@link dict} filled using the
	 * values of the input array of tuples.
	 * 
	 * The input tuples have to be instances of {@link tuple2}{@code <String,T>}. The first dimension
	 * of each tuple is used as key and the second as value for the dict.
	 *
	 * @param tuples		zero or more instances of {@link tuple2}{@code <String,T>}
	 * @param <T> 			any type
	 * @return a new {@link dict}
	 *
	 */ 
	@SafeVarargs
	public static <T> dict<T> dict(tuple... tuples){
		dict<T> dict = new dict<T>();
		dict.update(tuples);
		return dict;
	}
		
	/**
	 * Factory method that returns an new {@link dict} filled using the
	 * values of the input {@link Map}{@code <String,T>}.
	 * 	 *
	 * @param 		map	a {@link Map} of type {@code <String,T>}
	 * @param <T> 	any type
	 * @return a new {@link dict}
	 *
	 */  
	public static <T> dict<T> dict(Map<String,T> map){
		return new dict<T>(map);
	}
	
	/**
	 * Factory method that returns an new {@link dict} filled using the
	 * values of the input another {@link dict}.
	 * 	 *
	 * @param dict	a {@link dict}{@code <T>}.
	 * @param <T> 	any type
	 * @return a new {@link dict}
	 *
	 */   
	public static <T> dict<T> dict(dict<T> dict){
		return dict.copy();
	}
	
	/**
	 * Factory method that returns an new {@link DefaultDict} of type @param defaultType.
	 * 
	 * DefaultDict in xpresso works similarly to Python's defaultdict.
	 * 
	 * See <a href="https://docs.python.org/2/library/collections.html">collections</a>.
	 * 
	 * @param defaultType	a Class{@code <T>} object
	 * @param <T> 			any type
	 * @return	a new DefaultDict
	 */   
	public static <T> DefaultDict<T> DefaultDict(Class<T> defaultType){
		return new DefaultDict<T>(defaultType);
	}
	
	/**
	 * Factory method that returns an new {@link OrderedDict} from an input {@link Iterable}.
	 * 
	 * The input iterable object has to be an {@code Iterable<tuple>} or
	 * two dimensions or an {@code Iterable<tuple2<String,T>>} or an OrderedDict
	 * 
	 * OrderedDict in xpresso works similarly to Python's OrderedDict.
	 * 
	 * See <a href="https://docs.python.org/2/library/collections.html#collections.OrderedDict">collections</a>.
	 * 
	 * @param iterable	an {@link Iterable}
	 * @param <T> 		any type
	 * @return	new OrderedDict
	 *
	 */   
	public static <T> OrderedDict<T> OrderedDict(Iterable<T> iterable){
		return new OrderedDict<T>(iterable);
	}
	
	/**
	 * Factory method that returns an new empty {@link OrderedDict}.
	 * 
	 * OrderedDict in xpresso works similarly to Python's OrderedDict.
	 * 
	 * See <a href="https://docs.python.org/2/library/collections.html#collections.OrderedDict">collections</a>.
	 * 
	 * @param <T> 	the type of elements in the dict
	 * @return	new OrderedDict
	 *
	 */   
	public static <T> OrderedDict<T> OrderedDict(){
		return new OrderedDict<T>();
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting values in the input iterable.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 * @param iterable	an {@link Iterable} of type T
	 * @param <T> 		any type
	 * @return	new OrderedDict
	 *
	 */   
	public static <T> Bag<T> Bag(Iterable<T> iterable){
		return new Bag<T>(iterable);
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting characters in the input string.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 * @param string	a {@link String}
	 * @return	new Bag
	 *
	 */   
	public static Bag<String> Bag(String string){
		return new Bag<String>(x.str(string));
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting strings in the input array.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * @param string0	the first {@link String} to put in the new Bag
	 * @param string1	the second {@link String} to put in the new Bag
	 * @param otherStrings	all other {@link String}s to put in the new Bag
	 * @return	new Bag
	 * 
	 */   
	public static Bag<String> Bag(String string0,String string1,String... otherStrings){
		Iterable<String> iterable = Helpers.newArrayList(string0,string1,otherStrings);
		return new Bag<String>(iterable);
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting numbers in the input array.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * @param number0	the first {@link Number} to put in the new Bag
	 * @param number1	the second {@link Number} to put in the new Bag
	 * @param otherNumbers	all other {@link Number}s to put in the new Bag
	 * @return	new Bag
	 */ 
	public static Bag<Number> Bag(Number number0,Number number1,Number... otherNumbers){
		Iterable<Number> iterable = Helpers.newArrayList(number0,number1,otherNumbers);
		return new Bag<Number>(iterable);
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained by 
	 * counting bolleans in the input array.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * @param boolean0	the fist {@link Boolean} to put in the new Bag
	 * @param boolean1	the second {@link Boolean} to put in the new Bag
	 * @param otherBooleans	all other {@link Boolean}s to put in the new Bag
	 * @return	new Bag
	 */ 
	public static Bag<Boolean> Bag(Boolean boolean0,Boolean boolean1,Boolean... otherBooleans){
		Iterable<Boolean> iterable = Helpers.newArrayList(boolean0,boolean1,otherBooleans);
		return new Bag<Boolean>(iterable);
	}
	
	/**
	 * Factory method that returns an new {@link Bag} obtained from the input {@link Map}. 
	 * The keys in the input map will used as the new Bag's counted elements, while
	 * the Integer values of the input Map will be used as the respective counts
	 * for those counted elements.
	 * 
	 * {@link Bag} in xpresso works similarly to Python's Counter object.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/collections.html#collections.Counter">collections</a>.
	 * 
	 * @param <T>	the type of elements in the Bag
	 * @param map	a map containing elements to put into new Bag
	 * @return	new Bag
	 */ 
	public static <T> Bag<T> Bag(Map<T,Integer> map){
		return new Bag<T>(map);
	}
	
	/**
	 * Returns the length of the argument, usually it is the number of elements it contains. The len of a scalar or a null is 0.
	 * The len of an Object is 0 unless the object has a property "length" or methods "len", "length", or "size"
	 * 
	 * @param value	any Object
	 * @return the length of the Object
	 * 
	 */ 
	public static int len(Object value){
		if(value instanceof Iterable<?>){
			int counter = 0;
			for(@SuppressWarnings("unused") Object element : (Iterable<?>)value){
				counter++;
			}
			return counter;
		}
		if(value instanceof String){
			return ((String)value).length();
		}
		if(value instanceof Number){
			return 0;
		}
		if(value instanceof Boolean){
			return 0;
		}
		if(value instanceof Lengthful){
			return ((Lengthful)value).len();
		}
		if(value instanceof Map<?,?>){
			return ((Map<?,?>)value).size();
		}
		try{
			return (Integer)(value.getClass().getMethod("length").invoke(value));
		}catch(Exception e){
			//e.printStackTrace();
			
		}
		try{
			return ((Object[])value).length;
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			return (Integer)(value.getClass().getField("length").getInt(value));
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			return (Integer)(value.getClass().getMethod("size").invoke(value));
		}catch(Exception e){
			
		}
		try{
			return (Integer)(value.getClass().getMethod("len").invoke(value));
		}catch(Exception e){
			
		}
		return 0;
	}
	
	/**
	 * Returns the time in seconds since the epoch as a floating point number. 
	 * @return time in seconds
	 */
	public static double time(){
		return Time.time();
	}
	
	/**
	 * An instance of Time class. 
	 * 
	 */
	public static Time Time = new Time();
	
	/**
	 * Returns the running {@link Timer} object. This object can be then stopped and printed:
	 * 
	 * <pre>
	 * {@code
	 *  Timer timer = x.Timer();
	 *  //do something
	 *  x.print(timer.stop());
	 * }
	 * </pre>
	 *  
	 *  Console: 0.133s
	 * @return the running {@link Timer} object
	 */
	public static Timer Timer(){
		return new Timer();
	}
	
	/**
	 * Starts the static {@link java.lang.ThreadLocal} timer of the {@link ThreadTimer} class.
	 * This timer can be started, stopped and printed.
	 * 
	 * The difference between {@code x.timer} based on the {@link ThreadTimer}
	 * and {@code x.Timer()} based on {@link Timer} it that if you use {@code x.timer}
	 * you don't have to 
	 * create any instance of any object. You can simply use the static
	 * reference <pre>{@code x.timer.start()}</pre> and <pre>{@code x.timer.stop();}</pre>
	 * The timer {@code x.timer} is global for the current thread and independent of
	 * any other thread. 
	 * 
	 * Example:
	 * 
	 * <pre>
	 * {@code 
	 *  x.timer.start();
	 *  //do something
	 *  x.print(x.timer.stop());
	 *  
	 *  Console: 0.133s
	 * }
	 * </pre>
	 * 
	 */
	public static ThreadTimer timer = new ThreadTimer(); 
	
	/**
	 * An instance of a {@link TokenStatic} object.
	 * 
	 * Example:
	 * 
	 * <pre>
	 * {@code
	 * String stem = x.Token.stem("Worker");
	 * }
	 * </pre>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * {@code
	 * String stem = x.Token.stem("Marcher", "french");
	 * }
	 * </pre>
	 *  
	 * 
	 */
	public static TokenStatic Token = new TokenStatic();
	
	/**
	 * An instance of a {@link Token} object.
	 * 
	 * Example:
	 * 
	 * <pre>
	 * {@code
	 * String stem = x.Token("Worker").stem();
	 * }
	 * </pre>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * {@code
	 * String stem = x.Token("Marcher").stem("french");
	 * }
	 * </pre>
	 *  
	 * Example:
	 * 
	 * <pre>
	 * {@code
	 * String stem = x.Token("Java8").shape();
	 * }
	 * </pre>
	 * 
	 * <pre>
	 * Console: ULLLD
	 * </pre>
	 * @param string	the string that defines the token
	 * @return new {@link Token} for the given {@link String}
	 */
	public static Token Token(String string) {
		return new Token(string);
	};
	
	/**
	 * Return the double value number rounded to nDigits digits after the decimal point.
	 * If ndigits is omitted, it defaults to zero. The result is a double.
	 * 
	 * @param number	the number to round
	 * @param nDigits	the number of decimals after the decimal point
	 * @return a rounded input value
	 */
	public static double round(double number, int nDigits){
		return (double)Math.round(number * Math.pow(10, nDigits)) / Math.pow(10, nDigits);
	}
	
	/**
	 * Return the double value number rounded to ndigits digits after the decimal point.
	 * If ndigits is omitted, it defaults to zero. The result is a double.
	 * 
	 * @param number	the number to round
	 * @return a rounded input value
	 */
	public static double round(double number){
		return (double)Math.round(number * Math.pow(10, 0)) / Math.pow(10, 0);
	}
	
	/**
	 * 
	 * @param iterable	in {@link Iterable} of booleans
	 * @return Only returns true if all elements of the input {@link Iterable} are true.
	 * 
	 */
	public static boolean all(Iterable<Boolean> iterable){
	    for(Boolean element : iterable){
	    	if (x.isFalse(element)){
	    		return false;
	    	}
	    }
	    return true;
	}
	
	/**
	 * @return Only returns true if the input {@link Predicate}, when applied 
	 * to each element of the input {@link Iterable}, returns true. 
	 * 
	 * @param iterable		an Iterable of type {@code <T>}
	 * @param <T>			any type
	 * @param predicate		any {@link Predicate}
	 * 
	 */
	public static <T> boolean all(Iterable<T> iterable, Predicate<Object> predicate){
	    for(T element : iterable){
	    	if (x.isFalse(element)){
	    		return false;
	    	}
	    }
	    return true;
	}
	
	/**
	 * @return Returns true if at least one element of the input {@link Iterable} is true.
	 * @param iterable an Iterable object 
	 * 
	 */
	public static boolean any(Iterable<Boolean> iterable){
	    for(Boolean element : iterable){
	    	if (x.isTrue(element)){
	    		return true;
	    	}
	    }
	    return false;
	}
	
	/**
	 * @return Returns true if the input {@link Predicate}, when applied 
	 * to at least one element of the input {@link Iterable}, returns true. 
	 * 
	 * @param iterable		an Iterable of type {@code <T>}
	 * @param <T>			any type
	 * @param predicate		any {@link Predicate}
	 * 
	 */
	public static <T> boolean any(Iterable<T> iterable, Predicate<Object> predicate){
	    for(T element : iterable){
	    	if (x.isTrue(element)){
	    		return true;
	    	}
	    }
	    return false;
	}
	
	
	/**
	 * @return Returns the biggest element of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable of type {@code <T>}
	 * @param <T>			any type 
	 * 
	 */
	public static <T extends Comparable<? super T>> T max(Iterable<T> iterable){
		return x.<T>reduce(x.<T>max(), iterable);
	}
	
	/**
	 * @return Returns the biggest element among the input values. 
	 * 
	 * @param value0 	value of type {@code <T>}
	 * @param value1	value of type {@code <T>}
	 * @param values	values of type {@code <T>}
	 * @param <T>						any type
	 * 
	 */
	@SafeVarargs
	public static <T extends Comparable<? super T>> T max(T value0,T value1,T... values){
		return max(Helpers.newArrayList(value0,value1,values));
	}
	
	/**
	 * @return Returns the smallest element of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable of type {@code <T>}
	 * @param <T>			any type
	 * 
	 */
	public static <T extends Comparable<? super T>> T min(Iterable<T> iterable){
		return x.<T>reduce(x.<T>min(), iterable);
	}
	
	/**
	 * @return Returns the smallest element among the input values. 
	 * 
	 * @param value0 	value of type {@code <T>}
	 * @param value1	value of type {@code <T>}
	 * @param values	values of type {@code <T>}
	 * @param <T>						any type
	 * 
	 */
	@SafeVarargs
	public static <T extends Comparable<? super T>> T min(T value0,T value1,T... values){
		return min(Helpers.newArrayList(value0,value1,values));
	}
	
	
	/**
	 * Sums the elements of the input {@link Iterable} and returs the sum. 
	 * 
	 * @param iterable		an Iterable of Numbers
	 * @param <T>			a type that extends a {@link Number}
	 * @return the sum of elements of the input {@link Iterable}
	 * 
	 */
	public static <T extends Number> T sum(Iterable<T> iterable){
		return x.<T>reduce(x.<T>add(), iterable);
	}
	
	/**
	 * Averages the elements of the input {@link Iterable} and returs the average. 
	 * 
	 * @param iterable		an Iterable of Numbers
	 * @param <T>			a type that extends a {@link Number}
	 * @return the average of elemets of the Iterable
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T avg(Iterable<T> iterable){
		x.assertNotEmpty(iterable);
		double result = (x.<T>reduce(x.<T>add(), iterable)).doubleValue()/x.len(iterable);
		Class<?> cls = iterable.iterator().next().getClass();
		if(cls.equals(Double.class)){
			return (T)(Object)result;
		}else if(cls.equals(Float.class)){
			return (T)(Object)result;
		}else{
			return (T)(Object)(int)Math.round(result);
		}
	}
	
	/**
	 * @return Returns the first element of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable
	 * @param <T>			any type
	 * 
	 */
	public static <T> T first(Iterable<T> iterable){
		x.assertNotEmpty(iterable);
		return iterable.iterator().next();
	}
	
	/**
	 * @return Returns the last element of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable
	 * @param <T>			any type
	 * 
	 */
	public static <T> T last(Iterable<T> iterable){
		x.assertNotEmpty(iterable);
		return x.list(iterable).sliceFrom(-1).toScalar();
	}
	
	/**
	 * @return Returns the {@link Iterator} over {@link Integer} elements starting from @param min
	 * with the increment between the values given by @param step.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than @param max. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * @param step		the increment
	 * 
	 */
	public static Iterable<Integer> count(final int min,final int max,final int step){
		Iterable<Integer> generator = new Iterable<Integer>(){
			public Iterator<Integer> iterator(){
				return new Iterator<Integer>(){
					int currentValue = min-step;
					@Override
					public boolean hasNext() {
						if(currentValue < max-1){
							return true;
						}
						return false;
					}

					@Override
					public Integer next() {
						currentValue += step;
						return currentValue;
					}
				};
			}
		};
		return generator;
	}
	
	/**
	 * Alias for compatibility with Python.
	 * 
	 * @return Returns the {@link Iterator} over {@link Integer} elements starting from @param min
	 * with the increment between the values given by @param step.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than @param max. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * @param step		the increment
	 */
	public static Iterable<Integer> range(final int min,final int max,final int step){
		return range(min, max, step);
	}
	
	/**
	 * @return Returns the {@link Iterable} over {@link Integer} elements starting from @param min
	 * with the increment of 1 between the values.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than @param max. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * 
	 */
	public static Iterable<Integer> count(int min,int max){
		return count(min,max,1);
	}
	/**
	 * Alias for compatibility with Python.
	 * 
	 * @return Returns the {@link Iterable} over {@link Integer} elements starting from @param min
	 * with the increment of 1 between the values.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than @param max. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 */
	public static Iterable<Integer> range(final int min,final int max){
		return range(min, max, 1);
	}
	
	/**
	 * @return Returns the {@link Iterable} over {@link Integer} elements starting from 0
	 * with the increment of 1 between the values.
	 * 
	 * The last returned value by the Iterator will be @param max - 1. 
	 * 
	 * @param max		end value (not returned)
	 * 
	 */
	public static Iterable<Integer> countTo(int max){
		return count(0,max,1);
	}
	
	/**
	 * @return Returns an infinite {@link Iterable} over {@link Integer} elements
	 * starting from @param min with the increment of 1 between the values. 
	 * 
	 * @param min	start value
	 * 
	 */
	public static Iterable<Integer> countFrom(final int min){
		Iterable<Integer> generator = new Iterable<Integer>(){
			public Iterator<Integer> iterator(){
				return new Iterator<Integer>(){
					int currentValue = min-1;
					@Override
					public boolean hasNext() {
						return true;
					}

					@Override
					public Integer next() {
						currentValue += 1;
						return currentValue;
					}
				};
			}
		};
		return generator;
	}

	/**
	 * @return Returns the {@link Iterable} over {@link Double} elements starting from @param min
	 * with the increment between the values given by @param step.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Double} less than @param max. 
	 * 
	 * @param min		start value
	 * @param max		end value (not returned)
	 * @param step		the increment
	 * 
	 */
	public static Iterable<Double> countReal(final double min,final double max,final double step){
		Iterable<Double> generator = new Iterable<Double>(){
			public Iterator<Double> iterator(){
				return new Iterator<Double>(){
					double currentValue = min-step;
					@Override
					public boolean hasNext() {
						if(currentValue < max-1){
							return true;
						}
						return false;
					}

					@Override
					public Double next() {
						currentValue += step;
						return currentValue;
					}
				};
			}
		};
		return generator;
	}
	
	/**
	 * @return Returns the {@link Iterable} over {@link Integer} elements starting from 0
	 * with the increment between the values given by @param step.
	 * 
	 * The last returned value by the Iterable will be the biggest {@link Integer} less than
	 * the length of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable of any type
	 * @param <T>			the type of elements of the input Iterable
	 * @param step		the increment
	 * 
	 */
	public static <T> Iterable<Integer> count(final Iterable<T> iterable, final int step){
		Iterable<Integer> generator = new Iterable<Integer>(){
			public Iterator<Integer> iterator(){
				return new Iterator<Integer>(){
					Iterator<T> iter = iterable.iterator();
					int currentValue = 0-step;
					@Override
					public boolean hasNext() {
						return iter.hasNext();
					}

					@Override
					public Integer next() {
						currentValue += step;
						for(Integer i : countTo(step)){
							currentValue+=i;
							iter.next();
						}
						return currentValue;
					}
				};
			}
		};
		return generator;
	}
	
	/**
	 * @return Returns the {@link Iterable} over {@link Integer} elements starting from 0
	 * with the increment of 1 between the values.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than
	 * the length of the input {@link Iterable}. 
	 * 
	 * @param iterable		an Iterable of any type
	 * @param <T>			the type of elements of the input Iterable
	 * 
	 */
	public static <T> Iterable<Integer> count(final Iterable<T> iterable){
		return count(iterable,1);
	}
	
	/**
	 * @return Returns the {@link Iterable} over {@link Integer} elements starting from 0
	 * with the increment of step between the values.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than
	 * the length of the input array. 
	 * 
	 * @param array		an array
	 * @param <T>		type of elements of the array
	 * @param step		the increment
	 * 
	 */
	public static <T> Iterable<Integer> count(T[] array, int step){
		return count(Helpers.newArrayList(array),step);
	}
	
	/**
	 * @return Returns the {@link Iterable} over {@link Integer} elements starting from 0
	 * with the increment of 1 between the values.
	 * 
	 * The last returned value by the Iterator will be the biggest {@link Integer} less than
	 * the length of the input array. 
	 * 
	 * @param array		an array
	 * @param <T>		type of elements of the array
	 * 
	 */
	public static <T> Iterable<Integer> count(T[] array){
		return count(Helpers.newArrayList(array),1);
	}
	
	/**
	 * Applies the input {@link Function} to each element of the input {@link Iterable}
	 * @return a new {@link Iterable} containing the Functions' outputs of each element.
	 * 
	 * Preserves the order of elements.
	 *  
	 * @param iterable	an iterable or type I
	 * @param function	a function to apply to elements of the {@link Iterable}
	 * @param <I>		input element type
	 * @param <O>		output element type
	 * 
	 */
	public static <I,O> Iterable<O> map(Function<Object,O> function, Iterable<I> iterable){
		if(iterable instanceof set<?>){
			set<O> newSet = new set<O>();
			for (I element : iterable){
				newSet.put(function.apply(element));
			}
			return newSet;
		}else{
			list<O> newList = new list<O>();
			for (I element : iterable){
				newList.append(function.apply(element));
			}
			return newList;
		}
	}
	
	/**
	 * Applies function of two arguments cumulatively to the items of iterable,
	 * from left to right, so as to reduce the iterable to a single value.
	 * For example, <pre>{@code x.reduce(lambda x, y: x+y, [1, 2, 3, 4, 5])}</pre> calculates ((((1+2)+3)+4)+5).
	 * The left argument, {@code x}, is the accumulated value and the right argument,
	 * y, is the update value from the iterable.
	 * 
	 * If the optional initializer is present, it is placed before the items of the iterable in the calculation,
	 * and serves as a default when the iterable is empty.
	 * If initializer is not given and iterable contains only one item, the first item is returned.
	 * 
	 * Roughly equivalent to:
	 * <pre>
	 * {@code
	 * 
	 * def reduce(function, iterable, initializer):
	 * 		it = iter(iterable)
	 * 		accum_value = initializer
	 * 		for x in it:
	 * 			accum_value = function(accum_value, x)
	 * 		return accum_value
	 * }
	 * </pre>
	 * 
	 * Preserves the order of elements.
	 *  
	 * @param iterable	an iterable of type I
	 * @param initializer the initializer
	 * @param function	a function to apply to every pair of subsequent elements
	 * @param <I>		input and output element types
	 * @return the final value
	 * 
	 */
	public static <I> I reduce(Function<tuple2<I,I>,I> function, Iterable<I> iterable, I initializer){
		x.assertNotNull(initializer);
		list<I> completeList = x.listOf(initializer).extend(iterable);
		if(x.len(completeList) == 1) {
			return completeList.get(0);
		}
		Iterator<I> iter = completeList.iterator();
		I output = function.apply(tuple2.valueOf(iter.next(), iter.next()));
		while(iter.hasNext()){
			output = function.apply(tuple2.valueOf(output, iter.next()));
		}
		return output;
	}
	
	/**
	 * Applies function of two arguments cumulatively to the items of iterable,
	 * from left to right, so as to reduce the iterable to a single value.
	 * For example, <pre>{@code x.reduce(lambda x, y: x+y, [1, 2, 3, 4, 5])}</pre> calculates ((((1+2)+3)+4)+5).
	 * The left argument, {@code x}, is the accumulated value and the right argument,
	 * y, is the update value from the iterable.
	 *  
	 * Roughly equivalent to:
	 * <pre>
	 * {@code
	 * 
	 * def reduce(function, iterable):
	 * 		it = iter(iterable)
     *   	try:
     *      	 initializer = next(it)
     *   	except StopIteration:
     *      	 raise TypeError('reduce() of empty sequence with no initial value')
	 * 		accum_value = initializer
	 * 		for x in it:
	 * 			accum_value = function(accum_value, x)
	 * 		return accum_value
	 * }
	 * </pre>
	 * 
	 * 
	 * Preserves the order of elements.
	 *  
	 * @param iterable	an iterable of type I
	 * @param function	a function to apply to every pair of subsequent elements
	 * @param <I>		input and output element types
	 * @return the final value
	 * 
	 * 
	 */
	public static <I> I reduce(Function<tuple2<I,I>,I> function, Iterable<I> iterable){
		if(x.len(iterable) == 1) {
			return iterable.iterator().next();
		}
		Iterator<I> iter = iterable.iterator();
		I output = function.apply(tuple2.valueOf(iter.next(), iter.next()));
		while(iter.hasNext()){
			output = function.apply(tuple2.valueOf(output, iter.next()));
		}
		return output;
	}
	
	/**
	 * Applies the input {@link Predicate} to each element of the input {@link Iterable}
	 * @return a new {@link Iterable} containing those elements of the input {@link Iterable}
	 * for which the predicate is true.
	 * 
	 * Preserves the order of elements.
	 *  
	 * @param iterable	an {@link Iterable} of type T
	 * @param predicate	a {@link Predicate}
	 * @param <T>	element types of the iterable
	 * 
	 */
	public static <T> Iterable<T> filter(Predicate<Object> predicate, Iterable<T> iterable){
		if(iterable instanceof set<?>){
			set<T> newSet = new set<T>();
			for (T element : iterable){
				if(predicate.apply(element)){
					newSet.put(element);	
				}
			}
			return newSet;
		}else{
			list<T> newList = new list<T>();
			for (T element : iterable){
				if(predicate.apply(element)){
					newList.append(element);	
				}
			}
			return newList;
		}
	}
	
	/**
	 * @return Returns a set containing all unique elements from all input {@link Iterable}s.
	 *  	
	 * @param iterables	an array of {@link Iterable}s of type {@code <T>}
	 * @param <T>	element type of each input iterable
	 * 
	 */
	@SafeVarargs
	public static <T> set<T> union(Iterable<T>... iterables){
		set<T> result = x.set(iterables[0]);
		if (iterables.length > 1){
			for (int i = 1;i<iterables.length;i++){
				for (T element : iterables[i]){
					result.put(element);
				}
			}
		}
		return result;
	}
		
	/**
	 * @return Returns an {@link Iterable} of {@link tuple}s (int index, T value) from the input {@link Iterable} of type T.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @param iterable		an {@link Iterable} of type T
	 * @param startCount	the value of index of the first tuple
	 * @param <T> 			any type
	 *
	 */   
	public static <T> Iterable<tuple2<Integer,T>> enumerate(final Iterable<T> iterable, final int startCount){
		Iterable<tuple2<Integer,T>> generator = new Iterable<tuple2<Integer,T>>(){
			public Iterator<tuple2<Integer,T>> iterator(){
				return new Iterator<tuple2<Integer,T>>(){
					int currentCount = startCount-1;
					Iterator<T> iter = iterable.iterator();
					@Override
					public boolean hasNext() {
						return iter.hasNext();
					}
					@Override
					public tuple2<Integer,T> next() {
						currentCount++;
						return tuple2.valueOf(currentCount, iter.next());
					}
				};
			}
		};
		return generator;
	}
	
	
	/**
	 * @return Returns an {@link Iterable} of {@link tuple}s (int index, T value) from the input {@link Iterable} of type T.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @param iterable		an {@link Iterable} of type T
	 * @param <T> 			any type
	 *
	 */  
	public static <T> Iterable<tuple2<Integer,T>> enumerate(Iterable<T> iterable){
		return enumerate(iterable,0);
	}
	
	/**
	 * @return Returns an {@link Iterable} of {@link tuple}s (int index, T value) from the input values.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @param value0		the first value
	 * @param value1		the second value
	 * @param values		the array of all other values
	 * @param <T> 			any type
	 *
	 */  
	@SafeVarargs
	public static <T> Iterable<tuple2<Integer,T>> enumerate(T value0, T value1, T... values){
		List<T> lst = Helpers.newArrayList(value0,value1,values);
		return enumerate(lst,0);
	}
	
	/**
	 * @return Returns an {@link Iterable} of {@link tuple}s (int index, String character) for the input string,
	 * thet iterates over the characters of the string.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @param string		a {@link String} object
	 * @param startCount	the value if the index of the first tuple in the output Iterable
	 *
	 */  
	public static Iterable<tuple2<Integer,String>> enumerate(String string, int startCount){
		str str = x.str(string);
		return enumerate(str,startCount);
	}
	
	/**
	 * @return Returns an {@link Iterable} of {@link tuple}s (int index, String character) for the input string,
	 * thet iterates over the characters of the string.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @param string		a {@link String} object
	 *
	 */  
	public static Iterable<tuple2<Integer,String>> enumerate(String string){
		return enumerate(string,0);
	}
	
	/**
	 * @return Returns an {@link Iterable} of {@link tuple}s (int index, String character) for the input array,
	 * thet iterates over the elements of the array.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @param array			an array or type T
	 * @param startCount	the value if the index of the first tuple in the output Iterable
	 * @param <T> 			any type
	 *
	 */  
	public static <T> Iterable<tuple2<Integer,T>> enumerate(T[] array, int startCount){
		return enumerate(Helpers.newArrayList(array),startCount);
	}
	
	/**
	 * @return Returns an {@link Iterable} of {@link tuple}s (int index, String character) for the input array,
	 * thet iterates over the elements of the array.
	 * 
	 * enumerate in xpresso works similarly to Python's enumerate.
	 * 
	 * See the page about Python's <a href="https://docs.python.org/2/library/functions.html#enumerate">enumerate</a>
	 * 		for more details.
	 * 
	 * @param array			an array of type T
	 * @param <T> 			any type
	 *
	 */  
	public static <T> Iterable<tuple2<Integer,T>> enumerate(T[] array){
		return enumerate(Helpers.newArrayList(array),0);
	}	
	
	/**
	 * 
	 * @return Makes an {@link Iterable} returning elements from the input iterable and saving a copy of each.
	 * When the input  iterable is exhausted, return elements from the saved copy. Repeats maxCount times.
	 * 
	 * Equivalent to: <pre>{@code x.cycle(x.listOf("A","B","C","D") --> A B C D A B C D A B C D ...}</pre>
	 * 
	 * @param iterable	an {@link Iterable}
	 * @param <T>	the type of elements in the input Iterable
	 * @param maxCount	the number of repetitions 
	 */
	public static <T> Iterable<T> cycle(final Iterable<T> iterable, final Integer maxCount){
		Iterable<T> generator = new Iterable<T>(){
			public Iterator<T> iterator(){
				return new Iterator<T>(){
					Iterator<T> iter = iterable.iterator();
					int currentCount = 0;
					@Override
					public boolean hasNext() {
						if(currentCount < maxCount || maxCount == null){
							if (!iter.hasNext()){
								iter = iterable.iterator();
							}
							return iter.hasNext();	
						}
						return false;
					}
					@Override
					public T next() {
						currentCount++;
						return iter.next();
					}
				};
			}
		};
		return generator;
	}
	
	/**
	 * 
	 * @return Makes an {@link Iterable} returning elements from the input iterable and saving a copy of each.
	 * When the input  iterable is exhausted, return elements from the saved copy. Repeats indifinitely.
	 * 
	 * Equivalent to: <pre>{@code x.cycle(x.listOf("A","B","C","D") --> A B C D A B C D A B C D ...}</pre>
	 * 
	 * @param iterable	an {@link Iterable}
	 * @param <T>	the type of elements in the input Iterable
	 * 
	 */
	public static <T> Iterable<T> cycle(final Iterable<T> iterable){
		return cycle(iterable,null);
	}
	
	/**
	 * 
	 * @return Makes an {@link Iterable} returning characters from the input string and saving a copy of these characters.
	 * When the above iterable is exhausted, return elements from the saved copy. Repeats indifinitely.
	 * 
	 * Equivalent to: <pre>{@code x.cycle("ABCD") --> A B C D A B C D A B C D ... }</pre>
	 * 
	 * @param string	a string that contains the characters to repeat
	 * 
	 */
	public static Iterable<String> cycle(final String string){
		return cycle(x.str(string));
	}

	/**
	 * 
	 * @return Makes an {@link Iterable} returning characters from the input string and saving a copy of these characters.
	 * When the above iterable is exhausted, return elements from the saved copy. Repeats maxCount times.
	 * 
	 * Equivalent to: <pre>{@code x.cycle("ABCD") --> A B C D A B C D A B C D ... } </pre>
	 * 
	 * @param string	a string that contains the characters to repeat
	 * @param maxCount	the number of repetitions 
	 * 
	 */
	public static Iterable<String> cycle(final String string, final int maxCount){
		return cycle(x.str(string),maxCount);
	}
	
	/**
	 * @return Make an {@link Iterable} that returns the input value of type T
	 * over and over again for maxCount times.
	 * 
	 * @param value		the value to repeat
	 * @param <T>	the type of the value
	 * @param maxCount	the number of repetitions 
	 */
	public static <T> Iterable<T> repeat(final T value, final Integer maxCount){
		Iterable<T> generator = new Iterable<T>(){
			public Iterator<T> iterator(){
				return new Iterator<T>(){
					int currentCount = 0;
					@Override
					public boolean hasNext() {
						if(currentCount < maxCount || maxCount == null){
							return true;	
						}
						return false;
					}
					@Override
					public T next() {
						currentCount++;
						return value;
					}
				};
			}
		};
		return generator;
	}

	/**
	 * @return Make an infinite {@link Iterable} that returns the input value of type T
	 * over and over again.
	 * 
	 * @param value	the value to repeat
	 * @param <T>	the type of the value
	 */
	public static <T> Iterable<T> repeat(final T value){
		return repeat(value, null);
	}
	
	/**
	 * Sorts the values of the input {@link Iterable} of type T according to
	 * the evaluation function {@link Function} which is applied to each element
	 * of the input Iterable.
	 * 
	 * @param iterable	an iterable of type T
	 * @param <T>		any type
	 * @param function	a function that takes an object and retursn a {@link Comparable}
	 * @param reverse	if true, then the values are sorted from biggest to smallest 
	 * @return a sorted {@link Iterable} of type T
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Iterable<T> sort(Iterable<T> iterable,Function<Object,? extends Comparable<?>> function,boolean reverse){
		class KeyValue<K extends Comparable<K>,V> implements Comparable<KeyValue<K,V>>, Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6209178251903971368L;
			
			private K key;
			private V value;
			
			public KeyValue(K key, V value){
				this.key = key;
				this.value = value;
			}
			
			public K getKey(){
				return this.key;
			}
			
			public V getValue(){
				return this.value;
			}
			
			public int compareTo(KeyValue<K,V> keyValue){
				return this.key.compareTo(keyValue.getKey());
			}
			
			@Override
			public String toString(){
				return this.key.toString() + '~' + this.value.toString();
			}
		}

		ArrayList<KeyValue<?,T>> keyValues = new ArrayList<KeyValue<?,T>>();
		for (T element: iterable){
			if(function != null){
				keyValues.add(new KeyValue(function.apply(element),element));	
			}else{
				keyValues.add(new KeyValue((Comparable<?>)element,element));
			}
		}
		
		Collections.sort(keyValues);
		
		if(reverse){
			Collections.reverse(keyValues);
		}
		ArrayList<T> resultList = new ArrayList<T>();
		for (KeyValue<?,T> keyValue : keyValues){
			resultList.add(keyValue.getValue());
		}
		if(iterable instanceof list<?>){
			return x.list(resultList);
		}
		return resultList;
	}
	
	public static <T> Iterable<T> sort(Iterable<T> iterable,Function<Object,? extends Comparable<?>> function){
		return sort(iterable,function,false);
	}
	
	public static <T extends Comparable<T>> Iterable<T> sort(Iterable<T> iterable, boolean reverse){
		return sort(iterable,null,reverse);
	}
	
	public static <T extends Comparable<T>> Iterable<T> sort(Iterable<T> iterable){
		return sort(iterable,null,false);
	}
	
	public static str sort(str str,Function<Object,? extends Comparable<?>> function,boolean reverse){
		return x.str(sort(str.toArrayList(),function,reverse));
	}
	
	public static str sort(str str,Function<Object,? extends Comparable<?>> function){
		return x.str(sort(str.toArrayList(),function,false));
	}
	
	public static str sort(str str){
		return x.str(sort(str.toArrayList(),null,false));
	}
	
	public static str sort(str str, boolean reverse){
		return x.str(sort(str.toArrayList(),null,reverse));
	}
	
	/**
	 * Reverse the order of elements of the input {@link Iterable}
	 * @param iterable {@link Iterable} of type T
	 * @param <T>	any type
	 * @return the input iterable in the reverse order
	 * 
	 */
	public static <T> Iterable<T> reverse(Iterable<T> iterable){
		ArrayList<T> newArrayList = Helpers.newArrayList(iterable);
		Collections.reverse(newArrayList);
		return x.list(newArrayList);

	}
	
	/**
	 * Reverse the order of charcters in the input {@link str}
	 * @param str a {@link str} object
	 * @return the input str with the characters in the reverse order
	 */
	public static str reverse(str str) {
		return x.str(reverse(Helpers.newArrayList(str)));
	}
	
	/**
	 * Returns the N largest elements of the input {@link Iterable}.
	 * @param iterable {@link Iterable} of type T
	 * @param <T>	any type
	 * @param N	the desired number of results
	 * @return an {@link Iterable} that contains the N largest elements of the input {@link Iterable} 
	 * 
	 */
	public static <T extends Comparable<T>> Iterable<T> largestN(Iterable<T> iterable, int N){
		return x.list(sort(iterable,true)).sliceTo(N);
	}
	
	/**
	 * Returns the N smallest elements of the input {@link Iterable}.
	 * @param <T>	any type
	 * @param N	the desired number of results
	 * @param iterable	an {@link Iterable} of type T
	 * @return an {@link Iterable} that contains the N smallest elements of the input {@link Iterable}
	 */
	public static <T extends Comparable<T>> Iterable<T> smallestN(Iterable<T> iterable, int N){
		return x.list(sort(iterable)).sliceTo(N);
	}
	
	/**
	 * @return Only returns true if the input {@link Iterable} contains the value.
	 * @param iterable {@link Iterable} of type T
	 * @param <T>	any type
	 * @param value	a value of type T 
	 * 
	 */
	public static <T> boolean contains(Iterable<T> iterable, T value){
		if (iterable instanceof Collection<?>) {
			  return ((Collection<?>)iterable).contains(value);
		}else{
			for (T val : iterable){
				if (val.equals(value)){
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * @return This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * 
	 * Example:
	 * <pre>
	 * {@code
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * }
 	 * </pre>
 	 * 
 	 * <pre>
 	 * {@code
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
     * }
     * </pre>
	 * 
	 */
	public static list<tuple> zip(){
		return x.list();
	}
	
	/**
	 * @return This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * @param <T0> type of Iterable0
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
     * }
     * </pre>
	 * 
	 */
	public static <T0> list<tuple> zip(Iterable<T0> iterable0){
		list<tuple> result = x.list();
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tuple(value));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * @return This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * @param iterable1 [, iterable2[, iterable3]...]
	 * @param <T0>	type of Iterable0
	 * @param <T1>	type of Iterable1
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
     * }
     * </pre>
	 * 
	 */
	public static <T0,T1> list<tuple> zip(Iterable<T0> iterable0,Iterable<T1> iterable1){
		list<tuple> result = x.list();
		list<T1> list1 = x.list(iterable1);
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				int index = (int)index__value.get(0);
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tuple(value,list1.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * @return This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * @param iterable1 [, iterable2[, iterable3]...]
	 * @param iterable2 [, iterable3...]
	 * @param <T0>	type of Iterable0
	 * @param <T1>	type of Iterable1
	 * @param <T2>	type of Iterable2
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
     * }
     * </pre>
	 * 
	 */
	public static <T0,T1,T2> list<tuple> zip(Iterable<T0> iterable0,Iterable<T1> iterable1,Iterable<T2> iterable2){
		list<tuple> result = x.list();
		list<T1> list1 = x.list(iterable1);
		list<T2> list2 = x.list(iterable2);
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				int index = (int)index__value.get(0);
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tuple(value,list1.get(index),list2.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * @return This method returns a list of tuples, where the i-th tuple contains the i-th element
	 * from each of the argument {@link Iterable}s. The returned list is truncated in length
	 * With a single sequence argument, it returns a list of 1-tuples. With no arguments, it returns an empty list.
	 * @param iterable0 [, iterable1[, iterable2]...]
	 * @param iterable1 [, iterable2[, iterable3]...]
	 * @param iterable2 [, iterable3]
	 * @param iterable3 an {@link Iterable}
	 * @param <T0>	type of Iterable0
	 * @param <T1>	type of Iterable1
	 * @param <T2>	type of Iterable2
	 * @param <T3>	type of Iterable3
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
 	 * x.print(zip(x.listOf(1,3,5),x.listOf(2,4,6)))
 	 * 
 	 * Console:
     * [(1, 2), (3, 4), (5, 6)]
     * }
     * </pre>
	 * 
	 */
	public static <T0,T1,T2,T3> list<tuple> zip(Iterable<T0> iterable0,Iterable<T1> iterable1,Iterable<T2> iterable2,Iterable<T3> iterable3){
		list<tuple> result = x.list();
		list<T1> list1 = x.list(iterable1);
		list<T2> list2 = x.list(iterable2);
		list<T3> list3 = x.list(iterable3);
		try{
			for(tuple index__value : x.enumerate(iterable0)){
				int index = (int)index__value.get(0);
				@SuppressWarnings("unchecked")
				T0 value = (T0)index__value.get(1);
				result.append(x.tuple(value,list1.get(index),list2.get(index),list3.get(index)));
			}	
		}catch(Exception e){
			
		}
		return result;
	}
	
	/**
	 * Does the opposite of zip.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * l = x.listOf(x.tupleOf(1,2), x.tupleOf(3,4), x.tupleOf(5,6));
 	 * x.print(x.unzip(l))
 	 * 
 	 * Console:
     * ([1, 3, 5], [2, 4, 6])
     * }
     * </pre>
	 * 
	 * @param iterable of tuples
	 * @param class0 the class of first element of each tuple in the {@link Iterable}
	 * @param <T0>	type of value0 in the tuple
	 * @return a tuple of lists. Each list of type class0, class1, ... 
	 */
	@SuppressWarnings("unchecked")
	public static <T0> tuple1<list<T0>> unzip(Iterable<tuple> iterable,Class<T0> class0){
		list<T0> list0 = x.list();
		for (tuple T0__ : iterable){
			list0.append((T0)T0__.get(0));
		}
		return tuple1.valueOf(list0);
	}
	
	/**
	 * Does the opposite of zip.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * l = x.listOf(x.tupleOf(1,2), x.tupleOf(3,4), x.tupleOf(5,6));
 	 * x.print(x.unzip(l))
 	 * 
 	 * Console:
     * ([1, 3, 5], [2, 4, 6])
     * }
     * </pre>
	 * 
	 * @param iterable of tuples
	 * @param class0 the class of first element of each tuple in the {@link Iterable}
	 * @param class1 the class of second element of each tuple in the {@link Iterable}
	 * @param <T0>	type of value0 in the tuple
	 * @param <T1>	type of value1 in the tuple
	 * @return a tuple of lists. Each list of type class0, class1, ... 
	 */
	@SuppressWarnings("unchecked")
	public static <T0,T1> tuple2<list<T0>,list<T1>> unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1){
		list<T0> list0 = x.list();
		list<T1> list1 = x.list();
		for (tuple T0__T1 : iterable){
			list0.append((T0)T0__T1.get(0));
			list1.append((T1)T0__T1.get(1));
		}
		return tuple2.valueOf(list0,list1);
	}
	
	/**
	 * Does the opposite of zip.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * l = x.listOf(x.tupleOf(1,2), x.tupleOf(3,4), x.tupleOf(5,6));
 	 * x.print(x.unzip(l))
 	 * 
 	 * Console:
     * ([1, 3, 5], [2, 4, 6])
     * }
     * </pre>
	 * 
	 * @param iterable of tuples
	 * @param class0 the class of first element of each tuple in the {@link Iterable}
	 * @param class1 the class of second element of each tuple in the {@link Iterable}
	 * @param class2 the class of third element of each tuple in the {@link Iterable}
	 * @param <T0>	type of value0 in the tuple
	 * @param <T1>	type of value1 in the tuple
	 * @param <T2>	type of value2 in the tuple
	 * @return a tuple of lists. Each list of type class0, class1, ... 
	 */
	@SuppressWarnings("unchecked")
	public static <T0,T1,T2> tuple3<list<T0>,list<T1>,list<T2>> unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1,Class<T2> class2){
		list<T0> list0 = x.list();
		list<T1> list1 = x.list();
		list<T2> list2 = x.list();
		for (tuple T0__T1__T2 : iterable){
			list0.append((T0)T0__T1__T2.get(0));
			list1.append((T1)T0__T1__T2.get(1));
			list2.append((T2)T0__T1__T2.get(2));
		}
		return tuple3.valueOf(list0,list1,list2);
	}
	
	/**
	 * Does the opposite of zip.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * l = x.listOf(x.tupleOf(1,2), x.tupleOf(3,4), x.tupleOf(5,6));
 	 * x.print(x.unzip(l))
 	 * 
 	 * Console:
     * ([1, 3, 5], [2, 4, 6])
     * }
     * </pre>
	 * 
	 * @param iterable of tuples
	 * @param class0 the class of first element of each tuple in the {@link Iterable}
	 * @param class1 the class of second element of each tuple in the {@link Iterable}
	 * @param class2 the class of third element of each tuple in the {@link Iterable}
	 * @param class3 the class of fourth element of each tuple in the {@link Iterable}
	 * @param <T0>	type of value0 in the tuple
	 * @param <T1>	type of value1 in the tuple
	 * @param <T2>	type of value2 in the tuple
	 * @param <T3>	type of value3 in the tuple
	 * @return a tuple of lists. Each list of type class0, class1, ... 
	 */
	@SuppressWarnings("unchecked")
	public static <T0,T1,T2,T3> tuple4<list<T0>,list<T1>,list<T2>,list<T3>>  unzip(Iterable<tuple> iterable,Class<T0> class0,Class<T1> class1,Class<T2> class2,Class<T3> class3){
		list<T0> list0 = x.list();
		list<T1> list1 = x.list();
		list<T2> list2 = x.list();
		list<T3> list3 = x.list();
		for (tuple T0__T1__T2__T3 : iterable){
			list0.append((T0)T0__T1__T2__T3.get(0));
			list1.append((T1)T0__T1__T2__T3.get(1));
			list2.append((T2)T0__T1__T2__T3.get(2));
			list3.append((T3)T0__T1__T2__T3.get(3));
		}
		return tuple4.valueOf(list0,list1,list2,list3);
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from a {@link String}. 
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * String jsonString = x.Json("Hello").toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console: "Hello"
	 *  
	 * Compare that with:
	 * 
	 * x.print("Hello");
	 * 
	 *  Console: Hello
	 *  }
	 * </pre>
	 * 
	 * @param string		a String object
	 * @param <O>			the type of the output object
	 * @return 				a Json object 
	 */
	public static <O> Json<O> Json(String string){
		return new Json<O>(string);
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from a {@link Map}. 
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Map<String,Integer> map = Helpers.newHashMap();
	 * map.put("a",1);
	 * map.put("b",2);
	 * String jsonString = x.Json(map).toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console {"a": 1, "b": 2}
	 * }
	 * </pre>
	 * 
	 * @param o				a Map object
	 * @param <O>			the type of the output object
	 * @return 				a Json object 
	 */
	public static <O> Json<O> Json(Map<?,?> o){
		return new Json<O>(o);
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from an {@link Iterable}. 
	 * 
	 * <pre>
	 * {@code
	 * Example 1:
	 * 
	 * list<String> lst = x.list("a","b","c");
	 * String jsonString = x.Json(lst).toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console {"a", "b", "c"}
	 *  
	 * Example 2:
	 * dict<Integer> dic = x.list(x.tuple("a":1),x.tuple("b":2));
	 * String jsonString = x.Json(lst).toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console {"a": 1, "b": 2}
	 * }
	 * </pre>
	 * 
	 * @param o				an Iterable
	 * @param <O>			the type of the output object
	 * @return 				a Json object 
	 */
	public static <O> Json<O> Json(Iterable<?> o){
		return new Json<O>(o);
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from a {@link tuple}. 
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Map<String,Integer> map = Helpers.newHashMap();
	 * map.put("a",1);
	 * map.put("b",2);
	 * String jsonString = x.Json(map).toString();
	 * 
	 * x.print(jsonString);
	 * 
	 *  Console {"a": 1, "b": 2}
	 * }
	 * </pre>
	 * 
	 * @param v				an Integer object
	 * @param <O>			the type of the output object
	 * @return 				a Json object 
	 */	
	public static <O> Json<O> Json(Integer v){
		return new Json<O>(v);
	}
	
	/**
	 * Factory method that creates a new xpresso {@link Json} object from a {@link Integer}. 
	 *  
	 * @param o				a tuple object
	 * @param <O>			the type of the output object
	 * @return 				a Json object 
	 */	
	public static <O> Json<O> Json(tuple o){
		return new Json<O>(o);
	}
	
	/**
	 * Factory method that creates a new Regex object from a string 
	 * regular expression. I takes an optional parameter flags
	 * (the same values as {@link Pattern}'s flags).
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Regex mama = x.Regex("\\bmama\\b", Regex.CASE_INSENSITIVE);
	 * }
	 * </pre> 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @param flags 			flags (standard {@link Pattern} flags values are accepted)
	 * @return 					a Regex object 
	 */
	public static Regex Regex(String regularExpression,int flags){
		return new Regex(regularExpression,flags);
	}
	
	/**
	 * Factory method that creates a new Regex object from a string 
	 * regular expression. I takes an optional parameter flags
	 * (the same values as {@link Pattern}'s flags).
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Regex mama = x.Regex("\\bmama\\b", Regex.CASE_INSENSITIVE);
	 * }
	 * </pre> 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static Regex Regex(String regularExpression){
		return new Regex(regularExpression,0);
	}
	
	/**
	 * Factory method that creates a new case insensitive
	 * Regex object from a string 
	 * regular expression.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Regex mama = x.RegexNoCase("\\bmama\\b");
	 * }
	 * </pre> 
	 * 
	 * @param regularExpression	a string with a valid Java regular expression
	 * @return 					a Regex object 
	 */
	public static Regex RegexNoCase(String regularExpression){
		return new Regex(regularExpression,Regex.CASE_INSENSITIVE);
	}
	
	/**
	 * @return Factory method that creates a new Regex object with given optional flags. 
	 * It takes a dictionary instead of the string and works as follows.
	 * 
	 * The translator dict is a {@code dict<String>} that contains pairs of the form:
	 * 
	 * (regular expressions string, replacement string)
	 * 
	 * When you call the {@link Regex#sub(str)} or the  {@link Regex#sub(String)}
	 * method of such a Regex object, the algorithms replaces each regular expressions string
	 * from the translator dict by the corresponding replacement string.
	 * 
	 * <pre>
	 * {@code

	 * Example:
	 * 
	 * dict\<String\> happyReplacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));
	 *
	 * text = x.Regex(happyReplacer).sub(text);
	 * }
	 * </pre> 
	 * 
	 * @param translator		a dict with string to string mapping
	 * @param flags				usual flags of {@link Pattern}
	 * @return 					a Regex object 
	 */
	public static Regex Regex(dict<String> translator,int flags){
		return new Regex(translator,flags);
	}
	
	/**
	 * @return Factory method that creates a new Regex object with given optional flags. 
	 * It takes a dictionary instead of the string and works as follows.
	 * 
	 * The translator dict is a {@code dict<String>} that contains pairs of the form:
	 * 
	 * (regular expressions string, replacement string)
	 * 
	 * When you call the {@link Regex#sub(str)} or the  {@link Regex#sub(String)}
	 * method of such a Regex object, the algorithms replaces each regular expressions string
	 * from the translator dict by the corresponding replacement string.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * dict\<String\> happyReplacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));
	 *
	 * text = x.Regex(happyReplacer).sub(text);
	 * }
	 * </pre> 
	 * 
	 * @param translator		a dict with string to string mapping
	 * @return 					a Regex object 
	 */
	public static Regex Regex(dict<String> translator){
		return new Regex(translator,0);
	}
	
	/**
	 * @return Factory method that creates a new case insensitive Regex object. 
	 * It takes a dictionary instead of the string and works as follows.
	 * 
	 * The translator dict is a {@code dict<String>} that contains pairs of the form:
	 * 
	 * (regular expressions string, replacement string)
	 * 
	 * When you call the {@link Regex#sub(str)} or the  {@link Regex#sub(String)}
	 * method of such a Regex object, the algorithms replaces each regular expressions string
	 * from the translator dict by the corresponding replacement string.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * dict\<String\> happyReplacer = x.dict(x.tuple("bad","good"),x.tuple("small","big"),x.tuple("hard","easy"));
	 *
	 * text = x.Regex(happyReplacer).sub(text);
	 * }
	 * </pre> 
	 * 
	 * @param translator		a dict with string to string mapping
	 * @return 					a Regex object 
	 */
	public static Regex RegexNoCase(dict<String> translator){
		return new Regex(translator,Regex.CASE_INSENSITIVE);
	}
	
	
	/**
	 * @return Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * }
	 * </pre>
	 * 
	 * @param startIndex	a start index for slicing
	 * @param endIndex		an end index for slicing 
	 */
	public Slicer slice(int startIndex,int endIndex){
		return slice(startIndex, endIndex, 1);
	}
	
	/**
	 * @return Factory method that creates a new Slicer object.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe
	 * }
	 * </pre> 
	 * 
	 * @param startIndex	a start index for slicing
	 * @param step			a step for slicing
	 * @param endIndex		an end index for slicing  
	 */
	public Slicer slice(int startIndex,int endIndex, int step){
		return new Slicer(startIndex, endIndex, step, false);	
	}
	
	/**
	 * @return Factory method that creates a new Slicer object with {@code startIndex} = 0 and {@code endIndex=Integer.MAX_VALUE} and {@code step=0}.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * <pre>
	 * {@code
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * }
	 * </pre>
	 * 
	 */
	public Slicer slice(){
		return slice(1);
	}
	
	/**
	 * @return Factory method that creates a new Slicer object with {@code startIndex} = 0 and {@code endIndex=Integer.MAX_VALUE}
	 * the given value of {@code step}.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe
	 * }
	 * </pre> 
	 * 
	 * @param step	the step of slicer 
	 */
	public Slicer slice(int step){
		if (step < 0){
			return new Slicer(Integer.MAX_VALUE,0,step,true);	
		}else{
			return new Slicer(0,Integer.MAX_VALUE,step,false);
		}	
	}
	
	/**
	 * @return Factory method that creates a new Slicer object with the {@code startIndex=0}.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * }
	 * </pre>
	 * 
	 * @param step			a step for slicing
	 * @param endIndex		an end index for slicing   
	 */
	public Slicer sliceTo(int endIndex, int step){
		int startIndex = 0;
		if (step < 0){
			startIndex = Integer.MAX_VALUE-1;
			return new Slicer(startIndex,endIndex,step,true);
		}
		return slice(startIndex,endIndex,step);
	}
	
	/**
	 * @return Factory method that creates a new Slicer object with {@code startIndex=0} and {@code step=1}.
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe
	 * }
	 * </pre> 
	 * 
	 * @param endIndex		an end index for slicing   
	 */
	public Slicer sliceTo(int endIndex){
		return sliceTo(endIndex, 1);
	}
	
	/**
	 * @return Factory method that creates a new Slicer object with {@code endIndex=Integer.MAX_VALUE}
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * <pre>
	 * {@code
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe 
	 * }
	 * </pre>
	 * 
	 * @param startIndex	a start index for slicing
	 * @param step			a step for slicing  
	 */
	public Slicer sliceFrom(int startIndex, int step){
		int endIndex = Integer.MAX_VALUE-1;
		if (step < 0){
			endIndex = 0;
		}
		return new Slicer(startIndex,endIndex,step,true);
	}
	
	/**
	 * @return Factory method that creates a new Slicer object with {@code endIndex=Integer.MAX_VALUE} and {@code step=0} 
	 * 
	 * Slicer objects can be used as input for the slice method of any Slicable
	 * object (like {@link list} or {@link str})
	 *
	 * <pre>
	 * {@code
	 *
	 * Example:
	 * 
	 * Slice LAST_THREE = x.sliceFrom(-3);
	 * 
	 * x.print(x.String("tic tac toe").slice(LAST_THREE));
	 * 
	 * Console: toe
	 * }
	 * </pre> 
	 * 
	 * @param startIndex	a start index for slicing 
	 */
	public Slicer sliceFrom(int startIndex){
		return sliceFrom(startIndex,1);
	}
	
	/**
	 * A less verbose console print method. Preferred over {@code System.out.println}.
	 * 
	 * {@code x.print} is also more versatile. It can take values of any type as input.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType
	 * }
	 * </pre> 
	 * 
	 * @param object	object to print
	 * @param <T0>		type of object to print
	 */
	public static <T0> void print(T0 object){
		System.out.println(object==null?"NullType":object);
	}
	
	/**
	 * A less verbose console print method. Preferrred over {@code System.out.println}.
	 * 
	 * {@code x.print} is also more versatile. It can take values of any type as input.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType
	 * }
	 * </pre> 
	 * 
	 * @param object0	first object to print
	 * @param <T0>		type of first object to print
	 * @param object1	second object to print
	 * @param <T1>		type of second object to print 
	 */
	public static <T0,T1> void print(T0 object0,T1 object1){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1));
	}
	
	/**
	 * A less verbose console print method. Preferrred over {@code System.out.println}.
	 * 
	 * {@code x.print} is also more versatile. It can take values of any type as input.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType
	 * }
	 * </pre> 
	 * 
	 * 
	 * @param object0	first object to print
	 * @param <T0>		type of first object to print
	 * @param object1	second object to print
	 * @param <T1>		type of second object to print
	 * @param object2	third object to print
	 * @param <T2>		type of third object to print 
	 */
	public static <T0,T1,T2> void print(T0 object0,T1 object1,T2 object2){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2));
	}
	
	/**
	 * A less verbose console print method. Preferrred over {@code System.out.println}.
	 * 
	 * {@code x.print} is also more versatile. It can take values of any type as input.
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType
	 * }
	 * </pre> 
	 * 
	 * 
	 * @param object0	first object to print
	 * @param <T0>		type of first object to print
	 * @param object1	second object to print
	 * @param <T1>		type of second object to print
	 * @param object2	third object to print
	 * @param <T2>		type of third object to print
	 * @param object3	fourth object to print
	 * @param <T3>		type of fourth object to print  
	 */
	public static <T0,T1,T2,T3> void print(T0 object0,T1 object1,T2 object2,T3 object3){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param object0	first object to print
	 * @param <T0>		type of first object to print
	 * @param object1	second object to print
	 * @param <T1>		type of second object to print
	 * @param object2	third object to print
	 * @param <T2>		type of third object to print
	 * @param object3	fourth object to print
	 * @param <T3>		type of fourth object to print
	 * @param object4	fifth object to print
	 * @param <T4>		type of fifth object to print   
	 */
	public static <T0,T1,T2,T3,T4> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4));
	}
	
	/**
	 * A less verbose console print method. Preferred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param object0	first object to print
	 * @param <T0>		type of first object to print
	 * @param object1	second object to print
	 * @param <T1>		type of second object to print
	 * @param object2	third object to print
	 * @param <T2>		type of third object to print
	 * @param object3	fourth object to print
	 * @param <T3>		type of fourth object to print
	 * @param object4	fifth object to print
	 * @param <T4>		type of fourth object to print
	 * @param object5	sixth object to print
	 * @param <T5>		type of sixth object to print
	 */
	public static <T0,T1,T2,T3,T4,T5> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param object0	first object to print
	 * @param <T0>		type of first object to print
	 * @param object1	second object to print
	 * @param <T1>		type of second object to print
	 * @param object2	third object to print
	 * @param <T2>		type of third object to print
	 * @param object3	fourth object to print
	 * @param <T3>		type of fourth object to print
	 * @param object4	fifth object to print
	 * @param <T4>		type of fourth object to print
	 * @param object5	sixth object to print
	 * @param <T5>		type of sixth object to print
	 * @param object6	seventh object to print
	 * @param <T6>		type of seventh object to print 
	 */
	public static <T0,T1,T2,T3,T4,T5,T6> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param object0	first object to print
	 * @param <T0>		type of first object to print
	 * @param object1	second object to print
	 * @param <T1>		type of second object to print
	 * @param object2	third object to print
	 * @param <T2>		type of third object to print
	 * @param object3	fourth object to print
	 * @param <T3>		type of fourth object to print
	 * @param object4	fifth object to print
	 * @param <T4>		type of fourth object to print
	 * @param object5	sixth object to print
	 * @param <T5>		type of sixth object to print
	 * @param object6	seventh object to print
	 * @param <T6>		type of seventh object to print
	 * @param object7	eighth object to print
	 * @param <T7>		type of eighth object to print  
	 */
	public static <T0,T1,T2,T3,T4,T5,T6,T7> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6,T7 object7){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6)+" "+(object7==null?"NullType":object7));
	}
	
	/**
	 * A less verbose console print method. Preferrred over System.out.println.
	 * 
	 * x.print is also more versatile. It can take values of any type as input.
	 * 
	 * Example:
	 * 
	 * x.print("Hello World", 1, true, x.list(1, 2, 3), null);
	 * 
	 * Console: Hello World 1 true [1, 2, 3] NullType 
	 * 
	 * @param object0	first object to print
	 * @param <T0>		type of first object to print
	 * @param object1	second object to print
	 * @param <T1>		type of second object to print
	 * @param object2	third object to print
	 * @param <T2>		type of third object to print
	 * @param object3	fourth object to print
	 * @param <T3>		type of fourth object to print
	 * @param object4	fifth object to print
	 * @param <T4>		type of fourth object to print
	 * @param object5	sixth object to print
	 * @param <T5>		type of sixth object to print
	 * @param object6	seventh object to print
	 * @param <T6>		type of seventh object to print
	 * @param object7	eighth object to print
	 * @param <T7>		type of eighth object to print
	 * @param object8	ninth object to print
	 * @param <T8>		type of ninth object to print   
	 */
	public static <T0,T1,T2,T3,T4,T5,T6,T7,T8> void print(T0 object0,T1 object1,T2 object2,T3 object3,T4 object4,T5 object5,T6 object6,T7 object7,T8 object8){
		System.out.println(""+(object0==null?"NullType":object0)+" "+(object1==null?"NullType":object1)+" "+(object2==null?"NullType":object2)+" "+(object3==null?"NullType":object3)+" "+(object4==null?"NullType":object4)+" "+(object5==null?"NullType":object5)+" "+(object6==null?"NullType":object6)+" "+(object7==null?"NullType":object7)+" "+(object8==null?"NullType":object8));
	}
	
	
	/**
	 * This static method evaluates an input object as true or false.
	 * 
	 * An object is evaluates as true only in following cases:
	 * 
	 * 1) It's an {@link Boolean} that equals to <code>false</code>
	 * 2) It's an {@link Double} that equals to 0.0
	 * 3) It's an {@link Double} that equals to 0.0
	 * 4) It's an {@link Float} that equals to 0.0
	 * 5) It's an empty {@link String}
	 * 6) It's an empty {@link Iterable}
	 * 7) It's a {@link tuple0}
	 * 8) It's a <code>null</code>
	 * 9) It's a {@link Truthful} whose isTrue() method returns <code>false</code>
	 *  
	 * @param value	the object for which we want to know whther it's true or not
	 * @return true or not
	 */
	public static boolean isTrue(Object value){
		try{
			if((Boolean)value == false){
				return false;
			}	
		}catch(Exception e){
			
		}
		if(value == null){
			return false;
		}
		if(value instanceof Truthful){
			return ((Truthful)value).isTrue();
		}
		if(value instanceof Iterable<?> && len(value) == 0){
			return false;
		}
		if(value instanceof tuple && len(value) == 0){
			return false;
		}
		if(value instanceof String && len(value) == 0){
			return false;
		}
		try{
			if((Integer)value <= 0){
				return false;
			}	
		}catch(Exception e){
			
		}
		try{
			if((Double)value <= 0.0){
				return false;
			}	
		}catch(Exception e){
			
		}
		try{
			if((Float)value <= 0.0){
				return false;
			}	
		}catch(Exception e){
			
		}
		return true;
	}
	
	/**
	 * This static method evaluates an input object as false or true.
	 * 
	 * An object is evaluates as true only in following cases:
	 * 
	 * 1) It's an {@link Boolean} that equals to <code>false</code>
	 * 2) It's an {@link Double} that equals to 0.0
	 * 3) It's an {@link Double} that equals to 0.0
	 * 4) It's an {@link Float} that equals to 0.0
	 * 5) It's an empty {@link String}
	 * 6) It's an empty {@link Iterable}
	 * 7) It's a {@link tuple0}
	 * 8) It's a <code>null</code>
	 * 9) It's a {@link Truthful} whose isTrue() method returns <code>false</code>
	 *  
	 * @param value	the object for which we want to know whther it's false or not
	 * @return false or not
	 */
	public static boolean isFalse(Object value){
		return !isTrue(value);
	}
	/**
	 * @return Memoizes an object and returns its memoized version.
	 * 
	 *As a quick example, let <pre>xerox</pre> be a {@link Function} object whose method <pre>apply</pre> copies the string <pre>"hello"</pre> the given number <pre>count</pre> of times:
	 *
	 * <pre>
	 * {@code 
	 * Function<Integer, String> xerox = new Function<Integer, String>() {
	 *	 public String apply(Integer count) {
	 *	 	return x.String("hello").times(count);
	 *	 }
	 * };
	 *}
	 * </pre>
	 *
	 *It's a long to execute function for large values of *count*.
	 *
	 *In order to avoid the long computation for the same value of *count*, we first create a cached version of xerox using <pre>x.memo</pre>:
	 * <pre>
	 * {@code
	 * Function<Integer,String> cachedXerox = x.memo(xerox);
	 * }
	 * </pre>
	 *		
	 *The first call of the function. The computation takes a very long time:
	 *
	 * <pre>
	 * {@code
	 * x.timer.start();
	 * String copies = cachedXerox.apply(5000000);
	 * x.print(x.timer.stop());
	 *
	 * Console: 18.898s
	 * }
	 * </pre>
	 * The second call with the same value of *count*, the result is instantaneous:
	 * <pre>
	 * {@code
	 *	x.timer.start();
	 *	String moreCopies = cachedXerox.apply(5000000);
	 *	x.print(x.timer.stop());
	 * 
	 *
	 * Console: 0.0s
	 * }
	 * </pre>
	 *
	 * x.memo can be used to cache methods of object of any Java type, not only {@link Function}.
	 * 
	 * @param object an object to memoize
	 * @param <T> the type of object to memoize
	 */
	@SuppressWarnings("unchecked")
	public static <T> T memo(T object){
		return (T)(Memoizer.memoize(object));
	}
	
	/**
	 * A synonym to {@code x.String.escape}
	 */
	public static Function<Object, String> escape = x.String.escape;
	
	/**
	 * A synonym to {@code x.String.escape(String)}
	 * 
	 * @param string a {@link String} to escape
	 * @return the escaped string
	 */
	public static String escape(String string){
		return x.String.escape(string);
	}
	
	/**
	 * A synonym to <pre>x.String.strip</pre>
	 */
	public static Function<Object, String> strip = x.String.strip;
	
	/**
	 * A synonym to <pre>x.String.trim</pre>
	 */
	public static Function<Object, String> trim = x.String.strip;
	
	/**
	 * A synonym to <pre>x.String.toLowerCase</pre>
	 */
	public static Function<Object, String> toLowerCase = x.String.toLowerCase;
	
	/**
	 * A synonym to <pre>x.String.lower</pre>
	 */
	public static Function<Object, String> lower = x.String.toLowerCase;
	
	/**
	 * A synonym to <pre>x.String.toUpperCase</pre>
	 */
	public static Function<Object, String> toUpperCase = x.String.toUpperCase;
	
	/**
	 * A synonym to <pre>x.String.upper</pre>
	 */
	public static Function<Object, String> upper = x.String.toUpperCase;
	
	/**
	 * A {@link Function} that takes any object as input and returns its "length", e.g.
	 * the number of elements in case of Iterable or tuple. 
	 */
	public static Function<Object, Integer> len = new Function<Object, Integer>() {
		public Integer apply(Object value) {
			return len(value);
		}
	};
	
	/**
	 * A {@link Function} that takes any object as input and returns the same object.
	 */
	public static Function<Object, Object> doNothing = new Function<Object, Object>() {
		public Object apply(Object string) {
			return string;
		}
	};
	
	/**
	 * A {@link Function} that always returns the same object no matter the input of the <pre>apply</pre> method.
	 * @param <T> the type of returned object
	 * @param obj the object to always return
	 * @return the same object <pre>obj</pre> no matter the input of the <pre>apply</pre> method.
	 */
	public static <T> Function<Object,T> constant(final T obj){
		return new Function<Object,T>() {
			public T apply(Object input) {
				return (T)obj;
			}
		};
	}
	
	/**
	 * A {@link Function} that takes an object's method name and method's
	 * parameters values as input, invokes that method with those parameters
	 * and the output of invocation.
	 * 
	 * @param methodName method name to invoke
	 * @param methodParams parameters for the method to invoke
	 * @param <T> the output type
	 * @return the output of invocation
	 */
	public static <T> Function<Object,T> invoke(final String methodName, final Object... methodParams){
		return new Function<Object,T>() {
			public T apply(Object obj) throws IllegalArgumentException{
				
				list<Object> methodParamsList;
				if(methodParams != null) {
					methodParamsList = x.list(methodParams);					
				} else{
					methodParamsList = x.list();
				}

				list<Class<?>> methodParamsTypes = x.list();
				for (tuple item : x.enumerate(methodParamsList)){
					item.name("idx","param");
					Class<?> currentType = item.get("param").getClass();
					if (currentType.equals(Integer.class)){
						currentType = int.class;
					}else if(currentType.equals(Double.class)){
						currentType = double.class;
					}else if(currentType.equals(Long.class)){
						currentType = long.class;
					}else if(currentType.equals(Float.class)){
						currentType = float.class;
					}
					methodParamsTypes.append(currentType); 
				}
				try{
					if(x.len(methodParams) > 0){
						Class<?>[] typesArr = new Class<?>[x.len(methodParamsTypes)];
						typesArr = methodParamsTypes.toArrayList().toArray(typesArr);
						return (T)(obj.getClass().getMethod(methodName, typesArr).invoke(obj, methodParamsList.toArrayList().toArray()));	
					}else{
						return (T)(obj.getClass().getMethod(methodName).invoke(obj));
					}
				}catch(Exception e){
					throw new IllegalArgumentException();
				}
			}
		};
	}
	
	/**
	 * A {@link Function} that takes a tuple of arguments an adds them up.
	 * 
	 * @return the {@link Function} that sums two numbers
	 * @param <T>	the output (and the input element) type
	 */
	public static <T extends Number> Function<tuple2<T,T>, T> add(){
		return new Function<tuple2<T,T>,T>() {
			public T apply(tuple2<T,T> values) throws IllegalArgumentException{
				T op1 = values.value0;
				T op2 = values.value1;

			    if( !(op1 instanceof Number) || !(op2 instanceof Number) ){
			        throw new IllegalArgumentException("Invalid operands for mathematical operator [+]");
			    }

			    if(op1 instanceof Double || op2 instanceof Double){
			        return (T)(Object)(((Number)op1).doubleValue() + ((Number)op2).doubleValue());
			    }

			    if(op1 instanceof Float || op2 instanceof Float){
			        return (T)(Object)(((Number)op1).floatValue() + ((Number)op2).floatValue());
			    }

			    if(op1 instanceof Long || op2 instanceof Long){
			        return (T)(Object)(((Number)op1).longValue() + ((Number)op2).longValue());
			    }

			    return (T)(Object)(((Number)op1).intValue() + ((Number)op2).intValue());
			}
		};
	}
	
	/**
	 * A {@link Function} that takes a tuple of arguments an adds them up.
	 * 
	 * @return the {@link Function} that averages two numbers
	 * @param <T>	the output (and the input element) type
	 */
	public static <T extends Number> Function<tuple2<T,T>, T> avg(){
		return new Function<tuple2<T,T>,T>() {
			public T apply(tuple2<T,T> values) throws IllegalArgumentException{
				T op1 = values.value0;
				T op2 = values.value1;

			    if( !(op1 instanceof Number) || !(op2 instanceof Number) ){
			        throw new IllegalArgumentException("Invalid operands for mathematical operator [+]");
			    }

			    if(op1 instanceof Double || op2 instanceof Double){
			        return (T)(Object)((((Number)op1).doubleValue() + ((Number)op2).doubleValue())/2);
			    }

			    if(op1 instanceof Float || op2 instanceof Float){
			        return (T)(Object)((((Number)op1).floatValue() + ((Number)op2).floatValue())/2);
			    }

			    if(op1 instanceof Long || op2 instanceof Long){
			        return (T)(Object)((((Number)op1).longValue() + ((Number)op2).longValue())/2);
			    }

			    return (T)(Object)((((Number)op1).intValue() + ((Number)op2).intValue())/2);
			}
		};
	}
	
	/**
	 * @return {@link Function} that takes a tuple of arguments an adds them up.
	 * @param <T>	the output (and the input element) type
	 */
	public static <T extends Comparable<? super T>> Function<tuple2<T,T>, T> min(){
		return new Function<tuple2<T,T>,T>() {
			public T apply(tuple2<T,T> values) throws IllegalArgumentException{
				if(values.value0.compareTo(values.value1) < 0){
					return values.value0;
				}else{
					return values.value1;
				}
			}
		};
	}
	
	/**
	 * @return A {@link Function} that takes a tuple of arguments an adds them up.
	 * @param <T>	the output (and the input element) type
	 */
	public static <T extends Comparable<? super T>> Function<tuple2<T,T>, T> max(){
		return new Function<tuple2<T,T>,T>() {
			public T apply(tuple2<T,T> values) throws IllegalArgumentException{
				if(values.value0.compareTo(values.value1) > 0){
					return values.value0;
				}else{
					return values.value1;
				}
			}
		};
	}
	
	/**
	 * A {@link Predicate} that is always false no matter the input. 
	 */
	public static Predicate<Object> FALSE = new Predicate<Object>() {
		public Boolean apply(Object string) {
			return false;
		}
	};
	
	/**
	 * A {@link Predicate} that is always true no matter the input. 
	 */
	public static Predicate<Object> TRUE = new Predicate<Object>() {
		public Boolean apply(Object string) {
			return true;
		}
	};
	
	/**
	 * A {@link Predicate} that only true if {@link x#len(Object)} returns 0 for the input value. 
	 */
	public static Predicate<Object> empty = new Predicate<Object>() {
		public Boolean apply(Object value) {
			return len(value) == 0;
		}
	};
	
	/**
	 * A synonym to {@link x#empty}. 
	 */
	public static Predicate<Object> isEmpty = empty;	
	
	/**
	 * A {@link Function} that retursn the value of the hash code of the input object. 
	 */
	public static Function<Object,Integer> getHashCode = new Function<Object,Integer>() {
		public Integer apply(Object value) {
			return value.hashCode();
		}
	};
}
