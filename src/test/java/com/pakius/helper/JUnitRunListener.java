package com.pakius.helper;


import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.slf4j.LoggerFactory;



public class JUnitRunListener extends RunListener {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JUnitRunListener.class);

    @Override
    public void testRunStarted(Description description) throws Exception {

        super.testRunStarted(description);
        log.info("=== started ===");

        MongoEmbed.start();
    }

    @Override
    public void testRunFinished(org.junit.runner.Result result) throws Exception {

        super.testRunFinished(result);
        MongoEmbed.stop();
        log.info("=== finished ===");
    }
}
