package com.ms.parser;

import com.ms.reader.JsonReader;
import com.ms.reader.token.*;

import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class JsonParser {

	private static final Map<Class<?>, Function<String, BiConsumer<Field, Object>>> setter;

	private static final Map<Class<?>, Function<String, Object>> converters;

	private final static Map<Class<? extends Collection>, Supplier<Collection>> collectionSuppliers;

	static {
		converters = new HashMap<>();
		converters.put(int.class, Integer::valueOf);
		converters.put(boolean.class, Boolean::valueOf);
		converters.put(String.class, value -> value);

		setter = new HashMap<>();
		setter.put(int.class, value -> {
			Integer i = (Integer) converters.get(int.class).apply(value);
			return (field, obj) -> {
				try {
					field.setAccessible(true);
					field.setInt(obj, i);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			};
		});
		setter.put(boolean.class, value -> {
			Boolean b = (Boolean) converters.get(boolean.class).apply(value);
			return (field, obj) -> {
				try {
					field.setAccessible(true);
					field.setBoolean(obj, b);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			};
		});
		setter.put(String.class, (value) -> (field, obj) -> {
			try {
				field.setAccessible(true);
				field.set(obj, value);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});

		collectionSuppliers = new HashMap<>();
		collectionSuppliers.put(List.class, () -> new ArrayList<>());
		collectionSuppliers.put(Set.class, () -> new HashSet());
	}

	public <T> T parseJson(Reader in, Class<T> type) {
		JsonReader jsonReader = new JsonReader(in);
		return parseTree(jsonReader, type);
	}

	public <T> T parseTree(JsonReader in, Class<T> type) {
		try {
			T obj = type.getConstructor().newInstance();
			Field currentField = null;
			Token token;
			do {
				token = in.readNext();
				switch (token) {
					case StringToken s -> {
						if (currentField == null) {
							currentField = type.getDeclaredField(s.string());
						} else {
							setter.get(currentField.getType()).apply(s.string()).accept(currentField, obj);
							currentField = null;
						}
					}
					case StartToken ignored -> {
						if (currentField != null) {
							currentField.setAccessible(true);
							currentField.set(obj, parseTree(in, currentField.getType()));
						}
					}
					case EndToken e -> {
						System.out.println(e);
						return obj;
					}
					case FieldSeparatorToken fs -> {
						System.out.println(fs);
						currentField = null;
					}
					case KeyValueSeparator kv -> System.out.println(kv);
					case ArrayStartToken as -> {
						System.out.println(as);
						currentField.setAccessible(true);
						Class<?> objectTypeInCollection = Class.forName(((ParameterizedType) currentField.getGenericType()).getActualTypeArguments()[0].getTypeName());
						currentField.set(obj, parseCollection(in, currentField.getType(), objectTypeInCollection));
					}
					case ArrayEndToken ae -> {
						System.out.println(ae);
					}
				}
			} while (true);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
				 NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public Collection parseCollection(JsonReader in, Class<?> collectionType, Class<?> type) {
		Collection collection = buildCollection(collectionType);
		Token token;
		do {
			token = in.readNext();
			switch (token) {
				case StringToken s -> collection.add(converters.get(type).apply(s.string()));
				case StartToken ignored -> {
					Object obj = parseTree(in, type);
					collection.add(obj);
				}
				case EndToken endToken -> System.out.println(endToken);
				case ArrayStartToken as -> {
					System.out.println(as);
					// Determine type of collection from currentField's type
					// make currentField accessible
					// create the same type collection and set it to the current field
				}
				case ArrayEndToken ae -> {
					System.out.println(ae);
					return collection;
				}
				case FieldSeparatorToken fs -> System.out.println(fs);
				case KeyValueSeparator keyValueSeparator -> System.out.println(keyValueSeparator);
			}
		} while (true);
	}

	private Collection buildCollection(Class<?> collectionType) {
		return collectionSuppliers.get(collectionType).get();
	}
}
