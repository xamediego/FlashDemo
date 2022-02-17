package mai.flash.view.scene;

public enum FxmlParts {

    DATA{
        public String getPath(){
            return "/FxmlFiles/DataView.fxml";
        }
    }, DECK{
        public String getPath(){
            return "/FxmlFiles/DeckView.fxml";
        }
    }, DECKTEMP{
        public String getPath(){
            return "/FxmlFiles/DeckTemplate.fxml";
        }
    }, FLASHMENU{
        public String getPath(){
            return "/FxmlFiles/FlashcardMenuView.fxml";
        }
    }, FLASHVIEW{
        public String getPath(){return "/FxmlFiles/FlashcardView.fxml";}
    };

    abstract String getPath();

}
