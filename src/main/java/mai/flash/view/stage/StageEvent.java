package mai.flash.view.stage;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StageEvent extends ApplicationEvent {

    private final FxmlView view;

    public StageEvent(Stage source, FxmlView view){
        super(source);
        this.view = view;
    }

    public FxmlView getView(){
        return this.view;
    }

    public Stage getStage(){
        return Stage.class.cast(getSource());
    }

}
