package dk.easv.GUI.Controller;

import dk.easv.BE.Event;

import java.io.IOException;

public interface IEventController extends IController {
    void setEventInfo(Event event) throws IOException;
}
