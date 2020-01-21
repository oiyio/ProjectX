package co.icanteach.projectx.ui

import androidx.lifecycle.MutableLiveData
import co.icanteach.projectx.common.Resource
import co.icanteach.projectx.common.RxAwareViewModel
import co.icanteach.projectx.common.ui.plusAssign
import co.icanteach.projectx.data.feed.MoviesRepository
import co.icanteach.projectx.data.feed.PopularTVShowsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PopularTVShowsViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : RxAwareViewModel() {

    internal val popularTvShowsLiveData = MutableLiveData<PopularTVShowsFeedViewState>()

    fun fetchMovies(page: Int) =
            moviesRepository
                    .fetchMovies(page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { resource ->
                        onMoviesResultReady(resource)
                    }
                    .also {
                        disposable += it
                    }

    private fun onMoviesResultReady(resource: Resource<PopularTVShowsResponse>) {
        popularTvShowsLiveData.value = PopularTVShowsFeedViewState(
                status = resource.status,
                error = resource.error,
                data = resource.data
        )
    }
}