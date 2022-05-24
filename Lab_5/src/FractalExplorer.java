import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.Rectangle2D;

/**
 * Виджет для просмотра различных областей различных фракталов
 */
public class FractalExplorer {
    private int sizeDisp;
    private JImageDisplay image;
    private FractalGenerator FGen;
    private Rectangle2D.Double range;
    private JComboBox comboBox;
    /**
     * Конструктор класса
     */
    private FractalExplorer (int sizeDisp){
        this.sizeDisp=sizeDisp;
        this.FGen = new Mandelbrot();
        this.range = new Rectangle2D.Double(0, 0, 0, 0);
        FGen.getInitialRange(this.range);
    }
    /**
     * Генерирует и отображает интерфейс
     */
    public void createAndShowGUI(){
        JFrame frame = new JFrame("Fractal Explorer");

        JPanel northPanel = new JPanel();
        JLabel label = new JLabel("Fractal:");
        comboBox = new JComboBox();
        comboBox.addItem(new Mandelbrot());
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());

        image = new JImageDisplay(sizeDisp, sizeDisp);

        JPanel southPanel = new JPanel();
        JButton resetButton = new JButton("Reset");
        JButton saveButton = new JButton("Save Image");

        comboBox.setActionCommand("Choose");
        comboBox.addActionListener(new ActionHandler());
        resetButton.setActionCommand("Reset");
        resetButton.addActionListener(new ActionHandler());
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new ActionHandler());
        image.addMouseListener(new MouseListener());

        northPanel.add(label, java.awt.BorderLayout.CENTER);
        northPanel.add(comboBox, java.awt.BorderLayout.CENTER);

        southPanel.add(resetButton, java.awt.BorderLayout.CENTER);
        southPanel.add(saveButton, java.awt.BorderLayout.CENTER);

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(image, java.awt.BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);
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
     * Обработчик событий
     */
    public class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Choose"))
            {
                FGen = (FractalGenerator) comboBox.getSelectedItem();
                range = new Rectangle2D.Double(0, 0, 0, 0);
                FGen.getInitialRange(range);
                drawFractal();
            }
            else if (e.getActionCommand().equals("Reset"))
            {
                FGen.getInitialRange(range);
                drawFractal();
            }
            else if (e.getActionCommand().equals("Save"))
            {
                JFileChooser сhooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                сhooser.setFileFilter(filter);
                сhooser.setAcceptAllFileFilterUsed(false);
                int t = сhooser.showSaveDialog(image);
                if (t == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(image.getBufferedImage(), "png", сhooser.getSelectedFile());
                    } catch (NullPointerException | IOException e1) {
                        JOptionPane.showMessageDialog(image, e1.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
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
