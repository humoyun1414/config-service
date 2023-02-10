package uz.humoyun.configservice.mapper;

import org.springframework.jdbc.core.RowMapper;
import uz.humoyun.configservice.model.PropertyItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyRowMapper implements RowMapper<PropertyItem> {
    @Override
    public PropertyItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PropertyItem(rs.getLong("id"), rs.getString("key"),
                rs.getString("value"));
    }
}
