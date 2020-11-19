/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.wm.shell.flicker

import com.android.server.wm.flicker.dsl.EventLogAssertion
import com.android.server.wm.flicker.dsl.LayersAssertion
import com.android.server.wm.flicker.dsl.WmAssertion
import com.android.server.wm.flicker.helpers.WindowUtils

@JvmOverloads
fun WmAssertion.statusBarWindowIsAlwaysVisible(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    all("statusBarWindowIsAlwaysVisible", bugId, enabled) {
        this.showsAboveAppWindow(FlickerTestBase.STATUS_BAR_WINDOW_TITLE)
    }
}

@JvmOverloads
fun WmAssertion.navBarWindowIsAlwaysVisible(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    all("navBarWindowIsAlwaysVisible", bugId, enabled) {
        this.showsAboveAppWindow(FlickerTestBase.NAVIGATION_BAR_WINDOW_TITLE)
    }
}

@JvmOverloads
fun LayersAssertion.noUncoveredRegions(
    beginRotation: Int,
    endRotation: Int = beginRotation,
    allStates: Boolean = true,
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    val startingBounds = WindowUtils.getDisplayBounds(beginRotation)
    val endingBounds = WindowUtils.getDisplayBounds(endRotation)
    if (allStates) {
        all("noUncoveredRegions", bugId, enabled) {
            if (startingBounds == endingBounds) {
                this.coversAtLeastRegion(startingBounds)
            } else {
                this.coversAtLeastRegion(startingBounds)
                        .then()
                        .coversAtLeastRegion(endingBounds)
            }
        }
    } else {
        start("noUncoveredRegions_StartingPos") {
            this.coversAtLeastRegion(startingBounds)
        }
        end("noUncoveredRegions_EndingPos") {
            this.coversAtLeastRegion(endingBounds)
        }
    }
}

@JvmOverloads
fun LayersAssertion.statusBarLayerIsAlwaysVisible(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    all("statusBarLayerIsAlwaysVisible", bugId, enabled) {
        this.showsLayer(FlickerTestBase.STATUS_BAR_WINDOW_TITLE)
    }
}

@JvmOverloads
fun LayersAssertion.navBarLayerIsAlwaysVisible(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    all("navBarLayerIsAlwaysVisible", bugId, enabled) {
        this.showsLayer(FlickerTestBase.NAVIGATION_BAR_WINDOW_TITLE)
    }
}

@JvmOverloads
fun LayersAssertion.appPairsDividerIsVisible(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    end("appPairsDividerIsVisible", bugId, enabled) {
        this.showsLayer(FlickerTestBase.APP_PAIRS_DIVIDER)
    }
}

@JvmOverloads
fun LayersAssertion.appPairsDividerIsInvisible(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    end("appPairsDividerIsInVisible", bugId, enabled) {
        this.hasNotLayer(FlickerTestBase.APP_PAIRS_DIVIDER)
    }
}

@JvmOverloads
fun LayersAssertion.dockedStackDividerIsVisible(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    end("dockedStackDividerIsVisible", bugId, enabled) {
        this.showsLayer(FlickerTestBase.DOCKED_STACK_DIVIDER)
    }
}

@JvmOverloads
fun LayersAssertion.dockedStackDividerIsInvisible(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    end("dockedStackDividerIsInvisible", bugId, enabled) {
        this.hasNotLayer(FlickerTestBase.DOCKED_STACK_DIVIDER)
    }
}

@JvmOverloads
fun LayersAssertion.navBarLayerRotatesAndScales(
    beginRotation: Int,
    endRotation: Int = beginRotation,
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    val startingPos = WindowUtils.getNavigationBarPosition(beginRotation)
    val endingPos = WindowUtils.getNavigationBarPosition(endRotation)

    start("navBarLayerRotatesAndScales_StartingPos", bugId, enabled) {
        this.hasVisibleRegion(FlickerTestBase.NAVIGATION_BAR_WINDOW_TITLE, startingPos)
    }
    end("navBarLayerRotatesAndScales_EndingPost", bugId, enabled) {
        this.hasVisibleRegion(FlickerTestBase.NAVIGATION_BAR_WINDOW_TITLE, endingPos)
    }

    if (startingPos == endingPos) {
        all("navBarLayerRotatesAndScales", bugId, enabled) {
            this.hasVisibleRegion(FlickerTestBase.NAVIGATION_BAR_WINDOW_TITLE, startingPos)
        }
    }
}

@JvmOverloads
fun LayersAssertion.statusBarLayerRotatesScales(
    beginRotation: Int,
    endRotation: Int = beginRotation,
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    val startingPos = WindowUtils.getStatusBarPosition(beginRotation)
    val endingPos = WindowUtils.getStatusBarPosition(endRotation)

    start("statusBarLayerRotatesScales_StartingPos", bugId, enabled) {
        this.hasVisibleRegion(FlickerTestBase.STATUS_BAR_WINDOW_TITLE, startingPos)
    }
    end("statusBarLayerRotatesScales_EndingPos", bugId, enabled) {
        this.hasVisibleRegion(FlickerTestBase.STATUS_BAR_WINDOW_TITLE, endingPos)
    }
}

fun EventLogAssertion.focusChanges(
    vararg windows: String,
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    all(enabled = enabled, bugId = bugId) {
        this.focusChanges(windows)
    }
}

fun EventLogAssertion.focusDoesNotChange(
    bugId: Int = 0,
    enabled: Boolean = bugId == 0
) {
    all(enabled = enabled, bugId = bugId) {
        this.focusDoesNotChange()
    }
}
