package com.example.careme.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.careme.data.network.ApiService
import com.example.careme.data.network.ResultItem
import com.example.careme.database.HistoryDatabase
import com.example.careme.database.RemoteKeys

//@OptIn(ExperimentalPagingApi::class)
//class HistoryRemoteMediator(
//    private val database: HistoryDatabase,
//    private val apiService: ApiService
//) : RemoteMediator<Int, ResultItem>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, ResultItem>
//    ): MediatorResult {
//        try {
//            val page = when (loadType) {
//                LoadType.REFRESH -> {
//                    // Refresh: reset to initial page index
//                    INITIAL_PAGE_INDEX
//                }
//                LoadType.PREPEND -> {
//                    // Prepend: not needed for your case, return Success with endOfPaginationReached = true
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                LoadType.APPEND -> {
//                    // Append: get the next page based on the last item's nextKey
//                    val remoteKeys = getRemoteKeyForLastItem(state)
//                    val nextPage = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
//                    nextPage
//                }
//            }
//
//            // Make network request to get data
//            val response = apiService.getHistory()
//            val responseData = response.result // Adjust this based on your actual response structure
//
//            database.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    // Clear existing database tables if refreshing
//                    database.remoteKeysDao().deleteRemoteKeys()
//                    database.historyDao().deleteAll()
//                }
//
//                // Calculate prevKey and nextKey
//                val prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1
//                val nextKey = if (responseData.isEmpty()) null else page + 1
//
//                // Map API response to RemoteKeys and insert into database
//                val keys = responseData.map {
//                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
//                }
//                database.remoteKeysDao().insertAll(keys)
//                // Insert history data into database
//                database.historyDao().insertHistory(responseData)
//            }
//
//            return MediatorResult.Success(endOfPaginationReached = responseData.isEmpty())
//        } catch (exception: Exception) {
//            return MediatorResult.Error(exception)
//        }
//    }
//
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ResultItem>): RemoteKeys? {
//        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
//            database.remoteKeysDao().getRemoteKeysId(data.id)
//        }
//    }
//
//    companion object {
//        private const val INITIAL_PAGE_INDEX = 1
//    }
//}

@OptIn(ExperimentalPagingApi::class)
class HistoryRemoteMediator(
    private val database: HistoryDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, ResultItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            Log.d("HistoryRemoteMediator", "Loading page: $page")
            val responseData = apiService.getHistory(page, state.config.pageSize).result
            Log.d("HistoryRemoteMediator", "Response data size: ${responseData.size}")

            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.historyDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.historyDao().insertHistory(responseData)

                Log.d("StoryRemoteMediator", "Loaded data size: ${responseData.size}")

                Log.d("StoryRemoteMediator", "endOfPaginationReached: $endOfPaginationReached")
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ResultItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ResultItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ResultItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

}