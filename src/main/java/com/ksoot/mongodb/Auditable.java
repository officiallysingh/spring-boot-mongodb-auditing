package com.ksoot.mongodb;

import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.annotation.Collation;

import java.lang.annotation.*;

@Persistent
@Collation
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Auditable {

  String auditCollectionName()  default "";
}
