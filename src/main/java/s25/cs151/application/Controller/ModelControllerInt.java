package s25.cs151.application.Controller;

import java.io.IOException;
import java.util.ArrayList;

public interface ModelControllerInt<E> {
    public ArrayList<E> load();
    public void save(E object) throws IOException;
}
