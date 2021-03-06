package com.wantedtech.common.xpresso.comprehension;

import com.wantedtech.common.xpresso.functional.Function;
import com.wantedtech.common.xpresso.functional.Predicate;

public class Tuple1ComprehensionIf {

	Tuple1Comprehension comprehension;
	
	public Tuple1ComprehensionIf(Tuple1Comprehension comprehension){
		this.comprehension = comprehension;
	}
	
	public Tuple1ComprehensionIf apply(Function<Object,?> function){
		this.comprehension.apply(function);
		return new Tuple1ComprehensionIf(this.comprehension);
	}
	
	public Tuple1ComprehensionIf replace(Object value){
		this.comprehension.replace(value);
		return new Tuple1ComprehensionIf(this.comprehension);
	}
	
	public Tuple1ComprehensionElse when(Predicate<Object> predicate){
		this.comprehension.when(predicate);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	public Tuple1ComprehensionElse unless(Predicate<Object> predicate){
		this.comprehension.unless(predicate);
		return new Tuple1ComprehensionElse(this.comprehension);
	}
	
	public Tuple1ComprehensionFor where(String fieldName0, String... fieldNames){
		this.comprehension.where(fieldName0, fieldNames);
		return new Tuple1ComprehensionFor(this.comprehension);
	}
}
