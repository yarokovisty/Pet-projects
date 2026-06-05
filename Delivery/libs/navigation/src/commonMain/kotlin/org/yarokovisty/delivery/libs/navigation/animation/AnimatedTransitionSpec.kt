package org.yarokovisty.delivery.libs.navigation.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation3.scene.Scene
import androidx.navigationevent.NavigationEvent
import org.yarokovisty.delivery.libs.navigation.destination.Screen

private const val ANIM_SLIDE_DURATION = 350
private const val ANIM_FADE_DURATION = 150

fun <T : Screen> slideNextTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform = {
    ContentTransform(
        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(ANIM_SLIDE_DURATION)),
        slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(ANIM_SLIDE_DURATION))
    )
}

fun <T : Screen> slidePreviousTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform = {
    ContentTransform(
        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(ANIM_SLIDE_DURATION)),
        slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(ANIM_SLIDE_DURATION))
    )
}

fun <T : Screen> slidePredictivePreviousTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.(
    @NavigationEvent.SwipeEdge Int
) -> ContentTransform =
    {
        ContentTransform(
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(ANIM_SLIDE_DURATION)),
            slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(ANIM_SLIDE_DURATION))
        )
    }

fun <T : Screen> fadeTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform = {
    ContentTransform(
        fadeIn(animationSpec = tween(ANIM_FADE_DURATION)),
        fadeOut(animationSpec = tween(ANIM_FADE_DURATION))
    )
}

fun <T : Screen> fadePredictiveTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.(
    @NavigationEvent.SwipeEdge Int
) -> ContentTransform = {
    ContentTransform(
        fadeIn(animationSpec = tween(ANIM_FADE_DURATION)),
        fadeOut(animationSpec = tween(ANIM_FADE_DURATION))
    )
}
