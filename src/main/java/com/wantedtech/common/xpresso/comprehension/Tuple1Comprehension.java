package com.wantedtech.common.xpresso.comprehension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuple;


class Tuple1Comprehension extends AbstractTupleComprehension{
	
	public Tuple1Comprehension(String fieldName0){
		this.outputFieldNames.add(fieldName0);
	}
	
	public void apply(Function<Object,?> function){
		if(outputFieldNames.size() > this.if_functions.size()){
			if_functions.add(function);
		}else{
			else_functions.add(function);
		}
	}
	
	public void replace(Object value){
		if(outputFieldNames.size() > this.if_functions.size()){
			if_functions.add(x.constant(value));
		}else{
			else_functions.add(x.constant(value));
		}
	}

	public void where(String fieldName0, String... fieldNames){
		this.elementFieldNames.add(fieldName0);
		for (String f : fieldNames){
			this.elementFieldNames.add(f);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void in(Iterable<?> elements){
		if(!(elements instanceof Iterable<?>)){
			throw new IllegalArgumentException("The input of forIter has to be an Iterable.");
		}
		if(if_functions.size() > 1 && if_functions.size() < outputFieldNames.size()){
			throw new IllegalArgumentException("Insufficient number of Functions to be applied to the desired output values.");
		}
		if(else_functions.size() > 1 && else_functions.size() < outputFieldNames.size()){
			throw new IllegalArgumentException("Insufficient number of Functions to be applied to the desired output values.");
		}
		if(else_functions.size() > 0 && if_functions.size() != else_functions.size()){
			throw new IllegalArgumentException("Incorrect number of Functions to be applied to the desired output values.");
		}
		if(if_functions.size() == 0){
			for(@SuppressWarnings("unused") String outField : outputFieldNames){
				if_functions.add(x.doNothing);
			}
		}
		original_elements = Helpers.newArrayList((Iterable<Object>)elements);
		for(Object element : elements){
			
			if(!(element instanceof tuple) && !(element instanceof Iterable<?>)){
				list<Object> replacedElement = x.list(); 
				for (String elementFieldName : elementFieldNames){
					if(x.Object(element).hasField(elementFieldName)){
						try {
							Field f = element.getClass().getField(elementFieldName);
							f.setAccessible(true);
							replacedElement.append(f.get(element));
						} catch (Exception e) {
							// cant happen
							e.printStackTrace();
						}
					}else if (x.Object(element).hasMethod(elementFieldName)) {
						try {
							Method m = element.getClass().getMethod(elementFieldName);
							m.setAccessible(true);
							replacedElement.append(m.invoke(element));
						} catch (Exception e) {
							// cant happen
							e.printStackTrace();
						}
					}else if (x.Object(element).hasMethod("get"+x.String(elementFieldName).title())) {
						try {
							Method m = element.getClass().getMethod("get"+x.String(elementFieldName).title());
							m.setAccessible(true);
							replacedElement.append(m.invoke(element));
						} catch (Exception e) {
							// cant happen
							e.printStackTrace();
						}
					}else{
						throw new IllegalArgumentException("Could not find the field " + elementFieldName + " in an element of the input Iterable.");
					}
				}
				element = replacedElement;
			}
			
			if(x.len(element) != elementFieldNames.size()){
				throw new IllegalArgumentException("I was expecting the dimensionality of each element of the input Iterable to be "+elementFieldNames.size());
			}
			tuple outputElement;
			int index0;
			if(outputFieldNames.get(0).equals("_")){
				index0 = elementFieldNames.size()-1;
			}else{
				index0 = elementFieldNames.indexOf(outputFieldNames.get(0));
			}
			if(if_predicate.apply(element)){
				if(element instanceof tuple){
					outputElement = x.tuple(if_functions.get(0).apply((((tuple)element).get(index0))));	
				}else if(element instanceof Iterable<?>){ 
					outputElement = x.tuple(if_functions.get(0).apply((((list<?>)element).get(index0))));
				}else{
					outputElement = null;
				}
			}else{
				if(element instanceof tuple){
					outputElement = x.tuple(else_functions.get(0).apply((((tuple)element).get(index0))));	
				}else if(element instanceof Iterable<?>){
					outputElement = x.tuple(else_functions.get(0).apply((((list<?>)element).get(index0))));
				}else{
					outputElement = null;
				}
			}
			transformed_elements.add(outputElement);
		}
		this.elements = transformed_elements;
		before_for = false;
	}
}
