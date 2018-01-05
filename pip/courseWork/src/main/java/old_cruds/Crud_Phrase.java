//package cruds;
//
//import Event;
//import Phrase;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.persistence.EntityManager;
//import java.util.ArrayList;
//
//@RequestScoped
//@ManagedBean(name = "phraseBean")
//public class Crud_Phrase extends Crud_Api {
//  /*
//  Тест ManyToOne
//  private int id = 1010;
//  <h:outputText value="#{phraseBean.read().answers.get(0).description}"/>
//  */
//
//  /*
//  Тест OneToOne
//  private int id = 1;
//  <h:outputText value="#{phraseBean.read().event.description}"/>
//  */
//  private int id;
//  private String description;
//  private Event event;
//  private ArrayList<Phrase> answers;
//  private Phrase question;
//
//  public Phrase read() {
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      return entityManager.find(Phrase.class, this.id);
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void create() {
//    Phrase row = new Phrase(this.description);
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      entityManager.getTransaction().begin();
//      entityManager.persist(row);
//      entityManager.getTransaction().commit();
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void delete() {
//    EntityManager entityManager = generateEntityManager();
//
//    try {
//      entityManager.getTransaction().begin();
//      entityManager.remove(read());
//      entityManager.getTransaction().commit();
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public void update() {
//    EntityManager entityManager = generateEntityManager();
//    Phrase row = read();
//
//    try {
//      entityManager.getTransaction().begin();
//      row.setDescription(this.description);
//      entityManager.getTransaction().commit();
//    } finally {
//      entityManager.close();
//    }
//  }
//
//  public int getId() {
//    return id;
//  }
//
//  public void setId(int id) {
//    this.id = id;
//  }
//
//  public String getDescription() {
//    return description;
//  }
//
//  public void setDescription(String description) {
//    this.description = description;
//  }
//
//  public Event getEvent() {
//    return event;
//  }
//
//  public void setEvent(Event event) {
//    this.event = event;
//  }
//
//  public ArrayList<Phrase> getAnswers() {
//    return answers;
//  }
//
//  public void setAnswers(ArrayList<Phrase> answers) {
//    this.answers = answers;
//  }
//
//  public Phrase getQuestion() {
//    return question;
//  }
//
//  public void setQuestion(Phrase question) {
//    this.question = question;
//  }
//}
