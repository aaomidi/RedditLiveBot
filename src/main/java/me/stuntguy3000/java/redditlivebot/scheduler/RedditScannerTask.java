package me.stuntguy3000.java.redditlivebot.scheduler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.stuntguy3000.java.redditlivebot.RedditLiveBot;
import me.stuntguy3000.java.redditlivebot.handler.RedditHandler;
import me.stuntguy3000.java.redditlivebot.object.Lang;
import me.stuntguy3000.java.redditlivebot.object.reddit.RedditThread;
import me.stuntguy3000.java.redditlivebot.object.reddit.redditthread.RedditThreadChildren;
import me.stuntguy3000.java.redditlivebot.object.reddit.redditthread.RedditThreadChildrenData;

// @author Luke Anderson | stuntguy3000
public class RedditScannerTask extends TimerTask {
    public RedditScannerTask() {
        new Timer().schedule(this, 0, 15 * 1000);
    }

    @Override
    public void run() {
        Lang.sendDebug("Checking for new live threads...");

        try {
            RedditThread redditThread = RedditHandler.getThread("live");

            if (redditThread != null) {
                for (RedditThreadChildren threadChild : redditThread.getData().getChildren()) {
                    RedditThreadChildrenData threadData = threadChild.getData();

                    if (threadData != null && threadData.getMedia() != null) {
                        long secs = (new Date().getTime()) / 1000;
                        String threadID = threadData.getMedia().getEvent_id();

                        RedditLiveBot.instance.getAdminControlHandler().threadUpdate(threadData, threadID);

                        // Score more than 5, up to 3 hours old.
                        /*if (threadData.getScore() >= 5 &&
                                (secs - threadData.getCreated_utc() < 10800) &&
                                !RedditLiveBot.instance.getConfigHandler().getBotSettings().getKnownLiveFeeds().contains(threadID.toLowerCase())) {
                            Lang.sendDebug("Following thread %s.", threadID);
                            RedditLiveBot.instance.getRedditHandler().startLiveThread(threadData);
                            return;
                        }*/
                    }
                }
            }

            Lang.sendDebug("Nothing new!");
        } catch (Exception e) {
            Lang.sendDebug("Exception Caught: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
    