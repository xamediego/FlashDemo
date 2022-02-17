package mai.flash.view.stage;

public enum FxmlView {

    MAIN{
        public String getPath(){
            return "/FxmlFiles/MainDisplay.fxml";
        }
    }, CARD{
        public String getPath(){
            return "/FxmlFiles/ChangeCard.fxml";
        }
    };

    abstract String getPath();
}
