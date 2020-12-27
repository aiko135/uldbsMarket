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

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
1
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
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
            .navigate(R.id.action_nav_chats_fragment_to_nav_chat_fragment, bundle)
        }
    }

    @Test fun testOpenGoodFragment() {
        val bundle : Bundle = Bundle();
        bundle.putString("goodid",good_id)
        activityRule.scenario.onActivity {
            it.findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_nav_catalog_fragment_to_nav_good_fragment, bundle)
        }
    }
}