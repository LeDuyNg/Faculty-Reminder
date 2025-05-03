package s25.cs151.application.Controller;

import java.util.ArrayList;

public interface ModelControllerInt<E> {
    public ArrayList<E> loadModelObjects();
    public void saveModelObjects(E object);
}
