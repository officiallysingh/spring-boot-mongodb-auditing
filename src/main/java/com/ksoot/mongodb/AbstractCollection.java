package com.ksoot.mongodb;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public abstract class AbstractCollection implements Identifiable<String>, Versionable<Long> {

    @Id
    protected String id;

    @Override
    public String getId() {
        return this.id;
    }

    @Version
    protected Long version;

    @Override
    public Long getVersion() {
        return this.version;
    }
}
