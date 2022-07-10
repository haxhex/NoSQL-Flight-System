import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConnectToMongo {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        // Database Details
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoCredential credential = MongoCredential.createCredential("hex"
                , "Flight", "hax1234".toCharArray());
        System.out.println("Credentials: " + credential);
        MongoDatabase database = mongoClient.getDatabase("Flight");
        Document document = new Document();

        DB db = mongoClient.getDB("Flight");
        DBCollection collection = db.getCollection("inventory");
//        DBCollection collection = db.getCollection("inventory");

//        WriteResult result = collection.remove(new BasicDBObject());
//        System.out.println(result.toString());

//        //Set up test data
//        setUpTestData(collection);
//
//        //Select all document from a collection
//        selectAllRecordsFromACollection(collection);
//
//        //Select first document in collection
//        selectFirstRecordInCollection(collection);
//
//        //Select single document and single field based on record number
//        selectSingleRecordAndFieldByRecordNumber(collection);
//
//        //Select all documents where record number = n
//        selectAllRecordByRecordNumber(collection);
//
//        //In example
//        in_Example(collection);
//
//        //Less than OR greater than example
//        lessThan_GreaterThan_Example(collection);
//
//        //Select document where record number != n
//        negation_Example(collection);
//
//        //And logical comparison query example
        //andLogicalComparison_Example(collection);
        MongoDatabase dbb = mongoClient.getDatabase("Flight");
        MongoCollection<Document> coll = dbb.getCollection("car2");
//        Document obj = new Document();
//        obj.append("name", "Volkswagen");
//        obj.append("color", "JetBlue");
//        obj.append("cno", "H672");
//        obj.append("speed", 62);
//        obj.append("mfdcountry", "Italy");
//        coll.insertOne(obj);
        ObjectId objectId = new ObjectId("61f88746ca300732f8f3d8cc");
        Bson query = new Document("_id", objectId);
        Bson update = new Document("$set",
                new Document("color", "Red"));

        System.out.println("before update");
        findAndPrint(coll);

        coll.findOneAndUpdate(query, update);

        System.out.println("after update of color field");
        findAndPrint(coll);

//
//        //Select documents based on regex match LIKE example
//        regex_Example(collection);;
         //Create Collection
//        database.createCollection("Employee");


        // Create Document
      //  Document document = new Document("flightNumber", "2343")
      //          .append("airport", "ISA")
       //         .append("country", "France")
       //         .append("destination", Arrays.asList("Italy", "USA"));

        // Insert Document to Collection
        //database.getCollection("test").insertOne(document);

        // Print data in collection
//        database.getCollection("Flights").find()
//                .forEach((Consumer<? super Document>) System.out::println);
//        database.listCollectionNames().forEach((Consumer<? super String>) System.out::println);
//        database.getCollection("inventory").find()
//                .forEach((Consumer<? super Document>) System.out::println);

        // Drop Collection
        //database.getCollection("presentations").drop();
        System.out.println();
    }
    private static void setUpTestData(DBCollection collection){
        for (int i=1; i <= 10; i++) {
            collection.insert(new BasicDBObject().append("employeeId", i).append("employeeName", "TestEmployee_"+i));
        }
    }
    private static void selectFirstRecordInCollection(DBCollection collection) {
        DBObject dbObject = collection.findOne();
        System.out.println(dbObject);
    }

    private static void selectAllRecordsFromACollection(DBCollection collection) {
        DBCursor cursor = collection.find();
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private static void selectSingleRecordAndFieldByRecordNumber(DBCollection collection) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("employeeId", 3);
//        BasicDBObject fields = new BasicDBObject();
//        fields.put("employeeId", 1);

        DBCursor cursor = collection.find(whereQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private static void selectAllRecordByRecordNumber(DBCollection collection) {
        BasicDBObject whereQuery = new BasicDBObject();
        System.out.println("Enter date : ");
        String date = scanner.next();
        whereQuery.put("departreTime", date);
        DBCursor cursor = collection.find(whereQuery);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private static void in_Example(DBCollection collection) {
        BasicDBObject inQuery = new BasicDBObject();
        List<Integer> list = new ArrayList<Integer>();
        System.out.println("Enter first : ");
        list.add(scanner.nextInt());
        System.out.println("Enter second : ");
        list.add(scanner.nextInt());
        System.out.println("Enter third : ");
        list.add(scanner.nextInt());
        inQuery.put("employeeId", new BasicDBObject("$in", list));
        DBCursor cursor = collection.find(inQuery);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private static void lessThan_GreaterThan_Example(
        DBCollection collection) {
        BasicDBObject gtQuery = new BasicDBObject();
        gtQuery.put("display_total_fare_per_ticket", new BasicDBObject("$gt", 160).append("$lt", 500));
        DBCursor cursor = collection.find(gtQuery);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private static void negation_Example(DBCollection collection) {
        BasicDBObject neQuery = new BasicDBObject();
        neQuery.put("employeeId", new BasicDBObject("$ne", 4));
        DBCursor cursor = collection.find(neQuery);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    private static void andLogicalComparison_Example(DBCollection collection) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
//        obj.add(new BasicDBObject("arrivalCity", "Dubai"));
//        obj.add(new BasicDBObject("departureCity", "Dubai"));
//        obj.add(new BasicDBObject("capacity", "Airbus A380"));
//        String s1 = scanner.nextLine(); // Dubai
//        String s2 = scanner.nextLine(); // New York City
//        String s3 = scanner.nextLine(); // Airbus A380

        obj.add(new BasicDBObject("employeeId", 2));
        obj.add(new BasicDBObject("employeeName", "TestEmployee_2"));
        //obj.add(new BasicDBObject("departreTime", "2022-06-21"));
        andQuery.put("$and", obj);

        //System.out.println(andQuery.toString());

        DBCursor cursor = collection.find(andQuery);
        while (cursor.hasNext()) {
            // System.out.println(cursor.next());
            cursor.remove();
        }
    }

    private static void regex_Example(DBCollection collection) {
        BasicDBObject regexQuery = new BasicDBObject();
        regexQuery.put("employeeName",
                new BasicDBObject("$regex", "TestEmployee_[3]")
                        .append("$options", "i"));

        System.out.println(regexQuery.toString());

        DBCursor cursor = collection.find(regexQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
    private static void findAndPrint(MongoCollection<Document> coll) {
        FindIterable<Document> cursor = coll.find();

        for (Document d : cursor)
            System.out.println(d);
    }
}
