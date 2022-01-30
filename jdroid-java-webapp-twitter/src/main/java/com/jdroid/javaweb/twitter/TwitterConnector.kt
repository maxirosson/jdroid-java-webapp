package com.jdroid.javaweb.twitter

import com.jdroid.java.exception.UnexpectedException
import twitter4j.TwitterFactory
import com.jdroid.javaweb.config.CoreConfigParameter
import twitter4j.StatusUpdate
import twitter4j.TwitterException
import java.lang.RuntimeException
import com.jdroid.java.utils.LoggerUtils
import com.jdroid.javaweb.application.Application
import twitter4j.Query
import twitter4j.Status
import twitter4j.conf.ConfigurationBuilder
import java.io.File
import java.lang.Exception

class TwitterConnector(
    oAuthConsumerKey: String = Application.get().remoteConfigLoader.getString(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_KEY)!!,
    oAuthConsumerSecret: String = Application.get().remoteConfigLoader.getString(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_SECRET)!!,
    oAuthAccessToken: String = Application.get().remoteConfigLoader.getString(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN)!!,
    oAuthAccessTokenSecret: String = Application.get().remoteConfigLoader.getString(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN_SECRET)!!
) {

    private var twitterFactory: TwitterFactory

    companion object {
        private val LOGGER = LoggerUtils.getLogger(TwitterConnector::class.java)
        const val CHARACTERS_LIMIT = 140
        const val NEW_CHARACTERS_LIMIT = 280
        const val URL_CHARACTERS_COUNT = 22
    }

    init {
        val cb = ConfigurationBuilder()
        cb.setDebugEnabled(true)
        cb.setOAuthConsumerKey(oAuthConsumerKey)
        cb.setOAuthConsumerSecret(oAuthConsumerSecret)
        cb.setOAuthAccessToken(oAuthAccessToken)
        cb.setOAuthAccessTokenSecret(oAuthAccessTokenSecret)
        twitterFactory = TwitterFactory(cb.build())
    }

    fun tweet(text: String, mediaPath: String?) {
        tweet(text, if (mediaPath != null) File(mediaPath) else null)
    }

    fun tweet(text: String, media: File? = null) {
        try {
            if (Application.get().remoteConfigLoader.getBoolean(CoreConfigParameter.TWITTER_ENABLED)!!) {
                val statusUpdate = StatusUpdate(text)
                if (media != null) {
                    statusUpdate.setMedia(media)
                }
                val status = twitterFactory.instance.updateStatus(statusUpdate)
                LOGGER.info("Successfully updated the status to [" + status.text + "].")
            } else {
                LOGGER.info("Ignored tweet status [" + text + "]" + if (media != null) " with media: " + media.absolutePath else "")
            }
        } catch (e: TwitterException) {
            throw RuntimeException(e)
        }
    }

    fun tweetSafe(text: String) {
        try {
            tweet(text)
        } catch (e: Exception) {
            LOGGER.error("Error when posting on Twitter: [$text]", e)
        }
    }

    fun searchTweets(queryText: String?): List<Status> {
        return searchTweets(Query(queryText))
    }

    fun searchTweets(query: Query?): List<Status> {
        val twitter = twitterFactory.instance
        return try {
            twitter.search(query).tweets
        } catch (e: TwitterException) {
            throw UnexpectedException(e)
        }
    }

    fun retweetStatus(tweetId: Long) {
        val twitter = twitterFactory.instance
        try {
            twitter.retweetStatus(tweetId)
        } catch (e: TwitterException) {
            throw UnexpectedException(e)
        }
    }
}
