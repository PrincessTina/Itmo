public class JThread extends Thread {


  public void run(){

    try{
      while(true) {
        System.out.println("дратути");
      }
    }
    catch(Exception e){
      System.out.println("Поток прерван");
    }
    System.out.printf("Поток %s завершил работу... \n", Thread.currentThread().getName());
  }
}
