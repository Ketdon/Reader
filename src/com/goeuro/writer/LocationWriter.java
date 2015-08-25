package com.goeuro.writer;

import com.goeuro.model.Location;

/**
 * Created by dmitrykoval on 23/08/15.
 */
public interface LocationWriter {

  void write(Iterable<Location> locations) throws LocationWriterException;

}
