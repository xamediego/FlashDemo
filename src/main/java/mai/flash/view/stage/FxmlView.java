package mai.flash.view.stage;

public enum FxmlView {

    MAIN{
        public String getPath(){
            return "/FxmlFiles/MainDisplay.fxml";
        }
    };

    abstract String getPath();
}
