package mai.flash.events.deldeckevent;

import org.springframework.context.ApplicationEvent;

public class DeleteDeckEvent extends ApplicationEvent {

    public DeleteDeckEvent(Long source) {
        super(source);
    }

    public Long getDeckId(){
        return Long.class.cast(getSource());
    }

}
