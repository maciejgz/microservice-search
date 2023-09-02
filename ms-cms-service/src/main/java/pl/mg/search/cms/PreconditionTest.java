package pl.mg.search.cms;

import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.exception.CustomPreconditionErrorException;
import liquibase.exception.CustomPreconditionFailedException;
import liquibase.precondition.CustomPrecondition;
import org.springframework.stereotype.Component;


@Component
public class PreconditionTest implements CustomPrecondition {

    @Override
    public void check(Database database) throws CustomPreconditionFailedException, CustomPreconditionErrorException {

        DatabaseConnection connection = database.getConnection();
        System.out.println(connection.getURL());
//        throw new CustomPreconditionFailedException("test");
    }
}
