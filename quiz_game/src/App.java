import javax.swing.SwingUtilities;

import database.Category;
import screens.CreateQuestionScreenGui;
import screens.QuizScreenGui;
import screens.TitleScreenGui;

public class App {
    public static void main(String[] args) throws Exception {
        //ensures Swing GUI tasks are executed on the event dispatch thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            

            @Override
            public void run(){
                new TitleScreenGui().setVisible(true);
                // new CreateQuestionScreenGui().setVisible(true);\
                // new QuizScreenGui(new Category(1, "Science"), 3).setVisible(true);
            }
        });











    }
}
