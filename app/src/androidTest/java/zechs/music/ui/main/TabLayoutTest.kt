package zechs.music.ui.main


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import zechs.music.R
import zechs.music.TabLayoutMatcher

@LargeTest
@RunWith(AndroidJUnit4::class)
class TabLayoutTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkPlaylistSongsAlbumArtistsTabsExist() {
        val tabLayout = onView(
            allOf(
                withId(R.id.tabLayout),
                withParent(withParent(IsInstanceOf.instanceOf(ViewGroup::class.java))),
                isDisplayed()
            )
        )
        tabLayout.check(matches(isDisplayed()))
        tabLayout.check(matches(TabLayoutMatcher.hasTabCount(4)))

        val viewPager = onView(
            allOf(
                withId(R.id.viewPager),
                withParent(withParent(withId(R.id.mainNavHostFragment))),
                isDisplayed()
            )
        )
        viewPager.check(matches(isDisplayed()))

        val tabView = onView(
            allOf(
                withContentDescription("Playlists"),
                childAtPosition(childAtPosition(withId(R.id.tabLayout), 0), 0),
                isDisplayed()
            )
        )
        tabView.perform(click())

        val textView = onView(
            allOf(
                withText("Playlists"),
                withParent(
                    allOf(
                        withContentDescription("Playlists"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Playlists")))

        val tabView2 = onView(
            allOf(
                withContentDescription("Songs"),
                childAtPosition(childAtPosition(withId(R.id.tabLayout), 0), 1),
                isDisplayed()
            )
        )
        tabView2.perform(click())

        val textView2 = onView(
            allOf(
                withText("Songs"),
                withParent(
                    allOf(
                        withContentDescription("Songs"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Songs")))

        val tabView3 = onView(
            allOf(
                withContentDescription("Albums"),
                childAtPosition(childAtPosition(withId(R.id.tabLayout), 0), 2),
                isDisplayed()
            )
        )
        tabView3.perform(click())

        val textView3 = onView(
            allOf(
                withText("Albums"),
                withParent(
                    allOf(
                        withContentDescription("Albums"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Albums")))

        val tabView4 = onView(
            allOf(
                withContentDescription("Artists"),
                childAtPosition(childAtPosition(withId(R.id.tabLayout), 0), 3),
                isDisplayed()
            )
        )
        tabView4.perform(click())

        val textView4 = onView(
            allOf(
                withText("Artists"),
                withParent(
                    allOf(
                        withContentDescription("Artists"),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Artists")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }

    }
}
