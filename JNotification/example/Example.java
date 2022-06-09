import notification.JNotification;

public class Example{

    public static  void main(String[] args){

        JNotification n = new JNotification("Warning message","This is a warning message");
        n.send();

    }

}
