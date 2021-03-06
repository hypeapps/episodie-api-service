package pl.hypeapp.episodie.dataproviders.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.hypeapp.episodie.core.usecase.tvshow.toplist.collectimdbtoptvshows.GetImdbTopTvShows;
import pl.hypeapp.episodie.core.usecase.tvshow.toplist.collectimdbtoptvshows.ImdbTopTvShowCrawlerFailException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ImdbTopListCrawlerDataProvider implements GetImdbTopTvShows {

    private static final int PATH_SECOND_SEGMENT = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImdbTopListCrawlerDataProvider.class);

    @Override
    public List<String> crawl(String url) {
        Document document;
        try {
            document = getDocument(url);
        } catch (IOException e) {
            LOGGER.info("Unable to get document: " + e.getMessage());
            throw new ImdbTopTvShowCrawlerFailException();
        }
        return initCrawler(document);
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private List<String> initCrawler(Document document) {
        List<String> imdbTvShowIds = new LinkedList<>();
        try {
            for (int i = 1; i <= 125; i++) {
                Elements titleColumn = document.select(".lister-list > tr:nth-child(" + i + ") > td:nth-child(2) > a:nth-child(1)");
                String showUrl = titleColumn.attr("href");
                imdbTvShowIds.add(showUrl.split("/")[PATH_SECOND_SEGMENT]);
            }
        } catch (Exception e) {
            throw new ImdbTopTvShowCrawlerFailException();
        }
        return imdbTvShowIds;
    }

}
