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

package org.mariotaku.twidere.task.statuses

import android.content.Context
import android.net.Uri
import org.mariotaku.microblog.library.MicroBlog
import org.mariotaku.microblog.library.mastodon.Mastodon
import org.mariotaku.microblog.library.twitter.model.Paging
import org.mariotaku.microblog.library.twitter.model.Status
import org.mariotaku.twidere.alias.MastodonStatus
import org.mariotaku.twidere.annotation.FilterScope
import org.mariotaku.twidere.annotation.ReadPositionTag
import org.mariotaku.twidere.fragment.HomeTimelineFragment
import org.mariotaku.twidere.model.AccountDetails
import org.mariotaku.twidere.model.UserKey
import org.mariotaku.twidere.model.refresh.RefreshTaskParam
import org.mariotaku.twidere.provider.TwidereDataStore.Statuses
import org.mariotaku.twidere.util.ErrorInfoStore
import org.mariotaku.twidere.util.sync.TimelineSyncManager

/**
 * Created by mariotaku on 16/2/11.
 */
class GetHomeTimelineTask(context: Context) : GetStatusesTask<RefreshTaskParam>(context) {

    override val contentUri: Uri = Statuses.CONTENT_URI

    override val filterScopes: Int = FilterScope.HOME

    override val errorInfoKey: String = ErrorInfoStore.KEY_HOME_TIMELINE

    override fun getTwitterStatuses(account: AccountDetails, twitter: MicroBlog, paging: Paging, params: RefreshTaskParam?): List<Status> {
        return twitter.getHomeTimeline(paging)
    }

    override fun getStatusNetStatuses(account: AccountDetails, statusNet: MicroBlog, paging: Paging, params: RefreshTaskParam?): List<Status> {
        return statusNet.getHomeTimeline(paging)
    }

    override fun getFanfouStatuses(account: AccountDetails, fanfou: MicroBlog, paging: Paging, params: RefreshTaskParam?): List<Status> {
        return fanfou.getHomeTimeline(paging)
    }

    override fun getMastodonStatuses(account: AccountDetails, mastodon: Mastodon, paging: Paging, params: RefreshTaskParam?): List<MastodonStatus> {
        return mastodon.getHomeTimeline(paging)
    }

    override fun syncFetchReadPosition(manager: TimelineSyncManager, accountKeys: Array<UserKey>) {
        val tag = HomeTimelineFragment.getTimelineSyncTag(accountKeys)
        manager.fetchSingle(ReadPositionTag.HOME_TIMELINE, tag)
    }
}