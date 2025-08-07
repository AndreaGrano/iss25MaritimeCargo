package main.java.dbdriver.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import main.java.dbdriver.Slot;

public class SlotCodec implements Codec<Slot>{

	@Override
	public void encode(BsonWriter writer, Slot slot, EncoderContext encoderContext) {
		writer.writeStartDocument();
		writer.writeInt32("_id", slot.getId());
		writer.writeInt32("slotNumber", slot.getSlotNumber());
		writer.writeInt32("PID", slot.getPID());
		writer.writeEndDocument();
	}

	@Override
	public Class<Slot> getEncoderClass() {
		return Slot.class;
	}

	@Override
	public Slot decode(BsonReader reader, DecoderContext decoderContext) {
		reader.readStartDocument();
        int id = 0;
        int slotNumber = 0;
        int PID = 0;

        for(int i = 0; i < 3; i++) {
        	reader.readName();
            switch (reader.getCurrentName()) {
	            case "_id":
	                id = reader.readInt32();
	                break;
                case "slotNumber":
                    slotNumber = reader.readInt32();
                    break;
                case "PID":
                    PID = reader.readInt32();
                    break;
            }
        }
        reader.readEndDocument();

        return new Slot(id, slotNumber, PID);
	}

}
