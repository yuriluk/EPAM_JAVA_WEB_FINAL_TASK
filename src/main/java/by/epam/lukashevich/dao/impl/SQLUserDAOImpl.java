package by.epam.lukashevich.dao.impl;

import by.epam.lukashevich.dao.UserDAO;
import by.epam.lukashevich.dao.exception.DAOException;
import by.epam.lukashevich.dao.pool.connection.ProxyConnection;
import by.epam.lukashevich.dao.pool.impl.DatabaseConnectionPool;
import by.epam.lukashevich.domain.entity.user.User;
import by.epam.lukashevich.domain.entity.user.Role;
import by.epam.lukashevich.domain.util.UserBuilder;
import by.epam.lukashevich.domain.util.impl.UserBuilderImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAOImpl implements UserDAO {

    private static final String GET_USER_BY_ID = "SELECT " +
            "u.id," +
            "u.name," +
            "u.email," +
            "u.login," +
            "u.password," +
            "u.roleId," +
            "u.banned" +
            " FROM users u" +
            " WHERE id=?";

    private static final String GET_ALL_USERS = "SELECT " +
            "u.id," +
            "u.name," +
            "u.email," +
            "u.login," +
            "u.password," +
            "u.roleId," +
            "u.banned" +
            " FROM users u" +
            " INNER JOIN roles r" +
            " ON u.roleId = r.id";

    private static final String ADD_NEW_USER =
            "INSERT INTO users " +
                    "(name, email, login, password, roleId) " +
                    "VALUES(?,?,?,?,?)";

    private static final String GET_USER_BY_LOGIN =
            "SELECT id FROM users where login=?";


    private static final String GET_USER_BY_LOGIN_PASS = "SELECT " +
            "u.id," +
            "u.name," +
            "u.email," +
            "u.login," +
            "u.password," +
            "u.roleId," +
            "u.banned" +
            " FROM users u" +
            " WHERE" +
            " login=?" +
            " and password=?";


    private static final String UPDATE_USER_STATUS = "UPDATE users SET" +
            " roleId = ?" +
            " WHERE id = ?";

    private static final String UPDATE_USER_BAN_STATUS = "UPDATE users SET" +
            " banned = ?" +
            " WHERE id = ?";

    private static final String UPDATE_USER = "UPDATE users SET " +
            "name=?, " +
            "email =?, " +
            "login=?, " +
            "password=?, " +
            "roleId=?, " +
            "banned=?" +
            " WHERE id = ?";

    private final DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    @Override
    public List<User> findAll() throws DAOException {

        List<User> users = new ArrayList<>();
        try (ProxyConnection proxyConnection = pool.getConnection();
             Connection con = proxyConnection.getConnectionWrapper();
             PreparedStatement st = con.prepareStatement(GET_ALL_USERS)) {

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                User user = getUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("SQL Exception can't create list of users in findAll()", e);
        }
        return users;
    }

    @Override
    public User findById(int id) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection();
             Connection con = proxyConnection.getConnectionWrapper();
             PreparedStatement st = con.prepareStatement(GET_USER_BY_ID)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return getUser(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL Exception can't find user in findById()", e);
        }
        throw new DAOException("No user with ID=" + id + ".");
    }

    @Override
    public User authorization(String login, String password) throws DAOException {
        User user = null;
        try (ProxyConnection proxyConnection = pool.getConnection();
             Connection con = proxyConnection.getConnectionWrapper();
             PreparedStatement st = con.prepareStatement(GET_USER_BY_LOGIN_PASS)) {

            st.setString(1, login);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("SQL Exception during registration()", e);
        }
        return user;
    }

    @Override
    public void registration(String login, String password, String email, String name, Role role) throws DAOException {

        try (ProxyConnection proxyConnection = pool.getConnection();
             Connection con = proxyConnection.getConnectionWrapper();
             PreparedStatement st = con.prepareStatement(ADD_NEW_USER)) {

            if (isLoginUsed(con, login)) {
                throw new DAOException("Login is already is use!");
            }
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, login);
            st.setString(4, password);
            st.setInt(5, role.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL Exception during registration()", e);
        }
    }

    @Override
    public boolean updateUserPassword(String login, String newPassword) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return false;
    }

    @Override
    public boolean update(User user) throws DAOException {
        try (ProxyConnection proxyConnection = pool.getConnection();
             Connection con = proxyConnection.getConnectionWrapper();
             PreparedStatement st = con.prepareStatement(UPDATE_USER)) {

            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getLogin());
            st.setString(4, user.getPassword());
            st.setInt(5, user.getRole().getId());
            st.setBoolean(6, user.getBanned());
            st.setInt(7, user.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException("SQL Exception during update()", e);
        }
    }

    @Override
    public void updateBanStatus(int id) throws DAOException {
        final User user = findById(id);
        final boolean isBanned = !user.getBanned();

        try (ProxyConnection proxyConnection = pool.getConnection();
             Connection con = proxyConnection.getConnectionWrapper();
             PreparedStatement st = con.prepareStatement(UPDATE_USER_BAN_STATUS)) {

            st.setBoolean(1, isBanned);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL Exception during update Ban status()", e);
        }
    }

    @Override
    public void updateStatus(int id) throws DAOException {
        final User user = findById(id);
        int roleId = user.getRole().getId();
        if (roleId == 2){
            roleId = 3;
        }
        else {
            roleId =2;
        }

        try (ProxyConnection proxyConnection = pool.getConnection();
             Connection con = proxyConnection.getConnectionWrapper();
             PreparedStatement st = con.prepareStatement(UPDATE_USER_STATUS)) {

            st.setInt(1, roleId);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL Exception during update status()", e);
        }
    }

    private boolean isLoginUsed(Connection connection, String login) throws SQLException {
        try (PreparedStatement st = connection.prepareStatement(GET_USER_BY_LOGIN)) {
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            return rs.first();
        }
    }

    private User getUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String password = rs.getString("password");
        int roleId = rs.getInt("roleId");
        boolean isBanned = rs.getBoolean("banned");

        Role role = Role.fromValue(roleId);

        UserBuilder userBuilder = new UserBuilderImpl(id);
        return userBuilder.withName(name)
                .withEmail(email)
                .withLogin(login)
                .withPassword(password)
                .withRole(role)
                .withIsBanned(isBanned)
                .build();
    }
}