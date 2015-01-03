package com.pakius.helper;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Pakius
 */
public class MongoEmbed {
    private static volatile boolean running = false;
    private static MongodExecutable mongodExe;
    private static MongodProcess mongod;
    private static MongoClient mongoClient;

    private static final String IP_LOCAL = "127.0.0.1";
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MongoEmbed.class);

    public static void start() throws IOException {

        if(running == false) {
            log.info("=== STARTING UP DB ===");


            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.DEVELOPMENT)
                    .net(new Net(27017, Network.localhostIsIPv6()))
                    .build();


            mongodExe = MongodStarter.getDefaultInstance().prepare(mongodConfig);
            mongod = mongodExe.start();

            mongoClient = new MongoClient( IP_LOCAL, 27017);
            running = true;
        }
        else
        {
            log.info("already running");
        }
    }

    public static void stop() {

        if(running) {

            log.info("=== SHUTTING DOWN TEST DB ===");
            if (mongod != null)
                mongod.stop();

            if (mongodExe != null)
                mongodExe.cleanup();

            if(mongoClient !=null)
                mongoClient = null;

            running = false;
        }
    }

}
