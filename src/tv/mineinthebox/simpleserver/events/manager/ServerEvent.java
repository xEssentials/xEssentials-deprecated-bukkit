package tv.mineinthebox.simpleserver.events.manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface ServerEvent {
	EventPriority priority() default EventPriority.MEDIUM;
}
