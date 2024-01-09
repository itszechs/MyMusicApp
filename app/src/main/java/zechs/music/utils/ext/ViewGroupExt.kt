package zechs.music.utils.ext

import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionManager

fun ViewGroup.doTransition(transition: Transition?) {
    TransitionManager.endTransitions(this)
    TransitionManager.beginDelayedTransition(this, transition)
}