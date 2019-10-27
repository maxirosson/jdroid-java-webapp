package com.jdroid.javaweb.facebook

import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.Parameter
import com.restfb.exception.FacebookOAuthException
import com.restfb.types.FacebookType
import com.restfb.types.User
import java.util.NoSuchElementException

class FacebookRepository {

    private fun createFacebookClient(accessToken: String): FacebookClient {
        return DefaultFacebookClient(accessToken)
    }

    fun exist(accessToken: String, facebookId: String): Boolean? {
        val fb = createFacebookClient(accessToken)
        val connection = fb.fetchConnection(FB_SEARCH, User::class.java, Parameter.with(FB_ID, facebookId))
        return connection.data.isNotEmpty()
    }

    fun getProfile(accessToken: String): User {
        val fb = createFacebookClient(accessToken)
        return fb.fetchObject(FB_ME, User::class.java)
    }

    fun isFriend(accessToken: String, facebookId: String): Boolean? {
        return getFriend(accessToken, facebookId) != null
    }

    @Throws(FacebookOAuthException::class)
    fun getFriend(accessToken: String, facebookId: String): User? {
        val fb = createFacebookClient(accessToken)
        val users = fb.executeFqlQuery(FRIEND_FQL.replace(FRIEND_FQL_REPLACEMENT.toRegex(), facebookId), User::class.java)
        return try {
            users.iterator().next()
        } catch (e: NoSuchElementException) {
            null
        }
    }

    @Throws(FacebookOAuthException::class)
    fun getFriends(accessToken: String): List<FacebookUser> {
        val fb = createFacebookClient(accessToken)
        return fb.executeFqlQuery(FRIENDS_FQL, FacebookUser::class.java)
    }

    @Throws(FacebookOAuthException::class)
    fun getAppFriends(accessToken: String): List<FacebookUser> {
        val fb = createFacebookClient(accessToken)
        return fb.executeFqlQuery(APP_FRIENDS_FQL, FacebookUser::class.java)
    }

    @Throws(FacebookOAuthException::class)
    fun getAppFriendsIds(accessToken: String): List<String> {
        val fb = createFacebookClient(accessToken)
        val facebookUsers = fb.executeFqlQuery(APP_FRIENDS_IDS_FQL, FacebookUser::class.java)
        return facebookUsers.map { it.facebookId!! }
    }

    fun publish(accessToken: String, message: String) {
        publish(accessToken, FB_ME, message)
    }

    fun publish(accessToken: String, facebookId: String, message: String) {
        val fb = createFacebookClient(accessToken)
        fb.publish(facebookId + FB_FEED, FacebookType::class.java, Parameter.with(FB_MESSAGE, message))
    }

    fun publishLink(
        accessToken: String,
        link: String,
        message: String,
        image: String,
        caption: String,
        description: String
    ) {
        publishLink(accessToken, FB_ME, link, message, image, caption, description)
    }

    fun publishLink(
        accessToken: String,
        facebookId: String,
        link: String,
        message: String,
        image: String,
        caption: String,
        description: String
    ) {
        val fb = createFacebookClient(accessToken)
        fb.publish(
            facebookId + FB_FEED, FacebookType::class.java, Parameter.with(FB_LINK, link),
            Parameter.with(FB_MESSAGE, message), Parameter.with(FB_PICTURE, image),
            Parameter.with(FB_CAPTION, caption), Parameter.with(FB_DESC, description)
        )
    }

    companion object {

        private const val FRIEND_FQL = "SELECT uid,name FROM user WHERE uid in (SELECT uid1 FROM friend WHERE uid2 = me() and uid1 = #friendId#)"
        private const val FRIEND_FQL_REPLACEMENT = "#friendId#"
        private const val FRIENDS_FQL =
            "SELECT uid,first_name,last_name,is_app_user FROM user WHERE uid in (SELECT uid1 FROM friend WHERE uid2 = me()) order by name"
        private const val APP_FRIENDS_FQL =
            "SELECT uid,first_name,last_name FROM user WHERE is_app_user AND uid in (SELECT uid1 FROM friend WHERE uid2 = me()) order by name"
        private const val APP_FRIENDS_IDS_FQL =
            "SELECT uid FROM user WHERE is_app_user AND uid in (SELECT uid1 FROM friend WHERE uid2 = me()) order by name"
        private const val FB_ID = "id"
        private const val FB_ME = "me"
        private const val FB_FEED = "/feed"
        private const val FB_SEARCH = "search"
        private const val FB_MESSAGE = "message"
        private const val FB_CAPTION = "caption"
        private const val FB_LINK = "link"
        private const val FB_PICTURE = "picture"
        private const val FB_DESC = "description"
    }
}
