package io.customer.remotehabits.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.HiltAndroidTest
import io.customer.remotehabits.BaseActivityTest
import io.customer.remotehabits.R
import io.customer.remotehabits.service.util.RandomUtil
import io.customer.remotehabits.service.vo.PokemonVo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest: BaseActivityTest<MainActivity>() {

    override fun provideTestClass(): Any = this
    override fun provideActivityClass(): Class<MainActivity> = MainActivity::class.java

    @Inject lateinit var randomUtil: RandomUtil

    @get:Rule val runRules = uiRules

    @Test
    fun givenNonShinyPokemon_expectShowNonShinyPokemon() {
        val expectedPokemon = getJsonAsset<PokemonVo>(TestAssetFiles.GET_POKEMON_SUCCESS)

        whenever(randomUtil.randomInt(any(), any())).thenReturn(1)
        mockWebServer.queue(200, expectedPokemon.json)

        launchActivity()

        onView(withId(R.id.pokemon_textview))
            .check(matches(withText("You caught a Pokemon!\n${expectedPokemon.obj.name}")))
    }

    @Test
    fun givenShinyPokemon_expectShowShinyPokemon() {
        val expectedPokemon = getJsonAsset<PokemonVo>(TestAssetFiles.GET_POKEMON_SUCCESS)

        whenever(randomUtil.randomInt(any(), any())).thenReturn(0)
        mockWebServer.queue(200, expectedPokemon.json)

        launchActivity()

        onView(withId(R.id.pokemon_textview))
            .check(matches(withText("You caught a Pokemon!\n?@&* YOU CAUGHT A RARE SHINY?!\n${expectedPokemon.obj.name}")))
    }

}