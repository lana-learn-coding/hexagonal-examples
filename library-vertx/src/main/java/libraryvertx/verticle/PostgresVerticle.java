package libraryvertx.verticle;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.serviceproxy.ServiceBinder;
import io.vertx.sqlclient.PoolOptions;
import lombok.extern.log4j.Log4j2;

import javax.inject.Singleton;

@Log4j2
@Singleton
public class PostgresVerticle extends AbstractVerticle {

	private PgPool pgClient;

	@Override
	public void start(Promise<Void> promise) {
		try {
			JsonObject configDB = config().getJsonObject("database").getJsonObject("postgres");
			String uri = configDB.getString("uri");
			Validate.notBlank(uri, "Uri database.postgres.uri must be not blank.");

			PgConnectOptions connectOptions = PgConnectOptions.fromUri(uri);
			PoolOptions poolOptions = new PoolOptions().setMaxSize(30);

			pgClient = PgPool.pool(vertx, connectOptions, poolOptions);
			new ServiceBinder(vertx)
					.setAddress(USER_REPOSITORY_ADDRESS)
					.register(UserRepository.class, new UserRepositoryImpl(pgClient));
			promise.complete();
		} catch(Exception ex) {
			log.error("@PostgresVerticle error: ", ex);
			promise.fail(ex);
		}
	}

	@Override
	public void stop() {
		if(pgClient != null) {
			pgClient.close();
		}
	}
}
