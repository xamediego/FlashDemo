package mai.flash.view.scene;

public enum FxmlParts {

    DATA{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/DataView.fxml";
        }
    }, DECK{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/DeckView.fxml";
        }
    }, DECKTEMP{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/DeckTemplate.fxml";
        }
    }, FLASHMENU{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/FlashcardMenuView.fxml";
        }
    }, FLASHVIEW{
        public String getPath(){return "/FxmlFiles/FxmlParts/FlashcardView.fxml";}
    }, CARD{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/ChangeCard.fxml";
        }
    }, IMPORT{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/ImportScreen.fxml";
        }
    }, GROUPDECK{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/GroupDeckMenu.fxml";
        }
    }, GROUPMENU{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/GroupMenu.fxml";
        }
    }, GRFLASHVIEW{
        public String getPath(){
            return "/FxmlFiles/FxmlParts/GroupFlashView.fxml";
        }
    };

    abstract String getPath();

}
