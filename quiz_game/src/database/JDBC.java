package database;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;


public class JDBC {
    //MySQL Configurations
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/quiz_gui_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "y.HpcGC37bmyi5j";

    public static boolean saveQuestionCategoryAndAnswerToDatabase(String question, String category, 
    String[] answers, int correctIndex){
        try{
            //establish a database connection
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USERNAME, DB_PASSWORD
            );

            //insert category if it's new, otherwise retrieve it from the database
            Category categoryObj = getCategory(category);
            if(categoryObj == null){
                //insert new category to database   
                categoryObj = insertCategory(category);

            }

            //insert question to database
            Question questionObj = insertQuestion(categoryObj, question);

            //insert answer
            insertAnswers(questionObj, answers, correctIndex);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Database Connection did not work.");
        }

        return true;
    }


    //question methods
    //get questions
    public static ArrayList<Question> getQuestions(Category category){
        ArrayList<Question> questions = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USERNAME, DB_PASSWORD
            );
            //query that retrieves all the questions of a category in random order
            PreparedStatement getQuestionsQuery = connection.prepareStatement(
                "SELECT * FROM QUESTION JOIN CATEGORY " + 
                "ON QUESTION.CATEGORY_ID = CATEGORY.CATEGORY_ID " + 
                "WHERE CATEGORY.CATEGORY_NAME = ? ORDER BY RAND()"
            );
            
            getQuestionsQuery.setString(1, category.getCategoryName());
            
            ResultSet resultSet = getQuestionsQuery.executeQuery();
            while(resultSet.next()){
                int questionId = resultSet.getInt("question_id");
                int categoryId = resultSet.getInt("category_id");
                String question = resultSet.getString("question_text");
                questions.add(new Question(questionId, categoryId, question));
            }
         return questions;

      

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Couldn't insert an answer. database/JDBC/getQuestions");
        }
        return null;

    }

    //insert question
    private static Question insertQuestion(Category category, String questionText){
        try {
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USERNAME, DB_PASSWORD
            );
            PreparedStatement insertQuestionQuery = connection.prepareStatement(
                "INSERT INTO QUESTION(CATEGORY_ID, QUESTION_TEXT) " + 
                "VALUES(?, ?)", 
                Statement.RETURN_GENERATED_KEYS
            );
            insertQuestionQuery.setInt(1, category.getCategoryId());
            insertQuestionQuery.setString(2, questionText);
            insertQuestionQuery.executeUpdate();

            //check for the question id
            ResultSet resultSet = insertQuestionQuery.getGeneratedKeys();
            if(resultSet.next()){
                int questionId = resultSet.getInt(1);
                return new Question(questionId, category.getCategoryId(), questionText);

            }

      

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Couldn't insert a question. database/JDBC/insertQuestion");
        }
        //returns null if it could not find the question in the database
        return null;
    }


    //catgory methods
    //get category 
    public static Category getCategory(String category){
        try {
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USERNAME, DB_PASSWORD
            );
            PreparedStatement getCategoryQuery = connection.prepareStatement(
                "SELECT * FROM CATEGORY WHERE CATEGORY_NAME = ?"
            );
            getCategoryQuery.setString(1, category);

            //execute query and store results
            ResultSet resultSet = getCategoryQuery.executeQuery();
            if(resultSet.next()){
                int categoryId = resultSet.getInt("category_id");
                return new Category(categoryId, category);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Couldn't get a category. database/JDBC/getCategory");
        }
        //returns null if it could not find the category in the database
        return null;
    }


    //get all categories for drop down list
    public static ArrayList<String> getCategories(){
        ArrayList<String> categoryList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USERNAME, DB_PASSWORD
            );
            Statement getCategoriesQuery = connection.createStatement();
            ResultSet resultSet = getCategoriesQuery.executeQuery("SELECT * FROM CATEGORY");

            while(resultSet.next()){
                String categoryName = resultSet.getString("category_name");
                categoryList.add(categoryName);

            }

            return categoryList;


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Couldn't insert a category. database/JDBC/getCategories");
        }
        //returns null if it could not find the category in the database
        return null;








    }

    //insert category
    private static Category insertCategory(String category){
        try {
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USERNAME, DB_PASSWORD
            );
            PreparedStatement insertQuestionQuery = connection.prepareStatement(
                "INSERT INTO CATEGORY(CATEGORY_NAME) " + "VALUES(?)", 
                Statement.RETURN_GENERATED_KEYS
            );
            insertQuestionQuery.setString(1, category);
            insertQuestionQuery.executeUpdate();

            //get the category id that gets automaticall incremented for each new insert in the category table
            ResultSet resultSet = insertQuestionQuery.getGeneratedKeys();
            if(resultSet.next()){
                int categoryId = resultSet.getInt(1);
                return new Category(categoryId, category);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Couldn't insert a category. database/JDBC/insertCategory");
        }
        //returns null if it could not find the category in the database
        return null;
    }


    //answer methods
   

    public static ArrayList<Answer> getAnswers(Question question){
        ArrayList<Answer> answers = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USERNAME, DB_PASSWORD
            );
            //query that retrieves all the questions of a category in random order
            PreparedStatement getAnswersQuery = connection.prepareStatement(
                "SELECT * FROM QUESTION JOIN ANSWER " + 
                "ON QUESTION.QUESTION_ID =  ANSWER.QUESTION_ID " + 
                "WHERE QUESTION.QUESTION_ID = ? ORDER BY RAND()"
            );
            getAnswersQuery.setInt(1, question.getQuestionId());

            ResultSet resultSet = getAnswersQuery.executeQuery();
            while(resultSet.next()){
                int answerId = resultSet.getInt("answer_id");
                String answerText = resultSet.getString("answer_text");
                boolean isCorrect = resultSet.getBoolean("is_correct");
                Answer answer = new Answer(answerId, question.getQuestionId(), answerText, isCorrect);
                answers.add(answer);
            }
            
         return answers;

      

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Couldn't insert an answer. database/JDBC/getAnswers");
        }
        return null;
    }

    //true - able to insert an answer
    //false - unable to insert an answer
    private static boolean insertAnswers(Question question, 
    String[] answers, int correctIndex){
        try {
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USERNAME, DB_PASSWORD
            );
            PreparedStatement insertAnswerQuery = connection.prepareStatement(
                "INSERT INTO ANSWER(QUESTION_ID, ANSWER_TEXT, IS_CORRECT)" + 
                "VALUE(?, ?, ?)"
            );
            
            insertAnswerQuery.setInt(1, question.getQuestionId());

            for(int i = 0; i < answers.length; i++){
                insertAnswerQuery.setString(2, answers[i]);

                if(i == correctIndex){
                    insertAnswerQuery.setBoolean(3, true);
                }else{
                    insertAnswerQuery.setBoolean(3, false);
                }

                insertAnswerQuery.executeUpdate();

            }

         return true;

      

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Couldn't insert an answer. database/JDBC/insertAnswer");
        }
        return false;

    }
}




















