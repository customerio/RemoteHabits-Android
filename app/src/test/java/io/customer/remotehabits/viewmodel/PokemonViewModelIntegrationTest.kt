package io.customer.remotehabits.viewmodel

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.HiltAndroidTest
import io.customer.remotehabits.BaseTest
import io.customer.remotehabits.R
import io.customer.remotehabits.rule.runBlockingTest
import io.customer.remotehabits.service.repository.PokemonRepository
import io.customer.remotehabits.service.vo.PokemonSpritesVo
import io.customer.remotehabits.service.vo.PokemonVo
import javax.inject.Inject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Response

// Added fallback because Robolectric does not support SDK 31 yet
@Config(sdk = [30])
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PokemonViewModelIntegrationTest : BaseTest() {

    override fun provideTestClass(): Any = this

    // We have to manually construct the ViewModel because @Inject doesn't work for ViewModel
    @Inject
    lateinit var pokemonRepository: PokemonRepository
    private lateinit var viewModel: PokemonViewModel

    @get:Rule
    val runInstantTaskRule = testRules

    @Before
    override fun setup() {
        super.setup()

        viewModel = PokemonViewModel(pokemonRepository)
    }

    @Test
    fun viewmodel_test() = mainCoroutineRule.runBlockingTest {
        whenever(pokemonApiService.getPokemon(any())).thenReturn(
            Response.success(
                200,
                PokemonVo(
                    context.getString(R.string.app_name),
                    PokemonSpritesVo(Uri.parse(""), Uri.parse(""))
                )
            )
        )

        testCoroutine { done ->
            viewModel.getPokemon("Remote Habits") { actual ->
                assertThat(actual.isSuccess).isTrue()
                assertThat(actual.bodyOrThrow().name).isEqualTo("Remote Habits")

                done()
            }
        }
    }

    @Test
    fun repo_test() = mainCoroutineRule.runBlockingTest {
        whenever(pokemonApiService.getPokemon(any())).thenReturn(
            Response.success(
                200,
                PokemonVo("ditto", PokemonSpritesVo(Uri.parse(""), Uri.parse("")))
            )
        )

        val actual = pokemonRepository.getPokemon("ditto")

        assertThat(actual.isSuccess).isTrue()
        assertThat(actual.bodyOrThrow().name).isEqualTo("ditto")
    }
}
