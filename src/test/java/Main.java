
import com.stason.testing.model.entity.Answer;
import com.stason.testing.model.entity.Question;
import com.stason.testing.model.entity.Test;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinetesting","root","root");
            connection=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/onlinetesting",
                    "root" ,
                    "root" );
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM onlinetesting.users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getString("login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
