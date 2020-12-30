package libraryvertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HttpServerVerticle extends AbstractVerticle {
	public HttpServerVerticle() {}

	public HttpServerVerticle(UserRouter userRouter) {
		this.userRouter = userRouter;
	}

	@Override
	public void init(Vertx vertx, Context context) {
		super.init(vertx, context);
		this.userRouter = new UserRouter(vertx);
	}

	@Override
	public void start(Promise<Void> promise) {
		try {
			Integer port = config().getInteger("http.port", 8888);
			HttpServer server = vertx.createHttpServer();
			Router router = Router.router(vertx);

			router.route().handler(LoggerHandler.create());
			router.route().handler(BodyHandler.create());
			router.route("/*").handler(StaticHandler.create());

			router.mountSubRouter("/users", userRouter.getRouter());

			server.requestHandler(router).listen(port);
			promise.complete();
		} catch(Exception ex) {
			log.error(ex);
			promise.fail(ex.getCause());
		}
	}
}
