# Spring Data MongoDB Auditing

## Introduction
[**`Hibernate Envers`**](https://hibernate.org/orm/envers) provides Auditing of JPA entities,
but no such library provides out of box support for Auditing MongoDB entities.
Auditing is a cross-cutting concern, should be kept separate from business logic and available to be applied
declaratively.

## Implementation
All code responsible for auditing is in [**`com.ksoot.mongodb`**](src/main/java/com/ksoot/mongodb) package.
* [**`Auditable`**](src/main/java/com/ksoot/mongodb/Auditable.java) is an `Annotation` to be used to make MongoDB entity classes eligible for Auditing.
  The entity classes not annotated with `Auditable` will not be audited.
* [**`AuditingMongoEventListener`**](src/main/java/com/ksoot/mongodb/AuditingMongoEventListener.java) is the main class
  responsible for auditing. While application startup it finds all collections enabled for Auditing and creates Audit collections for same 
  and prepare Audit metadata for each collection such as Version fields name, Audit collection name, etc. 
  Then it listens to eligible Entity object changes and creates Audit records.
* [**`MongoDBConfig`**](src/main/java/com/ksoot/mongodb/MongoDBConfig.java) is custom configuration class for MongoDB.
* [**`AuditEvent`**](src/main/java/com/ksoot/mongodb/AuditEvent.java) is template for Audit records. It defines following fields
  * **`id`** - Unique identifier for Audit record.
  * **`datetime`** - `OffsetDateTime` when the change happened.
  * **`actor`** - Audit Username if available in `SecurityContextHolder`, otherwise it will be set as `SYSTEM`.
  * **`revision`** - Autoincrement numeric value for each change to a particular record.
  * **`type`** - Type of change to a record such as `CREATED`, `UPDATED`, `DELETED`.
  * **`collectionName`** - Source MongoDB collection name.
  * **`source`** - Snapshot of Audited record.
* [**`MongoDBModule`**](src/main/java/com/ksoot/mongodb/MongoDBModule.java) registers custom serializer for MongoDB ObjectId, 
  otherwise the `id` attribute is not properly serialized.
* [**`MongoAuditProperties`**](src/main/java/com/ksoot/mongodb/MongoAuditProperties.java) maps to configuration properties defined in `application.properties` or `application.yml`.

## Configurations
Following are the configuration properties to customize MongoDB Auditing behaviour.
```yaml
application:
  mongodb:
    entity-base-packages:
      - com.ksoot
      - com.org
    auditing:
      enabled: true
      without-transaction: false
      prefix:
      suffix: _aud
```

* **`application.mongodb.entity-base-packages`** - List of packages to scan for MongoDB entities, Default: `Main class package name`.
* **`application.mongodb.auditing.enabled`** - Whether or not to enable MongoDB Auditing, Default: `true`. If required Auditing can be disabled by setting it to `false`.
* **`application.mongodb.auditing.without-transaction`** - Whether or not to do Auditing without Transactions, Default: `true`,
* **`application.mongodb.auditing.prefix`** - Audit collection name prefix, Default: ``.
* **`application.mongodb.auditing.suffix`** - Audit collection name suffix, Default: `_aud"`. 

## How it works
* Only the entity classes annotated with `Auditable` will be audited. 
* Audit collection name can either be specified in `Auditable` annotation (e.g. `@Auditable(name = "audit_logs")`) or it will be derived from source collection name, 
prefixed and suffixed with values defined in `application.mongodb.auditing.prefix` and `application.mongodb.auditing.suffix` respectively.
* If it is required to Audit all collections in a single Audit collection then `Auditable` annotation can be used with same `name` attribute value for all entity classes.
* On application startup it scans all the packages defined in `application.mongodb.entity-base-packages` for MongoDB entities annotated with `Auditable`. 
* For each such entity class it creates Audit collection with name as per settings and prepares Audit metadata.
* It listens to all changes to eligible entity classes and creates Audit records, whenever new records are created, existing records are updated or deleted.
* For newly created records `type` attribute of Audit record will be `CREATED`, for updated records `type` attribute will be `UPDATED` and for deleted records `type` attribute will be `DELETED`.
* `source` attribute of Audit record will contain the snapshot of the record after update. While deleting the record, the `source` will only contain `_id` of deleted record.
* It is recommended to use a version field annotated with `@Version` in entity classes to avoid concurrent updates. 
It expects a `Long` version field to differentiates between newly created records and already existing updated record. 
In absence of version field `type` attribute of Audit record will be `UPDATED` for newly created and updated records as well.
* The Audit Username is fetched from `SecurityContextHolder` if available, otherwise it will be set as `SYSTEM`.
* It is highly recommended to put the CRUD operation in a **Transaction** to have source collection updated and audit entry atomically.
But If required `application.mongodb.auditing.without-transaction` can be set to `true` then Auditing will be done without Transactions.
* It is recommended to use `OffsetDateTime` or `ZonedDateTime` for `datetime` attribute of Audit record to avoid any timezone related issues. 
Custom converters and Codecs are configured for the same in [**`MongoDBConfig`**](src/main/java/com/ksoot/mongodb/MongoDBConfig.java).
* **Audit history is logged as follows.**
![Audit Date](https://github.com/officiallysingh/spring-boot-mongodb-auditing/blob/main/Audit%20data.png)

## Usage

You can copy the classes from [**`com.ksoot.mongodb`**](src/main/java/com/ksoot/mongodb) package to your project and use them as it is 
or do any changes as per your requirements.

## Demo

Clone this repository, import in your favourite IDE as either Maven or Gradle project. 

### Docker compose

Application is bundled with [**`Spring boot Docker compose`**](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.docker-compose).
* If you have docker installed, then simply run the application in `docker` profile by passing `spring.profiles.active=docker`
as program argument from your IDE.
* Depending on your current working directory in IDE, you may need to change `spring.docker.compose.file=spring-boot-mongodb-auditing/compose.yml`
to `spring.docker.compose.file=compose.yml` in [**`application-docker.yml`**](src/main/resources/config/application-docker.yml)
* Make sure the host ports mapped in [**`Docker compose file`**](compose.yml) are available or change the ports and
do the respective changes in database configurations [**`application-docker.yml`**](src/main/resources/config/application-docker.yml)

### Explicit MongoDB installation
If you have MongoDB installed locally or Using MongoDB Atlas, 
then change to your MongoDB URI in [**`application.yml`**](src/main/resources/config/application.yml) file as follows.
```yaml
spring:
  #------------------------- MongoDB configuration -------------------------
  data:
    mongodb:
      uri: <You MongoDB URI>
```

> [!IMPORTANT]
MongoDB replica set is required for Transactions to work. 
Refer to [**`MongoDB Replica Set`**](https://medium.com/workleap/the-only-local-mongodb-replica-set-with-docker-compose-guide-youll-ever-need-2f0b74dd8384) for more details.


### APIs
* Access [`Swagger`](http://localhost:8080/swagger-ui.html) at http://localhost:8080/swagger-ui.html
* Access Demo CRUD APIs at http://localhost:8080/swagger-ui/index.html?urls.primaryName=Product 
  * Create a new Product
```curl
curl -X 'POST' \
  'http://localhost:8080/v1/products' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "iPhone 15 mini",
  "description": "iPhone",
  "tags": [
    "mobile"
  ],
  "attributes": {
    "size": "mini",
    "color": "black"
  }
}'
```
  * Update existing Product, replace `60f0b0b0e3b9a91e8c7b0b0b` with your Product id
```curl
curl -X 'PATCH' \
'http://localhost:8080/v1/products/60f0b0b0e3b9a91e8c7b0b0b' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "description": "iPhone Handset"
}'
```
  * Delete existing Product, replace `60f0b0b0e3b9a91e8c7b0b0b` with your Product id
```curl
curl -X 'DELETE' \
  'http://localhost:8080/v1/products/60f0b0b0e3b9a91e8c7b0b0b' \
  -H 'accept: application/json'
```
* Access Audit History APIs at http://localhost:8080/swagger-ui/index.html?urls.primaryName=Audit to fetch Audit history of any Product.
Records can be filtered by Collection Name, Audit event type, Revision, Audit Username, Revisions and Datetime range.
```curl
curl -X 'GET' \
  'http://localhost:8080/v1/audit-history?collectionName=products&page=0&size=16' \
  -H 'accept: */*'
```

# Spring Data MongoDB Text search