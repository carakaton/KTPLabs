import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
/**
 * Виджет для просмотра различных областей фрактала
 */
public class FractalExplorer {
    private int sizeDisp;
    private JImageDisplay image;
    private FractalGenerator FGen;
    private Rectangle2D.Double range;
    /**
     * Конструктор класса
     */
    private FractalExplorer (int sizeDisp){
        this.sizeDisp = sizeDisp;
        this.FGen = new Mandelbrot();
        this.range = new Rectangle2D.Double(0, 0, 0, 0);
        FGen.getInitialRange(this.range);
    }
    /**
     * Генерирует и отображает интерфейс
     */
    public void createAndShowGUI(){
        JFrame frame = new JFrame("Fractal Explorer");

        JButton Button = new JButton("Reset");
        image = new JImageDisplay(sizeDisp, sizeDisp);

        Button.addActionListener(new ActionHandler());
        image.addMouseListener(new MouseListener());

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(image, java.awt.BorderLayout.CENTER);
        frame.add(Button, java.awt.BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    /**
     * Выводит фрактал на экран
     */
    private void drawFractal(){
        for (int x = 0; x < sizeDisp; x++) {
            for (int y = 0; y < sizeDisp; y++) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, sizeDisp, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, y);
                int count = FGen.numIterations(xCoord, yCoord);
                int rgbColor;
                if (count == -1)
                    rgbColor = 0;
                else {
                    float hue = 0.7f + (float) count / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                image.drawPixel(x, y, rgbColor);
            }
        }
        image.repaint();
    }
    /**
     * Обработчик событий для кнопки сброса
     */
    public class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FGen.getInitialRange(range);
            drawFractal();
        }
    }
    /**
     * Обработчик событий для мыши
     */
    public class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, sizeDisp, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, e.getY());
            FGen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
    /**
     * Входная точка программы
     */
    public static void main(String[] args) {
        FractalExplorer FExp = new FractalExplorer(800);
        FExp.createAndShowGUI();
        FExp.drawFractal();
    }
}
