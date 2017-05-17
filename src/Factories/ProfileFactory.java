package Factories;

import Beans.Account;
import Beans.Human;
import Beans.Profile;
import Beans.Role;
import Types.RoleScope;

import java.sql.Connection;
import java.util.List;

/**
 * Created by andre on 02.05.2017.
 */
public class ProfileFactory extends CommonFactory {
    public static Profile select(Connection conn, Account account, RoleScope roleScope) throws Exception {
        Human human = HumanFactory.select(conn, account.getHumanID());
        if (human != null){
            Profile profile = new Profile();
            profile.setHuman(human);
            profile.setAccount(account);
            profile.setContacts(ContactFactory.query(conn, account.getHumanID()));
            profile.setDocuments(DocumentFactory.query(conn, account.getHumanID()));
            profile.setLocations(LocationFactory.query(conn, account.getHumanID()));
            //Get human roles
            profile.setRoles(RoleFactory.query(conn, account.getHumanID(), RoleScope.Unknown, true));
            //Get selected role
            List<Role> roles = RoleFactory.query(conn, account.getHumanID(), roleScope, true);
            if (roles.size() > 0)
                profile.setRole(roles.stream().findFirst().get());
            return profile;
        }
        return null;
    }
}
