package notification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JNotification implements Runnable, MouseListener, MouseMotionListener {
    
    private JFrame frame = new JFrame();

    public static final int TOP_LEFT = 1;
    public static final int BOTTOM_LEFT = 2;
    public static final int BOTTOM_RIGHT = 3;
    public static final int TOP_RIGHT = 4;
    public static final int MIDDLE = 0;

    public static final ImageIcon ERROR_MESSAGE = new ImageIcon(resourceLoader("res/error.png"));
    public static final ImageIcon INFORMATION_MESSAGE = new ImageIcon(resourceLoader("res/info.png"));
    public static final ImageIcon WARNING_MESSAGE = new ImageIcon(resourceLoader("res/warn.png"));
    public static final ImageIcon DONE_MESSAGE = new ImageIcon(resourceLoader("res/done.png"));

    private boolean mouseExited = true;
    private float transparency = 0.0f;

    private final Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    private final int screenw = (int) screen.getWidth();
    private final int screenh = (int) screen.getHeight();

    private JLabel t;
    private JLabel b;

    private final String title, body;

    //default
    private int position = BOTTOM_RIGHT;
    private ImageIcon messageType = INFORMATION_MESSAGE;
    private Color backgroundColor = new Color(100, 100, 100);
    private Color borderColor=new Color(40,255,40);
    private Color titleColor = new Color(0, 0, 255);
    private Color bodyColor = new Color(255, 255, 255);
    private Font titleFont = new Font("Arial", Font.BOLD, 40);
    private Font bodyFont = new Font("Arial", Font.PLAIN, 20);
    private long delayTime=1500;
    private int h = 120;
    private int w = 400;
    private int borderThickness=0;

    public JNotification(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public JNotification(String title, String body, int position) {
        this.title = title;
        this.body = body;
        this.position = position;
    }

    public JNotification(String title, String body, ImageIcon messageType) {
        this.title = title;
        this.body = body;
        this.messageType = messageType;
    }

    public JNotification(String title, String body, int position, ImageIcon messageType) {
        this.title = title;
        this.body = body;
        this.position = position;
        this.messageType = messageType;
    }

    public JNotification(String title,String body,long delayTime){
        this.title = title;
        this.body = body;
        this.delayTime=delayTime;
    }
    
    public JNotification(String title,String body,int position,long delayTime){
        this.title = title;
        this.body = body;
        this.position=position;
        this.delayTime=delayTime;
    }
    
    public JNotification(String title,String body,long delayTime,ImageIcon messageType){
        this.title = title;
        this.body = body;
        this.delayTime=delayTime;
        this.messageType = messageType;
    }
    
    public JNotification(String title, String body, int position, long delayTime, ImageIcon messageType){
        this.title = title;
        this.body = body;
        this.position = position;
        this.delayTime=delayTime;
        this.messageType = messageType;
    }
    
    public void send() {

        Thread thread = new Thread(this);
        thread.start();

    }
    
    private static URL resourceLoader(String path){
        URL input = JNotification.class.getResource(path);
        if (input == null) {
            input = JNotification.class.getResource("/" + path);
        }
        return input;
    }
    
    public void setWidth(int width){
        this.w=width;
    }
    
    public void setHeight(int height){
        this.h=height;
    }

    public void setTitleColor(Color color) {
        this.titleColor = color;
    }

    public void setBodyColor(Color color) {
        this.bodyColor = color;
    }
    
    public void setBorderColor(Color borderColor){
        this.borderColor=borderColor;
    }
    
    public void setBorderThickness(int borderThickness){
        this.borderThickness=borderThickness;
    }

    public void setTitleFont(Font font) {
        this.titleFont = font;
    }

    public void setBodyFont(Font font) {
        this.bodyFont = font;
    }
    
    public void setBackgroundColor(Color backgroundColor){
        this.backgroundColor=backgroundColor;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    public void setDelayTime(long delayTime){
        this.delayTime=delayTime;
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == frame) {
            transparency = 0.0f;
        }
        mouseExited = me.getSource() != frame;
    }

    @Override
    public void mouseExited(MouseEvent me) {
        mouseExited = me.getSource() == frame;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (me.getSource() == frame) {
            transparency = 0.0f;
        }
        mouseExited = me.getSource() != frame;
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (me.getSource() == frame && (me.getXOnScreen() >= frame.getX() || me.getYOnScreen() >= frame.getY())) {
            transparency = 0.0f;
        }
        mouseExited = me.getSource() != frame;
    }

    @Override
    public void run() {

        frame.getContentPane().setBackground(backgroundColor);
        frame.setLayout(new BorderLayout());
        frame.setSize(w, h);
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0,0,frame.getWidth(),frame.getHeight(),25,25));
        frame.getRootPane().setBorder(
         BorderFactory.createMatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, borderColor));
        frame.setDefaultCloseOperation(0);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);

        t = new JLabel(this.title, this.messageType, JLabel.LEFT);
        b = new JLabel(this.body);

        t.setFont(titleFont);
        b.setFont(bodyFont);

        t.setForeground(titleColor);
        b.setForeground(bodyColor);

        int x = (int) w / 2;
        int y = (int) h / 2;

        switch (position) {
            case 0:
                frame.setLocation(((int) screenw / 2) - x, ((int) screenh / 2) - y);
                break;
            case 1:
                frame.setLocation(0, 0);
                break;
            case 2:
                frame.setLocation(0, screenh - h);
                break;
            case 4:
                frame.setLocation(screenw - w, 0);
                break;
            default:
                frame.getRootPane().setBorder(
                        BorderFactory.createMatteBorder(borderThickness, borderThickness, borderThickness + 2, borderThickness,
                                borderColor));
                frame.setLocation(screenw - w, screenh - h);

        }

        frame.add(t, BorderLayout.NORTH);
        frame.add(b, BorderLayout.CENTER);

        frame.setVisible(true);
        
        try {
            Thread.sleep(delayTime);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //loop
        while (transparency <= 1.0f) {
            frame.setOpacity(1.0f - transparency);
            if (mouseExited) {
                transparency += 0.01f;                
            }
            try {
                Thread.sleep(35);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        frame.dispose();
    }
}
