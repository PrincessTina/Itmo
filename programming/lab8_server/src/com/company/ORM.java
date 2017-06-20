package com.company;

import org.postgresql.ds.PGConnectionPoolDataSource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ORM {
  private PGConnectionPoolDataSource dataSource;
  private ResultSet resultSet; private Class currentClass;
  private Field[] fieldsOfCurrentClass;
  private StringBuilder queryBuilder = new StringBuilder();
  private StringBuilder fieldBuilder = new StringBuilder();

  public ORM(PGConnectionPoolDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void createTable(String o) throws IOException, SQLException, ClassNotFoundException {
    queryBuilder.delete(0,queryBuilder.length());
    currentClass = Class.forName(o);
    queryBuilder.append("CREATE TABLE IF NOT EXISTS ");
    queryBuilder.append(currentClass.getSimpleName());
    queryBuilder.append(" (");
    queryBuilder.append(System.lineSeparator());
    queryBuilder.append(buildFields(currentClass.getDeclaredFields()));
    queryBuilder.append(");");

    try {
      dataSource.getConnection().prepareCall(queryBuilder.toString()).execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    queryBuilder.delete(0,queryBuilder.length());
    fieldBuilder.delete(0, fieldBuilder.length());
  }

  private String buildFields(Field[] fields) {
    fieldBuilder.delete(0, fieldBuilder.length());
    String type, name;
    for (int i = 0; i < fields.length; i++) {
      type = fields[i].getType().getSimpleName();
      name = fields[i].getName();
      fieldBuilder.append(name);
      fieldBuilder.append(" "); fieldBuilder.append(convertType(type));
      fieldBuilder.append(","); fieldBuilder.append(System.lineSeparator());
    }

    fieldBuilder.append("key serial" + System.lineSeparator());
    return fieldBuilder.toString();
  }

  public void remove(Object o, String key, String compareSign) throws SQLException {
    int removeKey = Integer.parseInt(key);
    dataSource.getConnection().prepareCall("DELETE FROM " + o.getClass().getSimpleName() +
        " WHERE key" + compareSign + removeKey).executeUpdate();
    System.out.println(dataSource);
  }

  public void insertObject(Object o) throws IllegalAccessException, SQLException {
    fieldBuilder.delete(0, fieldBuilder.length());
    queryBuilder.delete(0, queryBuilder.length());
    currentClass = o.getClass(); Field[] fields = currentClass.getDeclaredFields();
    String value;
    queryBuilder.append("INSERT INTO ");
    queryBuilder.append(currentClass.getSimpleName());
    queryBuilder.append(" (");

    for (int i = 0; i < fields.length; i++) {
      if (convertType(fields[i].getType().getSimpleName()).equals("0"))
        continue;
      else {
        queryBuilder.append(fields[i].getName());
        if (i != fields.length-1) queryBuilder.append(",");
      } fields[i].setAccessible(true);
      value = fields[i].get(o).toString();
      fieldBuilder.append("'").append(value).append("'");
      if (i != fields.length-1) fieldBuilder.append(",");
    }
    queryBuilder.append(") VALUES (").append(fieldBuilder.toString()).append(");");
    dataSource.getConnection().prepareStatement(queryBuilder.toString()).execute();
    queryBuilder.delete(0, queryBuilder.length());
    fieldBuilder.delete(0, fieldBuilder.length());
  }

  private String convertType(String type) {
    switch (type) {
      case "String":
        return "text";
      case "int":
        return "integer";
      case "long":
        return "integer";
      case "byte":
        return "smallint";
      case "float":
        return "real";
      case "double":
        return "real";
      case "boolean":
        return "boolean";
        case "LocalDateTime":
          return "text";
    }
    return "text";
  }

  public ArrayList<Object> get(String o) {
    ArrayList<Integer> keys = new ArrayList<>();
    ArrayList<Object> objects = new ArrayList<>();

    try {
      currentClass = Class.forName(o);
      createTable(o);
      resultSet = dataSource.getConnection().createStatement().executeQuery("SELECT * FROM "+
          currentClass.getSimpleName());
    } catch (ClassNotFoundException | SQLException |IOException e) {
      System.out.println(e.getMessage());
      System.out.println("Error in get");
    }

    try {
      while (resultSet.next()) {
        Object newObject = currentClass.newInstance();
        fieldsOfCurrentClass = newObject.getClass().getDeclaredFields();
        for(int i = 0; i < fieldsOfCurrentClass.length; i++) {
          Field field = fieldsOfCurrentClass[i];
          field.setAccessible(true);
          System.out.println(field.getName() + "=" + resultSet.getObject(field.getName()));
          if(field.getType().getSimpleName().equals("LocalDateTime"))
          { System.out.println("profit");
          field.set(newObject, LocalDateTime.parse(resultSet.getString(field.getName())));
          } else {
            field.set(newObject, resultSet.getObject(field.getName()));
          }
        }
        keys.add(resultSet.getInt("key")); objects.add(newObject);
      }
      objects.add(keys);
    } catch (SQLException | InstantiationException | IllegalAccessException e) {
      System.out.println(e.getMessage());
      System.out.println("Error in get");
    }
    return objects;
  }
}
