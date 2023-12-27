package com.ksoot.mongodb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public final class MongoDBModule extends Module {

  @Override
  public String getModuleName() {
    return MongoDBModule.class.getSimpleName();
  }

  @Override
  public Version version() {
    return VersionUtil.versionFor(MongoDBModule.class);
  }

  @Override
  public void setupModule(final SetupContext context) {
    final SimpleModule module = new SimpleModule();
    module.addSerializer(ObjectId.class, new ObjectIdSerializer());
    module.setupModule(context);
  }

  private final class ObjectIdSerializer extends JsonSerializer<ObjectId> {

    @Override
    public void serialize(
        final ObjectId objectId, final JsonGenerator json, final SerializerProvider serializers)
        throws IOException {
      json.writeString(objectId.toString());
    }
  }
}
