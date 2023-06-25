import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS =(SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY = 100;
    final int x[]= new int [GAME_UNITS];
    final int y[]= new int [GAME_UNITS];
    int bodyparts =6;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.pink);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdaptor());
        startGame();

    }
    public void startGame()
    {
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(running)
        {/*
        for(int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE;i++)
        {
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }*/
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);


        for(int i = 0; i<bodyparts;i++) {
            if (i == 0) {
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(new Color(45, 180, 0)); // multicolor snanke line is below one
                g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
              }
             }
            g.setColor(Color.CYAN);
            g.setFont(new Font("Ink free", Font.BOLD, 45));
            FontMetrics metric = getFontMetrics(g.getFont());
            g.drawString("Score: " +appleEaten,(SCREEN_WIDTH-metric.stringWidth("Score: " +appleEaten))/2,g.getFont().getSize());
         }
        else {
            gameOver(g);
        }
    }
    public void newApple()    //putting random apple method
    {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move()
    {
        for(int i = bodyparts; i>0; i--)
        {
            x[i]= x[i-1];
            y[i] = y[i-1];
        }
        switch (direction)          //moving button setup
        {
            case'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }
    public void checkApple()
    {
        if ((x[0] == appleX) && y[0] == appleY)
        {
            bodyparts++;
            appleEaten++;
            newApple();
        }
    }
    public void checkCollisions()
    {  // check the head collision with the own body
        for(int i = bodyparts;i>0;i--)
        {
            if((x[0]==x[i]) && (y[0]==y[i]))
            {
                running = false;
            }
        }
        //checks if head touches left border
        if(x[0]<0)
        {
            running= false;
        }
        // check headed right side
        if(x[0]> SCREEN_WIDTH)
        {
            running = false;
        }
        //check head touches top border
        if(y[0]<0)
        {
            running=false;
        }
        //if head touches bottom border
        if(y[0]>SCREEN_HEIGHT)
        {
            running=false;
        }
        if(!running)
        {
            timer.stop();
        }
    }
    public void gameOver(Graphics g)
    {
        //score setup
        g.setColor(Color.CYAN);
        g.setFont(new Font("Ink free", Font.BOLD, 45));
        FontMetrics metric = getFontMetrics(g.getFont());
        g.drawString("Score: " +appleEaten,(SCREEN_WIDTH-metric.stringWidth("Score: " +appleEaten))/2,g.getFont().getSize());


        //game over page set up
        g.setColor(Color.CYAN);
        g.setFont(new Font("Ink free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH-metrics.stringWidth("GSME OVER"))/2,SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e)    // actionPerformed code goes here
    {
       if(running)
       {
           move();
           checkApple();
           checkCollisions();
       }
       repaint();
    }
    public class MyKeyAdaptor extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
