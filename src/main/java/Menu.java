import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.function.Consumer;

import static java.lang.System.exit;

public class Menu {
    static Scanner scanner = new Scanner(System.in);

    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.println("Choose your plan : ");
    }

    public static void main(String[] args) {
        // Connection
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoCredential credential = MongoCredential.createCredential("hex"
                , "Flight", "hax1234".toCharArray());
        System.out.println("Credentials: " + credential);
        MongoDatabase database = mongoClient.getDatabase("Flight");
        DB db = mongoClient.getDB("Flight");
        DBCollection collection = db.getCollection("Flights");
        MongoCollection<Document> coll = database.getCollection("Flights");

        // Test Connection - Print data in collection
        database.getCollection("Flights").find()
                .forEach((Consumer<? super Document>) System.out::println);
        database.listCollectionNames().forEach((Consumer<? super String>) System.out::println);

        String[] options = {"1- View all flights on a specific day",
                "2- View all flights in a certain price range",
                "3- View the minimum and maximum prices of flights from the origin to a specific destination",
                "4- View the average and total price of flights from the destination to a specific destination ",
                "5- View all flights on a specific day and specific type of flight",
                "6- View all flights in a certain price range and specific type of flight",
                "7- View the minimum and maximum prices of flights from the origin to a specific destination and specific type of flight",
                "8- View the average and total price of flights from the destination to a specific destination and specific type of flight",
                "9- View flights with a specific origin and destination in a price range and determine the cheapest flight",
                "10- View flights with a specific origin, destination and capacity",
                "11- View flights with a specific origin and destination in a price range and determine the cheapest flight by time interval",
                "12- View flights with a specific origin, destination and capacity by time interval",
                "13- View available airlines for flights from a specific destination to a specific date",
                "14- Delete items based on the date and name of the airline",
                "15- Change the capacity of a specific flight",
                "16- Change the capacity of all flights of an airline on a specific date, origin and destination",
                "17- View the names of existing airports of origin and destination for flights with a origin and destination (country) and a specific date",
                "18- Exit",
        };
        int option = 1;
        while (option != 18) {
            printMenu(options);
            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> option1(collection);
                    case 2 -> option2(collection);
                    case 3 -> option3(collection);
                    case 4 -> option4(collection);
                    case 5 -> option5(collection);
                    case 6 -> option6(collection);
                    case 7 -> option7(collection);
                    case 8 -> option8(collection);
                    case 9 -> option9(collection);
                    case 10 -> option10(collection);
                    case 11 -> option11();
                    case 12 -> option12();
                    case 13 -> option13(collection);
                    case 14 -> option14();
                    case 15 -> option15(coll);
                    case 16 -> option16();
                    case 17 -> option17(collection);
                    case 18 -> exit(0);
                }
            } catch (Exception ex) {
                System.out.println("Please enter an integer value between 1 and " + options.length);
                scanner.nextLine();
            }
        }
    }

    // TODO Queries
    private static void option1(DBCollection collection) {
        System.out.println("Query - 1");
//      db.Flights.find({"departreTime" : "2022-02-03"})
        String date;
        System.out.println("Enter date :");
        date = scanner.nextLine();
        date = scanner.nextLine();
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("departreTime", date);
        DBCursor cursor = collection.find(whereQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
            BasicDBObject query = new BasicDBObject();

            query.put("departreTime", date);

            BasicDBObject sorter = new BasicDBObject();

            sorter.put("display_total_fare_per_ticket", 1.0);

            Cursor cursor1 = collection.find(query).sort(sorter);
            while (cursor1.hasNext()) {
                BasicDBObject document = (BasicDBObject) cursor1.next();
                System.out.println(document.toString());
            }
        }
        if (sort == 2) {
            // Sort results descending by date
            BasicDBObject query = new BasicDBObject();

            query.put("departreTime", date);

            BasicDBObject sorter = new BasicDBObject();

            sorter.put("display_total_fare_per_ticket", -1.0);

            Cursor cursor1 = collection.find(query).sort(sorter);
            while (cursor1.hasNext()) {
                BasicDBObject document = (BasicDBObject) cursor1.next();
                System.out.println(document.toString());
            }

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option2(DBCollection collection) {
        System.out.println("Query - 2");
//      db.Flights.find({ "display_total_fare_per_ticket" : { $gt :  30, $lt : 400}});
        int min;
        int max;
        System.out.println("Enter minimum price : ");
        min = scanner.nextInt();
        System.out.println("Enter maximum price : ");
        max = scanner.nextInt();

        BasicDBObject gtQuery = new BasicDBObject();
        gtQuery.put("display_total_fare_per_ticket", new BasicDBObject("$gt", min).append("$lt", max));
        DBCursor cursor = collection.find(gtQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price


        }
        if (sort == 4) {
            // Sort results descending by price
        }
    }

    private static void option3(DBCollection collection) {
        System.out.println("Query - 3");
//        db.Flights.aggregate([
//                {$match:
//        {"departureCity": "Istanbul",
//                "arrivalCity": "Moscow"} },
//        { "$group": {
//            "_id": null,
//                    "max": { "$max": "$display_total_fare_per_ticket" },
//            "min": { "$min": "$display_total_fare_per_ticket" }
//        }}
//])
        String origin;
        String destination;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();

        List<DBObject> pipeline = Arrays.asList(
                new BasicDBObject()
                        .append("$match", new BasicDBObject()
                                .append("departureCity", origin)
                                .append("arrivalCity", destination)
                        ),
                new BasicDBObject()
                        .append("$group", new BasicDBObject()
                                .append("_id", new BsonNull())
                                .append("max", new BasicDBObject()
                                        .append("$max", "$display_total_fare_per_ticket")
                                )
                                .append("min", new BasicDBObject()
                                        .append("$min", "$display_total_fare_per_ticket")
                                )
                        )
        );
        AggregationOptions options = AggregationOptions.builder()
                // always use cursor mode
                .outputMode(AggregationOptions.OutputMode.CURSOR)
                .build();


        Cursor cursor = collection.aggregate(pipeline, options);
        while (cursor.hasNext()) {
            BasicDBObject document = (BasicDBObject) cursor.next();
            System.out.println(document.toString());
        }


        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option4(DBCollection collection) {
        System.out.println("Query - 4");
//        db.Flights.aggregate([
//                {$match:
//        {"departureCity": "Istanbul",
//                "arrivalCity": "Moscow"} },
//        { "$group": {
//            "_id": null,
//                    "Avg": { "$avg": "$display_total_fare_per_ticket" },
//            "Sum": { "$sum": "$display_total_fare_per_ticket" }
//        }}
//])

        String origin;
        String destination;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();


        List<DBObject> pipeline = Arrays.asList(
                new BasicDBObject()
                        .append("$match", new BasicDBObject()
                                .append("departureCity", origin)
                                .append("arrivalCity", destination)
                        ),
                new BasicDBObject()
                        .append("$group", new BasicDBObject()
                                .append("_id", new BsonNull())
                                .append("Avg", new BasicDBObject()
                                        .append("$avg", "$display_total_fare_per_ticket")
                                )
                                .append("Sum", new BasicDBObject()
                                        .append("$sum", "$display_total_fare_per_ticket")
                                )
                        )
        );
        AggregationOptions options = AggregationOptions.builder()
                // always use cursor mode
                .outputMode(AggregationOptions.OutputMode.CURSOR)
                .build();


        Cursor cursor = collection.aggregate(pipeline, options);
        while (cursor.hasNext()) {
            BasicDBObject document = (BasicDBObject) cursor.next();
            System.out.println(document.toString());
        }


        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }
    }

    private static void option5(DBCollection collection) {
        System.out.println("Query - 5_1");
//        db.Flights.find({$and : [{"departreTime" : "2022-06-21"}, {"cabin_name" : "First Class"}]})
        String date;
        String type;
        System.out.println("Enter date :");
        date = scanner.nextLine();
        date = scanner.nextLine();
        System.out.println("Enter type of flight : ");
        type = scanner.nextLine();

        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("departreTime", date));
        obj.add(new BasicDBObject("cabin_name", type));
        andQuery.put("$and", obj);
        DBCursor cursor = collection.find(andQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
            BasicDBObject query = new BasicDBObject();

            query.put("$and", Arrays.asList(
                            new BasicDBObject()
                                    .append("departreTime", date),
                            new BasicDBObject()
                                    .append("cabin_name", type)
                    )
            );

            BasicDBObject sort1 = new BasicDBObject();

            sort1.put("display_total_fare_per_ticket", 1.0);

            Cursor cursor1 = collection.find(query).sort(sort1);
            while (cursor1.hasNext()) {
                BasicDBObject document = (BasicDBObject) cursor1.next();
                System.out.println(document.toString());
            }
        }
        if (sort == 2) {
            // Sort results descending by date
            BasicDBObject query = new BasicDBObject();

            query.put("$and", Arrays.asList(
                            new BasicDBObject()
                                    .append("departreTime", date),
                            new BasicDBObject()
                                    .append("cabin_name", type)
                    )
            );

            BasicDBObject sort1 = new BasicDBObject();

            sort1.put("display_total_fare_per_ticket", -1.0);

            Cursor cursor1 = collection.find(query).sort(sort1);
            while (cursor1.hasNext()) {
                BasicDBObject document = (BasicDBObject) cursor1.next();
                System.out.println(document.toString());
            }
        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option6(DBCollection collection) {
        System.out.println("Query - 5_2");
//        db.Flights.find({$and : [{ "display_total_fare_per_ticket" : { $gt :  30, $lt : 400}} , {"cabin_name" : "Economy Class"}]})
        int min;
        int max;
        String type;
        System.out.println("Enter minimum price : ");
        min = scanner.nextInt();
        System.out.println("Enter maximum price : ");
        max = scanner.nextInt();
        System.out.println("Enter type of flight : ");
        type = scanner.nextLine();
        type = scanner.nextLine();

        BasicDBObject gtQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("display_total_fare_per_ticket", new BasicDBObject("$gt", min).append("$lt", max)));
        obj.add(new BasicDBObject("cabin_name", type));
        gtQuery.put("$and", obj);
        DBCursor cursor = collection.find(gtQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }
    }

    private static void option7(DBCollection collection) {
        System.out.println("Query - 5_3");
//        db.Flights.aggregate([
//                {$match:
//        {"departureCity": "Istanbul",
//                "arrivalCity": "Moscow",
//                "cabin_name": "Economy Class"} },
//        { "$group": {
//            "_id": null,
//                    "max": { "$max": "$display_total_fare_per_ticket" },
//            "min": { "$min": "$display_total_fare_per_ticket" }
//        }}
//])
        String origin;
        String destination;
        String type;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();
        System.out.println("Enter type of flight : ");
        type = scanner.nextLine();

        List<DBObject> pipeline = Arrays.asList(
                new BasicDBObject()
                        .append("$match", new BasicDBObject()
                                .append("departureCity", origin)
                                .append("arrivalCity", destination)
                                .append("cabin_name", type)
                        ),
                new BasicDBObject()
                        .append("$group", new BasicDBObject()
                                .append("_id", new BsonNull())
                                .append("max", new BasicDBObject()
                                        .append("$max", "$display_total_fare_per_ticket")
                                )
                                .append("min", new BasicDBObject()
                                        .append("$min", "$display_total_fare_per_ticket")
                                )
                        )
        );
        AggregationOptions options = AggregationOptions.builder()
                // always use cursor mode
                .outputMode(AggregationOptions.OutputMode.CURSOR)
                .build();


        Cursor cursor = collection.aggregate(pipeline, options);
        while (cursor.hasNext()) {
            BasicDBObject document = (BasicDBObject) cursor.next();
            System.out.println(document.toString());
        }


        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option8(DBCollection collection) {
        System.out.println("Query - 5_4");

//        db.Flights.aggregate([
//                {$match:
//        {"departureCity": "Istanbul",
//                "arrivalCity": "Moscow",
//                "cabin_name": "Economy Class"}},
//        { "$group": {
//            "_id": null,
//                    "Avg": { "$avg": "$display_total_fare_per_ticket" },
//            "Sum": { "$sum": "$display_total_fare_per_ticket" }
//        }}
//])
        String origin;
        String destination;
        String type;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();
        System.out.println("Enter type of flight : ");
        type = scanner.nextLine();

        List<DBObject> pipeline = Arrays.asList(
                new BasicDBObject()
                        .append("$match", new BasicDBObject()
                                .append("departureCity", origin)
                                .append("arrivalCity", destination)
                                .append("cabin_name", type)
                        ),
                new BasicDBObject()
                        .append("$group", new BasicDBObject()
                                .append("_id", new BsonNull())
                                .append("Avg", new BasicDBObject()
                                        .append("$avg", "$display_total_fare_per_ticket")
                                )
                                .append("Sum", new BasicDBObject()
                                        .append("$sum", "$display_total_fare_per_ticket")
                                )
                        )
        );
        AggregationOptions options = AggregationOptions.builder()
                // always use cursor mode
                .outputMode(AggregationOptions.OutputMode.CURSOR)
                .build();


        Cursor cursor = collection.aggregate(pipeline, options);
        while (cursor.hasNext()) {
            BasicDBObject document = (BasicDBObject) cursor.next();
            System.out.println(document.toString());
        }

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option9(DBCollection collection) {
        System.out.println("Query - 6");
//        -- Min
//        db.Flights.find({$and:[
//        {"departureCity": "Istanbul",
//                "arrivalCity": "Moscow",
//                "display_total_fare_per_ticket" : { $gt :  30, $lt : 400}}
//]}).sort({display_total_fare_per_ticket:1}).limit(1)
//                -- All
//        db.Flights.find({$and:[
//        {"departureCity": "Istanbul",
//                "arrivalCity": "Moscow",
//                "display_total_fare_per_ticket" : { $gt :  30, $lt : 400}}
//]})
        String origin;
        String destination;
        int minPrice;
        int maxPrice;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();
        System.out.println("Enter minimum price : ");
        minPrice = scanner.nextInt();
        System.out.println("Enter maximum price : ");
        maxPrice = scanner.nextInt();

        option9_1(collection, origin, destination, minPrice, maxPrice);
        System.out.println("_________________________________________");
        option9_2(collection, origin, destination, minPrice, maxPrice);

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option10(DBCollection collection) {
        System.out.println("Query - 7");
//        db.Flights.find({$and:[
//        {"departureCity": "Istanbul"},
//        {"arrivalCity": "Moscow"},
//        {"capacity" : "Airbus A320"}
//    ]})
        String origin;
        String destination;
        String capacity;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();
        System.out.println("Enter capacity : ");
        capacity = scanner.nextLine();

        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("arrivalCity", destination));
        obj.add(new BasicDBObject("departureCity", origin));
        obj.add(new BasicDBObject("capacity", capacity));
        andQuery.put("$and", obj);

        DBCursor cursor = collection.find(andQuery);
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }


        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option11() {
        System.out.println("Query - 8_6");
        // TODO :(
        String origin;
        String destination;
        int minPrice;
        int maxPrice;
        String fromDate;
        String toDate;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();
        System.out.println("Enter minimum price : ");
        minPrice = scanner.nextInt();
        System.out.println("Enter maximum price : ");
        maxPrice = scanner.nextInt();
        System.out.println("Enter start date: ");
        fromDate = scanner.nextLine();
        System.out.println("Enter end date: ");
        toDate = scanner.nextLine();

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }


    }

    private static void option12() {
        System.out.println("Query - 8_7");
        // TODO :(
        String origin;
        String destination;
        String capacity;
        String fromDate;
        String toDate;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();
        System.out.println("Enter capacity : ");
        capacity = scanner.nextLine();
        System.out.println("Enter start date: ");
        fromDate = scanner.nextLine();
        System.out.println("Enter end date: ");
        toDate = scanner.nextLine();

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option13(DBCollection collection) {
        System.out.println("Query - 9");
//        db.Flights1.find({$and:[
//        {"departureCity": "Brussels"},
//        { "arrivalCity": "Moscow"} , { "departreTime" : "2022-02-03" } ]}, { _id: 0, airline: 1 })
        String origin;
        String destination;
        String date;
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();
        System.out.println("Enter date : ");
        date = scanner.nextLine();

        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();

        obj.add(new BasicDBObject("arrivalCity", destination));
        obj.add(new BasicDBObject("departureCity", origin));
        obj.add(new BasicDBObject("departreTime", date));
        andQuery.put("$and", obj);

        DBCursor cursor = collection.find(andQuery);
        ArrayList<String> airline = new ArrayList<>();
        while (cursor.hasNext()) {
            if (airline.contains((String) cursor.next().get("airline")))
                continue;
            airline.add((String) cursor.next().get("airline"));
            System.out.println(cursor.next().get("airline"));
        }

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option14() {
        System.out.println("Query - 10");
//        db.Flights1.deleteMany( { $and : [{ "departreTime" : "2022-06-21" } , { "airline" : "Emirates Airlines" }]})
        String date;
        String airline;
        System.out.println("Enter date : ");
        date = scanner.nextLine();
        System.out.println("Enter name of airline : ");
        airline = scanner.nextLine();


        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }


    }

    private static void option15(MongoCollection<Document> coll) {
        System.out.println("Query - 11");
//  db.Flights.updateOne(
//    {"_id" : ObjectId("61f7a211eaf86ee5fc555c00")},
//    {
//        $set : { "capacity" : "Airbus A321"},
//        $currentDate: { lastModified: true }
//    }
//    )
        String id;
        String capacity;
        System.out.println("Enter flight id : ");
        id = scanner.nextLine();
        id = scanner.nextLine();
        System.out.println("Enter capacity : ");
        capacity = scanner.nextLine();

        ObjectId objectId = new ObjectId(id);
        Bson query = new Document("_id", objectId);
        Bson update = new Document("$set",
                new Document("capacity", capacity));

        coll.findOneAndUpdate(query, update);
        findAndPrint(coll);


        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }
    }

    private static void option16() {
        System.out.println("Query - 12");
//        db.Flights.updateMany({
//                "departureCity": "Istanbul",
//                "arrivalCity": "Moscow",
//                "departreTime" : "2022-02-06"},
//        {
//            $set : { "capacity" : "Airbus A325"},
//            $currentDate: { lastModified: true }
//        }
//)
        String airline;
        String date;
        String origin;
        String destination;
        System.out.println("Enter name of airline : ");
        airline = scanner.nextLine();
        airline = scanner.nextLine();
        System.out.println("Enter date : ");
        date = scanner.nextLine();
        System.out.println("Enter origin : ");
        origin = scanner.nextLine();
        System.out.println("Enter destination : ");
        destination = scanner.nextLine();


        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static void option17(DBCollection collection) {
        System.out.println("Query - 13");
//        db.Flights.find({$and:[
//        {"departureCity": "Brussels"},
//        { "arrivalCity": "Moscow"} , { "departreTime" : "2022-02-03" } ]}, { _id: 0, departureAirport: 1, arrivalAirport: 1 })
        String origin;
        String destination;
        String date;
        System.out.println("Enter origin country : ");
        origin = scanner.nextLine();
        origin = scanner.nextLine();
        System.out.println("Enter destination country : ");
        destination = scanner.nextLine();
        System.out.println("Enter date : ");
        date = scanner.nextLine();

        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();

        obj.add(new BasicDBObject("arrivalCountry", destination));
        obj.add(new BasicDBObject("departureCountry", origin));
        obj.add(new BasicDBObject("departreTime", date));
        andQuery.put("$and", obj);

        DBCursor cursor = collection.find(andQuery);
        while (cursor.hasNext()) {

            System.out.println("Origin Airport : " + cursor.next().get("departureAirport"));
            System.out.println("Destination Airport : " + cursor.next().get("arrivalAirport") + "\n");

        }

        int sort = sortHelper();
        if (sort == 1) {
            // Sort results ascending by date
        }
        if (sort == 2) {
            // Sort results descending by date

        }
        if (sort == 3) {
            // Sort results ascending by price
        }
        if (sort == 4) {
            // Sort results descending by price
        }

    }

    private static int sortHelper() {
        int choice;
        System.out.println("""
                1- Sort results ascending by date
                2- Sort results descending by date\040
                3- Sort results ascending by price\040
                4- Sort results descending by price\040
                5- Back""");
        choice = scanner.nextInt();
        if (!(choice > 0 && choice < 6)) {
            System.out.println("Please enter number between 1 and 5.");
            sortHelper();
        }
        return choice;
    }

    private static void findAndPrint(MongoCollection<Document> coll) {
        FindIterable<Document> cursor = coll.find();

        for (Document d : cursor)
            System.out.println(d);
    }

    public static void option9_1(DBCollection collection, String origin, String destination, int min, int max) {
        BasicDBObject query = new BasicDBObject();

        query.put("$and", Arrays.asList(
                        new BasicDBObject()
                                .append("departureCity", origin)
                                .append("arrivalCity", destination)
                                .append("display_total_fare_per_ticket", new BasicDBObject()
                                        .append("$gt", min)
                                        .append("$lt", max)
                                )
                )
        );

        BasicDBObject sort = new BasicDBObject();

        sort.put("display_total_fare_per_ticket", 1.0);

        int limit = 1;

        Cursor cursor = collection.find(query).sort(sort).limit(limit);
        while (cursor.hasNext()) {
            BasicDBObject document = (BasicDBObject) cursor.next();
            System.out.println(document.toString());
        }

    }

    public static void option9_2(DBCollection collection, String origin, String destination, int min, int max) {
        BasicDBObject query = new BasicDBObject();

        query.put("$and", Arrays.asList(
                        new BasicDBObject()
                                .append("departureCity", origin)
                                .append("arrivalCity", destination)
                                .append("display_total_fare_per_ticket", new BasicDBObject()
                                        .append("$gt", min)
                                        .append("$lt", max)
                                )
                )
        );

        Cursor cursor = collection.find(query);
        while (cursor.hasNext()) {
            BasicDBObject document = (BasicDBObject) cursor.next();
            System.out.println(document.toString());
        }


    }
}