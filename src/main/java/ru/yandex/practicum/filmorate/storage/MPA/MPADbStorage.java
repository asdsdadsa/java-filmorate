package ru.yandex.practicum.filmorate.storage.MPA;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class MPADbStorage implements MPAStorage {

    private final JdbcTemplate jdbcTemplate;

    public MPADbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<MPA> getMPA() {
        final String sqlQuery = "SELECT * FROM MPA";

        final List<MPA> MPAs = jdbcTemplate.query(sqlQuery, this::mapRowToMPA);

        return MPAs;
    }

    @Override
    public MPA MPAById(Integer id) {
        final String sqlQuery = "SELECT * FROM MPA WHERE MPA_id=?";

        final List<MPA> MPAs = jdbcTemplate.query(sqlQuery, this::mapRowToMPA, id);
        if (MPAs.size() < 1) {
            throw new NotFoundException("Ошибка поиска MPA с id " + id);
        }

        return MPAs.get(0);
    }

    public MPA mapRowToMPA(ResultSet resultSet, int rowNum) throws SQLException {
        return MPA.builder()
                .id(resultSet.getInt("MPA_id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
