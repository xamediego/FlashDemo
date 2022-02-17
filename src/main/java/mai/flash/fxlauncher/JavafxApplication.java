package mai.flash.fxlauncher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mai.flash.FlashDemo;
import mai.flash.view.scene.SceneSwitcher;
import mai.flash.view.stage.FxmlView;
import mai.flash.view.stage.StageEvent;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class JavafxApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void start(Stage stage){
        this.context.publishEvent(new StageEvent((stage), FxmlView.MAIN));
    }

    @Override
    public void init(){
        ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
            ac.registerBean(Application.class, () -> JavafxApplication.this);
        };
        this.context = new SpringApplicationBuilder().sources(FlashDemo.class).initializers(initializer).run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void stop(){
        this.context.close();
        Platform.exit();
    }
}
