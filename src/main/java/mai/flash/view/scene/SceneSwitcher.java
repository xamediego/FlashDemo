package mai.flash.view.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SceneSwitcher {

    @Autowired
    private ApplicationContext applicationContext;

    public Node getNode(FxmlParts view) throws IOException {
        return loader(view).load();
    }

    public FXMLLoader loader(FxmlParts view){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getPath()));
        loader.setControllerFactory(applicationContext::getBean);
        return loader;
    }
}
