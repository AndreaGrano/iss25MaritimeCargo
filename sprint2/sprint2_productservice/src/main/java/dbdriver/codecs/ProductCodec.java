package main.java.dbdriver.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;

import main.java.dbdriver.Product;

import org.bson.codecs.DecoderContext;

public class ProductCodec implements Codec<Product> {

    @Override
    public void encode(BsonWriter writer, Product product, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeInt32("_id", product.getPID());
        writer.writeString("name", product.getName());
        writer.writeDouble("weight", product.getWeight());
        writer.writeEndDocument();
    }

    @Override
    public Product decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        int pid = 0;
        String name = null;
        double weight = 0;

        for(int i = 0; i < 3; i++) {
        	reader.readName();
            switch (reader.getCurrentName()) {
	            case "_id":
	                pid = reader.readInt32();
	                break;
                case "name":
                    name = reader.readString();
                    break;
                case "weight":
                    weight = reader.readDouble();
                    break;
            }
        }
        reader.readEndDocument();

        return new Product(pid, name, weight);
    }

    @Override
    public Class<Product> getEncoderClass() {
        return Product.class;
    }
}
