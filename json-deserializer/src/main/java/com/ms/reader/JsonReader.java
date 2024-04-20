package com.ms.reader;

import com.ms.reader.token.ArrayEndToken;
import com.ms.reader.token.ArrayStartToken;
import com.ms.reader.token.EndToken;
import com.ms.reader.token.FieldSeparatorToken;
import com.ms.reader.token.StringToken;
import com.ms.reader.token.KeyValueSeparator;
import com.ms.reader.token.StartToken;
import com.ms.reader.token.Token;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class JsonReader extends PushbackReader {

	public JsonReader(Reader in, int size) {
		super(in, size);
	}

	public JsonReader(Reader in) {
		super(in);
	}

	public Token readNext(){
		try {
			char read = (char) this.read();
			if(read == '\uFFFF') return null;
			while (read == Constants.NEW_LINE || read == Constants.WHITESPACE){
				read = (char) this.read();
			}
			if(read == Constants.OBJ_START) {
				return new StartToken();
			} else if (read == Constants.OBJ_END) {
				return new EndToken();
			} else if (read == Constants.ARRAY_START) {
				return new ArrayStartToken();
			} else if (read == Constants.ARRAY_END) {
				return new ArrayEndToken();
			} else if (read == Constants.FIELD_SEPARATOR) {
				return new FieldSeparatorToken();
			} else if (read == Constants.KEY_VALUE_SEPARATOR) {
				return new KeyValueSeparator();
			} else {
				this.unread(read);
				String string = readString();
				return new StringToken(string);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

//	private ValueType determineValueType() throws IOException {
//		this.mark(1);
//		char read = (char) this.read();
//		this.reset();
//		if(read == Constants.OBJ_START){
//			return ValueType.OBJECT;
//		} else if (read == Constants.ARRAY_START) {
//			return ValueType.ARRAY;
//		} else {
//			return ValueType.PRIMITIVE;
//		}
//	}

	private String readString() throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		char read = (char) this.read();
		if(read == Constants.DOUBLE_QUOTE) {
			while ((read = (char) this.read()) != Constants.DOUBLE_QUOTE){
				stringBuilder.append(read);
			}
		} else {
			do {
				stringBuilder.append(read);
			} while ((read = (char) this.read()) != Constants.FIELD_SEPARATOR);
			this.unread(read);
		}
		return stringBuilder.toString();
	}
}
