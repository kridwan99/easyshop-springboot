package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, address, city, state, zip, phone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, profile.getUserId());
            stmt.setString(2, profile.getFirstName());
            stmt.setString(3, profile.getLastName());
            stmt.setString(4, profile.getAddress());
            stmt.setString(5, profile.getCity());
            stmt.setString(6, profile.getState());
            stmt.setString(7, profile.getZip());
            stmt.setString(8, profile.getPhone());
            stmt.setString(9, profile.getEmail());

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return profile;
    }

    @Override
    public Profile getProfile(int userId)
    {
        String sql = "SELECT * FROM profiles WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    return mapRow(rs);
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Profile updateProfile(int userId, Profile profile)
    {
        String sql = "UPDATE profiles SET first_name=?, last_name=?, address=?, city=?, state=?, zip=?, phone=?, email=? " +
                "WHERE user_id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, profile.getFirstName());
            stmt.setString(2, profile.getLastName());
            stmt.setString(3, profile.getAddress());
            stmt.setString(4, profile.getCity());
            stmt.setString(5, profile.getState());
            stmt.setString(6, profile.getZip());
            stmt.setString(7, profile.getPhone());
            stmt.setString(8, profile.getEmail());
            stmt.setInt(9, userId);

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return profile;
    }

    private Profile mapRow(ResultSet rs) throws SQLException
    {
        Profile profile = new Profile();

        profile.setUserId(rs.getInt("user_id"));
        profile.setFirstName(rs.getString("first_name"));
        profile.setLastName(rs.getString("last_name"));
        profile.setAddress(rs.getString("address"));
        profile.setCity(rs.getString("city"));
        profile.setState(rs.getString("state"));
        profile.setZip(rs.getString("zip"));
        profile.setPhone(rs.getString("phone"));
        profile.setEmail(rs.getString("email"));

        return profile;
    }
}
