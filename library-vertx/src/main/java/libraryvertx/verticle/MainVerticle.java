package libraryvertx.verticle;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainVerticle extends AbstractVerticle {
    public JsonObject getConfigFromFile(Vertx vertx, String configFilePath) {
        log.info("@Setting config: {}", configFilePath);
        Buffer buffer = vertx.fileSystem().readFileBlocking(configFilePath);
        return new JsonObject(buffer);
    }

    @Override
    public void start(Promise<Void> promise) {
        JsonObject config = this.getConfigFromFile(vertx, "./conf/config.json");
        context.config().mergeIn(config);
        vertx.executeBlocking(result -> {
            deployDatabaseVerticle().onSuccess(handler -> {
                deployController();
                result.complete();
            }).onFailure(throwable -> {
                log.error("Deploy fail", throwable);
                promise.fail(throwable);
            });
        }, res -> {
            if(res.succeeded()) {
                log.info("Deploy all verticle success!");
                promise.complete();
                return;
            }
            log.error("@MainVerticle - #start fail: {}", res);
            promise.fail("@MainVerticle - #start fail:" + res.cause().getMessage());
        });
    }

    private CompositeFuture deployDatabaseVerticle() {
        return CompositeFuture.all(
            this.deployVerticle(PostgresVerticle.class, this.createOptions("postgres", 1)),
            this.deployVerticle(CatFactServiceVerticle.class, this.createOptions("catfact-service", 1))
        );
    }

    private void deployController() {
        DeploymentOptions httpOption = this.createOptions("http", 10);
        vertx.deployVerticle(HttpServerVerticle.class, httpOption, result -> {
            if(result.succeeded()) {
                Integer portNumber = config().getInteger("http.port", 8888);
                log.info("HTTP server running: http://127.0.0.1:{} ", portNumber);
                return;
            }
            log.error("Could not start a HTTP server", result.cause());
        });
    }

    private Future<String> deployVerticle(Class<? extends Verticle> verticleClass, DeploymentOptions options) {
        Promise<String> promise = Promise.promise();
        String verticleName = verticleClass.getSimpleName();
        vertx.deployVerticle(verticleClass, options,
            handler -> {
                if(handler.succeeded()) {
                    log.info("@MainVerticle: deploy success {}!", verticleName);
                    promise.complete(verticleName + " deploy success");
                    return;
                }
                log.error("@MainVerticle: deploy fail {}!", verticleName);
                promise.fail(new RuntimeException("@Error deploy: " + verticleName));
            });
        return promise.future();
    }

    private DeploymentOptions createOptions(String field, Integer defaultValue) {
        JsonObject configInstance = config().getJsonObject("instance");
        return new DeploymentOptions()
            .setInstances(configInstance.getInteger(field, defaultValue))
            .setConfig(config());
    }
}
