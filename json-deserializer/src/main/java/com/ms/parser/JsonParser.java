package com.ms.parser;

import com.ms.reader.JsonReader;
import com.ms.reader.token.ArrayEndToken;
import com.ms.reader.token.ArrayStartToken;
import com.ms.reader.token.EndToken;
import com.ms.reader.token.FieldSeparatorToken;
import com.ms.reader.token.KeyValueSeparator;
import com.ms.reader.token.StartToken;
import com.ms.reader.token.StringToken;
import com.ms.reader.token.Token;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class JsonParser {

	private static final Map<Class<?>, Function<String, BiConsumer<Field, Object>>> setter;

	static {
		setter = new HashMap<>();
		setter.put(int.class, (value) -> {
			Integer i = Integer.valueOf(value);
			return (field, obj) -> {
				try {
					field.setInt(obj, i);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			};
		});
		setter.put(boolean.class, (value) -> {
			Boolean i = Boolean.valueOf(value);
			return (field, obj) -> {
				try {
					field.setBoolean(obj, i);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			};
		});
		setter.put(String.class, (value) -> (field, obj) -> {
			try {
				field.set(obj, value);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public <T> T parseJson(Reader in, Class<T> type){
		JsonReader jsonReader = new JsonReader(in);
		return parseTree(jsonReader, type);
	}

	public <T> T parseTree(JsonReader in, Class<T> type){
		try {
			T obj = type.getConstructor().newInstance();
			Field currentField = null;
			Token token;
			do {
				token = in.readNext();
				switch (token) {
					case StringToken s:
						if(currentField == null){
							currentField = type.getDeclaredField(s.string());
						} else {
							currentField.setAccessible(true);
							setter.get(currentField.getType()).apply(s.string()).accept(currentField, obj);
							currentField = null;
						}
						break;
					case StartToken s:
						if(currentField != null) {
							currentField.setAccessible(true);
							currentField.set(obj, parseTree(in, currentField.getType()));
						}
						break;
					case EndToken e:
						System.out.println(e);
						return obj;
					case FieldSeparatorToken s:
						System.out.println(s);
						break;
					case KeyValueSeparator k:
						System.out.println(k);
						break;
					case ArrayStartToken a:
						break;
					case ArrayEndToken e:
						break;
				}
			} while (token != null);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}
