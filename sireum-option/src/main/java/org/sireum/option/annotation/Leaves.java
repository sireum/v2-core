package org.sireum.option.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:jake.h.ehrlich@k-state.edu">Jake Ehrlich</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Leaves {
	Class<?> value();
}