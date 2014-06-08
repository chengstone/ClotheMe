
//public class ClotheMeDaoGenerator {
//
//}
/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ClotheMeDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.daogen.clotheme");
//        schema.setDefaultJavaPackageTest("de.greenrobot.daoexample.test");
//        schema.setDefaultJavaPackageDao("de.greenrobot.daoexample.dao");
        
        schema.enableKeepSectionsByDefault();
     // KEEP INCLUDES - put your custom includes here in the com.daogen.clotheme

     // KEEP INCLUDES END

     // KEEP FIELDS - put your custom fields here in the com.daogen.clotheme

     // KEEP FIELDS END

     // KEEP METHODS - put your custom methods here in the com.daogen.clotheme

     // KEEP METHODS END
//        schema.enableActiveEntitiesByDefault();
        addCategoryArchive(schema);
        addInitialFlag(schema);
        addCategory(schema);
        addStyle(schema);
        addThickness(schema);
        addWearPlace(schema);
        addMeterial(schema);
        addPeopleInfo(schema);
        addSeason(schema);
        addStorageLocation(schema);
//        addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "/Users/chengstone/Documents/workspace/ClotheMe/src/com/daogen/clotheme");
    }

    private static void addCategoryArchive(Schema schema) {
        Entity CategoryArchive = schema.addEntity("CategoryArchive");
        CategoryArchive.addIdProperty().notNull().primaryKey().autoincrement();
        CategoryArchive.addIntProperty("MeterialID");
        CategoryArchive.addIntProperty("IsWashRemind");
        CategoryArchive.addStringProperty("RemindTime");
        CategoryArchive.addStringProperty("RemindFrequency");
        CategoryArchive.setTableName("CategoryArchive");
//        CategoryArchive.addDateProperty("date");
    }
    
    private static void addInitialFlag(Schema schema) {
        Entity InitialFlag = schema.addEntity("InitialFlag");
        InitialFlag.addIdProperty().notNull().primaryKey().autoincrement();
        InitialFlag.addBooleanProperty("InitialFlag");
    }

    private static void addCategory(Schema schema) {
        Entity Category = schema.addEntity("Category");
        Category.addIdProperty().notNull().primaryKey().autoincrement();
        Category.addStringProperty("Name");
        Category.addIntProperty("IsSubCategory");
        Category.addIntProperty("BelongCategoryID");
        Category.setTableName("Category");
//        CategoryArchive.addDateProperty("date");
    }
    
    private static void addStyle(Schema schema) {
        Entity Style = schema.addEntity("Style");
        Style.addIdProperty().notNull().primaryKey().autoincrement();
        Style.addStringProperty("Style");
        Style.setTableName("Style");
//        CategoryArchive.addDateProperty("date");
    }
    
    private static void addThickness(Schema schema) {
        Entity Thickness = schema.addEntity("Thickness");
        Thickness.addIdProperty().notNull().primaryKey().autoincrement();
        Thickness.addStringProperty("Thickness");
        Thickness.addIntProperty("Temperature");
        Thickness.addStringProperty("Whether");
        Thickness.setTableName("Thickness");
//        CategoryArchive.addDateProperty("date");
    }
    
    private static void addWearPlace(Schema schema) {
        Entity WearPlace = schema.addEntity("WearPlace");
        WearPlace.addIdProperty().notNull().primaryKey().autoincrement();
        WearPlace.addStringProperty("WearPlace");
        WearPlace.setTableName("WearPlace");
//        CategoryArchive.addDateProperty("date");
    }
    
    private static void addMeterial(Schema schema) {
        Entity Meterial = schema.addEntity("Meterial");
        Meterial.addIdProperty().notNull().primaryKey().autoincrement();
        Meterial.addStringProperty("Description");
        Meterial.addStringProperty("PicPath");
        Meterial.addStringProperty("Memo");
        Meterial.addIntProperty("BelongCategoryID");
        Meterial.addIntProperty("LocationID");
        Meterial.addIntProperty("PersonID");
        Meterial.addIntProperty("SeasonID");
        Meterial.addStringProperty("LastWashDate");
        Meterial.addIntProperty("ThicknessID");
        Meterial.addStringProperty("UseDate");
        Meterial.addStringProperty("WearPlaceID");
        Meterial.addIntProperty("StyleID");
        Meterial.setTableName("Meterial");
//        CategoryArchive.addDateProperty("date");
    }
    
    private static void addPeopleInfo(Schema schema) {
        Entity PeopleInfo = schema.addEntity("PeopleInfo");
        PeopleInfo.addIdProperty().notNull().primaryKey().autoincrement();
        PeopleInfo.addStringProperty("PersonName");
        PeopleInfo.addIntProperty("StyleID");
        PeopleInfo.setTableName("PeopleInfo");
//        CategoryArchive.addDateProperty("date");
    }
    
    private static void addSeason(Schema schema) {
        Entity Season = schema.addEntity("Season");
        Season.addIdProperty().notNull().primaryKey().autoincrement();
        Season.addStringProperty("Season");
        Season.addIntProperty("RecommendedThick");
        Season.addStringProperty("ProbablyTemp");
        Season.setTableName("Season");
//        CategoryArchive.addDateProperty("date");
    }
    
    private static void addStorageLocation(Schema schema) {
        Entity StorageLocation = schema.addEntity("StorageLocation");
        StorageLocation.addIdProperty().notNull().primaryKey().autoincrement();
        StorageLocation.addStringProperty("Location");
        StorageLocation.addStringProperty("LocationPath");
        StorageLocation.setTableName("StorageLocation");
//        CategoryArchive.addDateProperty("date");
    }
    
//    private static void addCustomerOrder(Schema schema) {
//        Entity customer = schema.addEntity("Customer");
//        customer.addIdProperty();
//        customer.addStringProperty("name").notNull();
//
//        Entity order = schema.addEntity("Order");
//        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
//        order.addIdProperty();
//        Property orderDate = order.addDateProperty("date").getProperty();
//        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
//        order.addToOne(customer, customerId);
//
//        ToMany customerToOrders = customer.addToMany(order, customerId);
//        customerToOrders.setName("orders");
//        customerToOrders.orderAsc(orderDate);
//    }

}
