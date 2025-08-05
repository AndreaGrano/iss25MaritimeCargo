package main.java.dbdriver;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import main.java.dbdriver.codecs.IntegerCodec;
import main.java.dbdriver.codecs.ProductCodec;
import main.java.dbdriver.codecs.SlotCodec;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.*;

public class Wrapper {
	private static final String uri = "mongodb+srv://clarissadesimoni:GnBC8GnMfYw3y4N9@maritime-cargo.vyudu9t.mongodb.net/?retryWrites=true&w=majority&appName=maritime-cargo";

	private MongoClient client;
	private MongoDatabase wrapper;
	private MongoCollection<Product> products;
	private MongoCollection<Slot> slots;
	
	public Wrapper() {
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(false).build());
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(pojoCodecRegistry, CodecRegistries.fromCodecs(new ProductCodec()), CodecRegistries.fromCodecs(new SlotCodec()), CodecRegistries.fromCodecs(new IntegerCodec()));
		 MongoClientSettings settings = MongoClientSettings.builder()
				 	.applyConnectionString(new ConnectionString(uri))
		            .codecRegistry(codecRegistry)
		            .build();
		this.client = MongoClients.create(settings);

		this.wrapper = client.getDatabase("maritime-cargo");
		this.products = wrapper.getCollection("products", Product.class);
		this.slots = wrapper.getCollection("slots", Slot.class);
	}
	
	public Product createProduct(String name, double weight) {
        try {
        	int pid = new ObjectId().getTimestamp();
        	Product res = new Product(pid, name, weight);
        	this.products.insertOne(res);
        	return res;
        } catch(MongoException e) {
            System.err.println("Insert error: " + e);
            return null;
        }
	}
	
	public Product getProduct(int PID) {
		try {
			Product res = products.find(Filters.eq("_id", PID)).first();
//			if(res == null) System.err.println("No products were found with PID " + String.valueOf(PID));
			if(res == null) {
				res = new Product();
				res.setPID(0);
				res.setName("NOT_FOUND");
				res.setWeight(0.0);
			}
			return res;
		} catch(MongoException e) {
            System.err.println("Fetch error: " + e);
            return null;
		}
	}
	
	public Product getProductByName(String name) {
		try {
			Product res = products.find(Filters.eq("name", name)).first();
			if(res == null) System.err.println("No products were found with name '" + name + "'");
			return res;
		} catch(MongoException e) {
            System.err.println("Fetch error: " + e);
            return null;
		}
	}
	
	public List<Product> getAllProducts() {
		List<Product> res = new ArrayList<>();
		try {
			products.find().sort(Sorts.ascending("_id")).iterator().forEachRemaining(p -> res.add(p));
			return res;
		} catch(MongoException e) {
            System.err.println("Fetch error: " + e);
            return res;
		}
	}
	
	public Product deleteProduct(int PID) {
		try {
			Product res = this.getProduct(PID);
			if(res == null) {
				System.err.println("Unable to delete product with PID " + String.valueOf(PID));
				return null;
			}
			DeleteResult result = products.deleteOne(Filters.eq("_id", PID));
			if(result.getDeletedCount() > 0) return res;
			else {
				System.err.println("Unable to delete product with PID " + String.valueOf(PID));
				return null;
			}
		} catch(MongoException e) {
            System.err.println("Delete error: " + e);
            return null;
		}
	}
	
	public Product deleteProductByName(String name) {
		try {
			Product res = this.getProductByName(name);
			if(res == null) {
				System.err.println("Unable to delete product with name '" + name + "'");
				return null;
			}
			DeleteResult result = products.deleteOne(Filters.eq("name", name));
			if(result.getDeletedCount() > 0) return res;
			else {
				System.err.println("Unable to delete product with name '" + name + "'");
				return null;
			}
		} catch(MongoException e) {
            System.err.println("Delete error: " + e);
            return null;
		}
	}
	
	public int getFirstAvailableSlot() {
		try {
			Slot res = slots.find(Filters.and(Filters.eq("PID", 0), Filters.gte("slotNumber", 1), Filters.lte("slotNumber", 4))).sort(Sorts.ascending("slotNumber")).first();
			if(res == null) {
				System.err.println("No free slots available");
				return 0;
			}
			else return res.getSlotNumber();
		} catch(MongoException e) {
            System.err.println("Fetch error: " + e);
            return 0;
		}
	}
	
	public Slot getSlot(int slotNumber) {
		try {
			Slot res = slots.find(Filters.eq("slotNumber", slotNumber)).sort(Sorts.ascending("slotNumber")).first();
			if(res == null) System.err.println("No slots were found with slotNumber " + String.valueOf(slotNumber));
			return res;
		} catch(MongoException e) {
            System.err.println("Fetch error: " + e);
            return null;
		}
	}

	public List<Slot> getAllSlots() {
		List<Slot> res = new ArrayList<>();
		try {
			slots.find().sort(Sorts.ascending("slotNumber")).iterator().forEachRemaining(p -> res.add(p));
			return res;
		} catch(MongoException e) {
            System.err.println("Fetch error: " + e);
            return res;
		}
	}
	
	public int loadProduct(int PID) {
		try {
            int slotNumber = getFirstAvailableSlot();
            if (slotNumber > 0 && slotNumber < 5) {
                UpdateResult result = slots.updateOne(
                    Filters.eq("slotNumber", slotNumber),
                    Updates.set("PID", PID)
                );
                return result.getModifiedCount() > 0 ? slotNumber : 0;
            } else {
                if (slotNumber >= 5) System.err.println("The hold is full");
                return 0;
            }
        } catch (MongoException e) {
            System.err.println("Update error: " + e);
            return 0;
        }
	}
	
	public boolean resetHold() {
		try {
			UpdateResult result = slots.updateOne(
				Filters.and(Filters.eq("PID", 0), Filters.gte("slotNumber", 1), Filters.lte("slotNumber", 4)),
                Updates.set("PID", 0)
            );
			return result.getModifiedCount() > 0;
		} catch(MongoException e) {
            System.err.println("Fetch error: " + e);
            return false;
		}
	}
	
	public void closeClient() {
		this.client.close();
	}
	
	public static void main(String[] args) {
		Wrapper w = new Wrapper();
		// w.loadProduct(1753353999);
		System.out.println(w.getProduct(1753353999));
	}
}
