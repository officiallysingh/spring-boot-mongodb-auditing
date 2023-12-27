# Spring Data MongoDB Auditing

## Introduction
[**`Hibernate Envers`**](https://hibernate.org/orm/envers) provides Auditing of JPA entities,
but no such library provides out of box support for Auditing MongoDB entities.
Auditing is a cross-cutting concern, should be kept separate from business logic and available to be applied
declaratively.

## Implementation
All code responsible for auditing is in [**`com.ksoot.mongodb`**](src/main/java/com/ksoot/mongodb) package.
* [**`Auditable`**](src/main/java/com/ksoot/mongodb/Auditable.java) is an `Annotation` to be used to make MongoDB entity classes eligible for Auditing.
* [**`AuditingMongoEventListener`**](src/main/java/com/ksoot/mongodb/AuditingMongoEventListener.java) is the main class
  responsible for auditing. While application startup it finds all collections enabled for Auditing and creates Audit collections for same 
  and prepared Audit metadata for each collection such as Version fields name, Audit collection name, etc.
* [**`MongoDBConfig`**](src/main/java/com/ksoot/mongodb/MongoDBConfig.java) is custom configuration class for MongoDB.
* [**`AuditEvent`**](src/main/java/com/ksoot/mongodb/AuditEvent.java) is template for Audit records. It defines following fields
  * **`id`** - Unique identifier for Audit record.
  * **`revision`** - Autoincrement numeric value for each change to a particular record.
  * **`type`** - Type of change to a record such as CREATED, UPDATED, DELETED.
  * **`collectionName`** - Source MongoDB collection name.
  * **`source`** - Snapshot of Audited record.
  * **`datetime`** - `OffsetDateTime` when the change happened.

## Usage

## Configurations


## Demo


./gradlew bootRun --args='--spring.profiles.active=docker'

./gradlew --stop

Documentation to be updated soon

# Spring Data MongoDB Text search