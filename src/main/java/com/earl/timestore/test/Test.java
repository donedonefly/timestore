package com.earl.timestore.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.ParseException;
import java.util.Properties;

public class Test {

	public static void main(String[] args) throws ParseException, IOException {
		Properties prop = new Properties();
		InputStream reader = Test.class.getClassLoader().getResourceAsStream("com/earl/timestore/test/test.properties");
		prop.load(reader);
		System.out.println(prop);
		reader.close();
	}

}
