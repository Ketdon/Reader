package com.goeuro.writer;

import com.goeuro.model.Location;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dmitrykoval on 23/08/15.
 */
public class CsvLocationWriter implements LocationWriter {

  private final String filename;

  public CsvLocationWriter(String filename) {
    this.filename = filename;
  }

  public void write(Iterable<Location> locations) throws LocationWriterException {
    try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
      for (Location location : locations) {
        if (location != null)
          writer.writeNext(locationValuesAsStrings(location));
      }
    } catch (IOException e) {
      throw new LocationWriterException("Failed to write locations into csv file.", e);
    }
  }

  private String[] locationValuesAsStrings(Location location) {
    return new String[] {
            location.getId().toString(),
            location.getName(),
            location.getType(),
            Double.toString(location.getLat()),
            Double.toString(location.getLon())
    };
  }
}
