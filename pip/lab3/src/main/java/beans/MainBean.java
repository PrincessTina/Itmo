package beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mainBean")
@SessionScoped
public class MainBean {
  private String inputText;

  public String getInputText() {
    return inputText;
  }

  public void setInputText(String inputText) {
    this.inputText = inputText;
  }

  public void showMessage() {
    FacesMessage message = new FacesMessage("Заголовок", "Частичное обновление страницы");
    message.setSeverity(FacesMessage.SEVERITY_INFO); //как выглядит окошко с сообщением
    FacesContext.getCurrentInstance().addMessage(null, message);

    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Всплывашка", "GrowlMessage"));

    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Значение", inputText));
  }
}
