package com.jdroid.javaweb.facebook

import com.restfb.Facebook

class FacebookUser {

    @Facebook("uid")
    var facebookId: String? = null

    @Facebook("first_name")
    var firstName: String? = null

    @Facebook("last_name")
    var lastName: String? = null

    @Facebook("is_app_user")
    var isAppUser: Boolean? = null
}
