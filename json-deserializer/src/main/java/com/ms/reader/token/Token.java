package com.ms.reader.token;

public sealed interface Token permits ArrayEndToken, ArrayStartToken, EndToken, FieldSeparatorToken,
		StringToken, KeyValueSeparator, StartToken {

}
