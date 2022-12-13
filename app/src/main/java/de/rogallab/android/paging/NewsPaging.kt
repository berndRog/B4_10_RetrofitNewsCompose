package de.rogallab.android.paging

/*
class NewsPaging {

   fun getCharacters(
      newsWebservice: INewsWebApi
   ): PagingSource<Int, News> {

      return object : PagingSource<Int, News>() {

         override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
            val pageNumber = params.key ?: 0

            val charactersResponse = newsRepository.getNews(page = pageNumber)
            val characters = charactersResponse.results

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (charactersResponse.info.next != null) pageNumber + 1 else null

            return LoadResult.Page(
               data = characters,
               prevKey = prevKey,
               nextKey = nextKey
            )
         }
      }
   }

}
*/