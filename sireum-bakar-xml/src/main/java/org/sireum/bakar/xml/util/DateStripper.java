package org.sireum.bakar.xml.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DateStripper {

  public static void main(final String[] args) throws IOException {
    final File f = new File(DateStripper.class.getResource(".").getPath()
        .replace("bin", "src/main/java")).getParentFile();

    for (final File f2 : f.listFiles()) {
      if (f2.isFile()) {
        final StringBuilder sb = new StringBuilder();
        final BufferedReader br = new BufferedReader(new FileReader(f2));
        String line;
        while ((line = br.readLine()) != null) {
          if (!line.contains("Generated on:")) {
            sb.append(line).append("\n");
          }
        }
        br.close();

        final FileWriter fw = new FileWriter(f2);
        fw.write(sb.toString());
        fw.close();
        System.out.println("Wrote: " + f2);
      }
    }
  }
}
