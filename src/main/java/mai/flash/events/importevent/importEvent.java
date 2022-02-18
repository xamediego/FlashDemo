package mai.flash.events.importevent;

import org.springframework.context.ApplicationEvent;

public class importEvent extends ApplicationEvent {

    private final String deckName;

    public importEvent(String source, String deckName){
        super(source);
        this.deckName = deckName;
    }

    public String getFilePath(){
        return String.class.cast(getSource());
    }

    public String getDeckName() {
        return deckName;
    }

}
