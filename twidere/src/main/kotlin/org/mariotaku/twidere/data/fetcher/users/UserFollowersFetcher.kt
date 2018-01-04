/*
 *             Twidere - Twitter client for Android
 *
 *  Copyright (C) 2012-2017 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mariotaku.twidere.data.fetcher.users

import org.mariotaku.microblog.library.Fanfou
import org.mariotaku.microblog.library.Mastodon
import org.mariotaku.microblog.library.StatusNet
import org.mariotaku.microblog.library.Twitter
import org.mariotaku.microblog.library.model.Paging
import org.mariotaku.microblog.library.model.mastodon.Account
import org.mariotaku.microblog.library.model.mastodon.LinkHeaderList
import org.mariotaku.microblog.library.model.microblog.User
import org.mariotaku.twidere.data.fetcher.UsersFetcher
import org.mariotaku.twidere.exception.RequiredFieldNotFoundException
import org.mariotaku.twidere.model.AccountDetails
import org.mariotaku.twidere.model.UserKey

class UserFollowersFetcher(
        val userKey: UserKey?,
        val screenName: String?
) : UsersFetcher {
    override fun forFanfou(account: AccountDetails, fanfou: Fanfou, paging: Paging): List<User> = when {
        userKey != null -> fanfou.getUsersFollowers(userKey.id, paging)
        screenName != null -> fanfou.getUsersFollowers(screenName, paging)
        else -> throw RequiredFieldNotFoundException("user_id", "screen_name")
    }

    override fun forMastodon(account: AccountDetails, mastodon: Mastodon, paging: Paging): LinkHeaderList<Account> = when {
        userKey != null -> mastodon.getFollowers(userKey.id, paging)
        else -> throw RequiredFieldNotFoundException("user_id")
    }

    override fun forStatusNet(account: AccountDetails, statusNet: StatusNet, paging: Paging): List<User> = when {
        userKey != null -> statusNet.getStatusesFollowersList(userKey.id, paging)
        screenName != null -> statusNet.getStatusesFollowersListByScreenName(screenName, paging)
        else -> throw RequiredFieldNotFoundException("user_id", "screen_name")
    }

    override fun forTwitter(account: AccountDetails, twitter: Twitter, paging: Paging): List<User> = when {
        userKey != null -> twitter.getFollowersList(userKey.id, paging)
        screenName != null -> twitter.getFollowersListByScreenName(screenName, paging)
        else -> throw RequiredFieldNotFoundException("user_id", "screen_name")
    }

}