package pl.hypeapp.episodie.entrypoints.job.tvshow.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class UpdateTvShowsJobResult {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTvShowsJobResult.class);
    private final Date date = new Date();

    public void recordJobSuccessfulResult() {
        LOGGER.info(date.toString() + " JOB SUCCESSFUL");
    }

    public void recordJobUnsuccessfulResult(String message) {
        LOGGER.info(date.toString() + " JOB FAILED BECAUSE: " + message);
    }

}