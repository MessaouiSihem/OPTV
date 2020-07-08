package com.example.orangeapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.orangeapplication.data.model.detail.DetailProgram
import com.example.orangeapplication.data.model.program.Program
import com.example.orangeapplication.data.repository.ProgramRepository
import com.example.orangeapplication.utils.Resource
import com.example.orangeapplication.viewmodel.DetailProgramViewModel
import com.example.orangeapplication.viewmodel.ProgramViewModel
import com.example.orangeapplication.viewmodel.base.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @Rule
    @JvmField
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: DetailProgramViewModel

    @Mock
    var observer: Observer<Resource<DetailProgram>>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailProgramViewModel(ProgramRepository())

        observer?.let { viewModel.getDetail().observeForever(it) }
    }

    @Test
    fun check_has_observer() {
        assertTrue(viewModel.getDetail().hasObservers())
    }

    @Test
    fun check_not_null() {
        assertNotNull(viewModel.getDetail())
    }

    @Test
    fun must_return_error_response() {
        viewModel.fetchDetail("/apps/v2/details/serie/TITATITATA")

        // We Must comment those lines in DetailViewModel
        // .subscribeOn(Schedulers.io())
        // .observeOn(AndroidSchedulers.mainThread())

        verify(observer)?.onChanged(Resource.loading(null))
        verify(observer)?.onChanged(
            Resource.error(
                "Une erreur est survenue !",
                null
            )
        )
    }

    @Test
    fun must_return_success_response() {
        // We Must comment those lines in DetailViewModel
        // .subscribeOn(Schedulers.io())
        // .observeOn(AndroidSchedulers.mainThread())
        viewModel.fetchDetail("/apps/v2/details/serie/PSGAMEOFTHRW0058624")

        verify(observer)?.onChanged(Resource.loading(null))
        verify(observer)?.onChanged(Resource.success(null))
    }
}