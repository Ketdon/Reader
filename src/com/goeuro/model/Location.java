package com.goeuro.model;

import java.math.BigInteger;

/**
 * Created by dmitrykoval on 23/08/15.
 */
public class Location {

  private final BigInteger id;
  private final String name;
  private final String type;
  private final double lat;
  private final double lon;

  public Location(BigInteger id, String name, String type, double lat, double lon) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.lat = lat;
    this.lon = lon;
  }

  public BigInteger getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public double getLat() {
    return lat;
  }

  public double getLon() {
    return lon;
  }

  @Override
  public String toString() {
    return "ID: " + id + " Name: " + name + " Type: " + type + " lat: " + lat + " lon: " + lon;
  }
}
