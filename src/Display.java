import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Display extends JPanel {

    public Display() {
        setUI();
    }

    private void setUI() {
        this.setLayout(new BorderLayout());
        final JFrame frame = new JFrame("Interpreter");
        final JFileChooser fileChooser = new JFileChooser();
        final JTextArea textArea = new JTextArea();
        final JPanel content_panel = new JPanel();
        final JPanel button_panel = new JPanel();
        final JPanel enter_panel = new JPanel();

        JMenuBar menu_bar;
        JMenu menu, help_menu, about_menu, logging_menu;

        /**
         * Buttons
         */
        JButton button_home = new JButton("Home");
        JButton button_settings = new JButton("Settings");
        JButton button_pageobject = new JButton("Page Object");
        JButton button_enter = new JButton("Enter");

        /**
         * Text Area
         */
        textArea.setEditable(true);

        /**
         * Panel
         */
        button_panel.add(button_home);
        button_panel.add(button_settings);
        button_panel.add(button_pageobject);

        content_panel.setLayout(new BorderLayout());
        content_panel.add(textArea);

        enter_panel.add(button_enter);

        /**
         * Menu Bar
         */
        // Create the menu bar
        menu_bar = new JMenuBar();

        /**
         * Menu Items
         */
        JMenuItem open_item, new_item, close_item, save_item;
        menu = new JMenu("File");
        open_item = new JMenuItem("Open");
        new_item = new JMenuItem("New");
        close_item = new JMenuItem("Close");
        save_item = new JMenuItem("Save");
        menu.add(open_item);
        menu.add(new_item);
        menu.add(close_item);
        menu.add(save_item);

        help_menu = new JMenu("Help");

        about_menu = new JMenu("About");

        JMenuItem info, warn, error;
        logging_menu = new JMenu("Logging");
        info = new JMenuItem("Info");
        warn = new JMenuItem("Warn");
        error = new JMenuItem("Error");
        logging_menu.add(info);
        logging_menu.add(warn);
        logging_menu.add(error);

        /**
         * Menu Bar
         */
        menu_bar.add(menu);
        menu_bar.add(help_menu);
        menu_bar.add(about_menu);
        menu_bar.add(logging_menu);

        open_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.showOpenDialog(frame);
                File file = fileChooser.getSelectedFile();
            }
        });

        close_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });

        button_enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String test = textArea.getText();
                    Parser p = new Parser(test);
                    Feature feature = p.parse();
                    feature.execute();
                    Memory.displayMemory();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LexicalException e) {
                    e.printStackTrace();
                } catch (ParserException e) {
                    e.printStackTrace();
                }
            }
        });

        save_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.setDialogTitle("File to save.");
                int userSelection = fileChooser.showSaveDialog(frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file_save = fileChooser.getSelectedFile();
                        FileWriter fileWriter = new FileWriter(file_save);
                        fileWriter.write(textArea.getText());
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

//                    fileChooser.showOpenDialog(frame);
//                    FileWriter fileWriter = new FilWriter(fileChooser.getSelectedFile() + ".aw");
//                    FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/test_data/test_file.txt");
//                    fileWriter.write(textArea.getText());
            }
        });

        /**
         * Frame
         */
        frame.setBackground(new Color(149, 165, 166));
        frame.setJMenuBar(menu_bar);
        frame.add(button_panel, BorderLayout.NORTH);
        frame.add(content_panel, BorderLayout.CENTER);
        frame.add(enter_panel, BorderLayout.SOUTH);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
