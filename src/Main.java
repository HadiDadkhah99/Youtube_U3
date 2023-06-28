import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends JFrame {

    private JPanel background;
    private List<GameOBJ> objs = new ArrayList<>();

    public Main() {

        setBounds(200, 200, 400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);


        background = new JPanel();
        background.setBounds(0, 0, 400, 400);
        background.setBackground(Color.BLACK);
        background.setOpaque(true);
        background.setLayout(null);

        add(background);


        Line line = new Line(50, 300, 300, 10);
        background.add(line);
        objs.add(line);


        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                Snow snow = new Snow(e.getX() - 10, e.getY() - 50, 4);
                background.add(snow);
                objs.add(snow);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //thread
        new Thread(() -> {

            while (true) {

                update();

                //delay
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();


    }

    public void update() {

        try {

            for (GameOBJ s : objs)
                s.updateOBJ();


            for (int i = 0; i < objs.size(); i++) {

                for (int j = 0; j < objs.size(); j++) {

                    GameOBJ a = objs.get(i);
                    GameOBJ b = objs.get(j);

                    if (a == b)
                        continue;


                    if (a.isCollided(b)) {
                        a.stopOBJ();
                        a.goLastLocation();
                    }

                }
            }


        } catch (Exception e) {
            //for arraylist exception
        }

    }


    public static void main(String[] args) {

        new Main();

    }


    public static class Snow extends GameOBJ {

        public int speed;

        //size of snows
        public static final int SIZE = 12;

        public Snow(int x, int y, int speed) {
            super(x, y, SIZE, SIZE);
            this.speed = speed;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillOval(SIZE / 2, SIZE / 2, SIZE / 2, SIZE / 2);

        }

        public void setXY(int x, int y) {
            //!!!
            setPreX(getObjX());
            setPreY(getObjY());

            setObjX(x);
            setObjY(y);
            setLocation(x, y);
            //this is for linux (Graphic card) !!!
            Toolkit.getDefaultToolkit().sync();
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        @Override
        public void updateOBJ() {

            setXY(getObjX(), getObjY() + speed);

        }

        @Override
        public void stopOBJ() {
            super.stopOBJ();
            speed = 0;
        }

        @Override
        public void goLastLocation() {
            super.goLastLocation();
            setXY(getPreX(), getPreY());
        }
    }

    public static class Line extends GameOBJ {
        public Line(int objX, int objY, int WIDTH, int STROKE) {
            super(objX, objY, WIDTH, STROKE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(getObjHeight()));
            g2.drawLine(0, 0, getObjWidth(), 0);

        }
    }

    public static class GameOBJ extends JComponent {

        private int objX, objY, objWidth, objHeight;
        private int preX, preY;

        public GameOBJ(int objX, int objY, int objWidth, int objHeight) {
            this.objX = objX;
            this.objY = objY;
            this.objWidth = objWidth;
            this.objHeight = objHeight;

            setBounds(objX, objY, objWidth, objHeight);
            setLayout(null);
            setOpaque(true);
        }

        public void setObjX(int objX) {
            this.objX = objX;
        }

        public void setObjY(int objY) {
            this.objY = objY;
        }

        public void setObjWidth(int objWidth) {
            this.objWidth = objWidth;
        }

        public void setObjHeight(int objHeight) {
            this.objHeight = objHeight;
        }

        public int getObjX() {
            return objX;
        }

        public int getObjY() {
            return objY;
        }

        public int getObjWidth() {
            return objWidth;
        }

        public int getObjHeight() {
            return objHeight;
        }

        public void setPreX(int preX) {
            this.preX = preX;
        }

        public void setPreY(int preY) {
            this.preY = preY;
        }

        public int getPreX() {
            return preX;
        }

        public int getPreY() {
            return preY;
        }

        public void updateOBJ() {

        }

        public Rectangle getRectangle() {
            return new Rectangle(objX, objY, objWidth, objHeight);
        }

        public boolean isCollided(GameOBJ obj) {

            return getRectangle().intersects(obj.getRectangle());

        }

        public void stopOBJ() {

        }

        public void goLastLocation() {

        }
    }


}
