package uz.humoyun.configservice.repository;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.humoyun.configservice.mapper.PropertyRowMapper;
import uz.humoyun.configservice.model.PropertyDto;
import uz.humoyun.configservice.model.PropertyItem;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PropertyRepository {
    private final JdbcTemplate template;

    public PropertyRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<PropertyItem> findByApplication(String application) {
        return template.query(
                "select * from property where application = ?",
                new PropertyRowMapper(),
                new Object[]{application});
    }

    public void update(String application, PropertyDto property) {
        this.template.batchUpdate(
                "update property set key = ? , value = ?, where id = ? and application = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, property.items().get(i).key());
                        ps.setString(2, property.items().get(i).value());
                        ps.setLong(3, property.items().get(i).id());
                        ps.setString(4, application);
                    }

                    @Override
                    public int getBatchSize() {
                        return property.items().size();
                    }
                }
        );
    }


}
