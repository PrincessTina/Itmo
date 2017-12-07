package beans;

import org.primefaces.extensions.component.analogclock.model.AnalogClockColorModel;
import org.primefaces.extensions.component.analogclock.model.DefaultAnalogClockColorModel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.awt.*;

@ManagedBean(name = "colorBean")
@RequestScoped
public class ColorBean {
  private AnalogClockColorModel theme = new DefaultAnalogClockColorModel();

  public AnalogClockColorModel getTheme() {
    theme.setBorder(Color.WHITE);
    theme.setFace(Color.BLACK);

    theme.setHourHand(Color.decode("#4d1f80"));
    theme.setMinuteHand(Color.decode("#4d1f80"));
    theme.setSecondHand(Color.decode("#4d1f80"));

    theme.setHourSigns(Color.WHITE);
    theme.setPin(Color.WHITE);


    return theme;
  }
}
