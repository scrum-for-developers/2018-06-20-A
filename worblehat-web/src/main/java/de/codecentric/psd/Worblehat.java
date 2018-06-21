package de.codecentric.psd;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class Worblehat {

	private Worblehat () {
		super();
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Worblehat.class, args);

		// this code is basically to (a) demonstrate how to stop a Spring application and (b)
		// get rid of the SonarQube warning to close the context properly
		System.out.println("Enter 'stop' to stop Worblehat.");
		String line;
		do {
			line = scan.nextLine();
		} while (!StringUtils.equals(line, "stop"));
		applicationContext.close();
	}

}
