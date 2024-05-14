package screens;

import javax.swing.*;

import constants.CommonConstants;
import database.Category;
import database.JDBC;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TitleScreenGui extends JFrame {

    // constants
    private JComboBox categoriesMenu;
    private JTextField numOfQuestionsTextField;

    public TitleScreenGui() {
        // call the constructor of the supercall with the title of "title screen"
        super("Title Screen");

        // set size
        setSize(400, 565);

        // set layout
        setLayout(null);

        // set the frame to the center
        setLocationRelativeTo(null);

        // disable resizing
        setResizable(false);

        // set the default close operation
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // change the backgroud color
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        addGuiComponents();

    }

    private void addGuiComponents() {
        JLabel titleLabel = new JLabel("Quiz Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBounds(0, 20, 390, 43);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(titleLabel);

        JLabel chooseCategoryLabel = new JLabel("Choose a category");
        chooseCategoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chooseCategoryLabel.setBounds(0, 90, 400, 43);
        chooseCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseCategoryLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(chooseCategoryLabel);

        // category DropDown Menu

        // temporary cateogary list
        ArrayList<String> categoryList = JDBC.getCategories();

        categoriesMenu = new JComboBox<>(categoryList.toArray());
        categoriesMenu.setBounds(20, 120, 337, 45);
        categoriesMenu.setForeground(CommonConstants.DARK_BLUE);
        add(categoriesMenu);

        // number of question label
        JLabel numOfQuestionsLabel = new JLabel("Number of questions: ");
        numOfQuestionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsLabel.setBounds(20, 190, 172, 20);
        numOfQuestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numOfQuestionsLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(numOfQuestionsLabel);

        // number of questions input
        numOfQuestionsTextField = new JTextField("10");
        numOfQuestionsTextField.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsTextField.setBounds(200, 190, 148, 26);
        numOfQuestionsTextField.setHorizontalAlignment(SwingConstants.CENTER);
        numOfQuestionsTextField.setForeground(CommonConstants.DARK_BLUE);
        add(numOfQuestionsTextField);

        // start button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBounds(65, 290, 262, 45);
        startButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        startButton.setForeground(CommonConstants.LIGHT_BLUE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    // retrieve category

                    Category category = JDBC.getCategory(categoriesMenu.getSelectedItem().toString());

                    // invalid category
                    if (category == null)
                        return;

                    int numOfQuestions = Integer.parseInt(numOfQuestionsTextField.getText());

             
                    // load quiz screen
                    QuizScreenGui quizScreenGui = new QuizScreenGui(category, numOfQuestions);
                    quizScreenGui.setLocationRelativeTo(TitleScreenGui.this);
                    

                    // dispose title screen
                    TitleScreenGui.this.dispose();
                    
                    //display quiz screen
                    quizScreenGui.setVisible(true);
                }

            }
        });
        add(startButton);

        // exit
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBounds(65, 350, 262, 45);
        exitButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        exitButton.setForeground(CommonConstants.LIGHT_BLUE);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // dispose title screen
                TitleScreenGui.this.dispose();

            }
        });
        add(exitButton);

        // create a question button
        JButton creatAQuestionButton = new JButton("Create a question");
        creatAQuestionButton.setFont(new Font("Arial", Font.BOLD, 16));
        creatAQuestionButton.setBounds(65, 420, 262, 45);
        creatAQuestionButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        creatAQuestionButton.setForeground(CommonConstants.LIGHT_BLUE);
        creatAQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create questions screen gui
                CreateQuestionScreenGui createQuestionScreenGui = new CreateQuestionScreenGui();
                createQuestionScreenGui.setLocationRelativeTo(TitleScreenGui.this);

                // dispose of htis title screen
                TitleScreenGui.this.dispose();

                // display create a question screen gui
                createQuestionScreenGui.setVisible(true);

            }
        });
        add(creatAQuestionButton);

    }

    // true - valid input
    // false - invalid input
    private boolean validateInput() {
        // num of questions field must no be empty
        if (numOfQuestionsTextField.getText().replaceAll(" ", "").length() <= 0)
            return false;

        // no category is chose
        if (categoriesMenu.getSelectedItem() == null)
            return false;

        return true;

    }

}
