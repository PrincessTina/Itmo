import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// Показывает, куда мэпится корень для всех java классов
@ApplicationPath("/*")
public class ApplicationConfig extends Application { }