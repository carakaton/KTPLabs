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
    private int rowsRemaning;
    JButton resetButton;
    JButton saveButton;
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
        resetButton = new JButton("Reset");
        saveButton = new JButton("Save Image");

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
    private void drawFractal() {
        enableUI(false);
        rowsRemaning = sizeDisp;
        for (int i = 0; i < sizeDisp; i++)
        {
            FractalWorker rowDrawer = new FractalWorker(i);
            rowDrawer.execute();
        }
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
            if (rowsRemaning==0) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, sizeDisp, e.getX());
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, e.getY());
                FGen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
                drawFractal();
            }
        }
    }
    /**
     * Класс для организации фоновых потоков
     */
    private class FractalWorker extends SwingWorker<Object, Object> {
        private int j;
        public FractalWorker(int j) {
            this.j = j;
        }
        public Object doInBackground() {
            for (int i = 0; i < sizeDisp; i++) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, sizeDisp, i);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, sizeDisp, j);
                int count = FGen.numIterations(xCoord,yCoord);
                if (count == -1)
                    image.drawPixel(i, j, 0);
                else {
                    double hue = 0.7f + (float) count / 200f;
                    int rgbColor = Color.HSBtoRGB((float) hue, 1f, 1f);
                    image.drawPixel(i, j, rgbColor);
                }
            }
            image.repaint(0, 0, j, sizeDisp, 1);
            return null;
        }
        public void done() {
            rowsRemaning--;
            if (rowsRemaning==0)
                enableUI(true);
        }
    }
    /**
     * Включает/выключает интерфейс
     */
    public void enableUI(boolean var) {
        saveButton.setEnabled(var);
        resetButton.setEnabled(var);
        comboBox.setEnabled(var);
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
