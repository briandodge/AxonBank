package gov.dvla.osl.EventSource.api.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoRepository<Taggregate, TKey> extends Repository<Taggregate, TKey> {

    private MongoClient client = null;
    private String database;
    private String collectionName;

    public MongoRepository(MongoClient client, String database, String collectionName){
        this.client = client;
        this.database = database;
        this.collectionName = collectionName;
    }

    public MongoRepository(MongoClientURI uri){
        this.client = new MongoClient(uri);
    }

    @Override
    public void add(Taggregate aggregate) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Document document = Document.parse(mapper.writeValueAsString(aggregate));
            this.client.getDatabase(database).getCollection(collectionName).insertOne(document);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private boolean accountExists(String accountId) {

        MongoCollection<Document> collection =  this.client.getDatabase(database).getCollection(collectionName);
        Document existingAccount = collection.find(eq("accountId", accountId)).first();
        return existingAccount != null;
    }

    @Override
    public void save(Taggregate aggregate) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Document document = Document.parse(mapper.writeValueAsString(aggregate));
            Boolean accountExists = accountExists("9997");
            if (!accountExists) {
                this.client.getDatabase(database).getCollection(collectionName).insertOne(document);
            }
            Bson filter = new Document("accountId", "9997");
            Bson updateOperation = new Document("$set", document);
            this.client.getDatabase(database).getCollection(collectionName).updateOne(filter, updateOperation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Taggregate aggregate) {

    }

    @Override
    public void delete(Taggregate aggregate) {

    }

    @Override
    public String find(TKey id) {
        Document document = this.client.getDatabase(database).getCollection(collectionName).find(eq("accountId", id)).first();
        String json = document.toJson();

        return json;


    }

    @Override
    public List<Taggregate> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }


}
