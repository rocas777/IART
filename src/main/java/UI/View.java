package UI;

import board.StateManager;
import board.Square;
import board.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class View {

    private JFrame mainFrame;

    public View(int width, int height, StateManager stateManager, State currentState){
        //layout.setHgap(10);
        //layout.setVgap(10);


        mainFrame = new JFrame("Aquarium");
        mainFrame.setSize(width+200,height+5);
        Dimension d = new Dimension();
        d.setSize(width + 200,height + 5);
        mainFrame.getContentPane().setPreferredSize(d);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(new Rects(mainFrame, stateManager,currentState,width + 10,height + 10,Color.white));
        mainFrame.setVisible(true);

    }
}

class Rects extends JPanel {
    State currentState;
    StateManager stateManager;
    Frame mainFrame;
    int w,h;
    Color color;


    public Rects(JFrame frame, StateManager stateManager, State currentState, int w, int h, Color color) {
        super();
        mainFrame = frame;
        this.currentState=currentState;
        this.stateManager = stateManager;
        this.w = w;
        this.h = h;
        this.color = color;

        setBackground(Color.darkGray);
        this.setFocusable(true);
        this.requestFocus();
        this.addMouseListener(new MouseClick());

        setLayout(null);

        JButton easy = new JButton("EASY");
        easy.setBounds(w+15,40,150,50);
        easy.addActionListener(new LevelChanger(0));
        add(easy);

        JButton medium = new JButton("MEDIUM");
        medium.setBounds(w+15,120,150,50);
        medium.addActionListener(new LevelChanger(1));
        add(medium);

        JButton hard = new JButton("HARD");
        hard.setBounds(w+15,200,150,50);
        hard.addActionListener(new LevelChanger(2));
        add(hard);

        JButton custom = new JButton("CUSTOM");
        custom.setBounds(w+15,200,150,50);
        custom.addActionListener(new LevelChanger(3));
        add(custom);

        JButton hint = new JButton("HINT");
        hint.setBounds(w+15,280,150,50);
        hint.addActionListener(new Helper());
        add(hint);

        JButton reset = new JButton("RESET");
        reset.setBounds(w+15,360,150,50);
        reset.addActionListener(new Resetter());
        add(reset);

    }



    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //Setup background

        Color borderColor = Color.BLACK;

        int amountH = (int) ((h-10) / (currentState.getMatrix().size()+(currentState.getMatrix().get(0).size()-1)*0.1));
        int amountW = (int) ((w-10) / (currentState.getMatrix().get(0).size()+(currentState.getMatrix().get(0).size()-1)*0.1));
        paintMainSquares(g,amountW,amountH);
        paintRightSquares(g,(int) (amountW),(int) (amountH),borderColor);
        paintDownSquares(g,(int) (amountW),(int) (amountH),borderColor);
        paintBorders(g,(int) (amountW),(int) (amountH),borderColor);

        paintNumbers(g,(int) (amountW),(int) (amountH));

    }

    private  void paintNumbers(Graphics g,int w, int h){
        g.setFont(new Font("default", Font.BOLD, 16));
        //System.out.println(board.getHorizontalCount());
        for(int i = 0; i< stateManager.getHorizontalCount().size(); i++){
            g.drawString(stateManager.getHorizontalCount().get(i).toString(),i*(w+6) + 30, 18);
        }
        for(int i = 0; i< stateManager.getVerticalCount().size(); i++){
            g.drawString(stateManager.getVerticalCount().get(i).toString(), 10 , i*(h+6) + 40);
        }
    }

    private  void paintMainSquares(Graphics g,int w,int h){
        int x = 0;
        int y = 0;
        for (List<Square> lines: currentState.getMatrix()) {
            x=0;
            for (Square s: lines) {
                int l_x = (int) (w*x*1.1+5);
                int l_y = (int) (h*y*1.1+5);
                if(!s.isPainted())
                    drawRectangle(g, color,l_x-2.5, l_y-2.5, w+5, h+5);
                else
                    drawRectangle(g, Color.cyan,l_x-2.5, l_y-2.5, w+5, h+5);
                x++;
            }
            y++;
        }
    }

    private void paintBorders(Graphics g,int w,int h, Color border){
        drawRectangle(g,border,0,0,currentState.getMatrix().get(0).size()*(w+6),h * 0.08);
        drawRectangle(g,border,0,currentState.getMatrix().size() * (h+6),currentState.getMatrix().get(0).size()*(w+6) ,h * 0.08);

        drawRectangle(g,border,0,0,w * 0.08, currentState.getMatrix().size()*(h+6));
        drawRectangle(g,border,currentState.getMatrix().get(0).size() * (w+6),0,w * 0.08, currentState.getMatrix().size()*(h+6)+5);
    }

    private  void paintRightSquares(Graphics g,int w,int h,Color border){
        for (int y = 0; y < currentState.getMatrix().size(); y++) {
            for (int x = 0; x < currentState.getMatrix().get(0).size() -1 ; x++) {
                int l_x = (int) (w+w*x*1.1+5+ w * 0.02);
                int l_y = (int) (h*y*1.1);
                if(currentState.getMatrix().get(y).get(x).getAquariumIdentifier() != currentState.getMatrix().get(y).get(x+1).getAquariumIdentifier()) {
                    drawRectangle(g, border, l_x, l_y, w * 0.08, h + 10);
                }
            }
        }
    }

    private  void paintDownSquares(Graphics g,int w,int h,Color border) {
        for (int y = 0; y < currentState.getMatrix().size() - 1; y++) {
            for (int x = 0; x < currentState.getMatrix().get(0).size(); x++) {
                int l_x = (int) (w * x * 1.1);
                int l_y = (int) (h + h * y * 1.1 + 5 + h * 0.02);
                if (currentState.getMatrix().get(y).get(x).getAquariumIdentifier() != currentState.getMatrix().get(y + 1).get(x).getAquariumIdentifier()) {
                    drawRectangle(g, border, l_x , l_y, w + 10, h * 0.08);
                }
            }
        }
    }


    private void drawRectangle(Graphics g, Color color, double x, double y, double width, double height) //centers rectangle
    {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
        RenderingHints render = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        render.put(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.addRenderingHints(render);
        g2.setColor(color);
        g2.fill(rect);
    }


    private class MouseClick extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x=e.getX();
            int y=e.getY();

            if(x<=w && y<=h){
                stateManager.actOnClick(x,y);
                revalidate();
                repaint();
            }

        }
    }

    private class Resetter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.toString());
        }
    }

    private class LevelChanger implements ActionListener {
        int level;

        public LevelChanger(int level) {
            this.level=level;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.toString());
        }
    }

    private class Helper implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.toString());
        }
    }


}

