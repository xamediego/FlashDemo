package mai.flash;

import javafx.application.Application;
import mai.flash.fxlauncher.JavafxApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlashDemo {

	public static void main(String[] args) {
		Application.launch(JavafxApplication.class, args);
	}

}
