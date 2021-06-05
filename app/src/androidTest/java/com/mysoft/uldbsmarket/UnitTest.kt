package com.mysoft.uldbsmarket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mysoft.uldbsmarket.model.Good
import com.mysoft.uldbsmarket.util.Util
import com.mysoft.uldbsmarket.vm.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UnitTest {
    //Тестовые данные 1
    val test_chat_uuid = "3a7ac6a6-3587-4f70-9259-d2c5bd0655d4"
    val test_managername = "Александр_test"
    val test_client_uuid = "0aab3e7f-cde2-4b0d-ac93-141c987be013"
    //Тестовые данные 2
    val good_id = "0aab3e7f-cde2-4b0d-ac93-141c987be013"
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test fun testOpenChatFragment() {
        val bundle : Bundle = Bundle();
        bundle.putString("chatid",test_chat_uuid)
        bundle.putString("managername",test_managername)
        bundle.putString("userid", test_chat_uuid)
        activityRule.scenario.onActivity {
            it.findNavController(R.id.nav_host_fragment)
            .navigate(R.id.nav_chat_fragment, bundle)
        }
    }

    @Test fun testOpenGoodFragment() {
        val bundle : Bundle = Bundle();
        bundle.putString("goodid",good_id)
        activityRule.scenario.onActivity {
            it.findNavController(R.id.nav_host_fragment)
                .navigate(R.id.nav_good_fragment, bundle)
        }
    }

    @Test fun testPriceCalculation(){
        val goods : List<Good> = listOf(
            Good(UUID.randomUUID(),"test1","","",500f,"", emptyList()),
            Good(UUID.randomUUID(),"test2","","",750f,"", emptyList())
        )
        val expected = 1250f
        assertEquals(expected, Good.SummPrice(goods))
    }

    @Test fun testHashing(){
        val string = "testabc1234"
        val hash = "36738db5f95a6bc7d8c058529926f4fb"
        assertEquals(hash, Util.md5(string))
    }

    @Test fun testViewModelFactory(){
        activityRule.scenario.onActivity {
            val factory = ViewModelFactory(it)
            factory.create(UserViewModel::class.java)
            factory.create(GoodViewModel::class.java)
            factory.create(RequestViewModel::class.java)
            factory.create(ChatViewModel::class.java)
        }
    }
}