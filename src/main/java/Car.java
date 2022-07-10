
package com.journaldev.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Car {

    // method that retrieves all the documents present in the database without
    // any criteria
    public static void findAll() throws UnknownHostException {

        // Get a new connection to the db assuming that it is running
        MongoClient mongoClient = new MongoClient("localhost");

        // //use test as a datbase,use your database here
        DB db = mongoClient.getDB("test");

        // //fetch the collection object ,car is used here,use your own
        DBCollection coll = db.getCollection("car");

        // //invoking find() method and storing the result in carCursor
        DBCursor carCursor = coll.find();

        // printing the cursor contents
        try {
            while (carCursor.hasNext()) {
                System.out.println(carCursor.next());
            }
        } finally {
            carCursor.close();// close the cursor
        }
    }

    // method to retrieve documents based on the selection criteria
    public static void findBYCriteria() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("Flight");
        DBCollection col = db.getCollection("car");

        // criteria specified as speed greater than 50
        DBObject query = new BasicDBObject("speed",
                new BasicDBObject("$gt", 50));
        // /result stored in cursor using find() method
        DBCursor carCursor1 = col.find(query);
        System.out
                .println(" ");
        try {
            while (carCursor1.hasNext()) {
                System.out.println(carCursor1.next());
            }
        } finally {
            carCursor1.close();
        }
    }

    public static void findByEquality() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");

        // criteria with id 3 is specified
        DBObject query = new BasicDBObject("_id", 3);
        DBCursor c1 = col.find(query);
        System.out
                .println(" ");
        try {
            while (c1.hasNext()) {
                System.out.println(c1.next());
            }
        } finally {
            c1.close();
        }
    }

    public static void findByQueryOperators() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");

        // criteria with speed greater than 40 and less than 65
        DBObject query = new BasicDBObject("speed",
                new BasicDBObject("$gt", 40).append("$lt", 65));
        DBCursor carCursor1 = col.find(query);
        System.out
                .println(" ");
        try {
            while (carCursor1.hasNext()) {
                System.out.println(carCursor1.next());
            }
        } finally {
            carCursor1.close();
        }
    }

    public static void findByfields() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");

        DBObject query = new BasicDBObject("speed",
                new BasicDBObject("$gt", 60));
        // fields with name and speed field is specified and only these fields
        // are displayed
        BasicDBObject fields = new BasicDBObject("name", 1).append("speed", 1);
        DBCursor carCursor1 = col.find(query, fields);
        System.out
                .println(" ");
        try {
            while (carCursor1.hasNext()) {
                System.out.println(carCursor1.next());
            }
        } finally {
            carCursor1.close();
        }
    }

    public static void excludeByfields() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");

        DBObject query = new BasicDBObject("speed",
                new BasicDBObject("$gt", 65));
        // excluding mfdcountry and cno fields by setting to 0
        BasicDBObject fields = new BasicDBObject("mfdcountry", 0).append("cno",
                0);
        DBCursor carCursor1 = col.find(query, fields);
        System.out
                .println(" ");
        try {
            while (carCursor1.hasNext()) {
                System.out.println(carCursor1.next());
            }
        } finally {
            carCursor1.close();
        }
    }

    public static void excludeByIdfield() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");

        DBObject query = new BasicDBObject("speed",
                new BasicDBObject("$gt", 65)); // excluding id field by setting
        // to 0
        BasicDBObject fields = new BasicDBObject("_id", 0);
        DBCursor carCursor1 = col.find(query, fields);
        System.out
                .println(" ");
        try {
            while (carCursor1.hasNext()) {
                System.out.println(carCursor1.next());
            }
        } finally {
            carCursor1.close();
        }
    }

    public static void sortMongodb() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");
        DBCursor carCursor = col.find();
        // sort the car collection in ascending order
        carCursor.sort(new BasicDBObject("name", 1));

        try {
            while (carCursor.hasNext()) {

                System.out.println(carCursor.next());
            }
        } finally {
            carCursor.close();
        }
    }

    public static void limitMongodb() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");
        // limits to only 2 records
        DBCursor carCursor = col.find().limit(2);

        try {
            while (carCursor.hasNext()) {

                System.out.println(carCursor.next());
            }
        } finally {
            carCursor.close();
        }
    }

    public static void skipMongodb() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");
        // skips the first 10 records
        DBCursor carCursor = col.find().skip(10);
        System.out
                .println(" ");
        try {
            while (carCursor.hasNext()) {

                System.out.println(carCursor.next());
            }
        } finally {
            carCursor.close();
        }
    }

    public static void sortLimitMongodb() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");
        DBCursor carCursor = col.find();
        // combining sort and limit methods
        carCursor.sort(new BasicDBObject("name", 1)).limit(2);
        System.out
                .println(" ");
        try {
            while (carCursor.hasNext()) {
                System.out.println(carCursor.next());
            }
        } finally {
            carCursor.close();
        }
    }

    public static void insertArray() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car"); // regno array is declared
        List<Integer> regno = new ArrayList<Integer>();
        // adding values to regno field
        regno.add(31);
        regno.add(41);
        regno.add(65);
        regno.add(75);
        // setting regno to new object b1
        BasicDBObject b1 = new BasicDBObject("regno", regno);
        // inserting b1 to collection col
        col.insert(b1);
        DBCursor c3 = col.find();
        try {
            while (c3.hasNext()) {
                System.out.println(c3.next());
            }
        } finally {
            c3.close();
        }
    }

    public static void queryArray() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("test");
        DBCollection col = db.getCollection("car");

        // querying for array values greater than 31 and less than 65
        DBObject query = new BasicDBObject("regno",
                new BasicDBObject("$gt", 31).append("$lt", 65));
        DBCursor c1 = col.find(query);
        try {
            while (c1.hasNext()) {
                System.out.println(c1.next());
            }
        } finally {
            c1.close();
        }
    }

    public static void queryArrayElement() throws UnknownHostException {
        MongoClient m1 = new MongoClient("localhost");
        DB db = m1.getDB("Flight");
        DBCollection col = db.getCollection("car");

        // quering for regno 75
        DBObject query = new BasicDBObject("regno", 75);
        DBCursor c1 = col.find(query);

        try {
            while (c1.hasNext()) {
                System.out.println(c1.next());
            }
        } finally {
            c1.close();
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        queryArrayElement();
    }

}
