package de.heilsen.ganzhornfest.data.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Scope
public @interface RepositoryScope {
}
