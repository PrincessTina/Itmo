package com.company;

import com.google.gson.Gson;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class ORM {
    static Connection connection;
    static private Class currentClass;
    static private Field[] fieldsOfCurrentClass;

    static ArrayList<Object> read(String stringClass) throws Exception {
        ArrayList<Object> objects = new ArrayList<>();

        CachedRowSet resultSet = new CachedRowSetImpl();
        Statement statement = connection.createStatement();
        currentClass = Class.forName(stringClass);
        Object newObject = currentClass.newInstance();
        fieldsOfCurrentClass = newObject.getClass().getDeclaredFields();
        String date;
        Status status;
        ArrayList<Integer> digit = new ArrayList<>();

        createTable(stringClass);

        resultSet.populate(statement.executeQuery("select * from " + currentClass.getSimpleName()));

        while (resultSet.next()) {
            newObject = currentClass.newInstance();
            for (Field field : fieldsOfCurrentClass) {
                field.setAccessible(true);

                if (field.getType().getSimpleName().equals("ZonedDateTime")) {
                    date = resultSet.getString(field.getName());

                    for (String tokens : date.split("-")) {
                        digit.add(Integer.parseInt(tokens));
                    }
                    field.set(newObject, ZonedDateTime.of(digit.get(0), digit.get(1), digit.get(2),
                            digit.get(3), digit.get(4),
                            digit.get(5), digit.get(6), ZoneId.of("Europe/Moscow")));

                    digit.clear();
                } else if (field.getType().getSimpleName().equals("Status")) {
                    switch (resultSet.getString(field.getName())) {
                        case "married":
                            status = Status.married;
                            break;
                        case "idle":
                            status = Status.idle;
                            break;
                        case "have_a_girlfriend":
                            status = Status.have_a_girlfriend;
                            break;
                        case "single":
                            status = Status.single;
                            break;
                        default:
                            status = Status.all_is_complicated;
                    }
                    field.set(newObject, status);
                } else if (field.getType().getSimpleName().equals("double")) {
                    field.set(newObject, resultSet.getDouble(field.getName()));
                } else {
                    field.set(newObject, resultSet.getObject(field.getName()));
                }
            }
            objects.add(newObject);
        }
        return objects;
    }

    static void remove(ArrayList<Integer> indexes) throws Exception {
        String request1 = "delete from " + currentClass.getSimpleName() + " where id = ?;";
        String request2 = "alter sequence " + currentClass.getSimpleName() + "_seq restart with 0;";
        String request3 = "update " + currentClass.getSimpleName() + " set id = nextval('" +
                currentClass.getSimpleName() + "_seq');";

        CallableStatement statement = connection.prepareCall(request1);
        connection.setAutoCommit(false);

        for (int index : indexes) {
            statement.setInt(1, index);
            statement.execute();
        }
        connection.commit();
        connection.setAutoCommit(true);

        statement = connection.prepareCall(request2);
        statement.execute();

        statement = connection.prepareCall(request3);
        statement.execute();
    }

    static void add(String stringForNewCollection) throws Exception {
        ArrayList<Object> collection = parsingObject(stringForNewCollection);
        Object newShorty = collection.get(0);
        StringBuilder request = new StringBuilder();
        StringBuilder values = new StringBuilder();

        request.append("insert into ");
        request.append(currentClass.getSimpleName());
        request.append("(");

        for (Field field : fieldsOfCurrentClass) {
            request.append(field.getName());
            request.append(", ");

            field.setAccessible(true);

            if ((field.getType() == String.class) || (field.getType() == Status.class)) {
                values.append("'");
                values.append(field.get(newShorty));
                values.append("', ");
            } else if (field.getType() == double.class) {
                values.append(field.get(newShorty));
                values.append("::decimal, ");
            } else if (field.getType() == ZonedDateTime.class) {
                values.append("'");
                values.append(ZonedDateTime.parse(
                        field.get(newShorty).toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-0")));
                values.append("', ");
            } else {
                values.append(field.get(newShorty));
                values.append(", ");
            }
        }
        request.delete(request.length() - 2, request.length());
        request.append(") values(");
        request.append(values);
        request.delete(request.length() - 2, request.length());
        request.append(");");

        connection.prepareStatement(request.toString()).execute();
    }

    static void modify(String stringForNewCollection, ArrayList<Integer> indexes) throws Exception {
        ArrayList<Object> collection = parsingObject(stringForNewCollection);
        Object newShorty = collection.get(0);
        StringBuilder request = new StringBuilder();
        int index = indexes.get(0);

        request.append("update ");
        request.append(currentClass.getSimpleName());
        request.append(" set ");

        for (Field field : fieldsOfCurrentClass) {
            if (!field.getName().matches("id")) {
                request.append(field.getName());
                request.append(" = ");

                field.setAccessible(true);

                if ((field.getType() == String.class) || (field.getType() == Status.class)) {
                    request.append("'");
                    request.append(field.get(newShorty));
                    request.append("', ");
                } else if (field.getType() == double.class) {
                    request.append(field.get(newShorty));
                    request.append("::decimal, ");
                } else if (field.getType() == ZonedDateTime.class) {
                    request.append("'");
                    request.append(ZonedDateTime.parse(
                            field.get(newShorty).toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-0")));
                    request.append("', ");
                } else {
                    request.append(field.get(newShorty));
                    request.append(", ");
                }
            }
        }
        request.delete(request.length() - 2, request.length());
        request.append(" where id = ");
        request.append(index);
        request.append(";");

        connection.prepareStatement(request.toString()).execute();
    }

    private static void createTable(String stringClass) throws Exception {
        StringBuilder request = new StringBuilder();
        currentClass = Class.forName(stringClass);

        request.append("create table if not exists ");
        request.append(currentClass.getSimpleName());
        request.append("(\n");
        request.append(buildFields());
        request.append(");");

        connection.prepareCall(request.toString()).execute();
    }

    private static String buildFields() {
        StringBuilder request = new StringBuilder();

        for (Field field: fieldsOfCurrentClass) {
            request.append(field.getName());
            request.append(" ");
            request.append(convertType(field.getType().getSimpleName()));
            request.append(",\n");
        }
        request.deleteCharAt(request.length() - 1);
        return request.toString();
    }

    private static String convertType(String type) {
        switch (type) {
            case "double":
                return "decimal";
            case "int":
                return "int";
            default:
                return "text";
        }
    }

    private static ArrayList<Object> parsingObject(String string) throws Exception {
        ArrayList<Object> searchedCollection = new ArrayList<>();
        Gson gson = new Gson();
        String arrayOfObjects = string.substring(0, string.length()), date;
        Object newObject;
        boolean state = false;

        for (String tokens : arrayOfObjects.split("%")) {
            for (Field field: fieldsOfCurrentClass) {
                if (field.getType().getSimpleName().equals("ZonedDateTime")) {
                    date = tokens.substring(tokens.indexOf("date") - 2, tokens.length() - 2).substring(9);
                    tokens = tokens.substring(0, tokens.indexOf("date") - 2) + "}";
                    newObject = gson.fromJson(tokens, currentClass);
                    field.set(newObject, ZonedDateTime.parse(date));

                    searchedCollection.add(newObject);
                    state = true;
                }
            }
            if (!state) {
                searchedCollection.add(gson.fromJson(tokens, currentClass));;
            }
            state = false;
        }
        return searchedCollection;
    }
}
