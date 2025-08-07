package main.java.dbdriver.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class IntegerCodec implements Codec<Integer>{

	@Override
	public void encode(BsonWriter writer, Integer value, EncoderContext encoderContext) {
		writer.writeInt32(value);		
	}

	@Override
	public Class<Integer> getEncoderClass() {
		return Integer.class;
	}

	@Override
	public Integer decode(BsonReader reader, DecoderContext decoderContext) {
		int value = reader.readInt32();
		
		return value;
	}

}
