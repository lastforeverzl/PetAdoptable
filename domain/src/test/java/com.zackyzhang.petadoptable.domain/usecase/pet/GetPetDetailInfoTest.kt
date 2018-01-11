package com.zackyzhang.petadoptable.domain.usecase.pet

import com.nhaarman.mockito_kotlin.mock
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetById
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetDetailInfo
import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelterById
import com.zackyzhang.petadoptable.domain.mapper.PetDetailMapper
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetPetDetailInfoTest {

    private lateinit var getPetDetail: GetPetDetailInfo

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockGetPetById: GetPetById
    private lateinit var mockGetShelterById: GetShelterById
    private lateinit var mockPetDetailMapper: PetDetailMapper

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockGetPetById = mock()
        mockGetShelterById = mock()
        mockPetDetailMapper = mock()
        getPetDetail = GetPetDetailInfo(mockGetShelterById, mockGetPetById, mockPetDetailMapper,
                mockThreadExecutor, mockPostExecutionThread)
    }

    @Before
    fun executeCallsRepository() {

    }
}