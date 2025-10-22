package ru.reddmix.interfaces;

import ru.reddmix.DualBase.proto.DatabaseProtos;
import java.util.List;

public interface PostgresRepository {
    List<DatabaseProtos.Game> fetchGamesFromPostgres();
}
