package libraryvertx;

import io.vertx.core.Launcher;
import libraryvertx.verticle.MainVerticle;

public class LibraryApplication {
    public static void main(String[] args) {
        Launcher.main(new String[]{"run", MainVerticle.class.getName()});
    }
}
