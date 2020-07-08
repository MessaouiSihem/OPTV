package com.example.orangeapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.orangeapplication.data.model.program.Program
import com.example.orangeapplication.data.repository.ProgramRepository
import com.example.orangeapplication.utils.Resource
import com.example.orangeapplication.viewmodel.ProgramViewModel
import com.example.orangeapplication.viewmodel.base.ViewModelFactory
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProgramViewModelTest {

    @Rule  @JvmField
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: ProgramViewModel

    @Mock
    var observer: Observer<Resource<Program>>? = null

    @Before
    fun setUp()  {
        MockitoAnnotations.initMocks(this)
        viewModel= ProgramViewModel(ProgramRepository())

        observer?.let { viewModel.programs.observeForever(it) }
    }

    @Test
    fun check_has_observer(){

        // We Must comment those lines in ProgramViewModel
        // .subscribeOn(Schedulers.io())
        // .observeOn(AndroidSchedulers.mainThread())
        assertTrue(viewModel.getPrograms().hasObservers())
    }

    @Test
    fun check_not_null() {
        // We Must comment those lines in ProgramViewModel
        // .subscribeOn(Schedulers.io())
        // .observeOn(AndroidSchedulers.mainThread())

       assertNotNull(viewModel.getPrograms())
    }

    @Test
    fun must_return_error_response() {
        viewModel.fetchPrograms("TATATI")

        // We Must comment those lines in ProgramViewModel
        // .subscribeOn(Schedulers.io())
        // .observeOn(AndroidSchedulers.mainThread())

        Mockito.verify(observer)?.onChanged(Resource.loading(null))
        Mockito.verify(observer)?.onChanged(
            Resource.error(
                "Une erreur est survenue !",
                null
            )
        )
    }

    @Test
    fun must_return_success_response() {
        // We Must comment those lines in ProgramViewModel
        // .subscribeOn(Schedulers.io())
        // .observeOn(AndroidSchedulers.mainThread())
        viewModel.fetchPrograms("title%3DAmour")

        Mockito.verify(observer)?.onChanged(Resource.loading(null))
        Mockito.verify(observer)?.onChanged(Resource.success(null))
    }
}