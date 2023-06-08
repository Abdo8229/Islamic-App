package com.example.islamapplictation.ui.quran.soraSearchList

import com.example.islamapplictation.data.local.datapase.repo.SoraListRepoImp
import com.example.islamapplictation.util.Resource
import javax.inject.Inject

class GetAllSoraUseCase @Inject constructor(private val soraListRepo: SoraListRepoImp) :
    UseCase<NoParam, SoraListStatus>() {
    override suspend fun invoke(t: NoParam): SoraListStatus {
        return  when (val soraResponce = soraListRepo.getAllSora()) {
            is Resource.Error ->

                SoraListStatus.Failure(soraResponce.massage!!)

            is Resource.Success -> {
                val response = soraResponce.data
                if (response == null) {
                     SoraListStatus.Failure("UnExpected Error")

                } else {
                      SoraListStatus.Success(response)
                }
            }


            else -> SoraListStatus.Empty
        }
    }


}


abstract class UseCase<T,M>{
    abstract operator suspend fun invoke(t:T): M
}

object NoParam{}