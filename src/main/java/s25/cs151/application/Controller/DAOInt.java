package s25.cs151.application.Controller;

import java.io.IOException;
import java.util.ArrayList;

public interface DAOInt<E> {
    public ArrayList<E> load();
    public boolean save(E object) throws IOException;
}
