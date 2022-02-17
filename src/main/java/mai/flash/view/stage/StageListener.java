package mai.flash.view.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageListener implements ApplicationListener<StageEvent> {

    private final ApplicationContext applicationContext;

    public StageListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageEvent stageEvent) {

        try {
            Stage stage = stageEvent.getStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(stageEvent.getView().getPath()));
            loader.setControllerFactory(applicationContext::getBean);
            Parent rootView = loader.load();
            Scene scene = new Scene(rootView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
