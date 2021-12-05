package com.ldev.kinoonline.feature.main_screen.ui

import com.ldev.kinoonline.feature.base.view_model.Sorting

sealed class SortedBy : Sorting {
    object Date : Sorting
    object Name : Sorting
    object Popularity : Sorting
    object Rating : Sorting
}
