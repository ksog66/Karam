package com.kklabs.karam.presentation.components.githubchart

import java.time.LocalDate

open class JetCalendarType

data class JetDay(val date: LocalDate, val isPartOfMonth: Boolean) : JetCalendarType()