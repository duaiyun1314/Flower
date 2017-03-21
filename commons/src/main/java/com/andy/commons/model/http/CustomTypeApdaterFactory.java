package com.andy.commons.model.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by wanglu on 2016/12/19.
 */

public class CustomTypeApdaterFactory implements TypeAdapterFactory {
    private static final String ERROR = "error";
    private static final String DATA = "data";

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementTypeAdapter = gson.getAdapter(JsonElement.class);


        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            //当获取了http请求的数据后，获得error所对应的值，抛出异常
            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementTypeAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has(ERROR)) {
                        String errorJson = jsonObject.get(ERROR).toString();
                        throw new ErrorException(errorJson);
                    }
                }
                return delegate.fromJsonTree(jsonElement);
            }

        }.nullSafe();
    }
}
