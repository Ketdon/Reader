package com.goeuro;

import com.goeuro.model.Location;
import com.goeuro.reader.HttpLocationReader;
import com.goeuro.reader.LocationReaderException;
import com.goeuro.writer.CsvLocationWriter;
import com.goeuro.writer.LocationWriter;
import com.goeuro.writer.LocationWriterException;

public class Main {

  private static void printUsage() {
    System.out.println("Please provide location name as an argument.");
  }

  private static String parseInput(String[] args) {
    String location = null;

    if (args.length == 1 && args[0] != null && args[0].length() > 0)
      location = args[0];

    return location;
  }

  public static void main(String[] args) {
    String location = parseInput(args);

    if (location == null) {
      printUsage();
      System.exit(1);
    }

    Iterable<Location> locReader = new HttpLocationReader(location);
    LocationWriter locWriter = new CsvLocationWriter("locations.csv");

    try {
      locWriter.write(locReader);
    } catch (LocationWriterException | LocationReaderException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }
}
