package com.mpeixoto.product;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Class responsible for reading files.
 *
 * @author mpeixoto
 */
public class ReadFile implements TestRule {

  @Override
  public Statement apply(Statement statement, Description description) {
    return statement;
  }

  /**
   * Method responsible for reading the content of a file and returning as a String.
   *
   * @param argument path where the file that will be read is
   * @return The content of the file
   */
  public static String readFile(String argument) {

    try {
      byte[] bytes = Files.readAllBytes(new File(argument).toPath());
      return new String(bytes, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
