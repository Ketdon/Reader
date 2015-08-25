package com.goeuro.reader;

import com.goeuro.model.Location;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by dmitrykoval on 23/08/15.
 *
 * This reader assumes the result is relatively small and easily fits into memory.
 * In case amount of data is big, streaming JSON parser would be more appropriate.
 */
public class HttpLocationReader implements Iterable<Location> {

  private static final String LOCATION_API_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";

  private final String location;
  private Iterator<JsonValue> locationsJsonIterator;

  public HttpLocationReader(String location) {
    this.location = location;
  }


  @Override
  public Iterator<Location> iterator() {
    initJsonReader();

    return new Iterator<Location>() {
      @Override
      public boolean hasNext() {
        return locationsJsonIterator.hasNext();
      }

      @Override
      public Location next() {
        return readNextLocation();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  private void initJsonReader() {
    String requestUrl = LOCATION_API_URL + location;
    try (InputStream is = new URL(requestUrl).openStream(); JsonReader jsonReader = Json.createReader(is)) {
      locationsJsonIterator = jsonReader.readArray().iterator();
    } catch (IOException | JsonException | IllegalStateException  e) {
      throw new LocationReaderException("Cannot read locations from HTTP API.", e);
    }
  }

  private Location readNextLocation() {
    Location location = null;

    JsonValue val = locationsJsonIterator.next();
    if (val != null && val.getValueType() == JsonValue.ValueType.OBJECT) {
      JsonObject locationObj = (JsonObject) val;
      JsonNumber id = locationObj.getJsonNumber("_id");
      String name = locationObj.getString("name");
      String type = locationObj.getString("type");
      JsonObject coords = locationObj.getJsonObject("geo_position");

      if (id != null && name != null && type != null && coords != null && coords.containsKey("latitude") && coords.containsKey("longitude")) {
        double lat = coords.getJsonNumber("latitude").doubleValue();
        double lon = coords.getJsonNumber("longitude").doubleValue();

        location = new Location(id.bigIntegerValue(), name, type, lat, lon);
      }
    }

    return location;
  }

}
